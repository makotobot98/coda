# Traversal

### 285. Inorder Successor in BST

```java
/*
alg: BST two pointer traversal
	- one pointer to traverse, while the other pointer track the previous 'Bigger' node
		- the Bigger previous node is only updated when we go left substree (when cur.val > target.val)
	- when traverser finds the target:
		- try to find the smallest node in the right subtree of target
			- if such node does not exist, return the previous 'Bigger' node
			- else return this node
time: O(height = logn)
space: O(1)
*/
class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode prev = null;
        while (root != null) {
            if (root.val == p.val) {
                TreeNode min = smallestRight(root); //finds smallest node in right subtree
                return min == null ? prev : min;
            } else if (root.val < p.val) {
                root = root.right;	//do not update the prev "bigger" node here
            } else {
                prev = root;
                root = root.left;
            }
        }
        return null;
    }
    public TreeNode smallestRight(TreeNode root) {
        if (root.right == null) {
            return null;
        } else if (root.right.left == null) {
            return root.right;
        }
        
        TreeNode prev = root.right;
        TreeNode p = root.right.left;
        while (p != null) {
            prev = p;
            p = p.left;
        }
        return prev;
    }
}
```



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


### 449. Serialize and Deserialize BST
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
algorithm: since BST is already a inorder traversal from its sorted property, we only
need one more traversal to locate the position of each node

time :
    serialize: O(n) time and O(h) for recursion call stack
    deserialize: O(n) time and O(h) for recursion call stack
*/
public class Codec {

    // Encodes a tree to a single string.
    //use preorder to serialize the string
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        String splitor = ",";
        StringBuilder sb = new StringBuilder();
        preorderSerialize(root, sb, splitor);
        sb.deleteCharAt(sb.length() - 1);   //remove the last ","
        return sb.toString();
    }
    public void preorderSerialize(TreeNode root, StringBuilder sb, String splitor) {
        if (root == null) {
            return;
        }
        sb.append(root.val);
        sb.append(splitor);
        preorderSerialize(root.left, sb, splitor);
        preorderSerialize(root.right, sb, splitor);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data.length() == 0) {
            return null;
        }
        String[] preorder = data.split(",");
        return preorderBuild(preorder, new int[1], Integer.MAX_VALUE);
    }
    /*
    same as the leetCode 1008. Construct BST from preorder, check out my post of O(n)
    with explaination of several approaches
    https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/discuss/358756/java-recursion-O(N2)-and-O(N)
    */
    public TreeNode preorderBuild(String[] preorder, int[] cur, int max) {
        if (cur[0] == preorder.length) {
            return null;
        } else if (Integer.valueOf(preorder[cur[0]]) > max) {
            return null;
        }
        
        TreeNode root = new TreeNode(Integer.valueOf(preorder[cur[0]++]));
        root.left = preorderBuild(preorder, cur, root.val);
        root.right = preorderBuild(preorder, cur, max);
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
```


### 297. Serialize and Deserialize Binary Tree
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
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preorder(root, sb, 'X', ',');
        sb.deleteCharAt(sb.length() - 1);   //delete last seperator
        return sb.toString();
    }
    public void preorder(TreeNode root, StringBuilder sb, char NULL, char SEP) {
        if (root == null) {
            sb.append(NULL);
            sb.append(SEP);
            return;
        }
        sb.append(root.val);
        sb.append(SEP);
        preorder(root.left, sb, NULL, SEP);
        preorder(root.right, sb, NULL, SEP);
    }
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        String[] s = data.split(",");
        return preorderBuild(s, new int[1], "X");
    }
    public TreeNode preorderBuild(String[] s, int[] cur, String NULL) {
        if (cur[0] == s.length || s[cur[0]].equals(NULL)) {
            cur[0]++;
            return null;
        }
        //System.out.println("at " + s[cur[0]]);
        int val = Integer.valueOf(s[cur[0]++]);
        TreeNode root = new TreeNode(val);
        root.left = preorderBuild(s, cur, NULL);
        root.right = preorderBuild(s, cur, NULL);
        return root;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));

```


### 99. Recover Binary Search Tree
```java
/*
observation: inorder of bst gives a sorted array, given that two nodes are swapped, we visualize two elements in a sorted array is swapped, so we do a inorder traversal to locate the swapped node 
    - first node is identified misplaced if its next inorder iterator is less than that node
    - second node is identified misplaced it it is less than the prev node
        e.g., 1 4 3 2 inf, here we found 4 is misplaced at 3, we found 2 is misplaced since its < 3

edge case1: two misplaced node are adjacent
    e.g., 1 3 2 4, the previous property still hold
    *but note its tricky that two nodes are spotted at the same time

recursion param: use two boolean flag to track if we have found the first and 2nd misplaced nodes, use a prev pointer to track the previously visited node (prev initially = -inf)
recursive rule:
    1. inorder traverse left subtree
    2. 
        - if found[0] && found[1], swap value
        - if root.value < prev.val, its a either a 1st,2nd or both misplaced node found
        - update the prev pointer
    3. inorder traverse right subtree
    
time: O(n)
space: O(1)
*/
class Solution {
    public void recoverTree(TreeNode root) {
        TreeNode[] found = new TreeNode[2];
        TreeNode[] prev = new TreeNode[1];
        prev[0] = new TreeNode(Integer.MIN_VALUE);
        inorder(root, found, prev);
        
        //swap
        int temp = found[0].val;
        found[0].val = found[1].val;
        found[1].val = temp;
        
    }
    public void inorder(TreeNode root, TreeNode[] found, TreeNode[] prev) {
        if (root == null) {
            return;
        }
        
        inorder(root.left, found, prev);
        if (root.val < prev[0].val) {
            if (found[0] == null) {
                found[0] = prev[0];
            } 
            if (found[0] != null) {//tricky to handle the two nodes spotted at same place, meaning two misplaced nodes are adjacent; if not adjacent, this node will be overwrite when spotted again the 2nd node
                found[1] = root;
            }
        }
        prev[0] = root;
        inorder(root.right, found, prev);
    }
}
```