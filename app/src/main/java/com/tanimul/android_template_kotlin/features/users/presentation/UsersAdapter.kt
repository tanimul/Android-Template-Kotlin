package com.tanimul.android_template_kotlin.features.users.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tanimul.android_template_kotlin.databinding.ItemUserBinding
import com.tanimul.android_template_kotlin.features.users.domain.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class UsersAdapter(val viewModel: UsersViewModel) :
    PagingDataAdapter<User, UsersAdapter.UsersViewHolder>(DiffCallBack()) {
    class UsersViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(user: User, viewModel: UsersViewModel) {
            binding.apply {
                this.user = user
                this.viewModel = viewModel
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UsersViewHolder {
        return UsersViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it, viewModel) }
    }

    private class DiffCallBack : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean =
            oldItem == newItem
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@BindingAdapter(value = ["bindUsers", "bindViewModel"], requireAll = true)
fun RecyclerView.setUsers(
    data: PagingData<User>?,
    viewModel: UsersViewModel
) {
    if (adapter == null) {
        val usersAdapter = UsersAdapter(viewModel)
        adapter = usersAdapter
        viewModel.observeLoadState(usersAdapter)
    }

    (adapter as? UsersAdapter)?.let { usersAdapter ->
        data?.let {
            viewModel.viewModelScope.launch {
                usersAdapter.submitData(it)
            }
        }
    }
}