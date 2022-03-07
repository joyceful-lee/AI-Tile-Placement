import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcTest {

    @Test
    void arcCreate() {
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
        Arc arc = new Arc(n2, n1);
        
        assertEquals(arc, Arc.arcCreate(n2));
    }
}