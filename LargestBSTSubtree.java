 /*
 recursion
 1. get the left and right [min, max, countOfNode] from subtress relative to root
 2. for cur cur root be a root of bst, left[0,1] and right [0,1] != -inf, and left.max < root.key < right.min
 3. return {left.min, right.max, leftCount + rightCount + 1}, if not possible, return {-inf, -inf} instead
 
 time: O(n)
 space: O(1)
 */
public class Solution {
  public int largestBSTSubtree(TreeNode root) {
    // Write your solution here
    int[] max = new int[1];
    rec(root, max);
    return max[0];
  }
    
  //returns [min, max, # of nodes]
  public int[] rec(TreeNode root, int[] max) {
    if (root == null) {
      return new int[] {0, 0, 0};
    } else if (root.left == null && root.right == null) {
      max[0] = Math.max(max[0], 1);
      return new int[] {root.val, root.val, 1};
    }

    int[] left = rec(root.left, max);
    int[] right = rec(root.right, max);
    if (left[0] == Integer.MIN_VALUE || left[1] == Integer.MIN_VALUE || right[0] == Integer.MIN_VALUE || right[1] == Integer.MIN_VALUE) {
      return new int[] {-1, -1, -1};
    }

    if (root.left != null && root.right != null) {
      if (left[1] < root.val && right[0] > root.val) {
        int count = left[2] + right[2] + 1;
        max[0] = Math.max(max[0], count);
        return new int[] {left[0], right[1], count};
      }
    } else if (root.left != null) {
      if (left[1] < root.val) {
        int count = left[2] + 1;
        max[0] = Math.max(max[0], count);
        return new int[] {left[0], root.val, count};
      }
    } else if (root.right != null) {
      if (right[0] > root.val) {
        int count = right[2] + 1;
        max[0] = Math.max(max[0], count);
        return new int[] {root.val, right[1], count};
      }
    }

    return new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
  }
}