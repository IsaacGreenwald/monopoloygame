import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MonopolyApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        Board board = new Board();
        Scene scene = new Scene(board, 700, 700);
        primaryStage.setTitle("Monopoly Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
