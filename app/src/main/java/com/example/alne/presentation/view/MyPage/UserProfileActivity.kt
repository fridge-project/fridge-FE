package com.example.alne.view.MyPage

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.alne.GlobalApplication
import com.example.alne.R
import com.example.alne.databinding.ActivityUserProfileBinding
import com.example.alne.data.model.Profile
import com.example.alne.viewmodel.MyPageViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class UserProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserProfileBinding
    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_CHOOSER = 2
    var photoFile: File? = null
    lateinit var viewModel: MyPageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MyPageViewModel::class.java)

        viewModel.userProfileLiveData.observe(this, Observer {profile ->
            if(profile != null){
                setImage(profile)
            }
        })
        binding.userProfileTb.setNavigationOnClickListener {
            onBackPressed()
        }

        //계정탈퇴
        binding.userProfileQuitLinear.setOnClickListener{
            DeleteAccountCustomDialog().show(supportFragmentManager, LogoutCustomDialog.TAG)
        }

        with(binding){
            userProfileImageCv.setOnClickListener{
                showDialog()
            }
            userProfileImagePlusCv.setOnClickListener{
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
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

    private fun dispatchTakePictureIntent() {

        val permissionCheck = ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        )

        // 허용이 안되어있으면,
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            // 다시한번 물어 봐라
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(Manifest.permission.CAMERA),
                1000
            )
            Toast.makeText(
                this, "카메라 권한 필요합니다.",
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
                this,
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

    private fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap):Uri? {
        var values= ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME,filename)
        values.put(MediaStore.Images.Media.MIME_TYPE,mimeType)
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
            values.put(MediaStore.Images.Media.IS_PENDING,1)
        }
        val uri= contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
        try {
            if(uri!=null){
                val descriptor= contentResolver.openFileDescriptor(uri,"w")
                if(descriptor!=null){
                    val fos= FileOutputStream(descriptor.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos)
                    fos.close()
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q){
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING,0)
                        contentResolver.update(uri,values,null,null)
                    }
                }
            }
        } catch (e: java.lang.Exception){
            Log.e("File","error=")
        }
        return uri
    }

    private fun getPhotoFile(fileName: String): File? {
        val storageDirectory: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return try {
            File.createTempFile(fileName, ".jpg", storageDirectory)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun chooserImage(){
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.type = "image/*"

        startActivityForResult(
            intent,
            REQUEST_IMAGE_CHOOSER
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var bitmap: Bitmap? = null
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            if(Build.VERSION.SDK_INT<28) {
                bitmap= MediaStore.Images.Media
                    .getBitmap(contentResolver, Uri.fromFile(photoFile))
                setImage(bitmap)
            }else{
                val decode= ImageDecoder.createSource(contentResolver,
                    Uri.fromFile(photoFile!!.absoluteFile))
                bitmap= ImageDecoder.decodeBitmap(decode)


                if(bitmap != null){
                    setImage(bitmap)
                    saveImageFile(photoFile!!.name,getExtension(photoFile!!.name),bitmap)
                }
            }
        }else if( requestCode == REQUEST_IMAGE_CHOOSER && resultCode== RESULT_OK && data?.data != null){
            var albumUri: Uri = data.data!!
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data);
//            val cursor: Cursor = contentResolver.query(albumUri, null, null, null, null)!!
//            cursor.moveToNext();
//            val path = cursor.getString(cursor.getColumnIndex("_data"))
//            Log.d("path" ,"onActivityResult: {${path.toString()} ")
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = managedQuery(albumUri, proj, null,null,null)
            var index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToNext();
            photoFile = File(cursor?.getString(index!!))

            if(albumUri!=null){
                setImage(bitmap)
            }
        }
        Log.d("onActivityResult:bitmap", bitmap.toString())
    }

    private fun setImage(bitmap: Bitmap){
        binding.userProfileImagePlusCv.visibility = View.GONE
        binding.userProfileImage.setImageBitmap(bitmap)
        binding.userProfileImage.scaleType = ImageView.ScaleType.FIT_XY
        binding.userProfileImage.setPadding(0,0,0,0)
        setSubmitButton()
    }

    private fun setImage(profile: Profile){
        binding.userProfileImagePlusCv.visibility = View.GONE
        Glide.with(this).load(profile.image).into(binding.userProfileImage)
        binding.userProfileImage.scaleType = ImageView.ScaleType.FIT_XY
        binding.userProfileImage.setPadding(0,0,0,0)
    }

    private fun setSubmitButton() {
        binding.userProfileBt.setBackgroundColor(resources.getColor(R.color.blue))
        binding.userProfileBt.setOnClickListener {
//            viewModel.saveUserProfileImage(photoFile!!, GlobalApplication.prefManager.getUserToken()?.userId!!)
            finish()
        }
    }
}