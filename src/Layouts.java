import java.util.Arrays;
/*
 * Holds the layouts:
 * - EL has 4 options for when rotated
 * - OUTER is the same when rotated
 * - FULL is the same when rotated
 * 
 * Tile numbered by:
 * 0  1  2  3
 * 4  5  6  7
 * 8  9  10 11
 * 12 13 14 15
 * 
 * 1 = bush is covered
 * 0 = bush is uncovered
 */

public class Layouts {

    // changes tile layout to EL layout designated via the 'option'
    public static char[] getEl(int option){
        // following values are 1s
        // EL 0 - 12 8 4 0 1 2 3 (Left and Top)
        // EL 1 - 0 1 2 3 7 11 15 (Top and Right)
        // EL 2 - 3 7 11 15 14 13 12 (Right and Bottom)
        // EL 3 - 15 14 13 12 8 4 0 (Bottom and Left)
        char[] elLayout = new char[16];
        switch(option){
            case(0):
                for(int i = 0; i< elLayout.length; i++){
                    if(i == 4 || i == 12 || i == 8 || i == 0 || i == 1 || i == 2 || i == 3)
                        elLayout[i] = '1';
                    else
                        elLayout[i] = '0';
                }
                break;
            case(1):
                for(int i = 0; i< elLayout.length; i++){
                    if(i == 0 || i == 1 || i == 2 || i == 3 || i == 7 || i == 11 || i == 15)
                        elLayout[i] = '1';
                    else
                        elLayout[i] = '0';
                }
                break;
            case(2):
                for(int i = 0; i< elLayout.length; i++){
                    if(i == 3 || i == 7 || i == 11 || i == 15 || i == 14 || i == 13 || i == 12)
                        elLayout[i] = '1';
                    else
                        elLayout[i] = '0';
                }
                break;
            case(3):
                for(int i = 0; i< elLayout.length; i++){
                    if(i == 15 || i == 14 || i == 13 || i == 12 || i == 8 || i == 4 || i == 0)
                        elLayout[i] = '1';
                    else
                        elLayout[i] = '0';
                }
                break;
        }
        return elLayout;
    }

    // populates layout with all 1s for full
    public static char[] getFull(){
        //0 - 15 = all 1s
        char[] fullLayout = new char[16];
        Arrays.fill(fullLayout, '1');
        return fullLayout;
    }

    // changes tile layout to OUTER
    public static char[] getOuter(){
        //following values are 1s
        // 0 1 2 3 7 11 15 14 13 12 8 4
        char[] outerLayout = new char[16];
        for(int i = 0; i< outerLayout.length; i++){
            if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 7 || 
                    i == 8 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15)
                outerLayout[i] = '1';
            else
                outerLayout[i] = '0';
        }
        return outerLayout;
    }
    
    // the same as FULL, kept in a different function for testing different options
    public static char[] getInitialLayout(){
        char[] initialLayout = new char[16];
        Arrays.fill(initialLayout, '1');
        return initialLayout;
    }
    
}
