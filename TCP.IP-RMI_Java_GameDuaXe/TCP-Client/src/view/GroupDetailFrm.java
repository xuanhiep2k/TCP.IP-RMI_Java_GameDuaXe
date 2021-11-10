/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.TCP_PlayerCtr;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Group;
import model.ObjectWrapper;
import model.Player;

/**
 *
 * @author NONAME
 */
public class GroupDetailFrm extends javax.swing.JFrame {

    /**
     * Creates new form GroupDetailFrm
     */
    private Player player;
    private TCP_PlayerCtr myRemoteObject;
    private Group group;
    private ArrayList<Group> listMember, listApproval;

    public GroupDetailFrm(TCP_PlayerCtr ro, Player pl, Group gr) {
        initComponents();
        myRemoteObject = ro;
        this.player = pl;
        this.group = gr;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        txtWelcome.setText("Xin chào: " + player.getFullname());
        txtNameGroup.setText(group.getNameGroup());
        txtHost.setText(group.getHost());

        if (player.getFullname().equals(group.getHost())) {
            btnJoin.setVisible(false);
            btnLeaveGroup.setVisible(false);
            btnAcceptJoin.setVisible(true);
        } else {
            btnJoin.setVisible(true);
            btnLeaveGroup.setVisible(true);
            btnAcceptJoin.setVisible(false);
        }
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_APPROVAL, group));
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_MEMBER, group));
        group.setIdplayer(player.getId_player());
        group.setNameGroup(group.getNameGroup());
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CHECK_JOIN_APPROVAL, group));
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CHECK_LEAVE, group));
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CHECK_JOIN, player));

        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LIST_APPROVAL, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_JOIN_APPROVAL, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_GROUP, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DELETE_GROUP, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LIST_MEMBER, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_LEAVE, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_JOIN, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_GROUP, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_GROUP, this));

    }

    public void checkLeave(ObjectWrapper data) {
        if (data.getData().equals("false")) {
            btnLeaveGroup.setVisible(false);
            btnChallenge.setVisible(false);
        }
    }

    public void leaveGroup(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            //JOptionPane.showMessageDialog(this, "Ban da roi nhom: " + group.getNameGroup());
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_GROUP, player));
            this.dispose();
        }
    }

    public void checkJoin(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            btnJoin.setVisible(false);
        }
    }

    public void checkJoinApproval(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            btnJoin.setVisible(false);
        }
    }

    public void tbl_listMember(ObjectWrapper data) {
        listMember = new ArrayList<>();
        if (data.getData() instanceof ArrayList<?>) {
            listMember = (ArrayList<Group>) data.getData();
            String[] columnNames1 = {"Danh sách thành viên trong nhóm"};
            Object[][] value1 = new Object[listMember.size()][columnNames1.length];
            for (int i = 0; i < listMember.size(); i++) {
                value1[i][0] = listMember.get(i).getNamePlayer();
            }
            DefaultTableModel tableModelMember = new DefaultTableModel(value1, columnNames1) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    //unable to edit cells
                    return false;
                }
            };
            tbl_memberGroup.setModel(tableModelMember);
        }
    }

    public void listApproval(ObjectWrapper data) {
        listApproval = new ArrayList<>();
        if (player.getFullname().equals(group.getHost())) {
            if (data.getData() instanceof ArrayList<?>) {
                listApproval = (ArrayList<Group>) data.getData();
                String[] columnNames1 = {"Name Player", "Name Group", "Host"};
                Object[][] value1 = new Object[listApproval.size()][columnNames1.length];
                for (int i = 0; i < listApproval.size(); i++) {
                    value1[i][0] = listApproval.get(i).getNamePlayer();
                    value1[i][1] = listApproval.get(i).getNameGroup();
                    value1[i][2] = listApproval.get(i).getHost();
                }
                DefaultTableModel tableModelApproval = new DefaultTableModel(value1, columnNames1) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        //unable to edit cells
                        return false;
                    }
                };
                tbl_approval.setModel(tableModelApproval);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtWelcome = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtNameGroup = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_memberGroup = new javax.swing.JTable();
        btnChallenge = new javax.swing.JButton();
        btnJoin = new javax.swing.JButton();
        btnLeaveGroup = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_approval = new javax.swing.JTable();
        btnAcceptJoin = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtHost = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtWelcome.setEditable(false);
        txtWelcome.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        txtWelcome.setForeground(new java.awt.Color(0, 51, 255));
        txtWelcome.setBorder(null);

        btnExit.setText("Thoát");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jLabel1.setText("Tên nhóm:");

        txtNameGroup.setEditable(false);
        txtNameGroup.setForeground(new java.awt.Color(0, 0, 255));
        txtNameGroup.setBorder(null);

        tbl_memberGroup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Danh sách thành viên trong nhóm"
            }
        ));
        jScrollPane2.setViewportView(tbl_memberGroup);

        btnChallenge.setText("Challenge");

        btnJoin.setText("Join");
        btnJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoinActionPerformed(evt);
            }
        });

        btnLeaveGroup.setText("Leave Group");
        btnLeaveGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveGroupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnLeaveGroup)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnJoin, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChallenge))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnChallenge)
                    .addComponent(btnJoin)
                    .addComponent(btnLeaveGroup))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách thành viên", jPanel1);

        tbl_approval.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name Player", "Name Group", "Host"
            }
        ));
        tbl_approval.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_approvalMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_approval);

        btnAcceptJoin.setText("Accept Join");
        btnAcceptJoin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptJoinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnAcceptJoin))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAcceptJoin)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách chờ xét duyệt", jPanel2);

        jLabel2.setText("Host:");

        txtHost.setEditable(false);
        txtHost.setForeground(new java.awt.Color(0, 0, 255));
        txtHost.setBorder(null);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNameGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtHost))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnExit)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNameGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    public void join(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            JOptionPane.showMessageDialog(this, "Ban da xin vao nhom: " + group.getNameGroup());
            btnJoin.setVisible(false);
            this.dispose();
        }
    }
    private void btnJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoinActionPerformed
        // TODO add your handling code here:
        group.setIdplayer(player.getId_player());
        group.setNamePlayer(player.getFullname());
        group.setNameGroup(group.getNameGroup());
        group.setHost(group.getHost());

        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.JOIN_GROUP, group));
    }//GEN-LAST:event_btnJoinActionPerformed

    private void btnLeaveGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveGroupActionPerformed
        // TODO add your handling code here:
        group.setIdplayer(player.getId_player());
        group.setNameGroup(group.getNameGroup());
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LEAVE_GROUP, group));
    }//GEN-LAST:event_btnLeaveGroupActionPerformed
    Group gr = new Group();
    private void tbl_approvalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_approvalMouseClicked
        // TODO add your handling code here:
        int column = tbl_approval.getColumnModel().getColumnIndexAtX(evt.getX()); // get the coloum of the button
        int row = evt.getY() / tbl_approval.getRowHeight(); // get the row of the button

        // *Checking the row or column is valid or not
        if (row < tbl_approval.getRowCount() && row >= 0 && column < tbl_approval.getColumnCount() && column >= 0) {
            gr = listApproval.get(row);
        }
    }//GEN-LAST:event_tbl_approvalMouseClicked

    public void acceptGroup(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            //  JOptionPane.showMessageDialog(this, "Da them " + gr.getNamePlayer() + " vao nhom " + group.getNameGroup());
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_GROUP, player));
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_APPROVAL, gr));
            myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_MEMBER, group));
            this.dispose();
        }
    }
    private void btnAcceptJoinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptJoinActionPerformed
        // TODO add your handling code here:
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.ACCEPT_GROUP, gr));
    }//GEN-LAST:event_btnAcceptJoinActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcceptJoin;
    private javax.swing.JButton btnChallenge;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnJoin;
    private javax.swing.JButton btnLeaveGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbl_approval;
    private javax.swing.JTable tbl_memberGroup;
    private javax.swing.JTextField txtHost;
    private javax.swing.JTextField txtNameGroup;
    private javax.swing.JTextField txtWelcome;
    // End of variables declaration//GEN-END:variables
}
