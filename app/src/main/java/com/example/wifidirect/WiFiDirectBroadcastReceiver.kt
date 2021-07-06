package com.example.wifidirect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.ArrayList


class WiFiDirectBroadcastReceiver(
    private var mManager: WifiP2pManager,
    private var mChannel: WifiP2pManager.Channel,
    private var mActivity: MainActivity
) : BroadcastReceiver() {

    private var deviceNameArray = ArrayList<String>()
    private var deviceArray = ArrayList<WifiP2pDevice>()

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        when {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION == action -> {
                //DETECTA EL ESTADO INICIAL DEL WIFI
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1)
                if (state==WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    Toast.makeText(context,"WIFI ON", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"WIFI OFF", Toast.LENGTH_SHORT).show()
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION == action -> {
                //OBTIENE
                val list: WifiP2pDeviceList? =
                    intent.getParcelableExtra(WifiP2pManager.EXTRA_P2P_DEVICE_LIST)

                for (dispositivo in list!!.deviceList) { //...
                    deviceNameArray.add(dispositivo.deviceName)
                    deviceArray.add(dispositivo)
                    mActivity.cargarArreglo(deviceNameArray, deviceArray)
                    Toast.makeText(mActivity, dispositivo.deviceName, Toast.LENGTH_SHORT).show()
                }

            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION == action -> {
                // do something
            }
        }
    }
}