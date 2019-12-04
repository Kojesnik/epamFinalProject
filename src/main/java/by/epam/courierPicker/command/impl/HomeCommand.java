package by.epam.courierPicker.command.impl;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class HomeCommand implements ActionCommand {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        logger.info("HomeCommand executed");
        String page = Path.MAIN_PATH;
        logger.info("Redirect to " + page);
        return page;
    }
}
