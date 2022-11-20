package oop.cw1_2223.assignment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Folder_Flags {

    private final File targetFolder;
    private final boolean recursion;
    private final boolean exifDate;
    private final boolean fileNameDate;
    private final boolean fileTimestamp;

    public Folder_Flags(File targetFolder, boolean recursion, boolean exifDate,
                        boolean fileNameDate, boolean fileTimestamp) {
        this.targetFolder = targetFolder;
        this.recursion = recursion;
        this.exifDate = exifDate;
        this.fileNameDate = fileNameDate;
        this.fileTimestamp = fileTimestamp;
    }

    public List<File> getFiles() {
        List<File> list = new ArrayList<>();
        list.add(this.targetFolder);
        int position = 0;
        while (position < list.size()) {
            File current = list.get(position);
            if (current.isDirectory()) {
                list.remove(position);
                if (list.size() == 0 || this.recursion) {
                    File[] files = current.listFiles();
                    if (files != null) {
                        list.addAll(Arrays.asList(files));
                    }
                }
            } else {
                position++;
            }
        }
        return list;
    }

    public theDate computeDateDestination(File file) {
        theDate dateDestination = null;
        if (this.exifDate) {
            ExifDate a = new ExifDate();
            dateDestination = a.compute(file);
        }
        if (dateDestination == null && this.fileNameDate) {
            FileNameDate b = new FileNameDate();
            dateDestination = b.compute(file);
        }
        if (dateDestination == null && this.fileTimestamp) {
            FileTimestamp c = new FileTimestamp();
            dateDestination = c.compute(file);
        }
        return dateDestination;
    }

}
