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


# Traversal

### 129. Sum Root to Leaf Numbers
Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.

An example is the root-to-leaf path 1->2->3 which represents the number 123.

Find the total sum of all root-to-leaf numbers.

Note: A leaf is a node with no children.
```
Example:

Input: [1,2,3]
    1
   / \
  2   3
Output: 25
Explanation:
The root-to-leaf path 1->2 represents the number 12.
The root-to-leaf path 1->3 represents the number 13.
Therefore, sum = 12 + 13 = 25.

Example 2:

Input: [4,9,0,5,1]
    4
   / \
  9   0
 / \
5   1
Output: 1026
Explanation:
The root-to-leaf path 4->9->5 represents the number 495.
The root-to-leaf path 4->9->1 represents the number 491.
The root-to-leaf path 4->0 represents the number 40.
Therefore, sum = 495 + 491 + 40 = 1026.
```

```java
/*
straight preorder traversal with a prefix sum
time: O(n)
space: O(h)

*/
class Solution {
    public int sumNumbers(TreeNode root) {
        int[] res = new int[1];
        preorder(root, res, 0);
        return res[0];
    }
    public void preorder(TreeNode root, int[] res, int cur) {
        if (root == null) {
            return;
        } else if (root.left == null && root.right == null) {
            cur = cur * 10 + root.val;
            res[0] += cur;
            return;
        }
        
        cur = cur * 10 + root.val;
        preorder(root.left, res, cur);
        preorder(root.right, res, cur);
        
    }
}
```


### [212. Word Search II](https://leetcode.com/problems/word-search-ii/) *****
Given a 2D board and a list of words from the dictionary, find all words in the board.

Each word must be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.

 
```
Example:

Input: 
board = [
  ['o','a','a','n'],
  ['e','t','a','e'],
  ['i','h','k','r'],
  ['i','f','l','v']
]
words = ["oath","pea","eat","rain"]

Output: ["eat","oath"]
```

```java
/*
alg: brute force dfs search for each word in the array, TLE(time limit exceeded)

time: O(mnw), m = # of rows, n = # of cols, w = # of words
space: O(l), l = max length of a word


dfs:

let c = char to search in word index at cur
i,j = current coordinate of search in board

recursive rule:
     for neighbor of i,j:
        if board[i][j] == word[cur] && i,j not visited:
            add i,j to visited
            return true if dfs(neighbor)
            remove i,j from visited
    return false

base case:
    if i,j is out of bound: return false
    if cur == word.length: return true

use a hashset to track the visited coordinate
*/
class Solution {
    int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public List<String> findWords(char[][] board, String[] words) {
        int m = board.length;
        int n = board[0].length;
        Set<List<Integer>> visited;
        List<String> res = new ArrayList<>();
        for (String w : words) {
            boolean found = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (board[i][j] == w.charAt(0)) {
                        visited = new HashSet<>();
                        //System.out.println("search: i: " + i + " " + j + " " + w);
                        visited.add(Arrays.asList(i, j));
                        if (dfs(board, w, visited, i, j, 1)) {
                            res.add(w);
                            found = true;
                            break;
                        }
                    }
                }
                if (found == true) {
                    break;
                }
            }
        }
        
        return res;
    }
    public boolean dfs(char[][] b, String word, Set<List<Integer>> visited, int i, int j, int cur)
    {
        int m = b.length;
        int n = b[0].length;
        if (cur == word.length()) {
            return true;
        }
        
        char c = word.charAt(cur);
        //System.out.println(c);
        for(int[] dir : dirs) {
            int inext = i + dir[0];
            int jnext = j + dir[1];
            
            if (inext >= 0 && inext < m && jnext >= 0 && jnext < n && b[inext][jnext] == c) {
                //if inext,jnext is in the range, and next char matches
                List<Integer> coord = Arrays.asList(inext, jnext);
                //System.out.println("\tcoord: i: " + inext + ",j: " + jnext);
                if (!visited.contains(coord)) {
                    visited.add(coord);
                    //System.out.println("\tmatch: i: " + inext + ",j: " + jnext+ ",cur: " + cur);
                    if (dfs(b, word, visited, inext, jnext, cur + 1)) {
                        return true;
                    }
                    visited.remove(coord);
                }
            }
        }
        return false;
    }
}
```