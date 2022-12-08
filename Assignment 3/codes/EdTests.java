package assignments2021.a3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

class Solution_Insert<T extends Comparable<T>> extends TST<T>{
    TSTNode<T> root;

    public Solution_Insert(TSTNode<T> root) {
        this.root = root;
    }

    public Solution_Insert() {
        this.root = null;
    }

    public void insert(T element) {
        super.insert(element);
        this.root = super.root;
    }

    public boolean contains(T element){

        if (this.root == null){
            return false;
        }
        int cmpr = element.compareTo(this.root.element);
        if (cmpr > 0){
            // greater than current
            if (this.root.right != null){
                return new Solution_Insert(this.root.right).contains(element);
            }else{
                return false;
            }
        }else if (cmpr < 0){
            // less than current
            if (this.root.left != null){
                return new Solution_Insert(this.root.left).contains(element);
            }else{
                return false;
            }
        }else {
            // equal to current
            return true;
        }
    }




    @Override
    public String toString() {
        if (this.root == null)
            return "empty tree";
        // creates a buffer of 100 characters for the string representation
        StringBuilder buffer = new StringBuilder(100);
        // build the string
        stringfy(buffer, this.root,"", "");
        return buffer.toString();
    }

    /**
     * Build a string representation of the tertiary tree.
     * @param buffer String buffer
     * @param node Root node
     * @param nodePrefix The string prefix to add before the node's data (connection line from the parent)
     * @param childrenPrefix The string prefix for the children nodes (connection line to the children)
     */
    private void stringfy(StringBuilder buffer, TSTNode<T> node, String nodePrefix, String childrenPrefix) {
        buffer.append(nodePrefix);
        buffer.append(node.element);
        buffer.append('\n');
        if (node.left != null)
            stringfy(buffer, node.left,childrenPrefix + "|~~ ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|~~ null\n");
        if (node.mid != null)
            stringfy(buffer, node.mid,childrenPrefix + "|-- ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|-- null\n");
        if (node.right != null)
            stringfy(buffer, node.right,childrenPrefix + "|__ ", childrenPrefix + "    ");
        else
            buffer.append(childrenPrefix + "|__ null\n");
    }

    private TSTNode<T> remove(T element, TSTNode<T> root){
        if (root == null)
            return null;

        int cmpr = element.compareTo(root.element);
        if (cmpr > 0){
            // greater than current
            if (root.right != null)
                root.right = remove(element, root.right);
        }else if (cmpr < 0){
            // less than current
            if (root.left != null)
                root.left = remove(element, root.left);
        }else {
            // equal to current
            if (root.mid != null){
                // remove last mid if exist
                root.mid = remove(element, root.mid);
            }else if (root.left != null){
                // remove root and promote max of left subtree to root
                // remember to move all the middle children of left max
                TSTNode<T> leftMax = root.left.findMax();
                root.element = leftMax.element;
                root.mid = leftMax.mid;
                leftMax.mid = null;
                root.left = remove(root.element, root.left);
            }else {
                root = root.right;
            }
        }
        return root;
    }

}



class Solution_TST<T extends Comparable<T>> implements Iterable<T>{

    Solution_TSTNode<T> root;

    public Solution_TST(Solution_TSTNode<T> root) {
        this.root = root;
    }

    public Solution_TST() {
        this.root = null;
    }

    public void insert(T element){
        if (this.root == null){
            this.root = new Solution_TSTNode<>(element);
        }
        else {
            this.root.add(element);
        }
    }

    public void remove(T element){
        this.root = remove(element, this.root);
    }

    public boolean contains(T element){
        return this.root != null && this.root.contains(element);
    }

    public void rebalance(){
        Iterator<T> iter = iterator();
        ArrayList<T> arrList = new ArrayList<>();
        ArrayList<T> dup = new ArrayList<>();
        while (iter.hasNext()){
            T element = iter.next();
            // remove duplicates
            // alternatively can keep duplicates in another list and insert afterward
            if (!arrList.contains(element))
                arrList.add(element);
            else
                dup.add(element);
        }
        this.root = insert(arrList);
        // add in duplicates
        for(T e : dup){
            insert(e);
        }
    }

    private Solution_TSTNode<T> insert(List<T> list){
        int len = list.size();
        if (len == 0)
            return null;
        int mid = len/2;
        Solution_TSTNode root = new Solution_TSTNode<>(list.get(mid));
        root.left = insert(list.subList(0,mid));
        root.right = insert(list.subList(mid+1,list.size()));
        return root;
    }

    public int height(){
        if (this.root != null){
            return this.root.height();
        }else{
            return -1;
        }
    }

    public void inorderPrintAsList(){
        String buffer = "[";
        for (T element: this) {
            buffer += element + ", ";
        }
        int len = buffer.length();
        if (len > 1)
            buffer = buffer.substring(0,len-2);
        buffer += "]";
        System.out.println(buffer);
    }

    @Override
    public String toString() {
        if (this.root == null)
            return "empty tree";
        // creates a buffer of 100 characters for the string representation
        StringBuilder buffer = new StringBuilder(100);
        // build the string
        stringfy(buffer, this.root,"", "");
        return buffer.toString();
    }

    /**
     * Build a string representation of the tertiary tree.
     * @param buffer String buffer
     * @param node Root node
     * @param nodePrefix The string prefix to add before the node's data (connection line from the parent)
     * @param childrenPrefix The string prefix for the children nodes (connection line to the children)
     */
    private void stringfy(StringBuilder buffer, Solution_TSTNode<T> node, String nodePrefix, String childrenPrefix) {
        buffer.append(nodePrefix);
        buffer.append(node.element);
        buffer.append('\n');
        if (node.left != null)
            stringfy(buffer, node.left,childrenPrefix + "|~~ ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|~~ null\n");
        if (node.mid != null)
            stringfy(buffer, node.mid,childrenPrefix + "|-- ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|-- null\n");
        if (node.right != null)
            stringfy(buffer, node.right,childrenPrefix + "|__ ", childrenPrefix + "    ");
        else
            buffer.append(childrenPrefix + "|__ null\n");
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        return new Solution_TSTIterator(this.root);
    }

    private Solution_TSTNode<T> remove(T element, Solution_TSTNode<T> root){
        if (root == null)
            return null;

        int cmpr = element.compareTo(root.element);
        if (cmpr > 0){
            // greater than current
            if (root.right != null)
                root.right = remove(element, root.right);
        }else if (cmpr < 0){
            // less than current
            if (root.left != null)
                root.left = remove(element, root.left);
        }else {
            // equal to current
            if (root.mid != null){
                // remove last mid if exist
                root.mid = remove(element, root.mid);
            }else if (root.left != null && root.right != null){
                // remove root and promote max of left subtree to root
                // remember to move all the middle children of left max
                Solution_TSTNode<T> leftMax = root.left.findMax();
                root.element = leftMax.element;
                root.mid = leftMax.mid;
                leftMax.mid = null;
                root.left = remove(root.element, root.left);
            }else {
                root = (root.left == null) ? root.right : root.left;
            }
        }
        return root;
    }
}



class Solution_Rebalance<T extends Comparable<T>> extends TST<T> {
    TSTNode<T> root;

    public Solution_Rebalance(TSTNode<T> root) {
        this.root = root;
    }

    public Solution_Rebalance() {
        this.root = null;
    }

    public void insert(T element){
        this.root = super.root;
        if (this.root == null){
            this.root = new TSTNode<>(element);
            super.root = this.root;
        }
        else {
//            this.root.add(element);
            this.add(element);
        }
    }

