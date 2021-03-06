/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui;

import client.IBusinessLogic;
import common.AddNoteResult;
import common.Authorization;
import common.AuthorizationResult;
import common.ChangePasswdResult;
import common.CloudFile;
import common.DeleteFileResult;
import common.DeleteNoteResult;
import common.DownloadFileResult;
import common.DownloadFileResult.DownloadFileStatus;
import common.FileDirectoryResult;
import common.FileDirectoryResult.FileDirectoryStatus;
import static common.LoginResult.OK;
import static common.LoginResult.wrong;
import common.Note;
import common.NoteListResult;
import common.NoteListResult.NoteListStatus;
import common.RenameFileResult;

import common.UploadFileResult;
import static common.UploadFileResult.tooLarge;
import static common.UploadFileResult.unAuthorized;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.io.IOUtils;

/**
 *
 * @author yzj
 */
public class FilePane extends javax.swing.JPanel {
//    private class UserObserverable extends Observable{
//        private String userID;
//        
//        private String getUserID() {
//            return userID;
//        }
//
//        private void setUserID(String userID) {
//            this.userID = userID;
//            setChanged();
//            notifyObservers();
//        }
//    }
//    
//    private class UserObserver implements Observer{
//
//        @Override
//        public void update(Observable o, Object arg) {
//            UserObserverable currentUser=(UserObserverable) o;
//            String userID=currentUser.getUserID();
//            if (!userID.equals(m_Business.getUser().getUserID())){
//                uploadFileButton.setEnabled(false);
//                deleteFileButton.setEnabled(false);
//                renameFileButton.setEnabled(false);
//                authorizationButton.setEnabled(false);
//            }
//        }
//        
//    }

    private final JFrame m_MainFrame;
    private final IBusinessLogic m_Business;
    private String currentID;
    private FileTableModel fileTableModel;
    private NoteTableModel noteTableModel;
    
    public String getCurrentID() {
        return currentID;
    }

    public void setCurrentID(String targetID) {
        if (targetID.equals(currentID))   //发生变化了再重置
            return;
        this.currentID = targetID;
            if (currentID.equals(m_Business.getUser().getUserID())){    //跳转回自己的界面
                uploadFileButton.setEnabled(true);
                downloadFileButton.setEnabled(false);
                deleteFileButton.setEnabled(false);
                renameFileButton.setEnabled(false);
                authorizationButton.setEnabled(false);
                addNoteButton.setEnabled(false);
                deleteNoteButton.setEnabled(false);
            }
            else{               //跳转到他人界面
                uploadFileButton.setEnabled(false);
                downloadFileButton.setEnabled(false);
                deleteFileButton.setEnabled(false);
                renameFileButton.setEnabled(false);
                authorizationButton.setEnabled(false);
                addNoteButton.setEnabled(false);
                deleteNoteButton.setEnabled(false);
            }
    }

