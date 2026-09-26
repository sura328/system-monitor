package systemmonitor;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class MemoryMonitor {
    
    private final OperatingSystemMXBean osBean;

    public MemoryMonitor() {
        osBean = (OperatingSystemMXBean)
            ManagementFactory.getOperatingSystemMXBean();
    }

    public long getTotalMemory() {
        return osBean.getTotalMemorySize();
    }

    public long getUsedMemory() {
        long totalMemory = osBean.getTotalMemorySize();
        long freeMemory = osBean.getFreeMemorySize();

        return totalMemory - freeMemory;
    }

    public double getMemoryUsage() {
        long totalMemory = osBean.getTotalMemorySize();
        long getUsedMemory = getUsedMemory();
        
        return (double) getUsedMemory / totalMemory * 100;
    }

    public double bytesToGigabytes(long bytes) {
        return bytes / (1024.0 * 1024.0 * 1024.0);
    }
}
