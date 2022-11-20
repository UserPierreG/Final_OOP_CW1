package oop.cw1_2223.assignment;

import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FileTimestamp extends Methods{

    public theDate compute(File file) {
        long timestamp = file.lastModified();
        ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        dateDestination = new theDate(zdt.getYear(), zdt.getMonthValue(),zdt.getDayOfMonth(),"File Timestamp");
        return dateDestination;
    }
}
