# BFS

### [103. Binary Tree Zigzag Level Order Traversal](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) ***
Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).
```
For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its zigzag level order traversal as:
[
  [3],
  [20,9],
  [15,7]
]
```

```java
/*


                    3
                /       \  
                9       20
                    /       \
                    15      7
                    /       / \
                    1       2  4 
alg: deque bfs, maintain a deque so from left to right is what appears in each level

level 0: ... | 9, 20  read from left to right, offer left then right child, offerLast
level 1:     15 , 7 | ...  read from right to left, offer from right then left child, offerFirst
level 2:  ... | 1, 4, 2 read from left to right, offer left then right child, offerLast

time: O(n)
space: O(n)
*/
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Deque<TreeNode> deque = new ArrayDeque<>();
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        int p = 0;
        deque.offerFirst(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            List<Integer> ls = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                if (p % 2 == 0) {
                    TreeNode cur = deque.pollFirst();
                    ls.add(cur.val);
                    if (cur.left != null) {
                        deque.offerLast(cur.left);
                    }
                    if (cur.right != null) {
                        deque.offerLast(cur.right);
                    }
                } else {
                    TreeNode cur = deque.pollLast();
                    ls.add(cur.val);
                    if (cur.right != null) {
                        deque.offerFirst(cur.right);
                    }
                    if (cur.left != null) {
                        deque.offerFirst(cur.left);
                    }
                }
            }
            p = (p + 1) % 2;
            res.add(ls);
        }
        return res;
    }
}
```