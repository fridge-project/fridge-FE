package com.example.alne.view.SignUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alne.databinding.ActivitySignUpBinding
import com.example.alne.view.Login.LoginActivity
import com.example.alne.viewmodel.SignUpViewModel
import com.example.alne.data.model.User
import com.example.alne.utils.RESPONSE_STATUS

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding.signUpLoginBt.setOnClickListener {
            checkSignUp()
        }
    }

    private fun checkSignUp(){
        if(binding.signUpNameEt.text?.isNotEmpty()!! || binding.signUpEmailEt.text?.isNotEmpty()!! || binding.signUpPasswordEt.text?.isNotEmpty()!! || binding.signUpPasswordVerifyEt.text?.isNotEmpty()!!){
            if(binding.signUpPasswordEt.text.toString() == binding.signUpPasswordVerifyEt.text.toString()){
                viewModel.signUp(
                    User(binding.signUpEmailEt.text.toString(), binding.signUpNameEt.text.toString() ,binding.signUpPasswordEt.text.toString()),
                    completion = { responseState, responseMessage ->
                        when(responseState){
                            RESPONSE_STATUS.OKAY -> {
                                Toast.makeText(this@SignUpActivity, "회원가입했습니다.",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                            }
                            RESPONSE_STATUS.FAIL -> {
                                Toast.makeText(this@SignUpActivity, "동일한 아이디가 존재합니다.", Toast.LENGTH_SHORT).show()
                            }
                            RESPONSE_STATUS.NETWORK_ERROR -> {
                                Toast.makeText(this@SignUpActivity, "네트워크 오류입니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
            }else{
                Toast.makeText(this, "비밀번호가 다릅니다.", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this, "빈칸을 채워주세요.", Toast.LENGTH_LONG).show()
        }
    }


}