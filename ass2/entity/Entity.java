package comp1110.ass2.entity;

public abstract class Entity {

    /**
     *
     * @return
     */
    abstract void decode(String representation);

    abstract String encode();
}
