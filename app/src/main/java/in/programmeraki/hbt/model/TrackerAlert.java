package in.programmeraki.hbt.model;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrackerAlert {

    public static int warningType = 1;
    public static int criticalType = 2;

    private Date datetime;
    private String msg;
    private int type;
    private int val;

    public Date getDatetime() {
        return datetime;
    }

    @SuppressLint("SimpleDateFormat")
    public String getDatetimeString() {
        return new SimpleDateFormat("DD/MM/YYYY HH:mm:ss").format(datetime);
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
