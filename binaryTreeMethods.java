/*
* Author: Joshua Getega, jgetega@gmail.com
* These methods are to be added to the IntTree Class at https://practiceit.cs.washington.edu/problems/bjp3/chapter17/IntTree.java
* Add to IntTree Class above to compile
* Methods are solutions to coding exercises in Ch. 17 of Building Java Programs by Stuart Reges and Marty Stepp
*/



/*
* Adds nodes until the tree is a "perfect" tree
* A perfect tree is one where all leaves are at the same level
* Each node added should store value 0
*/
public void makePerfect() {
    int height = this.height();
    makePerfect(overallRoot, height);
}

private IntTreeNode makePerfect(IntTreeNode root, int heightVal) {
    if (!(root == null && heightVal == 0)) {
        if (root == null && heightVal == 1) {
            root = new IntTreeNode(0);
        } else if (root == null && heightVal > 1) {
            root = new IntTreeNode(0);
            root.left = makePerfect(root.left, heightVal - 1);
            root.right = makePerfect(root.right, heightVal - 1);
        } else {
            root.left = makePerfect(root.left, heightVal - 1);
            root.right = makePerfect(root.right, heightVal - 1);
        } 
    }
    return root;
}

// Helper methods for makePerfect() above
public int height() {
    return height(overallRoot);
}

private int height(IntTreeNode root) {
    if (root == null) {
        return 0;
    } else {
        return 1 + Math.max(height(root.left), height(root.right));
    }
}

/*
* Makes sure that all branches end on an even level
* If a leaf is at an odd level, it should be removed from the tree
* The root is defined as being on level 1
*/
public void evenLevels() {
    overallRoot = evenLevels(overallRoot, 1);
}

private IntTreeNode evenLevels(IntTreeNode root, int level) {
    if (root != null) {
        if (root.left == null && root.right == null && (level % 2 != 0)) {
            root = null;
        } else {
            root.left = evenLevels(root.left, level + 1);
            root.right = evenLevels(root.right, level + 1);
        }
    }
    return root;
}

/*
* Returns a list containing the sequence of values obtained from an inorder binary tree traversal
*/
public ArrayList<Integer> inOrderList() {
    ArrayList<Integer> inOrderList = new ArrayList<Integer>();
    return inOrderList(overallRoot, inOrderList);
}

private ArrayList<Integer> inOrderList(IntTreeNode root, ArrayList<Integer> inOrderList) {
    if (root != null) {
        if (root.left == null && root.right == null) {
            inOrderList.add(root.data);
        } else {
            inOrderList(root.left, inOrderList);
            inOrderList.add(root.data);
            inOrderList(root.right, inOrderList);
        }
    }
    return inOrderList;
}

/*
* - Accepts another binary tree of integers as a parameter
* - Combines it with this tree to return a new third tree
* - New tree should should be a union of the 2 original trees
* - For the nodes of the new tree, store a 1 if just the first tree 
* had the node, 2 if just the second tree had the node, 3 if both trees had the node
*/
public IntTree combineWith(IntTree t2) {
    IntTree t3 = new IntTree();
    t3.overallRoot = combineWith(this.overallRoot, t2.overallRoot, t3.overallRoot);
    return t3;
}

private IntTreeNode combineWith(IntTreeNode root1, IntTreeNode root2, IntTreeNode root3) {
    if (root1 == null && root2 == null) {
        root3 = null;
    } else if (root1 != null && root2 == null) {
        root3 = new IntTreeNode(1);
        root3.left = combineWith(root1.left, root2, root3.left);
        root3.right = combineWith(root1.right, root2, root3.right);
    } else if (root1 == null && root2 != null) {
        root3 = new IntTreeNode(2);
        root3.left = combineWith(root1, root2.left, root3.left);
        root3.right = combineWith(root1, root2.right, root3.right);
    } else {
        root3 = new IntTreeNode(3);
        root3.left = combineWith(root1.left, root2.left, root3.left);
        root3.right = combineWith(root1.right, root2.right, root3.right);
    }
    return root3;
}

/*
* Eliminates branch nodes that have only 1 child
*/
public void tighten() {
    overallRoot = tighten(overallRoot);
}

private IntTreeNode tighten(IntTreeNode root) {
    if (root != null) {
        if (root == null) {
            root = null;
        } else if (root.left == null && root.right != null) {
            root = tighten(root.right);
        } else if (root.left != null && root.right == null) {
            root = tighten(root.left);
        } else {
            root.left = tighten(root.left);
            root.right = tighten(root.right);
        }
    }
    return root;
}

