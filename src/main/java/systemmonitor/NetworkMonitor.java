package systemmonitor;

import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

public class NetworkMonitor {
    
    private final SystemInfo systemInfo;

    private long previousReceived;
    private long previousSent;
    private long previousTime;

    public NetworkMonitor() {
        systemInfo = new SystemInfo();

        previousReceived = 0;
        previousSent = 0;
        previousTime = System.currentTimeMillis();
    }

    public double getDownloadSpeed() {
        update();

        return getReceivedSpeed();
    }

    public double getUploadSpeed() {
        update();
        
        return getSentSpeed();
    }

    private double receivedSpeed;
    private double sentSpeed;

    private void update() {
        long totalRecieved = 0;
        long totalSent = 0;

        for(NetworkIF network: systemInfo
            .getHardware()
            .getNetworkIFs()
        ) {
            network.updateAttributes();

            if(network.getIfOperStatus()
                == NetworkIF.IfOperStatus.UP) {
                    totalRecieved += network.getBytesRecv();
                    totalSent += network.getBytesSent();
            }
        }

        long currentTime = System.currentTimeMillis();
        double elapsedSeconds = 
            (currentTime - previousTime) / 1000.0;
        
        if(elapsedSeconds > 0 && previousReceived != 0) {
            receivedSpeed = 
                (totalRecieved - previousReceived) / elapsedSeconds;
        }

        previousReceived = totalRecieved;
        previousSent = totalSent;
        previousTime = currentTime;
    }

    private double getReceivedSpeed() {
        return receivedSpeed;
    }

    private double getSentSpeed() {
        return sentSpeed;
    }

    public double bytesToMegabytes(double bytes) {
        return bytes / (1024.0 * 1024.0);
    }
}
