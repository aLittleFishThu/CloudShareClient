/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui;
      
import client.IBusinessLogic;
import common.Credential;
import common.LoginResult;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 *
 * @author yzj
 */
public class LoginDialog extends javax.swing.JDialog {
    private final JFrame m_MainFrame;
    private final IBusinessLogic m_Business;
    /**
     * Creates new form NewJDialog
     * @param mainFrame
     * @param business
     */
    public LoginDialog(JFrame mainFrame, IBusinessLogic business) {
        super(mainFrame, true);
        m_MainFrame=mainFrame;
        m_Business=business;
        initComponents();
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

        titleLabel = new JLabel();
        userIDLabel = new JLabel();
        userIDField = new JTextField();
        passwdLabel = new JLabel();
        passwdField = new JPasswordField();
        loginButton = new JButton();
        registerButton = new JButton();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("云协作");
        setLocation(new Point(0, 0));
        setResizable(false);
        addWindowListener(formListener);

        titleLabel.setFont(new Font("微软雅黑", 0, 18)); // NOI18N
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("登录云协作账号");

        userIDLabel.setFont(new Font("微软雅黑", 0, 14)); // NOI18N
        userIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userIDLabel.setText("登录名");

        userIDField.setFont(new Font("Consolas", 0, 12)); // NOI18N

        passwdLabel.setFont(new Font("微软雅黑", 0, 14)); // NOI18N
        passwdLabel.setText("登录密码");

        passwdField.setFont(new Font("Consolas", 0, 12)); // NOI18N

        loginButton.setFont(new Font("微软雅黑", 0, 16)); // NOI18N
        loginButton.setText("登录");
        loginButton.addActionListener(formListener);

        registerButton.setFont(new Font("微软雅黑", 0, 16)); // NOI18N
        registerButton.setText("注册");
        registerButton.addActionListener(formListener);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(userIDLabel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwdLabel))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(passwdField, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
                            .addComponent(userIDField, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(titleLabel)))))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(userIDField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(userIDLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passwdLabel)
                    .addComponent(passwdField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                    .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements ActionListener, WindowListener {
        FormListener() {}
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == loginButton) {
                LoginDialog.this.loginAction(evt);
            }
            else if (evt.getSource() == registerButton) {
                LoginDialog.this.registerAction(evt);
            }
        }

        public void windowActivated(WindowEvent evt) {
        }

        public void windowClosed(WindowEvent evt) {
        }

        public void windowClosing(WindowEvent evt) {
            if (evt.getSource() == LoginDialog.this) {
                LoginDialog.this.formWindowClosing(evt);
            }
        }

        public void windowDeactivated(WindowEvent evt) {
        }

        public void windowDeiconified(WindowEvent evt) {
        }

        public void windowIconified(WindowEvent evt) {
        }

        public void windowOpened(WindowEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents

    private void loginAction(ActionEvent evt) {//GEN-FIRST:event_loginAction
        String userID=userIDField.getText();
        String password=new String(passwdField.getPassword());
        Credential cred=new Credential(userID,password);
        try {
            LoginResult result=m_Business.login(cred);
            switch(result){
                case OK:
                    FilePane filePane=new FilePane(m_MainFrame,m_Business);
                    m_MainFrame.setContentPane(filePane);
                    m_MainFrame.pack();
                    m_MainFrame.setVisible(true);
                    this.dispose();
                    break;
                case wrong:
                    JOptionPane.showMessageDialog
                    (this,"登录失败，请检查用户名及密码","登录",JOptionPane.WARNING_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog
                    (this,"未知错误","登录",JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }  catch (IOException e1) {
            JOptionPane.showMessageDialog
            (this, "网络错误","登录",JOptionPane.ERROR_MESSAGE);
        } 
    }//GEN-LAST:event_loginAction

    private void registerAction(ActionEvent evt) {//GEN-FIRST:event_registerAction
        RegisterDialog registerDialog=new RegisterDialog(this,m_Business);
    }//GEN-LAST:event_registerAction

    private void formWindowClosing(WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton loginButton;
    private JPasswordField passwdField;
    private JLabel passwdLabel;
    private JButton registerButton;
    private JLabel titleLabel;
    private JTextField userIDField;
    private JLabel userIDLabel;
    // End of variables declaration//GEN-END:variables
}
