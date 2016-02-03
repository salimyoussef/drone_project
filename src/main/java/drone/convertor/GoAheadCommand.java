package drone.convertor;

import com.google.gson.JsonObject;
import path.PathPoint;
import pathToNavCommands.Command;
import drone.Moveable;
import java.rmi.RemoteException;

public class GoAheadCommand implements Command {
	
	public GoAheadCommand() {

	}
	
	@Override
	public void execute(Moveable drone, JsonObject jsonCommand)  {
		PathPoint point = new PathPoint(jsonCommand.get("x").getAsDouble(),jsonCommand.get("y").getAsDouble(),
				jsonCommand.get("z").getAsDouble());
		drone.goTo(point);
	}

}
