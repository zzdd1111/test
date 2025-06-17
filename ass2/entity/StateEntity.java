package comp1110.ass2.entity;

public class StateEntity extends Entity{

    public PlayerEntity[] playerArr = new PlayerEntity[4];

    public AssamEntity assam = new AssamEntity();
    public BoardEntity board = new BoardEntity();

    /**
     * Pc03802iPy00003oPp00003oPr08203iA50SBp20r17r14r02r02c09n00r20p23r14y23y23c18n00r20p23n00c17c23r22n00r15y17c19c19y06r19r19r21p11p16y07c05n00r06r21c14p16c25y25p22p22n00y13y13p10y25c10r08
     */
    public
    @Override
    void decode(String representation) {

        if(representation.length()== 0 )
            return;

        String playerStr = representation.substring(0,4*8);
        String assemStr = representation.substring(4*8,4*8+4);
        String boardStr = representation.substring(4*8+4);

        //decode player
        playerArr[0] = new PlayerEntity();
        playerArr[1] = new PlayerEntity();
        playerArr[2] = new PlayerEntity();
        playerArr[3] = new PlayerEntity();
        playerArr[0].decode(playerStr.substring(0,8));
        playerArr[1].decode(playerStr.substring(8,16));
        playerArr[2].decode(playerStr.substring(16,24));
        playerArr[3].decode(playerStr.substring(24));


        //decode assem
        assam.decode(assemStr);

        //decode board
        board.decode(boardStr);

    }

//    public static void main(String[] args) {
//
//        StateEntity a  =new StateEntity();
//        a.decode("Pc03802iPy00003oPp00003oPr08203iA50SBp20r17r14r02r02c09n00r20p23r14y23y23c18n00r20p23n00c17c23r22n00r15y17c19c19y06r19r19r21p11p16y07c05n00r06r21c14p16c25y25p22p22n00y13y13p10y25c10r08");
//
//    }

    @Override
    String encode() {
        return null;
    }
}
