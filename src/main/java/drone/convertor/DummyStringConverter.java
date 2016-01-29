package drone.convertor;

import java.util.HashMap;

import pathToNavCommands.Command;
import drone.Moveable;
import pathToNavCommands.StringToCommandStrategy;

public class DummyStringConverter implements StringToCommandStrategy {

	private static HashMap<String, Command> commands = new HashMap<>();
	static{
		commands.put("goAhead", new GoAheadCommand());
	}

	Moveable drone;
  	public DummyStringConverter (Moveable drone) {this.drone = drone; }

@Override
public void executeCommand(String stringCommand) {
	String cleanCommand = stringCommand.substring(1,stringCommand.length()-1);
	String[] params = cleanCommand.split(" ");
	Command command = commands.get(params[0]);
	command.execute(drone,params);
}
  
}

