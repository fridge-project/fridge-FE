package com.example.alne

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.alne.data.model.Jwt
import com.example.alne.data.model.Token
import com.google.gson.Gson

class SharedPrefManager(val context: Context) {

    private val SHARED_SHEARCH_HISTORY = "shared_search_history"
    private val KEY_SEARCH_HISTORY = "key_search_history"

    private val USER_INFO = "user_info"
    private val KEY_USER_TOKEN = "accessToken"

    private val LOGIN_SETTING = "login_setting"


//    // 검색 목록을 저장
//    fun storeSearchHistoryList(searchHistoryList: MutableList<SearchData>){
//        Log.d("storeSearchHistoryList", searchHistoryList.toString())
//        // 매개변수로 들어온 배열을 -> 문자열로 변환
//        val searchHistoryListString : String = Gson().toJson(searchHistoryList)
//        val shared = context.getSharedPreferences(SHARED_SHEARCH_HISTORY, Context.MODE_PRIVATE)
//        val editor = shared.edit()
//        editor.putString(KEY_SEARCH_HISTORY, searchHistoryListString)
//        editor.apply()
//
//    }
//
//    //검색 목록 가져오기
//    fun getSearchHistoryList(): MutableList<SearchData>{
//        val shared = context.getSharedPreferences(SHARED_SHEARCH_HISTORY, Context.MODE_PRIVATE)
//        val storedSearchHistoryListString = shared.getString(KEY_SEARCH_HISTORY, "")!!
//        Log.d("getSearchHistoryList", storedSearchHistoryListString.toString())
//        var storedSearchHistoryList = ArrayList<SearchData>()
//        if(storedSearchHistoryListString.isNotEmpty()){
//            storedSearchHistoryList = Gson().
//                    fromJson(storedSearchHistoryListString, Array<SearchData>::class.java).
//                    toMutableList() as ArrayList<SearchData>
//        }
//        return storedSearchHistoryList
//    }
//
//    // 검색 목록 지우기
//    fun clearSearchHistoryList(){
//        val shared = context.getSharedPreferences(SHARED_SHEARCH_HISTORY, Context.MODE_PRIVATE)
//        val editor = shared.edit()
//        editor.clear()
//        editor.apply()
//    }

    //토큰 저장
    fun saveJwt(accessToken: String, refreshToken: String){
        val sharedPreferences = context?.getSharedPreferences(USER_INFO, AppCompatActivity.MODE_PRIVATE)!!
        val edit = sharedPreferences.edit()
        edit.putString(KEY_USER_TOKEN, Gson().toJson(Token(accessToken, refreshToken)))
        edit.commit()
    }

    //토큰 불러오기
    fun getUserToken(): Token {
        val sharedPreferences = context?.getSharedPreferences(USER_INFO, AppCompatActivity.MODE_PRIVATE)
        val userJwt = Gson().fromJson(sharedPreferences?.getString(KEY_USER_TOKEN,null), Token::class.java)
        Log.d("token", userJwt.toString())
        return userJwt
    }

    // 자동로그인, 아이디기록 여부 저장
    fun saveAutoLogin(autoLogin: Boolean){
        val sharedPreferences = context?.getSharedPreferences(LOGIN_SETTING, AppCompatActivity.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()!!
        edit.putBoolean("autoLogin", autoLogin)
        edit.commit()
    }

    fun saveIdLogin(saveId: String?){
        val sharedPreferences = context?.getSharedPreferences(LOGIN_SETTING, AppCompatActivity.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()!!
        edit.putString("saveId", saveId)
        edit.commit()
    }

    fun getSaveIdLogin(): String?{
        val sharedPreferences = context?.getSharedPreferences(LOGIN_SETTING, AppCompatActivity.MODE_PRIVATE)!!
        return sharedPreferences.getString("saveId", null)
    }

    fun getAutoLogin(): Boolean {
        val sharedPreferences = context?.getSharedPreferences(LOGIN_SETTING, AppCompatActivity.MODE_PRIVATE)!!
        return sharedPreferences.getBoolean("autoLogin", false)
    }

    // 정보 삭제
    fun deleteUserData(){
        val sharedPreferences1 = context?.getSharedPreferences(LOGIN_SETTING, AppCompatActivity.MODE_PRIVATE)!!
        val sharedPreferences = context?.getSharedPreferences(USER_INFO, AppCompatActivity.MODE_PRIVATE)!!
        val edit = sharedPreferences.edit()
        var edit1 = sharedPreferences1.edit()
        edit.clear()
        edit1.clear()
        edit1.commit()
        edit.commit()
    }


}