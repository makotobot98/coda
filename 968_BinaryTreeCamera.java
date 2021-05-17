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
we dont put camera on leaf, since by putting the camera on its parent can always monitor more nodes. thus starting from the leaf node, we can greedily build cameras

alg1: bfs with parent pointers, starting from the leaf. Time: O(n). Space: O(n)
    1. build parent relation map, and track the degree
    2. push all edge degree = 1 nodes onto queue
    3. process queue, queue = nodes with degree = 1; initialize a set marking if a node is being watched; initializea set to track which node has camera installed
        - expand rule:
            - if current node is unwatched, install a camera to its only neighbor if not already installed
            - if current node is watched, do nothing
        - generate rule:
            - if currnt node has camera, mark all neighbor as watched
            - decrease neighbor degree by 1, push neighbors that have degree 1 onto queue


alg2: dfs

recursive rule:
    1. get child information from left and right
    2. if any of child requires cur to install camera, or current root has no parent and not marked as visited, we must install at cur node. mark all neighbor as watched, tell parent no need to install for cur node
    3. otherwise we tell parent they need to install for cur
base case:
    if current is a leaf, tell parent to install

time: O(n)
space: O(n) for usage of the hashset
*/
class Solution {
    public int minCameraCover(TreeNode root) {
        int[] res = new int[1];
        rec(root, res, null, new HashSet<TreeNode>());
        return res[0];
    }
    public boolean rec(TreeNode root, int[] res, TreeNode parent, Set<TreeNode> watched) {
        if (root == null) {
            return false;
        } else if (root.left == null && root.right == null) {
            if (parent == null) {
                res[0]++;
            }
            return true;
        }
        
        boolean l = rec(root.left, res, root, watched);
        boolean r = rec(root.right, res, root, watched);
        if ((parent == null && !watched.contains(root)) || l || r) {
            //if no more parent to request to install camera there, or left and right child told cur to install
            res[0]++;
            watched.add(root.left);
            watched.add(root.right);
            watched.add(root);
            watched.add(parent);
            return false;
        } else if (watched.contains(root)) {
            return false;
        }
        
        //!watched.contains(root) && no more parent
        return true;
    }
    
}