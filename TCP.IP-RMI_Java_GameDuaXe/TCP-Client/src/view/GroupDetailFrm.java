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
    private ArrayList<Group> listMember;

    public GroupDetailFrm(TCP_PlayerCtr ro, Player pl, Group gr) {
        initComponents();
        myRemoteObject = ro;
        this.player = pl;
        this.group = gr;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        txtWelcome.setText("Xin chào: " + player.getFullname());
        txtNameGroup.setText(group.getNameGroup());
        if (player.getFullname().equals(group.getHost())) {
            btnJoin.setVisible(false);
        } else {
            btnJoin.setVisible(true);
        }

        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.LIST_MEMBER, group));

        group.setIdplayer(player.getId_player());
        group.setNameGroup(group.getNameGroup());
        myRemoteObject.sendData(new ObjectWrapper(ObjectWrapper.CHECK_JOIN_APPROVAL, group));

        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LIST_MEMBER, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_JOIN, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CHECK_JOIN_APPROVAL, this));
        myRemoteObject.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_JOIN_GROUP, this));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

            }
        });
    }

    public void checkJoin(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            btnJoin.setVisible(false);
        } else {
            btnJoin.setVisible(true);
        }
    }

    public void checkJoinApproval(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            btnJoin.setVisible(false);
        } else {
            btnJoin.setVisible(true);
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
                .addGap(0, 12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách thành viên", jPanel1);

        tbl_approval.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name Player", "Name Group", "Host"
            }
        ));
        jScrollPane3.setViewportView(tbl_approval);

        btnAcceptJoin.setText("Accept Join");

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
                .addGap(0, 11, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách chờ xét duyệt", jPanel2);

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
                        .addComponent(txtNameGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(txtNameGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tbl_approval;
    private javax.swing.JTable tbl_memberGroup;
    private javax.swing.JTextField txtNameGroup;
    private javax.swing.JTextField txtWelcome;
    // End of variables declaration//GEN-END:variables
}
