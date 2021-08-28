package com.example.wifidirect

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.net.wifi.p2p.*
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*
import android.os.Build




class MainActivity : AppCompatActivity() {


    private lateinit var btnOnOff: Button
    private lateinit var btnDiscover: Button
    private lateinit var btnSend: Button
    private lateinit var listView: ListView
    private lateinit var readMsgBox: TextView
    private lateinit var conectionStatus: TextView
    private lateinit var writeMsg: EditText

    private lateinit var wifiManager: WifiManager

    private lateinit var mManager: WifiP2pManager
    private lateinit var mChannel: WifiP2pManager.Channel

    private lateinit var mReceiver: BroadcastReceiver
    private lateinit var mIntentFilter: IntentFilter

    private lateinit var peers : ArrayList<WifiP2pDevice>
    private lateinit var deviceNameArray : ArrayList<String>
    private lateinit var deviceArray : ArrayList<WifiP2pDevice>



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialWork()

        if (wifiManager.isWifiEnabled) {
            btnOnOff.text = "ACTIVADO"
        } else {
            btnOnOff.text = "DESACTIVADO"
        }
    }
    fun cargarArreglo(deviceNameArray:ArrayList<String>, deviceArray:ArrayList<WifiP2pDevice>){
        Log.v("Sergio","Hola $deviceArray")
        this.deviceArray = deviceArray
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNameArray)
        listView.adapter = adapter
    }

    private fun initialWork() {
        btnOnOff = findViewById(R.id.onOff)
        btnDiscover = findViewById(R.id.discover)
        btnSend = findViewById(R.id.sendButton)
        listView = findViewById(R.id.peerListView)
        readMsgBox = findViewById(R.id.readMsg)
        conectionStatus = findViewById(R.id.connectionStatus)
        writeMsg = findViewById(R.id.writeMsg)

        //
        wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        //

        mManager = getSystemService(WIFI_P2P_SERVICE) as WifiP2pManager
        mChannel = mManager.initialize(this, Looper.getMainLooper(), null) as WifiP2pManager.Channel

        mReceiver = WiFiDirectBroadcastReceiver(mManager, mChannel, this)

        mIntentFilter = IntentFilter()
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        Log.v("Sergio","mIntentFilter $mIntentFilter")
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(mReceiver, mIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(mReceiver)
    }

    fun onClickActivarWiFi(v: View) {
        if (wifiManager.isWifiEnabled) {
            btnOnOff.text = ("DESACTIVADO")
            wifiManager.isWifiEnabled = false
        } else {
            btnOnOff.text = ("ACTIVADO")
            wifiManager.isWifiEnabled = true
        }
    }

    fun onClickDiscover(v: View) {

        mManager.discoverPeers(mChannel, object : WifiP2pManager.ActionListener {
            override fun onSuccess() {
                connectionStatus.text = ("Discovery Started")
            }

            override fun onFailure(reason: Int) {
                connectionStatus.text = ("Discovery not Started")
            }
        })
    }
}