package path;

import java.util.ArrayList;
import java.util.Iterator;

public class Path {

	ArrayList<PathPoint> rout;

	public Path() {
		this.rout = new ArrayList<PathPoint>();
	}

	public void add(PathPoint p) {
		this.rout.add(p);
	}

	public void remove(PathPoint p) {
		this.rout.remove(p);
	}

	public Iterator<PathPoint> iterator() {
		return this.rout.iterator();
	}

	public PathPoint getFirst() {
		return this.rout.get(0);
	}

	public PathPoint getLast() {
		return this.rout.get(this.rout.size() - 1);
	}

	public ArrayList<PathPoint> getPathPoints() {
		return this.rout;
	}

	public int getLength() {
		return this.rout.size();
	}

	public PathPoint getPathPoint(int i) {
		return this.rout.get(i);
	}

}
