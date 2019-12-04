package by.epam.courierPicker.encoding;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoding {

    private static final Logger logger = LogManager.getLogger();

    public static String encodePassword(String password) {
        byte[] bytes = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(password.getBytes("utf-8"));
            bytes = messageDigest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.error(ex.getMessage());
        }
        BigInteger bigInteger = new BigInteger(1, bytes);
        logger.info("Password encoded");
        return bigInteger.toString(16);
    }

}
