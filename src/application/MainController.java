package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
    final private boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    final private Vector<String> gameVersionVector = new Vector<>();
    final private Map<String, String> gameVersionMap = new HashMap<>();
    final private Vector<String> forgeVersionVector = new Vector<>();
    final private Map<String, String> forgeVersionMap = new HashMap<>();
    final private Map<String, String> serverProperties = new HashMap<>();
    private int maxRamValue;
    private int minRamValue;

    /** Public Items */
    @FXML private TabPane mainTabPane;
    @FXML private Button startInstallButton;
    @FXML private Button ramResetButton;
    @FXML private Button optionResetButton;
    @FXML private Label statusLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Button checkNewButton;

    /** Basic Options */
    @FXML private TextField gameVersionTextField = new TextField();
    @FXML private Button gameVersionButton;
    @FXML private ChoiceBox<String> modVersionChoiceBox;
    @FXML private TextField modVersionTextField = new TextField();
    @FXML private Button modVersionButton;
    @FXML private TextField installPathTextField = new TextField();
    @FXML private Button installPathButton;
    @FXML private CheckBox changeRamCheckBox;
    @FXML private Slider maxRamSlider;
    @FXML private TextField maxRamTextField = new TextField();
    @FXML private Slider minRamSlider;
    @FXML private TextField minRamTextField = new TextField();
    @FXML private CheckBox guiCheckBox;
    @FXML private CheckBox eulaCheckBox;

    /** Advance Options */
    @FXML private TextField portTextField = new TextField();
    @FXML private TextField maxPlayerTextField = new TextField();
    @FXML private TextField spawnProtectionTextField = new TextField();
    @FXML private TextField viewDistanceTextField = new TextField();
    @FXML private ChoiceBox<String> pvpChoiceBox;
    @FXML private ChoiceBox<String> gamemodeChoiceBox;
    @FXML private ChoiceBox<String> difficultyChoiceBox;
    @FXML private ChoiceBox<String> commandBolckChoiceBox;
    @FXML private ChoiceBox<String> onlineModeChoiceBox;
    @FXML private TextField motdTextField = new TextField();

    /** Information */
    @FXML private Label nameLabel;
    @FXML private Label versionLabel;
    @FXML private Label authorLabel;
    @FXML private TextField emailTextField = new TextField();
    @FXML private TextField tutorialTextField = new TextField();
    @FXML private TextField websiteTextField = new TextField();

    /** Initialize and Reset */
    @FXML
    public void initialize() {
        /** Basic Options */
        gameVersionTextField.setEditable(false);
        modVersionChoiceBox.getItems().clear();
        modVersionChoiceBox.getItems().addAll("不使用", "Forge", "Fabric");
        modVersionChoiceBox.getSelectionModel().selectFirst();
        modVersionChoiceBox.setDisable(true);
        modVersionTextField.setEditable(false);
        modVersionButton.setDisable(true);
        installPathTextField.setEditable(false);
        resetRamOptions();

        /** Advanced Options */
        pvpChoiceBox.getItems().clear();
        pvpChoiceBox.getItems().addAll("true", "false");
        gamemodeChoiceBox.getItems().clear();
        gamemodeChoiceBox.getItems().addAll("survival", "creative", "adventure", "spectator");
        difficultyChoiceBox.getItems().clear();
        difficultyChoiceBox.getItems().addAll("peaceful", "easy", "normal", "hard");
        commandBolckChoiceBox.getItems().clear();
        commandBolckChoiceBox.getItems().addAll("true", "false");
        onlineModeChoiceBox.getItems().clear();
        onlineModeChoiceBox.getItems().addAll("true", "false");
        resetAdnavcedOptions();

        /** Information */
        statusLabel.setText(Program.Status.READY);
        nameLabel.setText(Program.Information.NAME);
        versionLabel.setText(Program.Information.VERSION);
        authorLabel.setText(Program.Information.AUTHOR);
        emailTextField.setText(Program.Information.EMAIL);
        emailTextField.setEditable(false);
        tutorialTextField.setText(Program.Information.TUTORIAL);
        tutorialTextField.setEditable(false);
        websiteTextField.setText(Program.Information.WEBSITE);
        websiteTextField.setEditable(false);

        /** Listeners */
        maxRamTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) onMaxRamTextField(new ActionEvent());
        });
        minRamTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) onMinRamTextField(new ActionEvent());
        });
        portTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) onPortTextField(new ActionEvent());
        });
        maxPlayerTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) onMaxPlayerTextField(new ActionEvent());
        });
        spawnProtectionTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) onSpawnProtectionTextField(new ActionEvent());
        });
        viewDistanceTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) onViewDistanceTextField(new ActionEvent());
        });
        motdTextField.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) onMotdTextField(new ActionEvent());
        });
    }

    /** Functions */
    private void resetRamOptions() {
        changeRamCheckBox.setDisable(false);
        changeRamCheckBox.setSelected(false);
        maxRamValue = 1024;
        maxRamSlider.setValue(1024);
        maxRamSlider.setDisable(true);
        maxRamTextField.setText("1024");
        maxRamTextField.setDisable(true);
        minRamValue = 1024;
        minRamSlider.setValue(1024);
        minRamSlider.setDisable(true);
        minRamTextField.setText("1024");
        minRamTextField.setDisable(true);
        ramResetButton.setDisable(true);
    }

    private void resetAdnavcedOptions() {
        serverProperties.clear();
        serverProperties.put(Program.Options.SERVER_PORT, "25565");
        serverProperties.put(Program.Options.MAX_PLAYERS, "20");
        serverProperties.put(Program.Options.SPAWN_PROTECTION, "16");
        serverProperties.put(Program.Options.VIEW_DISTANCE, "10");
        serverProperties.put(Program.Options.PVP, "true");
        serverProperties.put(Program.Options.GAMEMODE, "0");
        serverProperties.put(Program.Options.DIFFICULTY, "2");
        serverProperties.put(Program.Options.ENABLE_COMMAND_BLOCK, "false");
        serverProperties.put(Program.Options.ONLINE_MODE, "true");
        serverProperties.put(Program.Options.MOTD, "");

        portTextField.setText("25565");
        maxPlayerTextField.setText("20");
        spawnProtectionTextField.setText("16");
        viewDistanceTextField.setText("10");
        pvpChoiceBox.getSelectionModel().selectFirst();
        gamemodeChoiceBox.getSelectionModel().selectFirst();
        difficultyChoiceBox.getSelectionModel().select(2);
        commandBolckChoiceBox.getSelectionModel().selectLast();
        onlineModeChoiceBox.getSelectionModel().selectFirst();
        motdTextField.setText("");
    }

    private boolean fixTextField(TextField textField, String replace) {
        final String str = textField.getText();
        if (!str.matches("^[0-9]+$") || str.equals("")) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "請輸入正整數。");
            textField.setText(replace);
            return true;
        }
        return false;
    }

    private void createRunFile(String path, String filename, int maxRam, int minRam, boolean isGui) {
        final File file = new File(path + "StartServer" + (isWindows ? ".bat" : ".sh"));
        file.setExecutable(true);
        try (FileWriter writer = new FileWriter(file)) {
            if (!isWindows) writer.write("#!/bin/sh\n");
            writer.write("java -Xmx" + maxRam+ "M -Xms" + minRam + "M ");
            if (filename.indexOf(".jar") >= 0) writer.write("-jar ");
            writer.write(filename);
            if (!isGui) writer.write(" nogui");
            writer.write("\n");
            if (isWindows) writer.write("pause\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    private void createPropertiesFile(String path, Map<String, String> propertiesMap) {
        try (FileWriter writer = new FileWriter(new File(path + "server.properties"))) {
            for (var obj : propertiesMap.entrySet()) {
                writer.write(obj.getKey() + "=" + obj.getValue() + "\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createEulaFile(String path) {
        try (FileWriter writer = new FileWriter(new File(path + "eula.txt"))) {
            writer.write("eula=true\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Actions */
    public void onStartInstallButton(ActionEvent event) {
        /** Check Errors */
        if (!eulaCheckBox.isSelected()) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "您必須同意 EULA 條款。");
            return;
        }
        if (gameVersionTextField.getText().equals("")) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "請選擇伺服器遊戲版本。");
            return;
        }
        if (modVersionChoiceBox.getSelectionModel().getSelectedIndex() == 1) {
            if (modVersionTextField.getText().equals("")) {
                MessageBox.alertBox(AlertType.INFORMATION, "提示", "請選擇模組版本，或將其調整為不使用。");
                return;
            }
        }
        if (installPathTextField.getText().equals("")) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "請選擇伺服器安裝位置。");
            return;
        }

        /** Download Main Server File */
        // 0 -> None, 1 -> Forge, 2 -> Fabric
        Task<Void> task;
        Thread thread;
        final int modTypeValue = modVersionChoiceBox.getSelectionModel().getSelectedIndex();
        final String installPathValue = installPathTextField.getText() + "/";
        final String gameVersionValue = gameVersionTextField.getText();
        switch (modTypeValue) {
            case 0: // None
                task = new DownloadFile(gameVersionMap.get(gameVersionValue), installPathValue + "server.jar", Program.Status.DOWNLOADING);
                progressBar.progressProperty().bind(task.progressProperty());
                statusLabel.textProperty().bind(task.titleProperty());
                mainTabPane.disableProperty().bind(task.runningProperty());
                thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();

                task.setOnSucceeded(e -> {
                    createRunFile(installPathValue, "server.jar", maxRamValue, minRamValue, guiCheckBox.isSelected());
                    createEulaFile(installPathValue);
                    createPropertiesFile(installPathValue, serverProperties);
                    MessageBox.alertBox(AlertType.INFORMATION, "安裝完成", "Minecraft 伺服器已安裝至您所選擇的位置，請至該處執行 StartServer.bat 或 StartServer.sh。");
                    mainTabPane.disableProperty().unbind();
                });

                task.setOnFailed(e -> {
                    mainTabPane.disableProperty().unbind();
                });

                break;
            case 1: // Forge
                if (forgeVersionMap.isEmpty()) {
                    MessageBox.alertBox(AlertType.INFORMATION, "提示", "沒有 " + gameVersionValue + " 版本的 Forge，請選擇其他版本。");
                    break;
                }
                final String forgeVersionValue = modVersionTextField.getText();
                task = new DownloadFile(forgeVersionMap.get(forgeVersionValue), installPathValue + "forge-installer.jar", Program.Status.DOWNLOADING);
                progressBar.progressProperty().bind(task.progressProperty());
                statusLabel.textProperty().bind(task.titleProperty());
                mainTabPane.disableProperty().bind(task.runningProperty());
                thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();

                task.setOnSucceeded(e -> {
                    final Task<Void> installTask = new InstallForge(installPathValue);
                    progressBar.progressProperty().bind(installTask.progressProperty());
                    statusLabel.textProperty().bind(installTask.titleProperty());
                    mainTabPane.disableProperty().bind(installTask.runningProperty());
                    final Thread installThread = new Thread(installTask);
                    installThread.setDaemon(true);
                    installThread.start();

                    installTask.setOnSucceeded(ie -> {
                        createEulaFile(installPathValue);
                        createPropertiesFile(installPathValue, serverProperties);
                        if (Integer.parseInt(forgeVersionValue.split("\\.")[1]) >= 17) {
                            final String argsFile = "@libraries/net/minecraftforge/forge/" + forgeVersionValue + (isWindows ? "/win" : "/unix") + "_args.txt";
                            createRunFile(installPathValue, argsFile, maxRamValue, minRamValue, guiCheckBox.isSelected());
                        }
                        else {
                            try {
                                Process process = Runtime.getRuntime().exec((isWindows ? "mv" : "move") + " *.jar server.jar", null, new File(installPathValue));
                                process.waitFor();
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            createRunFile(installPathValue, "server.jar", maxRamValue, minRamValue, guiCheckBox.isSelected());
                        }
                        progressBar.progressProperty().unbind();
                        progressBar.setProgress(1);
                        MessageBox.alertBox(AlertType.INFORMATION, "安裝完成", "Minecraft 伺服器已安裝至您所選擇的位置，請至該處執行 StartServer.bat 或 StartServer.sh。");
                        mainTabPane.disableProperty().unbind();
                    });

                    installTask.setOnFailed(ie -> {
                        mainTabPane.disableProperty().unbind();
                    });
                });

                task.setOnFailed(e -> {
                    mainTabPane.disableProperty().unbind();
                });
                break;
            case 2: // Fabric
                String fabricVersionValue;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(Program.Url.FABRIC_VERSION).openStream()))) {
                    fabricVersionValue = reader.readLine();
                }
                catch (Exception exception) {
                    MessageBox.alertBox(AlertType.ERROR, "錯誤", "請檢常網路設備是否連接正常");
                    exception.printStackTrace();
                    break;
                }
                final String fabricInstallerUrl = "https://maven.fabricmc.net/net/fabricmc/fabric-installer/" + fabricVersionValue + "/fabric-installer-" + fabricVersionValue + ".jar";
                task = new DownloadFile(fabricInstallerUrl, installPathValue + "fabric-installer.jar", Program.Status.DOWNLOADING);
                progressBar.progressProperty().bind(task.progressProperty());
                statusLabel.textProperty().bind(task.titleProperty());
                mainTabPane.disableProperty().bind(task.runningProperty());
                thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();

                task.setOnSucceeded(e -> {
                    final Task<Void> installTask = new InstallFabric(installPathValue, gameVersionValue);
                    progressBar.progressProperty().bind(installTask.progressProperty());
                    statusLabel.textProperty().bind(installTask.titleProperty());
                    mainTabPane.disableProperty().bind(installTask.runningProperty());
                    final Thread installThread = new Thread(installTask);
                    installThread.setDaemon(true);
                    installThread.start();

                    installTask.setOnSucceeded(ie -> {
                        createRunFile(installPathValue, "fabric-server-launch.jar", maxRamValue, minRamValue, guiCheckBox.isSelected());
                        createEulaFile(installPathValue);
                        createPropertiesFile(installPathValue, serverProperties);
                        MessageBox.alertBox(AlertType.INFORMATION, "安裝完成", "Minecraft 伺服器已安裝至您所選擇的位置，請至該處執行 StartServer.bat 或 StartServer.sh。");
                        mainTabPane.disableProperty().unbind();
                    });

                    installTask.setOnFailed(ie -> {
                        mainTabPane.disableProperty().unbind();
                    });
                });

                task.setOnFailed(e -> {
                    mainTabPane.disableProperty().unbind();
                });
                break;
            default: break;
        }
    }

    public void onGameVersionButton(ActionEvent event) {
        final String filename = "GameVersion.info";
        final Task<Void> task = new DownloadFile(Program.Url.GAME_VERSION, filename, Program.Status.PROCESSING);
        progressBar.progressProperty().bind(task.progressProperty());
        statusLabel.textProperty().bind(task.titleProperty());
        mainTabPane.disableProperty().bind(task.runningProperty());
        final Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        task.setOnSucceeded(e -> {
            String line = new String();
            String str[] = new String[2];
            gameVersionVector.clear();
            gameVersionMap.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
                while ((line = reader.readLine()) != null) {
                    str = line.split(" ");
                    gameVersionVector.add(str[0]);
                    gameVersionMap.put(str[0], str[1]);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            final String newVersion = MessageBox.choiceDialog("選擇版本", "請選擇遊戲主版本", gameVersionVector, gameVersionTextField.getText());
            if (!newVersion.equals(gameVersionTextField.getText())) {
                modVersionTextField.setText("");
                modVersionChoiceBox.getSelectionModel().selectFirst();
            }
            modVersionChoiceBox.setDisable(newVersion.equals(""));
            gameVersionTextField.setText(newVersion);
            mainTabPane.disableProperty().unbind();
        });

        task.setOnFailed(e -> {
            mainTabPane.disableProperty().unbind();
        });
    }

    public void onModVersionChoiceBox() {
        if (modVersionChoiceBox.getSelectionModel().getSelectedIndex() == 1) { // Forge
            modVersionButton.setDisable(false);
        }
        else {
            modVersionTextField.setText("");
            modVersionButton.setDisable(true);
        }
        if (Integer.parseInt(gameVersionTextField.getText().split("\\.")[1]) < 14) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "Fabric 僅支援 1.14 或以上的版本。");
            modVersionChoiceBox.getSelectionModel().selectFirst();
        }
    }

    public void onModVersionButton(ActionEvent event) {
        if (gameVersionTextField.getText().equals("")) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "請先選擇伺服器遊戲版本。");
            return;
        }

        final String gameVersion = gameVersionTextField.getText();
        final String filename = "ForgeVersion.info";
        final Task<Void> task = new DownloadFile(Program.Url.FORGE_VERSION, filename, Program.Status.PROCESSING);
        progressBar.progressProperty().bind(task.progressProperty());
        statusLabel.textProperty().bind(task.titleProperty());
        mainTabPane.disableProperty().bind(task.runningProperty());
        final Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

        task.setOnSucceeded(e -> {
            String line = new String();
            String str[] = new String[3];
            forgeVersionVector.clear();
            forgeVersionMap.clear();
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
                while ((line = reader.readLine()) != null) {
                    str = line.split(" ");
                    if (gameVersion.equals(str[0])) {
                        forgeVersionVector.add(str[2]);
                        forgeVersionMap.put(str[2], "https://maven.minecraftforge.net/net/minecraftforge/forge/" + str[2] + "/forge-" + str[2] + "-installer.jar");
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            if (forgeVersionVector.isEmpty())
                MessageBox.alertBox(AlertType.INFORMATION, "提示", "沒有 " + gameVersion + " 這個版本的 Forge 伺服器。");
            else modVersionTextField.setText(
                MessageBox.choiceDialog("選擇版本", "請選 Forge 版本", forgeVersionVector, modVersionTextField.getText())
            );
            mainTabPane.disableProperty().unbind();
        });

        task.setOnFailed(e -> {
            mainTabPane.disableProperty().unbind();
        });
    }

    public void onInstallPathButton() {
        mainTabPane.setDisable(true);
        DirectoryChooser chooser = new DirectoryChooser();
        File directory = chooser.showDialog(new Stage());
        if (directory == null) {
            mainTabPane.setDisable(false);
            return;
        }
        if (directory.list().length > 0) {
            if (!MessageBox.confirmationDialog("提示", "安裝伺服器的位置建議為空資料夾，您所選取的資料夾不為空，確定仍然要選擇此資料夾嗎？")) {
                mainTabPane.setDisable(false);
                return;
            }
        }
        installPathTextField.setText(directory.getPath());
        mainTabPane.setDisable(false);
    }

    public void onChangeRamCheckBox(ActionEvent event) {
        if (changeRamCheckBox.isSelected()) {
            MessageBox.alertBox(AlertType.WARNING, "警告", "更改伺服器記憶體限制可能會影響伺服器的穩定性，或甚至無法正常啟動，請小心調整。");
            maxRamSlider.setDisable(false);
            maxRamTextField.setDisable(false);
            minRamSlider.setDisable(false);
            minRamTextField.setDisable(false);
            changeRamCheckBox.setDisable(true);
            ramResetButton.setDisable(false);
        }
    }

    public void onResetRamButton(ActionEvent event) {
        resetRamOptions();
    }

    public void onMaxRamSlider() {
        final int newVal = (int)maxRamSlider.getValue();
        if (newVal < minRamValue) {
            MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "最大記憶體限制必須大於等於最小限制。");
            maxRamSlider.setValue(minRamValue);
            maxRamTextField.setText(String.valueOf(minRamValue));
            return;
        }
        maxRamTextField.setText(String.valueOf(newVal));
        maxRamValue = newVal;
    }

    public void onMaxRamTextField(ActionEvent event) {
        if (fixTextField(maxRamTextField, String.valueOf((int)maxRamSlider.getValue())))
            return;
        final int newVal = Integer.parseInt(maxRamTextField.getText());
        if (newVal < minRamValue) {
            MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "最大記憶體限制必須大於等於最小限制。");
            maxRamSlider.setValue(minRamValue);
            maxRamTextField.setText(String.valueOf(minRamValue));
            return;
        }
        maxRamSlider.setValue(newVal);
        maxRamValue = newVal;
    }

    public void onMinRamSlider() {
        final int newVal = (int)minRamSlider.getValue();
        if (newVal > maxRamValue) {
            MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "最大記憶體限制必須大於最小限制。");
            minRamSlider.setValue(maxRamValue);
            minRamTextField.setText(String.valueOf(maxRamValue));
            return;
        }
        minRamTextField.setText(String.valueOf(newVal));
        minRamValue = newVal;
    }

    public void onMinRamTextField(ActionEvent event) {
        if (fixTextField(minRamTextField, String.valueOf((int)minRamSlider.getValue())))
            return;
        final int newVal = Integer.parseInt(minRamTextField.getText());
        if (newVal > maxRamValue) {
            MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "最大記憶體限制必須大於最小限制。");
            minRamSlider.setValue(maxRamValue);
            minRamTextField.setText(String.valueOf(maxRamValue));
            return;
        }
        minRamSlider.setValue(newVal);
        minRamValue = newVal;
    }

    public void onOptionResetButton() {
        resetAdnavcedOptions();
    }

    public void onPortTextField(ActionEvent event) {
        if (fixTextField(portTextField, serverProperties.get(Program.Options.SERVER_PORT)))
            return;
        final int newVal = Integer.parseInt(portTextField.getText());
        if (newVal < 1025 || newVal > 65535) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "請輸入 1025 ~ 65535 內的值。");
            portTextField.setText(serverProperties.get(Program.Options.SERVER_PORT));
            return;
        }
        MessageBox.alertBox(AlertType.WARNING, "警告", "更改伺服器連接埠可能與其他應用程式產生衝突，造成無法連線或系統不穩定，請確認設置的連接埠是否為空連接埠，並為其設置防火牆通道允許通過。");
        serverProperties.put(Program.Options.SERVER_PORT, String.valueOf(newVal));
    }

    public void onMaxPlayerTextField(ActionEvent event) {
        if (fixTextField(maxPlayerTextField, serverProperties.get(Program.Options.MAX_PLAYERS)))
            return;
        final int newVal = Integer.parseInt(maxPlayerTextField.getText());
        if (newVal < 1) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "請輸入正整數。");
            maxPlayerTextField.setText(serverProperties.get(Program.Options.MAX_PLAYERS));
            return;
        }
        serverProperties.put(Program.Options.MAX_PLAYERS, String.valueOf(newVal));
    }

    public void onSpawnProtectionTextField(ActionEvent event) {
        if (fixTextField(spawnProtectionTextField, serverProperties.get(Program.Options.SPAWN_PROTECTION)))
            return;
        serverProperties.put(Program.Options.SPAWN_PROTECTION, spawnProtectionTextField.getText());
    }

    public void onViewDistanceTextField(ActionEvent event) {
        if (fixTextField(viewDistanceTextField, serverProperties.get(Program.Options.VIEW_DISTANCE)))
            return;
        serverProperties.put(Program.Options.VIEW_DISTANCE, viewDistanceTextField.getText());
    }

    public void onPvpChoiceBox(ActionEvent event) {
        serverProperties.put(Program.Options.PVP,
            commandBolckChoiceBox.getSelectionModel().getSelectedIndex() == 0 ? "true" : "false");
    }

    public void onGamemodeChoiceBox(ActionEvent event) {
        serverProperties.put(Program.Options.GAMEMODE,
            String.valueOf(gamemodeChoiceBox.getSelectionModel().getSelectedIndex()));
    }

    public void onDifficultyChoiceBox(ActionEvent event) {
        serverProperties.put(Program.Options.DIFFICULTY,
            String.valueOf(difficultyChoiceBox.getSelectionModel().getSelectedIndex()));
    }

    public void onCommandBlockChoiceBox(ActionEvent event) {
        serverProperties.put(Program.Options.ENABLE_COMMAND_BLOCK,
            commandBolckChoiceBox.getSelectionModel().getSelectedIndex() == 0 ? "true" : "false");
    }

    public void onOnlineModeChoiceBox(ActionEvent event) {
        serverProperties.put(Program.Options.ONLINE_MODE,
            onlineModeChoiceBox.getSelectionModel().getSelectedIndex() == 0 ? "true" : "false");
    }

    public void onMotdTextField(ActionEvent event) {
        serverProperties.put(Program.Options.MOTD, motdTextField.getText());
    }
}
