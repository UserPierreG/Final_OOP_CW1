package oop.cw1_2223.assignment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.drew.imaging.ImageProcessingException;

public class Assignment1 {

  public static void scan(List<Folder_Flags> allFolders, Sim_Date sim_Date, File outputFolder) throws IOException {
    Set<File> duplicateDetector = new HashSet<>();
    for(Folder_Flags folder : allFolders) {
      List<File> files = folder.getFiles();
      for(File file : files) {
        process(sim_Date, file, folder, outputFolder, duplicateDetector);
      }
    }
  }

  private static void process(Sim_Date sim_Date, File file, Folder_Flags folder, File outputFolder,
                              Set<File> duplicateDetector) throws IOException {

    theDate dateDestination = folder.computeDateDestination(file);

    if (dateDestination != null) {
      System.out.println("Date determined for '" + file + "' " + dateDestination.getYear() + "-" +
              dateDestination.getMonth() + "-" + dateDestination.getDay() + " (" + dateDestination.getMethod() + ")");
      File destinationFolder = sim_Date.getDestinationFolder(dateDestination);
      File outputDestinationFolder = new File(outputFolder, destinationFolder.getPath());
      File destinationFilename = new File(outputDestinationFolder, file.getName());

      if (sim_Date.isSimulation()) {
        if (duplicateDetector.add(destinationFilename)) {
          System.out.println( "[SIMULATING] Renaming '" + file + "' to '" + destinationFilename + "'");
        } else {
          System.out.println( "[SIMULATING] Did not rename '" + file + "' to '" + destinationFilename +
                  "' as destination file exists.");
        }
      } else {
        ensureFolderExists(outputDestinationFolder);
        if (!destinationFilename.exists()) {
          if (file.renameTo(destinationFilename)) {
            // issue confirmation of file move
            System.out.println( "Renamed '" + file + "' to '" + destinationFilename + "'");
          } else {
            // issue warning that file move failed
            System.out.println( "Failed to rename '" + file + "' to '" + destinationFilename + "'");
          }
        } else {
          // an attempt to move second file to same name in same date folder
          // issue warning and do not move
          System.out.println( "Did not rename '" + file + "' to '" + destinationFilename + "' as destination file exists.");
        }
      }
    } else {
      // issue warning that no date could be determined for file
      System.out.println( "Could not determine date for '" + file + "'.");
    }
  }

  public static void ensureFolderExists( final File folder) throws IOException {
    if (folder.exists()) {
      if (folder.isDirectory()) {
        return;
      } else {
        throw new IOException( "Folder is actually a file: " + folder);
      }
    } else {
      ensureFolderExists(folder.getParentFile());
      if (folder.mkdir()) {
        return;
      } else {
        throw new IOException( "Could not create folder: " + folder);
      }
    }
  }

  public static void main(String[] args) throws IOException, ImageProcessingException {

    File commonParent = new File("C:\\Users\\Space Grey\\Desktop\\cwimages");
    File outputFolder = new File(commonParent, "PhotoOrganiserOutput");

    Folder_Flags folder1 = new Folder_Flags(new File(commonParent, "cwimages\\dsacw_images"),
            true, true, true, true);
    Folder_Flags folder2 = new Folder_Flags(new File(commonParent, "cwimages\\oopimages"),
            true, true, true, true);

    List<Folder_Flags> allFolders = new ArrayList<>();
    allFolders.add(folder1);
    allFolders.add(folder2);

    Sim_Date sim_Date = new Sim_Date(true, true, false, false);


    scan(allFolders, sim_Date, outputFolder);

  }
}
