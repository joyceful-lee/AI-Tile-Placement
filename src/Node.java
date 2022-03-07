import java.util.ArrayList;
import java.util.Arrays;

class Tile{
    char[] value;
    char[] layout;
    ArrayList<Character> domain;
    String layoutName;
    
    // tile = [x,y], where x = value and y = whether covered or uncovered
    public Tile(char[] val, char[] lay, String name){
        value = val;
        layout = lay;
        domain = new ArrayList<>();
        domain.add('O');
        domain.add('E');
        domain.add('F');
        layoutName = name;
    }
    
    public String toString(){
        String result = "";
        result+= ("Value: ");
        result += Arrays.toString(this.value);
        result += (" Layout: ");
        result += Arrays.toString(this.layout);
        result += this.layoutName;
        result += "\t";
        result += this.domain.toString();
        
        return result;
    }
}

public class Node {
    Tile[] tiles;
    Node parent;
    int[] layoutTarget; // Goal values given
    int[] colorTarget; // Goal values given
    int distColor; //distance from current value to target
    int distLayout; //distance from current value to target
    int[] currentLayoutCount; //current number of layouts
    int[] currentColorCount; //current number of colors
    
    public Node(Tile[] tile, int[] targetNum, int[] tileCount){
        tiles = tile;
        layoutTarget = tileCount;
        colorTarget = targetNum;
        currentLayoutCount = layoutNumberCalc();
        currentColorCount = targetNumberCalc();
        distLayout = tileCount[0] + tileCount[1] + tileCount[2];
        distColor = currentColorCount[0] + currentColorCount[1] +
                currentColorCount[2] + currentColorCount[3];
    }
    
    public Node(Node base){
        this.tiles = new Tile[base.tiles.length];
        this.layoutTarget = base.layoutTarget;
        this.colorTarget = base.colorTarget;
        this.distColor = base.distColor;
        this.distLayout = base.distLayout;
        this.parent = base;
        this.currentLayoutCount = base.layoutNumberCalc();
        this.currentColorCount = base.targetNumberCalc();
        for(int i = 0; i<base.tiles.length;i++){
            this.tiles[i] = new Tile
                    (base.tiles[i].value, base.tiles[i].layout, base.tiles[i].layoutName);
        }
    }
    
    // count how many of each layout is assigned
    public int[] layoutNumberCalc(){
        int[] numCalc = new int[3];
        for (Tile tile : tiles) {
            if (tile.layoutName.equals("OUTER")) {
                numCalc[0] += 1;
            }
            if (tile.layoutName.equals("EL 0") || tile.layoutName.equals("EL 1")
                    || tile.layoutName.equals("EL 2") || tile.layoutName.equals("EL 3")) {
                numCalc[1] += 1;
            }
            if (tile.layoutName.equals("FULL")) {
                numCalc[2] += 1;
            }
        }
        return numCalc;
    }
    
    // count how many of each bush color is assigned
    public int[] targetNumberCalc(){
        int one = 0, two = 0, three = 0, four = 0;
        int[] numCalc = new int[4];
        for (Tile tile : tiles) {
            for (int j = 0; j < tile.value.length; j++) {
                if (tile.value[j] == '1' && tile.layout[j] == '0') {
                    one++;
                }
                if (tile.value[j] == '2' && tile.layout[j] == '0') {
                    two++;
                }
                if (tile.value[j] == '3' && tile.layout[j] == '0') {
                    three++;
                }
                if (tile.value[j] == '4' && tile.layout[j] == '0') {
                    four++;
                }
            }
        }
        numCalc[0] = one;
        numCalc[1] = two;
        numCalc[2] = three;
        numCalc[3] = four;
        
        return numCalc;
    }
    
    // checks layout number and bush color number: if == goal, solution is found.
    public boolean finalCheck(){
        for(int i = 0; i< currentLayoutCount.length;i++){
            if(this.currentLayoutCount[i] == this.layoutTarget[i]){
                continue;
            }else{
                return false;
            }
        }
        for(int i = 0; i<this.currentColorCount.length; i++) {
            if (this.currentColorCount[i] == this.colorTarget[i]) {
                continue;
            }else{
                return false;
            }
        }
        return true;
    }

    // checks if layout is within bounds - removes domain if not
    public boolean layoutCheck() {
        int[] numCalc = new int[]{0,0,0};
        numCalc[0] = this.currentLayoutCount[0];
        numCalc[1] = this.currentLayoutCount[1];
        numCalc[2] = this.currentLayoutCount[2];
        if(this.currentLayoutCount[0] == this.layoutTarget[0] ||
                this.currentLayoutCount[1] == this.layoutTarget[1]){
            for (Tile tile : tiles) {
                int temp;
                if(numCalc[0] >= this.layoutTarget[0]){
                    if(numCalc[0] > this.layoutTarget[0] && tile.domain.contains('O')){
                        temp = tile.domain.indexOf('O');
                        tile.domain.remove(temp);
                    }
                    numCalc[0]++;
                }
                if(numCalc[1] >= this.layoutTarget[1]){
                    if(numCalc[1] > this.layoutTarget[1] && tile.domain.contains('E')){
                        temp = tile.domain.indexOf('E');
                        tile.domain.remove(temp);
                    }
                    numCalc[1]++;
                }
            }
        }else return this.currentLayoutCount[0] <= this.layoutTarget[0] &&
                this.currentLayoutCount[1] <= this.layoutTarget[1];
        return true;
    }

