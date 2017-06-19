package test;

import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class TestJDialog {
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
        JFrame mainFrame=new JFrame();
        mainFrame.setSize(200, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("OptionPane.font", new Font("微软雅黑", Font.PLAIN, 12));
        UIManager.put("TextField.font",new Font("微软雅黑", Font.PLAIN, 12));
        JOptionPane.showConfirmDialog(null, "test");
        JOptionPane.showInputDialog("mainFrame", "Input");
        
    }
}
