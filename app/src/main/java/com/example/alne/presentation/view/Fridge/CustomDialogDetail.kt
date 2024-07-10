package com.example.alne.view.Fridge

import android.Manifest
import android.R
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.alne.databinding.ItemFoodDetailBinding
import com.example.alne.data.model.FridgeIngredient
import com.example.alne.data.model.Jwt
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class CustomDialogDetail(context: Context, val food: FridgeIngredient, myCustomDialogDetailInterface: MyCustomDialogDetailInterface): DialogFragment() {
    private lateinit var binding: ItemFoodDetailBinding
    var storage: String? = null
    val myAdapter = ArrayAdapter(
        context,
        R.layout.simple_spinner_dropdown_item,
        arrayListOf("냉장", "냉동")
    )
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_CHOOSER = 2
    var photoFile: File? = null

    var adate = Date()
    val atime = System.currentTimeMillis()
    val atimeFormat = SimpleDateFormat("hh:mm")
    val adateFormat = SimpleDateFormat("yyyy-MM-dd")

    private var myCustomDialogDetailInterface:MyCustomDialogDetailInterface? = null

    // 인터페이스 연결
    init {
        this.myCustomDialogDetailInterface = myCustomDialogDetailInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ItemFoodDetailBinding.inflate(inflater, container, false)
        if(food.imageURL != ""){
            binding.itemFoodDetailIv.visibility = View.VISIBLE
            Glide.with(requireContext()).load(food.imageURL).into(binding.itemFoodDetailIv)
            binding.itemFoodDetailIv.scaleType = ImageView.ScaleType.FIT_XY
            binding.itemFoodDetailDefaultIv.visibility = View.GONE
        }

        var dateString: String = food.exp!!
        var date = dateString.split(" ")[0]
        var time = dateString.split(" ")[1]
        var calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, date.split(".")[0].toInt())
        calendar.set(Calendar.MONTH, date.split(".")[1].toInt())
        calendar.set(Calendar.DAY_OF_MONTH, date.split(".")[2].toInt())
        calendar.set(Calendar.HOUR, time.split(":")[0].toInt())
        calendar.set(Calendar.MINUTE, time.split(":")[1].toInt())

        var ahour = atimeFormat.format(atime).split(":")[0]
        var aminute = atimeFormat.format(atime).split(":")[1]
        var adddate = adateFormat.format(adate).split("-")[0] +"."+ adateFormat.format(adate).split("-")[1] +"."+ adateFormat.format(adate).split("-")[2]
        var addtime: String? = ahour +":"+aminute
        var addDate = adddate + " " + addtime

        binding.foodTitleEt.setText(food.name)
        binding.foodMemoTv.setText(food.memo)

        binding.submitBt.setOnClickListener {
            val title = binding.foodTitleEt.text.toString()
            Log.d("data", title + " "+ storage)
            myCustomDialogDetailInterface?.onSubmitBtnDetailClicked(
                FridgeIngredient(null, title, binding.foodMemoTv.text.toString(), storage!!,date + " " + time, addDate, "" )
            )
//                FridgeIngredient(jwt.userId,title,date + " " + time, addDate, binding
//                .foodMemoTv.text.toString(),storage!!), photoFile)
            dismiss()
        }

        binding.cancelBt.setOnClickListener {
            dismiss()
        }

        binding.itemFoodDetailIv.setOnClickListener{
            showDialog()
        }

        binding.itemFoodDetailDefaultIv.setOnClickListener {
            showDialog()
        }

        val spinner = binding.spinner
        spinner.adapter = myAdapter
        spinner.setSelection(0)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    0 -> {
                        storage = "COLD"
                    }
                    1-> {
                        storage = "FROZEN"
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        binding.foodAddDatePicker.text = "${calendar.get(Calendar.YEAR)}.${String.format("%02d",calendar.get(Calendar.MONTH))}.${String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH))}"
        var mDateSetListener = object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                date = "${p1}.${p2+1}.${p3}"
                binding.foodAddDatePicker.text = date
            }
        }
        binding.foodAddTimePicker.text = "${String.format("%02d",calendar.get(Calendar.HOUR))}:${String.format("%02d",calendar.get(Calendar.MINUTE))}"
        var mTimeSetListener = object: TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                Log.d("time",p1.toString() + " " + p2.toString())
                time = "${p1}:${p2}"
                binding.foodAddTimePicker.text = time
            }
        }
        binding.foodAddDatepickerLinear.setOnClickListener{
            DatePickerDialog(requireContext(),
                AlertDialog.THEME_HOLO_LIGHT,mDateSetListener, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        binding.foodAddTimepickerLinear.setOnClickListener{
            TimePickerDialog(requireContext(), AlertDialog.THEME_HOLO_LIGHT, mTimeSetListener, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE),true).show()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialogFragmentResize()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun dialogFragmentResize() {
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val deviceWidth = size.x
        val deviceHeght = size.y
        val params = dialog?.window?.attributes
        params?.width = (deviceWidth*0.95).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun dispatchTakePictureIntent() {

        val permissionCheck = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.CAMERA
        )

        // 허용이 안되어있으면,
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // 다시한번 물어 봐라
            ActivityCompat.requestPermissions(
                context as Activity, arrayOf<String>(Manifest.permission.CAMERA),
                1000
            )
            Toast.makeText(
                context, "카메라 권한 필요합니다.",
                Toast.LENGTH_SHORT
            ).show()
            return
        } else {
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // 사진의 파일명을 만들기
            val fileName = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            photoFile = getPhotoFile(fileName)

            //android:authorities="com.wonjun.cameraapp.fileprovider"
            // 동일해야함
            val fileProvider: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.alne.fileprovider", photoFile!!
            )
            i.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            startActivityForResult(i, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun getExtension(fileStr: String): String {
        val fileExtension = fileStr.substring(fileStr.lastIndexOf(".")+1,fileStr.length);
        return fileExtension
    }

    private fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap): Uri? {
        var values= ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME,filename)
        values.put(MediaStore.Images.Media.MIME_TYPE,mimeType)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            values.put(MediaStore.Images.Media.IS_PENDING,1)
        }
        val uri=requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        try {
            if(uri!=null){
                val descriptor=requireContext().contentResolver.openFileDescriptor(uri,"w")
                if(descriptor!=null){
                    val fos= FileOutputStream(descriptor.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
                    fos.close()
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING,0)
                        requireContext().contentResolver.update(uri,values,null,null)
                    }
                }
            }
        } catch (e: java.lang.Exception){
            Log.e("File","error=")
        }
        return uri
    }

    private fun getPhotoFile(fileName: String): File? {
        val storageDirectory: File = context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return try {
            File.createTempFile(fileName, ".jpg", storageDirectory)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun chooserImage(){
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            startActivityForResult(
                Intent.createChooser(this, "Get Album"),
                REQUEST_IMAGE_CHOOSER
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var bitmap: Bitmap? = null
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            if(Build.VERSION.SDK_INT<28) {
                bitmap = MediaStore.Images.Media
                    .getBitmap(requireContext().contentResolver, Uri.fromFile(photoFile))
                binding.itemFoodDetailIv.setImageBitmap(bitmap)
                binding.itemFoodDetailIv.scaleType = ImageView.ScaleType.FIT_XY
            }else{
                val decode= ImageDecoder.createSource(requireContext().contentResolver,
                    Uri.fromFile(photoFile!!.absoluteFile))
                bitmap= ImageDecoder.decodeBitmap(decode)
                binding.itemFoodDetailIv.setImageBitmap(bitmap)
                binding.itemFoodDetailIv.scaleType = ImageView.ScaleType.FIT_XY

                if(bitmap != null){
                    var uri = saveImageFile(photoFile!!.name,getExtension(photoFile!!.name),bitmap)
                }
            }
        }else if( requestCode == REQUEST_IMAGE_CHOOSER && resultCode== Activity.RESULT_OK && data?.data != null){
            var albumUri: Uri = data.data!!
            bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, data.data);
            if(albumUri!=null){
                Glide.with(requireContext().applicationContext).load(albumUri)
                    .into(binding.itemFoodDetailIv)
                binding.itemFoodDetailIv.scaleType = ImageView.ScaleType.FIT_XY
            }
        }
        Log.d("onActivityResult:bitmap", bitmap.toString())
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        //다이아로그에 여러개 항목을 보여주게 가능한 함수
        builder.setItems(com.example.alne.R.array.alert_photo,
            DialogInterface.OnClickListener { dialogInterface, i ->
                // 각각을 클릭했을때 이벤트를 작성
                // i : 누른게 무엇인지 알려줌
                if (i == 0) { // 첫번째 항목을 눌렀다면,
                    dispatchTakePictureIntent()
                } else if (i == 1) {
                    chooserImage()
                }
            })
        builder.show()
    }
}


// 재료 수정하기(편집)
interface MyCustomDialogDetailInterface {
    //fun onSubmitBtnDetailClicked(food: FridgeIngredient, photoFile: File?)
    fun onSubmitBtnDetailClicked(food: FridgeIngredient)
}