package path;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class PathPoint {

	@SerializedName("x")
	@Expose
	private double x;

	@SerializedName("y")
	@Expose
	private double y;

	@SerializedName("z")
	@Expose
	private double z;

	public PathPoint(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "{\n" + "  'log':" + x + ",\n  'lat':" + y + "\n}";
	}

	public static void main(String args[]) {
		System.out.println(new PathPoint(4, 1, 5));
	}
}
