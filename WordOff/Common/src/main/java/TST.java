import java.util.LinkedList;
import java.util.List;

/**
 * Project : WordOff
 * Date : 27.09.17
 *
 * @brief Implémentation des Tertiary Search Tries dans le but de stocker un dictionnaire
 */
class TST {
    /**
     * @brief Structure d'un noeud du TST
     */
    class Node {
        Node left;      // noeud de gauche
        Node middle;    // noeud suivant (centre)
        Node right;     // noeud de droite
        char data;      // caractère contenu par le noeud
        boolean isEnd;  // true si le noeud est la fin d'un mot
        int nodeHeight; // hauteur du noeud dans l'arbre (sert à l'équilibrage)

        /**
         * @param data le caractère du noeud
         * @brief Constructeur du noeud
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

    private Node root;  // noeud racine de l'arbre

    /**
     * @brief Constructeur de l'arbre vide
     */
    public TST() {
        root = null;
    }

    /**
     * @return true ssi l'arbre est vide
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @brief Vide l'arbre
     */
    public void clear() {
        root = null;
    }

    /**
     * @param key
     * @brief insère un mot dans l'arbre
     */
    public void insert(String key) {
        root = insert(root, key, 0);
    }

    /**
     * @param r     racine du mot
     * @param key   mot à insérer
     * @param index index de la première lettre
     * @return le noeud auquel la dernière lettre a été insérée
     * @brief insère un mot dans l'arbre à partir de la index-ième lettre
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
     * @param word mot recherché
     * @return true ssi le mot est contenu dans l'arbre
     */
    public boolean contains(String word) {
        return contains(root, word, 0);
    }

    /**
     * @param r     noeud racine de la recherche
     * @param key   mot recherché
     * @param index première lettre du mot cherché
     * @return true ssi le mot est contenu dans l'arbre
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
     * @param r le noeud racine du sous-arbre à équilibrer
     * @return le (nouveau) noeud racine du sous-arbre équilibré
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
     * @param r
     * @brief Met à jour la hauteur du noeud en argument
     */
    private void updateNodeHeight(Node r) {
        r.nodeHeight = Math.max(height(r.right), height(r.left)) + 1;
    }

    /**
     * @param r noeud
     * @return hauteur du noeud
     */
    private int height(Node r) {
        if (r == null) {
            return -1;
        }
        return r.nodeHeight;
    }

    /**
     * @param r
     * @return la nouvelle racine du sous-arbre
     * @brief effectue une rotation du sous arbre de racine r vers la droite
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
     * @param r
     * @return la nouvelle racine du sous-arbre
     * @brief effectue une rotation du sous arbre de racine r vers la gauche
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
     * @return différence de hauteur des sous-arbres gauche et droit de racine r
     */
    private int balance(Node r) {
        if (r == null) {
            return 0;
        }
        return height(r.left) - height(r.right);
    }

    public List<String> getAnagrams(String str) {
        LinkedList<String> list = new LinkedList<>();
        if (root != null) {
            fillAnagrams(str, "", root, list);
        }
        return list;
    }

    /**
     * Remplit la liste list avec des anagrammes de str et de toutes ses sous-chaines de caractères
     * @param str
     * @param key
     * @param r
     * @param list
     */
    private void fillAnagrams(String str, String key, Node r, List<String> list) {
        if (r == null) {
            return;
        }

        // cherche dans les sous-arbres gauche et droit
        fillAnagrams(str, key, r.left, list);
        fillAnagrams(str, key, r.right, list);

        // si la lettre du noeud est dans le mot, on la retire et on descend
        if (str.contains("" + r.data)) {
            key = key + r.data; // on ajoute la lettre au mot en formation

            // si le mot en formation existe, on ajoute sa clé à la liste
            if (r.isEnd) {
                list.add(key);
            }

            // on descend avec une lettre en moins
            str = str.replaceFirst("" + r.data, "");
            fillAnagrams(str, key, r.middle, list);
        }
    }
}

