package pipl.ynov.com.schoolpierre.Models;

public class User {
    private String login;
    private String password;
    private String Auth_token;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuth_token() {
        return Auth_token;
    }

    public void setAuth_token(String auth_token) {
        Auth_token = auth_token;
    }
}
