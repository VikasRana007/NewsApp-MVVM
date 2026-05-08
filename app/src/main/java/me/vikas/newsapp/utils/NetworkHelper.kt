package me.vikas.newsapp.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import javax.inject.Inject

interface NetworkHelper {
    fun isInternetConnected(): Boolean
}


/**
 * Later we will update it(Network Helper) in Reactive Real Time updates using flow.
 */
class NetworkHelperImpl @Inject constructor(private val context: Context) : NetworkHelper {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isInternetConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}