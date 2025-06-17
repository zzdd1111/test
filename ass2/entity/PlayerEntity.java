package comp1110.ass2.entity;

import comp1110.ass2.common.*;

public class PlayerEntity extends Entity{

    public int dirhams;
    public int rugNumber;
    public char color;
    public char status;


    Common common = new Common();

    public  PlayerEntity(){

    }
    public PlayerEntity(int dirhams,int rugNumber,char color,char status)
    {
        this.dirhams = dirhams;
        this.rugNumber = rugNumber;
        this.color = color;
        this.status = status;
    }

    /**
     *
     * @param representation e.g. "Pr00803i"
     */
    @Override
     public void decode(String representation) {
        if(representation == null || representation.length() != 8)
            return;

        this.color = representation.charAt(1);
        this.dirhams = Integer.parseInt(representation.substring(2,5));
        this.rugNumber = Integer.parseInt(representation.substring(5,7));
        this.status = representation.charAt(7);

    }

    @Override
    public String encode() {
        return "P" + this.color + common.ConvertDigit(this.dirhams,3) + common.ConvertDigit(this.rugNumber,2) + this.status;
    }

//    public static void main(String[] args) {
//
//    }

}
