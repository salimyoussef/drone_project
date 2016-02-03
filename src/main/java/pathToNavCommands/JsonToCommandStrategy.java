package pathToNavCommands;

import com.google.gson.JsonObject;

public interface JsonToCommandStrategy {
	public void executeCommand(JsonObject stringCommand);
}
