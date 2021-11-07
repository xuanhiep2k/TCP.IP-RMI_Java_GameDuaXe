package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import model.Player;

public interface RMILoginInterface extends Remote {

    public Player checkLogin(Player player) throws RemoteException;

    public String registerUser(Player player) throws RemoteException;
}
