package comp1110.ass2.entitytest;

import comp1110.ass2.entity.BoardEntity;
import comp1110.ass2.entity.RugEntity;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BoardEntityTest {

    private BoardEntity boardEntity;

    @Before
    public void setUp() {
        boardEntity = new BoardEntity();
    }

    @Test
    public void testDecode() {
        String representation = "Bp03c11y14c08y12r01y07r05p09r06p00c02p15c04y10c15y13r07y03r09p11r12p01c06p14c08y05c00y02r11y06r13p07r04p10c09p12c13y00c07y08r14y01r02p04r03p05c10p08";
        boardEntity.decode(representation);

        // Add assertions to check if the decode method works correctly
        assertEquals('p', boardEntity.ruglist[0][0].color);
        assertEquals(3, boardEntity.ruglist[0][0].id);
        assertEquals('c', boardEntity.ruglist[0][1].color);
        assertEquals(11, boardEntity.ruglist[0][1].id);
        // Add more assertions as needed
    }

    @Test
    public void testEncode() {
        // Set up the boardEntity with some rug values
        String representation = "Bp03c11y14c08y12r01y07r05p09r06p00c02p15c04y10c15y13r07y03r09p11r12p01c06p14c08y05c00y02r11y06r13p07r04p10c09p12c13y00c07y08r14y01r02p04r03p05c10p08";
        boardEntity.decode(representation);

        // Encode the boardEntity and check if the result matches the expected representation
        String expectedRepresentation = "Bp03c11y14c08y12r01y07r05p09r06p00c02p15c04y10c15y13r07y03r09p11r12p01c06p14c08y05c00y02r11y06r13p07r04p10c09p12c13y00c07y08r14y01r02p04r03p05c10p08";
        assertEquals(expectedRepresentation, boardEntity.encode());
    }
}
