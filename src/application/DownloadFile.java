package application;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.concurrent.Task;
import javafx.scene.control.Alert.AlertType;

public class DownloadFile extends Task<Void> {
    private String url;
    private String filename;
    private String status;

    public DownloadFile(String url, String filename, String status) {
        this.url = url;
        this.filename = filename;
        this.status = status;
    }

    @Override
    protected Void call() throws Exception {
        updateTitle(this.status);
        URLConnection connection = new URL(url).openConnection();
        long fileLength = connection.getContentLengthLong();
        
        try (InputStream is = connection.getInputStream();
                OutputStream os = Files.newOutputStream(Paths.get(filename))) {
            
            long nread = 0L;
            byte[] buf = new byte[8192];
            int n;
            while ((n = is.read(buf)) > 0) {
                os.write(buf, 0, n);
                nread += n;
                updateProgress(nread, fileLength);
            }
        }
        return null;
    }

    @Override
    protected void failed() {
        MessageBox.alertBox(AlertType.ERROR, "錯誤", "請檢常網路設備是否連接正常");
        updateTitle(Program.Status.ERROR);
    }

    @Override
    protected void succeeded() {
        updateTitle(Program.Status.READY);
    }
}
