import org.json.JSONException
import org.json.JSONObject

class Parser {
    fun parseResponse(response: String) : String {
        val jsonObject : JSONObject = JSONObject(response)
        return jsonObject.get("cmd").toString()
    }

    fun getXValue(response: String) : Int {
        val jsonObject : JSONObject = JSONObject(response)
        return jsonObject.get("x").toString().toInt()
    }

    fun getYValue(response: String) : Int {
        val jsonObject : JSONObject = JSONObject(response)
        return jsonObject.get("y").toString().toInt()
    }

    fun getFirstValue(response: String) : Boolean {
        val jsonObject : JSONObject = JSONObject(response)
        return jsonObject.get("first").toString().toBoolean()
    }

    fun getTypeValue(response: String) : String {
        val jsonObject : JSONObject = JSONObject(response)
        return jsonObject.get("type").toString()
    }
}