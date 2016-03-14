package glassapptest.app;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JsonParser {
	
	public String createRequestJson(){
		JSONObject jObject = new JSONObject();
		try {
			jObject.put("equipmentID", "ILINQAMTEST2");
			jObject.put("modality", "CT");
			jObject.put("facilityID", "215DSE");
			jObject.put("countryCode", "US");
			jObject.put("problemType", "System problem");
			jObject.put("equipmentStatus", "CompletelyDown");
			jObject.put("name", "Lastname,firstname");
			jObject.put("phoneNumber", "123456");
			jObject.put("description", "Display problem");
			jObject.put("shortDescription", "Display problem");
			jObject.put("timePeriod", "16,Sep,2013,16,31");
			jObject.put("serviceCode", "A");
			jObject.put("locale", "en_US");
			jObject.put("requestingApp", "icenter");
		} catch (JSONException e) {
			Log.d("createRequestJson","JSONException has occured");
			e.printStackTrace(System.out);
		}
		return jObject.toString();
	}

}
