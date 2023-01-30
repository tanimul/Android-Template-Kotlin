package com.tanimul.android_template_kotlin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.tanimul.android_template_kotlin.data.database.UserDao
import com.tanimul.android_template_kotlin.data.models.response.UserModel
import com.tanimul.android_template_kotlin.data.models.response.UserResponse
import com.tanimul.android_template_kotlin.network.ApiClient
import com.tanimul.android_template_kotlin.network.ApiInterface
import timber.log.Timber

class UserListRepository(private val userDao: UserDao) {
    private val TAG: String = "UserListRepository"
    private val apiInterface: ApiInterface = ApiClient.getClient()
    var showAllUser: LiveData<List<UserModel>> = userDao.showUserList()
    suspend fun getUserList(since: Int, per_page: Int) {

        apiInterface.getUsers(since, per_page).body()?.let { addUserList(it) }

    }


    private fun addUserList(userList: List<UserResponse>) {
        Timber.d( "addUserList: ${userList.size}")

        userDao.addUsers(userList.map { data ->
            UserModel(
                login = data.login, node_id = data.nodeId, avatar_url = data.avatarUrl
            )
        })

    }
}