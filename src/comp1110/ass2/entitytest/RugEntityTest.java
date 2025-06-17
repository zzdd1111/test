package comp1110.ass2.entitytest;

import comp1110.ass2.entity.CoordinateEntity;
import comp1110.ass2.entity.RugEntity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RugEntityTest {
    private RugEntity rugEntity;

    @Before
    public void setUp() {
        rugEntity = new RugEntity();
    }

    @Test
    public void testDecode() {
        String representation = "p223334";
        rugEntity.decode(representation);

        // Add assertions to check if the decode method works correctly
        // For example:
        assertEquals('p', rugEntity.color);
        assertEquals(22, rugEntity.id);
        assertEquals(3,rugEntity.coordinate1.getX() );
        assertEquals(3,rugEntity.coordinate1.getY() );
        assertEquals(3,rugEntity.coordinate2.getX() );
        assertEquals(4,rugEntity.coordinate2.getY() );
        // Add more assertions as needed
    }

    @Test
    public void testEncode() {
        // Set up the assamEntity with some values
        rugEntity.coordinate1= new CoordinateEntity(2,3);
        rugEntity.coordinate2= new CoordinateEntity(1,3);
        rugEntity.color = 'r';
        rugEntity.id= 32;
        // Encode the assamEntity and check if the result matches the expected representation
        String expectedRepresentation = "r322313";
        assertEquals(expectedRepresentation, rugEntity.encode());
    }
}
