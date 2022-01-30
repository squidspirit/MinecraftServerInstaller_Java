package application;

import java.util.Vector;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert.AlertType;

public class MessageBox {
    public static void alertBox (AlertType alertType, String title, String message) {
        final Alert dialog = new Alert(alertType);
        dialog.setTitle(title);
        dialog.setHeaderText("");
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    public static void alertBox (AlertType alertType, String title, String message, String header) {
        final Alert dialog = new Alert(alertType);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        dialog.showAndWait();
    }

    public static String choiceDialog (String title, String message, Vector<String> vec, String selected) {
        final ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle(title);
        dialog.setHeaderText(message);
        for (var str : vec) {
            dialog.getItems().add(str);
        }
        if (selected.equals("")) dialog.setSelectedItem(vec.get(0));
        else dialog.setSelectedItem(selected);
        dialog.showAndWait();
        if (dialog.getResult() == null) return "";
        return dialog.getResult();
    }

    public static boolean confirmationDialog(String title, String message) {
        final Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setTitle(title);
        dialog.setHeaderText("");
        dialog.setContentText(message);
        dialog.showAndWait();
        return (dialog.getResult() == ButtonType.OK);
    }
}
