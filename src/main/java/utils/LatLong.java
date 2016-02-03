package utils;

import path.PathPoint;
import java.awt.geom.*;

public class LatLong {

	private int imageW, imageH;
	private double west = -79.974642, north = 39.647556, east = -79.971244, south = 39.644675;

	public LatLong(int imageW, int imageH, double west, double north, double east, double south) {
		super();
		this.imageW = imageW;
		this.imageH = imageH;
		this.west = west;
		this.north = north;
		this.east = east;
		this.south = south;
	}

	public Point2D convertLatLongToCoord(PathPoint coord) {
		double x = coord.getX(), px = imageW * (x - east) / (west - east), y = coord.getY(),
				py = imageH * (y - north) / (south - north);
		return new Point2D.Double(px, py);
	}

}