package gui;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bfz_project.obj.Extra;

import java.awt.*;

public class ExtrasSelection extends JPanel{

    
    private ArrayList<Extra> allAvaleableExtras;
    private JButton commit;
    private JButton addExtra;

    private JComboBox<Extra>[] extrasBox = new JComboBox[2];
    private JComboBox<Integer>[] extrasAmmountBox = new JComboBox[2];


    ExtrasSelection(ArrayList<Extra> allAvaleableExtras){
        this.allAvaleableExtras = allAvaleableExtras;
        this.setBounds(0, 0, 200, 300);
        this.setLayout(null);
        JLabel extra = new JLabel("Extras");
        extra.setBounds(5, 5, 50, 20);
        this.add(extra);

        JLabel singleBedLabel = new JLabel("Single bed");
        singleBedLabel.setBounds(5,30,100,20);
        this.add(singleBedLabel);
        this.add(setPanel(0));

        JLabel doubleBedRooms = new JLabel("Double bed");
        doubleBedRooms.setBounds(5, 80, 100, 20);
        this.add(doubleBedRooms);
        this.add(setPanel(1));
        

        addExtra = new JButton("Add Extras");
        addExtra.setBounds(45, 130, 100, 20);
        this.add(addExtra);

        commit = new JButton("submit");
        commit.setBounds(45, 155, 100, 20);
        this.add(commit);

        this.setVisible(false);
    }

    public JButton getSubmit(){
        return commit;
    }
    
    public JComboBox<Extra>[] getExtrasBox() {
        return extrasBox;
    }

    public JComboBox<Integer>[] getExtrasAmmountBox() {
        return extrasAmmountBox;
    }

    public JButton getAddExtra() {
        return addExtra;
    }

    //initialises dropdowns for extras exposed as a function, because used more then once
    public JPanel setPanel(int i){
        JPanel panel = new JPanel();
        panel.setBounds(5, 55+i*50, 180, 20);
        panel.setLayout(new GridLayout(1,2));
        extrasBox[i] = new JComboBox<Extra>();
        for(Extra e : allAvaleableExtras){
            extrasBox[i].addItem(e);
        }
        extrasAmmountBox[i] = new JComboBox<Integer>();
        panel.add(extrasBox[i]);
        panel.add(extrasAmmountBox[i]);
        return panel;
    }

    //sets up the ammount of possible single and double bed extras so you can't
    //order more extras then rooms
    public void setUpDropdowns(int singleBed, int doubleBed){
        for(int i = 0; i<=singleBed;i++){
            extrasAmmountBox[0].addItem(i);
        }
        for(int i = 0; i<=doubleBed;i++){
            extrasAmmountBox[1].addItem(i);
        }
    }
}
