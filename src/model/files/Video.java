package model.files;

import controller.OsController;
import model.Os;

public class Video extends File {
    private String quality;
    private String videosLength;

    public Video(String fileName, long size, boolean forDrive) {
        super(fileName, size, "mp4", forDrive);
    }

    public String getQuality() {
        return quality;
    }

    public String getVideosLength() {
        return videosLength;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setVideosLength(String videosLength) {
        this.videosLength = videosLength;
    }

    @Override
    public String toString() {
        return fileName + " " + type + '\n' +
                OsController.createAddress(Os.os.currentFolder, "", Os.os.currentDrive) + '\\' + fileName + '\n' +
                "Size: " + size + "MB" + '\n' +
                "Quality: " + quality + '\n' +
                "Video Length: " + videosLength;
    }
}
