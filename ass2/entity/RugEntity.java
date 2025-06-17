package comp1110.ass2.entity;

public class RugEntity extends Entity{
//    String rugstring;
//    public void RugEntity(String rugstring){
//        this.rugstring=rugstring;
//    }
    public char color;
    public int id;
    public boolean isplaced;
//    public CoordinateEntity[] coordinate = new CoordinateEntity[2];
    public CoordinateEntity coordinate1 ;
    public  CoordinateEntity coordinate2;




    @Override
    public void decode(String representation) {
        this.color = representation.charAt(0);
//        this.id = Integer.parseInt(String.valueOf(representation.charAt(1) + representation.charAt(2)));
        this.id=Integer.parseInt(String.valueOf(representation.charAt(2)))+Integer.parseInt(String.valueOf(representation.charAt(1)))*10;
        this.coordinate1=new CoordinateEntity(Integer.parseInt(String.valueOf(representation.charAt(3))),Integer.parseInt(String.valueOf(representation.charAt(4))));
        this.coordinate2=new CoordinateEntity(Integer.parseInt(String.valueOf(representation.charAt(5))),Integer.parseInt(String.valueOf(representation.charAt(6))));
//        this.coordinate1.setX(Integer.parseInt(String.valueOf(representation.charAt(3))));
//        this.coordinate1.setY(Integer.parseInt(String.valueOf(representation.charAt(4))));
//        this.coordinate2.setX(Integer.parseInt(String.valueOf(representation.charAt(5))));
//        this.coordinate2.setY(Integer.parseInt(String.valueOf(representation.charAt(6))));
//        this.coordinate[0].y = Integer.parseInt(String.valueOf(representation.charAt(4)));
//        this.coordinate[1].x = Integer.parseInt(String.valueOf(representation.charAt(5)));
//        this.coordinate[1].y = Integer.parseInt(String.valueOf(representation.charAt(6)));
    }

    @Override
    public String encode() {
        if(this.id<10){
            return this.color + '0'+String.valueOf(this.id) + Integer.toString(this.coordinate1.getX())
                    + Integer.toString(this.coordinate1.getY())
                    + Integer.toString(this.coordinate2.getX())
                    + Integer.toString(this.coordinate2.getY());
        }
        else{
            return this.color + String.valueOf(this.id)+ Integer.toString(this.coordinate1.getX())
                    + Integer.toString(this.coordinate1.getY())
                    + Integer.toString(this.coordinate2.getX())
                    + Integer.toString(this.coordinate2.getY());
        }
    }
//    public char getrugcolor(){
//        char color=rugstring.charAt(0);
//        return color;
//    }
//    public int getcoordinate(){
//        char coordinatex=rugstring.charAt(3);
//        int coordinatex1 = Integer.parseInt(String.valueOf(coordinatex));
////        int coordinatex1=rugstring.charAt(3);
//    }
}
