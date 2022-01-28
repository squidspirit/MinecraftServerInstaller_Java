package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

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
    @FXML private TextField installLocatinTextField = new TextField();
    @FXML private Button installLocationButton;
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
        installLocatinTextField.setEditable(false);
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
        nameLabel.setText(Information.NAME);
        versionLabel.setText(Information.VERSION);
        authorLabel.setText(Information.AUTHOR);
        emailTextField.setText(Information.EMAIL);
        emailTextField.setEditable(false);
        tutorialTextField.setText(Information.TUTORIAL);
        tutorialTextField.setEditable(false);
        websiteTextField.setText(Information.WEBSITE);
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
    public void onChangeRamCheckBox(ActionEvent event) {
        if (changeRamCheckBox.isSelected()) {
            MessageBox.alertBox(AlertType.INFORMATION, changeRamCheckBox.getText(), "更改伺服器記憶體限制可能會影響伺服器的穩定性，或甚至無法正常啟動，請小心調整。");
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

    public void onGameVersionButton(ActionEvent event) {
        if (Information.VersionList.gameVersion.isEmpty()) Information.VersionList.gameVersion = Network.getGameVersion();
        var vec = Information.VersionList.gameVersion;
        if (vec.isEmpty()) return;
        String version = MessageBox.choiceDialog(vec, "遊戲版本", "請選擇 Minecraft 主版本", gameVersionTextField.getText());
        if (version != null) {
            gameVersionTextField.setText(version);
        }
    }

    public void onModVersionChoiceBox() {
        if (modVersionChoiceBox.getSelectionModel().getSelectedIndex() == 0) {
            modVersionButton.setDisable(true);
            return;
        }
        modVersionButton.setDisable(false);
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
