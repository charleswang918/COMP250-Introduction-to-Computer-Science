import java.util.ArrayList;

// add your imports here
class TSTNode<T extends Comparable<T>>{
    T element;     	            // The data in the node
    TSTNode<T>  left;   		// left child
    TSTNode<T>  mid;   		    // middle child
    TSTNode<T>  right;  		// right child

    // TODO: implement the node class here
    
    TSTNode(T element){
        this.element = element;
        this.left = null;
        this.mid = null;
        this.right = null;
    }

    TSTNode<T> findMax(){
        return findMax(this);
    }

    TSTNode<T> findMin(){
        return findMin(this);
    }

    int height(){
        return height(this);
    }

    // add your own helper methods if necessary
    TSTNode<T> insert(TSTNode<T> root, T key){
        if (root == null){
            root = new TSTNode<T>(key);
        }
        else if (key.compareTo(root.element) < 0){
            root.left = insert(root.left, key);
        }
        else if (key.compareTo(root.element) > 0){
            root.right = insert(root.right, key);
        }
        else if (key.compareTo(root.element) == 0){
            root.mid = insert(root.mid, key);
        }
        return root;
    }

    boolean contains(TSTNode<T> root, T key){
        if (root == null){
            return false;
        }
        if (key.compareTo(root.element) < 0){
            return contains(root.left, key);
        }
        else if (key.compareTo(root.element) > 0){
            return contains(root.right, key);
        }
        else{
            return true;
        }
    }

    int height(TSTNode<T> root){
        if (root == null){
            return -1;
        }
        else {
            int maxLeftRight = Math.max(height(root.left), height(root.right));
            int maxVal = Math.max(maxLeftRight, height(root.mid));
            return 1 + maxVal;
        }
    }

    TSTNode<T> findMax(TSTNode<T> root){
        if (root == null){
            return null;
        }
        else if (root.right == null){
            return root;
        }
        else {
            return findMax(root.right);
        }
    }

    TSTNode<T> findMin(TSTNode<T> root){
        if (root == null){
            return null;
        }
        else if (root.left == null){
            return root;
        }
        else {
            return findMin(root.left);
        }
    }

    TSTNode<T> remove(TSTNode<T> root, T key){
        if (root == null){
            return null;
        }
        else if (key.compareTo(root.element) < 0){
            root.left = remove(root.left, key);
        }
        else if (key.compareTo(root.element) > 0){
            root.right = remove(root.right, key);
        }
        else{
            if (root.mid != null){
                root.mid = remove(root.mid, key);
            }
            else if (root.left == null){
                root = root.right;
            }
            else if (root.right == null){
                root = root.left;
            }
            else {
                root.element = findMin(root.left).element;
                root.left = remove(root.left, root.element);
            }
        }
        return root;
    }

    TSTNode<T> balanceTST(TSTNode<T> root){
        TSTIterator<T> it = new TSTIterator<>(root);
        return balanced(it.TList);
    }

    TSTNode<T> balanced(ArrayList<T> lis){
        if (lis.size() == 0){
            return null;
        }
        int mid = lis.size()/2;
        TSTNode<T> root = new TSTNode<>(lis.get(mid));
        if (lis.size() > 1){
            int left = mid;
            int right = mid;

            if (left-1 >= 0){
                if (lis.get(left-1) == lis.get(mid)){
                    left--;
                }
            }
            if (right+1 < lis.size()){
                if (lis.get(right+1) == lis.get(mid)){
                    right++;
                }
            }
            root.left = balanced(new ArrayList<T>(lis.subList(0, left)));
            root.mid = balanced(new ArrayList<T>(lis.subList(left, right)));
            root.right = balanced(new ArrayList<T>(lis.subList(right+1, lis.size())));
        }
        return root;
    }
}