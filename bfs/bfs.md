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


### [490. The Maze](https://leetcode.com/problems/the-maze/) ****

There is a ball in a maze with empty spaces and walls. The ball can go through empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall. When the ball stops, it could choose the next direction.

Given the ball's start position, the destination and the maze, determine whether the ball could stop at the destination.

The maze is represented by a binary 2D array. 1 means the wall and 0 means the empty space. You may assume that the borders of the maze are all walls. The start and destination coordinates are represented by row and column indexes.

 
```
Example 1:

Input 1: a maze represented by a 2D array

0 0 1 0 0
0 0 0 0 0
0 0 0 1 0
1 1 0 1 1
0 0 0 0 0

Input 2: start coordinate (rowStart, colStart) = (0, 4)
Input 3: destination coordinate (rowDest, colDest) = (4, 4)

Output: true

Explanation: One possible way is : left -> down -> left -> down -> right -> down -> right.
```

```java

/*
alg: bfs from the starting point
time: O(MN)
sapce: O(MN)
*/

public class Solution {
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        int[][] dirs={{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        Queue < int[] > queue = new LinkedList < > ();
        queue.add(start);
        visited[start[0]][start[1]] = true;
        while (!queue.isEmpty()) {
            int[] s = queue.remove();
            if (s[0] == destination[0] && s[1] == destination[1])
                return true;
            for (int[] dir: dirs) {
                int x = s[0] + dir[0];
                int y = s[1] + dir[1];
                while (x >= 0 && y >= 0 && x < maze.length && y < maze[0].length && maze[x][y] == 0) {
                    x += dir[0];
                    y += dir[1];
                }
                if (!visited[x - dir[0]][y - dir[1]]) {
                    queue.add(new int[] {x - dir[0], y - dir[1]});
                    visited[x - dir[0]][y - dir[1]] = true;
                }
            }
        }
        return false;
    }
}
```



### 1291. Sequential Digits
An integer has sequential digits if and only if each digit in the number is **one more than the previous digit**.

Return a sorted list of all the integers in the range `[low, high]` inclusive that have sequential digits.

 
```
Example 1:

Input: low = 100, high = 300
Output: [123,234]
Example 2:

Input: low = 1000, high = 13000
Output: [1234,2345,3456,4567,5678,6789,12345]
```
```java
/*
alg: dfs, each time pick a digit from [1, 9], if too small, we continue to pick, else if too big, we stop picking, else if in the range, we add to the result and continue picking

let n = max len of digit
time: O(9^n)
space: O(n)


alg2: bfs, generate number each round with length n, then length n + 1 ...
time: O(# of result)
space: O(# of result)
*/
class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new ArrayList<>();
        bfs(low, high, res);
        return res;
    }
    public void bfs(int l, int r, List<Integer> res) {
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= 9; i++) {
            queue.offer(i);
        }
        
        while (!queue.isEmpty()) {
            int s = queue.size();
            for (int i = 0; i < s; i++) {
                int cur = queue.poll();
                if (cur >= l && cur <= r) {
                    res.add(cur);
                }
                
                if (cur % 10 != 9) {
                    int next = cur * 10 + (cur % 10) + 1;
                    if (next < r) {
                        queue.offer(next);
                    }
                }
                
                
            }
        }
    }
}

```