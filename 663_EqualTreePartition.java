/*
algorithm: tree recursion

1. get the sum of left subTree and right subTree
2. sum including current node = root.val + left + right
3. return sum containing the root

above algorithm find the sum of a root, so we need to find the sum of the root node,
and then found if there exist a descendent such that the sum(descendent) = root / 2
*/
class Solution {
    public boolean checkEqualTree(TreeNode root) {
        boolean[] flag = new boolean[1];
        int rootSum = helper(root, flag, Integer.MAX_VALUE);
        if (rootSum % 2 != 0) {//note that -13 / 2 == -1
            return false;
        }
        
        /*
        to handle case rootSum == 0, we search from leftSubtree and rightSubtree to ensure
        at least one edge is removed
        */
        helper(root.left, flag, rootSum / 2);
        if (flag[0] == true) {
            return true;
        }
        helper(root.right, flag, rootSum / 2);
        return flag[0];
    }
     //helper returns the sum of tree with a given root node, flag to record if found the target
    public int helper(TreeNode root, boolean[] flag, int target) {
        if (root == null) {
            return 0;
        } else if (flag[0]) { //already found the target
            return -1;
        }
        
        int left = helper(root.left, flag, target);
        int right = helper(root.right, flag, target);
        int curSum = root.val + left + right;
        if (curSum == target) {
            flag[0] = true;
        }
        return curSum;
    }
}