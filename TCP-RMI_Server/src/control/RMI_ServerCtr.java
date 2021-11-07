package control;

import dao.PlayerDAO;
import general.FriendInterface;
import general.GroupInterface;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import model.IPAddress;
import view.RMI_ServerMainFrm;
import general.PlayerInterface;
import general.RankInterface;
import general.RequestModelInterface;
import model.Friend;
import model.Group;
import model.Player;
import model.Rank;
import model.RequestModel;
import server.RMILoginInterface;

public class RMI_ServerCtr extends UnicastRemoteObject implements RMILoginInterface, PlayerInterface,
        RequestModelInterface, RankInterface, FriendInterface, GroupInterface {

    private IPAddress myAddress = new IPAddress("localhost", 9999);     // default server host/port
    private Registry registry;
    private RMI_ServerMainFrm view;
    private String rmiService = "rmiServer";    // default rmi service key

    public RMI_ServerCtr(RMI_ServerMainFrm view) throws RemoteException {
        this.view = view;
    }

    public RMI_ServerCtr() throws RemoteException {
        super();
    }

    public RMI_ServerCtr(int port, String service) throws RemoteException {
        myAddress.setPort(port);
        this.rmiService = service;
    }

    public RMI_ServerCtr(RMI_ServerMainFrm view, int port, String service) throws RemoteException {
        this.view = view;
        myAddress.setPort(port);
        this.rmiService = service;
    }

    public void start() throws RemoteException {
        // registry this to the localhost
        try {
            try {
                //create new one
                registry = LocateRegistry.createRegistry(myAddress.getPort());
            } catch (ExportException e) {//the Registry exists, get it
                registry = LocateRegistry.getRegistry(myAddress.getPort());
            }
            registry.rebind(rmiService, this);
            myAddress.setHost(InetAddress.getLocalHost().getHostAddress());
            view.showServerInfo(myAddress, rmiService);
            view.showMessage("The RIM has registered the service key: " + rmiService + ", at the port: " + myAddress.getPort());
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws RemoteException {
        // unbind the service
        try {
            if (registry != null) {
                registry.unbind(rmiService);
                UnicastRemoteObject.unexportObject(this, true);
            }
            view.showMessage("The RIM has unbinded the service key: " + rmiService + ", at the port: " + myAddress.getPort());
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Player checkLogin(Player player) throws RemoteException {
        return new PlayerDAO().checkLogin(player);
    }

    @Override
    public String registerUser(Player player) throws RemoteException {
        return new PlayerDAO().registerUser(player);
    }

    @Override
    public void updateStatus(String value, int id) throws RemoteException {
        new PlayerDAO().updateStatus(value, id);
    }

    @Override
    public ArrayList<Player> playersOnline(Player player) throws RemoteException {
        return new PlayerDAO().playersOnline(player);
    }

    @Override
    public boolean addFriend(int id, int playerid, String name1, String name2) throws RemoteException {
        return new PlayerDAO().addFriend(id, playerid, name1, name2);
    }

    @Override
    public boolean requestFriend(int senderId, int recieverId, String requestname) throws RemoteException {
        return new PlayerDAO().requestFriend(senderId, recieverId, requestname);
    }

    @Override
    public boolean checkrequestFriend(int senderId, int recieverId) throws RemoteException {
        return new PlayerDAO().checkrequestFriend(senderId, recieverId);
    }

    @Override
    public ArrayList<RequestModel> getRequest() throws RemoteException {
        return new PlayerDAO().getRequest();
    }

    @Override
    public boolean waiterOnline(int recieverid) throws RemoteException {
        return new PlayerDAO().waiterOnline(recieverid);
    }

    @Override
    public boolean checkOnline(String status, int id) throws RemoteException {
        return new PlayerDAO().checkOnline(status, id);
    }

    @Override
    public boolean insertRank(int idplayer, String fullname) throws RemoteException {
        return new PlayerDAO().insertRank(idplayer, fullname);
    }

    @Override
    public boolean checkRank(int idplayer, String fullname) throws RemoteException {
        return new PlayerDAO().checkRank(idplayer, fullname);
    }

    @Override
    public boolean rank(int rank) throws RemoteException {
        return new PlayerDAO().rank(rank);
    }

    public ArrayList<Rank> orderRank() throws RemoteException {
        return new PlayerDAO().orderRank();
    }

    @Override
    public boolean deleteRequest(int senderId, int recieverId) throws RemoteException {
        return new PlayerDAO().deleteRequest(senderId, recieverId);
    }

    @Override
    public boolean checkFriend(int id_player1, int id_player2) throws RemoteException {
        return new PlayerDAO().checkFriend(id_player1, id_player2);
    }

    @Override
    public ArrayList<Friend> listFiend() throws RemoteException {
        return new PlayerDAO().listFiend();
    }

    @Override
    public boolean deleteFriend(int senderId, int recieverId) throws RemoteException {
        return new PlayerDAO().deleteFriend(senderId, recieverId);
    }

    @Override
    public boolean createGroup(int idplayer, String namePlayer, String nameGroup, String host) throws RemoteException {
        return new PlayerDAO().createGroup(idplayer, namePlayer, nameGroup, host);
    }

    @Override
    public boolean checkNameGroup(String nameGroup) throws RemoteException {
        return new PlayerDAO().checkNameGroup(nameGroup);
    }

    @Override
    public ArrayList<Group> listGroup() throws RemoteException {
        return new PlayerDAO().listGroup();
    }

    @Override
    public boolean checkGroup(int idplayer) throws RemoteException {
        return new PlayerDAO().checkGroup(idplayer);
    }

    @Override
    public boolean deleteGroup(String host) throws RemoteException {
        return new PlayerDAO().deleteGroup(host);
    }

    @Override
    public ArrayList<Group> listMember(String nameGroup) throws RemoteException {
        return new PlayerDAO().listMember(nameGroup);
    }

    @Override
    public boolean checkJoin(int idplayer) throws RemoteException {
        return new PlayerDAO().checkJoin(idplayer);
    }

    @Override
    public boolean joinGroup(int idplayer, String namePlayer, String nameGroup, String host) throws RemoteException {
        return new PlayerDAO().joinGroup(idplayer, namePlayer, nameGroup, host);
    }

    @Override
    public boolean checkJoinApproval(int idplayer, String nameGroup) throws RemoteException {
        return new PlayerDAO().checkJoinApproval(idplayer, nameGroup);
    }

}
