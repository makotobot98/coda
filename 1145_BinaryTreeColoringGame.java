/*
the problem is same as seeing if can count # of nodes from x.left, or x.right, or x.parent, such that we can count # of nodes, ycount, such that ycount > n - ycount

the reason for this intuition is that any node acts as a path block, meaning we pick any node i, then depends on the opponent node j, either left subtree, right subtree or the parent subtree of i will be a path block

algorithm: it's simply a variation of count # nodes in a given tree
1. locate node with value x
    *if root.val == x, we can only split tree into left, or right. so no need to count parent tree
2. count nodes in x.left, x.right, x.parent respectively
3. validate if any ycount > n - ycount, or equivalently, 2*ycount > n

time: O(n)
spcae: O(height) for recursion stack
*/
class Solution {
    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        if (root == null) {
            return true;
        }
        
        if (root.val == x) {
            return (2 * count(root.left, -1) > n) || (2 * count(root.right, -1) > n); 
        }
        
        //root is not x
        TreeNode xnode = locate(root, x);
        int nparent = count(root, x);
        if (2 * nparent > n) {
            return true;
        }
        int nleft = count(xnode.left, -1);
        if (2 * nleft > n) {
            return true;
        }
        int nright = count(xnode.right, -1);
        if (2 * nright > n) {
            return true;
        }
        return false;
    }
    //count # of nodes, if meet t, return. t is the node where we should stop counting that subtree, node t should not be counted either
    public int count(TreeNode root, int t) {
        if (root == null || root.val == t) {
            return 0;
        }
        int left = count(root.left, t);
        int right = count(root.right, t);
        return left + right + 1;
    }
    //return target node x, given root node
    public TreeNode locate(TreeNode root, int x) {
        if (root == null || root.val == x) {
            return root;
        }
        TreeNode left = locate(root.left, x);
        if (left != null) {
            return left;
        }
        TreeNode right = locate(root.right, x);
        return right;
    }
    
}