package model;

import java.io.Serializable;

public class ObjectWrapper implements Serializable {

    private static final long serialVersionUID = 20210811011L;
    public static final int SERVER_INFORM_CLIENT_NUMBER = 1;
    public static final int LOGIN_USER = 2;
    public static final int REPLY_LOGIN_USER = 3;
    public static final int REGISTER_USER = 4;
    public static final int REPLY_REGISTER_USER = 5;
    public static final int UPDATE_PLAYER_STATUS = 6;
    public static final int REPLY_UPDATE_PLAYER_STATUS = 7;
    public static final int PLAYERS_ONLINE = 8;
    public static final int REPLY_PLAYERS_ONLINE = 9;
    public static final int REQUEST_FRIEND = 10;
    public static final int REPLY_REQUEST_FRIEND = 11;
    public static final int ADD_FRIEND = 12;
    public static final int REPLY_ADD_FRIEND = 13;
    public static final int CHECK_REQUEST_FRIEND = 14;
    public static final int REPLY_CHECK_REQUEST_FRIEND = 15;
    public static final int GET_REQUEST = 16;
    public static final int REPLY_GET_REQUEST = 17;
    public static final int WAITER_ONLINE = 18;
    public static final int REPLY_WAITER_ONLINE = 19;
    public static final int CHECK_ONLINE = 20;
    public static final int REPLY_CHECK_ONLINE = 21;
    public static final int CHECK_RANK = 24;
    public static final int REPLY_CHECK_RANK = 25;
    public static final int RANK = 26;
    public static final int REPLY_RANK = 27;
    public static final int ORDER_RANK = 28;
    public static final int REPLY_ORDER_RANK = 29;
    public static final int DELETE_REQUEST = 30;
    public static final int REPLY_DELETE_REQUEST = 31;
    public static final int CHECK_FRIEND = 32;
    public static final int REPLY_CHECK_FRIEND = 33;
    public static final int LIST_FRIEND = 34;
    public static final int REPLY_LIST_FRIEND = 35;
    public static final int DELETE_FRIEND = 36;
    public static final int REPLY_DELETE_FRIEND = 37;
    public static final int CREATE_GROUP = 38;
    public static final int REPLY_CREATE_GROUP = 39;
    public static final int LIST_GROUP = 40;
    public static final int REPLY_LIST_GROUP = 41;
    public static final int CHECK_GROUP = 42;
    public static final int REPLY_CHECK_GROUP = 43;
    public static final int DELETE_GROUP = 44;
    public static final int REPLY_DELETE_GROUP = 45;
    public static final int LIST_MEMBER = 46;
    public static final int REPLY_LIST_MEMBER = 47;
    public static final int CHECK_JOIN = 48;
    public static final int REPLY_CHECK_JOIN = 49;
    public static final int JOIN_GROUP = 50;
    public static final int REPLY_JOIN_GROUP = 51;
    public static final int CHECK_JOIN_APPROVAL = 52;
    public static final int REPLY_CHECK_JOIN_APPROVAL = 53;

    private int performative;
    private Object data;

    public ObjectWrapper() {
        super();
    }

    public ObjectWrapper(int performative, Object data) {
        super();
        this.performative = performative;
        this.data = data;
    }

    public int getPerformative() {
        return performative;
    }

    public void setPerformative(int performative) {
        this.performative = performative;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
