import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void layoutNumberCalc() {
        String val = "12345678";
        String layout = "11111111";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layout.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layout.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layout.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layout.toCharArray(), "EL 1")
        };
        int[] targetNumTest = new int[]{1,0,0,1};
        int[] tileCountTest = new int[]{1,0,0};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        int[] numberResult = new int[] {1,2,1};
        assertArrayEquals(numberResult, n1.currentLayoutCount);
    }

    @Test
    void targetNumberCalc() {
        String val = "1223334444123412";
        String layoutF = "1111111111111111",
                layoutO = "1111100110011111",
                layoutE = "1111100010001000";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutO.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        int[] targetNumTest = new int[]{1,0,0,1};
        int[] tileCountTest = new int[]{1,0,0};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        int[] numberResult = new int[] {5,4,3,10};
        assertArrayEquals(numberResult, n1.targetNumberCalc());
    }

    @Test
    void finalCheck() {
        String val = "1223334444123412";
        String layoutF = "1111111111111111",
                layoutO = "1111100110011111",
                layoutE = "1111100010001000";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutO.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        int[] targetNumTest = new int[]{1,0,0,1};
        int[] tileCountTest = new int[]{1,0,0};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        assertEquals(false,n1.finalCheck());
        
    }

    @Test
    void layoutCheck() {
        String val = "1223334444123412";
        String layoutF = "1111111111111111",
                layoutO = "1111100110011111",
                layoutE = "1111100010001000";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutO.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        int[] targetNumTest = new int[]{1,0,0,1};
        int[] tileCountTest = new int[]{1,2,1};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        assertEquals(true, n1.layoutCheck());
    }

    @Test
    void targetCheck() {
        String val = "1223334444123412";
        String layoutF = "1111111111111111",
                layoutO = "1111100110011111",
                layoutE = "1111100010001000";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutO.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        int[] targetNumTest = new int[]{12,12,12,12};
        int[] tileCountTest = new int[]{1,2,1};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        assertEquals(true, n1.targetCheck());
        int[] targetNumTestFail = new int[]{1,1,1,1};
        Node n2 = new Node(tilesTest, targetNumTestFail, tileCountTest);
        assertEquals(false, n2.targetCheck());
    }

    @Test
    void changeLayout() {
        String val = "1223334444123412";
        String layoutF = "1111111111111111",
                layoutO = "1111100110011111",
                layoutE = "1111100010001000";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutO.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        int[] targetNumTest = new int[]{12,12,12,12};
        int[] tileCountTest = new int[]{1,2,1};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        assertEquals(true, n1.changeLayout(0,0,0));
    }

    @Test
    void tileOrder() {
        String val = "1223334444123412";
        String layoutF = "1111111111111111",
                layoutO = "1111100110011111",
                layoutE = "1111100010001000";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutO.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        int[] targetNumTest = new int[]{12,12,12,12};
        int[] tileCountTest = new int[]{1,2,1};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        int[][] orderTest = {
                {3,15},
                {2,15},
                {1,15},
                {0,15}
        };
        assertArrayEquals(orderTest, n1.tileOrder());
    }
}