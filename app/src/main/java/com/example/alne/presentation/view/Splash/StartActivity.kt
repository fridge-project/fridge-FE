package com.example.alne.view.Splash
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.alne.databinding.ActivityStartBinding
import com.example.alne.presentation.viewmodel.StartViewModel
import com.example.alne.view.Login.LoginActivity
import com.example.alne.view.SignUp.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartBinding
    private val viewModel: StartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.saveRecipeData()
        viewModel.saveIngredientData()

        binding.startLoginBt.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.loginSignUpBt.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}