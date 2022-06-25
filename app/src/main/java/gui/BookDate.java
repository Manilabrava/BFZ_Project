package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.*;

public class BookDate extends JPanel{

    private JLabel[] names = new JLabel[2];
    private JTextField[] dates = new JTextField[2];
    private String[] s = {"start Date", "end Date"};
    private JButton search;

    BookDate(){
        this.setLayout(new GridLayout(5,1));
        this.setBounds(50, 50, 100, 80);
        for(int i = 0;i<2;i++){
            names[i] = new JLabel(s[i]);
            dates[i] = new JTextField();
            this.add(names[i]);
            this.add(dates[i]);
        }
        search = new JButton("search");
        this.add(search);
    }


    JTextField[] getDates(){
        return dates;
    }

    JButton getSearch(){
        return search;
    }
}
