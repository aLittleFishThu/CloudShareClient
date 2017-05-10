/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui;

import client.IBusinessLogic;
import common.DeleteFileResult;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

/**
 *
 * @author yzj
 */
public class FileButtonPane extends javax.swing.JPanel {
    private final JFrame m_MainFrame;
    private final IBusinessLogic m_Business;

    /**
     * Creates new form FileContentPane
     */
    public FileButtonPane(JFrame mainFrame,IBusinessLogic business) {
        m_MainFrame=mainFrame;
        m_Business=business;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uploadFileButton = new JButton();
        downloadFileButton = new JButton();
        deleteFileButton = new JButton();
        renameFileButton = new JButton();
        authorizationButton = new JButton();

        FormListener formListener = new FormListener();

        setForeground(new Color(204, 204, 204));

        uploadFileButton.setFont(new Font("微软雅黑", 0, 14)); // NOI18N
        uploadFileButton.setText("上传");
        uploadFileButton.setActionCommand("JButton1");
        uploadFileButton.setBorder(null);
        uploadFileButton.setBorderPainted(false);
        uploadFileButton.setMaximumSize(new Dimension(30, 20));
        uploadFileButton.setMinimumSize(new Dimension(30, 20));
        uploadFileButton.addActionListener(formListener);

        downloadFileButton.setFont(new Font("微软雅黑", 0, 14)); // NOI18N
        downloadFileButton.setActionCommand("JButton1");
        downloadFileButton.setBorder(null);
        downloadFileButton.setBorderPainted(false);
        downloadFileButton.setEnabled(false);
        downloadFileButton.setLabel("下载");
        downloadFileButton.setMaximumSize(new Dimension(30, 20));
        downloadFileButton.setMinimumSize(new Dimension(30, 20));
        downloadFileButton.setPreferredSize(new Dimension(30, 20));
        downloadFileButton.addActionListener(formListener);

        deleteFileButton.setFont(new Font("微软雅黑", 0, 14)); // NOI18N
        deleteFileButton.setText("删除");
        deleteFileButton.setEnabled(false);
        deleteFileButton.addActionListener(formListener);

        renameFileButton.setFont(new Font("微软雅黑", 0, 14)); // NOI18N
        renameFileButton.setText("重命名");
        renameFileButton.setEnabled(false);
        renameFileButton.addActionListener(formListener);

        authorizationButton.setFont(new Font("微软雅黑", 0, 14)); // NOI18N
        authorizationButton.setText("修改权限");
        authorizationButton.setEnabled(false);
        authorizationButton.addActionListener(formListener);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(uploadFileButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downloadFileButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteFileButton, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(renameFileButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(authorizationButton)
                .addContainerGap(145, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(downloadFileButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteFileButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(uploadFileButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(renameFileButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(authorizationButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements ActionListener {
        FormListener() {}
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == uploadFileButton) {
                FileButtonPane.this.uploadFileButtonActionPerformed(evt);
            }
            else if (evt.getSource() == downloadFileButton) {
                FileButtonPane.this.downloadFileButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteFileButton) {
                FileButtonPane.this.deleteFileButtonActionPerformed(evt);
            }
            else if (evt.getSource() == renameFileButton) {
                FileButtonPane.this.renameFileButtonActionPerformed(evt);
            }
            else if (evt.getSource() == authorizationButton) {
                FileButtonPane.this.authorizationButtonActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void uploadFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_uploadFileButtonActionPerformed
        UploadFileWindow uploadWindow=new UploadFileWindow(m_MainFrame,m_Business);
    }//GEN-LAST:event_uploadFileButtonActionPerformed

    private void downloadFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_downloadFileButtonActionPerformed
        
    }//GEN-LAST:event_downloadFileButtonActionPerformed

    private void deleteFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_deleteFileButtonActionPerformed
        try {
            DeleteFileResult result=m_Business.deleteFile();
            switch(result){
            case OK:
                JOptionPane.showMessageDialog
                (m_MainFrame,"删除",null,JOptionPane.INFORMATION_MESSAGE);
                break;
            case unAuthorized:
                JOptionPane.showMessageDialog
                (m_MainFrame,"您未登录，请重新登录",null,JOptionPane.WARNING_MESSAGE);
                break;
            case wrong:
                JOptionPane.showMessageDialog
                (m_MainFrame,"文件不存在，请重新选择",null,JOptionPane.WARNING_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog
                (m_MainFrame,"未知错误",null,JOptionPane.ERROR_MESSAGE);
                break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog
            (m_MainFrame,"网络错误",null,JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_deleteFileButtonActionPerformed

    private void renameFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_renameFileButtonActionPerformed
        try {
            DeleteFileResult result=m_Business.deleteFile();
            switch(result){
            case OK:
                JOptionPane.showMessageDialog
                (m_MainFrame,"删除",null,JOptionPane.INFORMATION_MESSAGE);
                break;
            case unAuthorized:
                JOptionPane.showMessageDialog
                (m_MainFrame,"您未登录，请重新登录",null,JOptionPane.WARNING_MESSAGE);
                break;
            case wrong:
                JOptionPane.showMessageDialog
                (m_MainFrame,"文件不存在，请重新选择",null,JOptionPane.WARNING_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog
                (m_MainFrame,"未知错误",null,JOptionPane.ERROR_MESSAGE);
                break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog
            (m_MainFrame,"网络错误",null,JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_renameFileButtonActionPerformed

    private void authorizationButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_authorizationButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_authorizationButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    JButton authorizationButton;
    JButton deleteFileButton;
    JButton downloadFileButton;
    JButton renameFileButton;
    JButton uploadFileButton;
    // End of variables declaration//GEN-END:variables
}
