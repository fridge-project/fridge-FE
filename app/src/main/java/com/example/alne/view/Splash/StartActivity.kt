package com.example.alne.view.Splash
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.alne.databinding.ActivityStartBinding
import com.example.alne.view.Login.LoginActivity
import com.example.alne.view.SignUp.SignUpActivity

class StartActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startLoginBt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.loginSignUpBt.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}