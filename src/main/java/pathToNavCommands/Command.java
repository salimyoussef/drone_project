package pathToNavCommands;

import com.google.gson.JsonObject;
import drone.Moveable;
import java.io.Serializable;

public interface Command extends Serializable {
	void execute(Moveable drone, JsonObject params);
}
