package com.example.alne.view.MyPage

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.alne.GlobalApplication
import com.example.alne.databinding.LogoutDialogBinding
import com.example.alne.utils.REPONSE_STATUS
import com.example.alne.view.Splash.StartActivity
import com.example.alne.viewmodel.MyPageViewModel
import com.kakao.sdk.user.UserApiClient

class LogoutCustomDialog: DialogFragment() {

    lateinit var binding: LogoutDialogBinding
    lateinit var viewModel: MyPageViewModel

    companion object {
        const val TAG = "LogoutCustomDialog"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LogoutDialogBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity()).get(MyPageViewModel::class.java)

        binding.dialogQuitBt.setOnClickListener {
            dismiss()
        }

        binding.dialogSubmitBt.setOnClickListener {
            logOut()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)
        val deviceWidth = size.x
        val deviceHeght = size.y
        val params = dialog?.window?.attributes
        params?.width = (deviceWidth*0.95).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun logOut(){
        viewModel.logout(GlobalApplication.prefManager.getUserToken(),
            completion = { responseState ->
                when(responseState){
                    REPONSE_STATUS.OKAY -> {
                        Toast.makeText(requireContext(), "로그아웃에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(requireContext(), StartActivity::class.java))
                    }

                    REPONSE_STATUS.FAIL -> {
                        Toast.makeText(requireContext(), "로그아웃에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }

                    REPONSE_STATUS.NETWORK_ERROR -> {
                        Toast.makeText(requireContext(), "네트워크 오류입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}