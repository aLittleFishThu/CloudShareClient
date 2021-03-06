package client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.Font;

public class TestLoginDialog extends JDialog {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            TestLoginDialog dialog = new TestLoginDialog();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public TestLoginDialog() {
        setBounds(100, 100, 450, 300);
        SpringLayout springLayout = new SpringLayout();
        getContentPane().setLayout(springLayout);
        {
            JLabel titleLabel = new JLabel("\u767B\u5F55\u4E91\u534F\u4F5C\u8D26\u53F7");
            springLayout.putConstraint(SpringLayout.NORTH, titleLabel, 10, SpringLayout.NORTH, getContentPane());
            springLayout.putConstraint(SpringLayout.WEST, titleLabel, 152, SpringLayout.WEST, getContentPane());
            springLayout.putConstraint(SpringLayout.EAST, titleLabel, -170, SpringLayout.EAST, getContentPane());
            titleLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
            getContentPane().add(titleLabel, BorderLayout.NORTH);
        }
    }

}
