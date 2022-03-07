import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SearchAlgorithmTest {

    @Test
    void getNeighbors() {
        String val = "1234567812345678";
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
        Node n2 = new Node(tilesTest, targetNumTest, tileCountTest);
        n2.parent = n1;
        ArrayList<Node> list = SearchAlgorithm.getNeighbors(n1);
        assertEquals(0, list.size());
    }

    @Test
    void inOpen() {
        String val = "1234567812345678";
        String layoutF = "1111111111111111",
                layoutO = "1111100110011111",
                layoutE = "1111100010001000";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutO.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        Tile[] tilesTest1 = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        int[] targetNumTest = new int[]{1,0,0,1};
        int[] tileCountTest = new int[]{1,0,0};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        Node n2 = new Node(tilesTest, targetNumTest, tileCountTest);
        Node n3 = new Node(tilesTest1, targetNumTest, tileCountTest);
        ArrayList<Node> testList = new ArrayList<>();
        testList.add(n1);
        testList.add(n2);
        assertTrue(SearchAlgorithm.inOpen(n1, testList));
        assertFalse(SearchAlgorithm.inOpen(n3, testList));
    }

    @Test
    void inClosed() {
        String val = "1234567812345678";
        String layoutF = "1111111111111111",
                layoutO = "1111100110011111",
                layoutE = "1111100010001000";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutO.toCharArray(), "OUTER"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        Tile[] tilesTest1 = {
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutF.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0"),
                new Tile(val.toCharArray(), layoutE.toCharArray(), "EL 0")
        };
        int[] targetNumTest = new int[]{1,0,0,1};
        int[] tileCountTest = new int[]{1,0,0};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        Node n2 = new Node(tilesTest, targetNumTest, tileCountTest);
        Node n3 = new Node(tilesTest1, targetNumTest, tileCountTest);
        ArrayList<Node> testList = new ArrayList<>();
        testList.add(n1);
        testList.add(n2);
        assertTrue(SearchAlgorithm.inClosed(n1, testList));
        assertFalse(SearchAlgorithm.inClosed(n3, testList));
    }
}