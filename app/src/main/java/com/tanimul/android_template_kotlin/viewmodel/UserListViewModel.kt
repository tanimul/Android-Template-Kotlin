package com.tanimul.android_template_kotlin.viewmodel


import androidx.lifecycle.*
import com.tanimul.android_template_kotlin.data.models.response.UserModel
import com.tanimul.android_template_kotlin.data.repository.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private  val  repository: UserListRepository) : ViewModel() {
    private val TAG: String = "UserListViewModel"
    private var _userStateFlow: MutableStateFlow<List<UserModel>> = MutableStateFlow(emptyList())
    val showAllUser: StateFlow<List<UserModel>> = _userStateFlow
    init {

        viewModelScope.launch{
            repository.showAllUser.collect{
                _userStateFlow.value=it
            }
        }
    }

    fun getUserList(since: Int, per_page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserList(since, per_page)
        }
    }
}