package model;

import java.util.ArrayList;

public class Hard {
    private long freeSpace;
    private long size;
    private int numberOfDrives;
    private ArrayList<Drive> drives = new ArrayList<>();
    public static Hard hard;

    public Hard(long size, int numberOfDrives) {
        this.freeSpace = size;
        this.size = size;
        this.numberOfDrives = numberOfDrives;
        hard = this;
    }


    public long getSize() {
        return size;
    }

    public void decreaseFreeSpace(long driveSize){
        this.freeSpace -= driveSize;
    }

    public void decreaseNumberOfDrives() {
        this.numberOfDrives--;
    }

    public int getNumberOfDrives() {
        return numberOfDrives;
    }

    public void addDrive(Drive drive) {
        this.drives.add(drive);
    }

    public ArrayList<Drive> getDrives() {
        return drives;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    @Override
    public String toString() {
        return "Hard{" +
                "freeSpace=" + freeSpace +
                ", size=" + size +
                ", numberOfDrives=" + numberOfDrives +
                ", drives=" + drives +
                '}';
    }
}
