package bfz_project.obj;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity

@Table
public class Booking {

    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingID;

    @ManyToMany
    private List<Room> bookedRooms;

    private int adultsAmmount;

    @Column (name = "date_from")
    private Date from;

    @Column (name = "date_to")
    private Date to;


    public Booking(Date from, Date to){
        this.from = from;
        this.to = to;
    }

    public Booking(String from, String to){
        this.from = Date.valueOf(from);
        this.to = Date.valueOf(to);
        this.bookedRooms = new ArrayList<Room>();
    }

    //empty constructor needed for hibernate
    public Booking(){

    }

    
    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public int getId() {
        return bookingID;
    }

    public List<Room> getBookedRooms() {
        return bookedRooms;
    }

    public void addBookedRoom(Room room){
        bookedRooms.add(room);
    }

    public void addBookedRooms(ArrayList<Room> rooms){
        bookedRooms.addAll(rooms);
    }

    public int getAdultsAmmount() {
        return adultsAmmount;
    }

    public void setAdultsAmmount(int adultsAmmount) {
        this.adultsAmmount = adultsAmmount;
    }

    //adds given free rooms to the bookedRooms List of this instance
    public void addRoomsToBooking(ArrayList<Room> freeRooms, int singleBedRoomsAmmount, int doubleBedRoomsAmmount){
        for(Room r: freeRooms){
            if(singleBedRoomsAmmount > 0 && r.getType().getTypeID()==RoomType.SIGLE_BED_ROOM_ID){
                bookedRooms.add(r);
                singleBedRoomsAmmount--;
                continue;
            }
            if(doubleBedRoomsAmmount > 0 && r.getType().getTypeID()==RoomType.DOUBLE_BED_ROOM_ID){
                bookedRooms.add(r);
                doubleBedRoomsAmmount--;
            }
        }
    }

    // returns an int array mapped 0->single bed room ammount 1->double bed room ammount
    public int[] getAmmountsOfDifferentTypesOfRooms(){
        int[] roomTypesAmmount = new int[2];
        for(Room r : bookedRooms){
            if(r.getType().getTypeID()==RoomType.SIGLE_BED_ROOM_ID){
                roomTypesAmmount[0]++;
            }
            else{
                roomTypesAmmount[1]++;
            }
        }
        return roomTypesAmmount;
    }

    
    public ArrayList<Room> getSingleBedRoomsOnly(){
        ArrayList<Room> tempRooms = new ArrayList<Room>();
        for(Room r : bookedRooms){
            if(r.getType().getTypeID()==RoomType.SIGLE_BED_ROOM_ID){
                tempRooms.add(r);
            }
        }
        return tempRooms;
    }

    public ArrayList<Room> getDoubleBedRoomsOnly(){
        ArrayList<Room> tempRooms = new ArrayList<Room>();
        for(Room r : bookedRooms){
            if(r.getType().getTypeID()==RoomType.DOUBLE_BED_ROOM_ID){
                tempRooms.add(r);
            }
        }
        return tempRooms;
    }
}