    /**
     * Creates new form FileContentPane
     * @param mainFrame
     * @param business
     */
    public FilePane(JFrame mainFrame, IBusinessLogic business) {
        m_MainFrame = mainFrame;
        m_Business = business;
        currentID = m_Business.getUser().getUserID();
        fileTableModel = new FileTableModel(new ArrayList<CloudFile>());
        noteTableModel = new NoteTableModel(new ArrayList<Note>());
        
        try {
            FileDirectoryResult result=m_Business.getDirectory(currentID);
            FileDirectoryStatus status=result.getResult();
            if (status.equals(FileDirectoryStatus.OK))
                fileTableModel= new FileTableModel(result.getFileDirectory());
        } catch (IOException e) {
        }
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

        fileButtonPane = new JPanel();
        uploadFileButton = new JButton();
        downloadFileButton = new JButton();
        deleteFileButton = new JButton();
        renameFileButton = new JButton();
        authorizationButton = new JButton();
        jSeparator1 = new JSeparator();
        addNoteButton = new JButton();
        deleteNoteButton = new JButton();
        jSeparator2 = new JSeparator();
        targetIDField = new JTextField();
        searchUserButton = new JButton();
        returnButton = new JButton();
        jSeparator3 = new JSeparator();
        refreshButton = new JButton();
        jSeparator4 = new JSeparator();
        changePasswdButton = new JButton();
        fileSplitPane = new JSplitPane();
        jSplitPane2 = new JSplitPane();
        fileInfoScroll = new JScrollPane();
        fileInfoTable = new JTable();
        noteDisplayScroll = new JScrollPane();
        noteTable = new JTable();
        jSplitPane1 = new JSplitPane();
        fileDisplayScroll = new JScrollPane();
        fileContentArea = new JTextArea();
        noteInputScroll = new JScrollPane();
        noteInputArea = new JTextArea();

        FormListener formListener = new FormListener();

        setForeground(new Color(240, 240, 240));
        setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        setMaximumSize(new Dimension(1366, 768));
        setPreferredSize(new Dimension(1366, 700));

        fileButtonPane.setMaximumSize(new Dimension(32767, 50));
        fileButtonPane.setMinimumSize(new Dimension(404, 50));

        uploadFileButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        uploadFileButton.setText("上传");
        uploadFileButton.setActionCommand("JButton1");
        uploadFileButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        uploadFileButton.setBorderPainted(false);
        uploadFileButton.setContentAreaFilled(false);
        uploadFileButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        uploadFileButton.setFocusPainted(false);
        uploadFileButton.setMaximumSize(new Dimension(30, 20));
        uploadFileButton.setMinimumSize(new Dimension(30, 20));
        uploadFileButton.addMouseListener(formListener);
        uploadFileButton.addActionListener(formListener);

        downloadFileButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        downloadFileButton.setText("下载");
        downloadFileButton.setActionCommand("JButton1");
        downloadFileButton.setBorder(null);
        downloadFileButton.setBorderPainted(false);
        downloadFileButton.setContentAreaFilled(false);
        downloadFileButton.setEnabled(false);
        downloadFileButton.setFocusPainted(false);
        downloadFileButton.setMaximumSize(new Dimension(30, 20));
        downloadFileButton.setMinimumSize(new Dimension(30, 20));
        downloadFileButton.setPreferredSize(new Dimension(30, 20));
        downloadFileButton.addMouseListener(formListener);
        downloadFileButton.addActionListener(formListener);

        deleteFileButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        deleteFileButton.setText("删除");
        deleteFileButton.setBorder(null);
        deleteFileButton.setBorderPainted(false);
        deleteFileButton.setContentAreaFilled(false);
        deleteFileButton.setEnabled(false);
        deleteFileButton.setFocusPainted(false);
        deleteFileButton.addMouseListener(formListener);
        deleteFileButton.addActionListener(formListener);

        renameFileButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        renameFileButton.setText("重命名");
        renameFileButton.setBorder(null);
        renameFileButton.setBorderPainted(false);
        renameFileButton.setContentAreaFilled(false);
        renameFileButton.setEnabled(false);
        renameFileButton.setFocusPainted(false);
        renameFileButton.addMouseListener(formListener);
        renameFileButton.addActionListener(formListener);

        authorizationButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        authorizationButton.setText("修改权限");
        authorizationButton.setBorder(null);
        authorizationButton.setBorderPainted(false);
        authorizationButton.setContentAreaFilled(false);
        authorizationButton.setEnabled(false);
        authorizationButton.setFocusPainted(false);
        authorizationButton.addMouseListener(formListener);
        authorizationButton.addActionListener(formListener);

        jSeparator1.setOrientation(SwingConstants.VERTICAL);

        addNoteButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        addNoteButton.setText("新增备注");
        addNoteButton.setActionCommand("JButton1");
        addNoteButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        addNoteButton.setBorderPainted(false);
        addNoteButton.setContentAreaFilled(false);
        addNoteButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        addNoteButton.setEnabled(false);
        addNoteButton.setFocusPainted(false);
        addNoteButton.setMaximumSize(new Dimension(30, 20));
        addNoteButton.setMinimumSize(new Dimension(30, 20));
        addNoteButton.addMouseListener(formListener);
        addNoteButton.addActionListener(formListener);

        deleteNoteButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        deleteNoteButton.setText("删除备注");
        deleteNoteButton.setActionCommand("JButton1");
        deleteNoteButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        deleteNoteButton.setBorderPainted(false);
        deleteNoteButton.setContentAreaFilled(false);
        deleteNoteButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        deleteNoteButton.setEnabled(false);
        deleteNoteButton.setFocusPainted(false);
        deleteNoteButton.setMaximumSize(new Dimension(30, 20));
        deleteNoteButton.setMinimumSize(new Dimension(30, 20));
        deleteNoteButton.addMouseListener(formListener);
        deleteNoteButton.addActionListener(formListener);

        jSeparator2.setOrientation(SwingConstants.VERTICAL);

        targetIDField.setFont(new Font("微软雅黑", 0, 12)); // NOI18N

        searchUserButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        searchUserButton.setText("查找用户");
        searchUserButton.setActionCommand("JButton1");
        searchUserButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        searchUserButton.setBorderPainted(false);
        searchUserButton.setContentAreaFilled(false);
        searchUserButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        searchUserButton.setFocusPainted(false);
        searchUserButton.setMaximumSize(new Dimension(30, 20));
        searchUserButton.setMinimumSize(new Dimension(30, 20));
        searchUserButton.addMouseListener(formListener);
        searchUserButton.addActionListener(formListener);

        returnButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        returnButton.setText("返回主页");
        returnButton.setActionCommand("JButton1");
        returnButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        returnButton.setFocusPainted(false);
        returnButton.setMaximumSize(new Dimension(30, 20));
        returnButton.setMinimumSize(new Dimension(30, 20));
        returnButton.addMouseListener(formListener);
        returnButton.addActionListener(formListener);

        jSeparator3.setOrientation(SwingConstants.VERTICAL);

        refreshButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        refreshButton.setText("刷新");
        refreshButton.setActionCommand("JButton1");
        refreshButton.setBorder(null);
        refreshButton.setBorderPainted(false);
        refreshButton.setContentAreaFilled(false);
        refreshButton.setFocusPainted(false);
        refreshButton.setMaximumSize(new Dimension(30, 20));
        refreshButton.setMinimumSize(new Dimension(30, 20));
        refreshButton.setPreferredSize(new Dimension(30, 20));
        refreshButton.addMouseListener(formListener);
        refreshButton.addActionListener(formListener);

        jSeparator4.setOrientation(SwingConstants.VERTICAL);

        changePasswdButton.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        changePasswdButton.setText("修改密码");
        changePasswdButton.setActionCommand("JButton1");
        changePasswdButton.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        changePasswdButton.setBorderPainted(false);
        changePasswdButton.setContentAreaFilled(false);
        changePasswdButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        changePasswdButton.setFocusPainted(false);
        changePasswdButton.setMaximumSize(new Dimension(30, 20));
        changePasswdButton.setMinimumSize(new Dimension(30, 20));
        changePasswdButton.addMouseListener(formListener);
        changePasswdButton.addActionListener(formListener);

        GroupLayout fileButtonPaneLayout = new GroupLayout(fileButtonPane);
        fileButtonPane.setLayout(fileButtonPaneLayout);
        fileButtonPaneLayout.setHorizontalGroup(fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(fileButtonPaneLayout.createSequentialGroup()
                .addComponent(uploadFileButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(downloadFileButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteFileButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(renameFileButton, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(authorizationButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addNoteButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteNoteButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(targetIDField, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchUserButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(returnButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(changePasswdButton, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        fileButtonPaneLayout.setVerticalGroup(fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, fileButtonPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator4)
                    .addGroup(fileButtonPaneLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(changePasswdButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addGroup(fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addGroup(fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jSeparator2)
                                    .addGroup(fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(addNoteButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(deleteNoteButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator1)
                                    .addGroup(fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(downloadFileButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(deleteFileButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(renameFileButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(authorizationButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(uploadFileButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jSeparator3)
                                    .addGroup(GroupLayout.Alignment.LEADING, fileButtonPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(targetIDField)
                                        .addComponent(searchUserButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(returnButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))))))
                .addGap(15, 15, 15))
        );

        fileSplitPane.setBackground(new Color(255, 255, 255));
        fileSplitPane.setDividerLocation(750);
        fileSplitPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        fileSplitPane.setMaximumSize(new Dimension(1366, 768));
        fileSplitPane.setPreferredSize(new Dimension(701, 646));

        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setContinuousLayout(true);
        jSplitPane2.setMaximumSize(new Dimension(1366, 768));

        fileInfoScroll.setBackground(new Color(255, 255, 255));
        fileInfoScroll.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        fileInfoScroll.getViewport().setBackground(Color.WHITE);
        fileInfoScroll.addMouseListener(formListener);

        fileInfoTable.setAutoCreateRowSorter(true);
        fileInfoTable.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        fileInfoTable.setModel(fileTableModel);
        fileInfoTable.getTableHeader().setFont(new Font("微软雅黑", 0, 12));
        fileInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        fileInfoTable.setRowHeight(20);
        fileInfoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileInfoTable.setShowHorizontalLines(false);
        fileInfoTable.setShowVerticalLines(false);
        fileInfoTable.addMouseListener(formListener);
        fileInfoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent evt) {
                fileSelectedChanged(evt);
            }
        });
        fileInfoScroll.setViewportView(fileInfoTable);

        jSplitPane2.setTopComponent(fileInfoScroll);

        noteDisplayScroll.setBackground(new Color(255, 255, 255));
        noteDisplayScroll.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        noteDisplayScroll.getViewport().setBackground(Color.WHITE);
        noteDisplayScroll.addMouseListener(formListener);

        noteTable.setAutoCreateRowSorter(true);
        noteTable.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        noteTable.setModel(noteTableModel);
        noteTable.getTableHeader().setFont(new Font("微软雅黑", 0, 12));
        noteTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        noteTable.setRowHeight(20);
        noteTable.setShowHorizontalLines(false);
        noteTable.setShowVerticalLines(false);
        noteTable.addMouseListener(formListener);
        noteTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent evt) {
                    noteSelectedChanged(evt);
                }
            }
        );
        noteDisplayScroll.setViewportView(noteTable);

        jSplitPane2.setBottomComponent(noteDisplayScroll);

        fileSplitPane.setLeftComponent(jSplitPane2);

        jSplitPane1.setDividerLocation(500);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);

        fileDisplayScroll.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        fileDisplayScroll.setMinimumSize(new Dimension(30, 30));

        fileContentArea.setEditable(false);
        fileContentArea.setColumns(20);
        fileContentArea.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        fileContentArea.setRows(5);
        fileContentArea.setToolTipText("");
        fileContentArea.setBorder(BorderFactory.createTitledBorder(null, "预览区", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("微软雅黑", 0, 12))); // NOI18N
        fileDisplayScroll.setViewportView(fileContentArea);

        jSplitPane1.setTopComponent(fileDisplayScroll);

        noteInputScroll.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        noteInputScroll.setMaximumSize(new Dimension(1366, 768));
        noteInputScroll.setMinimumSize(new Dimension(30, 30));

        noteInputArea.setColumns(20);
        noteInputArea.setFont(new Font("微软雅黑", 0, 12)); // NOI18N
        noteInputArea.setRows(5);
        noteInputArea.setToolTipText("");
        noteInputArea.setBorder(BorderFactory.createTitledBorder(null, "备注区", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("微软雅黑", 0, 12))); // NOI18N
        noteInputScroll.setViewportView(noteInputArea);

        jSplitPane1.setBottomComponent(noteInputScroll);

        fileSplitPane.setRightComponent(jSplitPane1);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(fileSplitPane, GroupLayout.DEFAULT_SIZE, 1346, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileButtonPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fileButtonPane, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fileSplitPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements ActionListener, MouseListener {
        FormListener() {}
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == uploadFileButton) {
                FilePane.this.uploadFileButtonActionPerformed(evt);
            }
            else if (evt.getSource() == downloadFileButton) {
                FilePane.this.downloadFileButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteFileButton) {
                FilePane.this.deleteFileButtonActionPerformed(evt);
            }
            else if (evt.getSource() == renameFileButton) {
                FilePane.this.renameFileButtonActionPerformed(evt);
            }
            else if (evt.getSource() == authorizationButton) {
                FilePane.this.authorizationButtonActionPerformed(evt);
            }
            else if (evt.getSource() == addNoteButton) {
                FilePane.this.addNoteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == deleteNoteButton) {
                FilePane.this.deleteNoteButtonActionPerformed(evt);
            }
            else if (evt.getSource() == searchUserButton) {
                FilePane.this.searchUserButtonActionPerformed(evt);
            }
            else if (evt.getSource() == returnButton) {
                FilePane.this.returnButtonActionPerformed(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FilePane.this.refreshButtonActionPerformed(evt);
            }
            else if (evt.getSource() == changePasswdButton) {
                FilePane.this.changePasswdButtonActionPerformed(evt);
            }
        }

        public void mouseClicked(MouseEvent evt) {
            if (evt.getSource() == fileInfoScroll) {
                FilePane.this.fileInfoScrollMouseClicked(evt);
            }
            else if (evt.getSource() == fileInfoTable) {
                FilePane.this.fileInfoTableMouseClicked(evt);
            }
            else if (evt.getSource() == noteDisplayScroll) {
                FilePane.this.noteDisplayScrollMouseClicked(evt);
            }
            else if (evt.getSource() == noteTable) {
                FilePane.this.noteTableMouseClicked(evt);
            }
        }

        public void mouseEntered(MouseEvent evt) {
            if (evt.getSource() == uploadFileButton) {
                FilePane.this.uploadFileButtonMouseEntered(evt);
            }
            else if (evt.getSource() == downloadFileButton) {
                FilePane.this.downloadFileButtonMouseEntered(evt);
            }
            else if (evt.getSource() == deleteFileButton) {
                FilePane.this.deleteFileButtonMouseEntered(evt);
            }
            else if (evt.getSource() == renameFileButton) {
                FilePane.this.renameFileButtonMouseEntered(evt);
            }
            else if (evt.getSource() == authorizationButton) {
                FilePane.this.authorizationButtonMouseEntered(evt);
            }
            else if (evt.getSource() == addNoteButton) {
                FilePane.this.addNoteButtonMouseEntered(evt);
            }
            else if (evt.getSource() == deleteNoteButton) {
                FilePane.this.deleteNoteButtonMouseEntered(evt);
            }
            else if (evt.getSource() == searchUserButton) {
                FilePane.this.searchUserButtonMouseEntered(evt);
            }
            else if (evt.getSource() == returnButton) {
                FilePane.this.returnButtonMouseEntered(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FilePane.this.refreshButtonMouseEntered(evt);
            }
            else if (evt.getSource() == changePasswdButton) {
                FilePane.this.changePasswdButtonMouseEntered(evt);
            }
        }

        public void mouseExited(MouseEvent evt) {
            if (evt.getSource() == uploadFileButton) {
                FilePane.this.uploadFileButtonMouseExited(evt);
            }
            else if (evt.getSource() == downloadFileButton) {
                FilePane.this.downloadFileButtonMouseExited(evt);
            }
            else if (evt.getSource() == deleteFileButton) {
                FilePane.this.deleteFileButtonMouseExited(evt);
            }
            else if (evt.getSource() == renameFileButton) {
                FilePane.this.renameFileButtonMouseExited(evt);
            }
            else if (evt.getSource() == authorizationButton) {
                FilePane.this.authorizationButtonMouseExited(evt);
            }
            else if (evt.getSource() == addNoteButton) {
                FilePane.this.addNoteButtonMouseExited(evt);
            }
            else if (evt.getSource() == deleteNoteButton) {
                FilePane.this.deleteNoteButtonMouseExited(evt);
            }
            else if (evt.getSource() == searchUserButton) {
                FilePane.this.searchUserButtonMouseExited(evt);
            }
            else if (evt.getSource() == returnButton) {
                FilePane.this.returnButtonMouseExited(evt);
            }
            else if (evt.getSource() == refreshButton) {
                FilePane.this.refreshButtonMouseExited(evt);
            }
            else if (evt.getSource() == changePasswdButton) {
                FilePane.this.changePasswdButtonMouseExited(evt);
            }
        }

        public void mousePressed(MouseEvent evt) {
        }

        public void mouseReleased(MouseEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents
    
    private void authorizationButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_authorizationButtonActionPerformed
        CloudFile selectedFile=getSelectedFile();
        if (selectedFile==null){
            JOptionPane.showMessageDialog(this, "请选择文件", "修改权限", JOptionPane.WARNING_MESSAGE);
            return;
        }

        AuthorizationWindow authorizationWindow = new AuthorizationWindow(m_MainFrame);
        if (!authorizationWindow.isConfirmed()){
            //fileInfoTable.clearSelection();
            return;
        }

        try {
            Authorization authorization=authorizationWindow.getAuthorization();
            AuthorizationResult result = m_Business.setAuthorization(selectedFile, authorization);
            switch (result) {
                case OK:
                    JOptionPane.showMessageDialog(m_MainFrame, "修改成功", "修改权限", JOptionPane.INFORMATION_MESSAGE);
                    getDirectory(currentID);
                break;
                    case unAuthorized:
                    JOptionPane.showMessageDialog(m_MainFrame, "您未登录，请重新登录", "修改权限", JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog(m_MainFrame, "文件不存在，请重新选择", "修改权限", JOptionPane.WARNING_MESSAGE);
                    getDirectory(currentID);
                    break;
                default:
                    JOptionPane.showMessageDialog(m_MainFrame, "未知错误", null, JOptionPane.ERROR_MESSAGE);
                break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(m_MainFrame, "网络错误", "修改权限", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_authorizationButtonActionPerformed

    private void renameFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_renameFileButtonActionPerformed
        CloudFile selectedFile=getSelectedFile();
        if (selectedFile==null){
            JOptionPane.showMessageDialog(this, "请选择文件", "重命名", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newFilename=JOptionPane.showInputDialog(m_MainFrame,"请输入新文件名");
        if (newFilename==null){
            //fileInfoTable.clearSelection();
            return;
        }

        if (newFilename.equals("")){
            JOptionPane.showMessageDialog(this, "请输入正确的文件名", "重命名", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            RenameFileResult result = m_Business.renameFile(selectedFile,newFilename);
            switch (result) {
                case OK:
                    JOptionPane.showMessageDialog(this, "重命名成功", "重命名", JOptionPane.INFORMATION_MESSAGE);
                    getDirectory(currentID);
                    break;
                case unAuthorized:
                    JOptionPane.showMessageDialog(this, "您未登录，请重新登录", "重命名", JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog(this, "文件不存在，请重新选择", "重命名", JOptionPane.WARNING_MESSAGE);
                    getDirectory(currentID);
                    break;
                case repeatedName:
                    JOptionPane.showMessageDialog(this, "与已有文件重名", "重命名", JOptionPane.WARNING_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(m_MainFrame, "未知错误", "重命名", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(m_MainFrame, "网络错误", "重命名", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_renameFileButtonActionPerformed

    private void deleteFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_deleteFileButtonActionPerformed
        CloudFile selectedFile=getSelectedFile();
        if (selectedFile==null){
            JOptionPane.showMessageDialog(this, "请选择文件", "删除文件", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            DeleteFileResult result = m_Business.deleteFile(selectedFile);
            switch (result) {
                case OK:
                    JOptionPane.showMessageDialog(this, "删除成功",  "删除文件", JOptionPane.INFORMATION_MESSAGE);
                    getDirectory(currentID);
                    break;
                case unAuthorized:
                    JOptionPane.showMessageDialog(this, "您未登录，请重新登录",  "删除文件", JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog(this, "文件不存在，请重新选择", "删除文件", JOptionPane.WARNING_MESSAGE);
                    getDirectory(currentID);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "未知错误",  "删除文件", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "网络错误",  "删除文件", JOptionPane.ERROR_MESSAGE);
        }
        //fileInfoTable.clearSelection();
    }//GEN-LAST:event_deleteFileButtonActionPerformed

    private void downloadFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_downloadFileButtonActionPerformed
        CloudFile selectedFile=getSelectedFile();
        if (selectedFile==null){
            JOptionPane.showMessageDialog(m_MainFrame, "请选择文件", "下载文件", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DownloadFileResult result;
        try {
            result = m_Business.downloadFile(selectedFile);
            byte[] content=result.getContent();
            DownloadFileResult.DownloadFileStatus status=result.getResult();
            switch(status){
                case OK:
                    DownloadFileWindow downloadWindow = new DownloadFileWindow(m_MainFrame,selectedFile,content);
                    break;
                case unAuthorized:
                    JOptionPane.showMessageDialog
                    (this,"您未登录，请重新登录", "下载文件",JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog
                    (this,"文件不存在，请重新选择", "下载文件",JOptionPane.WARNING_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog
                    (this,"未知错误", "下载文件",JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }  catch (IOException e) {
            JOptionPane.showMessageDialog
            (this,"网络错误", "下载文件",JOptionPane.ERROR_MESSAGE);
        }
        //fileInfoTable.clearSelection();
    }//GEN-LAST:event_downloadFileButtonActionPerformed

    private void uploadFileButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_uploadFileButtonActionPerformed
        UploadFileWindow uploadWindow = new UploadFileWindow(m_MainFrame);
        if (!uploadWindow.isConfirmed()){
            //fileInfoTable.clearSelection();
            return;
        }

        String filename=uploadWindow.getFileName();
        String filePath=uploadWindow.getFilePath();

        UploadFileResult result;
        try {
            result = m_Business.uploadFile(filename, filePath);
            switch(result){
                case OK:
                    JOptionPane.showMessageDialog
                    (this,"上传成功", "上传文件",JOptionPane.INFORMATION_MESSAGE);
                    break;
                case tooLarge:
                    JOptionPane.showMessageDialog
                    (this,"文件过大，请重新选择","上传文件",JOptionPane.WARNING_MESSAGE);
                    break;
                case unAuthorized:
                    JOptionPane.showMessageDialog
                    (this,"您未登录，请重新登录","上传文件",JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog
                    (this,"文件不存在，请重新选择","上传文件",JOptionPane.WARNING_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog
                    (this,"未知错误","上传文件",JOptionPane.ERROR_MESSAGE);
                    break;

            }
        }  catch (IOException e) {
            JOptionPane.showMessageDialog
            (this,"网络错误","上传文件",JOptionPane.ERROR_MESSAGE);
        }
        getDirectory(currentID);
        //fileInfoTable.clearSelection();
    }//GEN-LAST:event_uploadFileButtonActionPerformed

    private void uploadFileButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_uploadFileButtonMouseEntered
        if (uploadFileButton.isEnabled())
            uploadFileButton.setContentAreaFilled(true);
    }//GEN-LAST:event_uploadFileButtonMouseEntered

    private void uploadFileButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_uploadFileButtonMouseExited
        uploadFileButton.setContentAreaFilled(false);
    }//GEN-LAST:event_uploadFileButtonMouseExited

    private void fileInfoTableMouseClicked(MouseEvent evt) {//GEN-FIRST:event_fileInfoTableMouseClicked
        refreshNoteAndText();
    }//GEN-LAST:event_fileInfoTableMouseClicked

    private void noteTableMouseClicked(MouseEvent evt) {//GEN-FIRST:event_noteTableMouseClicked
//        Note selectedNote=getSelectedNote();
//        if (selectedNote==null){   //无文件被选中
//            return;
//        }
//        String userID=m_Business.getUser().getUserID();
//        if (!selectedNote.getCreator().equals(userID))
//            deleteNoteButton.setEnabled(false);
//        else
//            deleteNoteButton.setEnabled(true);
    }//GEN-LAST:event_noteTableMouseClicked

    private void downloadFileButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_downloadFileButtonMouseEntered
        downloadFileButton.setContentAreaFilled(true);
    }//GEN-LAST:event_downloadFileButtonMouseEntered

    private void downloadFileButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_downloadFileButtonMouseExited
        downloadFileButton.setContentAreaFilled(false);
    }//GEN-LAST:event_downloadFileButtonMouseExited

    private void deleteFileButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_deleteFileButtonMouseEntered
        deleteFileButton.setContentAreaFilled(true);
    }//GEN-LAST:event_deleteFileButtonMouseEntered

    private void deleteFileButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_deleteFileButtonMouseExited
        deleteFileButton.setContentAreaFilled(false);
    }//GEN-LAST:event_deleteFileButtonMouseExited

    private void renameFileButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_renameFileButtonMouseEntered
        renameFileButton.setContentAreaFilled(true);
    }//GEN-LAST:event_renameFileButtonMouseEntered

    private void renameFileButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_renameFileButtonMouseExited
        renameFileButton.setContentAreaFilled(false);
    }//GEN-LAST:event_renameFileButtonMouseExited

    private void authorizationButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_authorizationButtonMouseEntered
        authorizationButton.setContentAreaFilled(true);
    }//GEN-LAST:event_authorizationButtonMouseEntered

    private void authorizationButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_authorizationButtonMouseExited
        authorizationButton.setContentAreaFilled(false);
    }//GEN-LAST:event_authorizationButtonMouseExited

    private void addNoteButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_addNoteButtonMouseEntered
        addNoteButton.setContentAreaFilled(true);
    }//GEN-LAST:event_addNoteButtonMouseEntered

    private void addNoteButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_addNoteButtonMouseExited
        addNoteButton.setContentAreaFilled(false);
    }//GEN-LAST:event_addNoteButtonMouseExited

    private void addNoteButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_addNoteButtonActionPerformed
        String content=noteInputArea.getText();
        if (content.equals(""))
            return;
        try {
            String fileID=getSelectedFile().getFileID();
            String creator=m_Business.getUser().getUserID();
            Note note=new Note(content,fileID,creator);
            AddNoteResult result = m_Business.addNote(note);
            switch (result) {
                case OK:
                    JOptionPane.showMessageDialog(this, "添加备注成功", "添加备注", JOptionPane.INFORMATION_MESSAGE);
                    refreshNoteAndText();
                    break;
                case unAuthorized:
                    JOptionPane.showMessageDialog(this, "您未登录，请重新登录", "添加备注", JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog(this, "文件不存在", "添加备注", JOptionPane.WARNING_MESSAGE);
                    getDirectory(currentID);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "未知错误", "添加备注", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "网络错误", "添加备注", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_addNoteButtonActionPerformed

    private void deleteNoteButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_deleteNoteButtonMouseEntered
        deleteNoteButton.setContentAreaFilled(true);
    }//GEN-LAST:event_deleteNoteButtonMouseEntered

    private void deleteNoteButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_deleteNoteButtonMouseExited
        deleteNoteButton.setContentAreaFilled(false);
    }//GEN-LAST:event_deleteNoteButtonMouseExited

    private void deleteNoteButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_deleteNoteButtonActionPerformed
        Note selectedNote=getSelectedNote();
        if (selectedNote==null){
            JOptionPane.showMessageDialog(this, "请选择备注", "删除备注", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            DeleteNoteResult result = m_Business.deleteNote(selectedNote);
            switch (result) {
                case OK:
                    JOptionPane.showMessageDialog(this, "删除备注成功", "删除备注", JOptionPane.INFORMATION_MESSAGE);
                    refreshNoteAndText();
                    break;
                case unAuthorized:
                    JOptionPane.showMessageDialog(this, "您未登录，请重新登录", "删除备注", JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog(this, "文件不存在", "删除备注", JOptionPane.WARNING_MESSAGE);
                    getDirectory(currentID);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "未知错误","删除备注", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "网络错误","删除备注", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteNoteButtonActionPerformed

    private void searchUserButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_searchUserButtonMouseEntered
        searchUserButton.setContentAreaFilled(true);
    }//GEN-LAST:event_searchUserButtonMouseEntered

    private void searchUserButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_searchUserButtonMouseExited
        searchUserButton.setContentAreaFilled(false);
    }//GEN-LAST:event_searchUserButtonMouseExited

    private void searchUserButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_searchUserButtonActionPerformed
        String targetID=targetIDField.getText();
        if (!targetID.equals("")){  //输入不为空白
            getDirectory(targetID);
            clearNoteAndText();
        }
    }//GEN-LAST:event_searchUserButtonActionPerformed

    private void returnButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_returnButtonMouseEntered
        returnButton.setContentAreaFilled(true);
    }//GEN-LAST:event_returnButtonMouseEntered

    private void returnButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_returnButtonMouseExited
        returnButton.setContentAreaFilled(false);
    }//GEN-LAST:event_returnButtonMouseExited

    private void returnButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_returnButtonActionPerformed
        //currentID=m_Business.getUser().getUserID();
        String targetID=m_Business.getUser().getUserID();
        getDirectory(targetID);
        clearNoteAndText();
    }//GEN-LAST:event_returnButtonActionPerformed

    private void refreshButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseEntered
        refreshButton.setContentAreaFilled(true);
    }//GEN-LAST:event_refreshButtonMouseEntered

    private void refreshButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_refreshButtonMouseExited
        refreshButton.setContentAreaFilled(false);
    }//GEN-LAST:event_refreshButtonMouseExited

    private void refreshButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        getDirectory(currentID);
        clearNoteAndText();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void fileInfoScrollMouseClicked(MouseEvent evt) {//GEN-FIRST:event_fileInfoScrollMouseClicked
        Point p=evt.getPoint();
        if (!fileInfoTable.contains(p)){    //单击表格外的空白区域取消选择或排序
            fileInfoTable.clearSelection();
            fileInfoTable.getRowSorter().setSortKeys(null);
            fileInfoTable.repaint();
            clearNoteAndText();
        }
    }//GEN-LAST:event_fileInfoScrollMouseClicked

    private void noteDisplayScrollMouseClicked(MouseEvent evt) {//GEN-FIRST:event_noteDisplayScrollMouseClicked
        Point p=evt.getPoint();
        if (!noteTable.contains(p)){        //单击表格外的空白区域取消选择或排序
            noteTable.clearSelection();
            noteTable.getRowSorter().setSortKeys(null);
            noteTable.repaint();
        }
    }//GEN-LAST:event_noteDisplayScrollMouseClicked

    private void changePasswdButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_changePasswdButtonMouseEntered
        changePasswdButton.setContentAreaFilled(true);
    }//GEN-LAST:event_changePasswdButtonMouseEntered

    private void changePasswdButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_changePasswdButtonMouseExited
        changePasswdButton.setContentAreaFilled(false);
    }//GEN-LAST:event_changePasswdButtonMouseExited

    private void changePasswdButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_changePasswdButtonActionPerformed
        ChangePasswdWindow changePasswdWindow = new ChangePasswdWindow(m_MainFrame,m_Business);
    }//GEN-LAST:event_changePasswdButtonActionPerformed
    
    
    private void noteSelectedChanged(ListSelectionEvent evt){
        if (!evt.getValueIsAdjusting()){
            int rowIndex=noteTable.getSelectedRow();
            if (rowIndex!=-1)
                rowIndex=noteTable.convertRowIndexToModel(rowIndex);
            if (rowIndex==-1){
                deleteNoteButton.setEnabled(false);
            }
            else{
                String userID=m_Business.getUser().getUserID();
                if (!noteTableModel.getValueAt(rowIndex).getCreator().equals(userID))
                    deleteNoteButton.setEnabled(false);
                else
                    deleteNoteButton.setEnabled(true);
            }
        }
    }
    
    private void fileSelectedChanged(ListSelectionEvent evt){
        if (!evt.getValueIsAdjusting()){
            String userID=m_Business.getUser().getUserID();
            int rowIndex=fileInfoTable.getSelectedRow();
            if (rowIndex!=-1)
                rowIndex=fileInfoTable.convertRowIndexToModel(rowIndex);
            if (this.currentID.equals(userID)){ //自己界面
                if (rowIndex==-1){  //无文件选中
                    deleteFileButton.setEnabled(false);
                    renameFileButton.setEnabled(false);
                    authorizationButton.setEnabled(false);
                    downloadFileButton.setEnabled(false);
                    deleteNoteButton.setEnabled(false);
                    addNoteButton.setEnabled(false);
                    deleteNoteButton.setEnabled(false);
                }
                else{
                    deleteFileButton.setEnabled(true);
                    renameFileButton.setEnabled(true);
                    authorizationButton.setEnabled(true);
                    downloadFileButton.setEnabled(true);
                    addNoteButton.setEnabled(true);
                }
            }
            else{                   //他人界面下
                if (rowIndex==-1){  //无文件选中
                    downloadFileButton.setEnabled(false);
                    addNoteButton.setEnabled(false);
                    deleteNoteButton.setEnabled(false);
                }
                else{
                    downloadFileButton.setEnabled(true);
                    addNoteButton.setEnabled(true);
                }
            }
        }
    }
    
    private CloudFile getSelectedFile(){
        int rowIndex=fileInfoTable.getSelectedRow();
        if (rowIndex!=-1)
            rowIndex=fileInfoTable.convertRowIndexToModel(rowIndex);
        CloudFile selectedFile;
        if (rowIndex!=-1)
            selectedFile= fileTableModel.getValueAt(rowIndex);
        else
            selectedFile=null;
        return selectedFile;
    }
    
    private Note getSelectedNote(){
        //NoteTableModel model = (NoteTableModel) noteTable.getModel();
        int rowIndex=noteTable.getSelectedRow();
        if (rowIndex!=-1)
            rowIndex=noteTable.convertRowIndexToModel(rowIndex);
        Note selectedNote;
        if (rowIndex!=-1)
            selectedNote= noteTableModel.getValueAt(rowIndex);
        else
            selectedNote=null;
        return selectedNote;
    }
    
    private void getDirectory(String targetID) {
        FileDirectoryResult result;
        try {
            result=m_Business.getDirectory(targetID);
            FileDirectoryStatus status=result.getResult();
            switch(status){
            case OK:
                ArrayList<CloudFile> directory=result.getFileDirectory();
                Collections.sort(directory);
                fileInfoTable.clearSelection();
                fileInfoTable.getRowSorter().setSortKeys(null);
                fileTableModel.setDirectory(directory);
                fileInfoTable.setModel(fileTableModel);
                fileInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                fileInfoTable.setEnabled(true);
                setCurrentID(targetID);
                //fileInfoTable.repaint();
                repaint();
                break;
            case unAuthorized:
                JOptionPane.showMessageDialog(m_MainFrame, "您未登录，请重新登录", "加载文件目录", JOptionPane.WARNING_MESSAGE);
                m_MainFrame.setVisible(false);
                LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                break;
            case wrong:
                JOptionPane.showMessageDialog(m_MainFrame, "用户不存在，加载目录失败", "加载文件目录", JOptionPane.WARNING_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(m_MainFrame, "未知错误", "加载文件目录", JOptionPane.ERROR_MESSAGE);
                break;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(m_MainFrame, "网络错误", "加载文件目录", JOptionPane.ERROR_MESSAGE);
        }
        clearNoteAndText();
        fileInfoTable.clearSelection();
    }
    
    private void clearNoteAndText(){
        noteTable.getRowSorter().setSortKeys(null);
        noteTableModel.setNoteList(new ArrayList<Note>());
        noteTable.setModel(noteTableModel);
        noteTable.clearSelection();
        noteTable.repaint();
        fileContentArea.setText("");
        fileContentArea.repaint();
    }
    
    private void refreshNoteAndText(){
        CloudFile selectedFile=getSelectedFile();
        if (selectedFile==null){   //无文件被选中
            clearNoteAndText();    //清除note和文件区
        }
        else{
            try{        
                NoteListResult noteListResult=m_Business.getNoteList(selectedFile);
                NoteListStatus noteStatus=noteListResult.getResult();
                switch (noteStatus){
                case OK:
                    ArrayList<Note> noteList=noteListResult.getNoteList();
                    Collections.sort(noteList);
                    noteTable.getRowSorter().setSortKeys(null);
                    noteTableModel.setNoteList(noteList);
                    noteTable.setModel(noteTableModel);
                    noteTable.clearSelection();
                    noteTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                      if (selectedFile.getFilename().endsWith(".txt")){
                        DownloadFileResult downloadFileResult=m_Business.downloadFile(selectedFile);
                        DownloadFileStatus fileStatus=downloadFileResult.getResult();
                        switch (fileStatus){
                            case OK:
                                String content=IOUtils.toString(downloadFileResult.getContent(),"gbk");
                                fileContentArea.setText(content);
                                noteTable.repaint();
                                fileContentArea.repaint();
                                break;
                            case unAuthorized:
                                JOptionPane.showMessageDialog(m_MainFrame, "您未登录，请重新登录", "预览文件", JOptionPane.WARNING_MESSAGE);
                                m_MainFrame.setVisible(false);
                                LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                                break;
                            case wrong:
                                JOptionPane.showMessageDialog(m_MainFrame, "文件不存在", "预览文件", JOptionPane.WARNING_MESSAGE);
                                getDirectory(currentID);
                                break;
                            default:
                                JOptionPane.showMessageDialog(m_MainFrame, "未知错误", "预览文件", JOptionPane.ERROR_MESSAGE);
                                break;
                        }
                    }
                    else{
                        fileContentArea.setText("");
                        noteTable.repaint();
                        fileContentArea.repaint();
                    }
                    break;
                case unAuthorized:
                    JOptionPane.showMessageDialog(m_MainFrame, "您未登录，请重新登录", "加载备注", JOptionPane.WARNING_MESSAGE);
                    m_MainFrame.setVisible(false);
                    LoginDialog loginDialog=new LoginDialog(m_MainFrame,m_Business);
                    break;
                case wrong:
                    JOptionPane.showMessageDialog(m_MainFrame, "文件不存在", "加载备注", JOptionPane.WARNING_MESSAGE);
                    getDirectory(currentID);
                    break;
                default:
                    JOptionPane.showMessageDialog(m_MainFrame, "未知错误", "加载备注", JOptionPane.ERROR_MESSAGE);
                    break;
                }
   
            } catch(IOException e){
                JOptionPane.showMessageDialog(m_MainFrame, "网络错误", "加载备注", JOptionPane.ERROR_MESSAGE);
                clearNoteAndText();
            }
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    JButton addNoteButton;
    JButton authorizationButton;
    JButton changePasswdButton;
    JButton deleteFileButton;
    JButton deleteNoteButton;
    JButton downloadFileButton;
    JPanel fileButtonPane;
    JTextArea fileContentArea;
    JScrollPane fileDisplayScroll;
    JScrollPane fileInfoScroll;
    JTable fileInfoTable;
    JSplitPane fileSplitPane;
    JSeparator jSeparator1;
    JSeparator jSeparator2;
    JSeparator jSeparator3;
    JSeparator jSeparator4;
    JSplitPane jSplitPane1;
    JSplitPane jSplitPane2;
    JScrollPane noteDisplayScroll;
    JTextArea noteInputArea;
    JScrollPane noteInputScroll;
    JTable noteTable;
    JButton refreshButton;
    JButton renameFileButton;
    JButton returnButton;
    JButton searchUserButton;
    JTextField targetIDField;
    JButton uploadFileButton;
    // End of variables declaration//GEN-END:variables
}
