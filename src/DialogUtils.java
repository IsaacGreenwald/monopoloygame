import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;
import java.util.Optional;

public class DialogUtils {

    /**
     * Displays an alert with a given message to the player.
     *
     * @param message The message to be displayed in the alert.
     */
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Window window = javafx.stage.Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        alert.initOwner(window);
        alert.setTitle("Monopoly Game Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Prompts the user with a dialog containing a specific message and set of options.
     *
     * @param message The message to be displayed in the dialog.
     * @param options Varargs of ButtonType options for the dialog.
     * @return The ButtonType choice selected by the user
     */
    public static ButtonType promptUser(String message, ButtonType... options) {
        Dialog<ButtonType> dialog = new Dialog<>();
        Window window = javafx.stage.Window.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null);
        dialog.initOwner(window);
        dialog.setTitle("Property Purchase");
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().addAll(options);
        Optional<ButtonType> result = dialog.showAndWait();
        return result.orElse(ButtonType.NO);
    }

}
