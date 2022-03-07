import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class FileReaderTest extends TestCase {

    @org.junit.jupiter.api.Test
    void dimensionCalc() {
        List<String> testText = new ArrayList<String>();
        testText.add("test");
        testText.add("{test}");
        assertEquals(1, FileReader.dimensionCalc(testText));
    }

    @org.junit.jupiter.api.Test
    void findTotalTiles() {
        int[] testTiles = new int[]{1,1,1};
        assertEquals(3, FileReader.findTotalTiles(testTiles));
    }

    @org.junit.jupiter.api.Test
    void setTileCount() {
        List<String> testText = new ArrayList<String>();
        testText.add("{OUTER_BOUNDARY=6, EL_SHAPE=11, FULL_BLOCK=8}");
        int[] testTiles = new int[]{6,11,8};
        assertArrayEquals(testTiles, FileReader.setTileCount(0,testText));
    }

    @org.junit.jupiter.api.Test
    void setTargets() {
        List<String> testText = new ArrayList<String>();
        testText.add("#");
        testText.add("1:18");
        testText.add("2:12");
        testText.add("3:16");
        testText.add("4:5");
        int[] testTiles = new int[]{18,12,16,5};
        assertArrayEquals(testTiles, FileReader.setTargets(0,testText));
    }
}