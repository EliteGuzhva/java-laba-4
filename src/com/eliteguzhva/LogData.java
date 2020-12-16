package com.eliteguzhva;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LogData {
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;
    public int second;
    public String username;
    public String clientVersion;
    public String agentVersion;

    public String toString() {
        Calendar cal = new GregorianCalendar(year, month - 1, day, hour, minute, second);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return String.format("%s username: %s; clientVersion: %s; agentVersion: %s",
                sdf.format(cal.getTime()), username, clientVersion, agentVersion);
    }
}
