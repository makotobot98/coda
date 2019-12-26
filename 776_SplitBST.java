/*
steps: tree recursion
let f returns [smallerSplitedTree, biggerSplitedTree]

we have two cases for current root
    case a:
        root.val <= V: get the splited results from right subtree 
        root.right = rightResult[0]
        return {root, rightResult[1]}
    case B:
        root.val > V: get the splited results left subtree 
        root.left = leftResult[1]
        return {leftResult[0], root}

base case:
    if root == null: return {null, null}
*/
class Solution {
    public TreeNode[] splitBST(TreeNode root, int V) {
        if (root == null) {
            return new TreeNode[]{null, null};
        }
        if (root.val <= V) {
            TreeNode[] right = splitBST(root.right, V);
            root.right = right[0];
            return new TreeNode[]{root, right[1]};
        }
        TreeNode[] left = splitBST(root.left, V);
        root.left = left[1];
        return new TreeNode[]{left[0], root};
    }
}