    public void remove(T element){
        this.root = remove(element, this.root);
        super.root = this.root;
    }

    public void rebalance(){
        super.rebalance();
        this.root = super.root;
    }


    private void add(T element){
        int cmpr = element.compareTo(this.root.element);
        if (cmpr > 0){
            // greater than current
            if (this.root.right != null){
                new Solution_Rebalance<>(this.root.right).add(element);
            }else{
                this.root.right = new TSTNode<>(element);
            }
        }else if (cmpr < 0){
            // less than current
            if (this.root.left != null){
                new Solution_Rebalance<>(this.root.left).add(element);
            }else{
                this.root.left = new TSTNode<>(element);
            }
        }else {
            // equal to current
            if (this.root.mid != null){
                new Solution_Rebalance<>(this.root.mid).add(element);
            }else{
                this.root.mid = new TSTNode<>(element);
            }
        };

    }

    public boolean contains(T element){

        if (this.root == null){
            return false;
        }
        int cmpr = element.compareTo(this.root.element);
        if (cmpr > 0){
            // greater than current
            if (this.root.right != null){
                return new Solution_Rebalance<>(this.root.right).contains(element);
            }else{
                return false;
            }
        }else if (cmpr < 0){
            // less than current
            if (this.root.left != null){
                return new Solution_Rebalance<>(this.root.left).contains(element);
            }else{
                return false;
            }
        }else {
            // equal to current
            return true;
        }
    }




    @Override
    public String toString() {
        if (this.root == null)
            return "empty tree";
        // creates a buffer of 100 characters for the string representation
        StringBuilder buffer = new StringBuilder(100);
        // build the string
        stringfy(buffer, this.root,"", "");
        return buffer.toString();
    }

    /**
     * Build a string representation of the tertiary tree.
     * @param buffer String buffer
     * @param node Root node
     * @param nodePrefix The string prefix to add before the node's data (connection line from the parent)
     * @param childrenPrefix The string prefix for the children nodes (connection line to the children)
     */
    private void stringfy(StringBuilder buffer, TSTNode<T> node, String nodePrefix, String childrenPrefix) {
        buffer.append(nodePrefix);
        buffer.append(node.element);
        buffer.append('\n');
        if (node.left != null)
            stringfy(buffer, node.left,childrenPrefix + "|~~ ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|~~ null\n");
        if (node.mid != null)
            stringfy(buffer, node.mid,childrenPrefix + "|-- ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|-- null\n");
        if (node.right != null)
            stringfy(buffer, node.right,childrenPrefix + "|__ ", childrenPrefix + "    ");
        else
            buffer.append(childrenPrefix + "|__ null\n");
    }

    private TSTNode<T> remove(T element, TSTNode<T> root){
        if (root == null)
            return null;

        int cmpr = element.compareTo(root.element);
        if (cmpr > 0){
            // greater than current
            if (root.right != null)
                root.right = remove(element, root.right);
        }else if (cmpr < 0){
            // less than current
            if (root.left != null)
                root.left = remove(element, root.left);
        }else {
            // equal to current
            if (root.mid != null){
                // remove last mid if exist
                root.mid = remove(element, root.mid);
            }else if (root.left != null){
                // remove root and promote max of left subtree to root
                // remember to move all the middle children of left max
                TSTNode<T> leftMax = root.left.findMax();
                root.element = leftMax.element;
                root.mid = leftMax.mid;
                leftMax.mid = null;
                root.left = remove(root.element, root.left);
            }else {
                root = root.right;
            }
        }
        return root;
    }
}



class Solution_Remove <T extends Comparable<T>> extends TST<T>{

    TSTNode<T> root;

    public Solution_Remove(TSTNode<T> root) {
        this.root = root;
    }

    public  Solution_Remove() {
        this.root = null;
    }

    public void insert(T element){
        this.root = super.root;
        if (this.root == null){
            this.root = new TSTNode<>(element);
            super.root = this.root;
        }
        else {
//            this.root.add(element);
            this.add(element);
        }
    }

    private void add(T element){
        int cmpr = element.compareTo(this.root.element);
        if (cmpr > 0){
            // greater than current
            if (this.root.right != null){
                new Solution_Remove<>(this.root.right).add(element);
            }else{
                this.root.right = new TSTNode<>(element);
            }
        }else if (cmpr < 0){
            // less than current
            if (this.root.left != null){
                new Solution_Remove<>(this.root.left).add(element);
            }else{
                this.root.left = new TSTNode<>(element);
            }
        }else {
            // equal to current
            if (this.root.mid != null){
                new Solution_Remove<>(this.root.mid).add(element);
            }else{
                this.root.mid = new TSTNode<>(element);
            }
        };

    }

    public void remove(T element){
        super.remove(element);
        this.root = super.root;
    }

    public void rebalance(){
        Iterator<T> iter = iterator();
        ArrayList<T> arrList = new ArrayList<>();
        ArrayList<T> dup = new ArrayList<>();
        while (iter.hasNext()){
            T element = iter.next();
            // remove duplicates
            // alternatively can keep duplicates in another list and insert afterward
            if (!arrList.contains(element))
                arrList.add(element);
            else
                dup.add(element);
        }
        this.root = insert(arrList);
        // add in duplicates
        for(T e : dup){
            insert(e);
        }
    }

    private TSTNode<T> insert(List<T> list){
        int len = list.size();
        if (len == 0)
            return null;
        int mid = len/2;
        TSTNode root = new TSTNode<>(list.get(mid));
        root.left = insert(list.subList(0,mid));
        root.right = insert(list.subList(mid+1,list.size()));
        return root;
    }

    public int height(){
        if (this.root != null){
            return this.root.height();
        }else{
            return -1;
        }
    }

    public void inorderPrintAsList(){
//        if (this.root != null){
//            this.root.print();
//            System.out.println();
//        }
        String buffer = "[";
        for (T element: this) {
            buffer += element + ", ";
        }
        int len = buffer.length();
        if (len > 1)
            buffer = buffer.substring(0,len-2);
        buffer += "]";
        System.out.println(buffer);
    }

    @Override
    public String toString() {
        if (this.root == null)
            return "empty tree";
        // creates a buffer of 100 characters for the string representation
        StringBuilder buffer = new StringBuilder(100);
        // build the string
        stringfy(buffer, this.root,"", "");
        return buffer.toString();
    }

    /**
     * Build a string representation of the tertiary tree.
     * @param buffer String buffer
     * @param node Root node
     * @param nodePrefix The string prefix to add before the node's data (connection line from the parent)
     * @param childrenPrefix The string prefix for the children nodes (connection line to the children)
     */
    private void stringfy(StringBuilder buffer, TSTNode<T> node, String nodePrefix, String childrenPrefix) {
        buffer.append(nodePrefix);
        buffer.append(node.element);
        buffer.append('\n');
        if (node.left != null)
            stringfy(buffer, node.left,childrenPrefix + "|~~ ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|~~ null\n");
        if (node.mid != null)
            stringfy(buffer, node.mid,childrenPrefix + "|-- ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|-- null\n");
        if (node.right != null)
            stringfy(buffer, node.right,childrenPrefix + "|__ ", childrenPrefix + "    ");
        else
            buffer.append(childrenPrefix + "|__ null\n");
    }

