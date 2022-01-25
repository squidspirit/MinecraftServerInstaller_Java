package application;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
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
    @FXML private Spinner<Integer> maxRamSpinner;
    @FXML private Slider minRamSlider;
    @FXML private Spinner<Integer> minRamSpinner;
    @FXML private CheckBox guiCheckBox;
    @FXML private CheckBox eulaCheckBox;

    /** Advance Options */
    @FXML private Spinner<Integer> portSpinner;
    @FXML private Spinner<Integer> maxPlayerSpinner;
    @FXML private Spinner<Integer> spawnProtectionSpinner;
    @FXML private Spinner<Integer> viewDistanceSpinner;
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
        maxRamSlider.setValue(1024);
        maxRamSlider.setDisable(true);
        maxRamSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(128, 10240, 1024, 128));
        maxRamSpinner.setDisable(true);
        minRamSlider.setValue(1024);
        minRamSlider.setDisable(true);
        minRamSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(128, 10240, 1024, 128));
        minRamSpinner.setDisable(true);
        ramResetButton.setDisable(true);

        /** Advance Options */
        portSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10000, 65535, 25565, 1));
        maxPlayerSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 20, 1));
        spawnProtectionSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 128, 16, 1));
        viewDistanceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 32, 10, 1));
        pvpChoiceBox.getItems().clear();
        pvpChoiceBox.getItems().addAll("true", "false");
        pvpChoiceBox.getSelectionModel().selectFirst();
        gamemodeChoiceBox.getItems().clear();
        gamemodeChoiceBox.getItems().addAll("survival", "creative", "adventure", "spectator");
        gamemodeChoiceBox.getSelectionModel().selectFirst();
        difficultyChoiceBox.getItems().clear();
        difficultyChoiceBox.getItems().addAll("peaceful", "easy", "normal", "hard");
        difficultyChoiceBox.getSelectionModel().select(2);
        commandBolckChoiceBox.getItems().clear();
        commandBolckChoiceBox.getItems().addAll("true", "false");
        commandBolckChoiceBox.getSelectionModel().selectLast();
        onlineModeChoiceBox.getItems().clear();
        onlineModeChoiceBox.getItems().addAll("true", "false");
        onlineModeChoiceBox.getSelectionModel().selectFirst();

        /** Information */
        nameLabel.setText(Information.Name);
        versionLabel.setText(Information.Version);
        authorLabel.setText(Information.Author);
        emailTextField.setText(Information.Email);
        emailTextField.setEditable(false);
        tutorialTextField.setText(Information.Tutorial);
        tutorialTextField.setEditable(false);
        websiteTextField.setText(Information.Website);
        websiteTextField.setEditable(false);
    }
}
