package comp1110.ass2.gui;

import comp1110.ass2.common.Common;
import comp1110.ass2.entity.PlayerEntity;
import comp1110.ass2.entity.StateEntity;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static comp1110.ass2.Marrakech.*;


public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    private final Group controls = new Group();
    private TextField boardTextField;
    private int playernumber=0;
    int[] xarray = {0, 1, 0, -1};
    int[] yarray = {-1, 0, +1, 0};
    int clickbutton[] = new int[2];

    private final Image boardImage = new Image(getClass().getResourceAsStream("Image/Board.png"));

    String state="Pc03015iPy03015iPp03015iPr03015iA66EBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
    //String state="Pc02901iPy00000oPp04301iPr04801iA02EBp21p43p43c31r07p31c51p25c25n00c31r36p31c51p25p37r43p15r45r45r48r18c34r43p13r50p10r20p22c34p30y01r50p40p40r38c39c39c29r40p29p08c30c30p46p46r22c23c23";
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

        //Image image = new Image(getClass().getResourceAsStream("Image/Board.png"));
        ImageView imageView = new ImageView(boardImage);

        double imgWidth = 625;
        double imgHeight = 625;
        imageView.setFitWidth(imgWidth);
        imageView.setFitHeight(imgHeight);
        GridPane.setHalignment(imageView, HPos.CENTER);
        gridPane.add(imageView, 0, 0);

        javafx.scene.canvas.Canvas canvas = new Canvas(imgWidth, imgHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double startX = 183;
        double startY = 93;
        double squareWidth = 63;
        double squareHeight = 63;

        for (int row = 0; row < board.ruglist.length; row++) {
            for (int col = 0; col < board.ruglist[0].length; col++) {
                double x = startX + row * squareWidth;
                double y = startY + col * squareHeight;

                //draw the square
                var color = common.ConvertPaintColor(board.ruglist[row][col].color);
                gc.setFill(color);
                gc.fillRect(x, y, squareWidth, squareHeight);
                gc.setStroke(Color.BLACK);
                if(color==Color.ORANGE) {
                    gc.strokeRect(x, y, squareWidth, squareHeight);
                }else
                {
                    for (int i = 0; i < board.ruglist.length; i++) {
                        for (int j = 0; j < board.ruglist[0].length; j++) {
                            if (i != row || j != col) {
                                double x_b = startX + i * squareWidth;
                                double y_b = startY + j * squareHeight;
                                var first_rug = board.ruglist[row][col];
                                var second_rug = board.ruglist[i][j];
                                if (first_rug.color!= 'n' &&first_rug.color == second_rug.color && first_rug.id == second_rug.id) {
//                                    System.out.println("i="+i+" j="+j+" x="+x+" y="+y);
//                                    System.out.println("r="+row+" c="+col+" nx="+x_b+" ny="+y_b);
                                    if(i == row){
                                        gc.strokeRect(x, y<y_b?y:y_b, squareWidth, squareHeight*2);
                                    }else{
                                        gc.strokeRect(x<x_b?x:x_b, y, squareWidth*2, squareHeight);
                                    }
                                }
                            }
                        }
                    }

                }

                //draw assem
                if (row == assam.position.getX() && col == assam.position.getY()) {
                    Image assem_img = new Image(getClass().getResourceAsStream("Image/A_" + assam.direction + ".png"));
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
            javafx.scene.control.TextArea textArea = new TextArea(playerInfo);
            textArea.setStyle("-fx-border-color: " + common.ConvertColor(player.color));
            textArea.setEditable(false);
            textArea.setPrefWidth(300);
            textArea.setPrefHeight(150);

            rightGridPane.add(textArea,  0, i);
        }

        gridPane.add(rightGridPane, 1, 0);

        if (!controls.getChildren().isEmpty()){
            controls.getChildren().remove(0);
        }

        controls.getChildren().add(gridPane);
    }
        @Override
    public void start(Stage stage) throws Exception {
                // FIXME Task 7 and 15
                Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

                root.getChildren().add(controls);
                displayState(state);
            Image player1 = new Image(getClass().getResourceAsStream("Image/currentplayer.png"));
            ImageView playerview1 = new ImageView(player1);
            playerview1.setX(950); // 设置 X 坐标
            playerview1.setY(15);
            playerview1.setVisible(true);
            root.getChildren().add(playerview1);
            Image player2 = new Image(getClass().getResourceAsStream("Image/currentplayer.png"));
            ImageView playerview2 = new ImageView(player1);
            playerview2.setX(950); // 设置 X 坐标
            playerview2.setY(165);
            playerview2.setVisible(false);
            root.getChildren().add(playerview2);
            Image player3 = new Image(getClass().getResourceAsStream("Image/currentplayer.png"));
            ImageView playerview3= new ImageView(player3);
            playerview3.setX(950); // 设置 X 坐标
            playerview3.setY(315);
            playerview3.setVisible(false);
            root.getChildren().add(playerview3);
            Image player4 = new Image(getClass().getResourceAsStream("Image/currentplayer.png"));
            ImageView playerview4 = new ImageView(player4);
            playerview4.setX(950); // 设置 X 坐标
            playerview4.setY(465);
            playerview4.setVisible(false);
            root.getChildren().add(playerview4);
                StateEntity game = new StateEntity();
                game.decode(state);
                var playerArr = game.playerArr;
                var assam = game.assam;
                var board = game.board;
                double startX = 183;
                double startY = 93;
                double squareWidth = 63;
                double squareHeight = 63;
                Image press = new Image(getClass().getResourceAsStream("Image/press.png"));
                ImageView pressview = new ImageView(press);
                pressview.setX(600); // 设置 X 坐标
                pressview.setY(630);
                pressview.setFitWidth(squareWidth);
                pressview.setFitHeight(squareHeight);
                pressview.setVisible(false);
            Image image1 = new Image(getClass().getResourceAsStream("Image/" +'N' + ".png"));
            ImageView imageView1 = new ImageView(image1);
            imageView1.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
            imageView1.setY(startY + (assam.position.getY() - 1) * squareHeight);
            imageView1.setFitWidth(squareWidth);
            imageView1.setFitHeight(squareHeight);
            root.getChildren().add(imageView1);
            imageView1.setVisible(false);
            Image image2 = new Image(getClass().getResourceAsStream("Image/" + 'E' + ".png"));
            ImageView imageView2 = new ImageView(image2);
            imageView2.setX(startX + (assam.position.getX()+1) * squareWidth); // 设置 X 坐标
            imageView2.setY(startY + (assam.position.getY()) * squareHeight);
            imageView2.setFitWidth(squareWidth);
            imageView2.setFitHeight(squareHeight);
            root.getChildren().add(imageView2);
            imageView2.setVisible(false);
            Image image3 = new Image(getClass().getResourceAsStream("Image/" + 'S' + ".png"));
            ImageView imageView3 = new ImageView(image3);
            imageView3.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
            imageView3.setY(startY + (assam.position.getY()+1) * squareHeight);
            imageView3.setFitWidth(squareWidth);
            imageView3.setFitHeight(squareHeight);
            root.getChildren().add(imageView3);
            imageView3.setVisible(false);
            Image image4 = new Image(getClass().getResourceAsStream("Image/" + 'W' + ".png"));
            ImageView imageView4 = new ImageView(image4);
            imageView4.setX(startX + (assam.position.getX()-1) * squareWidth); // 设置 X 坐标
            imageView4.setY(startY + (assam.position.getY()) * squareHeight);
            imageView4.setFitWidth(squareWidth);
            imageView4.setFitHeight(squareHeight);
            root.getChildren().add(imageView4);
            imageView4.setVisible(false);
                switch (assam.direction) {
                    case 'N':
                        imageView1.setVisible(true);
                        imageView2.setVisible(true);
                        imageView4.setVisible(true);
                        break;
                    case 'E':
                        imageView1.setVisible(true);
                        imageView2.setVisible(true);
                        imageView3.setVisible(true);
                        break;
                    case 'S':
                        imageView2.setVisible(true);
                        imageView3.setVisible(true);
                        imageView4.setVisible(true);
                        break;
                    case 'W':
                        imageView1.setVisible(true);
                        imageView3.setVisible(true);
                        imageView4.setVisible(true);
                        break;

                }
            imageView1.setOnMouseClicked(event -> {
//                System.out.println("direction: N");
                assam.direction = 'N';
                state = state.substring(0, 32 + 3) + 'N' + state.substring(32 + 3 + 1);
//                System.out.println(state);
                imageView1.setVisible(false);
                imageView2.setVisible(false);
                imageView3.setVisible(false);
                imageView4.setVisible(false);
                pressview.setVisible(true);
                displayState(state);
                stage.setScene(scene);
                stage.show();
            });
            imageView2.setOnMouseClicked(event -> {
//                System.out.println("direction: E");
                assam.direction = 'E';
                state = state.substring(0, 32 + 3) + 'E' + state.substring(32 + 3 + 1);
//                System.out.println(state);
                imageView1.setVisible(false);
                imageView2.setVisible(false);
                imageView3.setVisible(false);
                imageView4.setVisible(false);
                pressview.setVisible(true);
                displayState(state);
                stage.setScene(scene);
                stage.show();
            });
            imageView3.setOnMouseClicked(event -> {
//                System.out.println("direction: S");
                assam.direction = 'S';
                state = state.substring(0, 32 + 3) + 'S' + state.substring(32 + 3 + 1);
//                System.out.println(state);
                imageView1.setVisible(false);
                imageView2.setVisible(false);
                imageView3.setVisible(false);
                imageView4.setVisible(false);
                pressview.setVisible(true);
                displayState(state);
                stage.setScene(scene);
                stage.show();
            });
            imageView4.setOnMouseClicked(event -> {
//                System.out.println("direction: W");
                assam.direction = 'W';
                state = state.substring(0, 32 + 3) + 'W' + state.substring(32 + 3 + 1);
//                System.out.println(state);
                imageView1.setVisible(false);
                imageView2.setVisible(false);
                imageView3.setVisible(false);
                imageView4.setVisible(false);
                pressview.setVisible(true);
                displayState(state);
                stage.setScene(scene);
                stage.show();
            });
                displayState(state);
//        Image press =new Image(getClass().getResourceAsStream("Image/press.png"));
//        ImageView pressview= new ImageView(press);
//            pressview.setX(600); // 设置 X 坐标
//            pressview.setY(630);
//            pressview.setFitWidth(squareWidth);
//            pressview.setFitHeight(squareHeight);

            Image highlight1 = new Image(getClass().getResourceAsStream("Image/GreenSquare.png"));
            ImageView highlightview1 = new ImageView(highlight1);
            highlightview1.setFitHeight(squareHeight);
            highlightview1.setFitWidth(squareWidth);
            highlightview1.setVisible(false);
            root.getChildren().add(highlightview1);
                Image highlight2 = new Image(getClass().getResourceAsStream("Image/GreenSquare.png"));
                ImageView highlightview2 = new ImageView(highlight2);
                highlightview2.setFitHeight(squareHeight);
                highlightview2.setFitWidth(squareWidth);
                highlightview2.setVisible(false);
                root.getChildren().add(highlightview2);
                Image highlight3 = new Image(getClass().getResourceAsStream("Image/GreenSquare.png"));
                ImageView highlightview3 = new ImageView(highlight3);
                highlightview3.setFitHeight(squareHeight);
                highlightview3.setFitWidth(squareWidth);
                highlightview3.setVisible(false);
                root.getChildren().add(highlightview3);
                Image highlight4 = new Image(getClass().getResourceAsStream("Image/GreenSquare.png"));
                ImageView highlightview4 = new ImageView(highlight4);
                highlightview4.setFitHeight(squareHeight);
                highlightview4.setFitWidth(squareWidth);
                highlightview4.setVisible(false);
                root.getChildren().add(highlightview4);
                Image highlight5 = new Image(getClass().getResourceAsStream("Image/GreenSquare.png"));
                ImageView highlightview5 = new ImageView(highlight5);
                highlightview5.setFitHeight(squareHeight);
                highlightview5.setFitWidth(squareWidth);
                highlightview5.setVisible(false);
                root.getChildren().add(highlightview5);
                Image highlight6 = new Image(getClass().getResourceAsStream("Image/GreenSquare.png"));
                ImageView highlightview6 = new ImageView(highlight6);
                highlightview6.setFitHeight(squareHeight);
                highlightview6.setFitWidth(squareWidth);
                highlightview6.setVisible(false);
                root.getChildren().add(highlightview6);
                Image highlight7 = new Image(getClass().getResourceAsStream("Image/GreenSquare.png"));
                ImageView highlightview7 = new ImageView(highlight7);
                highlightview7.setFitHeight(squareHeight);
                highlightview7.setFitWidth(squareWidth);
                highlightview7.setVisible(false);
                root.getChildren().add(highlightview7);
                Image highlight8 = new Image(getClass().getResourceAsStream("Image/GreenSquare.png"));
                ImageView highlightview8 = new ImageView(highlight8);
                highlightview8.setFitHeight(squareHeight);
                highlightview8.setFitWidth(squareWidth);
                highlightview8.setVisible(false);
                root.getChildren().add(highlightview8);
                Image dieimage1 = new Image(getClass().getResourceAsStream("Image/" + '1'+ ".png"));
                ImageView dieimageview1 = new ImageView(dieimage1);
                dieimageview1.setX(600); // 设置 X 坐标
                dieimageview1.setY(630);
                dieimageview1.setFitWidth(squareWidth);
                dieimageview1.setFitHeight(squareHeight);
                dieimageview1.setVisible(false);
                root.getChildren().add(dieimageview1);
            Image dieimage2 = new Image(getClass().getResourceAsStream("Image/" + '2'+ ".png"));
            ImageView dieimageview2 = new ImageView(dieimage2);
            dieimageview2.setX(600); // 设置 X 坐标
            dieimageview2.setY(630);
            dieimageview2.setFitWidth(squareWidth);
            dieimageview2.setFitHeight(squareHeight);
            dieimageview2.setVisible(false);
            root.getChildren().add(dieimageview2);
            Image dieimage3 = new Image(getClass().getResourceAsStream("Image/" + '3'+ ".png"));
            ImageView dieimageview3= new ImageView(dieimage3);
            dieimageview3.setX(600); // 设置 X 坐标
            dieimageview3.setY(630);
            dieimageview3.setFitWidth(squareWidth);
            dieimageview3.setFitHeight(squareHeight);
            dieimageview3.setVisible(false);
            root.getChildren().add(dieimageview3);
            Image dieimage4 = new Image(getClass().getResourceAsStream("Image/" + '4'+ ".png"));
            ImageView dieimageview4 = new ImageView(dieimage4);
            dieimageview4.setX(600); // 设置 X 坐标
            dieimageview4.setY(630);
            dieimageview4.setFitWidth(squareWidth);
            dieimageview4.setFitHeight(squareHeight);
            dieimageview4.setVisible(false);
            root.getChildren().add(dieimageview4);
                pressview.setOnMouseClicked(event -> {
                    int dieresult = rollDie();
//                    System.out.println(dieresult);
                    switch (dieresult){
                        case 1:
                            dieimageview1.setVisible(true);
                            break;
                        case 2:
                            dieimageview2.setVisible(true);
                            break;
                        case 3:
                            dieimageview3.setVisible(true);
                            break;
                        case 4:
                            dieimageview4.setVisible(true);
                            break;
                    }
                    String assamstring = moveAssam(assam.encode(), dieresult);
                    assam.position.setX(Integer.parseInt(String.valueOf(assamstring.charAt(1))));
                    assam.position.setY(Integer.parseInt(String.valueOf(assamstring.charAt(2))));
                    assam.direction = assamstring.charAt(3);
                    state = state.substring(0, 32) + assamstring + state.substring(32 + 4);
//                    System.out.println(state);
                    displayState(state);
                    stage.setScene(scene);
                    stage.show();
                    pressview.setVisible(false);
                    int payment = getPaymentAmount(state);

                    char assamcolor = state.charAt(32 + 4 + 1 + assam.position.getX() * 21 + assam.position.getY() * 3);//the color where the assam stands
                    char playercolor = state.charAt(8 * (playernumber) + 1);
                    if (assamcolor == playercolor) {
                        payment = 0;
                    }
                    if (payment != 0) {
                        int ownernumber = 0;
                        for (int i = 0; i < 4; i++) {
                            if (playerArr[i].color == assamcolor) {
                                ownernumber = i;
                                break;
                            }
                        }
                        if (playerArr[playernumber].dirhams < payment) {
                            payment = playerArr[playernumber].dirhams;
                            playerArr[playernumber].dirhams = 0;
                            playerArr[playernumber].status = 'o';
                            playerArr[ownernumber].dirhams += payment;
                        } else {
                            playerArr[playernumber].dirhams -= payment;
                            playerArr[ownernumber].dirhams += payment;
                        }
//                        System.out.println("payment:" + payment);
                        state = playerArr[0].encode() + playerArr[1].encode() + playerArr[2].encode() + playerArr[3].encode() + state.substring(32);

//                        System.out.println("afterpay:"+state);
                        displayState(state);
                        stage.setScene(scene);
                        stage.show();
                    }
                    if (isGameOver(state)) {
                        char winner = getWinner(state);
                        System.out.println("winner is " + winner);
                        Image winneris = new Image(getClass().getResourceAsStream("Image/winneris.png"));
                        ImageView winnerview= new ImageView(winneris);
                        winnerview.setX(300); // 设置 X 坐标
                        winnerview.setY(100);
                        winnerview.setFitWidth(400);
                        winnerview.setFitHeight(200);
//                        winnerview.setVisible(false);
                        root.getChildren().add(winnerview);
                        for (int i=0;i<4;i++){
                            char color[]={'c','y','p','r'};
                            if (color[i]==winner){
                                Image winnerplayer = new Image(getClass().getResourceAsStream("Image/player"+(i+1)+".png"));
                                ImageView winnerplayerview= new ImageView(winnerplayer);
                                winnerplayerview.setX(300); // 设置 X 坐标
                                winnerplayerview.setY(300);
                                winnerplayerview.setFitWidth(400);
                                winnerplayerview.setFitHeight(200);
//                        winnerview.setVisible(false);
                                root.getChildren().add(winnerplayerview);
                            }
                        }
                    } else {
                        if (playerArr[playernumber].rugNumber == 0) {
                            char winner = getWinner(state);
                            System.out.println("winner is " + winner);
                            Image winneris = new Image(getClass().getResourceAsStream("Image/winneris.png"));
                            ImageView winnerview= new ImageView(winneris);
                            winnerview.setX(300); // 设置 X 坐标
                            winnerview.setY(100);
                            winnerview.setFitWidth(400);
                            winnerview.setFitHeight(200);
//                        winnerview.setVisible(false);
                            root.getChildren().add(winnerview);
                            for (int i=0;i<4;i++){
                                char color[]={'c','y','p','r'};
                                if (color[i]==winner){
                                    Image winnerplayer = new Image(getClass().getResourceAsStream("Image/player"+(i+1)+".png"));
                                    ImageView winnerplayerview= new ImageView(winnerplayer);
                                    winnerplayerview.setX(300); // 设置 X 坐标
                                    winnerplayerview.setY(300);
                                    winnerplayerview.setFitWidth(400);
                                    winnerplayerview.setFitHeight(200);
//                        winnerview.setVisible(false);
                                    root.getChildren().add(winnerplayerview);
                                }
                            }
                        }else {
                        int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);
                        String idString = new String();
                        if (id < 10) {
                            idString = '0' + String.valueOf(id);
                        } else {
                            idString = String.valueOf(id);
                        }
                        boolean highlightvalid[] = {false, false, false, false};
                        for (int i = 0; i < 4; i++) {//N button

                            if ((assam.position.getY() - 1 < 0) | (assam.position.getX() + xarray[i] < 0) | (assam.position.getY() - 1 + yarray[i] < 0)) {
                                continue;
                            }
                            String rugstring = playercolor + idString + String.valueOf(assam.position.getX()) + String.valueOf(assam.position.getY() - 1)
                                    + String.valueOf(assam.position.getX() + xarray[i]) + String.valueOf(assam.position.getY() - 1 + yarray[i]);
                            if (isPlacementValid(state, rugstring)) {
                                highlightvalid[0] = true;
                                break;
                            }
                        }
                        if (highlightvalid[0] == true) {
                            highlightview1.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                            highlightview1.setY(startY + (assam.position.getY() - 1) * squareHeight);
                            highlightview1.setVisible(true);
                        }
                        for (int i = 0; i < 4; i++) {//E button

                            if (  (assam.position.getY() + yarray[i] < 0)) {
                                continue;
                            }
                            String rugstring = playercolor + idString + String.valueOf(assam.position.getX() + 1) + String.valueOf(assam.position.getY())
                                    + String.valueOf(assam.position.getX() + 1 + xarray[i]) + String.valueOf(assam.position.getY() + yarray[i]);
                            if (isPlacementValid(state, rugstring)) {
                                highlightvalid[1] = true;

                                break;
                            }
                        }
                        if (highlightvalid[1] == true) {
                            highlightview2.setX(startX + (assam.position.getX() + 1) * squareWidth); // 设置 X 坐标
                            highlightview2.setY(startY + (assam.position.getY()) * squareHeight);
                            highlightview2.setVisible(true);
                        }
                        for (int i = 0; i < 4; i++) {//S button

                            if ( (assam.position.getX() + xarray[i] < 0)) {
                                continue;
                            }
                            String rugstring = playercolor + idString + String.valueOf(assam.position.getX()) + String.valueOf(assam.position.getY() + 1)
                                    + String.valueOf(assam.position.getX() + xarray[i]) + String.valueOf(assam.position.getY() + 1 + yarray[i]);

                            if (isPlacementValid(state, rugstring)) {
                                highlightvalid[2] = true;

                                break;
                            }
                        }
                        if (highlightvalid[2] == true) {
                            highlightview3.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                            highlightview3.setY(startY + (assam.position.getY() + 1) * squareHeight);
                            highlightview3.setVisible(true);
                        }
                        for (int i = 0; i < 4; i++) {//W button

                            if ((assam.position.getX() - 1 < 0) | (assam.position.getX() - 1 + xarray[i] < 0) | (assam.position.getY() + yarray[i] < 0)) {
                                continue;
                            }
                            String rugstring = playercolor + idString + String.valueOf(assam.position.getX() - 1) + String.valueOf(assam.position.getY())
                                    + String.valueOf(assam.position.getX() - 1 + xarray[i]) + String.valueOf(assam.position.getY() + yarray[i]);
                            if (isPlacementValid(state, rugstring)) {
                                highlightvalid[3] = true;

                                break;
                            }
                        }
                        if (highlightvalid[3] == true) {
                            highlightview4.setX(startX + (assam.position.getX() - 1) * squareWidth); // 设置 X 坐标
                            highlightview4.setY(startY + (assam.position.getY()) * squareHeight);
                            highlightview4.setVisible(true);
                        }

                    }}


                });


            highlightview1.setOnMouseClicked(event -> {//N button and the second button
                int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);

                char playercolor = state.charAt(8 * (playernumber) + 1);
                highlightview2.setVisible(false);
                highlightview3.setVisible(false);
                highlightview4.setVisible(false);
                clickbutton[0] = 0;
                String idString = new String();
                if (id < 10) {
                    idString = '0' + String.valueOf(id);
                } else {
                    idString = String.valueOf(id);
                }
                for (int i = 0; i < 4; i++) {
                    if ((assam.position.getY() -1 <0) | (assam.position.getX() + xarray[i] < 0) | (assam.position.getY() + yarray[i] - 1 <0)) {
                        continue;
                    }
                    String rugstring = playercolor + idString + String.valueOf(assam.position.getX()) + String.valueOf(assam.position.getY() - 1)
                            + String.valueOf(assam.position.getX() + xarray[i]) + String.valueOf(assam.position.getY() - 1 + yarray[i]);
                    if (isPlacementValid(state, rugstring)) {
                        if (i == 0) {
                            highlightview5.setX(startX + (assam.position.getX() + xarray[i]) * squareWidth);
                            highlightview5.setY(startY + (assam.position.getY() - 1 + yarray[i]) * squareHeight);
                            highlightview5.setVisible(true);
                        }
                        if (i == 1) {
                            highlightview6.setX(startX + (assam.position.getX() + xarray[i]) * squareWidth);
                            highlightview6.setY(startY + (assam.position.getY() - 1 + yarray[i]) * squareHeight);
                            highlightview6.setVisible(true);
                        }
                        if (i == 2) {
                            highlightview7.setX(startX + (assam.position.getX() + xarray[i]) * squareWidth);
                            highlightview7.setY(startY + (assam.position.getY() - 1 + yarray[i]) * squareHeight);
                            highlightview7.setVisible(true);
                        }
                        if (i == 3) {
                            highlightview8.setX(startX + (assam.position.getX() + xarray[i]) * squareWidth);
                            highlightview8.setY(startY + (assam.position.getY() - 1 + yarray[i]) * squareHeight);
                            highlightview8.setVisible(true);
                        }
                    }
                }
            });
                highlightview2.setOnMouseClicked(event -> {//E button and the second button
                    int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);

                    char playercolor = state.charAt(8 * (playernumber) + 1);
                    highlightview1.setVisible(false);
                    highlightview3.setVisible(false);
                    highlightview4.setVisible(false);
                    clickbutton[0] = 1;
                    String idString = new String();
                    if (id < 10) {
                        idString = '0' + String.valueOf(id);
                    } else {
                        idString = String.valueOf(id);
                    }
                    for (int i = 0; i < 4; i++) {
                        if ((assam.position.getX() + 1 > 6) | (assam.position.getX() + 1 + xarray[i] > 6) | (assam.position.getY() + yarray[i] < 0)) {
                            continue;
                        }
                        String rugstring = playercolor + idString + String.valueOf(assam.position.getX() + 1) + String.valueOf(assam.position.getY())
                                + String.valueOf(assam.position.getX() + 1 + xarray[i]) + String.valueOf(assam.position.getY() + yarray[i]);
                        if (isPlacementValid(state, rugstring)) {
                            if (i == 0) {
                                highlightview5.setX(startX + (assam.position.getX() + 1 + xarray[i]) * squareWidth);
                                highlightview5.setY(startY + (assam.position.getY() + yarray[i]) * squareHeight);
                                highlightview5.setVisible(true);
                            }
                            if (i == 1) {
                                highlightview6.setX(startX + (assam.position.getX() + 1 + xarray[i]) * squareWidth);
                                highlightview6.setY(startY + (assam.position.getY() + yarray[i]) * squareHeight);
                                highlightview6.setVisible(true);
                            }
                            if (i == 2) {
                                highlightview7.setX(startX + (assam.position.getX() + 1 + xarray[i]) * squareWidth);
                                highlightview7.setY(startY + (assam.position.getY() + yarray[i]) * squareHeight);
                                highlightview7.setVisible(true);
                            }
                            if (i == 3) {
                                highlightview8.setX(startX + (assam.position.getX() + 1 + xarray[i]) * squareWidth);
                                highlightview8.setY(startY + (assam.position.getY() + yarray[i]) * squareHeight);
                                highlightview8.setVisible(true);
                            }
                        }
                    }
                });
                highlightview3.setOnMouseClicked(event -> {//S button and the second button
                    int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);

                    char playercolor = state.charAt(8 * (playernumber) + 1);
                    highlightview1.setVisible(false);
                    highlightview2.setVisible(false);
                    highlightview4.setVisible(false);
                    clickbutton[0] = 2;
                    String idString = new String();
                    if (id < 10) {
                        idString = '0' + String.valueOf(id);
                    } else {
                        idString = String.valueOf(id);
                    }
                    for (int i = 0; i < 4; i++) {
                        if ((assam.position.getY() + 1 > 6) | (assam.position.getX() + xarray[i] < 0) | (assam.position.getY() + yarray[i] + 1 > 6)) {
                            continue;
                        }
                        String rugstring = playercolor + idString + String.valueOf(assam.position.getX()) + String.valueOf(assam.position.getY() + 1)
                                + String.valueOf(assam.position.getX() + xarray[i]) + String.valueOf(assam.position.getY() + 1 + yarray[i]);
                        if (isPlacementValid(state, rugstring)) {
                            if (i == 0) {
                                highlightview5.setX(startX + (assam.position.getX() + xarray[i]) * squareWidth);
                                highlightview5.setY(startY + (assam.position.getY() + 1 + yarray[i]) * squareHeight);
                                highlightview5.setVisible(true);
                            }
                            if (i == 1) {
                                highlightview6.setX(startX + (assam.position.getX() + xarray[i]) * squareWidth);
                                highlightview6.setY(startY + (assam.position.getY() + 1 + yarray[i]) * squareHeight);
                                highlightview6.setVisible(true);
                            }
                            if (i == 2) {
                                highlightview7.setX(startX + (assam.position.getX() + xarray[i]) * squareWidth);
                                highlightview7.setY(startY + (assam.position.getY() + 1 + yarray[i]) * squareHeight);
                                highlightview7.setVisible(true);
                            }
                            if (i == 3) {
                                highlightview8.setX(startX + (assam.position.getX() + xarray[i]) * squareWidth);
                                highlightview8.setY(startY + (assam.position.getY() + 1 + yarray[i]) * squareHeight);
                                highlightview8.setVisible(true);
                            }
                        }
                    }
                });
                highlightview4.setOnMouseClicked(event -> {//W button and the second button
                    int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);

                    char playercolor = state.charAt(8 * (playernumber) + 1);
                    highlightview1.setVisible(false);
                    highlightview2.setVisible(false);
                    highlightview3.setVisible(false);
                    clickbutton[0] = 3;
                    String idString = new String();
                    if (id < 10) {
                        idString = '0' + String.valueOf(id);
                    } else {
                        idString = String.valueOf(id);
                    }
                    for (int i = 0; i < 4; i++) {
                        if ((assam.position.getX() - 1 < 0) | (assam.position.getX() - 1 + xarray[i] < 0) | (assam.position.getY() + yarray[i] < 0)) {
                            continue;
                        }
                        String rugstring = playercolor + idString + String.valueOf(assam.position.getX() - 1) + String.valueOf(assam.position.getY())
                                + String.valueOf(assam.position.getX() - 1 + xarray[i]) + String.valueOf(assam.position.getY() + yarray[i]);
                        if (isPlacementValid(state, rugstring)) {
                            if (i == 0) {
                                highlightview5.setX(startX + (assam.position.getX() - 1 + xarray[i]) * squareWidth);
                                highlightview5.setY(startY + (assam.position.getY() + yarray[i]) * squareHeight);
                                highlightview5.setVisible(true);
                            }
                            if (i == 1) {
                                highlightview6.setX(startX + (assam.position.getX() - 1 + xarray[i]) * squareWidth);
                                highlightview6.setY(startY + (assam.position.getY() + yarray[i]) * squareHeight);
                                highlightview6.setVisible(true);
                            }
                            if (i == 2) {
                                highlightview7.setX(startX + (assam.position.getX() - 1 + xarray[i]) * squareWidth);
                                highlightview7.setY(startY + (assam.position.getY() + yarray[i]) * squareHeight);
                                highlightview7.setVisible(true);
                            }
                            if (i == 3) {
                                highlightview8.setX(startX + (assam.position.getX() - 1 + xarray[i]) * squareWidth);
                                highlightview8.setY(startY + (assam.position.getY() + yarray[i]) * squareHeight);
                                highlightview8.setVisible(true);
                            }
                        }
                    }
                });
                highlightview5.setOnMouseClicked(event -> {//the second button N
                    int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);

                    char playercolor = state.charAt(8 * (playernumber) + 1);
                    dieimageview1.setVisible(false);
                    dieimageview2.setVisible(false);
                    dieimageview3.setVisible(false);
                    dieimageview4.setVisible(false);
                    highlightview1.setVisible(false);
                    highlightview2.setVisible(false);
                    highlightview3.setVisible(false);
                    highlightview4.setVisible(false);
                    highlightview5.setVisible(false);
                    highlightview6.setVisible(false);
                    highlightview7.setVisible(false);
                    highlightview8.setVisible(false);
                    clickbutton[1] = 0;
                    String idString = new String();
                    if (id < 10) {
                        idString = '0' + String.valueOf(id);
                    } else {
                        idString = String.valueOf(id);
                    }

                    String rugstring = playercolor + idString + String.valueOf(assam.position.getX() + xarray[clickbutton[0]])
                            + String.valueOf(assam.position.getY() + yarray[clickbutton[0]])
                            + String.valueOf(assam.position.getX() + xarray[clickbutton[0]] + xarray[clickbutton[1]])
                            + String.valueOf(assam.position.getY() + yarray[clickbutton[0]] + yarray[clickbutton[1]]);
//                    System.out.println("rug to place:"+rugstring);
                    state = makePlacement(state, rugstring);
                    for (int i=0;i<4;i++){
                        playerArr[i].rugNumber=Integer.parseInt(String.valueOf(state.charAt(8*i+5)))*10
                                +Integer.parseInt(String.valueOf(state.charAt(8*i+6)));
                    }
                    displayState(state);
                    stage.setScene(scene);
                    stage.show();
//                    System.out.println("end:" + state);
                    playerview1.setVisible(false);
                    playerview2.setVisible(false);
                    playerview3.setVisible(false);
                    playerview4.setVisible(false);
                    for(int i=0;i<5;i++) {
                        if (playernumber != 3) {
                            playernumber += 1;
                            if ((playernumber == 1)&(playerArr[playernumber].status=='i')) {
                                playerview2.setVisible(true);
                                break;
                            } else if ((playernumber == 2)&(playerArr[playernumber].status=='i')) {
                                playerview3.setVisible(true);
                                break;
                            } else if ((playernumber == 3)&(playerArr[playernumber].status=='i')) {
                                playerview4.setVisible(true);
                                break;
                            }
                        } else {
                            playernumber = 0;
                            if(playerArr[playernumber].status=='i'){
                                playerview1.setVisible(true);
                                break;
                            }
                        }
                        if(i==5){
                            System.out.println("There is no player left");
                        }
                    }
                    imageView1.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                    imageView1.setY(startY + (assam.position.getY() - 1) * squareHeight);
                    imageView2.setX(startX + (assam.position.getX()+1) * squareWidth); // 设置 X 坐标
                    imageView2.setY(startY + (assam.position.getY()) * squareHeight);
                    imageView3.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                    imageView3.setY(startY + (assam.position.getY()+1) * squareHeight);
                    imageView4.setX(startX + (assam.position.getX()-1) * squareWidth); // 设置 X 坐标
                    imageView4.setY(startY + (assam.position.getY()) * squareHeight);
                    switch (assam.direction) {
                        case 'N':
                            imageView1.setVisible(true);
                            imageView2.setVisible(true);
                            imageView4.setVisible(true);
                            break;
                        case 'E':
                            imageView1.setVisible(true);
                            imageView2.setVisible(true);
                            imageView3.setVisible(true);
                            break;
                        case 'S':
                            imageView2.setVisible(true);
                            imageView3.setVisible(true);
                            imageView4.setVisible(true);
                            break;
                        case 'W':
                            imageView1.setVisible(true);
                            imageView3.setVisible(true);
                            imageView4.setVisible(true);
                            break;

                    }
                });
            highlightview6.setOnMouseClicked(event -> {//the second button E
                int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);

                char playercolor = state.charAt(8 * (playernumber) + 1);
                dieimageview1.setVisible(false);
                dieimageview2.setVisible(false);
                dieimageview3.setVisible(false);
                dieimageview4.setVisible(false);
                highlightview1.setVisible(false);
                highlightview2.setVisible(false);
                highlightview3.setVisible(false);
                highlightview4.setVisible(false);
                highlightview5.setVisible(false);
                highlightview6.setVisible(false);
                highlightview7.setVisible(false);
                highlightview8.setVisible(false);
                clickbutton[1] = 1;
                String idString = new String();
                if (id < 10) {
                    idString = '0' + String.valueOf(id);
                } else {
                    idString = String.valueOf(id);
                }
                String rugstring = playercolor + idString + String.valueOf(assam.position.getX() + xarray[clickbutton[0]])
                        + String.valueOf(assam.position.getY() + yarray[clickbutton[0]])
                        + String.valueOf(assam.position.getX() + xarray[clickbutton[0]] + xarray[clickbutton[1]])
                        + String.valueOf(assam.position.getY() + yarray[clickbutton[0]] + yarray[clickbutton[1]]);
//                System.out.println("rug to place"+rugstring);
                state = makePlacement(state, rugstring);
                for (int i=0;i<4;i++){
                    playerArr[i].rugNumber=Integer.parseInt(String.valueOf(state.charAt(8*i+5)))*10
                            +Integer.parseInt(String.valueOf(state.charAt(8*i+6)));
                }
                displayState(state);
                stage.setScene(scene);
                stage.show();
//                System.out.println("end:" + state);
                playerview1.setVisible(false);
                playerview2.setVisible(false);
                playerview3.setVisible(false);
                playerview4.setVisible(false);
                for(int i=0;i<5;i++) {
                    if (playernumber != 3) {
                        playernumber += 1;
                        if ((playernumber == 1)&(playerArr[playernumber].status=='i')) {
                            playerview2.setVisible(true);
                            break;
                        } else if ((playernumber == 2)&(playerArr[playernumber].status=='i')) {
                            playerview3.setVisible(true);
                            break;
                        } else if ((playernumber == 3)&(playerArr[playernumber].status=='i')) {
                            playerview4.setVisible(true);
                            break;
                        }
                    } else {
                        playernumber = 0;
                        if(playerArr[playernumber].status=='i'){
                            playerview1.setVisible(true);
                            break;
                        }
                    }
                    if(i==5){
                        System.out.println("There is no player left");
                    }
                }
                imageView1.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                imageView1.setY(startY + (assam.position.getY() - 1) * squareHeight);
                imageView2.setX(startX + (assam.position.getX()+1) * squareWidth); // 设置 X 坐标
                imageView2.setY(startY + (assam.position.getY()) * squareHeight);
                imageView3.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                imageView3.setY(startY + (assam.position.getY()+1) * squareHeight);
                imageView4.setX(startX + (assam.position.getX()-1) * squareWidth); // 设置 X 坐标
                imageView4.setY(startY + (assam.position.getY()) * squareHeight);
                switch (assam.direction) {
                    case 'N':
                        imageView1.setVisible(true);
                        imageView2.setVisible(true);
                        imageView4.setVisible(true);
                        break;
                    case 'E':
                        imageView1.setVisible(true);
                        imageView2.setVisible(true);
                        imageView3.setVisible(true);
                        break;
                    case 'S':
                        imageView2.setVisible(true);
                        imageView3.setVisible(true);
                        imageView4.setVisible(true);
                        break;
                    case 'W':
                        imageView1.setVisible(true);
                        imageView3.setVisible(true);
                        imageView4.setVisible(true);
                        break;

                }
            });
            highlightview7.setOnMouseClicked(event -> {//the second button S
                int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);

                char playercolor = state.charAt(8 * (playernumber) + 1);
                dieimageview1.setVisible(false);
                dieimageview2.setVisible(false);
                dieimageview3.setVisible(false);
                dieimageview4.setVisible(false);
                highlightview1.setVisible(false);
                highlightview2.setVisible(false);
                highlightview3.setVisible(false);
                highlightview4.setVisible(false);
                highlightview5.setVisible(false);
                highlightview6.setVisible(false);
                highlightview7.setVisible(false);
                highlightview8.setVisible(false);
                clickbutton[1] = 2;
                String idString = new String();
                if (id < 10) {
                    idString = '0' + String.valueOf(id);
                } else {
                    idString = String.valueOf(id);
                }
                String rugstring = playercolor + idString + String.valueOf(assam.position.getX() + xarray[clickbutton[0]])
                        + String.valueOf(assam.position.getY() + yarray[clickbutton[0]])
                        + String.valueOf(assam.position.getX() + xarray[clickbutton[0]] + xarray[clickbutton[1]])
                        + String.valueOf(assam.position.getY() + yarray[clickbutton[0]] + yarray[clickbutton[1]]);
//                System.out.println("rug to place"+rugstring);

                state = makePlacement(state, rugstring);
                for (int i=0;i<4;i++){
                    playerArr[i].rugNumber=Integer.parseInt(String.valueOf(state.charAt(8*i+5)))*10
                            +Integer.parseInt(String.valueOf(state.charAt(8*i+6)));
                }
                displayState(state);
                stage.setScene(scene);
                stage.show();
//                System.out.println("end:" + state);
                playerview1.setVisible(false);
                playerview2.setVisible(false);
                playerview3.setVisible(false);
                playerview4.setVisible(false);
                for(int i=0;i<5;i++) {
                    if (playernumber != 3) {
                        playernumber += 1;
                        if ((playernumber == 1)&(playerArr[playernumber].status=='i')) {
                            playerview2.setVisible(true);
                            break;
                        } else if ((playernumber == 2)&(playerArr[playernumber].status=='i')) {
                            playerview3.setVisible(true);
                            break;
                        } else if ((playernumber == 3)&(playerArr[playernumber].status=='i')) {
                            playerview4.setVisible(true);
                            break;
                        }
                    } else {
                        playernumber = 0;
                        if(playerArr[playernumber].status=='i'){
                            playerview1.setVisible(true);
                            break;
                        }
                    }
                    if(i==5){
                        System.out.println("There is no player left");
                    }
                }
                imageView1.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                imageView1.setY(startY + (assam.position.getY() - 1) * squareHeight);
                imageView2.setX(startX + (assam.position.getX()+1) * squareWidth); // 设置 X 坐标
                imageView2.setY(startY + (assam.position.getY()) * squareHeight);
                imageView3.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                imageView3.setY(startY + (assam.position.getY()+1) * squareHeight);
                imageView4.setX(startX + (assam.position.getX()-1) * squareWidth); // 设置 X 坐标
                imageView4.setY(startY + (assam.position.getY()) * squareHeight);
                switch (assam.direction) {
                    case 'N':
                        imageView1.setVisible(true);
                        imageView2.setVisible(true);
                        imageView4.setVisible(true);
                        break;
                    case 'E':
                        imageView1.setVisible(true);
                        imageView2.setVisible(true);
                        imageView3.setVisible(true);
                        break;
                    case 'S':
                        imageView2.setVisible(true);
                        imageView3.setVisible(true);
                        imageView4.setVisible(true);
                        break;
                    case 'W':
                        imageView1.setVisible(true);
                        imageView3.setVisible(true);
                        imageView4.setVisible(true);
                        break;

                }
            });
            highlightview8.setOnMouseClicked(event -> {//the second button W
                int id = playernumber * 15 + (15 - playerArr[playernumber].rugNumber);

                char playercolor = state.charAt(8 * (playernumber) + 1);
                dieimageview1.setVisible(false);
                dieimageview2.setVisible(false);
                dieimageview3.setVisible(false);
                dieimageview4.setVisible(false);
                highlightview1.setVisible(false);
                highlightview2.setVisible(false);
                highlightview3.setVisible(false);
                highlightview4.setVisible(false);
                highlightview5.setVisible(false);
                highlightview6.setVisible(false);
                highlightview7.setVisible(false);
                highlightview8.setVisible(false);
                clickbutton[1] = 3;
                String idString = new String();
                if (id < 10) {
                    idString = '0' + String.valueOf(id);
                } else {
                    idString = String.valueOf(id);
                }
                String rugstring = playercolor + idString + String.valueOf(assam.position.getX() + xarray[clickbutton[0]])
                        + String.valueOf(assam.position.getY() + yarray[clickbutton[0]])
                        + String.valueOf(assam.position.getX() + xarray[clickbutton[0]] + xarray[clickbutton[1]])
                        + String.valueOf(assam.position.getY() + yarray[clickbutton[0]] + yarray[clickbutton[1]]);
//                System.out.println("rug to place"+rugstring);
                state = makePlacement(state, rugstring);
                for (int i=0;i<4;i++){
                    playerArr[i].rugNumber=Integer.parseInt(String.valueOf(state.charAt(8*i+5)))*10
                            +Integer.parseInt(String.valueOf(state.charAt(8*i+6)));
                }
                displayState(state);
                stage.setScene(scene);
                stage.show();
//                System.out.println("end:" + state);
                playerview1.setVisible(false);
                playerview2.setVisible(false);
                playerview3.setVisible(false);
                playerview4.setVisible(false);
                for(int i=0;i<5;i++) {
                    if (playernumber != 3) {
                        playernumber += 1;
                        if ((playernumber == 1)&(playerArr[playernumber].status=='i')) {
                            playerview2.setVisible(true);
                            break;
                        } else if ((playernumber == 2)&(playerArr[playernumber].status=='i')) {
                            playerview3.setVisible(true);
                            break;
                        } else if ((playernumber == 3)&(playerArr[playernumber].status=='i')) {
                            playerview4.setVisible(true);
                            break;
                        }
                    } else {
                        playernumber = 0;
                        if(playerArr[playernumber].status=='i'){
                            playerview1.setVisible(true);
                            break;
                        }
                    }
                    if(i==5){
                        System.out.println("There is no player left");
                    }
                }
                imageView1.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                imageView1.setY(startY + (assam.position.getY() - 1) * squareHeight);
                imageView2.setX(startX + (assam.position.getX()+1) * squareWidth); // 设置 X 坐标
                imageView2.setY(startY + (assam.position.getY()) * squareHeight);
                imageView3.setX(startX + (assam.position.getX()) * squareWidth); // 设置 X 坐标
                imageView3.setY(startY + (assam.position.getY()+1) * squareHeight);
                imageView4.setX(startX + (assam.position.getX()-1) * squareWidth); // 设置 X 坐标
                imageView4.setY(startY + (assam.position.getY()) * squareHeight);
                switch (assam.direction) {
                    case 'N':
                        imageView1.setVisible(true);
                        imageView2.setVisible(true);
                        imageView4.setVisible(true);
                        break;
                    case 'E':
                        imageView1.setVisible(true);
                        imageView2.setVisible(true);
                        imageView3.setVisible(true);
                        break;
                    case 'S':
                        imageView2.setVisible(true);
                        imageView3.setVisible(true);
                        imageView4.setVisible(true);
                        break;
                    case 'W':
                        imageView1.setVisible(true);
                        imageView3.setVisible(true);
                        imageView4.setVisible(true);
                        break;

                }
            });
                root.getChildren().add(pressview);
                stage.setScene(scene);
                stage.show();



        }


}

