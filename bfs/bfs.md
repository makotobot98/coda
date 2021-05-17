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

## Best First Search

### [490. The Maze](https://leetcode.com/problems/the-maze/) \*\*\*\*

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

### 127. Word Ladder \*\*

Given two words (*beginWord* and *endWord*), and a dictionary's word list, find the length of shortest transformation sequence from *beginWord* to *endWord*, such that:

1. Only one letter can be changed at a time.
2. Each transformed word must exist in the word list.

**Note:**

- Return 0 if there is no such transformation sequence.
- All words have the same length.
- All words contain only lowercase alphabetic characters.
- You may assume no duplicates in the word list.
- You may assume *beginWord* and *endWord* are non-empty and are not the same.

```
Example 1:
Input:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

Output: 5

Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
return its length 5.

Example 2:
Input:
beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log"]

Output: 0

Explanation: The endWord "cog" is not in wordList, therefore no possible transformation.
```



```
algorithm: bfs

for each word in the dictionary, generate its prefix with 1-character replacement, e.g., for 'hot' in the dict, we generate('*ot', 'h*t', 'ho*') three prefixes, then we start searching from startWord with each time changing a single character to '*'. 

this follows a bfs fashion, as a prefix word might contain multiple dictionary word, e.g., '*ot' can be generated by both 'hot' and 'dot'.

algorithm works as running bfs from satrtWord, each time genearte a list of words with 1-character difference into queue, stop when we found the endWord

time: since this can be viewed as a graph where each node is a word in the dict and an edge means two words are 1 character difference, so the graph can be at most N node with N^2 edges. our search can at most visit N node with each time it cost M = length of startWord to generate the new prefix word, so O(M^2 * N)

space: O(M^2 * N)




note, this is how u compute complexity:
- there are N word in total, each of length M.
- each word has M transformations (a transformation is a new word with one character prefixed, so tansformations for 'hot' would be '*ot', 'h*t', 'ho*')
- there are in total N*M possible transformations, each transformation is mapped to a list of dictionay word
- each such list contains at most N word each M length
- so total we used space = N*M * N*M = N^2 * M^2

- time: we search at most N times due to only N nodes in the graph
- each time we need compute M transformations, and each transformation cost M to generate
- so time O(M * M * N)
```



### 1197. Minimum Knight Moves

In an **infinite** chess board with coordinates from `-infinity` to `+infinity`, you have a **knight** at square `[0, 0]`.

A knight has 8 possible moves it can make, as illustrated below. Each move is two squares in a cardinal direction, then one square in an orthogonal direction.

```java
// standard bfs, 8 directions on generation, time O(8^n), n = optimal move step, which is the returned value

class Solution {
    private final int[][] DIRECTIONS = new int[][] {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
    
    public int minKnightMoves(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);
        
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0, 0});
        
        Set<String> visited = new HashSet<>();
        visited.add("0,0");
        
        int result = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.remove();
                int curX = cur[0];
                int curY = cur[1];
                if (curX == x && curY == y) {
                    return result;
                }
                
                for (int[] d : DIRECTIONS) {
                    int newX = curX + d[0];
                    int newY = curY + d[1];
                    if (!visited.contains(newX + "," + newY) && newX >= -1 && newY >= -1) {
                        queue.add(new int[] {newX, newY});
                        visited.add(newX + "," + newY);
                    }
                }
            }
            result++;
        }
        return -1;
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
time: O(# of result) -> O(1) by counting # of possible outcomes are 9 + 8 + 7 ... + 1 ~= O(100)
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

### 126. Word Ladder II \*\*\*

The solution did well in following optimization:

- to generate the graph, for each word `wi`, we can essentially use `O(27 * len(wi))` to generate all outgoing edges for `wi`. see `getNeighbors()`
- solution separates finding the shortest transformation sequence into find the shortest transformation distance using bfs then use dfs to search for that length.
  - this way we greatly reduced the space overhead as we do not need to maintain a prefix to track previously added words
  - it also limit # of nodes and depth that dfs will traverse to only the shortest depth. Since bfs will always stop after k steps where k = shortest distance



Solution:

The basic idea is:



1). Use BFS to find the shortest distance between start and end, tracing the distance of crossing nodes from start node to end node, and store node's next level neighbors to HashMap;

2). Use DFS to output paths with the same distance as the shortest distance from distance HashMap: compare if the distance of the next level node equals the distance of the current node + 1.



```java
class Solution {
    public List<List<String>> findLadders(String start, String end, List<String> wordList) {
       HashSet<String> dict = new HashSet<String>(wordList);
       List<List<String>> res = new ArrayList<List<String>>();         
       HashMap<String, ArrayList<String>> nodeNeighbors = new HashMap<String, ArrayList<String>>();// Neighbors for every node
       HashMap<String, Integer> distance = new HashMap<String, Integer>();// Distance of every node from the start node
       ArrayList<String> solution = new ArrayList<String>();

       dict.add(start);          
       bfs(start, end, dict, nodeNeighbors, distance);                 
       dfs(start, end, dict, nodeNeighbors, distance, solution, res);   
       return res;
    }

