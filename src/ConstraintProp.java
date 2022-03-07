import java.util.ArrayList;

public class ConstraintProp {

    public boolean AC3(ArrayList<Node> neighbors) { //initial grid
        Arc arc = new Arc();
        ArrayList<Node> nei = neighbors;
        ArrayList<Arc> arcs = new ArrayList<>();
        for (Node neighbor : neighbors) {
            arc = arc.arcCreate(neighbor);
            arcs.add(arc);
        }
        while(arcs.size()>0){
            Node n = arcs.get(0).left();
            arc = arcs.remove(0);
            if (arcConsistency(arc.left())) {
                if(arc.left().distLayout <= 0){
                    return false;
                }
                for(Node next : nei){
                    if(next != n){
                        arc = arc.arcCreate(next);
                        arcs.add(arc);
                    }
                }
            }
        }
        return true;
    }
    
    public static boolean arcConsistency(Node node){
        //total of each tile of n1 (parent) <= n2 (current) and vice versa
        //total of targets of n1 (parent) >= n2 (current) and vice versa
        boolean removed = false;
        // -1 to not include Full
        for(int i=0;i<node.currentLayoutCount.length-1;i++){
            if (node.currentLayoutCount[i] > node.layoutTarget[i]) { // if not domain
                removed = true;
                break;
            }
        }
        if(node.currentLayoutCount[2] < node.layoutTarget[2]){
            removed = true;
        }
        for(int i=0;i<node.currentColorCount.length;i++){
            if (node.currentColorCount[i] > node.colorTarget[i]) {
                removed = true;
                break;
            }
        }
        for(int i = 0; i<node.tiles.length;i++){
            if(node.tiles[i].domain.isEmpty()){
                System.out.println("EMPTY");
                removed = true;
                node.tiles[i].domain.add('O');
                node.tiles[i].domain.add('E');
                node.tiles[i].domain.add('F');
            }
        }
        return removed;
    }
}
