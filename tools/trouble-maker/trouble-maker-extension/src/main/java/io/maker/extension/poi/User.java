package io.maker.extension.poi;

public class User {
    protected String username;
    protected String password;
    protected String nickname;

    public User() {
        super();
    }

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("username=");
        sb.append(username);
        sb.append(";password=");
        sb.append(password);
        sb.append(";nickname=");
        sb.append(nickname);
        return sb.toString();
    }
}
