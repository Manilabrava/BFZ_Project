package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import bfz_project.dba.DBAccess;
import bfz_project.obj.Booking;
import bfz_project.obj.Extra;
import bfz_project.obj.Room;

/*
 * Main fraim of the App
 * is invoked as a singleton
 * implemented as a Container for a Panel with CardLayout to enshure an easy access to different panels
 * 
 * Initialises an data base access
 */

public class Window extends JFrame{
    
    private static Window window = null;
    private DBAccess dba;
    private Booking booking;
    private JPanel mainCanvas;
    private CardLayout mainCanvasLayout;
    private HashMap<String,JPanel> components;
    private ArrayList<Room> freeRooms;

    private Window() throws Exception{
        dba = DBAccess.get();
        components = new HashMap<String,JPanel>();
        freeRooms = new ArrayList<Room>();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setBounds(100, 100, 200, 300);
        this.setVisible(true);

        mainCanvas = new JPanel(new CardLayout());
        mainCanvasLayout = (CardLayout)mainCanvas.getLayout();
        

        //initialisation of every used panel, every class is implemented as a child class of a JPanel
        components.put("StartWindow", new Entrance());
        mainCanvas.add("StartWindow", components.get("StartWindow"));

        components.put("BookDate", new BookDate());
        mainCanvas.add("BookDate", components.get("BookDate"));

        components.put("RoomSelection", new RoomSelection());
        mainCanvas.add("RoomSelection", components.get("RoomSelection"));

        components.put("ExtrasSelection", new ExtrasSelection(dba.getAllExtras()));
        mainCanvas.add("ExtrasSelection", components.get("ExtrasSelection"));

        components.put("CheckOut", new CheckOut());
        mainCanvas.add("CheckOut", components.get("CheckOut"));

        components.put("Remove", new Remove());
        mainCanvas.add("Remove", components.get("Remove"));

        
        //setting up start window of the app
        mainCanvasLayout.show(mainCanvas, "StartWindow");
        
        // here is still some work to do, besides documentation description of validate function
        // to draw defined window, start window appears only if you change the size of the frame
        mainCanvas.validate();

        //Actionlistener for the buttons from initialised JPanels are set up in this class
        //for a better communication porpuse
        this.buttonsSetUp();
        this.add(mainCanvas,BorderLayout.CENTER);
    }

    //singleton initialisation of the main fraim
    public static Window get() throws Exception{
        return window==null?window = new Window():window;
    }


    private void buttonsSetUp(){

        //every JPanel needed for buttonsetup
        Entrance entrance = (Entrance)components.get("StartWindow");
        BookDate bookDate = (BookDate)components.get("BookDate");
        RoomSelection roomselection = (RoomSelection)components.get("RoomSelection");
        ExtrasSelection extrasSelection = (ExtrasSelection)components.get("ExtrasSelection");
        CheckOut checkOut = (CheckOut)components.get("CheckOut");
        Remove remove = (Remove)components.get("Remove");

        //Buttons from the start window are saved in an array with the map 0->booking 1->cancle
        //this way this buttons just lead to main App functionality
        entrance.getButtons()[0].addActionListener(e->{
            mainCanvasLayout.show(mainCanvas, "BookDate");
        });

        entrance.getButtons()[1].addActionListener(e->{
            mainCanvasLayout.show(mainCanvas, "Remove");
        });

        //this function validates Date format and creates a Booking class which is a Window class field
        //this instance will be used through out the methods to set up an order
        //date format is limited to yyyy-mm-dd
        bookDate.getSearch().addActionListener(e->{
            String from = bookDate.getDates()[0].getText();
            String to = bookDate.getDates()[1].getText();
            if(this.isDate(from)&&this.isDate(to)){
                booking = new Booking(from, to);
                freeRooms = dba.getFreeRooms(Date.valueOf(from), Date.valueOf(to));
                mainCanvasLayout.show(mainCanvas, "RoomSelection");
                roomselection.setUpRoomAmmount(roomselection.calculateFreeRoomsAmmounts(freeRooms));
            }
            else{
                System.out.println("Wrong Date format use yyyy-mm-dd");
            }
        });

        /*
         * chosen ammount of wooms is only submitted if the ammount of Adults staying at the hotel
         * is less or equal room capacity of selected rooms
         * if this is true the ammount of selected double bed and single bed rooms is added to the booking
         */
        roomselection.getSubmit().addActionListener(e->{
            int selectedSingleBedRoomAmmount = (Integer)roomselection.getroomSelection()[0].getSelectedItem();
            int selectedDoubleBedRoomAmmount = (Integer)roomselection.getroomSelection()[1].getSelectedItem();
            int selectedRoomCapacity = selectedSingleBedRoomAmmount + selectedDoubleBedRoomAmmount*2;
            int selectedAdultsAmmount = (Integer)roomselection.getAdultSelection().getSelectedItem(); 

            if(selectedRoomCapacity >= selectedAdultsAmmount){
                booking.addRoomsToBooking(freeRooms, selectedSingleBedRoomAmmount, selectedDoubleBedRoomAmmount);
                booking.setAdultsAmmount(selectedAdultsAmmount);
                extrasSelection.setUpDropdowns(selectedSingleBedRoomAmmount, selectedDoubleBedRoomAmmount);
                mainCanvasLayout.show(mainCanvas, "ExtrasSelection");
            }
            else{
                System.out.println("Not enough beds selected");
            }
        });

        /*
         * extrascan be added to the rooms, you can only have as much of one specific extra as you have rooms
         * there is a split between single bed room and double bed room extras,
         * you cant select individual rooms for the extras they will be just added from lowest roomnumber
         * to the highest
         */
        extrasSelection.getAddExtra().addActionListener(e->{
            int singleBedRoomExtrasAmmount = (Integer)extrasSelection.getExtrasAmmountBox()[0].getSelectedItem();
            int doubleBedRoomExtrasAmmount = (Integer)extrasSelection.getExtrasAmmountBox()[1].getSelectedItem();
            for(Room r: booking.getSingleBedRoomsOnly()){
                if(singleBedRoomExtrasAmmount>0){
                    r.addExtra((Extra)extrasSelection.getExtrasBox()[0].getSelectedItem());
                    singleBedRoomExtrasAmmount--;
                }
                else{
                    r.getExtras().remove((Extra)extrasSelection.getExtrasBox()[0].getSelectedItem());
                }
            }

            for(Room r: booking.getDoubleBedRoomsOnly()){
                if(doubleBedRoomExtrasAmmount>0){
                    r.addExtra((Extra)extrasSelection.getExtrasBox()[1].getSelectedItem());
                    doubleBedRoomExtrasAmmount--;
                }
                else{
                    r.getExtras().remove((Extra)extrasSelection.getExtrasBox()[1].getSelectedItem());
                }
            }
        });


        extrasSelection.getSubmit().addActionListener(e->{
            checkOut.showOrder(booking);
            mainCanvasLayout.show(mainCanvas, "CheckOut");
        });

        checkOut.getOrder().addActionListener(e->{
            dba.book(booking);
            mainCanvasLayout.show(mainCanvas, "StartWindow");
        });

        remove.getRemove().addActionListener(e->{
            dba.removeBooking(Integer.parseInt(remove.getBookingID().getText()));
            mainCanvasLayout.show(mainCanvas,"StartWindow");
        });
    }


    //function validates if a given string is in a Date format you can extend this function for different formats
    private boolean isDate(String date){
        try {
            LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;           
        } catch (Exception e) {
            return false;
        }
    }
}