    private TSTNode<T> remove(T element, TSTNode<T> root){
        if (root == null)
            return null;

        int cmpr = element.compareTo(root.element);
        if (cmpr > 0){
            // greater than current
            if (root.right != null)
                root.right = remove(element, root.right);
        }else if (cmpr < 0){
            // less than current
            if (root.left != null)
                root.left = remove(element, root.left);
        }else {
            // equal to current
            if (root.mid != null){
                // remove last mid if exist
                root.mid = remove(element, root.mid);
            }else if (root.left != null){
                // remove root and promote max of left subtree to root
                // remember to move all the middle children of left max
                TSTNode<T> leftMax = root.left.findMax();
                root.element = leftMax.element;
                root.mid = leftMax.mid;
                leftMax.mid = null;
                root.left = remove(root.element, root.left);
            }else {
                root = root.right;
            }
        }
        return root;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator iterator() {
        // TODO: implement the iterator method here
        return new SolTSTIterator(this.root);
    }
}

class SolTSTIterator<T extends Comparable<T>> implements Iterator<T> {
    private LinkedList<T> queue;

    public SolTSTIterator(TSTNode<T> root) {
        this.queue = new LinkedList<>();
        inOrder(root);
    }
    /**
     * Left-Root-Middle-Right inorder traversal
     * @param node
     */
    private void inOrder(TSTNode<T> node){
        if (node != null){
            inOrder(node.left);
            queue.addLast(node.element);
            inOrder(node.mid);
            inOrder(node.right);
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
     * #next} would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     *
     * @throws NoSuchElementException
     *         if the iteration has no more elements
     */
    @Override
    public T next() {
        return queue.removeFirst();
    }
}


class Solution_Contains<T extends Comparable<T>> extends TST<T>{

    TSTNode<T> root;

    public Solution_Contains(TSTNode<T> root) {
        this.root = root;
    }

    public Solution_Contains() {
        this.root = null;
    }

    public void insert(T element){
        if (this.root == null){
            this.root = new TSTNode<>(element);
            super.root = this.root;
        }
        else {
            this.add(element);
        }
    }

    private void add(T element){
        int cmpr = element.compareTo(this.root.element);
        if (cmpr > 0){
            // greater than current
            if (this.root.right != null){
                new Solution_Contains<>(this.root.right).add(element);
            }else{
                this.root.right = new TSTNode<>(element);
            }
        }else if (cmpr < 0){
            // less than current
            if (this.root.left != null){
                new Solution_Contains<>(this.root.left).add(element);
            }else{
                this.root.left = new TSTNode<>(element);
            }
        }else {
            // equal to current
            if (this.root.mid != null){
                new Solution_Contains<>(this.root.mid).add(element);
            }else{
                this.root.mid = new TSTNode<>(element);
            }
        };

    }

    public void remove(T element){
        this.root = remove(element, this.root);
        super.root = this.root;
    }

    private TSTNode<T> insert(List<T> list){
        int len = list.size();
        if (len == 0)
            return null;
        int mid = len/2;
        TSTNode root = new TSTNode<>(list.get(mid));
        root.left = insert(list.subList(0,mid));
        root.right = insert(list.subList(mid+1,list.size()));
        return root;
    }

    public void inorderPrintAsList(){
//        if (this.root != null){
//            this.root.print();
//            System.out.println();
//        }
        String buffer = "[";
        for (T element: this) {
            buffer += element + ", ";
        }
        int len = buffer.length();
        if (len > 1)
            buffer = buffer.substring(0,len-2);
        buffer += "]";
        System.out.println(buffer);
    }

    @Override
    public String toString() {
        if (this.root == null)
            return "empty tree";
        // creates a buffer of 100 characters for the string representation
        StringBuilder buffer = new StringBuilder(100);
        // build the string
        stringfy(buffer, this.root,"", "");
        return buffer.toString();
    }

    /**
     * Build a string representation of the tertiary tree.
     * @param buffer String buffer
     * @param node Root node
     * @param nodePrefix The string prefix to add before the node's data (connection line from the parent)
     * @param childrenPrefix The string prefix for the children nodes (connection line to the children)
     */
    private void stringfy(StringBuilder buffer, TSTNode<T> node, String nodePrefix, String childrenPrefix) {
        buffer.append(nodePrefix);
        buffer.append(node.element);
        buffer.append('\n');
        if (node.left != null)
            stringfy(buffer, node.left,childrenPrefix + "|~~ ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|~~ null\n");
        if (node.mid != null)
            stringfy(buffer, node.mid,childrenPrefix + "|-- ", childrenPrefix + "│   ");
        else
            buffer.append(childrenPrefix + "|-- null\n");
        if (node.right != null)
            stringfy(buffer, node.right,childrenPrefix + "|__ ", childrenPrefix + "    ");
        else
            buffer.append(childrenPrefix + "|__ null\n");
    }

    private TSTNode<T> remove(T element, TSTNode<T> root){
        if (root == null)
            return null;

        int cmpr = element.compareTo(root.element);
        if (cmpr > 0){
            // greater than current
            if (root.right != null)
                root.right = remove(element, root.right);
        }else if (cmpr < 0){
            // less than current
            if (root.left != null)
                root.left = remove(element, root.left);
        }else {
            // equal to current
            if (root.mid != null){
                // remove last mid if exist
                root.mid = remove(element, root.mid);
            }else if (root.left != null){
                // remove root and promote max of left subtree to root
                // remember to move all the middle children of left max
                TSTNode<T> leftMax = root.left.findMax();
                root.element = leftMax.element;
                root.mid = leftMax.mid;
                leftMax.mid = null;
                root.left = remove(root.element, root.left);
            }else {
                root = root.right;
            }
        }
        return root;
    }

}



class Solution_NodeHeight<T extends Comparable<T>> extends TSTNode<T>{

    T element;     	            // The data in the node
    Solution_NodeHeight<T> left;   		// left child
    Solution_NodeHeight<T> mid;   		    // middle child
    Solution_NodeHeight<T> right;  		// right child

    // Constructors
    Solution_NodeHeight( T theElement )
    {
        this( theElement, null, null, null );
    }

    Solution_NodeHeight(T theElement, Solution_NodeHeight<T> lt, Solution_NodeHeight<T> eq , Solution_NodeHeight<T> gt )
    {
        super(theElement);
        element = super.element;
        left = (Solution_NodeHeight<T>) super.left;
        mid = (Solution_NodeHeight<T>) super.mid;
        right = (Solution_NodeHeight<T>) super.right;
    }

    void add(T element){
        int cmpr = element.compareTo(this.element);
        if (cmpr > 0){
            // greater than current
            if (this.right != null){
                this.right.add(element);
            }else{
                this.right = new Solution_NodeHeight<>(element);
                super.right = this.right;
            }
        }else if (cmpr < 0){
            // less than current
            if (this.left != null){
                this.left.add(element);
            }else{
                this.left = new Solution_NodeHeight<>(element);
                super.left = this.left;
            }
        }else {
            // equal to current
            if (this.mid != null){
                this.mid.add(element);
            }else{
                this.mid = new Solution_NodeHeight<>(element);
                super.mid = this.mid;
            }
        }
    }

    TSTNode<T> findMax(){
        if (this.right == null){
            return this;
        }else {
            return this.right.findMax();
        }
    }

    TSTNode<T> findMin(){
        if (this.left == null){
            return this;
        }else {
            return this.left.findMin();
        }
    }

    /**
     * Left-Root-Middle-Right
     */
    //    void print(){
    //        if (this.left != null){
    //            this.left.print();
    //        }
    //        System.out.print(this.element + " ");
    //        if (this.mid != null){
    //            this.mid.print();
    //        }
    //        if (this.right != null){
    //            this.right.print();
    //        }
    //    }

    boolean contains(T element){
        int cmpr = element.compareTo(this.element);
        if (cmpr > 0){
            // greater than current
            if (this.right != null){
                return this.right.contains(element);
            }else{
                return false;
            }
        }else if (cmpr < 0){
            // less than current
            if (this.left != null){
                return this.left.contains(element);
            }else{
                return false;
            }
        }else {
            // equal to current
            return true;
        }
    }


}



class Solution_NodeMaxMin<T extends Comparable<T>> extends TSTNode<T>{

    T element;     	            // The data in the node
    Solution_NodeMaxMin<T> left;   		// left child
    Solution_NodeMaxMin<T> mid;   		    // middle child
    Solution_NodeMaxMin<T> right;  		// right child

    // Constructors
    Solution_NodeMaxMin( T theElement )
    {
        this( theElement, null, null, null );
    }

    Solution_NodeMaxMin(T theElement, Solution_NodeMaxMin<T> lt, Solution_NodeMaxMin<T> eq , Solution_NodeMaxMin<T> gt )
    {
        super(theElement);
        element = super.element;
        left = (Solution_NodeMaxMin<T>) super.left;
        mid = (Solution_NodeMaxMin<T>) super.mid;
        right = (Solution_NodeMaxMin<T>) super.right;
    }

    void add(T element){
        int cmpr = element.compareTo(this.element);
        if (cmpr > 0){
            // greater than current
            if (this.right != null){
                this.right.add(element);
            }else{
                this.right = new Solution_NodeMaxMin<>(element);
                super.right = this.right;
            }
        }else if (cmpr < 0){
            // less than current
            if (this.left != null){
                this.left.add(element);
            }else{
                this.left = new Solution_NodeMaxMin<>(element);
                super.left = this.left;
            }
        }else {
            // equal to current
            if (this.mid != null){
                this.mid.add(element);
            }else{
                this.mid = new Solution_NodeMaxMin<>(element);
                super.mid = this.mid;
            }
        }
    }


    /**
     * Left-Root-Middle-Right
     */
    //    void print(){
    //        if (this.left != null){
    //            this.left.print();
    //        }
    //        System.out.print(this.element + " ");
    //        if (this.mid != null){
    //            this.mid.print();
    //        }
    //        if (this.right != null){
    //            this.right.print();
    //        }
    //    }

    boolean contains(T element){
        int cmpr = element.compareTo(this.element);
        if (cmpr > 0){
            // greater than current
            if (this.right != null){
                return this.right.contains(element);
            }else{
                return false;
            }
        }else if (cmpr < 0){
            // less than current
            if (this.left != null){
                return this.left.contains(element);
            }else{
                return false;
            }
        }else {
            // equal to current
            return true;
        }
    }

    int height(){
        // iterative bfs
        int acc = 0;
        LinkedList<Solution_NodeMaxMin<T>> queue = new LinkedList<>();
        queue.add(this.left);
        queue.add(this.mid);
        queue.add(this.right);

        while (!queue.isEmpty()){
            int len = queue.size();
            for (int i = 0; i < len; i++) {
                Solution_NodeMaxMin<T> node = queue.removeFirst();
                if (node != null){
                    queue.add(node.left);
                    queue.add(node.mid);
                    queue.add(node.right);
                }
            }
            acc++;
        }
        return acc - 1;
    }

}



class Solution_TSTIterator<T extends Comparable<T>> implements Iterator<T> {
    private LinkedList<T> queue;

    public Solution_TSTIterator(Solution_TSTNode<T> root) {
        this.queue = new LinkedList<>();
        inOrder(root);
    }
    /**
     * Left-Root-Middle-Right inorder traversal
     * @param node
     */
    private void inOrder(Solution_TSTNode<T> node){
        if (node != null){
            inOrder(node.left);
            queue.addLast(node.element);
            inOrder(node.mid);
            inOrder(node.right);
        }
    }

    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link
     * #next} would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     *
     * @throws NoSuchElementException
     *         if the iteration has no more elements
     */
    @Override
    public T next() {
        return queue.removeFirst();
    }
}



class Solution_Iterator{

    public static <T extends Comparable<T>> void insert(TSTNode<T> root, T element){
        int cmpr = element.compareTo(root.element);
        if (cmpr > 0){
            // greater than current
            if (root.right != null){
                insert(root.right, element);
            }else{
                root.right = new TSTNode<>(element);
            }
        }else if (cmpr < 0){
            // less than current
            if (root.left != null){
                insert(root.left, element);
            }else{
                root.left = new TSTNode<>(element);
            }
        }else {
            // equal to current
            if (root.mid != null){
                insert(root.mid, element);
            }else{
                root.mid = new TSTNode<>(element);
            }
        }
    }

    public static <T extends Comparable<T>> void buildTestObj(List<T> l, TSTNode<T> root){
        for(T x : l){
            insert(root, x);
        }
    }
}



class Solution_TSTNode<T extends Comparable<T>>{
    T element;     	            // The data in the node
    Solution_TSTNode<T> left;   		// left child
    Solution_TSTNode<T> mid;   		    // middle child
    Solution_TSTNode<T> right;  		// right child

    // Constructors
    Solution_TSTNode(T theElement )
    {
        this( theElement, null, null, null );
    }

    Solution_TSTNode(T theElement, Solution_TSTNode<T> lt, Solution_TSTNode<T> eq , Solution_TSTNode<T> gt )
    {
        element = theElement;
        left     = lt;
        mid      = eq;
        right    = gt;
    }

    void add(T element){
        int cmpr = element.compareTo(this.element);
        if (cmpr > 0){
            // greater than current
            if (this.right != null){
                this.right.add(element);
            }else{
                this.right = new Solution_TSTNode<>(element);
            }
        }else if (cmpr < 0){
            // less than current
            if (this.left != null){
                this.left.add(element);
            }else{
                this.left = new Solution_TSTNode<>(element);
            }
        }else {
            // equal to current
            if (this.mid != null){
                this.mid.add(element);
            }else{
                this.mid = new Solution_TSTNode<>(element);
            }
        }
    }


    Solution_TSTNode<T> findMax(){
        if (this.right == null){
            return this;
        }else {
            return this.right.findMax();
        }
    }

    Solution_TSTNode<T> findMin(){
        if (this.left == null){
            return this;
        }else {
            return this.left.findMin();
        }
    }

    boolean contains(T element){
        int cmpr = element.compareTo(this.element);
        if (cmpr > 0){
            // greater than current
            if (this.right != null){
                return this.right.contains(element);
            }else{
                return false;
            }
        }else if (cmpr < 0){
            // less than current
            if (this.left != null){
                return this.left.contains(element);
            }else{
                return false;
            }
        }else {
            // equal to current
            return true;
        }
    }

    int height(){
        int leftHeight = -1, midHeight = -1, rightHeight = -1;
        if (this.left != null)
            leftHeight = this.left.height();
        if (this.mid != null)
            midHeight = this.mid.height();
        if (this.right != null)
            rightHeight = this.right.height();
        return Math.max(leftHeight, Math.max(midHeight, rightHeight)) + 1;
    }
}


public class EdTests {


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

    void testRemoveTest1Aux(TST<Double> remove) {
        remove.insert(5.1);
        remove.remove(5.1);
        assertNull(remove.root);
    }

    void testRemoveTest2Aux(TST<Double> remove) {
        remove.insert(5.2);
        remove.remove(4.32);
        assertNotNull(remove.root);
        assertEquals(5.2, remove.root.element);
    }

    void testRemoveTest3Aux(TST<Integer> remove) {
        remove.insert(5);
        remove.insert(4);
        remove.insert(3);
        remove.insert(2);

        remove.remove(2);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertNotNull(remove.root.left);
        assertNotNull(remove.root.left.left);
        assertNull(remove.root.left.left.left);

    }



    void testRemoveTest4Aux(TST<Integer> remove) {
        remove.insert(5);
        remove.insert(6);
        remove.insert(7);
        remove.insert(8);

        remove.remove(8);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertNotNull(remove.root.right);
        assertNotNull(remove.root.right.right);
        assertNull(remove.root.right.right.right);

    }

    void testRemoveTest5Aux(TST<Integer> remove) {
        remove.insert(5);
        remove.insert(9);
        remove.insert(8);
        remove.insert(7);
        remove.insert(6);

        remove.remove(6);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertEquals(5, remove.root.element);
        assertNotNull(remove.root.right);
        assertEquals(9, remove.root.right.element);
        assertNotNull(remove.root.right.left);
        assertEquals(8, remove.root.right.left.element);
        assertNotNull(remove.root.right.left.left);
        assertEquals(7, remove.root.right.left.left.element);
        assertNull(remove.root.right.left.left.left);
    }

    void testRemoveTest6Aux(TST<Integer> remove) {
        remove.insert(5);
        remove.insert(9);
        remove.insert(8);
        remove.insert(7);
        remove.insert(6);

        remove.remove(8);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertEquals(5, remove.root.element);
        assertNotNull(remove.root.right);
        assertEquals(9, remove.root.right.element);
        assertNotNull(remove.root.right.left);
        assertEquals(7, remove.root.right.left.element);
        assertNotNull(remove.root.right.left.left);
        assertEquals(6, remove.root.right.left.left.element);
        assertNull(remove.root.right.left.left.left);
    }

    void testRemoveTest7Aux(TST<Integer> remove) {
        remove.insert(15);
        remove.insert(9);
        remove.insert(10);
        remove.insert(-5);
        remove.insert(9);
        remove.insert(-5);
        remove.insert(8);

        remove.remove(9);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertEquals(15, remove.root.element);

        assertNotNull(remove.root.left);
        assertEquals(9, remove.root.left.element);
        assertNull(remove.root.left.mid);
        assertNotNull(remove.root.left.right);
        assertEquals(10, remove.root.left.right.element);
    }

    void testRemoveTest8Aux(TST<Integer> remove) {
        remove.insert(15);
        remove.insert(9);
        remove.insert(13);
        remove.insert(-5);
        remove.insert(-5);
        remove.insert(7);
        remove.insert(8);

        remove.remove(9);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertEquals(15, remove.root.element);

        assertNotNull(remove.root.left);
        assertEquals(8, remove.root.left.element);
        assertNull(remove.root.left.mid);
        assertNotNull(remove.root.left.right);
        assertEquals(13, remove.root.left.right.element);

        assertNotNull(remove.root.left.left);
        assertEquals(-5, remove.root.left.left.element);
        assertNotNull(remove.root.left.left.right);
        assertEquals(7, remove.root.left.left.right.element);

        assertNull(remove.root.left.left.right.right);
    }

    void testRemoveTest9Aux(TST<Integer> remove) {
        remove.insert(15);
        remove.insert(25);
        remove.insert(22);
        remove.insert(18);
        remove.insert(18);
        remove.insert(20);
        remove.insert(20);

        remove.remove(22);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertEquals(15, remove.root.element);

        assertNotNull(remove.root.right);
        assertEquals(25, remove.root.right.element);
        assertNotNull(remove.root.right.left);
        assertEquals(18, remove.root.right.left.element);

        assertNotNull(remove.root.right.left.mid);
        assertEquals(18, remove.root.right.left.mid.element);

        assertNotNull(remove.root.right.left.right);
        assertEquals(20, remove.root.right.left.right.element);
    }

    void testRemoveTest10Aux(TST<Integer> remove) {
        remove.insert(15);
        remove.insert(9);
        remove.insert(12);
        remove.insert(12);
        remove.insert(10);
        remove.insert(-5);
        remove.insert(9);
        remove.insert(-5);
        remove.insert(8);
        remove.insert(7);
        remove.insert(20);
        remove.insert(21);
        remove.insert(18);
        remove.insert(17);
        remove.insert(19);

        remove.remove(15);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertEquals(12, remove.root.element);

        assertNotNull(remove.root.mid);
        assertEquals(12, remove.root.mid.element);

        assertNotNull(remove.root.left);
        assertEquals(9, remove.root.left.element);

        assertNotNull(remove.root.left.mid);
        assertEquals(9, remove.root.left.mid.element);

        assertNotNull(remove.root.left.right);
        assertEquals(10, remove.root.left.right.element);

        assertNotNull(remove.root.right);
        assertEquals(20, remove.root.right.element);


    }

    void testRemoveTest11Aux(TST<Integer> remove) {
        remove.insert(15);
        remove.insert(9);
        remove.insert(10);
        remove.insert(-5);
        remove.insert(9);
        remove.insert(-5);
        remove.insert(8);
        remove.insert(7);
        remove.insert(20);
        remove.insert(21);
        remove.insert(18);
        remove.insert(17);
        remove.insert(19);

        remove.remove(15);
        remove.remove(20);

        assertTrue(checkOrder(remove));

        assertNotNull(remove.root);
        assertEquals(10, remove.root.element);

        assertNotNull(remove.root.left);
        assertEquals(9, remove.root.left.element);

        assertNotNull(remove.root.left.mid);
        assertEquals(9, remove.root.left.mid.element);

        assertNull(remove.root.left.right);

        assertNotNull(remove.root.right);
        assertEquals(19, remove.root.right.element);

        assertNotNull(remove.root.right.right);
        assertEquals(21, remove.root.right.right.element);

        assertNotNull(remove.root.right.left);
        assertEquals(18, remove.root.right.left.element);

    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Remove Test 1")
    void testRemoveTest1() {
        Solution_Remove<Double> isolate = new Solution_Remove<>();
        try {
            testRemoveTest1Aux(isolate);
        } catch (AssertionError e) {
            TST<Double> student = new TST<>();
            testRemoveTest1Aux(student);
        }
    }

    @Test
    @Tag("score:1")
    @Tag("private")
    @DisplayName("TST Remove Test 2")
    void testRemoveTest2() {
        Solution_Remove<Double> isolate = new Solution_Remove<>();
        try {
            testRemoveTest2Aux(isolate);
        } catch (AssertionError e) {
            TST<Double> student = new TST<>();
            testRemoveTest2Aux(student);
        }
    }

    @Test
    @Tag("score:2")
    @DisplayName("TST Remove Test 3")
    void testRemoveTest3() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest3Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest3Aux(student);
        }

    }

    @Test
    @Tag("score:2")
    @DisplayName("TST Remove Test 4")
    void testRemoveTest4() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest4Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest4Aux(student);
        }

    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Remove Test 5")
    void testRemoveTest5() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest5Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest5Aux(student);
        }
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Remove Test 6")
    void testRemoveTest6() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest6Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest6Aux(student);
        }
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Remove Test 7")
    void testRemoveTest7() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest7Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest7Aux(student);
        }
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Remove Test 8")
    void testRemoveTest8() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest8Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest8Aux(student);
        }
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Remove Test 9")
    void testRemoveTest9() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest9Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest9Aux(student);
        }
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Remove Test 10")
    void testRemoveTest10() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest10Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest10Aux(student);
        }

    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Remove Test 11")
    void testRemoveTest11() {
        Solution_Remove<Integer> isolate = new Solution_Remove<>();
        try {
            testRemoveTest11Aux(isolate);
        } catch (AssertionError e) {
            TST<Integer> student = new TST<>();
            testRemoveTest11Aux(student);
        }

    }



    TSTNode<Integer> iter_root;

    @Test
    @Tag("score:2")
    @DisplayName("TSTIterator Test 1")
    void testIterator1(){
        iter_root = new TSTNode<>(5);
        List<Integer> params =  new LinkedList<Integer>();

        params.add(5);

        TST<Integer> tree = new TST<>();
        tree.root = iter_root;

        int i = 0;
        for(Integer element : tree){
            assertEquals(params.get(i++), element);
        }

        assertEquals(params.size(), i);

    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTIterator Test 2")
    void testIterator2(){
        iter_root = new TSTNode<>(25);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(15,25,22,18,18,20,20));

        Solution_Iterator aux = new Solution_Iterator();
        aux.buildTestObj(params, iter_root);

        params.add(25);
        params.sort(Comparator.naturalOrder());

        TST<Integer> tree = new TST<>();
        tree.root = iter_root;

        int i =0;
        for(Integer element : tree){
            assertEquals(params.get(i++), element);
        }

        assertEquals(params.size(), i);
    }


    @Test
    @Tag("score:2")
    @DisplayName("TSTIterator Test 3")
    void testIterator3(){
        iter_root = new TSTNode<>(1);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(2,3,4,5,6,7));

        Solution_Iterator aux = new Solution_Iterator();
        aux.buildTestObj(params, iter_root);

        params.add(1);
        params.sort(Comparator.naturalOrder());

        TST<Integer> tree = new TST<>();
        tree.root = iter_root;

        int i =0;
        for(Integer element : tree){
            assertEquals(params.get(i++), element);
        }

        assertEquals(params.size(), i);
    }

    @Test
    @Tag("score:3")
    @DisplayName("TSTIterator Test 4")
    void testIterator4(){
        iter_root = new TSTNode<>(5);
        List<Integer> params =  new LinkedList<Integer>();

        params.add(5);

        TST<Integer> tree = new TST<>();
        tree.root = iter_root;

        Iterator iter = tree.iterator();

        assertTrue(iter.hasNext());
        assertEquals(5, iter.next());
    }

    @Test
    @Tag("score:3")
    @DisplayName("TSTIterator Test 5")
    void testIterator5(){
        iter_root = new TSTNode<>(1);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(9,8,-1,-4,-6));

        Solution_Iterator aux = new Solution_Iterator();
        aux.buildTestObj(params, iter_root);

        params.add(1);
        params.sort(Comparator.naturalOrder());

        TST<Integer> tree = new TST<>();
        tree.root = iter_root;

        int i =0;
        for(Integer element : tree){
            assertEquals(params.get(i++), element);
        }

        assertEquals(params.size(), i);

    }

    @Test
    @Tag("score:3")
    @DisplayName("TSTIterator Test 6")
    void testIterator6(){
        iter_root = new TSTNode<>(5);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(5,6));

        Solution_Iterator aux = new Solution_Iterator();
        aux.buildTestObj(params, iter_root);

        TST<Integer> tree = new TST<>();
        tree.root = iter_root;

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
    @Tag("private")
    @DisplayName("TSTIterator Test 7")
    void testIterator7(){
        iter_root = new TSTNode<>(21);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(-1,26,0,2));

        Solution_Iterator aux = new Solution_Iterator();
        aux.buildTestObj(params, iter_root);

        TST<Integer> tree = new TST<>();
        tree.root = iter_root;

        Iterator iter1 = tree.iterator();
        Iterator iter2 = tree.iterator();

        assertTrue(iter1.hasNext());
        assertEquals(-1, iter1.next());
        assertTrue(iter1.hasNext());
        assertEquals(0, iter1.next());
        assertTrue(iter1.hasNext());
        assertEquals(2, iter1.next());

        assertTrue(iter2.hasNext());
        assertEquals(-1, iter2.next());
        assertTrue(iter2.hasNext());
        assertEquals(0, iter2.next());
        assertTrue(iter1.hasNext());
        assertEquals(21, iter1.next());

    }

    @Test
    @Tag("score:3")
    @Tag("private")
    @DisplayName("TSTIterator Test 8")
    void testIterator8(){
        iter_root = new TSTNode<>(0);
        List<Integer> params =  new LinkedList<Integer>(Arrays.asList(100,-50));

        Solution_Iterator aux = new Solution_Iterator();
        aux.buildTestObj(params, iter_root);

        TST<Integer> tree = new TST<>();
        tree.root = iter_root;

        Iterator iter1 = tree.iterator();
        Iterator iter2 = tree.iterator();

        assertTrue(iter1.hasNext());
        assertEquals(-50, iter1.next());
        assertTrue(iter2.hasNext());
        assertEquals(-50, iter2.next());
        assertTrue(iter2.hasNext());
        assertEquals(0, iter2.next());

        assertTrue(iter2.hasNext());
        assertEquals(100, iter2.next());
        assertFalse(iter2.hasNext());

        assertTrue(iter1.hasNext());
        assertEquals(0, iter1.next());
        assertTrue(iter1.hasNext());
        assertEquals(100, iter1.next());

    }





    @Test
    @Tag("score:1")
    @DisplayName("TST Insert Test 1")
    void testInsertTest1(){
        Solution_Insert<Integer> insert = new Solution_Insert<>();

        insert.insert(5);
        assertTrue(insert.contains(5));
    }

    @Test
    @Tag("score:2")
    @DisplayName("TST Insert Test 2")
    void testInsertTest2(){
        Solution_Insert<Integer> insert = new Solution_Insert<>();

        insert.insert(5);
        insert.insert(2);
        insert.insert(10);
        insert.insert(-1);
        insert.insert(15);
        insert.insert(25);
        insert.insert(-9);
        insert.contains(-9);
        assertTrue(insert.contains(-9));
    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Insert Test 3")
    void testInsertTest3(){
        Solution_Insert<Integer> insert = new Solution_Insert<>();

        insert.insert(5);
        insert.insert(2);
        insert.insert(10);
        insert.insert(-1);
        insert.insert(15);
        insert.insert(25);
        insert.insert(-9);
        assertTrue(insert.contains(25));
        assertEquals(25, insert.root.right.right.right.element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Insert Test 4")
    void testInsertTest4(){
        Solution_Insert<Integer> insert = new Solution_Insert<>();

        insert.insert(5);
        insert.insert(2);
        insert.insert(10);
        insert.insert(-1);
        insert.insert(15);
        insert.insert(25);
        insert.insert(-9);
        assertFalse(insert.contains(0));
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Insert Test 5")
    void testInsertTest5(){
        Solution_Insert<Character> insert = new Solution_Insert<>();

        insert.insert('5');
        insert.insert('5');
        insert.insert('5');

        assertEquals('5', insert.root.mid.mid.element);
        assertNull(insert.root.left);
        assertNull(insert.root.right);
        assertNull(insert.root.mid.mid.mid);

    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Insert Test 6")
    void testInsertTest6(){
        Solution_Insert<Integer> insert = new Solution_Insert<>();

        insert.insert(5);
        insert.insert(-1);
        insert.insert(-10);
        insert.insert(5);
        insert.insert(-15);

        assertNull(insert.root.right);
        assertEquals(5, insert.root.element);
        assertEquals(-1, insert.root.left.element);
        assertEquals(-10, insert.root.left.left.element);
        assertEquals(5, insert.root.mid.element);
        assertEquals(-15, insert.root.left.left.left.element);
    }

    @Test
    @Tag("score:1")
    @Tag("private")
    @DisplayName("TST Insert Test 7")
    void testInsertTest7(){
        Solution_Insert<Integer> insert = new Solution_Insert<>();

        insert.insert(1);
        insert.insert(2);
        insert.insert(3);
        insert.insert(4);

        assertEquals(1, insert.root.element);
        assertEquals(2, insert.root.right.element);
        assertNull(insert.root.left);
        assertEquals(3, insert.root.right.right.element);
        assertNull(insert.root.right.left);
        assertEquals(4, insert.root.right.right.right.element);

    }





    Solution_NodeMaxMin<Integer> nodemaxmin;

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Max Test 1")
    void testNodeMax1(){
        nodemaxmin = new Solution_NodeMaxMin<>(10);

        assertEquals(10, nodemaxmin.findMax().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Max Test 2")
    void testNodeMax2(){
        nodemaxmin = new Solution_NodeMaxMin<>(10);
        nodemaxmin.add(1);
        nodemaxmin.add(11);
        nodemaxmin.add(15);

        assertEquals(15, nodemaxmin.findMax().element);
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Max Test 3")
    void testNodeMax3(){
        nodemaxmin = new Solution_NodeMaxMin<>(1);
        nodemaxmin.add(2);
        nodemaxmin.add(3);
        nodemaxmin.add(4);
        nodemaxmin.add(4);
        nodemaxmin.add(4);
        nodemaxmin.add(5);

        assertEquals(5, nodemaxmin.findMax().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Max Test 4")
    void testNodeMax4(){
        nodemaxmin = new Solution_NodeMaxMin<>(100);
        nodemaxmin.add(4);
        nodemaxmin.add(3);
        nodemaxmin.add(4);
        nodemaxmin.add(4);
        nodemaxmin.add(4);
        nodemaxmin.add(5);
        nodemaxmin.add(101);

        assertEquals(101, nodemaxmin.findMax().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Min Test 1")
    void testNodeMin1(){
        nodemaxmin = new Solution_NodeMaxMin<>(10);

        assertEquals(10, nodemaxmin.findMin().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Min Test 2")
    void testNodeMin2(){
        nodemaxmin = new Solution_NodeMaxMin<>(10);
        nodemaxmin.add(3);
        nodemaxmin.add(1);
        nodemaxmin.add(15);

        assertEquals(1, nodemaxmin.findMin().element);
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Min Test 3")
    void testNodeMin3(){
        nodemaxmin = new Solution_NodeMaxMin<>(7);
        nodemaxmin.add(6);
        nodemaxmin.add(5);
        nodemaxmin.add(4);
        nodemaxmin.add(4);
        nodemaxmin.add(4);
        nodemaxmin.add(3);

        assertEquals(3, nodemaxmin.findMin().element);
    }

    @Test
    @Tag("score:1")
    @DisplayName("TSTNode Min Test 4")
    void testNodeMin4(){
        nodemaxmin = new Solution_NodeMaxMin<>(2);
        nodemaxmin.add(20);
        nodemaxmin.add(30);
        nodemaxmin.add(41);
        nodemaxmin.add(43);
        nodemaxmin.add(45);
        nodemaxmin.add(-5);

        assertEquals(-5, nodemaxmin.findMin().element);
    }


    Solution_NodeHeight<Integer> nodeheight;

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 1")
    void testNodeHeight1(){
        nodeheight = new Solution_NodeHeight<>(12);
        nodeheight.add(6);
        nodeheight.add(1);
        nodeheight.add(10);
        nodeheight.add(12);
        nodeheight.add(15);
        nodeheight.add(13);
        nodeheight.add(16);

        assertEquals(2, nodeheight.height());
    }


    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 2")
    void testNodeHeight2(){
        nodeheight = new Solution_NodeHeight<>(12);
        nodeheight.add(12);
        nodeheight.add(12);
        nodeheight.add(12);

        assertEquals(3, nodeheight.height());
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 3")
    void testNodeHeight3(){
        nodeheight = new Solution_NodeHeight<>(15);
        nodeheight.add(10);
        nodeheight.add(5);
        nodeheight.add(4);
        nodeheight.add(-4);
        nodeheight.add(3);
        nodeheight.add(3);

        assertEquals(6, nodeheight.height());
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 4")
    void testNodeHeight4(){
        nodeheight = new Solution_NodeHeight<>(15);

        assertEquals(0, nodeheight.height());
    }

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Height Test 5")
    void testNodeHeight5(){
        nodeheight = new Solution_NodeHeight<>(15);
        nodeheight.add(15);
        nodeheight.add(15);
        nodeheight.add(15);
        nodeheight.add(15);
        nodeheight.add(16);
        nodeheight.add(16);
        nodeheight.add(16);
        nodeheight.add(16);
        nodeheight.add(16);

        assertEquals(5, nodeheight.height());

    }



    @Test
    @Tag("score:1")
    @DisplayName("TST Contains Test 1")
    void testContains1(){
        Solution_Contains<Integer> contains = new Solution_Contains<>();

        contains.insert(5);

        assertTrue(contains.contains(5));
        assertFalse(contains.contains(4));
    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Contains Test 2")
    void testContains2(){
        Solution_Contains<Character> contains = new Solution_Contains<>();

        contains.insert('5');
        contains.insert('3');
        contains.insert('0');
        contains.insert('3');
        contains.insert('1');
        contains.insert('2');


        assertTrue(contains.contains('1'));
        assertTrue(contains.contains('2'));
        assertFalse(contains.contains('4'));

    }

    @Test
    @Tag("score:1")
    @DisplayName("TST Contains Test 3")
    void testContains3(){
        Solution_Contains<Integer> contains = new Solution_Contains<>();

        contains.insert(5);
        contains.insert(3);
        contains.insert(10);
        contains.insert(3);
        contains.insert(-1);
        contains.insert(21);


        assertTrue(contains.contains(3));
        contains.remove(3);
        assertTrue(contains.contains(3));
        contains.remove(3);
        assertFalse(contains.contains(3));
        contains.insert(15);
        contains.insert(14);
        contains.insert(13);
        assertTrue(contains.contains(13));

    }

    @Test
    @Tag("score:2")
    @DisplayName("TST Contains Test 4")
    void testContains4(){
        Solution_Contains<String> contains = new Solution_Contains<>();

        contains.insert("5");
        contains.insert("3");
        contains.insert("10");
        contains.insert("3");
        contains.insert("-1");
        contains.insert("21");
        contains.insert("15");
        contains.insert("14");
        contains.insert("13");
        contains.insert("11");


        assertTrue(contains.contains("13"));
        assertTrue(contains.contains("5"));
        contains.remove("3");
        assertTrue(contains.contains("3"));


    }



    TSTNode<Integer> nodecons;

    @Test
    @Tag("score:2")
    @DisplayName("TSTNode Constructor Test 1")
    void testNodeCons1(){
        nodecons = new TSTNode<Integer>(5);

        assertEquals(5, nodecons.element);

    }

    @Test
    @Tag("score:3")
    @DisplayName("TSTNode Constructor Test 2")
    void testNodeCons2(){
        nodecons = new TSTNode<Integer>(5);

        assertNull(nodecons.left);
        assertNull(nodecons.right);
        assertNull(nodecons.mid);

    }





    boolean compTST(Solution_TSTNode o1, TSTNode o2){
        if(o1 == null && o2 == null)
            return true;

        else if(o1 != null && o2 == null)
            return false;

        else if(o1 == null && o2 != null)
            return false;

        else if(o1.element == o2.element)
            return compTST(o1.left, o2.left) && compTST(o1.mid, o2.mid) && compTST(o1.right, o2.right);

        else
            return false;
    }

    private static TSTNode<Integer> insert(List<Integer> list){
        int len = list.size();
        if (len == 0)
            return null;
        int mid = len/2;
        TSTNode root = new TSTNode<>(list.get(mid));
        root.left = insert(list.subList(0,mid));
        root.right = insert(list.subList(mid+1,list.size()));
        return root;
    }

    <T extends Comparable<T>>  void buildTestObj(List<T> l, TST o1){
        for(T x : l){
            o1.insert(x);
        }
    }


    <T extends Comparable<T>>  void buildTestObj(List<T> l, Solution_TST o1){
        for(T x : l){
            o1.insert(x);
        }
    }

    Solution_TST<Integer> rebalance;
    TST<Integer> rebalance_s;

    @Test
    @Tag("score:4")
    @DisplayName("TST Rebalance Test 0")
    void testRebalanceTest0(){

//        This is the scenario in the PDF
        rebalance_s = new TST<>();

        rebalance_s.insert(5);
        rebalance_s.insert(2);
        rebalance_s.insert(0);
        rebalance_s.insert(-1);
        rebalance_s.insert(-1);
        rebalance_s.insert(0);
        rebalance_s.insert(3);
        rebalance_s.insert(5);
        rebalance_s.insert(7);
        rebalance_s.insert(8);

        rebalance_s.rebalance();

        assertEquals(3, rebalance_s.root.element);
        assertEquals(0, rebalance_s.root.left.element);
        assertEquals(-1, rebalance_s.root.left.left.element);
        assertEquals(-1, rebalance_s.root.left.left.mid.element);
        assertEquals(0, rebalance_s.root.left.mid.element);
        assertEquals(2, rebalance_s.root.left.right.element);
        assertEquals(7, rebalance_s.root.right.element);
        assertEquals(5, rebalance_s.root.right.left.element);
        assertEquals(8, rebalance_s.root.right.right.element);
        assertEquals(5, rebalance_s.root.right.left.mid.element);
    }

    @Test
    @Tag("score:3")
    @Tag("private")
    @DisplayName("TST Rebalance Test 1")
    void testRebalanceTest1(){
        Solution_TST<String> rebalance;
        TST<String> rebalance_s;
        rebalance = new Solution_TST<>();
        rebalance_s = new TST<>();

        List<String> params = Arrays.asList("decade", "vessel", "suppress", "seize");

        buildTestObj(params, rebalance);
        buildTestObj(params, rebalance_s);

        rebalance.rebalance();
        rebalance_s.rebalance();

        assertTrue(compTST(rebalance.root, rebalance_s.root));
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Rebalance Test 2")
    void testRebalanceTest2(){
        rebalance = new Solution_TST<>();
        rebalance_s = new TST<>();

        List<Integer> params = Arrays.asList(15,14,13,12,11,10,9,8,7,6,5);

        buildTestObj(params, rebalance);
        buildTestObj(params, rebalance_s);

        rebalance.rebalance();
        rebalance_s.rebalance();

        assertTrue(compTST(rebalance.root, rebalance_s.root));
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Rebalance Test 3")
    void testRebalanceTest3(){
        rebalance_s = new Solution_Rebalance<>();

        List<Integer> params = Arrays.asList(15,16,17,17,19,20,21,22,23,22,26);

        buildTestObj(params, rebalance_s);

        rebalance_s.rebalance();

        assertNotEquals(rebalance_s.root.element, 15);

        rebalance_s.remove(17);
        assertTrue(rebalance_s.contains(17));

        rebalance_s.remove(22);
        assertTrue(rebalance_s.contains(22));

        rebalance_s.remove(22);
        assertFalse(rebalance_s.contains(22));

        rebalance_s.remove(17);
        assertFalse(rebalance_s.contains(17));
    }

    @Test
    @Tag("score:1")
    @Tag("private")
    @DisplayName("TST Rebalance Test 4")
    void testRebalanceTest4(){
        rebalance = new Solution_TST<>();
        rebalance_s = new TST<>();

        List<Integer> params = Arrays.asList(1);

        buildTestObj(params, rebalance);
        buildTestObj(params, rebalance_s);

        rebalance.rebalance();
        rebalance_s.rebalance();

        assertTrue(compTST(rebalance.root, rebalance_s.root));
    }

    @Test
    @Tag("score:1")
    @Tag("private")
    @DisplayName("TST Rebalance Test 5")
    void testRebalanceTest5(){
        rebalance = new Solution_TST<>();
        rebalance_s = new TST<>();

        List<Integer> params = Arrays.asList(2,1,3);

        buildTestObj(params, rebalance);
        buildTestObj(params, rebalance_s);

        rebalance.rebalance();
        rebalance_s.rebalance();

        assertTrue(compTST(rebalance.root, rebalance_s.root));
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Rebalance Test 6")
    void testRebalanceTest6(){
        Solution_TST<Double> rebalance;
        TST<Double> rebalance_s;
        rebalance = new Solution_TST<>();
        rebalance_s = new TST<>();

        List<Double> params = Arrays.asList(62.9,62.8,62.7,62.6,62.5,62.4,62.3,62.2,63.0,63.1,63.2,63.3,63.4,63.5);

        buildTestObj(params, rebalance);
        buildTestObj(params, rebalance_s);

        rebalance.rebalance();
        rebalance_s.rebalance();

        assertTrue(compTST(rebalance.root, rebalance_s.root));
    }

    @Test
    @Tag("score:2")
    @Tag("private")
    @DisplayName("TST Rebalance Test 7")
    void testRebalanceTest7(){
        rebalance_s = new Solution_Rebalance<>();

        List<Integer> params = Arrays.asList(9,15,10,-5,9,-5,8,7,20,21,18,17,19);

        buildTestObj(params, rebalance_s);

        rebalance_s.rebalance();

        assertNotEquals(rebalance_s.root.element, -5);
        assertNotEquals(rebalance_s.root.element, 9);

        rebalance_s.remove(-5);
        assertTrue(rebalance_s.contains(-5));

        rebalance_s.remove(9);
        assertTrue(rebalance_s.contains(9));

        rebalance_s.remove(-5);
        assertFalse(rebalance_s.contains(-5));

        rebalance_s.remove(9);
        assertFalse(rebalance_s.contains(9));

    }

    @Test
    @Tag("score:3")
    @Tag("private")
    @DisplayName("TST Rebalance Test 8")
    void testRebalanceTest8(){
        rebalance = new Solution_TST<>();
        rebalance_s = new TST<>();

        List<Integer> params = Arrays.asList(50,25,15,10,20,35,30,40,80,60,55,70,90,81,82,83,84,85,86,87,88,91,92,93,94);

        buildTestObj(params, rebalance);
        buildTestObj(params, rebalance_s);

        rebalance.rebalance();
        rebalance_s.rebalance();

        assertTrue(compTST(rebalance.root, rebalance_s.root));
    }

}
