package com.example.alne.view.MyPage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView.ScaleType
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.alne.databinding.FragmentMyPageBinding
import com.example.alne.data.model.Profile
import com.example.alne.presentation.view.MyPage.CommentActivity
import com.example.alne.presentation.view.MyPage.LikeListActivity
import com.example.alne.viewmodel.MyPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment : Fragment() {
    lateinit var binding: FragmentMyPageBinding
    private val viewModel: MyPageViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPageBinding.inflate(layoutInflater)

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

        binding.myPageLikeListLinear.setOnClickListener {
            startActivity(Intent(requireContext(), LikeListActivity::class.java))
        }

        binding.myPageCommentLinear.setOnClickListener{
            startActivity(Intent(requireContext(), CommentActivity::class.java))
        }

        return binding.root
    }

    private fun setProfile(profile: Profile) {
        Glide.with(requireContext()).load(profile.image).into(binding.myPageProfileIv)
        binding.myPageProfileIv.scaleType = ScaleType.FIT_XY
        binding.myPageProfileIv.setPadding(0,0,0,0)
    }
}