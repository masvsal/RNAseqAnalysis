package model.logging;

import model.logging.*;

//prints contents of Eventlog  to console on closure of application
public class ScreenPrinter {
    private EventLog eventLog; //event log to print


    //MODIFIES: THIS
    //EFFECTS: instantiates new ScreenPrinter Object
    public ScreenPrinter(EventLog e) {
        this.eventLog = e;
    }


    //EFFECT: prints contents of Events in EventLog line by line to console sequentially. Each entry is labelled with
    //number and date.
    public void printLogToConsole() {
        int counter = 1;
        for (Event ev: eventLog) {
            System.out.println(counter + ") DATE: " + ev.getDate());
            System.out.println(ev.getDescription().split("\\|")[0]);
            System.out.println(ev.getDescription().split("\\|")[1]);
            System.out.println("-------------------------------------------");
            counter++;
        }
    }


}
