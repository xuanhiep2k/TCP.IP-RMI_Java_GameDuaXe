package control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.IPAddress;
import model.ObjectWrapper;
import view.*;

public class TCP_PlayerCtr {

    private Socket mySocket;
    private PlayerConnectToServer view;
    private ClientListening myListening;                            // thread to listen the data from the server
    private ArrayList<ObjectWrapper> myFunction;                  // list of active client functions
    private IPAddress serverAddress = new IPAddress("localhost", 8888);  // default server host and port

    private PlayerMainFrm playerView;

    public TCP_PlayerCtr(PlayerConnectToServer view) {
        super();
        this.view = view;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public TCP_PlayerCtr(PlayerMainFrm playerView) {
        super();
        this.playerView = playerView;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public TCP_PlayerCtr(PlayerMainFrm playerView, IPAddress serverAddr) {
        super();
        this.playerView = playerView;
        this.serverAddress = serverAddr;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public TCP_PlayerCtr(PlayerConnectToServer view, IPAddress serverAddr) {
        super();
        this.view = view;
        this.serverAddress = serverAddr;
        myFunction = new ArrayList<ObjectWrapper>();
    }

    public boolean openConnection() {
        try {
            mySocket = new Socket(serverAddress.getHost(), serverAddress.getPort());
            myListening = new ClientListening();
            myListening.start();
            view.showMessage("Connected to the server at host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error when connecting to the server!");
            return false;
        }
        return true;
    }

    public boolean sendData(Object obj) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
            oos.writeObject(obj);

        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error when sending data to the server!");
            return false;
        }
        return true;
    }

    public Object receiveData() {
        Object result = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
            result = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error when receiving data from the server!");
            return null;
        }
        return result;
    }

    public boolean closeConnection() {
        try {
            if (myListening != null) {
                myListening.stop();
            }
            if (mySocket != null) {
                mySocket.close();
                view.showMessage("Disconnected from the server!");
            }
            myFunction.clear();
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error when disconnecting from the server!");
            return false;
        }
        return true;
    }

    public ArrayList<ObjectWrapper> getActiveFunction() {
        return myFunction;
    }

    class ClientListening extends Thread {

        public ClientListening() {
            super();
        }

        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    Object obj = ois.readObject();
                    if (obj instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) obj;
                        if (data.getPerformative() == ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER) {
                            view.showMessage("Number of client connecting: " + data.getData());
                        } else {

                            for (int i = 0; i < myFunction.size(); i++) {
                                ObjectWrapper ft = myFunction.get(i);
                                int temp = myFunction.get(i).getPerformative();
                                if (temp == data.getPerformative()) {
                                    switch (data.getPerformative()) {
                                        case ObjectWrapper.REPLY_LOGIN_USER:
                                            LoginFrm loginView = (LoginFrm) ft.getData();
                                            loginView.receivedDataProcessing(data);
                                            // loginView.checkRank(data);
                                            break;
                                        case ObjectWrapper.REPLY_REGISTER_USER:
                                            RegisterFrm ecv = (RegisterFrm) ft.getData();
                                            ecv.receivedDataProcessing(data);
                                            break;
                                        case ObjectWrapper.REPLY_UPDATE_PLAYER_STATUS:
                                            break;
                                        case ObjectWrapper.REPLY_PLAYERS_ONLINE:
                                            PlayerMainFrm pmf = (PlayerMainFrm) ft.getData();
                                            pmf.tbl_onlineFriend(data);
                                            break;
                                        case ObjectWrapper.REPLY_ORDER_RANK:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.tbl_rankFriend(data);
                                            break;
                                        case ObjectWrapper.REPLY_REQUEST_FRIEND:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.RequestFriend(data);
                                            break;
                                        case ObjectWrapper.REPLY_CHECK_REQUEST_FRIEND:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.checkRequestFriend(data);
                                            break;
                                        case ObjectWrapper.REPLY_GET_REQUEST:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.tbl_waitFriend(data);
                                            break;
                                        case ObjectWrapper.REPLY_ADD_FRIEND:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.checkAccept(data);
                                            break;
                                        case ObjectWrapper.REPLY_CHECK_FRIEND:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.checkAdd(data);
                                            break;
                                        case ObjectWrapper.REPLY_DELETE_REQUEST:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.deny(data);
                                            break;
                                        case ObjectWrapper.REPLY_LIST_FRIEND:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.tbl_listFirend(data);
                                            break;
                                        case ObjectWrapper.REPLY_DELETE_FRIEND:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.deleFriend(data);
                                            break;
                                        case ObjectWrapper.REPLY_CREATE_GROUP:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.createGroup(data);
                                            break;
                                        case ObjectWrapper.REPLY_LIST_GROUP:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.tbl_Group(data);
                                            break;
                                        case ObjectWrapper.REPLY_CHECK_GROUP:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.checkGroup(data);
                                            break;
                                        case ObjectWrapper.REPLY_DELETE_GROUP:
                                            pmf = (PlayerMainFrm) ft.getData();
                                            pmf.deleteGroup(data);
                                            break;
                                        case ObjectWrapper.REPLY_LIST_MEMBER:
                                            GroupDetailFrm gdf = (GroupDetailFrm) ft.getData();
                                            gdf.tbl_listMember(data);
                                            break;
                                        case ObjectWrapper.REPLY_CHECK_JOIN:
                                            gdf = (GroupDetailFrm) ft.getData();
                                            gdf.checkJoin(data);
                                            break;
                                        case ObjectWrapper.REPLY_JOIN_GROUP:
                                            gdf = (GroupDetailFrm) ft.getData();
                                            gdf.join(data);
                                            break;
                                        case ObjectWrapper.REPLY_CHECK_JOIN_APPROVAL:
                                            gdf = (GroupDetailFrm) ft.getData();
                                            gdf.checkJoinApproval(data);
                                            break;
                                        case ObjectWrapper.REPLY_CHECK_LEAVE:
                                            gdf = (GroupDetailFrm) ft.getData();
                                            gdf.checkLeave(data);
                                            break;
                                        case ObjectWrapper.REPLY_LEAVE_GROUP:
                                            gdf = (GroupDetailFrm) ft.getData();
                                            gdf.leaveGroup(data);
                                            break;
                                        case ObjectWrapper.REPLY_LIST_APPROVAL:
                                            gdf = (GroupDetailFrm) ft.getData();
                                            gdf.listApproval(data);
                                            break;
                                        case ObjectWrapper.REPLY_ACCEPT_GROUP:
                                            gdf = (GroupDetailFrm) ft.getData();
                                            gdf.acceptGroup(data);
                                            break;
                                    }
                                }
                            }
                            if (data.getPerformative() != 21) {
                                view.showMessage("Received an object: " + data.getPerformative());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                view.showMessage("Error when receiving data from the server!");
            }
        }
    }
}
