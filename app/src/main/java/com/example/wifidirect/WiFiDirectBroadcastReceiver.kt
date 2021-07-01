package com.example.wifidirect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.WifiP2pManager
import android.widget.Toast

class WiFiDirectBroadcastReceiver(
    private var mManager: WifiP2pManager,
    private var mChannel: WifiP2pManager.Channel,
    private var mActivity: MainActivity
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        when {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION == action -> {
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE,-1)
                if (state==WifiP2pManager.WIFI_P2P_STATE_ENABLED){
                    Toast.makeText(context,"WIFI ON", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,"WIFI OFF", Toast.LENGTH_SHORT).show()
                }
            }
            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION == action -> {
                if(mManager!=null){
                    mManager.requestPeers(mChannel,mActivity.peerListListener)
                }
            }
            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION == action -> {
                // do something
            }
        }
    }
}