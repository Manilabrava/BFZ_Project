package gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bfz_project.obj.Room;
import bfz_project.obj.RoomType;

import java.awt.*;
import java.util.ArrayList;

public class RoomSelection extends JPanel{

    private JLabel singleRoomAmmount;
    private JLabel doubleRoomAmmount;
    private JComboBox<Integer>[] roomSelection = new JComboBox[2];
    private JLabel[] roomLables = new JLabel[2];
    private String[] lableNemes = {"Single Bed","Double Bed"};
    private JButton submit;
    private JComboBox<Integer> adultSelection;

    RoomSelection(){
        JPanel jp = new JPanel();
        this.setLayout(new GridLayout(4,1));
        jp.setLayout(new GridLayout(3,2));
        this.setBounds(0, 50, 180, 140);

        singleRoomAmmount = new JLabel();
        doubleRoomAmmount = new JLabel();

        for(int i = 0; i<2;i++){
            roomSelection[i] = new JComboBox<Integer>();
            roomLables[i] = new JLabel(lableNemes[i]);
            jp.add(roomLables[i]);
            jp.add(roomSelection[i]);
        }
        JLabel adults = new JLabel("Adults");
        adultSelection = new JComboBox<Integer>();
        jp.add(adults);
        jp.add(adultSelection);

        this.add(singleRoomAmmount);
        this.add(doubleRoomAmmount);
        this.add(jp);

        submit = new JButton("submit");
        this.add(submit);

        this.setVisible(false);
    }

    public JButton getSubmit(){
        return submit;
    }

    public JComboBox<Integer>[] getroomSelection(){
        return roomSelection;
    }

    public JComboBox<Integer> getAdultSelection() {
        return adultSelection;
    }

    //shows the ammount of avaleable rooms and sets up dropdowns
    // so only this ammount of rooms can be selected
    public void setUpRoomAmmount(int[] freeRooms){
        singleRoomAmmount.setText(freeRooms[0]+" Single Bed Room");
        doubleRoomAmmount.setText(freeRooms[1] + " Double Bed Room");
        for(int i = 0; i<=freeRooms[0];i++){
            roomSelection[0].addItem(i);
        }
        for(int i = 0; i<=freeRooms[1];i++){
            roomSelection[1].addItem(i);
        }

        int maxAdults = freeRooms[0]+freeRooms[1]*2;

        for(int i = 0; i<=maxAdults;i++){
            adultSelection.addItem(i);
        }
    }

    //just an initialisation for setUpRoomAmmount takes an array of free rooms
    //and returns an int array with mapping 0->ammount of single bed rooms 1-> ammount of double bed rooms
    public int[] calculateFreeRoomsAmmounts(ArrayList<Room> rooms){
        int[] freeRooms = new int[2];
        for(Room r : rooms){
            if(r.getType().getTypeID()==RoomType.SIGLE_BED_ROOM_ID){
                freeRooms[0]++; 
            }
            else{
                freeRooms[1]++;
            }
        }
        return freeRooms;
    }
    
}
