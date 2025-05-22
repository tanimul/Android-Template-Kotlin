package com.tanimul.android_template_kotlin.features.users.domain.usecase

import androidx.paging.PagingData
import com.tanimul.android_template_kotlin.di.IoDispatcher
import com.tanimul.android_template_kotlin.features.users.domain.model.User
import com.tanimul.android_template_kotlin.features.users.domain.repository.UsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UsersUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: UsersRepository
) {
    suspend operator fun invoke(): Flow<PagingData<User>> =
        repository.fetchUsers()

}