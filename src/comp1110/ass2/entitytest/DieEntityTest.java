package comp1110.ass2.entitytest;

import static org.junit.Assert.*;

import comp1110.ass2.entity.DieEntity;
import org.junit.Test;

public class DieEntityTest {

    @Test
    public void testRollDice() {

        int result = DieEntity.rollDice();
        assertTrue(result >= 1 && result <= 6);
    }
}
