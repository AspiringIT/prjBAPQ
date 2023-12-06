// Class representing a node in the genealogy tree
class Node {
    String name;
    Node left;
    Node right;

    Node(String name) {
        this.name = name;
        this.left = null;
        this.right = null;
    }
}