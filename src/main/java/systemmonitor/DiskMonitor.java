package systemmonitor;

import java.io.File;

public class DiskMonitor {
    
    private final File disk;

    public DiskMonitor() {
        disk = new File("C:");
    }

    public long getTotalSpace() {
        return disk.getTotalSpace();
    }

    public long getFreeSpace() {
        return disk.getFreeSpace();
    }

    public long getUsedSpace() {
        return getTotalSpace() - getFreeSpace();
    }

    public double getDiskUsage() {
        return (double) getUsedSpace() / getTotalSpace() * 100;
    }

    public double bytesToGigabytes(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }
}
