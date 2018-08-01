package in.programmeraki.hbt;

import android.app.Application;

import java.util.ArrayList;

import in.programmeraki.hbt.model.TrackerAlert;

public class Common extends Application {

    public static Common instance = new Common();
    public ArrayList<TrackerAlert> trackerAlerts = new ArrayList<>();

    public ArrayList<TrackerAlert> getPulseTrackerAlerts() {
        ArrayList<TrackerAlert> temp = new ArrayList<>();
        for (int i = 0; i < trackerAlerts.size(); i++) {
            if(trackerAlerts.get(i).getType() == TrackerAlert.pulseType){
                temp.add(trackerAlerts.get(i));
            }
        }
        return temp;
    }

    public ArrayList<TrackerAlert> getTempTrackerAlerts() {
        ArrayList<TrackerAlert> temp = new ArrayList<>();
        for (int i = 0; i < trackerAlerts.size(); i++) {
            if(trackerAlerts.get(i).getType() == TrackerAlert.tempType){
                temp.add(trackerAlerts.get(i));
            }
        }
        return temp;
    }
}
