package com.tanimul.android_template_kotlin.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tanimul.android_template_kotlin.R
import com.tanimul.android_template_kotlin.adapter.UserListAdapter
import com.tanimul.android_template_kotlin.data.models.response.UserResponse
import com.tanimul.android_template_kotlin.databinding.ActivityMainBinding
import com.tanimul.android_template_kotlin.ui.base.AppBaseActivity
import com.tanimul.android_template_kotlin.utils.extentions.JsonFileCode
import com.tanimul.android_template_kotlin.utils.extentions.isNetworkAvailable
import com.tanimul.android_template_kotlin.utils.extentions.openLottieDialog
import com.tanimul.android_template_kotlin.utils.extentions.toast
import com.tanimul.android_template_kotlin.viewmodel.UserListViewModel


class MainActivity : AppBaseActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var userList: ArrayList<UserResponse>
    lateinit var usersViewModel: UserListViewModel
    lateinit var userListAdapter: UserListAdapter
    var per_page = 20
    var since = 0
    private var onRetry: (() -> Unit)? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)
        title = getString(R.string.github_user_list)

        userList = ArrayList()
        userListAdapter = UserListAdapter(userList) {
            toast(it.login.toString())
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = userListAdapter

        usersViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[UserListViewModel::class.java]

        loadApis()

        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == userList.size - 1) {
                    since += per_page
                    loadApis()
                }
            }
        })

    }

    private fun loadApis() {
        Log.d(TAG, "loadApis: ")
        if (isNetworkAvailable()) {
            getData(since, per_page)
        } else {
            openLottieDialog(JsonFileCode.NO_INTERNET) {
                loadApis()
            }
        }

    }


    private fun getData(since: Int, per_page: Int) {
        showProgress(true)
        usersViewModel.getUserList(since, per_page)
        usersViewModel.users.observe(this) {
            Log.d(TAG, "getData: ${it?.size}")
            it?.let { it1 -> userList.addAll(it1) }
            userListAdapter.notifyDataSetChanged()
            showProgress(false)
        }
    }
}