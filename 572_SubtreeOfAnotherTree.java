/*
alg: dfs
1. get the left # of nodes and right # of nodes
2. if total = t.total nodes, run euqal tree
3. return total # of nodes in current tree
*/
class Solution {
    public boolean isSubtree(TreeNode s, TreeNode t) {
        int tnodes = nnodes(t);
        boolean[] res = new boolean[1];
        dfs(s, tnodes, t, res);
        return res[0];
    }
    public int nnodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = nnodes(root.left);
        int right = nnodes(root.right);
        return left + right + 1;
    }
    public int dfs(TreeNode root, int k, TreeNode t, boolean[] res) {
        if (root == null) {
            return 0;
        } else if (res[0]) {
            return -1;
        }
        
        int left = dfs(root.left, k, t, res);
        int right =dfs(root.right, k, t, res);
        if (left + right + 1 == k) {
            if (equalTree(root, t)) {
                res[0] = true;
                return -1;
            }
        }
        return left + right + 1;
    }
    public boolean equalTree(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        } else if (a == null || b == null) {
            return false;
        } else if (a.val != b.val) {
            return false;
        }
        
        return equalTree(a.left, b.left) && equalTree(a.right, b.right);
    }
}