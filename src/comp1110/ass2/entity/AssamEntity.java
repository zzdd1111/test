package comp1110.ass2.entity;

public class AssamEntity extends Entity{

    public char direction;
    public CoordinateEntity position;

    @Override
    public void decode(String representation) {
        int target_x = Integer.parseInt(String.valueOf(representation.charAt(1)));
        int target_y = Integer.parseInt(String.valueOf(representation.charAt(2)));
        this.position = new CoordinateEntity(target_x,target_y);
        this.direction = representation.charAt(3);
    }

    @Override
    public String encode() {
        return "A" + position.getX() + position.getY() + this.direction;
    }
}
