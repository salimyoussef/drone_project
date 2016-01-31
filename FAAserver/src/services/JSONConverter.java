package services;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONConverter {
	public String getX(String JSON_DATA) {
		final JSONObject obj = new JSONObject(JSON_DATA);
		final JSONArray geodata = obj.getJSONArray("geodata");
		final int n = geodata.length();
		String x = null;
	    for (int i = 0; i < n; ++i) {
	      final JSONObject information = geodata.getJSONObject(i);
	      x = information.getString("x");
	    }
	    return x;
	}
	public String getY(String JSON_DATA) {
		final JSONObject obj = new JSONObject(JSON_DATA);
		final JSONArray geodata = obj.getJSONArray("geodata");
		final int n = geodata.length();
		String y = null;
	    for (int i = 0; i < n; ++i) {
	      final JSONObject information = geodata.getJSONObject(i);
	      y = information.getString("y");
	    }
	    return y;
	}
	public String getZ(String JSON_DATA) {
		final JSONObject obj = new JSONObject(JSON_DATA);
		final JSONArray geodata = obj.getJSONArray("geodata");
		final int n = geodata.length();
		String z = null;
	    for (int i = 0; i < n; ++i) {
	      final JSONObject information = geodata.getJSONObject(i);
	      z = information.getString("z");
	    }
	    return z;
	}
}
