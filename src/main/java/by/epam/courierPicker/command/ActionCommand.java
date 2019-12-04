package by.epam.courierPicker.command;

import by.epam.courierPicker.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
    String execute(HttpServletRequest request) throws CommandException;
}
