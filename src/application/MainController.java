package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {
    Vector<Pair<String, String>> gameVersionList = new Vector<>();

    /** Public Items */
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
    }

    public void resetRamOptions() {
        changeRamCheckBox.setDisable(false);
        changeRamCheckBox.setSelected(false);
        maxRamSlider.setValue(1024);
        maxRamSlider.setDisable(true);
        maxRamTextField.setText("1024");
        maxRamTextField.setDisable(true);
        minRamSlider.setValue(1024);
        minRamSlider.setDisable(true);
        minRamTextField.setText("1024");
        minRamTextField.setDisable(true);
        ramResetButton.setDisable(true);
    }

    public void resetAdnavcedOptions() {
        portTextField.setText("25565");
        maxPlayerTextField.setText("20");
        spawnProtectionTextField.setText("16");
        viewDistanceTextField.setText("10");
        pvpChoiceBox.getSelectionModel().selectFirst();
        gamemodeChoiceBox.getSelectionModel().selectFirst();
        difficultyChoiceBox.getSelectionModel().select(2);
        commandBolckChoiceBox.getSelectionModel().selectLast();
        onlineModeChoiceBox.getSelectionModel().selectFirst();
    }

    /** Actions */
    public void onStartInstallButton(ActionEvent event) {
        if (!eulaCheckBox.isSelected()) {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "您必須同意 EULA 條款。");
            return;
        }
        if (gameVersionTextField.getText() == "") {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "請選擇伺服器遊戲版本。");
            return;
        }
        if (modVersionChoiceBox.getSelectionModel().getSelectedIndex() == 1) {
            if (modVersionTextField.getText() == "") {
                MessageBox.alertBox(AlertType.INFORMATION, "提示", "請選擇模組版本，或將其調整為不使用。");
                return;
            }
        }

    }

    public void onGameVersionButton(ActionEvent event) {
        final String filename = "GameVersion.info";
        Task<Void> task = new DownloadFile(Program.Url.GAME_VERSION, filename);
        progressBar.progressProperty().bind(task.progressProperty());
        statusLabel.textProperty().bind(task.titleProperty());
        gameVersionButton.disableProperty().bind(task.runningProperty());
        Thread downloadThread = new Thread(task);
        downloadThread.setDaemon(true);
        downloadThread.start();

        task.setOnSucceeded(e -> {
            String line = new String();
            String str[] = new String[2];
            Vector<Pair<String, String>> vec = new Vector<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
                while ((line = reader.readLine()) != null) {
                    str = line.split(" ");
                    vec.add(new Pair<>(str[0], str[1]));
                }
                gameVersionList = vec;
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            gameVersionTextField.setText(
                MessageBox.choiceDialog("選擇版本", "請選擇遊戲主版本", gameVersionList, gameVersionTextField.getText())
            );
        });  
    }

    public void onModVersionButton(ActionEvent event) {
        if (gameVersionTextField.getText() == "") {
            MessageBox.alertBox(AlertType.INFORMATION, "提示", "請先選擇伺服器遊戲版本。");
            return;
        }

        final String filename = "ForgeVersion.info";
        Task<Void> task = new DownloadFile(Program.Url.FORGE_VERSION, filename);
        progressBar.progressProperty().bind(task.progressProperty());
        statusLabel.textProperty().bind(task.titleProperty());
        gameVersionButton.disableProperty().bind(task.runningProperty());
        Thread downloadThread = new Thread(task);
        downloadThread.setDaemon(true);
        downloadThread.start();

        task.setOnSucceeded(e -> {
            String line = new String();
            String str[] = new String[2];
            Vector<Pair<String, String>> vec = new Vector<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
                while ((line = reader.readLine()) != null) {
                    str = line.split(" ");
                    vec.add(new Pair<>(str[0], str[1]));
                }
                gameVersionList = vec;
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            gameVersionTextField.setText(
                MessageBox.choiceDialog("選擇版本", "請選擇遊戲主版本", gameVersionList, gameVersionTextField.getText())
            );
        });
    }

    public void onInstallPathButton() {
        DirectoryChooser chooser = new DirectoryChooser();
        File directory = chooser.showDialog(new Stage());
        if (directory != null) {
            installPathTextField.setText(directory.getPath());
        }
    }

    public void onChangeRamCheckBox(ActionEvent event) {
        if (changeRamCheckBox.isSelected()) {
            MessageBox.alertBox(AlertType.WARNING, changeRamCheckBox.getText(), "更改伺服器記憶體限制可能會影響伺服器的穩定性，或甚至無法正常啟動，請小心調整。");
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

    public void onModVersionChoiceBox() {
        if (modVersionChoiceBox.getSelectionModel().getSelectedIndex() == 1) {
            modVersionButton.setDisable(false);
            return;
        }
        modVersionTextField.setText("");
        modVersionButton.setDisable(true);
    }

    public void onMaxRamSlider() {
        int newVal = (int)maxRamSlider.getValue();
        int minVal = (int)minRamSlider.getValue();
        if (newVal >= minVal) {
            maxRamTextField.setText(String.valueOf(newVal));
            return;
        }
        MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "最大記憶體限制必須大於最小限制。");
        maxRamSlider.setValue(minVal);
        maxRamTextField.setText(String.valueOf(minVal));
    }

    public void onMaxRamTextField(ActionEvent event) {
        if (!maxRamTextField.getText().matches("^[0-9]+$")) {
            MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "請輸入正整數。");
            maxRamTextField.setText(String.valueOf((int)maxRamSlider.getValue()));
            return;
        }
        int newVal = Integer.parseInt(maxRamTextField.getText());
        int minVal = (int)minRamSlider.getValue();
        if (newVal >= minVal) {
            maxRamSlider.setValue(newVal);
            return;
        }
        MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "最大記憶體限制必須大於最小限制。");
        maxRamSlider.setValue(minVal);
        maxRamTextField.setText(String.valueOf(minVal));
    }

    public void onMinRamSlider() {
        int newVal = (int)minRamSlider.getValue();
        int maxVal = (int)maxRamSlider.getValue();
        if (newVal <= maxVal) {
            minRamTextField.setText(String.valueOf(newVal));
            return;
        }
        MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "最大記憶體限制必須大於最小限制。");
        minRamSlider.setValue(maxVal);
        minRamTextField.setText(String.valueOf(maxVal));
    }

    public void onMinRamTextField(ActionEvent event) {
        if (!minRamTextField.getText().matches("^[0-9]+$")) {
            MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "請輸入正整數。");
            minRamTextField.setText(String.valueOf((int)minRamSlider.getValue()));
            return;
        }
        int newVal = Integer.parseInt(minRamTextField.getText());
        int maxVal = (int)maxRamSlider.getValue();
        if (newVal <= maxVal) {
            minRamSlider.setValue(newVal);
            return;
        }
        MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "最大記憶體限制必須大於最小限制。");
        minRamSlider.setValue(maxVal);
        minRamTextField.setText(String.valueOf(maxVal));
    }
}
