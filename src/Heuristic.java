import java.util.ArrayList;

public class Heuristic {
    
    // min remaining values
    public static int mrvCalc(ArrayList<Node> openList){
        int index = 0;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < openList.size(); i++) {
            Node temp = openList.get(i);
            if(temp.distColor < min) { // is distance to target < min
                min = temp.distColor;
                index = i;
            }
            if(temp.distColor == min){ //TIE - degree heuristic
                int minI = TIE(i, index, openList);
                if(minI != index){
                    index = minI;
                    min = temp.distColor;
                }
            }
        }
        return index;
    }

    // compare layouts for best option - choose greater distance
    public static int lcvCalc(Node neighbor, int index){
        int distTemp = Integer.MIN_VALUE; // current color distance
        int layout = 5;
        for (int j = 0; j < 6; j++) {
            if (j < 4 && neighbor.tiles[index].domain.contains('E')) {
                //el layout, send j
                if (neighbor.changeLayout(index, 1, j)) {
                    if (neighbor.distColor > distTemp) {
                        distTemp = neighbor.distColor;
                        layout = j;
                    }
                }
            } else if (j == 4 && neighbor.tiles[index].domain.contains('O')) { //outer
                if (neighbor.changeLayout(index, 0, 0)) {
                    if (neighbor.distColor > distTemp) {
                        distTemp = neighbor.distColor;
                        layout = j;
                    }
                }
            }
        }
        return layout;
    }
    
    public static int TIE(int currentIndex, int currentMin, ArrayList<Node> openList){
        //most constrained
        Node current = openList.get(currentIndex);
        Node min = openList.get(currentMin);
        if(current.distLayout > min.distLayout){
            return currentIndex;
        }else{
            return currentMin;
        }
    }
}
