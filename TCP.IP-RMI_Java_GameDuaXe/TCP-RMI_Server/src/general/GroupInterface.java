package general;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import model.Group;

public interface GroupInterface extends Remote {

    public boolean createGroup(int idplayer, String namePlayer, String nameGroup, String host) throws RemoteException;

    public boolean checkNameGroup(String nameGroup) throws RemoteException;

    public boolean checkGroup(int idplayer) throws RemoteException;

    public boolean checkJoin(int idplayer) throws RemoteException;

    public boolean checkJoinApproval(int idplayer, String nameGroup) throws RemoteException;

    public boolean acceptGroup(int idplayer, String namePlayer, String nameGroup, String host) throws RemoteException;

    public boolean deleteApproval(String namePlayer) throws RemoteException;

    public boolean leaveGroup(int idplayer, String nameGroup) throws RemoteException;

    public boolean joinGroup(int idplayer, String namePlayer, String nameGroup, String host) throws RemoteException;

    public boolean deleteGroup(String host) throws RemoteException;

    public ArrayList<Group> listGroup() throws RemoteException;

    public ArrayList<Group> listMember(String nameGroup) throws RemoteException;
}
