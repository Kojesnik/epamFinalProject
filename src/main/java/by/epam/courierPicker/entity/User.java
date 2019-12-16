package by.epam.courierPicker.entity;

import by.epam.courierPicker.type.RoleType;

public class User extends Entity{

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private RoleType role;
    private String state;

    public User() {}

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Builder newBuilder() {
        return new User.Builder();
    }

    public static class Builder {

        private User user;

        public Builder() {
            user = new User();
        }

        public Builder buildId(int id) {
            user.setId(id);
            return this;
        }

        public Builder buildLogin(String login) {
            user.setLogin(login);
            return this;
        }

        public Builder buildFirstName(String firstName) {
            user.setFirstName(firstName);
            return this;
        }

        public Builder buildLastName(String lastName) {
            user.setLastName(lastName);
            return this;
        }

        public Builder buildEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder buildPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder buildRole(RoleType role) {
            user.setRole(role);
            return this;
        }

        public Builder buildState(String state) {
            user.setState(state);
            return this;
        }

        public User build() {
            return user;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
