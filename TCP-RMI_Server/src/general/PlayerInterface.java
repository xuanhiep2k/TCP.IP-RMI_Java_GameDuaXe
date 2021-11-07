package general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Player;

public interface PlayerInterface extends Remote {

    public void updateStatus(String value, int id) throws RemoteException;

    public ArrayList<Player> playersOnline(Player player) throws RemoteException;

    public boolean requestFriend(int senderId, int recieverId, String requestname) throws RemoteException;

    public boolean addFriend(int id, int playerid, String name1, String name2) throws RemoteException;

    public boolean checkrequestFriend(int senderId, int recieverId) throws RemoteException;

    public boolean checkFriend(int id_player1, int id_player2) throws RemoteException;

}
