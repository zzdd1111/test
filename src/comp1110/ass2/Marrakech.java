package comp1110.ass2;

import comp1110.ass2.entity.AssamEntity;

import java.util.Random;

public class Marrakech {

    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     *  - The String is 7 characters long
     *  - The first character in the String corresponds to the colour character of a player present in the game
     *  - The next two characters represent a 2-digit ID number
     *  - The next 4 characters represent coordinates that are on the board
     *  - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     *  - c023343 (Shares the colour but not the ID)
     *  - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     * @param gameString A String representing the current state of the game as per the README
     * @param rug A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameString, String rug) {
        // FIXME: Task 4
        if (rug.length() != 7)
            return false;
        char character = rug.charAt(0);
        if (character != 'c' && character != 'y' && character != 'r' && character != 'p')
            return false;
        char num1 = rug.charAt(1);
        char num2 = rug.charAt(2);
        if (!(num1 >= '0' && num1 <= '9') || !(num2 >= '0' && num2 <= '9'))
            return false;
        char coordinate1 = rug.charAt(3);
        char coordinate2 = rug.charAt(4);
        char coordinate3 = rug.charAt(5);
        char coordinate4 = rug.charAt(6);
        if (!(coordinate1 >= '0' && coordinate1 <= '6') || !(coordinate2 >= '0' && coordinate2 <= '6') ||
                !(coordinate3 >= '0' && coordinate3 <= '6') || !(coordinate4 >= '0' && coordinate4 <= '6'))
            return false;
        int indexOfB = gameString.indexOf("B");
        String boardString = gameString.substring(indexOfB + 1);
        for (int i = 0;i < 147;i = i + 3){
            char boardchar1 = boardString.charAt(i);
            char boardchar2 = boardString.charAt(i + 1);
            char boardchar3 = boardString.charAt(i + 2);
            if ((character == boardchar1) && (num1 == boardchar2) && (num2 == boardchar3)) {
                return false;
            }
        }


        return true;
    }

    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     *  - One shows 1
     *  - Two show 2
     *  - Two show 3
     *  - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     * @return The result of the roll of the die meeting the criteria above
     */
    public static int rollDie() {
        // FIXME: Task 6
        Random random = new Random();
        int num = random.nextInt(6) + 1;
        switch (num){
            case 1: return 1;
            case 2, 3: return 2;
            case 4, 5: return 3;
            case 6: return 4;
        }
        return -1;
    }

    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {
        // FIXME: Task 8
        for(int i=0;i<4;i++) {
            char Pcheck = currentGame.charAt(8 * i);
            if (Pcheck != 'P') {
                break;
            }
            char oicheck=currentGame.charAt(8*i+7);
            if (oicheck=='o'){
                continue;
            }
            char num1=currentGame.charAt(8*i+5);
            char num2=currentGame.charAt(8*i+6);
            if(num1=='0'&&num2=='0'){
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     * @param currentAssam A String representing Assam's current state
     * @param rotation The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                 is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                 current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        // FIXME: Task 9
        String Assam="A";
        char position1=currentAssam.charAt(1);
        char position2=currentAssam.charAt(2);
        Assam+=position1;
        Assam+=position2;
        char[] direction = {'W','N','E','S','W','N'};
        int i=0;
        char Assamdir=currentAssam.charAt(3);
        switch (Assamdir){
            case 'N':
                i=1;
                break;
            case 'E':
                i=2;
                break;
            case 'S':
                i=3;
                break;
            case 'W':
                i=4;
                break;
            default:
                return currentAssam;
        }
        switch (rotation){
            case 90:
                i+=1;
                break;
            case 0:
                i=i;
                break;
            case 270:
                i-=1;
                break;
            default:
                return currentAssam;
        }
        char Assamdir1=direction[i];
        Assam+=Assamdir1;
        return Assam;
    }

    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     *   1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     *   2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     *      the new rug must not cover the entirety of another rug that's already on the board.
     * @param gameState A game string representing the current state of the game
     * @param rug A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rug) {
        // FIXME: Task 10
//        if(rug.length()!=7){
//            return false;
//        }
        char Assam=gameState.charAt(32+1);
        int Assam1 = Integer.parseInt(String.valueOf(Assam));
        Assam=gameState.charAt(32+2);
        int Assam2 = Integer.parseInt(String.valueOf(Assam));
//        int rugid1=Integer.parseInt(String.valueOf(rug.charAt(1)));
//        int rugid2=Integer.parseInt(String.valueOf(rug.charAt(2)));
        char rugpos=rug.charAt(3+0);
        int rug1 = Integer.parseInt(String.valueOf(rugpos));
        if(rug1>6){
            return false;
        }
        rugpos=rug.charAt(3+1);
        int rug2 = Integer.parseInt(String.valueOf(rugpos));
        rugpos=rug.charAt(3+2);
        if(rug2>6){
            return false;
        }
        int rug3 = Integer.parseInt(String.valueOf(rugpos));
        rugpos=rug.charAt(3+3);
        if(rug3>6){
            return false;
        }
        int rug4 = Integer.parseInt(String.valueOf(rugpos));
        boolean condition1=false;
        if(rug4>6){
            return false;
        }
        if((Assam1==rug1)&(Assam2==rug2-1|Assam2==rug2+1)){
            condition1=true;
        }
        else if((Assam2==rug2)&(Assam1==rug1-1|Assam1==rug1+1)){
            condition1=true;
        }
        else if((Assam1==rug3)&(Assam2==rug4-1|Assam2==rug4+1)){
            condition1=true;
        }
        else if((Assam2==rug4)&(Assam1==rug3-1|Assam1==rug3+1)){
            condition1=true;
        }
        if(condition1==false){
            return false;
        }
        if((Assam1==rug1)&&(Assam2==rug2)){
            return false;
        }else if((Assam1==rug3)&&(Assam2==rug4)){
            return false;
        }
        char place1P=gameState.charAt(32+4+1+rug1*21+rug2*3);
        char place11=gameState.charAt(32+4+1+rug1*21+rug2*3+1);
        char place12=gameState.charAt(32+4+1+rug1*21+rug2*3+2);
        char place2P=gameState.charAt(32+4+1+rug3*21+rug4*3);
        char place21=gameState.charAt(32+4+1+rug3*21+rug4*3+1);
        char place22=gameState.charAt(32+4+1+rug3*21+rug4*3+2);
        if(place1P=='n'|place2P=='n'){
            return true;
        }
        if((place1P==place2P)&&(place11==place21)&&(place12==place22)){
            return false;
        }
        return true;
    }

    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {
        // FIXME: Task 11
        char Assampos=gameString.charAt(32+1);
        int Assampos1=Integer.parseInt(String.valueOf(Assampos));
        Assampos=gameString.charAt(32+2);
        int Assampos2=Integer.parseInt(String.valueOf(Assampos));
        char color = gameString.charAt(32+4+1+Assampos1*21+Assampos2*3);
        if(color=='n'){
            return 0;
        }
        boolean[][] colorcheck=new boolean[7][7];
        for(int i=0;i<7;i++){
            for (int j=0;j<7;j++){
                colorcheck[i][j]=false;
            }
        }
        colorcheck[Assampos1][Assampos2]=true;
        boolean paymentcheck=true;
        while(paymentcheck) {
            paymentcheck = false;
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j < 7; j++) {
                    if (!colorcheck[i][j]) {
                        continue;
                    } else {
                        if ((i != 0) & (i != 6)) {
                            char colorx1 = gameString.charAt(32 + 4 + 1 + (i - 1) * 21 + j * 3);
                            char colorx2 = gameString.charAt(32 + 4 + 1 + (i + 1) * 21 + j * 3);
                            if (colorx1 == color) {
                                if (!colorcheck[i - 1][j]) {
                                    paymentcheck = true;
                                }

                                colorcheck[i - 1][j] = true;
                            }
                            if (colorx2 == color) {
                                if (!colorcheck[i + 1][j]) {
                                    paymentcheck = true;
                                }

                                colorcheck[i + 1][j] = true;
                            }
                        } else if (i == 0) {
                            char colorx3 = gameString.charAt(32 + 4 + 1 + (i + 1) * 21 + j * 3);
                            if (colorx3 == color) {
                                if (!colorcheck[i + 1][j]) {
                                    paymentcheck = true;
                                }

                                colorcheck[i + 1][j] = true;
                            }
                        } else if (i == 6) {
                            char colorx4 = gameString.charAt(32 + 4 + 1 + (i - 1) * 21 + j * 3);
                            if (colorx4 == color) {
                                if (!colorcheck[i - 1][j]) {
                                    paymentcheck = true;
                                }

                                colorcheck[i - 1][j] = true;
                            }
                        }
                        if ((j != 0) & (j != 6)) {
                            char colorx5 = gameString.charAt(32 + 4 + 1 + (i) * 21 + (j - 1) * 3);
                            char colorx6 = gameString.charAt(32 + 4 + 1 + (i) * 21 + (j + 1) * 3);
                            if (colorx5 == color) {
                                if (!colorcheck[i][j - 1]) {
                                    paymentcheck = true;
                                }

                                colorcheck[i][j - 1] = true;
                            }
                            if (colorx6 == color) {
                                if (!colorcheck[i][j + 1]) {
                                    paymentcheck = true;
                                }

                                colorcheck[i][j + 1] = true;
                            }
                        } else if (j == 0) {
                            char colorx7 = gameString.charAt(32 + 4 + 1 + (i) * 21 + (j + 1) * 3);
                            if (colorx7 == color) {
                                if (!colorcheck[i][j + 1]) {
                                    paymentcheck = true;
                                }

                                colorcheck[i][j + 1] = true;
                            }
                        } else if (j == 6) {
                            char colorx8 = gameString.charAt(32 + 4 + 1 + (i) * 21 + (j - 1) * 3);
                            if (colorx8 == color) {
                                if (!colorcheck[i][j - 1]) {
                                    paymentcheck = true;
                                }

                                colorcheck[i][j - 1] = true;
                            }
                        }

                    }
                }
            }
        }
        int paymentamount =0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                char color1=gameString.charAt(32+5+i*21+j*3);
                if(colorcheck[i][j]){
                    paymentamount+=1;
                }
            }
            }
        for(int i=0;i<4;i++){
            char playercolor=gameString.charAt(8*i+1);
            char playerstate=gameString.charAt(8*i+7);
            if((playercolor==color)&(playerstate=='o')){
                return 0;
            }
        }
        return paymentamount;
    }

    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        // FIXME: Task 12
        if(!isGameOver(gameState)){
            return 'n';
        }
        char color[]=new char[4];
        int dirhams[]=new int[4];
        int rugnumber[]=new int [4];
        int score[]= new int[4];
        boolean playerstate[]=new boolean[4];
        for (int i=0;i<4;i++){
            color[i]=gameState.charAt(8*i+1);
            dirhams[i]=Integer.parseInt(gameState.substring(8*i+2,8*i+5));
            char iocheck=gameState.charAt(8*i+7);
            if(iocheck=='i'){
                playerstate[i]=true;
            }else {
                playerstate[i]=false;
            }
        }
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                char colorowner=gameState.charAt(32+4+1+i*21+j*3);
                for(int k=0;k<4;k++){
                    if(colorowner==color[k]){
                        rugnumber[k]+=1;
                    }
                }

            }
            }
        for (int i = 0; i < 4; i++){
            score[i]=dirhams[i]+rugnumber[i];
        }
        for (int i=0;i<4;i++){
            System.out.println((i+1)+":"+score[i]);
        }
        char winnercolor='n';
        int winnerid=0;
        int maxscore=0;
        boolean tiecheck=false;
        for (int i = 0; i < 4; i++){
            if(playerstate[i]){
                if(score[i]>maxscore){
                    winnerid=i;
                    maxscore=score[i];
                    winnercolor=color[i];
                    tiecheck=false;
                }else if(maxscore==score[i]){
                    if(dirhams[i]>dirhams[winnerid]){
                        winnerid=i;
                        maxscore=score[i];
                        winnercolor=color[i];
                        tiecheck=false;
                    }else if(rugnumber[winnerid]==rugnumber[i]){
                        tiecheck=true;
                    }
//                    tiecheck=true;
                }
            }
        }
        if(tiecheck){
            return 't';
        }
        return winnercolor;
    }

    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult){
        // FIXME: Task 13
        int coordinatex=Integer.parseInt(String.valueOf(currentAssam.charAt(1)));
        int coordinatey=Integer.parseInt(String.valueOf(currentAssam.charAt(2)));
        char direction=currentAssam.charAt(3);
        while (dieResult!=0){
            if(coordinatex>6||coordinatex<0||coordinatey>6||coordinatey<0){
                System.out.println("coordinate wrong");
            }
            switch (direction){

                case 'N':
                    if(coordinatey!=0){
                        coordinatey-=1;
                    }else if(coordinatex==1||coordinatex==3||coordinatex==5){
                        coordinatex-=1;
                        direction='S';
                    }else if(coordinatex==0||coordinatex==2||coordinatex==4){
                        coordinatex+=1;
                        direction='S';
                    }
                    else if(coordinatex==6){
//                        coordinatex-=1; //the right way
                        direction='W';
                    }else {
                        System.out.println("something goes wrong");
                    }
                    break;
                case 'E':
                    if(coordinatex!=6){
                        coordinatex+=1;
                    }else if(coordinatey==1||coordinatey==3||coordinatey==5){
                        coordinatey+=1;
                        direction='W';
                    }else if(coordinatey==2||coordinatey==4||coordinatey==6){
                        coordinatey-=1;
                        direction='W';
                    }
                    else if(coordinatey==0){
//                        coordinatey+=1;//the right way
                        direction='S';
                    }else {
                        System.out.println("something goes wrong");
                    }
                    break;
                case 'S':
                    if(coordinatey!=6){
                        coordinatey+=1;
                    }else if(coordinatex==1||coordinatex==3||coordinatex==5){
                        coordinatex+=1;
                        direction='N';
                    }else if(coordinatex==2||coordinatex==4||coordinatex==6){
                        coordinatex-=1;
                        direction='N';
                    }
                    else if(coordinatex==0){
//                        coordinatex+=1;//the right way
                        direction='E';
                    }else {
                        System.out.println("something goes wrong");
                    }
                    break;
                case 'W':
                    if(coordinatex!=0){
                        coordinatex-=1;
                    }else if(coordinatey==1||coordinatey==3||coordinatey==5){
                        coordinatey-=1;
                        direction='E';
                    }else if(coordinatey==0||coordinatey==2||coordinatey==4){
                        coordinatey+=1;
                        direction='E';
                    }
                    else if(coordinatey==6){
//                        coordinatey-=1;//the right way
                        direction='N';
                    }else {
                        System.out.println("something goes wrong");
                    }
                    break;
                default:
                    System.out.println("wrong");
            }
            dieResult-=1;
        }
        String assam= "A"+coordinatex+coordinatey+direction;
        return assam;
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     * @param currentGame A String representation of the current state of the game.
     * @param rug A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rug) {
        // FIXME: Task 14
        if(isPlacementValid(currentGame,rug)){
            char colorchar[]={'c','y','p','r'};
            char color=rug.charAt(0);
            char colorid1=rug.charAt(1);
            char colorid2=rug.charAt(2);
            int colorid=Integer.parseInt(String.valueOf(rug.charAt(1)))*10+Integer.parseInt(String.valueOf(rug.charAt(2)));
            for (int i=0;i<7;i++){
                for (int j=0;j<7;j++){
                    if(currentGame.charAt(32+4+1+i*21+j*3)==color){
                        if(currentGame.charAt(32+4+1+i*21+j*3+1)==colorid1){
                            if(currentGame.charAt(32+4+1+i*21+j*3+2)==colorid2){
                                return currentGame;
                            }
                        }
                    }
                }
            }
            int coordinate1=Integer.parseInt(String.valueOf(rug.charAt(3)));
            int coordinate2=Integer.parseInt(String.valueOf(rug.charAt(4)));
            int coordinate3=Integer.parseInt(String.valueOf(rug.charAt(5)));
            int coordinate4=Integer.parseInt(String.valueOf(rug.charAt(6)));
            String rugtoreplace=""+color+colorid1+colorid2;
//            System.out.println(rugtoreplace);
//            StringBuilder state=new StringBuilder(currentGame);
//            state.replace(32+4+1+coordinate1*21+coordinate2*3,32+4+1+coordinate1*21+coordinate2*3+3,rugtoreplace);
//            state.replace(32+4+1+coordinate3*21+coordinate4*3,32+4+1+coordinate3*21+coordinate4*3+3,rugtoreplace);
            String resultstate=currentGame;
            resultstate = resultstate.substring(0, 32 + 4+1+coordinate1*21+coordinate2*3) + rugtoreplace + resultstate.substring(32 + 4+1+coordinate1*21+coordinate2*3+3);
            resultstate = resultstate.substring(0, 32 + 4+1+coordinate3*21+coordinate4*3) + rugtoreplace + resultstate.substring(32 + 4+1+coordinate3*21+coordinate4*3+3);
            char idcolor[]=new char[4];
            for (int i=0;i<4;i++){
                idcolor[i]=currentGame.charAt(8*i+1);
            }
            for (int i=0;i<4;i++){
                if(idcolor[i]==color){
                    int rugnumber1=Integer.parseInt(String.valueOf(currentGame.charAt(8*i+5)));
                    int rugnumber2=Integer.parseInt(String.valueOf(currentGame.charAt(8*i+6)));
                    if(rugnumber2!=0){
                        rugnumber2-=1;
                    }else if((rugnumber2==0)&&(rugnumber1==1)){
                        rugnumber1=0;
                        rugnumber2=9;
                    }else {
                        System.out.println("there is no rug left");
                    }

//                    String rugnumber=String.valueOf(rugnumber1)+String.valueOf(rugnumber2);
                    resultstate=resultstate.substring(0, 8*i+5) + rugnumber1 + resultstate.substring(8*i+6);
                    resultstate=resultstate.substring(0, 8*i+6) + rugnumber2 + resultstate.substring(8*i+7);
                    break;
                }
            }
            return resultstate;
        }else {
            return currentGame;
        }
    }

}
