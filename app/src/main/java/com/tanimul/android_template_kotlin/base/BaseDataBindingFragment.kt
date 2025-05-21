package com.tanimul.android_template_kotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.tanimul.android_template_kotlin.R
import com.tanimul.android_template_kotlin.utils.LoadingDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
abstract class BaseDataBindingFragment<T : ViewDataBinding> constructor(@LayoutRes private val mContentLayoutId: Int) :
    Fragment() {

    //private var navigationHost: NavigationHost? = null
    var mBinding by autoCleared<T>()

    private var loadingDialog: LoadingDialog? = null

    var mToolbar: Toolbar? = null
        private set

    /* override fun onAttach(newBase: Context) {
         navigationHost = newBase as NavigationHost
         super.onAttach(ViewPumpContextWrapper.wrap(newBase))
     }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            mContentLayoutId,
            container,
            false
        )
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.root.filterTouchesWhenObscured = true

        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dismissDialogOnDestroy()
        mToolbar = null
    }

    override fun onResume() {
        super.onResume()
    }

    protected open fun initLoadingDialog() {
        loadingDialog = LoadingDialog(requireContext(), false, getString(R.string.please_wait))
    }

    protected open fun showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog!!.isShowing) {
            if (!requireActivity().isFinishing && !requireActivity().isDestroyed) {
                loadingDialog!!.show()
            }
        }
    }

    protected open fun dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            if (!requireActivity().isFinishing && !requireActivity().isDestroyed && isAdded) {
                activity?.let { act ->
                    loadingDialog!!.dismiss(act)
                } ?: loadingDialog!!.dismiss()
            }
        }
    }

    private fun dismissDialogOnDestroy() {
        if (loadingDialog != null && loadingDialog!!.isShowing && isAdded) {
            activity?.let { act ->
                loadingDialog!!.dismiss(act)
            } ?: loadingDialog!!.dismiss()
        }
    }

    protected open val resToolbarId: Int = 0

    protected open val haveToolbar: Boolean = false

}
