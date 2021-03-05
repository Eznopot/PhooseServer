package com.salemCorp.phoose

import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.seismic.ShakeDetector
import org.json.JSONObject


class MainFragment : Fragment(), ShakeDetector.Listener {

    lateinit var Udpclient : UDPClient
    private var sensibility : Float? = (1).toFloat()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Udpclient = ViewModelProvider(requireActivity()).get(UDPClient::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!(arguments?.getString("ip").isNullOrBlank())) {
            Udpclient.changeIp(arguments?.getString("ip")!!)
        }
        if (arguments?.getInt("port") != null) {
            Udpclient.changePort(arguments?.getInt("port")!!)
        }
        if (arguments?.getFloat("sensibility") != (0).toFloat() && arguments?.getFloat("sensibility")!= null) {
            sensibility = arguments?.getFloat("sensibility")
        }
        val leftClick : FrameLayout = view.findViewById(R.id.left_click)
        val rightClick : FrameLayout = view.findViewById(R.id.right_click)

        viewTouchListener(view)
        clickTouchListener(rightClick, "right")
        clickTouchListener(leftClick, "left")
        val sensorManager = context?.getSystemService(SENSOR_SERVICE) as SensorManager?
        val sd = ShakeDetector(this)
        sd.start(sensorManager)
    }

    private fun clickTouchListener(card : FrameLayout, type : String) {
        card.setOnTouchListener { _, event ->
            val x = event.x.toInt()
            val y = event.y.toInt()

            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    if (!swipe) {
                        cursorMoving(x, y, false)
                    }
                }
                MotionEvent.ACTION_UP -> cursorRelease(type)
                MotionEvent.ACTION_DOWN -> {
                    if (!swipe) {
                        cursorMoving(x, y, true)
                        cursorClicks(type)
                    }
                }
            }
            card.performClick()
        }
    }

    private fun viewTouchListener(view : View) {
        view.setOnTouchListener { _, event ->
            val x = event.x.toInt()
            val y = event.y.toInt()

            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_POINTER_DOWN -> {
                    swipe = true
                    cursorMoving(x, y, true)
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    swipe = false
                    cursorMoving(x, y, true)
                }
                MotionEvent.ACTION_MOVE -> {
                    if (!swipe) {
                        cursorMoving(x, y, false)
                    } else {
                        scroll(y)
                    }
                }
                MotionEvent.ACTION_DOWN -> cursorMoving(x, y, true)
            }
            true
            //view.performClick()
        }

    }

    private fun cursorMoving(x: Int, y: Int, first: Boolean) {
        val json = JSONObject()
        val sx = (x * sensibility!!).toInt()
        val sy = (y * sensibility!!).toInt()
        json.put("cmd", "move")
        json.put("x", sx)
        json.put("y", sy)
        json.put("first", first)
        Udpclient.sendUDP(json.toString())
    }

    private fun cursorClicks(type: String) {
        val json = JSONObject()
        json.put("cmd", "click")
        json.put("type", type)
        Udpclient.sendUDP(json.toString())
    }

    private fun cursorRelease(type: String) {
        val json = JSONObject()
        json.put("cmd", "release")
        json.put("type", type)
        Udpclient.sendUDP(json.toString())
    }

    private fun scroll(y: Int) {
        val json = JSONObject()
        json.put("cmd", "scroll")
        json.put("y", y)
        Udpclient.sendUDP(json.toString())
    }

    override fun hearShake() {
        view?.post {
            findNavController().navigate(R.id.action_mainFragment_to_optionFragment)
        }
    }

    companion object {
        var swipe = false
    }
}