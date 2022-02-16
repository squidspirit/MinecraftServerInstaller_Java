package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;

public class InstallForge extends Task<Void> {
    final private boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    private String path;

    
    public InstallForge(String path) {
        this.path = path;
    }

    @Override
    protected Void call() throws Exception {
        updateTitle(Program.Status.PROCESSING);
        Process process;
        File file = new File(path + "install" + (isWindows ? ".bat" : ".sh"));
        try (FileWriter writer = new FileWriter(file)) {
            if (!isWindows) writer.write("#!/bin/sh\n");
            writer.write("java -jar ");
            if (!isWindows) writer.write("./");
            writer.write("forge-installer.jar --installServer\n");
            writer.write((isWindows ? "del " : "rm ./") + "forge-installer.jar\n");
            writer.write(isWindows ? "del install.bat\n" : "rm ./install.sh\n");
            if (!isWindows) Program.setExecutable(file);
        }
        process = Runtime.getRuntime().exec(isWindows ? "cmd /C install.bat" : "/bin/sh -c ./install.sh", null, new File(path));
        InputStreamReader isr = new InputStreamReader(process.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        process.waitFor();
        return null;
    }

    @Override
    protected void failed() {
        MessageBox.alertBox(AlertType.ERROR, "錯誤", "請檢常網路設備是否連接正常");
        updateTitle(Program.Status.ERROR);
    }

    @Override
    protected void succeeded() {
        updateProgress(1, 1);
        updateTitle(Program.Status.READY);
    }
}
