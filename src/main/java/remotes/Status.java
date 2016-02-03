package remotes;

import path.PathPoint;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mohannad on 04/01/16.
 */

public class Status implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String DRONE_ARRIVED = "Arrived";
	public static final String DRONE_DEPARTED = "Departed";
	public static final String DRONE_GOING = "Going";
	public static final String DRONE_Returning = "Returning";
	public static final String DRONE_Ready = "Ready";
	public static final String DRONE_Waiting = "Waiting";

	public String droneStatus = Status.DRONE_Waiting;

	public PathPoint currentLocation;
	public ArrayList<PathPoint> path;
	public ArrayList<PathPoint> pathChange;

	private String serializeArrayList(ArrayList<PathPoint> points) {

		if (points == null)
			return "[]";

		String res = "[\n";

		for (int i = 0; i < points.size(); i++) {
			PathPoint p = points.get(i);
			res += p.toString();
			if (i != points.size() - 1) {
				res += ",\n";
			}
		}
		res += "\n]";
		return res;
	}

	@Override
	public String toString() {
		return "{\n" + " 'droneStatus':'" + droneStatus + '\'' + ",  \n 'currentLocation':"
				+ (currentLocation != null ? currentLocation : "{}") + ",  \n 'path':" + serializeArrayList(path)
				+ ",  \n 'pathChange':" + serializeArrayList(pathChange) + "   \n}";
	}

	public static void main(String args[]) {
		Status s = new Status();
		s.currentLocation = new PathPoint(5.1, 55.2222, 6.111);
		System.out.println(s);
	}
}
