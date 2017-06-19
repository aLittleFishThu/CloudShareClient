/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui;
      
import client.IBusinessLogic;
import common.ChangePasswdResult;
import static common.LoginResult.OK;
import static common.RegisterResult.illegalID;
import static common.RegisterResult.illegalPassword;
import static common.RegisterResult.notMatch;
import static common.RegisterResult.usedID;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

/**
 *
 * @author yzj
 */
public class ChangePasswdWindow extends javax.swing.JDialog {
    //private final IBusinessLogic m_Business;
    private final IBusinessLogic m_Business;
    private final JFrame m_MainFrame;
    /**
     * Creates new form NewJDialog
     * @param mainFrame
     * @param business
     */
    public ChangePasswdWindow(JFrame mainFrame,IBusinessLogic business) {
        super(mainFrame, true);
        m_Business=business;
        m_MainFrame=mainFrame;
        initComponents();
        this.setTitle("修改密码");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        passwdLabel = new JLabel();
        passwdField = new JPasswordField();
        newPasswdLabel = new JLabel();
        newPasswdField = new JPasswordField();
        passwdTip = new JLabel();
        passwdAgainLabel = new JLabel();
        passwdAgainField = new JPasswordField();
        passwdAgainTip = new JLabel();
        confirmButton = new JButton();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("云协作");
        setLocation(new Point(0, 0));
        setResizable(false);

        passwdLabel.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        passwdLabel.setText("旧密码");

        passwdField.setFont(new Font("Consolas", 0, 12)); // NOI18N

        newPasswdLabel.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        newPasswdLabel.setText("新密码");

        newPasswdField.setFont(new Font("Consolas", 0, 12)); // NOI18N

        passwdTip.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        passwdTip.setForeground(new Color(153, 153, 153));
        passwdTip.setText("8~64位密码，仅含数字，字母，_和-");

        passwdAgainLabel.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        passwdAgainLabel.setText("确认密码");

        passwdAgainField.setFont(new Font("Consolas", 0, 12)); // NOI18N

        passwdAgainTip.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        passwdAgainTip.setForeground(new Color(153, 153, 153));
        passwdAgainTip.setText("请再次输入密码");

        confirmButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        confirmButton.setText("确定");
        confirmButton.addActionListener(formListener);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(newPasswdLabel)
                            .addComponent(passwdLabel))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(passwdField, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
                            .addComponent(newPasswdField, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(passwdAgainLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(passwdAgainTip)
                            .addComponent(passwdAgainField, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwdTip, GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)))))
            .addGroup(layout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(confirmButton)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passwdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwdLabel))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(newPasswdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(newPasswdLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwdTip)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passwdAgainLabel)
                    .addComponent(passwdAgainField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwdAgainTip)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmButton)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleDescription("");

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements ActionListener {
        FormListener() {}
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == confirmButton) {
                ChangePasswdWindow.this.confirmButtonActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void confirmButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
        String passwd=new String(passwdField.getPassword());
        String newPasswd=new String(newPasswdField.getPassword());
        String passwdAgain=new String(passwdAgainField.getPassword());
        
        if (passwd.equals("")){
            JOptionPane.showMessageDialog
                (this,"请输入旧密码","修改密码",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (newPasswd.equals("")){
            JOptionPane.showMessageDialog
                (this,"请输入新密码","修改密码",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (passwdAgain.equals("")){
            JOptionPane.showMessageDialog
                (this,"请确认密码","修改密码",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            ChangePasswdResult result=m_Business.changePasswd(passwd, newPasswd, passwdAgain);
            switch(result){
                case OK:
                    JOptionPane.showMessageDialog
                    (this,"修改密码成功","修改密码",JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    break;
                case illegalPassword:
                    JOptionPane.showMessageDialog
                    (this,"请输入合法的密码","修改密码",JOptionPane.WARNING_MESSAGE);
                    break;
                case notMatch:
                    JOptionPane.showMessageDialog
                    (this,"请确认两次输入的密码是否一致","修改密码",JOptionPane.WARNING_MESSAGE);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog
                    (this,"请确认旧密码输入正确","修改密码",JOptionPane.WARNING_MESSAGE);
                    break;
                case unAuthorized:
                    JOptionPane.showMessageDialog(this, "您未登录，请重新登录", "修改密码", JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    this.dispose();
                    break;
                default:
                    JOptionPane.showMessageDialog
                    (this,"未知错误","修改密码",JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }  catch (IOException e1) {
            JOptionPane.showMessageDialog
            (this, "网络错误","修改密码",JOptionPane.ERROR_MESSAGE);
        } 
    }//GEN-LAST:event_confirmButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton confirmButton;
    private JPasswordField newPasswdField;
    private JLabel newPasswdLabel;
    private JPasswordField passwdAgainField;
    private JLabel passwdAgainLabel;
    private JLabel passwdAgainTip;
    private JPasswordField passwdField;
    private JLabel passwdLabel;
    private JLabel passwdTip;
    // End of variables declaration//GEN-END:variables
}
