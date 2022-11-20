package oop.cw1_2223.assignment;

import java.io.File;

public class Sim_Date {

    private final boolean simulation;
    private final boolean y_Folders;
    private final boolean y_M_Folders;
    private final boolean y_M_D_Folders;

    public Sim_Date(boolean simulation, boolean y_Folders, boolean y_M_Folders,
                        boolean y_M_D_Folders) {
        this.simulation = simulation;
        this.y_Folders = y_Folders;
        this.y_M_Folders = y_M_Folders;
        this.y_M_D_Folders = y_M_D_Folders;
    }

    public boolean isSimulation() {
        return this.simulation;
    }

    public File getDestinationFolder(theDate dateDestination) {
        File parent = null;
        if (this.y_Folders) {
            parent = new File(parent, String.format("%04d", (Integer) dateDestination.getYear()));
        }
        if (this.y_M_Folders) {
            parent = new File(parent, String.format("%04d", (Integer) dateDestination.getYear()) + "-" +
                    String.format("%02d", (Integer) dateDestination.getMonth()));
        }
        if (this.y_M_D_Folders) {
            parent = new File(parent, String.format("%04d", (Integer) dateDestination.getYear()) + "-" +
                    String.format("%02d", (Integer) dateDestination.getMonth()) + "-" + String.format("%02d",
                    (Integer) dateDestination.getDay()));
        }
        return parent;
    }

}
