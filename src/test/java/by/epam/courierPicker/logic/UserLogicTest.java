package by.epam.courierPicker.logic;

import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.entity.User;
import by.epam.courierPicker.exception.LogicException;
import by.epam.courierPicker.type.RoleType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class UserLogicTest {

    @BeforeClass
    public void init() {

    }

    @Test
    public void testLoginUser() {
        Map<String, String> req = new HashMap();
        req.put(ParamName.ROLE_PARAM, "user");
        req.put(ParamName.STATUS_PARAM, "autoresized");
        req.put(ParamName.ID_USER_PARAM, "3");
        try {
            assertEquals(UserLogic.INSTANCE.loginUser("Masterdota12", "applenik"), req);
        } catch (LogicException e) {

        }
    }

    @Test
    public void testRegisterUser() {
        Map<String, String> req = new HashMap();
        req.put(ParamName.ROLE_PARAM, "user");
        req.put(ParamName.STATUS_PARAM, "autoresized");
        req.put(ParamName.ID_USER_PARAM, "6");
        try {
            assertEquals(UserLogic.INSTANCE.registerUser("Masterdota12", "user", "kiki@gmail.com", "shotkkkk", "Nikita", "Kolosss"), req);
        } catch (LogicException e) {

        }
    }

    @Test
    public void testFindUser() {
        User user = new User();
        user.setState("active");
        user.setEmail("koruzin@gmail.com");
        user.setFirstName("Nikita");
        user.setId(3);
        user.setLastName("Koruzin");
        user.setLogin("applenik");
        user.setPassword("3af04f00a35d00376c7d6173a8602505df8114fd");
        user.setRole(RoleType.USER);
        try {
            assertEquals(UserLogic.INSTANCE.findUser(3), user);
        } catch (LogicException e) {

        }
    }

    @Test
    public void testUpdateUser() {
    }

    @Test
    public void testBlockUser() {
    }

    @Test
    public void testUnblockUser() {
    }
}