package by.epam.courierPicker.connectionpool;

import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.constant.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public enum ConnectionPool {

    INSTANCE;

    private static final int DEFAULT_POOL_SIZE = 32;

    private final Logger logger = LogManager.getLogger();
    private BlockingQueue<ProxyConnection> freeConnections = new LinkedBlockingQueue<>(DEFAULT_POOL_SIZE);
    private Queue<ProxyConnection> givenConnections = new LinkedList<>();

    public static boolean isTest = false;

    ConnectionPool() {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input;
            input = classLoader.getResourceAsStream(Path.DATABASE_INFO_FILE);
            properties.load(input);
            Class.forName(properties.getProperty(ParamName.DRIVER_PARAM));
            for (int i = 0; i < DEFAULT_POOL_SIZE; ++i) {
                freeConnections.put(new ProxyConnection(DriverManager.getConnection(properties.getProperty(ParamName.URL_PARAM), properties.getProperty(ParamName.USER_PARAM), properties.getProperty(ParamName.PASSWORD_PARAM))));
            }
            logger.info("Connection initialised");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenConnections.add(connection);
            logger.info("Connection given");
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) {
        givenConnections.remove(connection);
        try {
            freeConnections.put(connection);
            logger.info("Connection back");
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    public void destroyConnectionPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                freeConnections.take().finalClose();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
        }
        deregisterDrivers();
        logger.info("Connection pool destroyed");
    }

    private void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("Drivers deregistered");
    }

}
