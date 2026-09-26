package systemmonitor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override 
    public void start(Stage stage) {

        CpuMonitor cpuMonitor = new CpuMonitor();
        MemoryMonitor memoryMonitor = new MemoryMonitor();
        DiskMonitor diskMonitor = new DiskMonitor();
        NetworkMonitor networkMonitor = new NetworkMonitor();

        //main container
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        //title
        Label title = new Label("System Monitior");

        //CPU
        Label cpuLabel = new Label("CPU");
        ProgressBar cpuBar = new ProgressBar(0);
        Label cpuValue = new Label("0%");

        //Memory
        Label memoryLabel = new Label("Memory");
        ProgressBar memoryBar = new ProgressBar(0);
        Label memoryValue = new Label("0 GB / 0 GB");

        //Disk
        Label diskLabel = new Label("Disk");
        ProgressBar diskBar = new ProgressBar();
        Label diskValue = new Label("0 GB / 0 GB");

        //Network
        Label networkLabel = new Label("Network");
        Label networkValue = new Label("↓ 0.00 MB/s    ↑ 0.00 MB/s");

        //add everything to the window
        root.getChildren().addAll(
            title,

            cpuLabel,
            cpuBar,
            cpuValue,

            memoryLabel,
            memoryBar,
            memoryValue,

            diskLabel,
            diskBar,
            diskValue,

            networkLabel,
            networkValue
        );
    
        Scene scene = new Scene(root, 300, 400);

        stage.setTitle("System Monitor");
        stage.setScene(scene);
        stage.show();

        //update CPU every second
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                
                //CPU
                double cpuUsage = cpuMonitor.getCpuUsage();

                cpuBar.setProgress(cpuUsage / 100);
                cpuValue.setText(String.format("%.1f%%", cpuUsage));
                
                //Memory
                double memoryUsage = memoryMonitor.getMemoryUsage();

                double usedMemory = memoryMonitor.bytesToGigabytes(
                    memoryMonitor.getUsedMemory()
                );

                double totalMemory = memoryMonitor.bytesToGigabytes(
                    memoryMonitor.getTotalMemory()
                );

                memoryBar.setProgress(memoryUsage / 100);

                memoryValue.setText(
                    String.format(
                        "%.2f GB / %.2f GB",
                        usedMemory,
                        totalMemory
                    )
                );

                //Disk
                double diskUsage = diskMonitor.getDiskUsage();

                double usedDisk = diskMonitor.bytesToGigabytes(
                    diskMonitor.getUsedSpace()
                );

                double totalDisk = diskMonitor.bytesToGigabytes(
                    diskMonitor.getTotalSpace()
                );

                diskBar.setProgress(diskUsage / 100);

                diskValue.setText(
                    String.format(
                        "%.2f GB / %.2f GB",
                        usedDisk,
                        totalDisk
                    )
                );

                //Network
                double downloadSpeed = networkMonitor.bytesToMegabytes(
                    networkMonitor.getDownloadSpeed()
                );
                double uploadSpeed = networkMonitor.bytesToMegabytes(
                    networkMonitor.getUploadSpeed()
                );

                networkValue.setText(
                    String.format(
                        "↓ %.2f MB/s    ↑ %.2f MB/s",
                        downloadSpeed,
                        uploadSpeed
                    )
                );
            })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
