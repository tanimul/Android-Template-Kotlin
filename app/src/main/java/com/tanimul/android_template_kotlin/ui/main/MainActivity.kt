package com.tanimul.android_template_kotlin.ui.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tanimul.android_template_kotlin.R
import com.tanimul.android_template_kotlin.adapter.UserListAdapter
import com.tanimul.android_template_kotlin.data.models.response.UserModel
import com.tanimul.android_template_kotlin.databinding.ActivityMainBinding
import com.tanimul.android_template_kotlin.ui.base.AppBaseActivity
import com.tanimul.android_template_kotlin.utils.extentions.JsonFileCode
import com.tanimul.android_template_kotlin.utils.extentions.isNetworkAvailable
import com.tanimul.android_template_kotlin.utils.extentions.openLottieDialog
import com.tanimul.android_template_kotlin.utils.extentions.toast
import com.tanimul.android_template_kotlin.viewmodel.UserListViewModel
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppBaseActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var userList: ArrayList<UserModel>
    private val usersViewModel: UserListViewModel by viewModels()
    lateinit var userListAdapter: UserListAdapter
    var perPage = 20
    var since = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        title = getString(R.string.github_user_list)

        userList = ArrayList()
        userListAdapter = UserListAdapter(userList) {
            toast("Clicked User: ${it.login}")
        }

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = userListAdapter

        loadApis()

        lifecycleScope.launch {
            usersViewModel.showAllUser.collect{
            showProgress(false)
            Timber.d("user size: ${it.size}")
            userList.clear()
            userList.addAll(it)
            userListAdapter.notifyDataSetChanged()
            }
        }


        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == userList.size - 1) {
                    since += perPage
                    loadApis()
                }
            }
        })

    }

    private fun loadApis() {
        Timber.d( "loadApis: ")
        if (isNetworkAvailable()) {
            getData(since, perPage)
        } else {
            openLottieDialog(JsonFileCode.NO_INTERNET) {
                loadApis()
            }
        }

    }


    private fun getData(since: Int, per_page: Int) {
        showProgress(true)
        usersViewModel.getUserList(since, per_page)
    }
}