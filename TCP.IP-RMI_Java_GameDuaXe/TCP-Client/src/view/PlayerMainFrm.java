/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.TCP_PlayerCtr;

import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Friend;
import model.Group;
import model.ObjectWrapper;

import model.Player;
import model.Rank;
import model.RequestModel;

/**
 *
 * @author AdamKyle
 */
public class PlayerMainFrm extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    private ArrayList<Player> list;
    private ArrayList<RequestModel> list1;
    private ArrayList<Friend> list4;
    private ArrayList<Group> listGroup;
    private Player player;
    private TCP_PlayerCtr myRemoteObject;
    private ArrayList<Rank> list2 = new ArrayList<>();
    ImageIcon online = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\TCP-Client\\src\\img\\online.png");
    ImageIcon offline = new ImageIcon("C:\\Users\\NONAME.NONAME\\Documents\\NetBeansProjects\\TCP-Client\\src\\img\\offline.png");

    public PlayerMainFrm(TCP_PlayerCtr ro, Player pl) {
        initComponents();
        this.player = pl;
        myRemoteObject = ro;

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        txt_Fullname.setText("Xin chào: " + player.getFullname());

        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CHECK_GROUP, player));

        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_PLAYERS_ONLINE, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ORDER_RANK, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_FRIEND, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_REQUEST_FRIEND, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_REQUEST, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ADD_FRIEND, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_FRIEND, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_REQUEST, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LIST_FRIEND, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_FRIEND, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_GROUP, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LIST_GROUP, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_GROUP, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_GROUP, this));

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.UPDATE_PLAYER_STATUS, player));
                myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.PLAYERS_ONLINE, player));
                myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.ORDER_RANK, player));
                myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.GET_REQUEST, player));
                myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_FRIEND, player));
                System.exit(0);
            }

        });

        btnAcceptPairMatch.setEnabled(false);
        btnDenyPairMatch.setEnabled(false);
    }

    public PlayerMainFrm() {
        super();
    }
    ArrayList<RequestModel> temp = new ArrayList<>();

    public void tbl_waitFriend(ObjectWrapper data) {
        //table wait friend
        temp = new ArrayList<>();
        if (data.getData() instanceof ArrayList<?>) {
            list1 = (ArrayList<RequestModel>) data.getData();

            for (int i = 0; i < list1.size(); i++) {
                if (list1.get(i).getRecieverid() == player.getId_player()) {
                    temp.add(list1.get(i));
                }
            }
            String[] columnNames1 = {"", "Danh sách chờ duyệt"};
            Object[][] value1 = new Object[temp.size()][columnNames1.length];
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getRecieverid() == player.getId_player()) {
                    if (temp.get(i).getStatus().equals("1")) {
                        value1[i][0] = online;
                        value1[i][1] = temp.get(i).getRequestname();
                    } else {
                        value1[i][0] = offline;
                        value1[i][1] = temp.get(i).getRequestname();
                    }
                }
            }
            DefaultTableModel tableModelWait = new DefaultTableModel(value1, columnNames1) {
                @Override
                public Class getColumnClass(int column1) {
                    tbl_wait.getColumnModel().getColumn(0).setPreferredWidth(20);
                    tbl_wait.getColumnModel().getColumn(1).setPreferredWidth(500);
                    return (column1 == 0) ? Icon.class : Object.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    //unable to edit cells
                    return false;
                }
            };
            tbl_wait.setModel(tableModelWait);
        }
    }

    public void tbl_rankFriend(ObjectWrapper data) {

        // table rank friend
        if (data.getData() instanceof ArrayList<?>) {
            list2 = (ArrayList<Rank>) data.getData();
            String[] columnNames2 = {"", "Name", "Core", "Rank"};
            Object[][] value2 = new Object[list2.size()][columnNames2.length];
            for (int i = 0; i < list2.size(); i++) {
                if (list2.get(i).getStatus().equals("1")) {
                    value2[i][0] = online;
                    value2[i][1] = list2.get(i).getFullname();
                    value2[i][2] = list2.get(i).getCore();
                    value2[i][3] = list2.get(i).getRank();
                } else {
                    value2[i][0] = offline;
                    value2[i][1] = list2.get(i).getFullname();
                    value2[i][2] = list2.get(i).getCore();
                    value2[i][3] = list2.get(i).getRank();
                }
            }
            DefaultTableModel tableModelRank = new DefaultTableModel(value2, columnNames2) {
                @Override
                public Class getColumnClass(int column1) {
                    tbl_rank.getColumnModel().getColumn(0).setPreferredWidth(20);
                    tbl_rank.getColumnModel().getColumn(1).setPreferredWidth(300);
                    tbl_rank.getColumnModel().getColumn(2).setPreferredWidth(100);
                    tbl_rank.getColumnModel().getColumn(3).setPreferredWidth(100);

                    return (column1 == 0) ? Icon.class
                            : Object.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    //unable to edit cells
                    return false;
                }
            };
            tbl_rank.setModel(tableModelRank);
        }
    }
    ArrayList<Friend> listF = new ArrayList<>();

    public void tbl_listFirend(ObjectWrapper data) {
        listF = new ArrayList<>();
        if (data.getData() instanceof ArrayList<?>) {
            list4 = (ArrayList<Friend>) data.getData();
            for (int i = 0; i < list4.size(); i++) {
                if (list4.get(i).getIdPlayer1() == player.getIdplayer() || list4.get(i).getIdPlayer2() == player.getIdplayer()) {
                    listF.add(list4.get(i));
                }
            }
            String[] columnNames3 = {"", "Fullname"};
            Object[][] value3 = new Object[listF.size()][columnNames3.length];
            for (int i = 0; i < listF.size(); i++) {
                if (listF.get(i).getIdPlayer1() == player.getIdplayer()) {
                    if (listF.get(i).getStt2().equals("1")) {
                        value3[i][0] = online;
                        value3[i][1] = listF.get(i).getNamePlayer2();
                    } else {
                        value3[i][0] = offline;
                        value3[i][1] = listF.get(i).getNamePlayer2();
                    }
                } else {
                    if (listF.get(i).getStt1().equals("1")) {
                        value3[i][0] = online;
                        value3[i][1] = listF.get(i).getNamePlayer1();
                    } else {
                        value3[i][0] = offline;
                        value3[i][1] = listF.get(i).getNamePlayer1();
                    }
                }
            }
            DefaultTableModel tableModelFriend = new DefaultTableModel(value3, columnNames3) {
                @Override
                public Class getColumnClass(int column1) {
                    tbl_friends.getColumnModel().getColumn(0).setPreferredWidth(30);
                    tbl_friends.getColumnModel().getColumn(1).setPreferredWidth(500);
                    return (column1 == 0) ? Icon.class : Object.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    //unable to edit cells
                    return false;
                }
            };
            tbl_friends.setModel(tableModelFriend);
        }
    }

    public void tbl_onlineFriend(ObjectWrapper data) {
        //table online friend
        if (data.getData() instanceof ArrayList<?>) {
            list = (ArrayList<Player>) data.getData();
            String[] columnNames = {"", "Danh sách người chơi online"};
            Object[][] value = new Object[list.size()][columnNames.length];
            for (int i = 0; i < list.size(); i++) {
                value[i][0] = online;
                value[i][1] = list.get(i).getFullname();
            }
            DefaultTableModel tableModelOnline = new DefaultTableModel(value, columnNames) {
                @Override
                public Class getColumnClass(int column) {
                    tbl_players_online.getColumnModel().getColumn(0).setPreferredWidth(20);
                    tbl_players_online.getColumnModel().getColumn(1).setPreferredWidth(500);
                    return (column == 0) ? Icon.class : Object.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    //unable to edit cells
                    return false;
                }
            };
            tbl_players_online.setModel(tableModelOnline);
        } else {
            tbl_players_online.setModel(null);
        }
    }

    public void tbl_Group(ObjectWrapper data) {
        //table wait friend
        listGroup = new ArrayList<>();
        if (data.getData() instanceof ArrayList<?>) {
            listGroup = (ArrayList<Group>) data.getData();
            String[] columnNames1 = {"Name Group", "Host", "Quanity Player"};
            Object[][] value1 = new Object[listGroup.size()][columnNames1.length];
            for (int i = 0; i < listGroup.size(); i++) {
                value1[i][0] = listGroup.get(i).getNameGroup();
                value1[i][1] = listGroup.get(i).getHost();
                value1[i][2] = listGroup.get(i).getQuanity();
            }
            DefaultTableModel tableModelGroup = new DefaultTableModel(value1, columnNames1) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    //unable to edit cells
                    return false;
                }
            };
            tbl_Group.setModel(tableModelGroup);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbFoundMatch = new javax.swing.JLabel();
        lbTimerPairMatch = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txt_Fullname = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        lbFindMatch = new javax.swing.JLabel();
        jProgressBar2 = new javax.swing.JProgressBar();
        btnCancelFindMatch = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_players_online = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_friends = new javax.swing.JTable();
        btnDeleteFriend = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_wait = new javax.swing.JTable();
        btnAccept = new javax.swing.JButton();
        btnDeny = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_rank = new javax.swing.JTable();
        btnAddFriend = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_Group = new javax.swing.JTable();
        btnCreateGroup = new javax.swing.JButton();
        txtNameGroup = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnDeleteGroup = new javax.swing.JButton();
        lbFoundMatch1 = new javax.swing.JLabel();
        lbTimerPairMatch1 = new javax.swing.JLabel();
        btnAcceptPairMatch = new javax.swing.JButton();
        btnDenyPairMatch = new javax.swing.JButton();

        lbFoundMatch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFoundMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFoundMatch.setText("Đã tìm thấy đối thủ ... Vào ngay?");

        lbTimerPairMatch.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        lbTimerPairMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTimerPairMatch.setText("15s");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_Fullname.setBackground(new java.awt.Color(204, 204, 204));
        txt_Fullname.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        txt_Fullname.setForeground(new java.awt.Color(51, 51, 255));
        txt_Fullname.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jProgressBar1.setIndeterminate(true);

        lbFindMatch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFindMatch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFindMatch.setText("Đang tìm trận...");

        jProgressBar2.setIndeterminate(true);

        btnCancelFindMatch.setText("Hủy");
        btnCancelFindMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelFindMatchActionPerformed(evt);
            }
        });

        tbl_players_online.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Danh sách người chơi online"
            }
        ));
        tbl_players_online.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_players_onlineMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_players_online);
        if (tbl_players_online.getColumnModel().getColumnCount() > 0) {
            tbl_players_online.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Danh sách người chơi", jPanel1);

        tbl_friends.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Fullname"
            }
        ));
        tbl_friends.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_friendsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_friends);

        btnDeleteFriend.setText("Xóa ");
        btnDeleteFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteFriendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDeleteFriend, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDeleteFriend)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách bạn bè", jPanel3);

        tbl_wait.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Danh sách chờ duyệt"
            }
        ));
        tbl_wait.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_waitMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_wait);

        btnAccept.setText("Accept");
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });

        btnDeny.setText("Deny");
        btnDeny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDenyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDeny, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccept)
                    .addComponent(btnDeny))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Chờ duyệt", jPanel4);

        tbl_rank.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Name", "Rank"
            }
        ));
        tbl_rank.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_rankMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbl_rank);
        if (tbl_rank.getColumnModel().getColumnCount() > 0) {
            tbl_rank.getColumnModel().getColumn(0).setHeaderValue("");
        }

        btnAddFriend.setText("Add Friend");
        btnAddFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFriendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnAddFriend))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddFriend)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Bảng xếp hạng", jPanel5);

        tbl_Group.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name Group", "Host", "Quanity Player"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Group.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_GroupMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_Group);

        btnCreateGroup.setText("Create Group");
        btnCreateGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateGroupActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên nhóm");

        btnDeleteGroup.setText("Delete");
        btnDeleteGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteGroupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNameGroup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCreateGroup)
                .addGap(135, 135, 135)
                .addComponent(btnDeleteGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateGroup)
                    .addComponent(txtNameGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnDeleteGroup))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Danh sách hội nhóm", jPanel6);

        lbFoundMatch1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbFoundMatch1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbFoundMatch1.setText("Đã tìm thấy đối thủ ... Vào ngay?");

        lbTimerPairMatch1.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        lbTimerPairMatch1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTimerPairMatch1.setText("15s");

        btnAcceptPairMatch.setText("Chấp nhận");
        btnAcceptPairMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptPairMatchActionPerformed(evt);
            }
        });

        btnDenyPairMatch.setText("Từ chối");
        btnDenyPairMatch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDenyPairMatchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDenyPairMatch)
                        .addGap(64, 64, 64)
                        .addComponent(btnAcceptPairMatch)
                        .addGap(154, 154, 154))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbFoundMatch1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTimerPairMatch1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTabbedPane1)
                                .addGap(7, 7, 7))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txt_Fullname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(29, 29, 29)
                                .addComponent(btnExit)
                                .addGap(13, 13, 13)))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbFindMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelFindMatch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_Fullname, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(lbFindMatch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelFindMatch)
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbFoundMatch1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTimerPairMatch1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDenyPairMatch)
                    .addComponent(btnAcceptPairMatch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // myRemoteObject.updatePlayerstatus("0", player.getIdplayer());
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnCancelFindMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelFindMatchActionPerformed
        // chỉ gửi yêu cầu lên server chứ ko đổi giao diện ngay
        // socketHandler sẽ đọc kết quả trả về từ server và quyết định có đổi stateDisplay hay không

    }//GEN-LAST:event_btnCancelFindMatchActionPerformed

    private void btnAcceptPairMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptPairMatchActionPerformed

    }//GEN-LAST:event_btnAcceptPairMatchActionPerformed

    private void btnDenyPairMatchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDenyPairMatchActionPerformed

    }//GEN-LAST:event_btnDenyPairMatchActionPerformed

    Rank temp1 = new Rank();
    RequestModel request = new RequestModel();
    private void tbl_players_onlineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_players_onlineMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbl_players_onlineMouseClicked
    Friend friend = new Friend();

    public void checkAccept(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            JOptionPane.showMessageDialog(this, "Ban va " + requestModel.getRequestname() + " da tro thanh ban be");
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.GET_REQUEST, player));
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_FRIEND, player));
        }
    }

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        // TODO add your handling code here:
        friend.setIdPlayer1(requestModel.getSenderid());
        friend.setIdPlayer2(requestModel.getRecieverid());
        friend.setNamePlayer1(requestModel.getRequestname());
        friend.setNamePlayer2(player.getFullname());
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.ADD_FRIEND, friend));

    }//GEN-LAST:event_btnAcceptActionPerformed
    public void RequestFriend(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            JOptionPane.showMessageDialog(this, "Da gui loi moi ket ban voi " + temp1.getFullname());
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.GET_REQUEST, player));
            btnAddFriend.setEnabled(false);
        }
    }

    public void checkRequestFriend(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            btnAddFriend.setEnabled(false);
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.GET_REQUEST, player));
        }
    }

    private void btnAddFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFriendActionPerformed
        // TODO add your handling code here:
        request.setSenderid(player.getId_player());
        request.setRecieverid(temp1.getIdplayer());
        request.setRequestname(player.getFullname());
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.REQUEST_FRIEND, request));
    }//GEN-LAST:event_btnAddFriendActionPerformed
    public void checkAdd(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            btnAddFriend.setEnabled(false);
        }
    }
    private void tbl_rankMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_rankMouseClicked
        // TODO add your handling code here:
        int column = tbl_rank.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_rank.getRowHeight(); // get the row of the button
        // *Checking the row or column is valid or not
        if (row < tbl_rank.getRowCount() && row >= 0 && column < tbl_rank.getColumnCount() && column >= 0) {
            temp1 = list2.get(row);
            if (player.getIdplayer() == temp1.getIdplayer()) {
                btnAddFriend.setEnabled(false);
            } else {
                btnAddFriend.setEnabled(true);
                RequestModel r = new RequestModel();
                r.setSenderid(player.getId_player());
                r.setRecieverid(temp1.getIdplayer());
                myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CHECK_FRIEND, r));
                myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CHECK_REQUEST_FRIEND, r));
            }
        }
    }//GEN-LAST:event_tbl_rankMouseClicked

    RequestModel requestModel = new RequestModel();

    public void deny(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            JOptionPane.showMessageDialog(this, "Da huy loi moi ket ban " + request.getRequestname());
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.GET_REQUEST, player));
        }
    }
    private void btnDenyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDenyActionPerformed
        // TODO add your handling code here:
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.DELETE_REQUEST, request));
    }//GEN-LAST:event_btnDenyActionPerformed

    private void tbl_waitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_waitMouseClicked
        // TODO add your handling code here:
        int column = tbl_wait.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_wait.getRowHeight(); // get the row of the button

        // *Checking the row or column is valid or not
        if (row < tbl_wait.getRowCount() && row >= 0 && column < tbl_wait.getColumnCount() && column >= 0) {
            requestModel = temp.get(row);
        }
    }//GEN-LAST:event_tbl_waitMouseClicked

    Friend delFriend = new Friend();

    public void deleFriend(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            if (player.getIdplayer() == delFriend.getIdPlayer1()) {
                JOptionPane.showMessageDialog(this, "Da xoa " + delFriend.getNamePlayer2() + " khoi danh sach ban be");
            } else if (player.getIdplayer() == delFriend.getIdPlayer2()) {
                JOptionPane.showMessageDialog(this, "Da xoa " + delFriend.getNamePlayer1() + " khoi danh sach ban be");
            }
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_FRIEND, player));
        }
    }
    private void btnDeleteFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteFriendActionPerformed
        // TODO add your handling code here:
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.DELETE_FRIEND, delFriend));
    }//GEN-LAST:event_btnDeleteFriendActionPerformed

    private void tbl_friendsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_friendsMouseClicked
        // TODO add your handling code here:
        int column = tbl_friends.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_friends.getRowHeight(); // get the row of the button

        // *Checking the row or column is valid or not
        if (row < tbl_friends.getRowCount() && row >= 0 && column < tbl_friends.getColumnCount() && column >= 0) {
            delFriend = list4.get(row);
        }
    }//GEN-LAST:event_tbl_friendsMouseClicked

    Group group = new Group();

    public void deleteGroup(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            JOptionPane.showMessageDialog(this, "Da xoa nhom: " + group.getNameGroup());
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_GROUP, player));
            btnCreateGroup.setEnabled(true);
        }
    }
    private void tbl_GroupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_GroupMouseClicked
        // TODO add your handling code here:
        int column = tbl_Group.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_Group.getRowHeight(); // get the row of the button

        // *Checking the row or column is valid or not
        if (row < tbl_Group.getRowCount() && row >= 0 && column < tbl_Group.getColumnCount() && column >= 0) {
            group = listGroup.get(row);
            ObjectWrapper existed = null;
            for (ObjectWrapper func : myRemoteObject.getActiveFunction()) {
                if (func.getData() instanceof GroupDetailFrm) {
                    ((GroupDetailFrm) func.getData()).dispose();
                    existed = func;
                }
            }
            if (existed != null) {
                myRemoteObject.getActiveFunction().remove(existed);
            }
            new GroupDetailFrm(myRemoteObject, player, listGroup.get(row)).setVisible(true);
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CHECK_JOIN, player));
            if (group.getHost().equals(player.getFullname())) {
                btnDeleteGroup.setEnabled(true);
            } else {
                btnDeleteGroup.setEnabled(false);
            }
        }
    }//GEN-LAST:event_tbl_GroupMouseClicked
    public void checkGroup(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            btnCreateGroup.setEnabled(false);
        }
    }

    public void createGroup(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            JOptionPane.showMessageDialog(this, "Ban da tao nhom: " + txtNameGroup.getText() + " thanh cong");
            txtNameGroup.setText("");
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_GROUP, player));
            btnCreateGroup.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Nhom: " + txtNameGroup.getText() + " da ton tai");
        }

    }
    private void btnCreateGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateGroupActionPerformed
        // TODO add your handling code here:
        Group group = new Group();
        group.setIdplayer(player.getId_player());
        group.setNamePlayer(player.getFullname());
        group.setNameGroup(txtNameGroup.getText());
        group.setHost(player.getFullname());

        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CREATE_GROUP, group));
    }//GEN-LAST:event_btnCreateGroupActionPerformed

    private void btnDeleteGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteGroupActionPerformed
        // TODO add your handling code here:
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.DELETE_GROUP, group));
    }//GEN-LAST:event_btnDeleteGroupActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnAcceptPairMatch;
    private javax.swing.JButton btnAddFriend;
    private javax.swing.JButton btnCancelFindMatch;
    private javax.swing.JButton btnCreateGroup;
    private javax.swing.JButton btnDeleteFriend;
    private javax.swing.JButton btnDeleteGroup;
    private javax.swing.JButton btnDeny;
    private javax.swing.JButton btnDenyPairMatch;
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbFindMatch;
    private javax.swing.JLabel lbFoundMatch;
    private javax.swing.JLabel lbFoundMatch1;
    private javax.swing.JLabel lbTimerPairMatch;
    private javax.swing.JLabel lbTimerPairMatch1;
    private javax.swing.JTable tbl_Group;
    private javax.swing.JTable tbl_friends;
    private javax.swing.JTable tbl_players_online;
    private javax.swing.JTable tbl_rank;
    private javax.swing.JTable tbl_wait;
    private javax.swing.JTextField txtNameGroup;
    private javax.swing.JLabel txt_Fullname;
    // End of variables declaration//GEN-END:variables
}
