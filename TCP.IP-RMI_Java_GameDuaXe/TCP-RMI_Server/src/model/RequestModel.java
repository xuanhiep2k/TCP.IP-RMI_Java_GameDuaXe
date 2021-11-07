/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author asmaa
 */
public class RequestModel implements Serializable {

    private int senderid;
    private int recieverid;
    private String requestname;
    private String status;

    public RequestModel() {
        super();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRequestname(String requestname) {
        this.requestname = requestname;
    }

    public String getRequestname() {
        return requestname;
    }

    public int getSenderid() {
        return senderid;
    }

    public void setSenderid(int senderid) {
        this.senderid = senderid;
    }

    public int getRecieverid() {
        return recieverid;
    }

    public void setRecieverid(int recieverid) {
        this.recieverid = recieverid;
    }

}
