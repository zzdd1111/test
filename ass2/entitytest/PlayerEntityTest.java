package comp1110.ass2.entitytest;

//import comp1110.ass2.entity.CoordinateEntity;

import comp1110.ass2.entity.PlayerEntity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerEntityTest {
    private PlayerEntity playerEntity;

    @Before
    public void setUp() {
        playerEntity = new PlayerEntity();
    }

    @Test
    public void testDecode() {
        String representation = "Pr00803i";
        playerEntity.decode(representation);

        // Add assertions to check if the decode method works correctly
        // For example:
        assertEquals('r', playerEntity.color);
        assertEquals(8,playerEntity.dirhams);
        assertEquals(3,playerEntity.rugNumber);
        assertEquals('i',playerEntity.status );
        // Add more assertions as needed
    }

    @Test
    public void testEncode() {
        // Set up the assamEntity with some values
        playerEntity.color='y';
        playerEntity.dirhams=11;
        playerEntity.rugNumber=5;
        playerEntity.status='i';
        // Encode the assamEntity and check if the result matches the expected representation
        String expectedRepresentation = "Py01105i";
        assertEquals(expectedRepresentation, playerEntity.encode());
    }
}
