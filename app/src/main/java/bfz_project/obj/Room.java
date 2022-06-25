package bfz_project.obj;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity

@Table
public class Room {

    @Id
    private int roomnumber;

    @OneToOne
    private RoomType type;

    @ManyToMany
    private List<Extra> extras;

    //empty constructor needed for hibernate
    public Room(){
    }

    public Room(int roomnumber, RoomType type){
        this.roomnumber=roomnumber;
        this.type = type;
        extras = new ArrayList<Extra>();
    }
    

    public RoomType getType() {
        return type;
    }

    public int getRoomnumber() {
        return roomnumber;
    }

    public List<Extra> getExtras(){
        return extras;
    }

    public void addExtra(Extra extra){
        if(!extras.contains(extra)){
            extras.add(extra);
        }
    }

    public void resetExtras(){
        extras = new ArrayList<Extra>();
    }


    public void addExtras(ArrayList<Extra> extras){
        extras.addAll(extras);
    }

    @Override
    public String toString(){
        return this.getType().getRoomTypeName() + " Nr. " + this.getRoomnumber() + " , " + "Kosten " + this.getType().getRoomTypeCost();
    }
}
