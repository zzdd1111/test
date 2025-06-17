package comp1110.ass2.gui;

import comp1110.ass2.common.Common;
import comp1110.ass2.common.Constants;
import comp1110.ass2.entity.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField boardTextField;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     * Pr03015iPp03015iPy03015iPc03015i|A32E|Bp03c11y14c08y12r01y07r05p09r06p00c02p15c04y10c15y13r07y03r09p11r12p01c06p14c08y05c00y02r11y06r13p07r04p10c09p12c13y00c07y08r14y01r02p04r03p05c10p08
     */
    public void displayState(String state) {
        // FIXME Task 5: implement the simple state viewer

        Common common = new Common();
        StateEntity game = new StateEntity();
        game.decode(state);
        var playerArr = game.playerArr;
        var assam = game.assam;
        var board = game.board;

        // Initial Board
        GridPane gridPane = new GridPane();
        gridPane.getColumnConstraints().add(new ColumnConstraints(2.0 / 3 * 1200));
        gridPane.getColumnConstraints().add(new ColumnConstraints(1.0 / 3 * 1200));

        Image image = new Image(getClass().getResourceAsStream("Image/Board.png"));
        ImageView imageView = new ImageView(image);

        double imgWidth = 625;
        double imgHeight = 625;
        imageView.setFitWidth(imgWidth);
        imageView.setFitHeight(imgHeight);
        GridPane.setHalignment(imageView, HPos.CENTER);
        gridPane.add(imageView, 0, 0);

        Canvas canvas = new Canvas(imgWidth, imgHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double startX = 183;
        double startY = 93;
        double squareWidth = 63;
        double squareHeight = 63;

        for(int row = 0;row<board.ruglist.length;row++){
            for(int col = 0; col < board.ruglist[0].length;col++){
                double x = startX + col * squareWidth;
                double y = startY + row * squareHeight;

                //draw the square
                gc.setFill(common.ConvertPaintColor(board.ruglist[row][col].color));
                gc.fillRect(x, y, squareWidth, squareHeight);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(x, y, squareWidth, squareHeight);

                //draw assem
                if(row == assam.position.getX() && col== assam.position.getY())
                {
                    Image assem_img = new Image(getClass().getResourceAsStream("Image/"+assam.direction+".png"));
                    gc.drawImage(assem_img, x, y, squareWidth, squareHeight);
                }

            }
        }



        gridPane.add(canvas, 0, 0);

        //initial Player
        GridPane rightGridPane = new GridPane();
        for(int i =0;i<playerArr.length;i++){

            PlayerEntity player = playerArr[i];
            String playerInfo = "Player"+(i+1)
                    +"\n"+"Color:"+player.color
                    +"\n"+"Dirhams:"+player.dirhams
                    +"\n"+"Rugs:"+player.rugNumber
                    +"\n"+"State:"+(player.status=='i'?"in":"out");
            TextArea textArea = new TextArea(playerInfo);
            textArea.setStyle("-fx-border-color: " + common.ConvertColor(player.color));
            textArea.setEditable(false);
            textArea.setPrefWidth(300);
            textArea.setPrefHeight(150);

            rightGridPane.add(textArea,  0, i);
        }

        gridPane.add(rightGridPane, 1, 0);

        controls.getChildren().add(gridPane);
    }



    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel,
                boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);

        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void show(Stage stage,String state) throws Exception{
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);

        displayState(state);
        stage.setScene(scene);
        stage.show();
    }
}
