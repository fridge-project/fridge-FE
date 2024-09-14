package com.example.alne.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.services.s3.model.S3Object
import com.example.alne.GlobalApplication
import com.example.alne.data.Network.FridgePostResponse
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.domain.repository.fridgeRepository
import com.example.alne.utils.RESPONSE_STATUS
import com.example.alne.utils.S3Util
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import javax.inject.Inject


@HiltViewModel
class FridgeViewModel @Inject constructor(
    private val repository: fridgeRepository,
): ViewModel() {

    val folderName = "fridge"
    val bucketName = "alne"

    //재료 등록 정보
    private val _getFridgeLiveData = MutableLiveData<ArrayList<FridgeIngredient>>()
    val getFridgeLiveData: LiveData<ArrayList<FridgeIngredient>> = _getFridgeLiveData

    // 현재 등록된 재료 아이템
    var fridgeItems = ArrayList<FridgeIngredient>()

    init {
        getFridgeFood()
    }

    // 재료 등록
    fun addItem(food: FridgeIngredient){
        fridgeItems.add(food)
        _getFridgeLiveData.postValue(fridgeItems)
    }

    // 재료 삭제
    fun deleteItem(position: Int){
        fridgeItems.removeAt(position)
        _getFridgeLiveData.postValue(fridgeItems)
    }

    fun updateItem(position: Int, food: FridgeIngredient){
        fridgeItems.removeAt(position)
        fridgeItems.add(food)
        _getFridgeLiveData.postValue(fridgeItems)
    }


    fun addFridgeDataTest(food: FridgeIngredient){
        repository.addFridgeDataTest(food).enqueue(object: Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                var res = response.body()
                when(response.code()){
                    200 -> {
                        var _id = res?.asJsonObject?.get("_id")?.asString
                        food._id = _id
                        addItem(food)

                    }

                    500 -> {

                    }
                }
                Log.d("addFridgeDataTest", res.toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {

            }

        })
    }

    fun addAWSS3Image(food: FridgeIngredient, context: Context, photoFile: File) {
        S3Util.instance
            .uploadWithTransferUtility(
                context,
                bucketName,
                folderName,
                photoFile!!,
                object : TransferListener {
                    override fun onStateChanged(id: Int, state: TransferState?) {
                        if (state === TransferState.COMPLETED) {
                            // 업로드 완료 시 URL 가져오기
                            var fileUrl = getS3ObjectUrl(bucketName, folderName+'/'+photoFile.name)
                            Toast.makeText(
                                context,
                                "Upload Completed! URL: $fileUrl", Toast.LENGTH_SHORT
                            ).show()
                            food.imageURL = fileUrl
                            addFridgeDataTest(food)
                            Log.d("onStateChanged", "Upload Completed! URL: $fileUrl")
                        } else if (state === TransferState.FAILED) {
                            // 업로드 실패 시 처리
                            Toast.makeText(
                                context,
                                "Upload Failed!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                        // 진행 상황 업데이트
                        // 진행 상황 업데이트
                        val percentDone = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                        Log.d("YourActivity", "Progress: $percentDone%")
                    }

                    override fun onError(id: Int, ex: Exception?) {
                        Log.d("S3Util", ex.toString())
                    }
                }
            )
    }

    fun getS3ObjectUrl(bucketName: String, key: String): String {
        return S3Util.instance.s3Client.getUrl(bucketName, key).toString();
    }

    fun getAWSGetPresignedURl(key: String) = S3Util.instance.getPresignedURl(bucketName,
        "$folderName/$key"
    )

    fun addFridgeData(food: FridgeIngredient, photoFile: File?){
        var fileBody: RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), photoFile!!)
        var file: MultipartBody.Part = MultipartBody.Part.createFormData("file", photoFile!!.name, fileBody)
        var content: RequestBody = RequestBody.create("application/json".toMediaTypeOrNull(), Gson().toJson(food))
        var accessToken = GlobalApplication.prefManager.getUserToken().accessToken
        repository.addFridgeData(accessToken,content, file).enqueue(object: Callback<FridgePostResponse>{
            override fun onResponse(
                call: Call<FridgePostResponse>,
                response: Response<FridgePostResponse>,
            ) {
                Log.d("addFridgeData", "onSuccess")
                Log.d("addFridgeData", response.body().toString())
                if(response.isSuccessful) {
                    var res = response.body()
                    when(res?.status){
                        200 -> {
                            Log.d("addFridgeData", "재료 등록 성공")
                            Log.d("addFridgeData", response.body()?.data.toString())
//                            getFridgeFood(getUserToken()?.accessToken!!, UserId(getUserToken()?.accessToken?.toInt()!!, null))

                        }
                        401 -> {
                            Log.d("addFridgeData", "재료 등록 실패")
                        }
                    }
                }
            }
            override fun onFailure(call: Call<FridgePostResponse>, t: Throwable) {
                Log.d("addFridgeData", "onFailure")
            }
        })
    }

    fun getFridgeFood(){
        repository.getFridgeFood().enqueue(object: Callback<JsonArray>{
            override fun onResponse(
                call: Call<JsonArray>,
                response: Response<JsonArray>,
            ) {
                var res: JsonArray? = response.body()
                when(response.code()){
                    200 -> {
                        if (res != null) {
                            var items: ArrayList<FridgeIngredient> = ArrayList()
                            for(json in res){
                                var jsonObject = json.asJsonObject
                                var _id = jsonObject.get("_id").asString
                                var name = jsonObject.get("name").asString
                                var memo = jsonObject.get("memo").asString
                                var storage = jsonObject.get("storage").asString
                                var exp = jsonObject.get("exp").asString
                                var date = jsonObject.get("date").asString
                                var imageURL = jsonObject.get("imageURL").asString
                                items.add(
                                    FridgeIngredient(_id,name,memo,storage,exp,date, imageURL)
                                )
                            }
                            fridgeItems.addAll(items)
                            _getFridgeLiveData.postValue(items)
                        }
                    }

                    401 -> {

                    }
                }
                Log.d("getFridgeFood", res.toString())
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Log.d("getFridgeFood", t.message.toString())
            }

        })
    }

    fun deleteFridgeFood(position: Int){
        repository.deleteFridgeFood(fridgeItems[position]._id!!).enqueue(object: Callback<String>{
            override fun onResponse(
                call: Call<String>,
                response: Response<String>,
            ) {
                var res = response.body()
                when(response.code()){
                    200 -> {
                        deleteItem(position)
                        Log.d("deleteFridgeFood", "성공")
                    }

                    //재료 삭제 실패(재료 id 오류)
                    401 -> {
                        Log.d("deleteFridgeFood", "401(실패)")
                    }

                    //재료 삭제 실패(accessToken 인증 실패)
                    500 -> {
                        Log.d("deleteFridgeFood", "500(실패)")
                    }
                }
                Log.d("deleteFridgeFood", res.toString())

            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("deleteFridgeFood", t.message.toString())
            }
        })
    }

    fun updateFridgeFood(food: FridgeIngredient, position: Int){
        repository.updateFridgeFood(food).enqueue(object: Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                var res = response.body()
                when(response.code()){
                    200 -> {
                        updateItem(position, food)
                        Log.d("updateFridgeFood", "성공")
                    }
                    500 -> {
                        Log.d("updateFridgeFood", "500(실패)")
                    }

                    401 -> {
                        Log.d("updateFridgeFood", "401(실패)")
                    }
                }
                Log.d("updateFridgeFood", res.toString())
            }

            override fun onFailure(p0: Call<JsonElement>, p1: Throwable) {
                Log.d("updateFridgeFood", p1.message.toString())
            }
        })

    }
}