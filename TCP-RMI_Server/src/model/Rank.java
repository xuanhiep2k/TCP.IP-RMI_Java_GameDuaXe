/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author NONAME
 */
public class Rank implements Serializable {

    private int idrank, idplayer, rank;
    private String fullname;
    private float core;
    private String status;

    public Rank() {
        super();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getIdrank() {
        return idrank;
    }

    public int getIdplayer() {
        return idplayer;
    }

    public int getRank() {
        return rank;
    }

    public String getFullname() {
        return fullname;
    }

    public float getCore() {
        return core;
    }

    public void setIdrank(int idrank) {
        this.idrank = idrank;
    }

    public void setIdplayer(int idplayer) {
        this.idplayer = idplayer;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setCore(float core) {
        this.core = core;
    }

}
