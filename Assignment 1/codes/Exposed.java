
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Exposed {

    Memory m;

    @BeforeEach
    void setUp(){
        m = new Memory(25);
    }

    @Test
    @Tag("score:2")
    @DisplayName("Memory Test 1")
    void testMemory1() {
        m = new Memory(35);

        assertEquals(m.idCount, 0);

    }

    @Test
    @Tag("score:3")
    @DisplayName("Memory Test 3")
    void testMemory3(){
        m = new Memory(6);
        m.put("hello");
        m.put("hi");
        assertEquals(m.idCount, 1);


    }

    @Test
    @Tag("score:2")
    @DisplayName("Get(int) Test 1")
    void testGetI1() {
        m.put("string");
        assertEquals("string", m.get(0));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Get(int) Test 2")
    void testGetI2() {
        m.put("linked");
        m.put("list");
        m.remove("list");
        assertNull(m.get(1));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Get(String) Test 1")
    void testGetS1() {
        m.put("banana");

        assertEquals(m.get("banana"), 0);

    }

    @Test
    @Tag("score:2")
    @DisplayName("Get(String) Test 1")
    void testGetS2() {
        m.put("banana");
        m.put("apple");

        assertEquals(m.get("apple"), 1);

    }

    @Test
    @Tag("score:10")
    @DisplayName("Put Test 1")
    void testPut1(){
        m = new Memory(10);

        m.put("kiwi");

        assertEquals(m.get("kiwi"), 0);

    }


    @Test
    @Tag("score:10")
    @DisplayName("Put Test 2")
    void testPut2(){
        m = new Memory(10);

        m.put("an");
        m.put("apple");

        assertEquals(m.get("an"), 0);
        assertEquals(m.get("apple"), 1);

    }

    @Test
    @Tag("score:2")
    @DisplayName("Remove(int) Test 1")
    void testRemoveI1() {
        m.put("string");
        assertEquals("string", m.remove(0));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Remove(int) Test 2")
    void testRemoveI2() {
        m.put("string");
        assertNull(m.remove(2));
    }

    @Test
    @Tag("score:5")
    @DisplayName("RemoveS Test 1")
    void testRemoveSE1(){
        m.put("kiwi");
        m.remove("kiwi");
        assertNull(m.get(0));
    }
}