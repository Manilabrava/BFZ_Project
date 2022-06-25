package gui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bfz_project.obj.Booking;
import bfz_project.obj.Extra;
import bfz_project.obj.Room;

import java.awt.*;

public class CheckOut extends JPanel{

    private String[] names = {"Single Bed", "Double Bed", "Extras", "von","bis","Kosten"};
    private JPanel jp;
    private JButton order;
    CheckOut(){
        this.setLayout(null);
        this.setBounds(0, 0, 200, 300);

        order = new JButton("order");
        order.setBounds(45,125,100,20);
        this.add(order);
        this.setVisible(false);

    }

    //Sets up order overview is used in an Actionlistener which switches to this site
    //displays information from the bookind instance and calculates the costs
    public void showOrder(Booking booking){

        jp = new JPanel();
        jp.setLayout(new GridLayout(6,2));
        jp.setBounds(0,0,200,120);

        int[] differentRooms = booking.getAmmountsOfDifferentTypesOfRooms();
        String extras = "";
        double extracost = 0;
        for(Room r : booking.getBookedRooms()){
            for(Extra e : r.getExtras()){
                extracost += e.getExtraCost();
                extras += e + ", ";
            }
            extras = extras.substring(0,extras.length()-2);
        }

        jp.add(new JLabel(names[0]));
        jp.add(new JLabel(Integer.toString(differentRooms[0])));
        jp.add(new JLabel(names[1]));
        jp.add(new JLabel(Integer.toString(differentRooms[1])));
        jp.add(new JLabel(names[2]));
        jp.add(new JLabel(extras));
        jp.add(new JLabel(names[3]));
        jp.add(new JLabel(booking.getFrom().toString()));
        jp.add(new JLabel(names[4]));
        jp.add(new JLabel(booking.getTo().toString()));
        jp.add(new JLabel(names[5]));
        double d = 0;

        for(Room r : booking.getBookedRooms()){
            d+=r.getType().getRoomTypeCost();
        }

        int multiplyer = (int)((booking.getTo().getTime()-booking.getFrom().getTime())/1000/60/60/24);
        d*=multiplyer;
        d+=extracost;
        jp.add(new JLabel(Double.toString(d)));
        this.add(jp);
    }

    public JButton getOrder(){
        return order;
    }
    
}
