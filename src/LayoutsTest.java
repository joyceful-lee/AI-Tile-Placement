import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class LayoutsTest {

    @Test
    void getEl() {
        char[] elTest0 = new char[16];
        for(int i = 0; i< elTest0.length; i++){
            if(i == 4 || i == 12 || i == 8 || i == 0 || i == 1 || i == 2 || i == 3)
                elTest0[i] = '1';
            else
                elTest0[i] = '0';
        }
        char[] elTest1 = new char[16];
        for(int i = 0; i< elTest1.length; i++){
            if(i == 0 || i == 1 || i == 2 || i == 3 || i == 7 || i == 11 || i == 15)
                elTest1[i] = '1';
            else
                elTest1[i] = '0';
        }
        char[] elTest2 = new char[16];
        for(int i = 0; i< elTest2.length; i++){
            if(i == 3 || i == 7 || i == 11 || i == 15 || i == 14 || i == 13 || i == 12)
                elTest2[i] = '1';
            else
                elTest2[i] = '0';
        }
        char[] elTest3 = new char[16];
        for(int i = 0; i< elTest3.length; i++){
            if(i == 15 || i == 14 || i == 13 || i == 12 || i == 8 || i == 4 || i == 0)
                elTest3[i] = '1';
            else
                elTest3[i] = '0';
        }
        assertArrayEquals(elTest0, Layouts.getEl(0));
        assertArrayEquals(elTest1, Layouts.getEl(1));
        assertArrayEquals(elTest2, Layouts.getEl(2));
        assertArrayEquals(elTest3, Layouts.getEl(3));
    }

    @Test
    void getFull() {
        char[] fullTest = new char[16];
        Arrays.fill(fullTest, '1');
        assertArrayEquals(fullTest, Layouts.getFull());
    }

    @Test
    void getOuter() {
        char[] outerTest = new char[16];
        for(int i = 0; i< outerTest.length; i++){
            if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 7 ||
                    i == 8 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15)
                outerTest[i] = '1';
            else
                outerTest[i] = '0';
        }
        assertArrayEquals(outerTest, Layouts.getOuter());
    }

    @Test
    void getInitialLayout() {
        char[] initialTest = new char[16];
        Arrays.fill(initialTest, '1');
        assertArrayEquals(initialTest, Layouts.getInitialLayout());
    }
}