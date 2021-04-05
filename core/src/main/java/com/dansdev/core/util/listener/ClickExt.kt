package com.dansdev.core.util.listener

import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor

object AppScope : CoroutineScope by GlobalScope {
    override val coroutineContext = Dispatchers.Main.immediate
}

@OptIn(ObsoleteCoroutinesApi::class)
fun View.setOnSafeClick(action: suspend (v: View) -> Unit) {
    val scope = (context as? CoroutineScope) ?: AppScope
    val eventActor = scope.actor<Unit>(capacity = Channel.CONFLATED) {
        for (event in channel) action(this@setOnSafeClick)
    }
    setOnClickWithTouchImpact { eventActor.offer(Unit) }
}
