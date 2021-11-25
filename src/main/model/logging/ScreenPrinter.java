package model.logging;

import model.logging.*;

//prints log to console
public class ScreenPrinter {
    private EventLog eventLog;


    public ScreenPrinter(EventLog e) {
        this.eventLog = e;
    }


    //EFFECT: prints contents of log line by line to console.
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
