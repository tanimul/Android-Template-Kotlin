package com.tanimul.android_template_kotlin.features.users.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tanimul.android_template_kotlin.R
import com.tanimul.android_template_kotlin.base.BaseDataBindingFragment
import com.tanimul.android_template_kotlin.common.extention.launchAndRepeatWithViewLifecycle
import com.tanimul.android_template_kotlin.common.extention.toast
import com.tanimul.android_template_kotlin.databinding.FragmentUsersBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UsersFragment :
    BaseDataBindingFragment<FragmentUsersBinding>(R.layout.fragment_users) {
    private val viewModel: UsersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.viewModel = viewModel

        launchAndRepeatWithViewLifecycle {
            launch {
                viewModel.uiAction.collectLatest { it ->
                    when (it) {
                        is UsersUiActions.SelectedUser -> activity?.toast("Clicked User.")
                    }
                }
            }
        }

    }

    companion object {
        fun newInstance() = UsersFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

}