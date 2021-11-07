package general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Friend;

public interface FriendInterface extends Remote {

    public ArrayList<Friend> listFiend() throws RemoteException;

    public boolean deleteFriend(int senderId, int recieverId) throws RemoteException;
}
