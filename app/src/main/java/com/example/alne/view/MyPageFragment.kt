package com.example.alne.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.alne.databinding.FragmentMyPageBinding
//import com.kakao.sdk.user.UserApiClient

class MyPageFragment : Fragment() {
    lateinit var binding: FragmentMyPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPageBinding.inflate(layoutInflater)

        binding.signUp.setOnClickListener {
            startActivity(Intent(requireContext(), SignUpActivity::class.java))
        }

        binding.login.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        binding.logOut.setOnClickListener {
//            UserApiClient.instance.logout { error ->
//                if (error != null) {
//                    Log.e("logout", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
//                    deleteAutoLogin()
//                    startActivity(Intent(requireContext(), StartActivity::class.java))
//                }
//                else {
//                    startActivity(Intent(requireContext(), StartActivity::class.java))
//                    Log.i("logout", "로그아웃 성공. SDK에서 토큰 삭제됨")
//                }
//            }
        }
        return binding.root
    }

    fun deleteAutoLogin(){
        val sharedPreferences1 = requireContext().getSharedPreferences("login_setting", AppCompatActivity.MODE_PRIVATE)
        val sharedPreferences = requireContext().getSharedPreferences("user_info", AppCompatActivity.MODE_PRIVATE)
        val sharedPreferences3 = requireContext().getSharedPreferences("user_token", AppCompatActivity.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        var edit1 = sharedPreferences1.edit()
        var edit2 = sharedPreferences3.edit()
        edit.clear()
        edit1.commit()
        edit1.commit()
        edit.commit()
        edit2.clear()
        edit2.commit()
    }
}