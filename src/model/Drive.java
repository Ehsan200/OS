package model;

public class Drive extends MotherDrive {
    private boolean isOsDrive;
    private long freeSpace;
    private long size;

    public Drive(String name, boolean isOsDrive, long size) {
        this.name = name;
        this.isOsDrive = isOsDrive;
        this.freeSpace = size;
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void addToFreeSpace(long fileSize) {
        this.freeSpace += fileSize;
    }

    public boolean isOsDrive() {
        return isOsDrive;
    }

    public long getFreeSpace() {
        return freeSpace;
    }

    public void decreaseFreeSpace(long fileSize) {
        this.freeSpace -= fileSize;
    }

    public static Drive getDriveWithName(String driveName) {
        for (Drive drive : Hard.hard.getDrives()) {
            if (drive.getName().equals(driveName))
                return drive;
        }
        return null;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String toString() {
        return name + ":";
    }


    public String toStrings() {
        return "Drive{" +
                "isOsDrive=" + isOsDrive +
                ", freeSpace=" + freeSpace +
                ", size=" + size +
                ", name='" + name + '\'' +
                '}';
    }
}
