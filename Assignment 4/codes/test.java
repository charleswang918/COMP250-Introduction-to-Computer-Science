
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<ERPriorityQueue.Patient> arr = new ArrayList<>();
        ERPriorityQueue.Patient p1 = new ERPriorityQueue.Patient("Bob", 2);
        ERPriorityQueue.Patient p2 = new ERPriorityQueue.Patient("Alice", 3);
        ERPriorityQueue.Patient p3 = new ERPriorityQueue.Patient("Mike", 1);
        arr.add(p1);
        arr.add(p2);
        arr.add(p3);
        System.out.println();
    }
}