    // BFS: Trace every node's distance from the start node (level by level).
    private void bfs(String start, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance) {
      for (String str : dict)
          nodeNeighbors.put(str, new ArrayList<String>());

      Queue<String> queue = new LinkedList<String>();
      queue.offer(start);
      distance.put(start, 0);

      while (!queue.isEmpty()) {
          int count = queue.size();
          boolean foundEnd = false;
          for (int i = 0; i < count; i++) {
              String cur = queue.poll();
              int curDistance = distance.get(cur);                
              ArrayList<String> neighbors = getNeighbors(cur, dict);

              for (String neighbor : neighbors) {
                  nodeNeighbors.get(cur).add(neighbor);
                  if (!distance.containsKey(neighbor)) {// Check if visited
                      distance.put(neighbor, curDistance + 1);
                      if (end.equals(neighbor))// Found the shortest path
                          foundEnd = true;
                      else
                          queue.offer(neighbor);
                      }
                  }
              }

              if (foundEnd)
                  break;
          }
      }

    // Find all next level nodes.    
    private ArrayList<String> getNeighbors(String node, Set<String> dict) {
      ArrayList<String> res = new ArrayList<String>();
      char chs[] = node.toCharArray();

      for (char ch ='a'; ch <= 'z'; ch++) {
          for (int i = 0; i < chs.length; i++) {
              if (chs[i] == ch) continue;
              char old_ch = chs[i];
              chs[i] = ch;
              if (dict.contains(String.valueOf(chs))) {
                  res.add(String.valueOf(chs));
              }
              chs[i] = old_ch;
          }

      }
      return res;
    }

    // DFS: output all paths with the shortest distance.
    private void dfs(String cur, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance, ArrayList<String> solution, List<List<String>> res) {
        solution.add(cur);
        if (end.equals(cur)) {
           res.add(new ArrayList<String>(solution));
        } else {
           for (String next : nodeNeighbors.get(cur)) {            
                if (distance.get(next) == distance.get(cur) + 1) {
                     dfs(next, end, dict, nodeNeighbors, distance, solution, res);
                }
            }
        }           
       solution.remove(solution.size() - 1);
    }
}

```



### 1765. Map of Highest Peak

```java
class Solution {
    int[][] dirs = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
    public int[][] highestPeak(int[][] isWater) {
        int m = isWater.length;
        int n = isWater[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<List<Integer>> q = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    visited[i][j] = true;
                    q.offer(Arrays.asList(i, j));
                }
            }
        }
        
        int level = 0;
        while (!q.isEmpty()) {
            int s = q.size();
            for (int i = 0; i < s; i++) {
                List<Integer> pair = q.poll();
                int x = pair.get(0);
                int y = pair.get(1);
                isWater[x][y] = level;
                for (int[] dir : dirs) {
                    int inext = x + dir[0];
                    int jnext = y + dir[1];
                    if (inext >= 0 && jnext >= 0 && inext < m && jnext < n && !visited[inext][jnext]) 
                    {
                        visited[inext][jnext] = true;
                        q.offer(Arrays.asList(inext, jnext));
                    }
                }
            }
            level++;
        }
        return isWater;
    }
}
```



### 854. K-Similar Strings

following is a solution that i copied, idea follows by BFS to all possible neighbor into cutting redundant neighbor generations.



**Logical Thinking**
In fact, the essence of the problem is to get the minimum number of swaps A needs to make itself equal to B.



It is a **shortest-path** problem so we can utilize **BFS**. The `graph` we build sets all possible strings that can be swapped from A as `node`s, and an `edge` exists if one string can be transformed into the other by one swap. We start at `A`, and target at `B`.



However, that will cause TLE.



We set all possible strings that can be formed by swapping the positions of two letters in A' one time as neighbors of A', however, only those can make A and B differ less are **meaningful neighbors**. That is, **if A'[i] != B[i] but A'[j] == B[i], the string formed by swap(A, i, j)** is a meaningful neighbor of A'. Please note that we just need to swap the first pair (A'[i], A'[j]) we meet because the order of swap doesn't matter.



**Clear Java Code**



```java
    public int kSimilarity(String A, String B) {

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(A);
        visited.add(A);
        int level = 0;

        while (!queue.isEmpty()) {
            int sz = queue.size();
            for (int i = 0; i < sz; i++) {
                String curNode = queue.poll();
                if (curNode.equals(B)) {
                    return level;
                }
                for (String neighbour : getNeighbours(curNode, B)) {
                    if (!visited.contains(neighbour)) {
                        queue.offer(neighbour);
                        visited.add(neighbour);
                    }
                }
            }
            level++;
        }

        return -1;
    }
    
    private List<String> getNeighbours(String S, String B) { 
        
        List<String> result = new ArrayList<>();
        char[] arr = S.toCharArray(); 
        
        int i = 0;
        for (; i < arr.length; i++) {
            if (arr[i] != B.charAt(i)) {
                break;
            }
        }
                
        for (int j = i + 1; j < arr.length; j++) { 
            if (arr[j] == B.charAt(i)) {
                swap(arr, i, j);             
                result.add(new String(arr));
                swap(arr, i, j);
            }
        }     
        
        return result;
    }
    
    private void swap(char[] arr, int i, int j) {
        
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
```

