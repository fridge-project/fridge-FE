package com.example.alne.view.MyPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.alne.databinding.FragmentMyPageBinding
import com.example.alne.data.model.Profile
import com.example.alne.presentation.view.MyPage.LikeListActivity
import com.example.alne.view.Login.LoginActivity
import com.example.alne.view.SignUp.SignUpActivity
import com.example.alne.view.Splash.StartActivity
import com.example.alne.viewmodel.MyPageViewModel
import com.kakao.sdk.user.UserApiClient

class MyPageFragment : Fragment() {
    lateinit var binding: FragmentMyPageBinding
    lateinit var viewModel: MyPageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPageBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(MyPageViewModel::class.java)

//        viewModel.userProfileLiveData.observe(viewLifecycleOwner, Observer { state ->
//            if(state != null){
//                binding.logout.setOnClickListener {
//                    LogoutCustomDialog().show(requireActivity().supportFragmentManager, LogoutCustomDialog.TAG)
//                }
//                setProfile(state)
//            }
//        })

        binding.logout.setOnClickListener {
            LogoutCustomDialog().show(requireActivity().supportFragmentManager, LogoutCustomDialog.TAG)
        }

        binding.myPageProfileSettingCv.setOnClickListener {
            startActivity(Intent(context, UserProfileActivity::class.java))
        }

        binding.mypageFavoriteLinear.setOnClickListener {
            startActivity(Intent(requireContext(), FavoriteActivity::class.java))
        }

        binding.myPageLikeListIv.setOnClickListener {
            startActivity(Intent(requireContext(), LikeListActivity::class.java))
        }


        return binding.root
    }

    private fun setProfile(profile: Profile) {
        Glide.with(requireContext()).load(profile.image).into(binding.myPageProfileIv)
        binding.myPageProfileIv.scaleType = ScaleType.FIT_XY
        binding.myPageProfileIv.setPadding(0,0,0,0)
    }
}