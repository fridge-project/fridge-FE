package com.example.alne.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.alne.databinding.ActivitySignUpBinding
import com.example.flo.Network.AuthApi
import com.example.flo.Network.AuthResponse
import com.example.flo.Network.getRetrofit
import com.example.flo.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var retrofit: Retrofit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setContentView(binding.root)

        binding.signUpLoginBt.setOnClickListener {
            if(binding.signUpNameEt.text?.isNotEmpty()!! || binding.signUpEmailEt.text?.isNotEmpty()!! || binding.signUpPasswordEt.text?.isNotEmpty()!! || binding.signUpPasswordVerifyEt.text?.isNotEmpty()!!){
                if(binding.signUpPasswordEt.text.toString() == binding.signUpPasswordVerifyEt.text.toString()){
                    signUp()
                }else{
                    Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "빈칸을 채워주세요.", Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun signUp(){
        retrofit.create(AuthApi::class.java).signUp(User( binding.signUpEmailEt.text.toString(), binding.signUpPasswordEt.text.toString(), binding.signUpEmailEt.text.toString()))
            .enqueue(object: Callback<AuthResponse>{
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>,
                ) {
                    if(response.isSuccessful){
                        var res = response.body()
                        when(res?.status){
                            200 -> {
                                Toast.makeText(this@SignUpActivity, "회원가입했습니다.",Toast.LENGTH_LONG).show()
                                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                            }
                            401 -> {
                                Toast.makeText(this@SignUpActivity, "동일한 아이디가 존재합니다.", Toast.LENGTH_LONG).show()
                            }
                        }
                        Log.d("signUp_success", res.toString())
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.d("signUp_failure", t.message.toString())
                }

            })
    }
}