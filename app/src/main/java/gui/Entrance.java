package gui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;

public class Entrance extends JPanel{

    private JButton[] buttons = new JButton[2];

    private String[] s = {"book", "cancel"};

    Entrance(){
        this.setLayout(new GridLayout(2,1));
        for(int i = 0; i<2;i++){
            buttons[i] = new JButton(s[i]);
            this.add(buttons[i]);
        }
    }

    JButton[] getButtons() {
        return buttons;
    }
    
}
