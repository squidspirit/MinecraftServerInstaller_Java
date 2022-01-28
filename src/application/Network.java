package application;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Pair;

public class Network {
    protected static boolean downloadFile(String urlStr, String path) {
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(urlStr).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.close();
        }
        catch (IOException e) {
            MessageBox.alertBox(AlertType.ERROR, "錯誤", "請檢查網路設備是否連接正常。");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Vector<Pair<String, String>> getGameVersion() {
        final String filename = "./GameVersion.info";
        Vector<Pair<String, String>> vec = new Vector<>();
        if (downloadFile(Information.Url.GAME_VERSION, filename)) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line = reader.readLine();
                while (line != null) {
                    String arr[] = line.split(" ");
                    vec.add(new Pair<String,String>(arr[0], arr[1]));
                    line = reader.readLine();
                }
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                vec.clear();
            }
        }
        return vec;
    }
    
    // public static 
}
