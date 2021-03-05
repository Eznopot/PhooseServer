package com.salemCorp.phoose

import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.ViewModel
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class UDPClient : ViewModel() {
    object Info {
        var ip : String = "192.168.1.90"
        var port: Int = 8080
    }

    init {
        Log.i("ViewModel", "ViewModel created!")
    }

    fun sendUDP(messageStr: String) {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            val socket = DatagramSocket()
            socket.broadcast = true
            val sendData = messageStr.toByteArray()
            val sendPacket = DatagramPacket(sendData, sendData.size, InetAddress.getByName(Info.ip), Info.port)
            socket.send(sendPacket)
        } catch (e: IOException) {
            Log.e("ERROR", e.toString())
        }
    }

    fun changeIp(ip : String) {
        Info.ip = ip
    }

    fun changePort(port : Int) {
        Info.port = port
    }

    fun getIp() : String {
        return Info.ip
    }

    fun getPort() : Int {
        return Info.port
    }
}