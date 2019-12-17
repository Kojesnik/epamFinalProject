package by.epam.courierPicker.servlet;

import by.epam.courierPicker.command.ActionCommand;
import by.epam.courierPicker.command.ActionFactory;
import by.epam.courierPicker.connectionpool.ConnectionPool;
import by.epam.courierPicker.constant.AttributeName;
import by.epam.courierPicker.constant.Path;
import by.epam.courierPicker.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setMaxInactiveInterval(10 * 60);
        String page;
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(req);
        try {
            page = command.execute(req);
        } catch (CommandException ex) {
            page = Path.MAIN_PATH;
            logger.error(ex.getMessage());
        }
        if (page != null) {
            if (req.getAttribute(AttributeName.REDIRECT) == null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(req, resp);
            } else {
                resp.sendRedirect("controller" + req.getAttribute("redirectTo"));
            }
        }
    }

    @Override
    public void destroy() {
        ConnectionPool.INSTANCE.destroyConnectionPool();
    }


}
