package by.epam.courierPicker.command;

import by.epam.courierPicker.command.impl.EmptyCommand;
import by.epam.courierPicker.type.CommandType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

    private static final Logger logger = LogManager.getLogger();

    public ActionCommand defineCommand(HttpServletRequest request) {
        ActionCommand currentCommand;
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            currentCommand = new EmptyCommand();
            logger.info("Empty command");
        }
        try {
            CommandType currentType = CommandType.valueOf(action.toUpperCase());
            currentCommand = currentType.getCommand();
            logger.info("Command defined");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("wrongAction", action);
            currentCommand = new EmptyCommand();
            logger.error(ex.getMessage());
        }
        return currentCommand;
    }

}
