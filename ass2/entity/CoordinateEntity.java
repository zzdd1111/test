package comp1110.ass2.entity;

public class CoordinateEntity extends Entity{
    int x;
    int y;

    public  CoordinateEntity(int x, int y){

        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    public void setX(int x){
        this.x=x;
    }
    public  void setY(int y){
        this.y=y;
    }

    @Override
    void decode(String representation) {

    }

    @Override
    String encode() {
        return null;
    }
}
