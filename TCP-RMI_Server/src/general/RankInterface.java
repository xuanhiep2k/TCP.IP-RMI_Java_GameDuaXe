package general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Rank;

public interface RankInterface extends Remote {

    public boolean insertRank(int idplayer, String fullname) throws RemoteException;

    public boolean checkRank(int idplayer, String fullname) throws RemoteException;

    public boolean rank(int rank) throws RemoteException;

    public ArrayList<Rank> orderRank() throws RemoteException;

}