/*
* Accepts minimum and maximum integers as parameters and removes any tree elements that 
* are not within that range, inclusive
* Assume the tree is a binary search tree (BST) & that elements are in valid BST order
*/
public void trim(int min, int max) {
    overallRoot = trim(overallRoot, min, max);
}

private IntTreeNode trim(IntTreeNode root, int min, int max) {
    if (root == null) {
        root = null;
    } else if (root.data < min) {
        root = trim(root.right, min, max);
    } else if (root.data > max) {
        root = trim(root.left, min, max);
    } else {
        root.left = trim(root.left, min, max);
        root.right = trim(root.right, min, max);
    }
    return root;
}

/*
* Accepts an integer n as a parameter and adds nodes to a tree so that the first n levels 
* are complete. A level is complete if every possible node at that level is not null. 
* Overall root is at level 1. Preserves any existing nodes in the tree. 
* Any new nodes added have -1 as their data.
*/
public void completeToLevel(int n) {
    if (n < 1) {
        throw new IllegalArgumentException();
    } else {
        overallRoot = completeToLevel(overallRoot, n);
    }
}

private IntTreeNode completeToLevel(IntTreeNode root, int n) {
    if (root == null || n > 1) {
        if (root == null && n == 1) {
            root = new IntTreeNode(-1);
        } else if (root == null && n > 1) {
            root = new IntTreeNode(-1);
            root.left = completeToLevel(root.left, n - 1);
            root.right = completeToLevel(root.right, n - 1);
        } else {
            root.left = completeToLevel(root.left, n - 1);
            root.right = completeToLevel(root.right, n - 1);
        }
    }
    return root;
}

/*
* Removes the leaves from a tree
*/
public void removeLeaves() {
    overallRoot = removeLeaves(overallRoot);
}

public IntTreeNode removeLeaves(IntTreeNode root) {
    if (root != null) {
        if (root.left == null && root.right == null) {
            root = null;
        } else {
            root.left = removeLeaves(root.left);
            root.right = removeLeaves(root.right);
        }
        
    }
    return root;
}

/*
* Changes the data stored in a binary tree, assigning sequential integers starting with 1 
* to each node so that a pre-order traversal will produce the numbers in order(1, 2, 3, etc.).
* Returns a count of how many nodes were in the tree
*/
public int numberNodes() {
    int numNodes = 0;
    return numberNodes(overallRoot, numNodes);
}

private int numberNodes(IntTreeNode root, int numNodes) {
    if (root == null) {
        return numNodes;
    } else {
        numNodes++;
        root.data = numNodes;
        numNodes = numberNodes(root.left, numNodes);
        numNodes = numberNodes(root.right, numNodes);
        return numNodes;
    }
}

/*
* Doubles all data values greater than 0 in a binary tree of integers
*/
public void doublePositives() {
    doublePositives(overallRoot);
}

public void doublePositives(IntTreeNode root) {
    if (root != null) {
        if (root.data > 0) {
            root.data *= 2;
        }
        doublePositives(root.left);
        doublePositives(root.right);
    }
}

/*
* Accepts another binary tree of integers as a parameter and compares the two trees to see 
* if they are equal to each other. 2 trees are considered equal if they have exactly the 
* same structure and store the same values.
*
*/
public Boolean equals2(IntTree tree2) {
    return equals2(this.overallRoot, tree2.overallRoot);
}

public Boolean equals2(IntTreeNode root1, IntTreeNode root2) {
    if (root1 == null && root2 == null) {
        return true;
    } else if (root1.left == null && root1.right == null && root2.left == null && root2.right == null && root1.data == root2.data) {
        return true;
    } else if (root1.left != null && root1.right != null && root2.left != null && root2.right != null && root1.left.data == root2.left.data && root1.right.data == root2.right.data && (equals2(root1.left, root2.left) && equals2(root1.right, root2.right))) {
        return true;
    } else if (root1.left != null && root2.left != null && root1.right == null && root2.right == null && root1.left.data == root2.left.data && equals2(root1.left, root2.left)) {
        return true;
    } else if (root1.left == null && root2.left == null && root1.right != null && root2.right != null && root1.right.data == root2.right.data && equals2(root1.right, root2.right)) {
        return true;
    } else {
        return false;
    }
}

/*
* Returns "empty" for an empty tree. For a leaf node, it returns the data in the node as a String.
* For a branch node, it returns a parenthesized String that has 3 elements separated by commas:
* 	1. The data at the root.
*	2. A String representation of the left subtree.
*	3. A String representation of the left subtree.
*/
public String toString2() {
    return toString2(overallRoot);
}

