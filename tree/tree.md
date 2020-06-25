# Tree Recursion

### [96. Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/) ***

Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?

Example:
```
Input: 3
Output: 5
Explanation:
Given n = 3, there are a total of 5 unique BST's:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
```

```java
/*
alg: recursion
    when constructing bst with i as root, we have left be [0 ... i - 1], right [i + 1 ... n]
    
    suppose recursive function f returns # of unique BST from [0, n]
    for i in [1, n]:
        pick a number i as root, so root = i
        # of ways to make BST with i as root  
            = # of BST formed from [0 ... i - 1] * # of BST formed from [i + 1 ... n]
            = f(i - 1) * # of BST formed from [i + 1 ... n]
            = f(i - 1) * f(n - i)  //since # BST formed from [123] = [456], structure of BST would be the same, so we can map # of BST from [456] to [123]
    
*/
class Solution {
    public int numTrees(int n) {
        if (n <= 1) {
            return n;
        }    
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[0] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                //root = j
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}
```


### [222. Count Complete Tree Nodes](https://leetcode.com/problems/count-complete-tree-nodes/) ***

Given a complete binary tree, count the number of nodes.

Note:

Definition of a complete binary tree from Wikipedia:
In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

Example:
```
Input: 
    1
   / \
  2   3
 / \  /
4  5 6

Output: 6
```

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
/*
algorithm: tree recursion and binary search
notice for a complete binary tree, either the leftSubtree of root is a full binary tree or rightSubTree is a full binary tree. To determine which side is a full binary tree, getHeight(leftChild) and getHeight(rightChild)
    1. leftHeight == rightHeight: leftSubtree is full binary tree
        # of nodes = leftSubtree + root + rightSubtree
                   = (2^leftHeight - 1) + (1) + countNodes(root.right) 
    2. leftHeight == rightHeight + 1: right is full binary tree
        # of nodes = leftSubtree + root + rightSubtree
                   = countNodes(root.left) + 1 + (2^rightHeight - 1)
    no other cases are possible since it's a complete binary tree

we implement our algorithm using recursion since each step we can obtain # of nodes from one side of the subtree(thus our problem size reduce by half) and recursively get the answer from the other side.
our recursion algorithm works as following:
    1. get leftHeight and rightHeight from child
    2. case1: leftHeight == 0
            leftsubtree is empty, so must the right,
            # of nodes in the tree is root itself = 1
       case2: rightHeight == 0 
            rightSubtree is empty but not left, 
            # of nodes in the tree = leftSubtree + root
                                   = (2^leftHeight - 1) + 1
       case3: leftHeight != 0 && rightHeight != 0
            both subtrees are non empty, which is the case above we have analyzed
            1. leftHeight == rightHeight
            2. leftHeight == rightHeight + 1
Complexity:
there are logn levels for complete binary tree, each level we run get height function that cost logn (go all the way left is the height for a complete binary tree), so intotal O((logn)^2)
space: O(logn)
*/
class Solution {
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int leftHeight = getHeight(root.left, 0);
        int rightHeight = getHeight(root.right, 0);
        if (leftHeight == 0) {
            return 1;
        }
        //leftHeight != 0 && rightHeight != 0
        if (leftHeight == rightHeight) {
            return ((int) Math.pow(2, leftHeight)) + countNodes(root.right);
        }
        return ((int) Math.pow(2, rightHeight)) + countNodes(root.left);
    }
    
    public int getHeight(TreeNode root, int level) {
        if (root == null) {
            return level;
        }
        return getHeight(root.left, level + 1);
    }
}
```