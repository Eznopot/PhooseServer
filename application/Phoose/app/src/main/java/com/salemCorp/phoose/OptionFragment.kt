package com.salemCorp.phoose

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class OptionFragment : Fragment() {

    lateinit var Udpclient : UDPClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Udpclient = ViewModelProvider(requireActivity()).get(UDPClient::class.java)
        return inflater.inflate(R.layout.fragment_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ipText : EditText = view.findViewById(R.id.Ip)
        val portText : EditText = view.findViewById(R.id.Port)
        val saveButton : Button = view.findViewById(R.id.saveButton)
        val sensibilityBar : SeekBar = view.findViewById(R.id.SensibilityBar)

        ipText.setText(Udpclient.getIp())
        portText.setText(Udpclient.getPort().toString())
        saveButton.setOnClickListener {
            val args = Bundle()
            args.putString("ip", ipText.text.toString())
            args.putInt("port", portText.text.toString().toInt())
            args.putFloat("sensibility", (sensibilityBar.progress.toFloat() / 100))
            view.findNavController().navigate(R.id.action_optionFragment_to_mainFragment, args)
        }
    }
}