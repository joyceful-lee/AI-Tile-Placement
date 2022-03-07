import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/*
 * Class to read in the input text files
 * and organizes data into usable forms
 */

public class FileReader {
    List<String> text = new ArrayList<>();
    private int[] tileCount; // total number of tiles given by {OUTER, EL, FULL}
    private int[] targets; // target number of 1 - 4 values
    private char[][] tiles; // landscape organized by tiles
    private int totalTiles;
    
    // reads in file
    public void readFile(String name) throws Exception{
        ClassLoader cL = getClass().getClassLoader();
        InputStream iS = cL.getResourceAsStream(name);
        try(InputStreamReader sR = new InputStreamReader(iS, StandardCharsets.UTF_8);
            BufferedReader bR = new BufferedReader(sR)) {
            String str;

            // file -> string list
            while ((str = bR.readLine()) != null) {
                // skip comments
                if(str.trim().startsWith("#")){
                    continue;
                }
                // skip empty lines
                if(str.isEmpty()){
                    continue;
                }
                // add to array list
                text.add(str);
            }
            // System.out.println(text);

            // find landscape square dimension by searching for first '{'
            int dimension = dimensionCalc(text);
            // System.out.println(dimension);

            // get tile numbers
            tileCount = setTileCount(dimension, text);
            
           // get total tiles (from the {})
            totalTiles = findTotalTiles(tileCount);
            
            // input list -> array
            // landscape organized in 2D array
            char[][] landscape = new char[dimension][1];
            tiles = new char[totalTiles][1];
            landscapeArray(dimension, landscape, text, totalTiles, tiles);

            // get targets
            targets = setTargets(dimension, text);
            
        }
    }
    
    // function to calculate landscape dimension
    // (counts # of lines before the {layout})
    public static int dimensionCalc(List<String> text){
        int dimension = 0;
        for (String s : text) {
            if (s.charAt(0) != '{') {
                dimension++;
            } else {
                break;
            }
        }
        return dimension;
    }
    
    // function to compute total tiles
    public static int findTotalTiles(int[] tileCount){
        int totalTiles = 0;
        for (int j : tileCount) {
            totalTiles += j;
        }
        return totalTiles;
    }
    
    // function to create landscape as an array organized by rows
    public static void landscapeArray(int dimension, char[][] landscape,
                                          List<String> text, int totalTiles, char[][] tiles){
        StringBuilder str = new StringBuilder();
        for(int j=0; j<dimension; j++) {
            for (int i = 0; i < text.get(0).length(); i++) {
                if (i % 2 != 0) {
                    continue;
                }
                str.append(text.get(j).charAt(i));
            }
            landscape[j] = str.toString().toCharArray();
            str.setLength(0);
        }
        // organizing the landscape into tiles
        int startR = 0, endR = 4;
        int startC = 0, endC = 4;
        int count = 0;
        int r =startR, c = startC;
        while(r<endR) { //row iteration
            while(c<endC) {
                if (count == totalTiles) {
                    break;
                }
                str.append(landscape[r][c]);
                if (r == (endR - 1) && c == (endC - 1)) {
                    tiles[count] = str.toString().toCharArray();
                    str.setLength(0);
                    count++;
                    if (endR >= dimension - 1) {
                        startR = 0;
                        r = startR;
                        endR = 4;
                        startC += 4;
                        endC += 4;
                        if (startC >= (dimension - 1)) {
                            break;
                        }
                    } else {
                        endR += 4;
                    }
                }
                c++;
            }
            r++;
            c=startC;
        }
    }
    
    // Tile values in an array: Outer(0), El(1), Full(2)
    public static int[] setTileCount(int size, List<String> text){
        int outer, el;
        // S for EL, F for FULL, T for OUTER - uses letters that don't occur elsewhere
        boolean elBeforeF = text.toString().indexOf('S') < text.toString().indexOf('F');
        boolean oBeforeF = text.toString().indexOf('T') < text.toString().indexOf('F');
        boolean oBeforeE = text.toString().indexOf('T') < text.toString().indexOf('S');
        if(oBeforeE && oBeforeF){
            outer = 0;
            if(elBeforeF){
                el = 1;
            }else{
                el = 2;
            }
        }else if(text.toString().indexOf('S') < text.toString().indexOf('T') && elBeforeF){
            el = 0;
            if(oBeforeF){
                outer = 1;
            }else{
                outer = 2;
            }
        }else{
            if(oBeforeE){
                outer = 1;
                el = 2;
            }else{
                el = 1;
                outer = 2;
            }
        }
        Vector<Integer> tilesTemp = new Vector<>();
        for (String token : text.get(size).replaceAll
                ("\\D+", " ").trim().split("\\s+")) {
            tilesTemp.add(Integer.parseInt(token));
        }
        int[] tileCount = new int[3];
        for(int i=0; i<3; i++) {
            for (int j = 0; j < tilesTemp.size(); j++) {
                if(i == outer) {
                    tileCount[0] = tilesTemp.elementAt(j);
                    i++;
                }else if(i == el){
                    tileCount[1] = tilesTemp.elementAt(j);
                    i++;
                }else{
                    tileCount[2] = tilesTemp.elementAt(j);
                    i++;
                }
            }
        }
        //System.out.println(Arrays.toString(tileCount));
        return tileCount;
    }
    
    // Retrieves target values of 1(0), 2(1), 3(2), 4(3)
    public static int[] setTargets(int size, List<String> text){
        Vector<Integer> targetTemp = new Vector<>();
        int[] targets = new int[4];
        for(int i=1;i<5;i++) {
            for (String token : text.get(size + i).replaceAll
                    ("\\D+", " ").trim().split("\\s+")) {
                targetTemp.add(Integer.parseInt(token));
            }
        }
        for(int i=0; i<4; i++) {
            for (int j = 0; j < targetTemp.size(); j++) {
                if (j % 2 == 0) {
                    continue;
                }
                targets[i] = targetTemp.elementAt(j);
                i++;
            }
        }
        return targets;
    }

    // get the targets for use in main class
    public int[] getTargets() {
        return targets;
    }

    // get the landscape for use in main class
    public char[][] getTiles() {
        return tiles;
    }

    // get the tiles for use in main class
    public int[] getTileCount() {
        return tileCount;
    }
    
    public int getTotalTiles(){
        return totalTiles;
    }
}
