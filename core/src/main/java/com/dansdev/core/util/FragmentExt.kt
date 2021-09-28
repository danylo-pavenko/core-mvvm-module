package com.dansdev.core.util

import android.net.Uri
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.dansdev.core.R
import com.dansdev.core.model.NavigateDir
import timber.log.Timber
import java.lang.ref.WeakReference

fun Fragment.openTabUrl(
    url: String?,
    @ColorRes toolbarColorResId: Int = android.R.color.darker_gray
) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    builder.setToolbarColor(ContextCompat.getColor(requireContext(), toolbarColorResId))
    customTabsIntent.launchUrl(requireContext(), Uri.parse(url))
}

fun Fragment.weakInstance(onUseWeakRef: Fragment.() -> Unit) {
    WeakReference<Fragment>(this).get()?.let {
        onUseWeakRef(it)
    } ?: Timber.w("Instance of fragment is NULL")
}

fun Fragment.handleNavigationDir(navDir: NavigateDir?) {
    when (navDir) {
        NavigateDir.Back -> activity?.onBackPressed()
        NavigateDir.Finish -> activity?.finish()
        is NavigateDir.Destination -> findNavController().navigate(navDir.destinationId)
        is NavigateDir.Path -> findNavController().navigate(navDir.uri)
        is NavigateDir.XDir -> findNavController().navigate(navDir.navDirections)
        else -> Timber.w("Can't navigate for $navDir")
    }
}

fun Fragment.translucentStatusBar(dark: Boolean = false) {
    if (dark) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            requireActivity().window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        requireActivity().window.navigationBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
    } else {
        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        requireActivity().window.navigationBarColor = ContextCompat.getColor(requireActivity(), R.color.black)
    }
}

fun AppCompatActivity.findNav(@IdRes fragmentHost: Int): NavController {
    val navHostFragment = supportFragmentManager.findFragmentById(fragmentHost) as NavHostFragment
    return navHostFragment.navController
}
