package com.cbm.patient.presentation.util

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import timber.log.Timber

object PermissionUtil {

    private const val STORAGE_PERMISSION_REQUEST = 1

    fun requestStorageRead(fragment: Fragment, onPermissionGranted: () -> Unit) {
        if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
            || ActivityCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fragment.requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), STORAGE_PERMISSION_REQUEST
            )
        } else {
            Timber.i("permission rationale no need to show")
            onPermissionGranted()
        }
    }

    fun onPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        when (requestCode) {
            STORAGE_PERMISSION_REQUEST -> if (permissions.contains(Manifest.permission.READ_EXTERNAL_STORAGE)
                && grantResults.contains(PackageManager.PERMISSION_GRANTED)
            ) {
                onPermissionGranted()
            } else onPermissionDenied()
        }
    }
}
