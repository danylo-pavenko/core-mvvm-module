package com.dansdev.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.dansdev.core.model.AlertMessage
import com.dansdev.core.util.alert.snackbarErr
import com.dansdev.core.util.alert.snackbarOk
import com.dansdev.core.util.alert.snackbarProgress
import com.dansdev.core.util.handleNavigationDir
import com.dansdev.core.util.translucentStatusBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class CoreFragment<VB : ViewBinding, VM : CoreViewModel<*>, SVM : CoreSharedViewModel>(
    @LayoutRes layoutId: Int,
    private val hasDarkStatusBar: Boolean = false
) : Fragment(layoutId) {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        if (!::binding.isInitialized) this.binding = onInflateViewBinding(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewReady(view, savedInstanceState)
        setupCoreObservers()
        setupObserveConnection()
        onSetupObservers(viewModel)
        translucentStatusBar(hasDarkStatusBar)
    }

    private fun setupCoreObservers() {
        viewModel.onNavigateAction().observe(viewLifecycleOwner) { navDir ->
            handleNavigationDir(navDir)
        }
        viewModel.onShowError().observe(viewLifecycleOwner) { message ->
            when (message) {
                is AlertMessage.ErrorMessage -> binding.root.snackbarErr(message.content(resources))
                is AlertMessage.SuccessMessage -> binding.root.snackbarOk(message.content(resources))
                is AlertMessage.ProgressMessage -> binding.root.snackbarProgress(message, message.content(resources))
                else -> throw IllegalArgumentException("You try to show new message type, please handle it")
            }
        }
    }

    private fun setupObserveConnection() {
        lifecycleScope.launch {
            sharedViewModel.onConnectedStateUpdate().collect { hasConnection ->
                withContext(Dispatchers.Main) {
                    if (hasConnection) view?.snackbarOk(R.string.network_available)
                    else view?.snackbarErr(R.string.no_connection)
                }
            }
        }
    }

    abstract val viewModel: VM
    abstract val sharedViewModel: SVM

    abstract fun onInflateViewBinding(view: View): VB
    abstract fun onSetupObservers(viewModel: VM)
    abstract fun onViewReady(view: View, savedInstanceState: Bundle?)
}
