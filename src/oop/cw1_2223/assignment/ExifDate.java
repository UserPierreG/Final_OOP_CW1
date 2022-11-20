package oop.cw1_2223.assignment;

import com.drew.imaging.ImageProcessingException;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ExifDate extends Methods{

    public theDate compute(File file) {
        try {
            java.util.Date date = ExifUtils.getFirstDate(ExifUtils.getMetaData(file));
            if (date != null) {
                ZonedDateTime zdt = date.toInstant().atZone(ZoneId.systemDefault());
                dateDestination = new theDate(zdt.getYear(), zdt.getMonthValue(),zdt.getDayOfMonth(),"EXIF data");
            }
        } catch(IOException | ImageProcessingException x) {
            System.out.println(x);
        }
        return dateDestination;
    }
}
