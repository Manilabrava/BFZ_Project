package bfz_project.obj;

import jakarta.persistence.*;


@Entity

@Table
public class Extra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int extraID;

    private String extraName;

    private double extraCost;

    //empty constructor needed for hibernate
    public Extra(){

    }

    public Extra(String extraName, double extraCost){
        this.extraName = extraName;
        this.extraCost = extraCost;
    }

    public double getExtraCost() {
        return extraCost;
    }


    public String getExtraName() {
        return extraName;
    }


    public int getOptionID() {
        return extraID;
    }

    @Override
    public String toString(){
        return this.getExtraName();
    }

    @Override
    public boolean equals(Object other){
        return this.getOptionID() == ((Extra)other).getOptionID();
    }
}
