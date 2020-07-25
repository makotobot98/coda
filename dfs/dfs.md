### [79. Word Search](https://leetcode.com/problems/word-search/) **

Given a 2D board and a word, find if the word exists in the grid.

The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those horizontally or vertically neighboring. The same letter cell may not be used more than once.

```
Example:

board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

Given word = "ABCCED", return true.
Given word = "SEE", return true.
Given word = "ABCB", return false.
```

```java
class Solution {
    int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public boolean exist(char[][] board, String word) {
        int s = word.length();
        if (s == 0) {
            return true;
        }
        
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, 0, i, j, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean dfs(char[][] board, String word, int cur, int i, int j, boolean[][] visited) {
        if (cur == word.length()) {
            return true;
        } else if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j] ||board[i][j] != word.charAt(cur)) {
            return false;
        }
        
        visited[i][j] = true;
        for (int[] dir : dirs) {
            int inext = i + dir[0];
            int jnext = j + dir[1];
            if (dfs(board, word, cur + 1, inext, jnext, visited)) {
                return true;
            }
        }
        visited[i][j] = false;
        return false;
    }
}
```


### [491. Increasing Subsequences](https://leetcode.com/problems/increasing-subsequences/) ****

Given an integer array, your task is to find all the different possible increasing subsequences of the given array, and the length of an increasing subsequence should be at least 2.

 
```
Example:

Input: [4, 6, 7, 7]
Output: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]

Example2:

input: [4,3,2,1]
output: [[1,2],[1,2,3],[1,2,3,4],[1,2,4],[1,3],[1,3,4],[1,4],[2,3],[2,3,4],[2,4],[3,4]]
explaination: no increasing subsequence

Constraints:

The length of the given array will not exceed 15.
The range of integer in the given array is [-100,100].
The given array may contain duplicates, and two equal integers should also be considered as a special case of increasing sequence.
```
```java
/*

input: [4,6,7,7]
alg: dfs
                    {4, 6, 7, 7}
                /       |       \
                4       6       7
            /       |
            4,6     4,7
            |       |
            4,6,7   4,7,7

time: O(2^n)
space: O(n)

*/
class Solution {
    public List<List<Integer>> findSubsequences(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> prefix = new ArrayList<>();
        dfs(nums, 0, prefix, res);
        return res;
    }
    public void dfs(int[] nums, int cur, List<Integer> prefix, List<List<Integer>> res) {
        if (prefix.size() > 1) {
            List<Integer> ls = new ArrayList<>();
            for (int i : prefix) {
                ls.add(i);
            }
            res.add(ls);
        }
        Set<Integer> set = new HashSet<>();
        for (int i = cur; i < nums.length; i++) {
            if (!prefix.isEmpty() && nums[i] < prefix.get(prefix.size() - 1)) {
                continue;
            } else if (set.contains(nums[i])) {
                continue;   //yes this can be simplified, for clarification only
            }
            prefix.add(nums[i]);
            set.add(nums[i]);
            dfs(nums, i + 1, prefix, res);
            prefix.remove(prefix.size() - 1);
        }
    }
}
```
