package com.tanimul.android_template_kotlin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.tanimul.android_template_kotlin.data.database.UserDao
import com.tanimul.android_template_kotlin.data.models.response.UserModel
import com.tanimul.android_template_kotlin.data.models.response.UserResponse
import com.tanimul.android_template_kotlin.network.ApiClient
import com.tanimul.android_template_kotlin.network.ApiInterface
import retrofit2.Response
import kotlin.math.sin

class UserListRepository(private val userDao: UserDao) {
    private val TAG: String = "UserListRepository"
    private val apiInterface: ApiInterface = ApiClient.getClient()
    var showAllUser: LiveData<List<UserModel>> = userDao.showUserList()
    suspend fun getUserList(since: Int,per_page: Int): Response<List<UserResponse>> {
        return apiInterface.getUsers(since,per_page)
    }
    suspend fun addUser(userModel: UserModel) {
        userDao.addUser(userModel)
        Log.d(TAG, "addSingleNote: ")
    }

}