package client.ui;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import common.Note;

@SuppressWarnings({ "rawtypes", "serial" })
public class NoteListModel extends AbstractListModel{

    private ArrayList<Note> m_NoteList;
    
    public NoteListModel(ArrayList<Note> noteArrayList){
        super();
        m_NoteList=noteArrayList;
    }
    
    @Override
    public Object getElementAt(int index) {
        if (m_NoteList.isEmpty())
            return null;
        Note note=m_NoteList.get(index);
        String s;
        s=note.getUploadTime()+' '+note.getCreator()+'\n'+note.getContent();
        return s;
    }

    @Override
    public int getSize() {
        
        return m_NoteList.size();
    }

    public ArrayList<Note> getNoteList() {
        return m_NoteList;
    }

    public void setNoteList(ArrayList<Note> noteArrayList) {
        this.m_NoteList = noteArrayList;
    }

    public Note getNoteInfoAt(int index){
        if (m_NoteList.isEmpty())
            return null;
        return m_NoteList.get(index);
    }
}
