# Recursion

## Tree Recursion

### L. Construct Binary Tree from Inorder and Postorder Traversal

Given inorder and postorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.
```
For example, given

inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7
```

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
/*
algorithm: recursion with inordr map

create a map maping tuples of (value at inorder, index at inorder) provided that inorder indices are unique
buld with post order arrary form postorder[len - 1] to left

recursion(int[] inorder, int[] postorder, Map<Integer, Integer> map, int[] cur, int l, int r):
    : map stores the inorder mapping, cur is the global index at postorder, l & r defines the boudaries in inorder array
    
recursive rule:
    root := postoder[cur[0]]    #build current root
    cur[0]--
    inorderIdx := map[root.val]
    recursively build right subtree with boundary(inorderIdx + 1, r)
    .... left subtree with boundary(l, inorderIdx - 1)

base case:
    if l > r: return null

time: O(n)
sapce: O(n)
*/
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        int[] cur = {postorder.length - 1};
        return rec(map, postorder, cur, 0, postorder.length - 1);
    }
    public TreeNode rec(Map<Integer, Integer> map, int[] postorder, int[] cur, int l, int r) {
        if (l > r) {
            return null;
        }
        
        TreeNode root = new TreeNode(postorder[cur[0]--]);
        int inorderIdx = map.get(root.val);
        root.right = rec(map, postorder, cur, inorderIdx + 1, r);
        root.left = rec(map, postorder, cur, l, inorderIdx - 1);
        return root;
    }
}
```