package com.dansdev.core

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.dansdev.app.PerfectDesignIniter
import com.novoda.merlin.Merlin

abstract class CoreMainActivity<VM: CoreSharedViewModel>: FragmentActivity() {

    abstract val viewModel: VM

    private val merlin by lazy {
        Merlin.Builder()
            .withConnectableCallbacks()
            .withDisconnectableCallbacks()
            .build(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleConnection()
    }

    private fun handleConnection() {
        merlin.registerConnectable { viewModel.networkState(true) }
        merlin.registerDisconnectable { viewModel.networkState(false) }
    }

    override fun onStart() {
        super.onStart()
        PerfectDesignIniter.onStart(this)
        merlin.bind()
    }

    override fun onStop() {
        merlin.unbind()
        super.onStop()
    }
}
