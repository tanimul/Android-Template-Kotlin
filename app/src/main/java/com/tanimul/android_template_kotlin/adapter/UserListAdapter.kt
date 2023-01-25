package com.tanimul.android_template_kotlin.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tanimul.android_template_kotlin.data.models.response.UserModel
import com.tanimul.android_template_kotlin.databinding.LayoutUserBinding
import com.tanimul.android_template_kotlin.utils.extentions.loadImageFromUrl

class UserListAdapter(
    private val mUserItems: List<UserModel>, private val onItemClicked: (UserModel) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {
    private val TAG = "CardListAdapter"

    inner class UserListViewHolder(val binding: LayoutUserBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return UserListViewHolder(
            LayoutUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ${mUserItems.size}")
        with(holder.binding) {
            with(mUserItems[position]) {
                tvName.text = login
                tvId.text = node_id
                avatar_url?.let { ivIcon.loadImageFromUrl(it) }

            }
        }
        holder.itemView.setOnClickListener {
            onItemClicked.invoke(mUserItems[position])
        }

    }

    override fun getItemCount(): Int {
        return mUserItems.size
    }
}