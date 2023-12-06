import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


// Main class for the Genealogy Tree GUI
//The time complexity of this code is O(1) because it only performs a constant number of operations regardless of the size of the input.
//The space complexity of this code is also O(1) because it only uses a fixed amount of memory to store the GUI components and the root node of the tree. The size of the input does not affect the amount of memory used.

public class GenealogyTreeGUI extends JFrame {
    private Node root; // Root node of the tree
    private HashMap<String, Node> nodes; // Map to store nodes by their names

    private JTextArea outputArea; // Text area for output
    private JTextField inputField; // Text field for user input

    public GenealogyTreeGUI() {
        root = null;
        nodes = new HashMap<>();

        setTitle("Genealogy Tree"); // Set the title of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
        setSize(700, 300); // Set window size
        setLayout(new BorderLayout()); // Set layout manager

        outputArea = new JTextArea(); // Create a text area for output
        JScrollPane scrollPane = new JScrollPane(outputArea); // Create a scrollable pane for the text area
        add(scrollPane, BorderLayout.CENTER); // Add the text area to the center of the window

        JPanel inputPanel = new JPanel(); // Create a panel for input components
        inputPanel.setLayout(new BorderLayout()); // Set the layout of the input panel
        inputField = new JTextField(); // Create a text field for user input
        inputPanel.add(inputField, BorderLayout.CENTER); // Add the text field to the input panel
        JButton executeButton = new JButton("Execute"); // Create a button for executing commands
        executeButton.addActionListener(new ExecuteButtonListener()); // Add an action listener to the button
        inputPanel.add(executeButton, BorderLayout.EAST); // Add the button to the input panel
        add(inputPanel, BorderLayout.SOUTH); // Add the input panel to the bottom of the window
        appendOutput("Available commands:\n" + "root name\nleft parent child\nright parent child\ndescendants person\nancestors person\nclear (Clears the text area)\n \nSee README for instructions on how to use these commands\n");


        setVisible(true); // Set the window visible
    }

    // Method to add the root node to the tree
    private void clearOutput() {
        outputArea.setText(null); // Clears the text in the output area
    }
    private void addRoot(String name) {
        if (root == null) {
            root = new Node(name);
            nodes.put(name, root);
            appendOutput("Root created. Command executed successfully\n");
        } else {
            appendOutput("Root node already exists. Cannot add another root.\n");
        }
    }

    // Method to add a child node to a parent node
    private void addChild(String parent, String child, String position) {
        if (!nodes.containsKey(parent)) {
            appendOutput("No node named '" + parent + "' found.\n");
            return;
        }

        Node parentNode = nodes.get(parent);
        if (position.equals("left") && parentNode.left == null) {
            Node newChild = new Node(child);
            parentNode.left = newChild;
            nodes.put(child, newChild);
            appendOutput("Left child created. Command executed successfully\n");
        } else if (position.equals("right") && parentNode.right == null) {
            Node newChild = new Node(child);
            parentNode.right = newChild;
            nodes.put(child, newChild);
            appendOutput( "Right child created. Command executed successfully\n");
        } else {
            appendOutput("Node '" + parent + "' already has a " + position + " child.\n");
        }
    }

    // Method to find descendants of a person
    private ArrayList<String> findDescendants(String person) {
        ArrayList<String> descendants = new ArrayList<>();
        if (!nodes.containsKey(person)) {
            appendOutput("No node named '" + person + "' found.\n");
            return descendants;
        }

        Node startNode = nodes.get(person);
        _findDescendantsHelper(startNode, descendants);
        return descendants;
    }

    // Recursive helper method to find descendants
    private void _findDescendantsHelper(Node node, ArrayList<String> descendants) {
        if (node == null) {
            return;
        }
        descendants.add(node.name);
        _findDescendantsHelper(node.left, descendants);
        _findDescendantsHelper(node.right, descendants);
    }

    // Method to find ancestors of a person
    private ArrayList<String> findAncestors(String person) {
        ArrayList<String> ancestors = new ArrayList<>();
        if (!nodes.containsKey(person)) {
            appendOutput("No node named '" + person + "' found.\n");
            return ancestors;
        }

        Node startNode = nodes.get(person);
        _findAncestorsHelper(root, startNode, ancestors);
        appendOutput("Ancestors found. Command executed successfully\n");

        return ancestors;
    }

    // Recursive helper method to find ancestors
    private boolean _findAncestorsHelper(Node current, Node target, ArrayList<String> ancestors) {
        if (current == null) {
            return false;
        }

        if (current == target || _findAncestorsHelper(current.left, target, ancestors) || _findAncestorsHelper(current.right, target, ancestors)) {
            if (current != target) {
                ancestors.add(current.name);
            }
            return true;
        }

        return false;
    }

    // Method to append text to the output area
    private void appendOutput(String text) {
        outputArea.append(text);
    }

    // Method to execute the user-entered command
    private void executeCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length < 1) {
            appendOutput("Invalid command. Please enter a valid command.\n");
            return;
        }

        String action = parts[0].toLowerCase();
        switch (action) {
            // Existing cases
            case "root":
                addRoot(parts[1]);
                break;
            case "left":
            case "right":
                if (parts.length < 3) {
                    appendOutput("Invalid command. Please enter a valid command.\n");
                    return;
                }
                addChild(parts[1], parts[2], action);
                break;
            case "descendants":
                ArrayList<String> descendants = findDescendants(parts[1]);
                appendOutput("Descendants of '" + parts[1] + "': " + descendants.toString() + "\n");
                break;
            case "ancestors":
                ArrayList<String> ancestors = findAncestors(parts[1]);
                appendOutput("Ancestors of '" + parts[1] + "': " + ancestors.toString() + "\n");
                break;
            case "clear": // New command to clear the output area
                clearOutput();
                break;
            default:
                appendOutput("Invalid command. Please enter a valid command.\n");
        }
    }
    // ActionListener for the Execute button
    private class ExecuteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = inputField.getText().trim(); //removes unnecessary spaces at the beginning and at the end
            executeCommand(command);
            inputField.setText("");
        }
    }

    // Main method to start the GUI application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GenealogyTreeGUI());
    }
}
