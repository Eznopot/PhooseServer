import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.Inet4Address
import java.net.InetAddress
import java.util.Enumeration

import java.net.NetworkInterface




class Info {
    var port: Int = 8080;
    var ip: String = "localhost"
}

class UDPServeur() {
    val info: Info = Info()
    var socket: DatagramSocket? = null

    init {
        try {
            socket = DatagramSocket(info.port)
            socket?.broadcast = true
            val list = NetworkInterface.getNetworkInterfaces().toList()
            val inetAddress = list[1].inetAddresses.toList()
            println("IP Address:- " + inetAddress[1].hostAddress)
            println("port is " + info.port)
        } catch (e: Exception) {
            println("error :$e")
            e.printStackTrace()
        }
    }

    fun receiveUDP() : String {
        val buffer = ByteArray(2048)
        val packet = DatagramPacket(buffer, buffer.size)
        var msg = ""

        try {
            socket!!.receive(packet)
            msg = String(packet.data)
            println("open fun receiveUDP packet received = $msg")
        } catch (e: Exception) {
            println("open fun receiveUDP catch exception." + e.toString())
            e.printStackTrace()
        }
        return msg
    }

    fun closeServer() {
        socket!!.close()
    }
}