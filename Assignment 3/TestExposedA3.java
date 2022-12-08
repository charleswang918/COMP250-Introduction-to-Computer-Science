//  package ed;
//Updated on November 13 2021
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestExposedA3 {

    TST<Integer> tree;
    TSTNode<Integer> root;

    @Test
    @Tag("score:1")
    @DisplayName("TST Contains Test 1")
    void testContains1() {
        tree = new TST<>();

        tree.insert(5);

        assertTrue(tree.contains(5));
        assertFalse(tree.contains(4));
    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Contains Test 2")
    void testContains2() {
        TST<Character> tree = new TST<>();

        tree.insert('5');
        tree.insert('3');
        tree.insert('0');
        tree.insert('3');
        tree.insert('1');
        tree.insert('2');


        assertTrue(tree.contains('1'));
        assertTrue(tree.contains('2'));
        assertFalse(tree.contains('4'));

    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Contains Test 3")
    void testContains3() {
        tree = new TST<>();

        tree.insert(5);
        tree.insert(3);
        tree.insert(10);
        tree.insert(3);
        tree.insert(-1);
        tree.insert(21);


        assertTrue(tree.contains(3));
        tree.remove(3);
        assertTrue(tree.contains(3));
        tree.remove(3);
        assertFalse(tree.contains(3));
        tree.insert(15);
        tree.insert(14);
        tree.insert(13);
        assertTrue(tree.contains(13));

    }

    @Test
    @Tag("score:2")
    @DisplayName("TST Contains Test 4")
    void testContains4() {
        TST<String> tree = new TST<>();

        tree.insert("5");
        tree.insert("3");
        tree.insert("10");
        tree.insert("3");
        tree.insert("-1");
        tree.insert("21");
        tree.insert("15");
        tree.insert("14");
        tree.insert("13");
        tree.insert("11");


        assertTrue(tree.contains("13"));
        assertTrue(tree.contains("5"));
        tree.remove("3");
        assertTrue(tree.contains("3"));


    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Insert Test 1")
    void testInsertTest1(){
        tree = new TST<>();

        tree.insert(5);
        assertTrue(tree.contains(5));
    }

    @Test
    @Tag("score:2")
    @DisplayName("TST Insert Test 2")
    void testInsertTest2(){
        tree = new TST<>();

        tree.insert(5);
        tree.insert(2);
        tree.insert(10);
        tree.insert(-1);
        tree.insert(15);
        tree.insert(25);
        tree.insert(-9);
        tree.contains(-9);
        assertTrue(tree.contains(-9));
    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Insert Test 3")
    void testInsertTest3(){
        tree = new TST<>();

        tree.insert(5);
        tree.insert(2);
        tree.insert(10);
        tree.insert(-1);
        tree.insert(15);
        tree.insert(25);
        tree.insert(-9);
        assertTrue(tree.contains(25));
        assertEquals(25, tree.root.right.right.right.element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Insert Test 4")
    void testInsertTest4(){
        tree = new TST<>();

        tree.insert(5);
        tree.insert(2);
        tree.insert(10);
        tree.insert(-1);
        tree.insert(15);
        tree.insert(25);
        tree.insert(-9);
        assertFalse(tree.contains(0));
    }

    void buildTestObj(List<Integer> l, TST<Integer> o1) {
        for (Integer x : l) {
            o1.insert(x);
        }
    }


    @Test
    @Tag("score:2")
    @DisplayName("TSTIterator Test 1")
    void testIterator1() {
        root = new TSTNode<>(5);
        List<Integer> params =  new LinkedList<Integer>();

        params.add(5);

        TST<Integer> tree = new TST<>();
        tree.root = root;

        int i = 0;
        for(Integer element : tree){
            assertEquals(params.get(i++), element);
        }

        assertEquals(params.size(), i);

    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTIterator Test 2")
    void testIterator2() {
        root = new TSTNode<>(25);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(15,25,22,18,18,20,20));

        TST<Integer> tree = new TST<>();
        tree.root = root;
        buildTestObj(params, tree);

        params.add(25);
        params.sort(Comparator.naturalOrder());

        int i =0;
        for(Integer element : tree){
            assertEquals(params.get(i++), element);
        }

        assertEquals(params.size(), i);

    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTIterator Test 3")
    void testIterator3() {
        root = new TSTNode<>(1);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(2,3,4,5,6,7));

        TST<Integer> tree = new TST<>();
        tree.root = root;
        buildTestObj(params, tree);

        params.add(1);
        params.sort(Comparator.naturalOrder());

        int i =0;
        for(Integer element : tree){
            assertEquals(params.get(i++), element);
        }

        assertEquals(params.size(), i);
    }


    @Test
    @Tag("score:3")
    @DisplayName("TSTIterator Test 4")
    void testIterator4() {
        root = new TSTNode<>(5);
        List<Integer> params =  new LinkedList<Integer>();

        params.add(5);

        TST<Integer> tree = new TST<>();
        tree.root = root;

        Iterator iter = tree.iterator();

        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());
    }

    @Test
    @Tag("score:3")
    @DisplayName("TSTIterator Test 5")
    void testIterator5() {
        root = new TSTNode<>(1);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(9,8,-1,-4,-6));

        TST<Integer> tree = new TST<>();
        tree.root = root;
        buildTestObj(params, tree);

        params.add(1);
        params.sort(Comparator.naturalOrder());

        int i =0;
        for(Integer element : tree){
            assertEquals(params.get(i++), element);
        }

        assertEquals(params.size(), i);
    }

    @Test
    @Tag("score:3")
    @DisplayName("TSTIterator Test 6")
    void testIterator6() {
        root = new TSTNode<>(5);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(5,6));

        TST<Integer> tree = new TST<>();
        tree.root = root;
        buildTestObj(params, tree);

        Iterator iter = tree.iterator();

        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());
        assertTrue(iter.hasNext());
        assertEquals(6, iter.next());
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Constructor Test 1")
    void testNodeCons1() {
        root = new TSTNode<Integer>(5);

        assertEquals(5, root.element);

    }

    @Test
    @Tag("score:3")
    @DisplayName("TSTNode Constructor Test 2")
    void testNodeCons2() {
        root = new TSTNode<Integer>(5);

        assertNull(root.left);
        assertNull(root.right);
        assertNull(root.mid);

    }


    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 1")
    void testNodeHeight1() {
        tree = new TST<>();
        tree.insert(12);
        tree.insert(6);
        tree.insert(1);
        tree.insert(10);
        tree.insert(12);
        tree.insert(15);
        tree.insert(13);
        tree.insert(16);

        assertEquals(2, tree.height());
    }


    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 2")
    void testNodeHeight2() {
        tree = new TST<>();
        tree.insert(12);
        tree.insert(12);
        tree.insert(12);
        tree.insert(12);

        assertEquals(3, tree.height());
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 3")
    void testNodeHeight3() {
        tree = new TST<>();
        tree.insert(15);
        tree.insert(10);
        tree.insert(5);
        tree.insert(4);
        tree.insert(-4);
        tree.insert(3);
        tree.insert(3);

        assertEquals(6, tree.height());
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 4")
    void testNodeHeight4() {
        tree = new TST<>();
        tree.insert(15);

        assertEquals(0, tree.height());
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 5")
    void testNodeHeight5() {
        tree = new TST<>();
        tree.insert(15);
        tree.insert(15);
        tree.insert(15);
        tree.insert(15);
        tree.insert(15);
        tree.insert(16);
        tree.insert(16);
        tree.insert(16);
        tree.insert(16);
        tree.insert(16);

        assertEquals(5, tree.height());

    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Max Test 1")
    void testNodeMax1() {
        tree = new TST<>();
        tree.insert(10);

        assertEquals(10, tree.root.findMax().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Max Test 2")
    void testNodeMax2() {
        tree = new TST<>();
        tree.insert(10);
        tree.insert(1);
        tree.insert(11);
        tree.insert(15);

        assertEquals(15, tree.root.findMax().element);
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Max Test 3")
    void testNodeMax3() {
        tree = new TST<>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(4);
        tree.insert(4);
        tree.insert(5);

        assertEquals(5, tree.root.findMax().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Max Test 4")
    void testNodeMax4() {
        tree = new TST<>();
        tree.insert(100);
        tree.insert(4);
        tree.insert(3);
        tree.insert(4);
        tree.insert(4);
        tree.insert(4);
        tree.insert(5);
        tree.insert(101);

        assertEquals(101, tree.root.findMax().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Min Test 1")
    void testNodeMin1() {
        tree = new TST<>();
        tree.insert(10);

        assertEquals(10, tree.root.findMin().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Min Test 2")
    void testNodeMin2() {
        tree = new TST<>();
        tree.insert(10);
        tree.insert(3);
        tree.insert(1);
        tree.insert(15);

        assertEquals(1, tree.root.findMin().element);
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Min Test 3")
    void testNodeMin3() {
        tree = new TST<>();
        tree.insert(7);
        tree.insert(6);
        tree.insert(5);
        tree.insert(4);
        tree.insert(4);
        tree.insert(4);
        tree.insert(3);

        assertEquals(3, tree.root.findMin().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Min Test 4")
    void testNodeMin4() {
        tree = new TST<>();
        tree.insert(2);
        tree.insert(20);
        tree.insert(30);
        tree.insert(41);
        tree.insert(43);
        tree.insert(45);
        tree.insert(-5);

        assertEquals(-5, tree.root.findMin().element);
    }

    boolean checkOrder(TST<Integer> o1) {
        int prev = Integer.MIN_VALUE;
        for (Integer x : o1) {
            if (prev > x) {
                return false;
            }
            prev = x;
        }

        return true;
    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Remove Test 1")
    void testRemoveTest1() {
        TST<Double> tree = new TST<>();
        tree.insert(5.1);
        tree.remove(5.1);
        assertNull(tree.root);
    }

    @Test
    @Tag("score:2")
    @DisplayName("TST Remove Test 3")
    void testRemoveTest3() {
        tree = new TST<>();
        tree.insert(5);
        tree.insert(4);
        tree.insert(3);
        tree.insert(2);

        tree.remove(2);

        assertTrue(checkOrder(tree));

        assertNotNull(tree.root);
        assertNotNull(tree.root.left);
        assertNotNull(tree.root.left.left);
        assertNull(tree.root.left.left.left);

    }

    @Test
    @Tag("score:2")
    @DisplayName("TST Remove Test 4")
    void testRemoveTest4() {
        tree = new TST<>();
        tree.insert(5);
        tree.insert(6);
        tree.insert(7);
        tree.insert(8);

        tree.remove(8);

        assertTrue(checkOrder(tree));

        assertNotNull(tree.root);
        assertNotNull(tree.root.right);
        assertNotNull(tree.root.right.right);
        assertNull(tree.root.right.right.right);

    }

    @Test
    @Tag("score:4")
    @DisplayName("TST Rebalance Test 0")
    void testRebalanceTest0(){

//        This is the scenario in the PDF
        tree = new TST<>();

        tree.insert(5);
        tree.insert(2);
        tree.insert(0);
        tree.insert(-1);
        tree.insert(-1);
        tree.insert(0);
        tree.insert(3);
        tree.insert(5);
        tree.insert(7);
        tree.insert(8);

        tree.rebalance();

        assertEquals(3, tree.root.element);
        assertEquals(0, tree.root.left.element);
        assertEquals(-1, tree.root.left.left.element);
        assertEquals(-1, tree.root.left.left.mid.element);
        assertEquals(0, tree.root.left.mid.element);
        assertEquals(2, tree.root.left.right.element);
        assertEquals(7, tree.root.right.element);
        assertEquals(5, tree.root.right.left.element);
        assertEquals(8, tree.root.right.right.element);
        assertEquals(5, tree.root.right.left.mid.element);
    }
}