package application;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class CreateFile implements AutoCloseable {
    final private boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    private String path;
    private String jarName;
    private boolean isEnableGui;
    private int maxRamValue;
    private int minRamValue;
    private Map<String, String> serverProperties;

    CreateFile(String path) {
        this.path = path;
        jarName = "server.jar";
        isEnableGui = false;
        maxRamValue = 1024;
        minRamValue = 1024;
        serverProperties = new HashMap<>();
    }

    public void build() throws Exception {
        final File startFile = new File(path + "StartServer." + (isWindows ? "bat" : "sh"));
        startFile.setExecutable(true);
        try (FileWriter writer = new FileWriter(startFile)) {
            if (!isWindows) writer.write("#! /bin/bash\n");
            writer.write("java -jar -Xmx" + maxRamValue + "M -Xms" + minRamValue + "M " + jarName);
            if (!isEnableGui) writer.write(" nogui");
            if (isWindows) writer.write("\npause");
            writer.flush();
        }
        try (FileWriter writer = new FileWriter(new File(path + "eula.txt"))) {
            writer.write("eula=true\n");
            writer.flush();
        }
        try (FileWriter writer = new FileWriter(new File(path + "server.properties"))) {
            for (Map.Entry<String, String> entry : serverProperties.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
            writer.flush();
        }
    }

    public void close() {

    }

    public void setEnableGui(boolean bool) {
        isEnableGui = bool;
    }

    public void setRam(int max, int min) {
        maxRamValue = max;
        minRamValue = min;
    }

    public void setProperties(Map<String, String> map) {
        serverProperties = map;
    }
}
