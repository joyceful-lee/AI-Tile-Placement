import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HeuristicTest {

    @Test
    void mrvCalc() {
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
        Node n3 = new Node(tilesTest, targetNumTest, tileCountTest);
        n2.parent = n1;
        n3.parent = n2;
        ArrayList<Node> testList = new ArrayList<>();
        testList.add(n2);
        testList.add(n3);
        assertEquals(0, Heuristic.mrvCalc(testList));
    }

    @Test
    void lcvCalc() {
        String val = "12345678";
        String layout = "11111111";
        Tile[] tilesTest = {
                new Tile(val.toCharArray(), layout.toCharArray(), "FULL"),
                new Tile(val.toCharArray(), layout.toCharArray(), "FULL")
        };
        int[] targetNumTest = new int[]{1,0,0,1};
        int[] tileCountTest = new int[]{1,0,0};
        Node n1 = new Node(tilesTest, targetNumTest, tileCountTest);
        int[][] list = {
                {0,2},
                {1,4}
        };
        assertEquals(4, Heuristic.lcvCalc(n1,0));
    }

    @Test
    void TIE() {
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
        ArrayList<Node> testList = new ArrayList<>();
        testList.add(n1);
        testList.add(n2);
        assertEquals(0, Heuristic.TIE(1,0,testList));
    }
}