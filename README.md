# Family Tree Assignment (prjBAPQ)
Write a program, with a graphical user interface, that allows the user 
to create genealogy trees and work with them. Each person in this genealogy tree will be 
restricted to having at most two children. The nodes of the tree will store string values, which are interpreted as
names of people. The program should support the following commands:

**root name:** Adds name as the root of the tree. This must be the first command entered: it creates a tree with a single node,
whose value field holds name. The created node becomes the root of the genealogy tree. The command is ignored if it is entered after the tree
has been created.

**left parent child:** Adds a new node child as the left child of the node with name parent. The command is ignored if there is no node with the name parent in the tree,
or if there is such a node, but it already has a left child.

**right parent child:** Adds a new node child as the right child of the node with name parent. The command is ignored if there is no node with the name parent in the tree,
or if there is such a node, but it already has a right child.

**descendants person:** Displays a list of all the descendants of the node with name person. The command is ignored if there is no node with that name.

**ancestors person:** Displays a list of all ancestors of the node with the name person. The command is ignored if there is no node in the tree with that name.

---

Added commands for convenience:


**clear:** Clears the text area for better readability this does **not** clear the binary tree