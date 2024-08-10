package com.example.alne

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.alne.databinding.ActivityMainBinding
import com.example.alne.view.Fridge.FridgeFragment
import com.example.alne.view.Home.HomeFragment
import com.example.alne.view.MyPage.MyPageFragment
import com.example.alne.view.Recipe.RecipeFragment
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()

        Log.d(TAG, "keyhash : ${Utility.getKeyHash(this)}")

    }

    fun initBottomNavigation(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, HomeFragment())
            .commitAllowingStateLoss()

            binding.bottomNavigationView.setOnItemSelectedListener{ item ->
                when(item.itemId){
                    R.id.homeFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, HomeFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }

                    R.id.fridgeFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, FridgeFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }

                    R.id.recipeFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, RecipeFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }

                    R.id.myPageFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_container, MyPageFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }

                }
                false
            }
    }
}