    // checks if bush color numbers are within bounds
    public boolean targetCheck(){
        for(int i = 0; i < currentColorCount.length; i++){
            if(this.currentColorCount[i] <= this.colorTarget[i]){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }
    
    // changes layout of tile & updates relevant values
    public boolean changeLayout(int tile, int option, int el){
        switch (option) {
            case (0) -> {
                this.tiles[tile].layout = Layouts.getOuter();
                this.tiles[tile].layoutName = "OUTER";
            }
            case (1) -> {
                this.tiles[tile].layout = Layouts.getEl(el);
                this.tiles[tile].layoutName = ("EL " + el);
            }
            case (2) -> {
                this.tiles[tile].layout = Layouts.getFull();
                this.tiles[tile].layoutName = "FULL";
            }
        }
        this.currentLayoutCount = layoutNumberCalc();
        this.currentColorCount = targetNumberCalc();
        distCalc(this);
        return layoutCheck() && targetCheck();
    }
    
    // calculates difference (distance) from current value to goal (layout and bush)
    public void distCalc(Node node){
        //options number
        node.distLayout = (node.layoutTarget[0] - node.currentLayoutCount[0]) 
                + (node.layoutTarget[1] - node.currentLayoutCount[1])
                + (node.currentLayoutCount[2] - node.layoutTarget[2]); //smaller better

        //target number lenient
        node.distColor = (node.colorTarget[0] - node.currentColorCount[0])
                + (node.colorTarget[1] - node.currentColorCount[1])
                + (node.colorTarget[2] - node.currentColorCount[2])
                + (node.colorTarget[3] - node.currentColorCount[3]);
         //smaller better
    }
    
    // organizes tile from tile with lease # of values to tile with most
    public int[][] tileOrder(){
        int count = 0;
        int[][] list = new int[this.tiles.length][2];
        for(int i = 0; i < this.tiles.length;i++){
            for(int j = 0; j < this.tiles[i].value.length;j++){
                if(this.tiles[i].value[j] == '1' || this.tiles[i].value[j] == '2' ||
                        this.tiles[i].value[j] == '3' ||  this.tiles[i].value[j] == '4'){
                    list[i][1] = count++;
                }
            }
            list[i][0] = i;
            count = 0;
        }
        Arrays.sort(list, (o1, o2) -> {
            if (o1[1] > o2[1])
                return 1;
            else
                return -1;
        });
        return list;
    }

    public boolean equals(Object o) {
        Node test = (Node)o;
        // If there are different solution values
        for(int i = 0; i< currentLayoutCount.length; i++) {
            if (this.currentLayoutCount[i] != test.currentLayoutCount[i]) {
                return false;
            }
        }
        for(int i = 0; i< currentColorCount.length; i++) {
            if (this.currentColorCount[i] != test.currentColorCount[i]) {
                return false;
            }
        }
        // If any tiles aren't the same, they aren't the same Node
        for (int i = 0; i < tiles.length; i++) {
            if (!Arrays.toString(this.tiles[i].layout).equals(Arrays.toString(test.tiles[i].layout))) {
                return false;
            }
        }
        return true;
    }
    
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Variable:");
        for(int i = 0; i < this.tiles.length;i++){
            result.append("\n");
            result.append("\t | \t Tile: ").append(i).append("\t | \t");
            result.append(this.tiles[i]);
        }
        result.append("\n").append("Tile Layout Count: ");
        result.append(Arrays.toString(layoutNumberCalc()));
        result.append("\n").append("Tile Layout Goal: ");
        result.append("[OUTER ").append(this.layoutTarget[0]).append(", EL ")
                .append(this.layoutTarget[1]).append(", FULL ")
                .append(this.layoutTarget[2]).append("]").append("\n");
        result.append("Target Number Count: ");
        result.append(Arrays.toString(targetNumberCalc()));
        result.append("\n").append("Target Number Goal: ");
        result.append("[1: ").append(this.colorTarget[0]).append(", 2: ")
                .append(this.colorTarget[1]).append(", 3: ").append(this.colorTarget[2]).append(", 4: ")
                .append(this.colorTarget[3]).append("]").append("\n");
        result.append("Target Difference: ").append(this.distColor).append("\n");
        result.append("Layout Difference: ").append(this.distLayout).append("\n");
        return result.toString();
    }
}
