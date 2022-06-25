package bfz_project.obj;

import jakarta.persistence.*;

@Entity


@Table
public class RoomType {

    //constants for a better way to search for different kinds of rooms
    @Transient
    public static final int SIGLE_BED_ROOM_ID = 1;
    @Transient
    public static final int DOUBLE_BED_ROOM_ID = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeID;

    private String roomTypeName;

    private double roomTypeCost;

    //empty constructor needed for hibernate
    public RoomType(){

    }

    public RoomType(String roomTypeName, double roomTypeCost){
        this.roomTypeName = roomTypeName;
        this.roomTypeCost = roomTypeCost;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public double getRoomTypeCost() {
        return roomTypeCost;
    }

    public int getTypeID() {
        return typeID;
    }
}
