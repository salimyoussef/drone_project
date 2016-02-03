package pathFinder;

import endPoints.AdressEndPoints;
import path.Path;

public interface PathPlannerStrategy {
	public Path findPath(AdressEndPoints endPoints);
}
