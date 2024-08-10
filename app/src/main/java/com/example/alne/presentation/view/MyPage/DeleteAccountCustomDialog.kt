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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.alne.GlobalApplication
import com.example.alne.databinding.LogoutDialogBinding
import com.example.alne.utils.RESPONSE_STATUS
import com.example.alne.view.Splash.StartActivity
import com.example.alne.viewmodel.MyPageViewModel
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAccountCustomDialog: DialogFragment() {

    lateinit var binding: LogoutDialogBinding
    private val viewModel: MyPageViewModel by activityViewModels()

    companion object {
        const val TAG = "DeleteAccountCustomDialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LogoutDialogBinding.inflate(layoutInflater)

        binding.textView.text = "탈퇴하시겠습니까?"

        binding.dialogQuitBt.setOnClickListener {
            dismiss()
        }

        binding.dialogSubmitBt.setOnClickListener {
            deleteAccount()
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

    private fun deleteAccount(){
        viewModel.deleteAccount(
            completion = { responseState ->
                when(responseState){
                    RESPONSE_STATUS.OKAY -> {
                        Toast.makeText(requireContext(), "탈퇴하셨습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(requireContext(), StartActivity::class.java))
                    }

                    RESPONSE_STATUS.FAIL -> {
                        Toast.makeText(requireContext(), "탈퇴에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }

                    RESPONSE_STATUS.NETWORK_ERROR -> {
                        Toast.makeText(requireContext(), "네트워크 오류입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}