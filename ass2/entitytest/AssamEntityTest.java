package comp1110.ass2.entitytest;

import comp1110.ass2.entity.AssamEntity;
import comp1110.ass2.entity.CoordinateEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AssamEntityTest {
    private AssamEntity assamEntity;

    @Before
    public void setUp() {
        assamEntity = new AssamEntity();
    }

    @Test
    public void testDecode() {
        String representation = "A235N";
        assamEntity.decode(representation);

        // Add assertions to check if the decode method works correctly
        assertEquals(2, assamEntity.position.getX());
        assertEquals(3, assamEntity.position.getY());
        assertEquals('N', assamEntity.direction);
    }

    @Test
    public void testEncode() {
        // Set up the assamEntity with some values
        assamEntity.position = new CoordinateEntity(4, 1);
        assamEntity.direction = 'S';

        // Encode the assamEntity and check if the result matches the expected representation
        String expectedRepresentation = "A41S";
        assertEquals(expectedRepresentation, assamEntity.encode());
    }
}
