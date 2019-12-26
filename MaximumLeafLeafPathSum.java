public class Solution {
 public int maxPathSum(TreeNode root) {
   // Write your solution here
   if (root == null) {
     return Integer.MIN_VALUE;
   }
   int[] max = new int[1];
   max[0] = Integer.MIN_VALUE;
   helper(root, max);
   return max[0];
 }
 public int helper(TreeNode root, int[] max) {
   if (root == null) {
     return 0;
   }
   int left = helper(root.left, max);
   int right = helper(root.right, max);

   if (root.left != null && root.right != null) {
     max[0] = Math.max(max[0], left + right + root.key);
     return Math.max(left, right) + root.key;
   }

   return Math.max(left, right) == 0 ? Math.min(left, right) + root.key :
     Math.max(left, right) + root.key;
 }
}