public String toString2(IntTreeNode root) {
    if (root == null) {
        return "empty";
    } else if (root.left == null && root.right == null) {
        return Integer.toString(root.data);
    } else {
        return "(" + root.data + ", " + toString2(root.left) + ", " + toString2(root.right) + ")";
    }
}


/*
* Returns whether or not a binary tree is full
* A full binary tree is one in which every node has 0 or 2 children
* The empty tree is considered full
*/
public Boolean isFull() {
    return isFull(overallRoot);
}

private Boolean isFull(IntTreeNode root) {
    if (root == null || (root.left == null && root.right == null)) {
        return true;
    } else if ((root.left != null && root.right == null) || (root.left == null && root.right != null)) {
        return false;
    } else {
        return (isFull(root.left) && isFull(root.right));
    }
}

/*
* Outputs the leaves of a binary tree from right to left
*
*/
public void printLeaves() {
    if (this.overallRoot == null) {
        System.out.println("no leaves");
    } else {
        System.out.print("leaves: ");
        printLeaves(overallRoot);
    }
}

public void printLeaves(IntTreeNode root) {
    if (root != null) {
        if (root.left == null && root.right == null) {
            System.out.print(root.data + " ");
        } else {
            printLeaves(root.right);
            printLeaves(root.left);
        }
    }
}

/*
* Accepts an integer parameter n and prints the values at level n from the left to right,
* one per line. overallRoot is at level 1. Throws an IllegalArgumentException if passed a 
* value for a level n that is less than 1.
*
*/
public void printLevel(int n) {
    if (n < 1) {
        throw new IllegalArgumentException();
    } else {
        printLevel(overallRoot, n);
    }
}

private void printLevel(IntTreeNode root, int level) {
    if (root != null) {
        if (level == 1) {
            System.out.println(root.data);
        } else {
            printLevel(root.left, level - 1);
            printLevel(root.right, level - 1);
        }
    }
    
}

/*
* Returns the number of branch nodes in a binary tree that contain even numbers.
* A branch node is one that has 1 or 2 children (i.e., not a leaf node). 
* An empty tree has 0 even branches
*/
public int countEvenBranches() {
    return countEvenBranches(overallRoot);
}

private int countEvenBranches(IntTreeNode root) {
    if (root == null) {
        return 0;
    } else if (root.left != null || root.right != null) {
        if (root.data % 2 == 0) {
            return 1 + countEvenBranches(root.left) + countEvenBranches(root.right);
        } else {
            return countEvenBranches(root.left) + countEvenBranches(root.right);
        }
    } else {
        return 0;
    }   

}

/*
* Returns the sum of the values stored in a binary tree of integers weighted by the depth
* of each value. Returns the value at the overallRoot plus 2 times the values stored at the 
* next level of the tree plus 3 times the values stored at the next level of the tree plus 4 
* times the values stored at the next level of the tree and so on.
*
*/
public int depthSum() {
    int depth = 1;
    return depthSum(overallRoot, depth);
}

private int depthSum(IntTreeNode root, int depth) {
    if (root == null) {
        return 0;
    } else {
        return depth * root.data + depthSum(root.left, depth + 1) + depthSum(root.right, depth + 1);
    }
}

/*
* Returns the number of empty branches in a tree. An empty tree is considered to 
* have one empty branch (the tree itself). For non-empty trees, it does not count 
* the total number of empty branches among the nodes of the tree. A leaf node has 
* 2 empty branches. A node with 1 non-empty child has 1 empty branch. A node
* with 2 non-empty children (a full branch) has no empty branches.
*/
public int countEmpty() {
    return countEmpty(overallRoot);
}

private int countEmpty(IntTreeNode root) {
    if (root == null) {
        return 1;
    } else if (root.left != null && root.right != null) {
        return countEmpty(root.left) + countEmpty(root.right);
    } else if (root.left != null && root.right == null) {
        return 1 + countEmpty(root.left);
    } else if (root.left == null && root.right != null) {
        return 1 + countEmpty(root.right);
    } else {
        return 2;
    }
}

/*
* Returns the number of left children in the tree.
* An empty tree has 0 left nodes.
*/
public int countLeftNodes() {
    return countLeftNodes(overallRoot);
}

private int countLeftNodes(IntTreeNode root) {
    if (root == null) {
        return 0;
    } else if (root.left != null && root.right != null) {
        return 1 + countLeftNodes(root.left) + countLeftNodes(root.right);
    } else if (root.left != null && root.right == null) {
        return 1 + countLeftNodes(root.left);
    } else if (root.left == null && root.right != null) {
        return countLeftNodes(root.right);
    } else {
        return 0;
    }
}