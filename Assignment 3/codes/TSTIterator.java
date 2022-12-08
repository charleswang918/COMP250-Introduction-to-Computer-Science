import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
// add your imports here

class TSTIterator<T extends Comparable<T>> implements Iterator<T> {
    // TODO: implement the iterator class here
    // add your own helper methods if necessary
    ArrayList<T> TList;
    int itertor;
    public TSTIterator(TSTNode<T> root){
        TList = new ArrayList<T>();
        itertor = 0;
        inorderTST(root, TList);
    }
    // Using inorder traversal to make sorted list for each elements of nodes
    public void inorderTST(TSTNode<T> root, ArrayList<T> list){
        if(root != null) {
            inorderTST(root.left, list);
            inorderTST(root.mid, list);
            list.add(root.element);
            inorderTST(root.right, list);
        }
    }
    /**
     * Returns {@code true} if the iteration has more elements. (In other words, returns {@code true} if {@link #next}
     * would return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        boolean hasnext = itertor < TList.size();
        return hasnext;
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
        T element = TList.get(itertor);
        itertor++;
        return element;
    }
}