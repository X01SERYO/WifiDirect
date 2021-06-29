package com.example.wifidirect

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    object Global {
        var btnOnOff: Button? = null
        var btnDiscover: Button? = null
        var btnSend: Button? = null
        var listView : ListView? = null
        var readMsgBox : TextView?=null
        var conectionStatus : TextView?=null
        var writeMsg : EditText?=null

        var wifiManager : WifiManager ? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialWork()

        if(Global.wifiManager?.isWifiEnabled == true){
            Global.btnOnOff?.text = "ACTIVADO"
        }else{
            Global.btnOnOff?.text = "DESACTIVADO"
        }
    }

    fun initialWork(){
        Global.btnOnOff = findViewById(R.id.onOff)
        Global.btnDiscover = findViewById(R.id.discover)
        Global.btnSend = findViewById(R.id.sendButton)
        Global.listView = findViewById(R.id.peerListView)
        Global.readMsgBox = findViewById(R.id.readMsg)
        Global.conectionStatus = findViewById(R.id.connectionStatus)
        Global.writeMsg = findViewById(R.id.writeMsg)

        Global.wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
    }

    fun onClick(v : View){
        if(Global.wifiManager?.isWifiEnabled == true){
            Global.btnOnOff?.text = "DESACTIVADO"
            Global.wifiManager?.isWifiEnabled = false
        }else{
            Global.btnOnOff?.text = "ACTIVADO"
            Global.wifiManager?.isWifiEnabled = true
        }
    }
}