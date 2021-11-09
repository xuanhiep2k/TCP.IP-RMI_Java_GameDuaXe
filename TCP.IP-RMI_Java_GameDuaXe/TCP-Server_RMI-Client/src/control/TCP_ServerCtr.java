package control;

import dao.PlayerDAO;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import model.Friend;
import model.Group;
import model.IPAddress;
import model.ObjectWrapper;
import model.Player;
import model.Rank;
import model.RequestModel;
import view.TCP_ServerMainFrm;

public class TCP_ServerCtr {

    private TCP_ServerMainFrm view;
    private ServerSocket myServer;
    private ServerListening myListening;
    private ArrayList<ServerProcessing> myProcess;
    private IPAddress myAddress = new IPAddress("localhost", 8888);  //default server host and port
    private RMI_PlayerCtr myRemoteObject;

    public TCP_ServerCtr(TCP_ServerMainFrm view, RMI_PlayerCtr ro) {
        myRemoteObject = ro;
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        openServer();

    }

    public TCP_ServerCtr(TCP_ServerMainFrm view, int serverPort) {
        myProcess = new ArrayList<ServerProcessing>();
        this.view = view;
        myAddress.setPort(serverPort);
        openServer();
    }

    private void openServer() {
        try {
            myServer = new ServerSocket(myAddress.getPort());
            myListening = new ServerListening();
            myListening.start();
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfor(myAddress);
            //System.out.println("server started!");
            view.showMessage("TCP server is running at the port " + myAddress.getPort() + "...");
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    public void stopServer() {
        try {
            for (ServerProcessing sp : myProcess) {
                sp.stop();
            }
            myListening.stop();
            myServer.close();
            view.showMessage("TCP server is stopped!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publicClientNumber() {
        ObjectWrapper data = new ObjectWrapper(ObjectWrapper.SERVER_INFORM_CLIENT_NUMBER, myProcess.size());
        for (ServerProcessing sp : myProcess) {
            sp.sendData(data);
        }
    }

    /**
     * The class to listen the connections from client, avoiding the blocking of
     * accept connection
     *
     */
    class ServerListening extends Thread {

        public ServerListening() {
            super();
        }

        public void run() {
            view.showMessage("server is listening... ");
            try {
                while (true) {
                    Socket clientSocket = myServer.accept();
                    ServerProcessing sp = new ServerProcessing(clientSocket);
                    sp.start();
                    myProcess.add(sp);
                    view.showMessage("Number of client connecting to the server: " + myProcess.size());
                    publicClientNumber();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The class to treat the requirement from client
     *
     */
    class ServerProcessing extends Thread {

        private Socket mySocket;
        //private ObjectInputStream ois;
        //private ObjectOutputStream oos;

        public ServerProcessing(Socket s) {
            super();
            mySocket = s;
        }

        public void sendData(Object obj) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
                oos.writeObject(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                while (true) {
                    ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(mySocket.getOutputStream());
                    Object o = ois.readObject();
                    if (o instanceof ObjectWrapper) {
                        ObjectWrapper data = (ObjectWrapper) o;
                        switch (data.getPerformative()) {
                            case ObjectWrapper.LOGIN_USER:
                                Player player = (Player) data.getData();
                                if (myRemoteObject.remoteLogin(player) instanceof Player) {
                                    player = myRemoteObject.remoteLogin(player);
                                    myRemoteObject.updateStatus("1", player.getIdplayer());
                                    if (!myRemoteObject.checkRank(player.getIdplayer(), player.getFullname())) {
                                        myRemoteObject.insertRank(player.getIdplayer(), player.getFullname());
                                    }
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, player));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, null));
                                }

                                break;
                            case ObjectWrapper.REGISTER_USER:
                                player = (Player) data.getData();
                                if (myRemoteObject.remoteRegisterUser(player).equals("ok")) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER_USER, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REGISTER_USER, "false"));
                                }
                                break;
                            case ObjectWrapper.UPDATE_PLAYER_STATUS:
                                player = (Player) data.getData();
                                myRemoteObject.updateStatus("0", player.getIdplayer());
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_UPDATE_PLAYER_STATUS, "ok"));
                                break;
                            case ObjectWrapper.PLAYERS_ONLINE:
                                player = (Player) data.getData();
                                ArrayList<Player> result = myRemoteObject.playersOnline(player);
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_PLAYERS_ONLINE, result));
                                data = new ObjectWrapper(ObjectWrapper.REPLY_PLAYERS_ONLINE, result);
                                for (ServerProcessing sp : myProcess) {
                                    sp.sendData(data);
                                }
                                break;
                            case ObjectWrapper.CHECK_RANK:
                                player = (Player) data.getData();
                                if (!myRemoteObject.checkRank(player.getIdplayer(), player.getFullname())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_RANK, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_RANK, "false"));
                                }
                                break;
                            case ObjectWrapper.ORDER_RANK:
                                ArrayList<Rank> resultRank = myRemoteObject.orderRank();
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ORDER_RANK, resultRank));
                                data = new ObjectWrapper(ObjectWrapper.REPLY_ORDER_RANK, resultRank);
                                for (ServerProcessing sp : myProcess) {
                                    sp.sendData(data);
                                }
                                break;
                            case ObjectWrapper.REQUEST_FRIEND:
                                RequestModel request = (RequestModel) data.getData();
                                if (!myRemoteObject.checkrequestFriend(request.getSenderid(), request.getRecieverid())) {
                                    if (myRemoteObject.requestFriend(request.getSenderid(), request.getRecieverid(), request.getRequestname())) {
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_FRIEND, "ok"));
                                        myRemoteObject.updateStatus("1", request.getSenderid());
                                    } else {
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_FRIEND, "false"));
                                    }
                                }
                                break;
                            case ObjectWrapper.CHECK_REQUEST_FRIEND:
                                request = (RequestModel) data.getData();
                                if (myRemoteObject.checkrequestFriend(request.getSenderid(), request.getRecieverid())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_REQUEST_FRIEND, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_REQUEST_FRIEND, "false"));
                                }
                                break;
                            case ObjectWrapper.GET_REQUEST:
                                ArrayList<RequestModel> listRequest = myRemoteObject.getRequest();
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_GET_REQUEST, listRequest));
                                data = new ObjectWrapper(ObjectWrapper.REPLY_GET_REQUEST, listRequest);
                                for (ServerProcessing sp : myProcess) {
                                    sp.sendData(data);
                                }
                                break;
                            case ObjectWrapper.ADD_FRIEND:
                                Friend friend = (Friend) data.getData();
                                if (myRemoteObject.addFriend(friend.getIdPlayer1(), friend.getIdPlayer2(), friend.getNamePlayer1(), friend.getNamePlayer2())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND, "ok"));
                                    myRemoteObject.updateStatus("1", friend.getIdPlayer1());
                                    myRemoteObject.updateStatus("1", friend.getIdPlayer2());
                                    myRemoteObject.deleteRequest(friend.getIdPlayer1(), friend.getIdPlayer2());
                                    myRemoteObject.deleteRequest(friend.getIdPlayer2(), friend.getIdPlayer1());
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND, "false"));
                                }
                                break;
                            case ObjectWrapper.CHECK_FRIEND:
                                request = (RequestModel) data.getData();
                                if (myRemoteObject.checkFriend(request.getSenderid(), request.getRecieverid())
                                        || myRemoteObject.checkFriend(request.getRecieverid(), request.getSenderid())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_FRIEND, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_FRIEND, "false"));
                                }
                                break;
                            case ObjectWrapper.DELETE_REQUEST:
                                request = (RequestModel) data.getData();
                                if (myRemoteObject.deleteRequest(request.getSenderid(), request.getRecieverid())
                                        || myRemoteObject.deleteRequest(request.getRecieverid(), request.getSenderid())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_REQUEST, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_REQUEST, "false"));
                                }
                                break;
                            case ObjectWrapper.LIST_FRIEND:
                                ArrayList<Friend> listFriend = myRemoteObject.listFiend();
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LIST_FRIEND, listFriend));
                                data = new ObjectWrapper(ObjectWrapper.REPLY_LIST_FRIEND, listFriend);
                                for (ServerProcessing sp : myProcess) {
                                    sp.sendData(data);
                                }
                                break;
                            case ObjectWrapper.DELETE_FRIEND:
                                friend = (Friend) data.getData();
                                if (myRemoteObject.deleteFriend(friend.getIdPlayer1(), friend.getIdPlayer2())
                                        || myRemoteObject.deleteFriend(friend.getIdPlayer2(), friend.getIdPlayer1())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_FRIEND, "ok"));
                                }
                                break;
                            case ObjectWrapper.CREATE_GROUP:
                                Group group = (Group) data.getData();
                                if (!myRemoteObject.checkNameGroup(group.getNameGroup())) {
                                    if (myRemoteObject.createGroup(group.getIdplayer(), group.getNamePlayer(), group.getNameGroup(), group.getHost())) {
                                        oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP, "ok"));
                                    }
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP, "false"));
                                }
                                break;
                            case ObjectWrapper.LIST_GROUP:
                                ArrayList<Group> listGroup = myRemoteObject.listGroup();
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LIST_GROUP, listGroup));
                                data = new ObjectWrapper(ObjectWrapper.REPLY_LIST_GROUP, listGroup);
                                for (ServerProcessing sp : myProcess) {
                                    sp.sendData(data);
                                }
                                break;
                            case ObjectWrapper.CHECK_GROUP:
                                player = (Player) data.getData();
                                if (new PlayerDAO().checkGroup(player.getIdplayer())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_GROUP, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_GROUP, "false"));
                                }
                                break;
                            case ObjectWrapper.DELETE_GROUP:
                                group = (Group) data.getData();
                                if (myRemoteObject.deleteGroup(group.getHost())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_GROUP, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_GROUP, "false"));
                                }
                                break;
                            case ObjectWrapper.LIST_MEMBER:
                                group = (Group) data.getData();
                                listGroup = myRemoteObject.listMember(group.getNameGroup());
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LIST_MEMBER, listGroup));
                                data = new ObjectWrapper(ObjectWrapper.REPLY_LIST_MEMBER, listGroup);
                                for (ServerProcessing sp : myProcess) {
                                    sp.sendData(data);
                                }
                                break;
                            case ObjectWrapper.CHECK_JOIN:
                                player = (Player) data.getData();
                                if (new PlayerDAO().checkJoin(player.getId_player())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_JOIN, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_JOIN, "false"));
                                }
                                break;
                            case ObjectWrapper.JOIN_GROUP:
                                group = (Group) data.getData();
                                if (myRemoteObject.joinGroup(group.getIdplayer(), group.getNamePlayer(), group.getNameGroup(), group.getHost())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_GROUP, "ok"));

                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_GROUP, "false"));
                                }
                                break;
                            case ObjectWrapper.CHECK_JOIN_APPROVAL:
                                group = (Group) data.getData();
                                if (new PlayerDAO().checkJoinApproval(group.getIdplayer(), group.getNameGroup())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_JOIN_APPROVAL, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_JOIN_APPROVAL, "false"));
                                }
                                break;
                            case ObjectWrapper.CHECK_LEAVE:
                                group = (Group) data.getData();
                                if (new PlayerDAO().checkLeave(group.getIdplayer(), group.getNameGroup())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_LEAVE, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_LEAVE, "false"));
                                }
                                break;
                            case ObjectWrapper.LEAVE_GROUP:
                                group = (Group) data.getData();
                                if (myRemoteObject.leaveGroup(group.getIdplayer(), group.getNameGroup())) {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_GROUP, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_GROUP, "false"));
                                }
                                break;
                            case ObjectWrapper.LIST_APPROVAL:
                                group = (Group) data.getData();
                                listGroup = new PlayerDAO().listApproval(group.getHost());
                                oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_LIST_APPROVAL, listGroup));
                                data = new ObjectWrapper(ObjectWrapper.REPLY_LIST_APPROVAL, listGroup);
                                for (ServerProcessing sp : myProcess) {
                                    sp.sendData(data);
                                }
                                break;
                            case ObjectWrapper.ACCEPT_GROUP:
                                group = (Group) data.getData();
                                if (myRemoteObject.acceptGroup(group.getIdplayer(), group.getNamePlayer(), group.getNameGroup(), group.getHost())) {
                                    myRemoteObject.deleteApproval(group.getNamePlayer());
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_GROUP, "ok"));
                                } else {
                                    oos.writeObject(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_GROUP, "false"));
                                }
                                break;
                        }
                    }
                    //ois.reset();
                    //oos.reset();
                }
            } catch (EOFException | SocketException e) {
                //e.printStackTrace();
                myProcess.remove(this);
                view.showMessage("Number of client connecting to the server: " + myProcess.size());
                publicClientNumber();

                try {
                    mySocket.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                this.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
