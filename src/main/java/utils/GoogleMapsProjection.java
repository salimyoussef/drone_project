package utils;

import path.PathPoint;
import java.awt.Point;

public class GoogleMapsProjection {

	/**
	 * Converts the given longitude value to the x value of the Google Maps
	 * world coordinate.
	 *
	 * @param lon
	 *            the longitude value
	 * @return the x value of the corresponding Google Maps world coordinate
	 */
	public static Double lonToXWorld(Double lon) {
		Double tiles = Math.pow(2, 0);
		Double circumference = 256 * tiles;
		Double radius = circumference / (2 * Math.PI);
		Double falseEasting = -1.0 * circumference / 2.0;
		return (radius * Math.toRadians(lon)) - falseEasting;
	}

	public static Double lng2M(Double lng) {
		double originShift = 2 * Math.PI * 6378137 / 2.0;
		double mx = lng * originShift / 180.0;
		return mx;
	}

	/**
	 * Converts the given latitude value to the y value of the Google Maps world
	 * coordinate.
	 *
	 * @param lat
	 *            the latitude value
	 * @return the y value of the corresponding Google Maps world coordinate
	 */
	public static Double latToYWorld(Double lat) {
		Double tiles = Math.pow(2, 0);
		Double circumference = 256 * tiles;
		Double radius = circumference / (2 * Math.PI);
		Double falseNorthing = circumference / 2.0;
		return ((radius / 2.0 * Math.log((1.0 + Math.sin(Math.toRadians(lat))) / (1.0 - Math.sin(Math.toRadians(lat)))))
				- falseNorthing) * -1;
	}

	public static Double lat2M(Double lat) {
		double originShift = 2 * Math.PI * 6378137 / 2.0;
		double my = Math.log(Math.tan((90 + lat) * Math.PI / 360.0)) / (Math.PI / 180.0);
		my = my * originShift / 180.0;
		return my;
	}

	/**
	 * Converts the given world coordinates to the pixel coordinates
	 * corresponding to the given zoom level.
	 *
	 * @param xWorld
	 *            the x value of the world coordinate
	 * @param yWorld
	 *            the y value of the world coordinate
	 * @param zoomLevel
	 *            the zoom level
	 * @return the pixel coordinates as a Point
	 */
	public static Point worldToPixel(Double xWorld, Double yWorld, int zoomLevel) {
		int zoom = (int) Math.pow(2, zoomLevel);
		int x = (int) Math.round(xWorld * zoom);
		int y = (int) Math.round(yWorld * zoom);
		return new Point(x, y);
	}

	public static PathPoint M2Pxl(Double mx, Double my, int zoomLevel) {
		double tileSize = 256;
		double initialResolution = 2 * Math.PI * 6378137 / tileSize;
		double originShift = 2 * Math.PI * 6378137 / 2.0;
		int zoom = (int) Math.pow(2, zoomLevel);
		double res = initialResolution / zoom;

		double px = Math.floor((mx + originShift) / res);
		double py = Math.floor((my + originShift) / res);

		return new PathPoint(px, py, 0);

	}

	public static double deg2mlng(double lng) {
		return lng * 20037508.34 / 180;
	}

	public static double deg2mlat(double lat) {
		double y = Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180);
		y = y * 20037508.34 / 180;
		return y;
	}

	public static double deg2mlnginv(double lng) {
		return lng * 180 / 20037508.34;
	}

	public static double deg2mlatinv(double lat) {
		double y = Math.atan(Math.exp(lat * Math.PI / 20037508.34)) * 360 / Math.PI - 90;
		return y;
	}

	public static PathPoint M2Pxlinv(Double px, Double py, int zoomLevel) {
		double tileSize = 256;
		double initialResolution = 2 * Math.PI * 6378137 / tileSize;
		double originShift = 2 * Math.PI * 6378137 / 2.0;
		int zoom = (int) Math.pow(2, zoomLevel);
		double res = initialResolution / zoom;

		double mx = px * res - originShift;
		double my = py * res - originShift;

		return new PathPoint(mx, my, 0);

	}

	public static PathPoint M2latlng(Double mx, Double my) {
		// double tileSize = 256;
		// double initialResolution = 2 * Math.PI * 6378137 / tileSize;
		double originShift = 2 * Math.PI * 6378137 / 2.0;

		double lon = (mx / originShift) * 180.0;
		double lat = (my / originShift) * 180.0;
		lat = 180 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180.0)) - Math.PI / 2.0);

		return new PathPoint(lon, lat, 0);

	}

	public static void main(String args[]) {
		System.out.println(deg2mlnginv(deg2mlng(-77.035974)));
		System.out.println(deg2mlatinv(deg2mlat(40.714728)));
	}

}