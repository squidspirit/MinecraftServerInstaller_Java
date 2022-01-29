package application;

import java.util.Vector;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert.AlertType;

public class MessageBox {
    public static void alertBox (AlertType alertType, String title, String header) {
        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    public static void alertBox (AlertType alertType, String title, String header, String message) {
        final Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static String choiceDialog (String title, String header, Vector<String> vec, String selected) {
        final ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        for (var str : vec) {
            dialog.getItems().add(str);
        }
        if (selected == "") dialog.setSelectedItem(vec.get(0));
        else dialog.setSelectedItem(selected);
        dialog.showAndWait();
        if (dialog.getResult() == null) return "";
        return dialog.getResult();
    }
}
