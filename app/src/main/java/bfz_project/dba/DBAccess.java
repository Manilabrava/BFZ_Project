package bfz_project.dba;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import org.hibernate.query.Query;

import bfz_project.obj.Booking;
import bfz_project.obj.Room;
import jakarta.persistence.TypedQuery;

import bfz_project.obj.*;

/*
 * Data Base Access. Is invoked by the Window class and is implemented as a Singleton, to avoid multiple connections
 */


public class DBAccess {

    private static DBAccess dba = null;

    
    private final StandardServiceRegistry registry;
    private Metadata md ;
    private SessionFactory sf;
    private Session session;
    Transaction transaction;

        //The Constructor establishes a connection to the database session.close() still has to be implemented
        //because an GUI exit isn't implemented yet
        private DBAccess() throws Exception{
            registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            md = new MetadataSources(registry).getMetadataBuilder().build();
            sf = md.getSessionFactoryBuilder().build();
            session = sf.openSession();
        }

        /*

        To initialise the database: 
        1. you have to change hibernate.hbm2ddl.auto property in hibernate.cfg.xml to create 
        2. you need to run this init function from constructor

        esiest way to establish this is to get DBAccess in the App Class to avoid launching GUI
        
        after initializing the data base, you need to change the setting back to update
        and remove this function from constructor


        private void initDataBase(){
            Transaction transaction = session.beginTransaction();
            RoomType rt1 = new RoomType("Einzelzimmer", 150);
            RoomType rt2 = new RoomType("Doppelzimmer", 180);

            session.persist(rt1);
            session.persist(rt2);

            String[] exNames = {"Kinderbett", "All Inclusive", "Free Minibar", "Mit Frühstück", "Mit mittagessen", "Mit Abendessen"};
            int[] exCost = {20,100,30,15,25,30};
            for (int i =0; i<6;i++) {
                session.persist(new Extra(exNames[i],exCost[i]));
            }

            for(int i = 1; i<337;i++ ){
                if(i<113){
                    session.persist(new Room(i, rt1));
                }
                else{
                    session.persist(new Room(i, rt2));
                }
            }

            transaction.commit();
        }
        */
        
        //gets DBAccess singleton
        public static DBAccess get() throws Exception{
            return dba==null? dba = new DBAccess():dba;
        }

        
        //this function looks for free rooms at the database in given time period
        //it also checks if the date is valid given, so you can't book rooms in the past
        //and for a negative time period
        public ArrayList<Room> getFreeRooms(Date von, Date bis){
            ArrayList<Room> freeRooms = null;

            if(von.after(Date.valueOf(LocalDate.now())) && bis.after(von)){
                transaction = session.beginTransaction();

                TypedQuery<Room> roomQuery = session.createQuery("from Room", Room.class);
                TypedQuery<Booking> bookingQuery = session.createQuery("from Booking", Booking.class);

                freeRooms = (ArrayList<Room>)roomQuery.getResultList();
                ArrayList<Booking> bookings = (ArrayList<Booking>)bookingQuery.getResultList();

                transaction.commit();

                for(Booking b : bookings){
                    if(b.getFrom().after(bis)||b.getTo().before(von)){
                    }
                    else{
                        freeRooms.removeAll(b.getBookedRooms());
                    }
                }
            }
            else{
                System.out.println("incorrect date imput");
            }
            return freeRooms;
        }

        //returns all possible extras from the data base
        public ArrayList<Extra> getAllExtras(){
            transaction = session.beginTransaction();

            TypedQuery<Extra> extraQuery = session.createQuery("from Extra", Extra.class);
            transaction.commit();

            return (ArrayList<Extra>)extraQuery.getResultList();
        }

        //needs to be implimented on app closing
        public void closeSession(){
            session.close();
        }

        //simple data base insertion of a booking
        public void book(Booking booking){
            transaction = session.beginTransaction();
            session.persist(booking);
            transaction.commit();
        }

        //function to remove all the constrains room_extra for the booking with given bookingID
        //and deletes this booking
        public void removeBooking(int BookingID){
            transaction = session.beginTransaction();
            Query<Booking> query = session.createQuery("from Booking where id="+BookingID, Booking.class);
            ArrayList<Booking> bookings = (ArrayList<Booking>)query.getResultList(); 
            for(Booking b : bookings){
                for(Room r : b.getBookedRooms()){
                    r.resetExtras();
                }
                session.remove(b);
            }
            transaction.commit();
        }
}
