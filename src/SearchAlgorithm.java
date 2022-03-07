import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
/*
 * This is the main class for the project.
 * 
 * IMPORTANT: make sure your IDE does not strip whitespaces at EOL.
 * This can be changed via the following:
 *      File -> Settings -> General -> On Save -> uncheck 'remove trailing spaces'
 * If this is not done, landscape will not be accurate.
 */

public class SearchAlgorithm {

    public static void main(String[] args) {
        final ExecutorService service = Executors.newSingleThreadExecutor();
        // Timeout control in seconds
        int timeout = 30;
        try{
            final Future<?> f = service.submit(()-> {
                FileReader fr = new FileReader();
                try {
                    fr.readFile("./data/input13"); // change input here
                } catch (Exception e) {
                    System.err.println("File not found");
                    System.exit(0);
                }
                char[][] tiles = fr.getTiles();
                int[] targets = fr.getTargets();
                int[] tileCount = fr.getTileCount();
                int totalTiles = fr.getTotalTiles();

                cspAlg(tiles, targets, tileCount, totalTiles);
            });
            f.get(timeout, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.err.println(timeout + " seconds passed");
            System.exit(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            service.shutdown();
        }

    }

    // CSP
    public static int cspAlg(char[][] tiles, int[] targets, int[] tileCount, int totalTiles) {
        ConstraintProp cp = new ConstraintProp();

        // ordered tile array creation
        // initialized with Full layout
        Tile[] tileArray = new Tile[totalTiles];
        for (int i = 0; i < totalTiles; i++) {
            tileArray[i] = new Tile(tiles[i], Layouts.getInitialLayout(), "FULL");
        }
        
        // Node = tile array, other values
        Node start = new Node(tileArray, targets, tileCount);
        ArrayList<Node> openList = new ArrayList<>();
        ArrayList<Node> closedList = new ArrayList<>();
        openList.add(start);

        while (openList.size() > 0) {
            int index = Heuristic.mrvCalc(openList); //MRV
            Node current = openList.remove(index);
            closedList.add(current);
            // to print each node:
            // System.out.println(current);

            // print solution when found
            if (current.finalCheck()) {
                solutionPrint(current);
                return 0;
                
            }

            // LCV Heuristic
            ArrayList<Node> neighbors = getNeighbors(current);
            
            // AC3 alg to check consistency before neighbors are added to open list
            if(cp.AC3(neighbors)) {
                for (Node test : neighbors) {

                    //node not in closed nor open set
                    if (!inClosed(test, closedList) && !inOpen(test, openList)) {
                        openList.add(test);
                    } else {
                        test.parent = current;
                    }
                }
            }
        }
        return -1;
    }

    // returns neighbor list
    static ArrayList<Node> getNeighbors(Node current) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int[][] list = current.tileOrder();
        for (int i = 0; i < current.tiles.length; i++) {
            Node neighbor = new Node(current);
            //loop for all then grab best using lcv
            int layout = Heuristic.lcvCalc(neighbor, list[i][0]);
            switch(layout){
                case 0-> neighbor.changeLayout(list[i][0],1,0); // El 0
                case 1-> neighbor.changeLayout(list[i][0],1,1); // El 1
                case 2-> neighbor.changeLayout(list[i][0],1,2); // El 2
                case 3-> neighbor.changeLayout(list[i][0],1,3); // El 3
                case 4-> neighbor.changeLayout(list[i][0],0,0); // Outer
                case 5-> neighbor.changeLayout(list[i][0],2,0); // Full
            }
            //System.out.println(layout);
            if(!neighbors.contains(neighbor)) {
                neighbors.add(neighbor);
                neighbor.parent = current;
                if(ConstraintProp.arcConsistency(neighbor)){
                    neighbors.remove(neighbor);
                }
            }
        }
        return neighbors;
    }

    static void solutionPrint(Node current) {
        StringBuilder list = new StringBuilder();
        
        // append Tile numbers, names, and uncovered bush values
        for (int i = 0; i < current.tiles.length; i++) {
            list.append("Tile ").append(i).append(": ")
                    .append(current.tiles[i].layoutName).append("\t | \t");
            // if you want to see the uncovered bushes (for checking, etc.):
            /*for (int j = 0; j < current.tiles[i].layout.length; j++) {
                if (current.tiles[i].layout[j] == '0') {
                    list.append(current.tiles[i].value[j]).append(" ");
                }
            }*/
            list.append("\n");
        }
        
        // append current layout count # (should equal input values)
        list.append("Layout Count: ")
                .append(Arrays.toString(current.currentLayoutCount)).append("\n");

        // append current color count # (should equal input values)
        list.append("Color Count: ")
                .append(Arrays.toString(current.currentColorCount));
        System.out.println(list);
    }

    static boolean inOpen(Node node, ArrayList<Node> openList) {
        return openList.contains(node);
    }

    static boolean inClosed(Node node, ArrayList<Node> closedList) {
        return closedList.contains(node);
    }
}
