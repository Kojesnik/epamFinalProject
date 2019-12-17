package by.epam.courierPicker.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import by.epam.courierPicker.constant.ParamName;
import by.epam.courierPicker.type.RoleType;

public class WelcomeTag extends TagSupport {

    private String role;
    private String lang;

    public void setRole(String role) {
        this.role = role;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }


    @Override
    public int doStartTag() throws JspException {
        try {
            String result = null;
            if (RoleType.ADMIN.toString().toLowerCase().equals(role)) {
                if (lang.equals(ParamName.LANG_EN)) {
                    result = "Hello admin";
                } else {
                    result = "Привет админ";
                }
            } else if (RoleType.COURIER.toString().toLowerCase().equals(role)){
                if (lang.equals(ParamName.LANG_EN)) {
                    result = "Welcome courier";
                } else {
                    result = "Приветствуем курьер";
                }
            } else if (RoleType.USER.toString().toLowerCase().equals(role)) {
                if (lang.equals(ParamName.LANG_EN)) {
                    result = "Welcome user";
                } else {
                    result = "Приветствуем клиент";
                }
            }
            else {
                if (lang.equals(ParamName.LANG_EN)) {
                    result = "Welcome guest";
                } else {
                    result = "Приветствуем гость";
                }
            }
            pageContext.getOut().write(result);
        } catch (IOException ex) {
            throw new JspException(ex.getMessage());
        }
        return SKIP_BODY;
    }
}
