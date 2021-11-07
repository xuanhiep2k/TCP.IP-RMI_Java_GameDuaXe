package view;

import control.TCP_PlayerCtr;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.IPAddress;
import model.ObjectWrapper;
import model.Player;

public class PlayerConnectToServer extends JFrame implements ActionListener {

    private JMenuBar mnbMain;
    private JMenu mnUser;
    private JMenu mnClient;
    private JMenuItem mniLogin, mniRegister;
    private JMenuItem mniEditClient;

    private JTextField txtServerHost;
    private JTextField txtServerPort;
    private JButton btnConnect;
    private JButton btnDisconnect;
    private JTextArea mainText;
    private TCP_PlayerCtr myControl;
    private Player player;

    public PlayerConnectToServer() {
        super("TCP client view");
        player = new Player();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        mnbMain = new JMenuBar();
        mnUser = new JMenu("Player");
        mniLogin = new JMenuItem("Login");
        mniRegister = new JMenuItem("Register");
        mniLogin.addActionListener(this);
        mniRegister.addActionListener(this);
        mnUser.add(mniLogin);
        mnUser.add(mniRegister);
        mnbMain.add(mnUser);

        mnClient = new JMenu("Help");
        mniEditClient = new JMenuItem("Help player");
        mniEditClient.addActionListener(this);
        mnClient.add(mniEditClient);
        mnbMain.add(mnClient);
        this.setJMenuBar(mnbMain);
        mniLogin.setEnabled(false);
        mniRegister.setEnabled(false);
        mniEditClient.setEnabled(false);

        JLabel lblTitle = new JLabel("Client TCP/IP");
        lblTitle.setFont(new java.awt.Font("Dialog", 1, 20));
        lblTitle.setBounds(new Rectangle(150, 20, 200, 30));
        mainPanel.add(lblTitle, null);

        JLabel lblHost = new JLabel("Server host:");
        lblHost.setBounds(new Rectangle(10, 70, 150, 25));
        mainPanel.add(lblHost, null);

        txtServerHost = new JTextField(50);
        txtServerHost.setBounds(new Rectangle(170, 70, 150, 25));
        mainPanel.add(txtServerHost, null);

        JLabel lblPort = new JLabel("Server port:");
        lblPort.setBounds(new Rectangle(10, 100, 150, 25));
        mainPanel.add(lblPort, null);

        txtServerPort = new JTextField(50);
        txtServerPort.setBounds(new Rectangle(170, 100, 150, 25));
        mainPanel.add(txtServerPort, null);

        btnConnect = new JButton("Connect");
        btnConnect.setBounds(new Rectangle(10, 150, 150, 25));
        btnConnect.addActionListener(this);
        mainPanel.add(btnConnect, null);

        btnDisconnect = new JButton("Disconnect");
        btnDisconnect.setBounds(new Rectangle(170, 150, 150, 25));
        btnDisconnect.addActionListener(this);
        btnDisconnect.setEnabled(false);
        mainPanel.add(btnDisconnect, null);

        JScrollPane jScrollPane1 = new JScrollPane();
        mainText = new JTextArea("");
        jScrollPane1.setBounds(new Rectangle(10, 200, 610, 240));
        mainPanel.add(jScrollPane1, BorderLayout.CENTER);
        jScrollPane1.getViewport().add(mainText, null);
        //mainPanel.add(mainText,null);

        this.setContentPane(mainPanel);
        this.pack();
        this.setSize(new Dimension(640, 480));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (myControl != null) {
                    myControl.closeConnection();
                }
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // TODO Auto-generated method stub
        if (ae.getSource() instanceof JButton) {
            JButton btn = (JButton) ae.getSource();
            if (btn.equals(btnConnect)) {// connect button
                if (!txtServerHost.getText().isEmpty() && (txtServerHost.getText().trim().length() > 0)
                        && !txtServerPort.getText().isEmpty() && (txtServerPort.getText().trim().length() > 0)) {//custom port
                    int port = Integer.parseInt(txtServerPort.getText().trim());
                    myControl = new TCP_PlayerCtr(this, new IPAddress(txtServerHost.getText().trim(), port));

                    //new ClientCtr(this, port);
                } else {
                    myControl = new TCP_PlayerCtr(this);
                }
                if (myControl.openConnection()) {
                    btnDisconnect.setEnabled(true);
                    btnConnect.setEnabled(false);
                    mniLogin.setEnabled(true);
                    mniRegister.setEnabled(true);
                    mniEditClient.setEnabled(true);

                } else {
                    resetClient();
                }
            } else if (btn.equals(btnDisconnect)) {// disconnect button
                resetClient();
            }
        } else if (ae.getSource() instanceof JMenuItem) {
            JMenuItem mni = (JMenuItem) ae.getSource();
            if (mni.equals(mniLogin)) {// login function
                for (ObjectWrapper func : myControl.getActiveFunction()) {
                    if (func.getData() instanceof LoginFrm) {
                        ((LoginFrm) func.getData()).setVisible(true);
                        return;
                    }
                }
                LoginFrm lfv = new LoginFrm(myControl);
                lfv.setVisible(true);
            } else if (mni.equals(mniRegister)) {// register function
                for (ObjectWrapper func : myControl.getActiveFunction()) {
                    if (func.getData() instanceof RegisterFrm) {
                        ((RegisterFrm) func.getData()).setVisible(true);
                        return;
                    }
                }
                RegisterFrm rfv = new RegisterFrm(myControl, player);
                rfv.setVisible(true);
            }
        }
    }

    public void showMessage(String s) {
        mainText.append("\n" + s);
        mainText.setCaretPosition(mainText.getDocument().getLength());
    }

    public void resetClient() {
        if (myControl != null) {
            myControl.closeConnection();
            myControl.getActiveFunction().clear();
            myControl = null;
        }
        btnDisconnect.setEnabled(false);
        btnConnect.setEnabled(true);
        mniLogin.setEnabled(false);
        mniEditClient.setEnabled(false);
        mniRegister.setEnabled(false);
    }

    public static void main(String[] args) {
        PlayerConnectToServer view = new PlayerConnectToServer();
        view.setVisible(true);
    }
}
