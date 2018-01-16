/*
 * File: TST.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of Tertiary Search Tries so it can contain a dictionary and generate anagrams
 */
class TST {
    /**
     * Structure of a TST node
     */
    class Node {
        Node left;      // left node
        Node middle;    // next node (center)
        Node right;     // right node
        char data;      // character in the node
        boolean isEnd;  // true if the node is the end of a word
        int nodeHeight; // height of a node (useful for balancing the tree)

        /**
         * Node constructor
         * @param data character key of the node
         */
        public Node(char data) {
            this.data = data;
            this.left = null;
            this.middle = null;
            this.right = null;
            this.isEnd = false;
            this.nodeHeight = 0;
        }
    }

    private Node root;  // root node

    /**
     * Empty tree constructor
     */
    public TST() {
        root = null;
    }

    /**
     * @return true if and only if the tree is empty
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Makes the tree empty
     */
    public void clear() {
        root = null;
    }

    /**
     * Inserst un word in the tree
     * @param key
     */
    public void insert(String key) {
        root = insert(root, key, 0);
    }

    /**
     * Inserts a word in the tree, starting from the letter at index "index"
     * @param r     word root
     * @param key   word to insert
     * @param index index of the first letter in the word
     * @return node in which the last letter was inserted
     */
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

    /**
     * @param word word to look up
     * @return true if and only if the word is in the tree
     */
    public boolean contains(String word) {
        return contains(root, word, 0);
    }

    /**
     * @param r     root node of the lookup
     * @param key   word to look up
     * @param index first letter of the wanted word
     * @return true if and only if the word is in the tree
     */
    private boolean contains(Node r, String key, int index) {
        char c;
        c = key.charAt(index);
        if (r == null) {
            return false;
        }

        if (c < r.data) {
            return contains(r.left, key, index);
        } else if (c > r.data) {
            return contains(r.right, key, index);
        } else {
            if (r.isEnd && index == key.length() - 1) {
                return true;
            } else if (index == key.length() - 1) {
                return false;
            } else {
                return contains(r.middle, key, index + 1);
            }
        }
    }

    /**
     * @param r the root of the subtree to balance
     * @return the new root of the now balanced subtree
     */
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

    /**
     * Update the height of node r
     * @param r
     */
    private void updateNodeHeight(Node r) {
        r.nodeHeight = Math.max(height(r.right), height(r.left)) + 1;
    }

    /**
     * @param r node
     * @return height of the node
     */
    private int height(Node r) {
        if (r == null) {
            return -1;
        }
        return r.nodeHeight;
    }

    /**
     * Executes a rotation to the right of the subtree of root r
     * @param r
     * @return new root of the subtree
     */
    private Node rotateRight(Node r) {
        Node y = r.left;
        r.left = y.right;
        y.right = r;

        updateNodeHeight(r);
        updateNodeHeight(y);
        return y;
    }

    /**
     * Executes a rotation to the left of the subtree of root r
     * @param r
     * @return new root of the subtree
     */
    private Node rotateLeft(Node r) {
        Node y = r.right;
        r.right = y.left;
        y.left = r;

        updateNodeHeight(r);
        updateNodeHeight(y);
        return y;
    }

    /**
     * @param r
     * @return height difference between the subtrees to the left and right of node r
     */
    private int balance(Node r) {
        if (r == null) {
            return 0;
        }
        return height(r.left) - height(r.right);
    }

    /**
     * Returns all anagrams of str
     * @param str
     * @return anagrams list
     */
    public List<String> getAnagrams(String str) {
        LinkedList<String> list = new LinkedList<>();
        if (root != null) {
            fillAnagrams(str, "", root, list);
        }
        return list;
    }

    /**
     * Fills list with anagrams of str and all its substrings.
     * Character '#' is a wildcard and can be replaced with any letter while looking for anagrams.
     *
     * @param str
     * @param key
     * @param r
     * @param list
     */
    private void fillAnagrams(String str, String key, Node r, List<String> list) {
        if (r == null || str.isEmpty()) {
            return;
        }

        // searches subtrees right and left
        fillAnagrams(str, key, r.left, list);
        fillAnagrams(str, key, r.right, list);

        // if the node's character is in the word, remove it and go down in the tree
        if (str.contains("" + r.data)) {

            // if the word we're building exists, add it's key to the list
            if (r.isEnd) {
                list.add(key + r.data);
            }

            // go down in the tree without this character
            String newstr = str.replaceFirst("" + r.data, "");
            fillAnagrams(newstr, key + r.data, r.middle, list);
        }

        // wildcard
        if (str.contains("#")) {

            // if the word exists and we still have a wildcard, go down a level
            if (r.isEnd) {
                list.add(key + "#" + r.data);
            }

            // go down in the tree without this wildcard
            String newstr = str.replaceFirst("#", "");
            fillAnagrams(newstr, key + "#" + r.data, r.middle, list);
        }
    }
}

