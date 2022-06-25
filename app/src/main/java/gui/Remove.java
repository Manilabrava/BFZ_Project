package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;

public class Remove extends JPanel{

    private JLabel name = new JLabel("Booking Number");
    private JTextField bookingID;
    private JButton remove;


    public JTextField getBookingID() {
        return bookingID;
    }


    Remove(){
        this.setLayout(new GridLayout(3,1));
        this.setBounds(50, 50, 100, 60);
        bookingID = new JTextField();
        this.add(name);
        this.add(bookingID);
        remove = new JButton("cancel");
        this.add(remove);
        this.setVisible(false);
    }

    public JButton getRemove(){
        return remove;
    }
    
}
