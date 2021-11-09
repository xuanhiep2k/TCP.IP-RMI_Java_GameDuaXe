package control;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import view.TCP_ServerMainFrm;
import general.*;
import java.util.ArrayList;
import model.*;
import server.RMILoginInterface;

public class RMI_PlayerCtr {

    private TCP_ServerMainFrm view;
    private RMILoginInterface loginRO;
    private PlayerInterface PlayerRO;
    private RequestModelInterface RequestRO;
    private FriendInterface FriendRO;
    private RankInterface RankRO;
    private GroupInterface GroupRO;
    private IPAddress serverAddress = new IPAddress("localhost", 9999); //default server address
    private String rmiService = "rmiServer";                            //default server service key

    public RMI_PlayerCtr(TCP_ServerMainFrm view) {
        this.view = view;
    }

    public RMI_PlayerCtr(TCP_ServerMainFrm view, String serverHost, int serverPort, String service) {
        this.view = view;
        serverAddress.setHost(serverHost);
        serverAddress.setPort(serverPort);
        rmiService = service;
    }

    public boolean init() {
        try {
            // get the registry
            Registry registry = LocateRegistry.getRegistry(serverAddress.getHost(), serverAddress.getPort());
            // lookup the remote objects
            loginRO = (RMILoginInterface) (registry.lookup(rmiService));
            PlayerRO = (PlayerInterface) (registry.lookup(rmiService));
            RequestRO = (RequestModelInterface) (registry.lookup(rmiService));
            RankRO = (RankInterface) (registry.lookup(rmiService));
            FriendRO = (FriendInterface) (registry.lookup(rmiService));
            GroupRO = (GroupInterface) (registry.lookup(rmiService));
            view.setServerHost(serverAddress.getHost(), serverAddress.getPort() + "", rmiService);
            view.showMessage("Found the remote objects at the host: " + serverAddress.getHost() + ", port: " + serverAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Error to lookup the remote objects!");
            return false;
        }
        return true;
    }

    public void stop() {
        serverAddress.getPort();
    }

    public String remoteRegisterUser(Player player) {
        try {
            return loginRO.registerUser(player);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Player remoteLogin(Player player) {
        try {
            return loginRO.checkLogin(player);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStatus(String value, int id) {
        try {
            PlayerRO.updateStatus(value, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Player> playersOnline(Player player) {
        try {
            return PlayerRO.playersOnline(player);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean requestFriend(int senderId, int recieverId, String requestname) {
        try {
            return PlayerRO.requestFriend(senderId, recieverId, requestname);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addFriend(int id, int playerid, String name1, String name2) {
        try {
            return PlayerRO.addFriend(id, playerid, name1, name2);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkrequestFriend(int senderId, int recieverId) {
        try {
            return PlayerRO.checkrequestFriend(senderId, recieverId);
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<RequestModel> getRequest() {
        try {
            return RequestRO.getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean waiterOnline(int recieverid) {
        try {
            return RequestRO.waiterOnline(recieverid);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkOnline(String status, int id) {
        try {
            return RequestRO.checkOnline(status, id);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean insertRank(int idplayer, String fullname) {
        try {
            return RankRO.insertRank(idplayer, fullname);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkRank(int idplayer, String fullname) {
        try {
            return RankRO.checkRank(idplayer, fullname);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean rank(int rank) {
        try {
            return RankRO.rank(rank);
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Rank> orderRank() {
        try {
            return RankRO.orderRank();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteRequest(int senderId, int recieverId) {
        try {
            return RequestRO.deleteRequest(senderId, recieverId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkFriend(int id_player1, int id_player2) {
        try {
            return PlayerRO.checkFriend(id_player1, id_player2);
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Friend> listFiend() {
        try {
            return FriendRO.listFiend();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteFriend(int senderId, int recieverId) {
        try {
            return FriendRO.deleteFriend(senderId, recieverId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean createGroup(int idplayer, String namePlayer, String nameGroup, String host) {
        try {
            return GroupRO.createGroup(idplayer, namePlayer, nameGroup, host);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkNameGroup(String nameGroup) {
        try {
            return GroupRO.checkNameGroup(nameGroup);
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Group> listGroup() {
        try {
            return GroupRO.listGroup();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean checkGroup(int idplayer) {
        try {
            return GroupRO.checkGroup(idplayer);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteGroup(String host) {
        try {
            return GroupRO.deleteGroup(host);
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Group> listMember(String nameGroup) {
        try {
            return GroupRO.listMember(nameGroup);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean checkJoin(int idplayer) {
        try {
            return GroupRO.checkJoin(idplayer);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean joinGroup(int idplayer, String namePlayer, String nameGroup, String host) {
        try {
            return GroupRO.joinGroup(idplayer, namePlayer, nameGroup, host);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkJoinApproval(int idplayer, String nameGroup) {
        try {
            return GroupRO.checkJoinApproval(idplayer, nameGroup);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean leaveGroup(int idplayer, String nameGroup) {
        try {
            return GroupRO.leaveGroup(idplayer, nameGroup);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean acceptGroup(int idplayer, String namePlayer, String nameGroup, String host) {
        try {
            return GroupRO.acceptGroup(idplayer, namePlayer, nameGroup, host);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteApproval(String namePlayer) {
        try {
            return GroupRO.deleteApproval(namePlayer);
        } catch (Exception e) {
            return false;
        }
    }
}
