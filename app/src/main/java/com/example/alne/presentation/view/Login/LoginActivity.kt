package com.example.alne.view.Login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.alne.GlobalApplication
import com.example.alne.MainActivity
import com.example.alne.R
import com.example.alne.databinding.ActivityLoginBinding
import com.example.alne.data.model.User
import com.example.alne.utils.RESPONSE_STATUS
import com.example.alne.utils.ToolBox
import com.example.alne.view.SignUp.SignUpActivity
import com.example.alne.viewmodel.LoginViewModel


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    //아이디 저장
    private var saveId: String? = null

    //자동 로그인
    private var autoLogin: Boolean = false
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        //회원가입 이동
        binding.loginSignUpBt.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // 자동 로그인 설정 시
        binding.loginAutoLoginIb.setOnClickListener {
            if (autoLogin) {
                binding.loginAutoLoginIb.setImageResource(R.drawable.circle)
            } else {
                binding.loginAutoLoginIb.setImageResource(R.drawable.checked)
            }
            autoLogin = !autoLogin
        }

        // 아이디 저장 설정 시
        binding.loginSaveIDIb.setOnClickListener {
            if (saveId != null) {
                saveId = null
                binding.loginSaveIDIb.setImageResource(R.drawable.circle)
            } else {
                saveId = binding.loginEmailEt.text.toString()
                binding.loginSaveIDIb.setImageResource(R.drawable.checked)
            }
        }

        // 로그인 버튼 클릭 시
        binding.loginLoginBt.setOnClickListener {
            downKeyBoard()
            ToolBox.showProgressingView(this)
            viewModel.login(
                User(
                binding.loginEmailEt.text.toString(),
                null,
                binding.loginPasswordEt.text.toString()
            ),
                completion = { responseState ->
                    when (responseState) {
                        RESPONSE_STATUS.OKAY -> {
                            Toast.makeText(this@LoginActivity, "로그인 성공했습니다.", Toast.LENGTH_SHORT)
                                .show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                // 설정 저장
                                GlobalApplication.prefManager.saveAutoLogin(autoLogin)
                                GlobalApplication.prefManager.saveIdLogin(binding.loginEmailEt.text.toString())
                                ToolBox.hideProgressingView(this)
                                finishAffinity()
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            }, 1000)
                        }

                        RESPONSE_STATUS.FAIL -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "이메일 또는 패스워드 오류입니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        RESPONSE_STATUS.NETWORK_ERROR -> {
                            Toast.makeText(this@LoginActivity, "네트워크 오류입니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
        }

        // 비밀번호 찾기 클릭 시
        binding.loginFindPasswordTv.setOnClickListener {

        }

    }

    override fun onResume() {
        super.onResume()
        saveId = GlobalApplication.prefManager.getSaveIdLogin()
        autoLogin = GlobalApplication.prefManager.getAutoLogin()
        if (autoLogin) {
            binding.loginAutoLoginIb.setImageResource(R.drawable.checked)
        } else {
            binding.loginAutoLoginIb.setImageResource(R.drawable.circle)
        }
        if (saveId != null) {
            binding.loginEmailEt.setText(saveId)
            binding.loginSaveIDIb.setImageResource(R.drawable.checked)
        } else {
            binding.loginEmailEt.setText("")
            binding.loginSaveIDIb.setImageResource(R.drawable.circle)
        }
    }

    //키보드 조정
    private fun downKeyBoard(){
        val manager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}