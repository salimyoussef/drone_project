package pathFinder;

import endPoints.AdressEndPoints;
import path.Path;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import path.PathPoint;
import utils.Location;
import utils.PolylineDecoder;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mohannad on 02/01/16.
 */
public class GooglePathFinder implements PathPlannerStrategy {

	private String key = "AIzaSyDRRinfRhWRbWSTPAw_3MA6K2LpTziucXQ";
	private String urlString = "https://maps.googleapis.com/maps/api/directions/json?origin=";
	private Path p;

	@Override
	public Path findPath(AdressEndPoints endPoints) {

		urlString += endPoints.getStartAdrr() + "&destination=" + endPoints.getEndAdrr();
		urlString += "&mode=driving&key=" + key;
		System.out.println(urlString);
		try {
			URL urlGoogleDirService = new URL(urlString);
			HttpURLConnection urlGoogleDirCon = (HttpURLConnection) urlGoogleDirService.openConnection();
			urlGoogleDirCon.setAllowUserInteraction(false);
			urlGoogleDirCon.setDoInput(true);
			urlGoogleDirCon.setDoOutput(false);
			urlGoogleDirCon.setUseCaches(true);
			urlGoogleDirCon.setRequestMethod("GET");
			urlGoogleDirCon.connect();
			JSONTokener tokener = new JSONTokener(urlGoogleDirCon.getInputStream());
			JSONObject jsonObject = new JSONObject(tokener);
			return parse(jsonObject);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void setPath(Path p) {
		this.p = p;
	}

	private Path getPath() {
		return p;
	}

	private Path parse(JSONObject jObject) {
		List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
		JSONArray jRoutes = null;
		JSONArray jLegs = null;
		JSONArray jSteps = null;

		Path rpath = new Path();
		try {
			jRoutes = jObject.getJSONArray("routes");
			/** Traversing all routes */
			for (int i = 0; i < jRoutes.length(); i++) {
				jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
				List path = new ArrayList<HashMap<String, String>>();
				/** Traversing all legs */
				for (int j = 0; j < jLegs.length(); j++) {
					jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
					/** Traversing all steps */
					for (int k = 0; k < jSteps.length(); k++) {
						double start_lat = ((JSONObject) ((JSONObject) jSteps.get(k)).getJSONObject("start_location"))
								.getDouble("lat");
						double start_lon = ((JSONObject) ((JSONObject) jSteps.get(k)).getJSONObject("start_location"))
								.getDouble("lng");
						double end_lat = ((JSONObject) ((JSONObject) jSteps.get(k)).getJSONObject("end_location"))
								.getDouble("lat");
						double end_lon = ((JSONObject) ((JSONObject) jSteps.get(k)).getJSONObject("end_location"))
								.getDouble("lng");
						String polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline"))
								.get("points");
						ArrayList<Location> polyPoints = PolylineDecoder.decodePoly(polyline);
						for (Location l : polyPoints) {
							PathPoint sPoint = new PathPoint(l.getLongitude(), l.getLatitude(), 0);
							rpath.add(sPoint);
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setPath(rpath);
		return rpath;
	}

	// just for testing
	public static void main(String args[]) {

		GooglePathFinder pathFinder = new GooglePathFinder();
		Path p = pathFinder.findPath(new AdressEndPoints("Paris", "Lille"));
		Iterator<PathPoint> i = p.iterator();
		while (i.hasNext()) {
			System.out.println(i.next().getX());
		}
		System.out.println("finish");
	}
}
