import java.util.Arrays;

public class Arc {
    private final Node n1;
    private final Node n2;

    public Arc() {
        n1 = null;
        n2 = null;
    }
    
    public Arc(Node one, Node two) {
        n1 = one;
        n2 = two;
    }
    
    public Node left() {
        return n1;
    }
    
    public Node right() {
        return n2;
    }

    public static Arc arcCreate(Node n){
        Arc temp;
        temp = new Arc(n, n.parent);
        return temp;
    }
    
    public boolean equals(Object other) {
        if(other == null){
            return false;
        }
        if(other == this){
            return true;
        }
        if(!(other instanceof Arc a)){
            return false;
        }
        return a.left().equals(n1) && a.right().equals(n2);
    }
    
    public String toString(){
        assert this.n1 != null;
        assert this.n2 != null;
        return "\nArc Left: " + Arrays.toString(this.n1.targetNumberCalc()) +
                " Arc Right: " + Arrays.toString(this.n2.targetNumberCalc());
    }
}
