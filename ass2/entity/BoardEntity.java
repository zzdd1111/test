package comp1110.ass2.entity;

import java.util.ArrayList;

public class BoardEntity extends Entity{

    AssamEntity assam;

    public RugEntity[][] ruglist = new RugEntity[7][7];


    @Override
    public void decode(String representation) {
        int indexOfB = representation.indexOf("B");
        String boardString = representation.substring(indexOfB + 1);
        for (int i = 0;i<7;i++) {
            String boardRowString = boardString.substring(i*21,21+i*21);// cut off a 21-bit string each time as a row

            for (int j = 0 ;j<7;j++){
                var rug = new RugEntity();
                rug.color = boardRowString.charAt(j*3);
                rug.id = Integer.parseInt(boardRowString.substring(1+j*3,3+j*3));//i=0 substring(1,3);i=1 substring(4,6)
                this.ruglist[i][j] = rug;
                //i = i + 3;
            }
        }
    }

    @Override
    public String encode() {
        String res = "B";
        for (int i = 0;i < 7;i++){
            for (int j = 0 ;j<7;j++){
                if (ruglist[i][j].id < 10){
                    res = res + ruglist[i][j].color + "0" + ruglist[i][j].id;
                }
                else {
                    res = res + ruglist[i][j].color + ruglist[i][j].id;
                }
            }
        }
        return res;
    }
}
