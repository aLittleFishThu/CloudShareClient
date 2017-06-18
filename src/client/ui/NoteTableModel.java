/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.ui;

import java.util.ArrayList;

import common.Note;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yzj
 */
@SuppressWarnings("serial")
public class NoteTableModel extends AbstractTableModel{
    private ArrayList<Note> m_NoteList;
    private String[] m_TableHeader;
    
    public NoteTableModel(ArrayList<Note> noteList){
        super();
        m_NoteList=noteList;
        m_TableHeader=new String[]{"上传时间","创建者","内容"};
    }
    
    public ArrayList<Note> getNoteList() {
        return m_NoteList;
    }

    public void setNoteList(ArrayList<Note> noteList) {
        m_NoteList = noteList;
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
        return m_NoteList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Note note=m_NoteList.get(rowIndex);
        switch(columnIndex){
        case 0:
            return note.getUploadTime();
        case 1:
            return note.getCreator();
        case 2:
            return note.getContent();
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
    
    public Note getValueAt(int rowIndex){
        return m_NoteList.get(rowIndex);
    }
}
