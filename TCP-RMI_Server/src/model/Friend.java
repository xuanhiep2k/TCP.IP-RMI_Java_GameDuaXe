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
public class Friend implements Serializable {

    private int id, idPlayer1, idPlayer2;
    private String namePlayer1, namePlayer2, stt1, stt2;

    public Friend() {
        super();
    }

    public void setStt1(String stt1) {
        this.stt1 = stt1;
    }

    public void setStt2(String stt2) {
        this.stt2 = stt2;
    }

    public String getStt1() {
        return stt1;
    }

    public String getStt2() {
        return stt2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdPlayer1(int idPlayer1) {
        this.idPlayer1 = idPlayer1;
    }

    public void setIdPlayer2(int idPlayer2) {
        this.idPlayer2 = idPlayer2;
    }

    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    public int getId() {
        return id;
    }

    public int getIdPlayer1() {
        return idPlayer1;
    }

    public int getIdPlayer2() {
        return idPlayer2;
    }

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

}
