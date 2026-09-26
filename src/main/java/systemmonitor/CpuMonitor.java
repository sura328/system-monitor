package systemmonitor;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class CpuMonitor {
    
    private final OperatingSystemMXBean osBean;

    public CpuMonitor() {
        osBean = (OperatingSystemMXBean)
            ManagementFactory.getOperatingSystemMXBean();
    }

    public double getCpuUsage() {
        double cpuUsage = osBean.getCpuLoad();

        if (cpuUsage < 0) {
            return 0;
        }

        return cpuUsage * 100;
    }
}
