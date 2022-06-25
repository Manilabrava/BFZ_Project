package bfz_project;
import gui.Window;

/*
 * Main entrance into the App, calls the singleton Window, which is the main GUI frame
 */

public class App {

    public static void main(String[] args) throws Exception{
        Window.get();
    }
}
