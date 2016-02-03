package drone;

import java.io.Serializable;

import path.PathPoint;

public interface Moveable extends Serializable {
	public void goTo(PathPoint point);
}
