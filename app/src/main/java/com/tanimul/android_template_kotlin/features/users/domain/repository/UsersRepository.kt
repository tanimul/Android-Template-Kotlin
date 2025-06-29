package com.tanimul.android_template_kotlin.features.users.domain.repository

import androidx.paging.PagingData
import com.tanimul.android_template_kotlin.features.users.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun fetchUsers(): Flow<PagingData<User>>
}
