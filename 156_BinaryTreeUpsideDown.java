/*
alg: recursion, define f returns the root of filped upsidedown tree from given root

1. get the flipped tree of left subtree, newRoot
2. root.left.right = root, root.left.left = root.right, root.right = null
3. return newRoot

*/
class Solution {
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        return rec(root);
    }
    public TreeNode rec(TreeNode root) {
        if (root.left == null) {
            return root;
        }
        TreeNode newRoot = rec(root.left);
        
        root.left.right = root;
        root.left.left = root.right;
        root.left = null;
        root.right = null;
        return newRoot;
    }
}