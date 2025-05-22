package com.tanimul.android_template_kotlin.features.users.presentation

import android.app.Application
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.paging.cachedIn
import com.tanimul.android_template_kotlin.features.users.domain.model.User
import com.tanimul.android_template_kotlin.features.users.domain.usecase.UsersUseCase
import com.tanimul.android_template_kotlin.base.BaseAndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class UsersViewModel @Inject constructor(
    application: Application,
    private val usersUseCase: UsersUseCase,
) : BaseAndroidViewModel(application) {

    private val _uiAction = Channel<UsersUiActions>(Channel.CONFLATED)
    val uiAction = _uiAction.receiveAsFlow()

    private val _users = Channel<Unit>(Channel.CONFLATED)
    val users = _users.receiveAsFlow().flatMapLatest {
        usersUseCase().cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _isShimmerLoading = MutableStateFlow<Boolean>(true)
    val isShimmerLoading = _isShimmerLoading.asStateFlow()

    private val _isProgressLoading = MutableStateFlow<Boolean>(true)
    val isProgressLoading = _isProgressLoading.asStateFlow()

    private val _emptyUsers = MutableStateFlow<Boolean>(false)
    val emptyUsers = _emptyUsers.asStateFlow()

    private val _currentDateTime = MutableStateFlow(Date())
    val currentDateTime = _currentDateTime.asStateFlow()

    init {
        viewModelScope.launch {
            fetchUsers()
        }
    }

    private fun setProgressLoading(value: Boolean) {
        _isProgressLoading.value = value
    }

    private fun setShimmerLoading(value: Boolean) {
        _isShimmerLoading.value = value
    }

    private fun setEmptyUsers(value: Boolean) {
        _emptyUsers.value = value
    }

    private fun fetchUsers() {
        _users.trySend(Unit)
    }

    fun uiAction(action: UsersUiActions) {
        _uiAction.trySend(action)
    }

    fun selectedUser(user: User) {
        uiAction(UsersUiActions.SelectedUser(user))
    }

    fun observeLoadState(adapter: PagingDataAdapter<*, *>) {
        viewModelScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                // Update loading states based on load state
                setProgressLoading(loadState.append is LoadState.Loading)
                setShimmerLoading(loadState.refresh is LoadState.Loading)

                // Check if the pagination has ended
                if (loadState.source.append.endOfPaginationReached) {
                    setEmptyUsers(adapter.itemCount == 0)
                }
            }
        }
    }
}

sealed interface UsersUiActions {
    data class SelectedUser(val user: User) : UsersUiActions
}
