package com.example.alne.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.alne.MainActivity
import com.example.alne.R
import com.example.alne.databinding.ActivityLoginBinding
import com.example.alne.model.Token
import com.example.flo.Network.AuthApi
import com.example.flo.Network.AuthResponse
import com.example.flo.Network.getRetrofit
import com.example.flo.model.User
//import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var retrofit: Retrofit
    private var saveId: Boolean = false
    private var autoLogin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        retrofit = getRetrofit()
        setContentView(binding.root)

        binding.loginSignUpBt.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginAutoLoginIb.setOnClickListener {
            if(autoLogin){
                binding.loginAutoLoginIb.setImageResource(R.drawable.circle)
                autoLogin = !autoLogin
            }else{
                binding.loginAutoLoginIb.setImageResource(R.drawable.checked)
                autoLogin = !autoLogin
            }
        }

        binding.loginSaveIDIb.setOnClickListener {
            if(saveId){
                binding.loginSaveIDIb.setImageResource(R.drawable.circle)
                saveId = !saveId
            }else{
                binding.loginSaveIDIb.setImageResource(R.drawable.checked)
                saveId = !saveId
            }
        }

        binding.loginKakaoBt.setOnClickListener {
            kakaoLogin()
        }

        binding.loginGoogleBt.setOnClickListener {
//            UserApiClient.instance.me { user, error ->
//                Log.d("kakao_nickname", user?.kakaoAccount?.name.toString() )
//                Log.d("kakao_age", user?.kakaoAccount?.profile?.nickname.toString() )
//                Log.d("kakao_email", user?.kakaoAccount?.profile?.profileImageUrl.toString() )
//            }
        }

        binding.loginLoginBt.setOnClickListener{
            retrofit.create(AuthApi::class.java).login(User(binding.loginEmailEt.text.toString(),  binding.loginPasswordEt.text.toString(), "")).enqueue(object: Callback<AuthResponse>{
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>,
                ) {
                    if(response.isSuccessful){
                        var res = response.body()
                        when(res?.status) {
                            200 -> {
                                Toast.makeText(this@LoginActivity, "로그인", Toast.LENGTH_LONG).show()
                                val sharedPreferences = getSharedPreferences("user_token", MODE_PRIVATE)
                                val edit = sharedPreferences.edit()
                                Log.d("userId_reponse", res.data.toString())
                                edit.putInt("userId", res.data)
                                edit.commit()

//                                if(autoLogin){
//                                    autoLogin()
//                                }
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            }
                            401 -> {
                                if(res.data == 10){
                                    Toast.makeText(this@LoginActivity, "비밀번호가 틀렸습니다", Toast.LENGTH_LONG).show()
                                }
                            }

                            200 -> {
                                if(res.data == 0) {
                                    Toast.makeText(this@LoginActivity, "아이디가 존재하지 않습니다", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                        Log.d("login_success", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.d("login_failure", t.toString())
                }
            })
        }
    }

    private fun kakaoLogin() {
//        UserApiClient.instance.loginWithKakaoAccount(this) { token, error ->
//            if (error != null) {
//                Log.e("kakao", "로그인 실패", error)
//            }
//            else if (token != null) {
//                Log.i("kakao", "로그인 성공 ${token.accessToken}")
//
//                retrofit.create(AuthApi::class.java).accessToken(Token(token.accessToken)).enqueue(object: Callback<AuthResponse>{
//                    override fun onResponse(
//                        call: Call<AuthResponse>,
//                        response: Response<AuthResponse>,
//                    ) {
//                        Log.d("kakaoLogin", "SUCCESS")
//                        Log.d("kakaoLogin", response.body().toString())
//                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//                    }
//
//                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                        Log.d("kakaoLogin", t.message.toString())
//                    }
//
//                })
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("login_setting", MODE_PRIVATE)
        saveId = sharedPreferences.getBoolean("saveId", false)
        autoLogin = sharedPreferences.getBoolean("autoLogin", false)
        Log.d("save_id", saveId.toString())
        Log.d("autoLogin", autoLogin.toString())
        if(autoLogin){
            binding.loginAutoLoginIb.setImageResource(R.drawable.checked)
        }else{
            binding.loginAutoLoginIb.setImageResource(R.drawable.circle)
        }
        if(saveId){
            binding.loginSaveIDIb.setImageResource(R.drawable.checked)
        }else{
            binding.loginSaveIDIb.setImageResource(R.drawable.circle)
        }
    }

    override fun onPause() {
        super.onPause()
        val sharedPreferences = getSharedPreferences("login_setting", MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putBoolean("saveId", saveId)
        edit.putBoolean("autoLogin", autoLogin)
        edit.commit()
    }

    fun autoLogin(){
        val sharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.putString("ID", binding.loginEmailEt.text.toString())
        edit.putString("PW", binding.loginPasswordEt.text.toString())
        edit.commit()
    }
}