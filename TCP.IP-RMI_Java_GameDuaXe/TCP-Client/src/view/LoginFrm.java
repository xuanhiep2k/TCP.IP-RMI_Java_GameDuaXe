package view;

import control.TCP_PlayerCtr;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import model.ObjectWrapper;

import model.Player;

public class LoginFrm extends JFrame implements ActionListener {

    private ArrayList<Player> listPlayer;
    private Player player;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnChange;
    private TCP_PlayerCtr mySocket;

    public LoginFrm(TCP_PlayerCtr socket) {
        super("TCP Login MVC");
        mySocket = socket;
        player = new Player();

        listPlayer = new ArrayList<Player>();
        txtUsername = new JTextField(15);
        txtPassword = new JPasswordField(15);
        txtPassword.setEchoChar('*');
        btnLogin = new JButton("Login");
        btnChange = new JButton("Change");

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Username:"));
        content.add(txtUsername);
        content.add(new JLabel("Password:"));
        content.add(txtPassword);
        content.add(btnLogin);
        btnLogin.addActionListener(this);
        content.add(btnChange);
        btnChange.addActionListener(this);

        this.setContentPane(content);
        this.pack();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LOGIN_USER, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_RANK, this));

    }

    public void actionPerformed(ActionEvent e) {
        if ((e.getSource() instanceof JButton) && (((JButton) e.getSource()).equals(btnLogin))) {
            //pack the entity
            player.setUsername(txtUsername.getText());
            player.setPassword(txtPassword.getText());
            //sending data
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LOGIN_USER, player));

        }
    }

    public void receivedDataProcessing(ObjectWrapper data) {
        if (data.getData() instanceof Player) {
            JOptionPane.showMessageDialog(this, "Login succesfully!");
            player = (Player) data.getData();

            mySocket.sendData(new ObjectWrapper(ObjectWrapper.CHECK_RANK, player));
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.ORDER_RANK, player));
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.PLAYERS_ONLINE, player));
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_REQUEST, player));
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_FRIEND, player));
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.LIST_GROUP, player));

            if (player != null) {
                //create new instance
                player.setStatus("1");
                (new PlayerMainFrm(mySocket, player)).setVisible(true);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username and/or password!");
        }
    }

}
