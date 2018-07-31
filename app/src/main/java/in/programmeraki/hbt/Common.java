package in.programmeraki.hbt;

import android.app.Application;

import java.util.ArrayList;

import in.programmeraki.hbt.model.TrackerAlert;

public class Common extends Application {

    public static Common instance = new Common();
    ArrayList<TrackerAlert> trackerAlerts = new ArrayList<>();

}
