package com.tanimul.android_template_kotlin.features.users.data.remote

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tanimul.android_template_kotlin.db.dao.UserDao
import com.tanimul.android_template_kotlin.features.users.domain.model.User
import com.tanimul.android_template_kotlin.features.users.domain.model.UserResponse
import com.tanimul.android_template_kotlin.network.ApiInterface
import retrofit2.Response
import timber.log.Timber

class UsersDataSource(
    private val apiInterface: ApiInterface,
    private val userDao: UserDao
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val currentLoadingPageKey = params.key ?: 1

            val response: Response<UserResponse> =
                apiInterface.fetchUsers(currentLoadingPageKey)
            Timber.d("load--> $response")
            val data = response.body()?.users
            if (data != null) {
                userDao.addUsers(data)

                LoadResult.Page(
                    data = data.toList(),
                    prevKey = if (currentLoadingPageKey > 1) currentLoadingPageKey - 1 else null,
                    nextKey = if (currentLoadingPageKey < (response.body()?.total ?: 1)
                    ) currentLoadingPageKey + 1 else null
                )
            } else {
                LoadResult.Error(Exception("Data is null or empty"))
            }


        } catch (e: Exception) {
            Timber.d("Exception--> $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}