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


### 473. Matchsticks to Square

Remember the story of Little Match Girl? By now, you know exactly what matchsticks the little match girl has, please find out a way you can make one square by using up all those matchsticks. You should not break any stick, but you can link them up, and each matchstick must be used exactly one time.

Your input will be several matchsticks the girl has, represented with their stick length. Your output will either be true or false, to represent whether you could make one square using all the matchsticks the little match girl has.

```
Example 1:
Input: [1,1,2,2,2]
Output: true

Explanation: You can form a square with length 2, one side of the square came two sticks with length 1.
Example 2:
Input: [3,3,3,3,4]
Output: false

Explanation: You cannot find a way to form a square with all the matchsticks.
```

```java
/*
dfs:

recursion tree: each level represent pick a num to one of the four bucket

            {1,1,2,2,2}
        /       |       |       \
      {1,,,}   {,1}     {,,1,}  {,,,1}
      /     \
     {11,,,} {1,1,,} ...
    
time: O(4^n)
space: O(n)
*/
class Solution {
    public boolean makesquare(int[] nums) {
        if (nums.length == 0) {
            return false;
        }
        
        int[] sums = new int[4];
        
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }
        
        int d = sum / 4;
        if (d * 4 != sum) {
            return false;
        }
        
        return dfs(nums, 0, sums, d);
    }
    public boolean dfs(int[] nums, int cur, int[] sums, int s) {
        if (cur == nums.length) {
            if (sums[0] == s && sums[0] == sums[1] && sums[1] == sums[2] && sums[2] == sums[3]) {
                return true;
            }
            return false;
        }
        
        int n = nums[cur];
        for (int i = 0; i < sums.length; i++) {
            if (sums[i] + n <= s) {
                sums[i] += n;
                if (dfs(nums, cur + 1, sums, s)) {
                return true;
                }
                sums[i] -= n;
            }
        }
        return false;
    }
    
}
```

### [478. Generate Random Point in a Circle](https://leetcode.com/problems/generate-random-point-in-a-circle/) ****
Given the radius and x-y positions of the center of a circle, write a function randPoint which generates a uniform random point in the circle.
```
Note:

input and output values are in floating-point.
radius and x-y position of the center of the circle is passed into the class constructor.
a point on the circumference of the circle is considered to be in the circle.
randPoint returns a size 2 array containing x-position and y-position of the random point, in that order.
Example 1:

Input: 
["Solution","randPoint","randPoint","randPoint"]
[[1,0,0],[],[],[]]
Output: [null,[-0.72939,-0.65505],[-0.78502,-0.28626],[-0.83119,-0.19803]]
Example 2:

Input: 
["Solution","randPoint","randPoint","randPoint"]
[[10,5,-7.5],[],[],[]]
Output: [null,[11.52438,-8.33273],[2.46992,-16.21705],[11.13430,-12.42337]]
```

```java
/*
alg: Rejection Sampling
generate points instead on a square bounded by 2 * radius, then if the generated point can have a eucalidean distance(sqrt(x - xcenter)^2 + (y - ycenter)^2) less than the radius, we then accept the sample
*/
class Solution {
    double xc;
    double yc;
    double rad;
    public Solution(double radius, double x_center, double y_center) {
        this.xc = x_center;
        this.yc = y_center;
        this.rad = radius;
    }
    
    public double[] randPoint() {
        double x0 = xc - rad;
        double y0 = yc - rad;
        while (true) {
            double x = x0 + Math.random() * rad * 2;
            double y = y0 + Math.random() * rad * 2;
            if (Math.sqrt(Math.pow((x - xc), 2) + Math.pow((y - yc), 2)) <= rad) {
                return new double[]{x, y};
            }
        }
        
    }
}
```