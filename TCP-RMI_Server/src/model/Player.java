package model;

import java.io.Serializable;

public class Player implements Serializable {

    private static final long serialVersionUID = 20210811010L;
    private int id_player;
    private String username;
    private String password;
    private String fullname;
    private String gender;
    private int core;
    private String email;
    private String position;
    private String status;

    public Player() {
        super();
    }

    public Player(int id_player, String username, String password, String fullname, String gender, int core, String email, String position, String status) {
        this.id_player = id_player;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.gender = gender;
        this.core = core;
        this.email = email;
        this.position = position;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setId_player(int id_player) {
        this.id_player = id_player;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_player() {
        return id_player;
    }

    public int getIdplayer() {
        return id_player;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public int getCore() {
        return core;
    }

    public void setIdplayer(int id_player) {
        this.id_player = id_player;
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

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
