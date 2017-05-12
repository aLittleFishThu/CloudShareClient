/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui;

import java.util.ArrayList;

import common.Authorization;
import common.CloudFile;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yzj
 */
@SuppressWarnings("serial")
public class FileTableModel extends AbstractTableModel{
    private ArrayList<CloudFile> m_Directory;
    private String[] m_TableHeader;
    
    public FileTableModel(ArrayList<CloudFile> directory){
        super();
        m_Directory=directory;
        m_TableHeader=new String[]{"文件名称","上传时间","创建者","权限"};
    }
    
    public ArrayList<CloudFile> getDirectory() {
        return m_Directory;
    }

    public void setDirectory(ArrayList<CloudFile> directory) {
        m_Directory = directory;
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        // TODO 自动生成的方法存根
        
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getColumnCount() {
       return m_TableHeader.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return m_TableHeader[columnIndex];
    }

    @Override
    public int getRowCount() {
        return m_Directory.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CloudFile file=m_Directory.get(rowIndex);
        switch(columnIndex){
        case 0:
            return file.getFilename();
        case 1:
            return file.getUploadTime();
        case 2:
            return file.getCreator();
        case 3:
            Authorization authorization=file.getAuthorization();
            switch(authorization){
            case open:
                return new String("公开");
            case self:
                return new String("仅对自己可见");
            case toFriend:
                return new String("对部分人可见");
            default:
                break;
            }
        default:
           return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // TODO 自动生成的方法存根
        return false;
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        // TODO 自动生成的方法存根
        
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO 自动生成的方法存根
        
    }
    
    public CloudFile getValueAt(int rowIndex){
        return m_Directory.get(rowIndex);
    }
}
