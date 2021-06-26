package com.dansdev.core

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.dansdev.core.util.alert.snackbarErr
import com.dansdev.core.util.alert.snackbarOk
import com.dansdev.core.util.handleNavigationDir
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@InternalCoroutinesApi
abstract class CoreBottomSheet<VB : ViewBinding, VM : CoreViewModel<*>, SVM : CoreSharedViewModel>(
    @LayoutRes
    private val layoutId: Int,
    private val isFullScreen: Boolean = true
) : BottomSheetDialogFragment() {

    protected lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!this::binding.isInitialized) this.binding = onInflateViewBinding(inflater.inflate(layoutId, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSetupObservers(viewModel)
        setupCoreObservers()
        onViewReady(view, savedInstanceState)
        setupObserveConnection()
    }

    private fun setupCoreObservers() {
        viewModel.onNavigateAction().observe(viewLifecycleOwner) { navDir ->
            handleNavigationDir(navDir)
        }
    }

    private fun setupObserveConnection() {
        lifecycleScope.launch {
            sharedViewModel.onConnectedStateUpdate().collectLatest { hasConnection ->
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = BottomSheetDialog(
            requireContext(),
            R.style.AppBottomSheetDialogTheme
        )

        bottomSheetDialog.dismissWithAnimation = true
        bottomSheetDialog.behavior.isFitToContents = !isFullScreen
        bottomSheetDialog.behavior.skipCollapsed = isFullScreen
        bottomSheetDialog.behavior.state = if (isFullScreen) BottomSheetBehavior.STATE_EXPANDED
        else BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetDialog.window?.statusBarColor = Color.TRANSPARENT
        bottomSheetDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        bottomSheetDialog.behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {}

            override fun onStateChanged(p0: View, state: Int) {
                if (state == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss()
                }
            }
        })

        return bottomSheetDialog
    }
}
