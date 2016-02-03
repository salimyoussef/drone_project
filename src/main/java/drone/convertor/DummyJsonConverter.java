package drone.convertor;

import java.util.HashMap;

import com.google.gson.JsonObject;
import pathToNavCommands.Command;
import drone.Moveable;
import pathToNavCommands.JsonToCommandStrategy;

public class DummyJsonConverter implements JsonToCommandStrategy {

	private static HashMap<String, Command> commands = new HashMap<>();

	static {
		commands.put("goAhead", new GoAheadCommand());
	}

	Moveable drone;

	public DummyJsonConverter(Moveable drone) {
		this.drone = drone;
	}

	@Override
	public void executeCommand(JsonObject jsonCommand) {
		Command command = commands.get(jsonCommand.get("idCommand").getAsString());
		command.execute(drone, jsonCommand);
	}

}
