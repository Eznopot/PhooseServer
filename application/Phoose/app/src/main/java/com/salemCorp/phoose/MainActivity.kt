package com.salemCorp.phoose

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var UDPclient: UDPClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen)
        UDPclient = ViewModelProvider(this).get(UDPClient::class.java)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val navHostFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment)
        navHostFragment!!.childFragmentManager.fragments[0]
        if (navHostFragment.childFragmentManager.fragments[0].javaClass == MainFragment::class.java) {
            val json = JSONObject()
            if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                json.put("cmd", "paste")
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                json.put("cmd", "copy")
            }
            UDPclient.sendUDP(json.toString())
        }
        return true
    }
}