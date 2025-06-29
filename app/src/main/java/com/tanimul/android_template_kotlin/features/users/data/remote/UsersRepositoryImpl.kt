package com.tanimul.android_template_kotlin.features.users.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tanimul.android_template_kotlin.db.dao.UserDao
import com.tanimul.android_template_kotlin.di.IoDispatcher
import com.tanimul.android_template_kotlin.features.users.domain.model.User
import com.tanimul.android_template_kotlin.features.users.domain.repository.UsersRepository
import com.tanimul.android_template_kotlin.network.ApiInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UsersRepositoryImpl @Inject constructor(
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    val apiInterface: ApiInterface,
    val userDao: UserDao
) : UsersRepository {

    override suspend fun fetchUsers(): Flow<PagingData<User>> {
        return Pager(config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                UsersDataSource(
                    apiInterface,
                    userDao
                )
            }).flow.flowOn(dispatcher)
    }

}