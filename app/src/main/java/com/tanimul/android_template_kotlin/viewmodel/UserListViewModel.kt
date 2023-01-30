package com.tanimul.android_template_kotlin.viewmodel


import android.app.Application
import androidx.lifecycle.*
import com.tanimul.android_template_kotlin.data.database.TemplateDatabase
import com.tanimul.android_template_kotlin.data.models.response.UserModel
import com.tanimul.android_template_kotlin.data.repository.UserListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class UserListViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG: String = "UserListViewModel"
    private val repository: UserListRepository
    val showAllUser= MutableStateFlow<List<UserModel>>(emptyList())
    init {
        val userDao = TemplateDatabase.getDatabase(application).userDao()
        repository = UserListRepository(userDao)

        viewModelScope.launch{
            repository.showAllUser.collect{
                showAllUser.value=it
            }
        }
    }

    fun getUserList(since: Int, per_page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserList(since, per_page)
        }
    }
}