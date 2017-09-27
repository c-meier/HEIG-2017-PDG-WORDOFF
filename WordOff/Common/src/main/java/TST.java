/**
 * Project : WordOff
 * Author(s) : Antoine Friant
 * Date : 27.09.17
 */

public class TST {
    class Node {
        Node left;
        Node middle;
        Node right;
        char data;
        boolean isEnd;
        int nodeHeight;

        public Node(char data) {
            this.data = data;

            this.left = null;
            this.middle = null;
            this.right = null;
            this.isEnd = false;
            this.nodeHeight = 0;
        }
    }

    private Node root;

    public TST() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public void insert(String word) {
        root = insert(root, word, 0);
    }

    private Node insert(Node r, String key, int index) {
        char c = key.charAt(index);

        if (r == null) {
            r = new Node(c);
        }

        if (c < r.data) {
            r.left = insert(r.left, key, index);
        } else if (c > r.data) {
            r.right = insert(r.right, key, index);
        } else if (index + 1 != key.length()) {
            r.middle = insert(r.middle, key, index + 1);
        } else {
            r.isEnd = true;
        }
        return restoreBalance(r);
    }

    public boolean search(String word) {
        return search(root, word, 0);
    }

    private boolean search(Node r, String key, int index) {
        char c;
        c = key.charAt(index);
        if (r == null) {
            return false;
        }

        if (c < r.data) {
            return search(r.left, key, index);
        } else if (c > r.data) {
            return search(r.right, key, index);
        } else {
            if (r.isEnd && index == key.length() - 1) {
                return true;
            } else if (index == key.length() - 1) {
                return false;
            } else {
                return search(r.middle, key, index + 1);
            }
        }
    }

    private Node restoreBalance(Node r) {
        if (balance(r) < -1) { // left < right-1
            if (balance(r.right) > 0) {
                r.right = rotateRight(r.right);
            }
            r = rotateLeft(r);
        } else if (balance(r) > 1) { // left > right+1
            if (balance(r.left) < 0) {
                r.left = rotateLeft(r.left);
            }
            r = rotateRight(r);
        } else {
            updateNodeHeight(r);
        }
        return r;
    }

    private void updateNodeHeight(Node r) {
        r.nodeHeight = Math.max(height(r.right), height(r.left)) + 1;
    }

    private int height(Node r) {
        if (r == null) {
            return -1;
        }
        return r.nodeHeight;
    }

    private Node rotateRight(Node r) {
        Node y = r.left;
        r.left = y.right;
        y.right = r;

        updateNodeHeight(r);
        updateNodeHeight(y);
        return y;
    }

    private Node rotateLeft(Node r) {
        Node y = r.right;
        r.right = y.left;
        y.left = r;

        updateNodeHeight(r);
        updateNodeHeight(y);
        return y;
    }

    private int balance(Node r) {
        if (r == null) {
            return 0;
        }
        return height(r.left) - height(r.right);
    }
}

