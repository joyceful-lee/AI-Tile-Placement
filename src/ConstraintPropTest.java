import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConstraintPropTest {

    @Test
    void arcConsistency() {
        String val = "12345678";
        String layout = "11111111";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layout.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layout.toCharArray(), "FULL")
        };
        int[] targetNumTest = new int[]{1,0,0,1};
        int[] tileCountTest = new int[]{1,0,0};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        Node n2 = new Node(tilesTest, targetNumTest, tileCountTest);
        n2.parent = n1;

        assertEquals(false, ConstraintProp.arcConsistency(n2));
    }
}