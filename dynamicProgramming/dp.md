> Ratings: * to ***** (not so-so to review -> super worthy to review)

# Linear Scan snd Look Back 

### 472. Concatenated Words

Same as Cutting Rope Problem

```java
/*
algorithm: dynamic programming
first store all words into a hashset dictionay,
then for each word w in the words list, our task become to find if we can concatenate w using
at least two words in the dictionary, for each word we run this algorithm then we can find the result

let M[i] = if we can look up word by having at least one cut in the word[0 ... i]
 word = "abc"
smallest problem:
M[0] = false since we cannot partition the word
M[1] = (case1, case2)
        case1: a | b, w/ a having one cur
        case2: a | b/ w/ a having no cut
M[2] = (case1, case2, case3)
        case1: ab | c
              M[1]  "c" in the dictionary
        ..
induction rule:
M[i] = M[j] if (M[j] || word[0 ... j] in the dictionary) && word[j + 1 ... i] in the dict
                for 0 <= j <= i - 1

time: let n = # of words, m = average length of each word, to check each word it cost O(m^3)
     so time = O(n * m^3)
        space = O(m)
*/
class Solution {
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) {
            return res;
        }
        Set<String> dict = new HashSet<>();
        for (String word : words) {
            dict.add(word);
        }
        
        for (String word : words) {
            if (canConcat(word, dict)) {
                res.add(word);
            }
        }
        return res;
    }
    public boolean canConcat(String word, Set<String> dict) {
        if (word.length() <= 0) {
            return false;
        }
        boolean[] M = new boolean[word.length()];
        M[0] = false;
        for (int i = 1; i < word.length(); i++) {
            for (int j = i - 1; j >= 0; j--) {
                if ((M[j] || dict.contains(word.substring(0, j + 1))) 
                    && dict.contains(word.substring(j + 1, i + 1))) 
                {
                    M[i] = true;
                    break;
                }
            }
        }
        return M[word.length() - 1];
    }
}
```



### 845. Longest Mountain in Array \*

```java
/*
alg1: dp 2 passes, time: O(n) space: O(n)
on first pass: dp[i] = longest ascending len ending at i
on second pass: dp[i] = longest ascending(from right to left) len ending at i

alg2: can optimize the space to O(1) in 1 pass
as we iterate, track the longest ascending & descending ending at i - 1, and update a global maximum
*/
class Solution {
    public int longestMountain(int[] A) {
        int n = A.length;
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            if (A[i] > A[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = 1;
            }
        }
        
        int max = 0;
        dp[n - 1] = 1;
        for (int i = n - 2; i > 0; i--) {
            int las = dp[i];
            if (A[i] > A[i + 1]) {
                dp[i] = dp[i + 1] + 1;
                if (las > 1) {//update only if its a peak
                    max = Math.max(max, las + dp[i] - 1);
                }
                
            } else {
                dp[i] = 1;
            }
            
        }
        return max;
    }
}
```



### 983. Minimum Cost For Tickets \*\*\*
In a country popular for train travel, you have planned some train travelling one year in advance.  The days of the year that you will travel is given as an array days.  Each day is an integer from 1 to 365.
```
Train tickets are sold in 3 different ways:

a 1-day pass is sold for costs[0] dollars;
a 7-day pass is sold for costs[1] dollars;
a 30-day pass is sold for costs[2] dollars.
The passes allow that many days of consecutive travel.  For example, if we get a 7-day pass on day 2, then we can travel for 7 days: day 2, 3, 4, 5, 6, 7, and 8.
```

Return the minimum number of dollars you need to travel every day in the given list of days.


```
Example 1:

Input: days = [1,4,6,7,8,20], costs = [2,7,15]
Output: 11
Explanation: 
For example, here is one way to buy passes that lets you travel your travel plan:
On day 1, you bought a 1-day pass for costs[0] = $2, which covered day 1.
On day 3, you bought a 7-day pass for costs[1] = $7, which covered days 3, 4, ..., 9.
On day 20, you bought a 1-day pass for costs[0] = $2, which covered day 20.
In total you spent $11 and covered all the days of your travel.
```

```java
/*
dp[i] = min costs to travel from day[0 ... i) exclusive
time: O(365)
space: O(365)
*/
class Solution {
    int[] ds = {1, 7, 30};
    public int mincostTickets(int[] days, int[] costs) {
        int n = days.length;
        if (n == 0) {
            return 0;
        }
        
        
        int[] arr = new int[366];
        for (int i = 0; i < n; i++) {
            arr[days[i]] = 1;
        }
        n = 365;
        int[] dp = new int[n + 1];
        
        //handle 1-day ticket ticket is more expensive then 2-day
        for (int i = costs.length - 2; i >= 0; i--) {
            costs[i] = Math.min(costs[i + 1], costs[i]);
        }
        
        //induction rule
        for (int i = 1; i <= n; i++) {
            if (arr[i] == 0) {
                dp[i] = dp[i - 1];
            } else {
                int c = Integer.MAX_VALUE;
                for (int j = 0; j < ds.length; j++) {
                    int idx = i - ds[j];
                    if (idx < 0) {
                        idx = 0;
                    }
                    c = Math.min(c, dp[idx] + costs[j]);
                }
                dp[i] = c;
            }
        }
        return dp[n];
    }
}
```
### [518. Coin Change II](https://leetcode.com/problems/coin-change-2/) ***

You are given coins of different denominations and a total amount of money. Write a function to compute the number of combinations that make up that amount. You may assume that you have infinite number of each kind of coin.
> Example:
>
> Input: amount = 5, coins = [1, 2, 5]
>  Output: 4
>  Explanation: there are four ways to make up the amount:
>
>      5=5
>      5=2+2+1
>      5=2+1+1+1
>      5=1+1+1+1+1

```java

/*
*Note: the traditional approach dp[i] = # of ways to make amount j using all coins would not work, since duplicated value are included

dp[i, j] = # of ways to make amount j using coins from [0, ... i)

base case: dp[0, 0] = 1, dp[0, j] = 0

induction rule:
    dp[i,j] = either use coin[i] or not use coin[i] in the combination
            = dp[i, j - coins[i]] + dp[i - 1, j]
*/

class Solution {
    public int change(int amount, int[] coins) {
        if (amount == 0) {
            return 1;
        } else if (coins.length == 0) {
            return 0;
        }
        int n = coins.length;
        int[][] dp = new int[n][amount + 1];
        for (int j = 0; j <= amount; j++) {
            dp[0][j] = j % coins[0] == 0 ? 1 : 0;
        }
        
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= amount; j++) {
                int use = 0;
                int notUse = 0;
                if (j - coins[i] >= 0) {
                    use = dp[i][j - coins[i]];
                }
                notUse = dp[i - 1][j];
                
                dp[i][j] = use + notUse;
            }
        }
        return dp[n - 1][amount];
        
    }
}
```

### [368. Largest Divisible Subset](https://leetcode.com/problems/largest-divisible-subset/)   \*\*

Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in this subset satisfies:

Si % Sj = 0 or Sj % Si = 0.

If there are multiple solutions, return any subset is fine.

>Example 1:
>
>Input: [1,2,3]
>
>Output: [1,2] (of course, [1,3] will also be ok)

### [174. Dungeon Game](https://leetcode.com/problems/dungeon-game/) \*\*

The demons had captured the princess (P) and imprisoned her in the bottom-right corner of a dungeon. The dungeon consists of M x N rooms laid out in a 2D grid. Our valiant knight (K) was initially positioned in the top-left room and must fight his way through the dungeon to rescue the princess.

The knight has an initial health point represented by a positive integer. If at any point his health point drops to 0 or below, he dies immediately.

Some of the rooms are guarded by demons, so the knight loses health (negative integers) upon entering these rooms; other rooms are either empty (0's) or contain magic orbs that increase the knight's health (positive integers).

In order to reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.

 

**Write a function to determine the knight's minimum initial health so that he is able to rescue the princess.**

For example, given the dungeon below, the initial health of the knight must be at least 7 if he follows the optimal path `RIGHT-> RIGHT -> DOWN -> DOWN`.

![](../rsrc/174eg.png)

```java
/*
let dp[i][j] = minimumHP required to reach d[m - 1][n - 1]

base case:
    dp[m - 1][n - 1] = Math.max(1, 1 - d[m - 1][n - 1]);
    
        (*max(1, 1 - d[m - 1][n - 1]) means:
            if d[m - 1][n - 1] < 0: we have a hp loss with the monster and thus we take -d[m - 1][n - 1] and + 1 for we need at least 1 hp to survive)
    
    dp[m - 1][j] = Math.max(1, dp[m - 1][j + 1] - d[m - 1][j])
    dp[i][n - 1] = Math.max(1, dp[i + 1][n - 1] - d[i][n - 1])
    
    
induction rule:
    dp[i][j] = min(Math.max(1, dp[i + 1][j] - d[i][j],
                   Math.max(1, dp[i][j + 1] - d[i][j]))
    
*/
class Solution {
    public int calculateMinimumHP(int[][] d) {
        int m = d.length;
        int n = d[0].length;
        int[][] dp = new int[m][n];
        
        //base case
        dp[m - 1][n - 1] = Math.max(1, 1 - d[m - 1][n - 1]);
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(1, dp[i + 1][n - 1] - d[i][n - 1]);
        }
        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.max(1, dp[m - 1][j + 1] - d[m - 1][j]);
        }
        
        //induction rule
        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.min(Math.max(1, dp[i + 1][j] - d[i][j]),
                   Math.max(1, dp[i][j + 1] - d[i][j]));
            }
        }
        return dp[0][0];
        
    }
}
```


### L. Longest Ascending Subarray
Given an unsorted array, find the length of the longest subarray in which the numbers are in ascending order.

```java
/*
let dp[i] = las ending at arr[i]

base case:
  dp[0] = 1

induction rule:
  dp[i] = dp[i - 1] + 1 if arr[i] > arr[i - 1]
update a global maximum
*/
public class Solution {
  public int longest(int[] arr) {
    if (arr.length == 0) {
      return 0;
    }

    int prev = 1;
    int max = prev;
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] > arr[i - 1]) {
        prev += 1;
        max = Math.max(prev, max);
      } else {
        prev = 1;
      }
    }
    return max;

  }
}

```

### L. Longest Ascending Subsequence

Given an array A[0]...A[n-1] of integers, find out the length of the longest ascending subsequence.

```java
/*
let dp[i] = las ending at arr[i]

base case:
  dp[0] = 1

induction rule:
  dp[i] = Math.max(dp[j]) + 1 if arr[i] > arr[j], for j in [0 ... i - 1]
use a global max to track the max

time: O(n^2)
space: O(n)
*/
public class Solution {
  public int longest(int[] arr) {
    // Write your solution here
    int n = arr.length;
    if (n == 0) {
      return 0;
    }
    int[] dp = new int[n];
    int max = 1;
    dp[0] = 1;
    for (int i = 1; i < n; i++) {
      dp[i] = 1;
      for (int j = i - 1; j >= 0; j--) {
        if (arr[i] > arr[j]) {
          dp[i] = Math.max(dp[i], dp[j] + 1);
        }
      }
      max = Math.max(dp[i], max);
    }

    return max;
  }
}
```

#### Binary Search O(nlogn)

- this problem can be solved using binary search, where we linear scan the input array and do a binary search for each entry

- [wiki](https://en.m.wikipedia.org/wiki/Longest_increasing_subsequence)



### L. Longest Ascending Subsequence II

Given an array A[0]...A[n-1] of integers, find out the longest ascending subsequence. If there are multiple results, then return any valid result.

```
Examples
Input: A = {5, 2, 6, 3, 4, 7, 5}
Output: [2,3,4,5]
Because [2, 3, 4, 5] is one of the longest ascending subsequences.
```

```java
/*
same as Longest Ascending Subsequence I, this time we are returning the sequence itself so we add some more logic to track

let dp[i][0] = las including arr[i], dp[i][1] = the previous index used

base case:
  dp[0][0] = 1;
  dp[0][1] = -1;  //-1 mark no previous index anymore

induction rule:
  dp[i][0] = Math.max(dp[i][0] + 1, 1), if arr[i] > arr[j] for j in [0 ... i - 1]
  dp[i][1] = the j in the previous step, -1 if no j used

time: O(n^2)
space: O(n)

*/

public class Solution {
  public int[] longest(int[] arr) {
    // Write your solution here
    int n = arr.length;
    if (n == 0) {
      return arr;
    } 

    int[][] dp = new int[n][2];
    int max = 1;
    int maxIdx = 0;
    dp[0][0] = 1;
    dp[0][1] = -1;
    
    for (int i = 1; i < n; i++) {
      dp[i][0] = 1;
      dp[i][1] = -1;

      for (int j = i - 1; j >= 0; j--) {
        if (arr[i] > arr[j]) {
          if (dp[j][0] + 1 > dp[i][0]) {
            dp[i][0] = dp[j][0] + 1;
            dp[i][1] = j;
          }
        }
      }

      if (dp[i][0] > max) {
        maxIdx = i;
        max = dp[i][0];
      }

    }

    int[] res = new int[max];
    for (int i = res.length - 1; i >= 0; i--) {
      res[i] = arr[maxIdx];
      maxIdx = dp[maxIdx][1];
    }
    return res;
  
  }
}
```

### L. Least Moves To Ascending Array
Given an integer array, what is the minimum number of operations to convert it to an ascending array.

One operation you can move one element of the array to another position.


```
Examples:

{1, 3, 2, 4}, the least moves needed is 1, move 2 to the middle of 1 and 3.
```

```java
/*
least move to ascending array is directly related to the longest ascending subsequence = length of array - # of longest ascending subsequence
time: O(n^2)
space: O(n)
*/
public class Solution {
  public int leastMoves(int[] arr) {
    int n = arr.length;
    if (n == 0) {
      return 0;
    }
    int[] las = new int[n];
    int max = 1;
    for (int i = 0; i < n; i++) {
      las[i] = 1;
      for (int j = i - 1; j >= 0; j--) {
        if (arr[i] > arr[j] && las[j] + 1 > las[i]) {
          las[i] = las[j] + 1;
        }
      }
      max = Math.max(max, las[i]);
    }
    return n - max;
  }
}
```

### L. Largest SubArray Product ***

Given an unsorted array of doubles, find the subarray that has the greatest product. Return the product.

Assumptions

The given array is not null and has length of at least 1.
Examples

`{2.0, -0.1, 4, -2, -1.5}`, the largest subarray product is `4 * (-2) * (-1.5) = 12`

```java
/*
dp:
max product including arr[i] max( min product ending at arr[i - 1] * arr[i],
                                  max * arr[i],
                                  arr[i])

preprocess to generate min/max product ending at arr[i]

time: O(n)
spcae: O(n)
*/
public class Solution {
  public double largestProduct(double[] arr) {
    int n = arr.length;
    if (n == 0) {
      return 0;
    }

    double[] min = new double[n];
    double[] max = new double[n];
    double[] dp = new double[n];

    min[0] = arr[0];
    max[0] = arr[0];
    dp[0] = arr[0];
    double maxP = arr[0];

    for (int i = 1; i < n; i++) {
      min[i] = Math.min(min[i - 1] * arr[i], Math.min(max[i - 1] * arr[i], arr[i]));
      max[i] = Math.max(max[i - 1] * arr[i], Math.max(min[i - 1] * arr[i], arr[i]));
      dp[i] = Math.max(min[i - 1] * arr[i], 
                  Math.max(
                    max[i - 1] * arr[i],
                    arr[i]
                  ));
      maxP = Math.max(dp[i], maxP);
    }

    return maxP;

  }
}

```

### L. Interleave Strings

```java
/*
let dp[i][j][k] = if can merge a[0 ... i - 1] and b[0 ... j - 1] to make c[0 ... k - 1]

base case:
dp[0][0][0] = true
dp[1][0][1] = a[0] == c[0] ? true : false
dp[0][1][1] = b[0] == c[0] ? true : false

induction rule:
dp[i][j][k] = 
  if a[i - 1] == c[k - 1] && dp[i - 1][j][k - 1]: true
  if b[j - 1] == c[k - 1] && dp[i][j - 1][k - 1]: true
  false otherwise

*/

public class Solution {
  public boolean canMerge(String a, String b, String c) {
    int m = a.length();
    int n = b.length();
    int w = c.length();
    if (m == 0 && n == 0 && w == 0) {
      return true;
    }
    
    boolean[][][] dp = new boolean[m + 1][n + 1][w + 1];
    dp[0][0][0] = true;
    for (int i = 1; i <= Math.min(m, w); i++) {
      dp[i][0][i] = a.charAt(i - 1) == c.charAt(i - 1) && dp[i - 1][0][i - 1];
    }
    for (int i = 1; i <= Math.min(n, w); i++) {
      dp[0][i][i] = b.charAt(i - 1) == c.charAt(i - 1) && dp[0][i - 1][i - 1];
    }


    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        for (int k = 1; k <= w; k++) {
          if (a.charAt(i - 1) == c.charAt(k - 1) && dp[i - 1][j][k - 1]) {
            dp[i][j][k] = true;
          } else if (b.charAt(j - 1) == c.charAt(k - 1) && dp[i][j - 1][k - 1]) {
            dp[i][j][k] = true;
          }
        }
      }
    }
    return dp[m][n][w];
  }
}

```

### L. Edit Distance    ****
Given two strings of alphanumeric characters, determine the minimum number of Replace, Delete, and Insert operations needed to transform one string into the other.

```
Examples

string one: “sigh”, string two : “asith”

the edit distance between one and two is 2 (one insert “a” at front then replace “g” with “t”).
```

```java
/*
alg: dp

let dp[i][j] = edit distance to make one[0 ... i) to two[0 ... j)

base case:
  dp[0][i] = i
  dp[0][j] = j
induction rule:
  dp[i][j] = dp[i - 1][j - 1] if one[i - 1] == two[j - 1]
           = min(replace, delete, insert)
                 (dp[i - 1][j - 1], [i - 1][j], [i][j - 1])

time: O(n^2)
space: O(n^2)
*/
public class Solution {
  public int editDistance(String one, String two) {
    // Write your solution here
    int m = one.length();
    int n = two.length();
    if (m == 0 || n == 0) {
      return m == 0 ? n : m;
    }
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) {
      dp[i][0] = i;
    }
    for (int j = 0; j <= n; j++) {
      dp[0][j] = j;
    }

    for (int i = 1; i <= m; i++) {
      for (int j = 1; j <= n; j++) {
        if (one.charAt(i - 1) == two.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1];
        } else {
          dp[i][j] = Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1])) + 1;
        }
      }
    }
    return dp[m][n];
  }
}

```
### [87. Scramble String](https://leetcode.com/problems/scramble-string/) *****
Given a string s1, we may represent it as a binary tree by partitioning it to two non-empty substrings recursively.

Below is one possible representation of s1 = "great":
```
    great
   /    \
  gr    eat
 / \    /  \
g   r  e   at
           / \
          a   t

To scramble the string, we may choose any non-leaf node and swap its two children.

For example, if we choose the node "gr" and swap its two children, it produces a scrambled string "rgeat".
    rgeat
   /    \
  rg    eat
 / \    /  \
r   g  e   at
           / \
          a   t
We say that "rgeat" is a scrambled string of "great".
Similarly, if we continue to swap the children of nodes "eat" and "at", it produces a scrambled string "rgtae".

    rgtae
   /    \
  rg    tae
 / \    /  \
r   g  ta  e
       / \
      t   a
We say that "rgtae" is a scrambled string of "great".
```

**Given two strings s1 and s2 of the same length, determine if s2 is a scrambled string of s1.**

```
Example 1:

Input: s1 = "great", s2 = "rgeat"
Output: true
Example 2:

Input: s1 = "abcde", s2 = "caebd"
Output: false
```

```java
/*
alg recursion with mem:

let dp[i][j][k] = if can scramble s1[i ... i + k - 1], and s2[k ... j + k - 1]

recursive rule:
    pick a partition size p, so we split s1 to [i .. i + p - 1] and [i + p ... i + k - 1]
    s2 to either s2[j ... j + p - 1] and [j + p ... j + k - 1], or s2[j][] and [j + k - p + 1 ... j]
    
    so if f(i, j, p) && f(i + p, j + p, k - p) || f(i, j + k - p, p) && f(i + p, j, k - p)

base case:
    f(i, j, 1) = s1[i] == s2[j]

iterative solution:
    we build dp[i][j][k] bottom up, since refering to i + ?, j + ?, k - ?, and we know base case for k = 1, we build from k [0 ... len], then i, j from [0 ... len]

time: O(n^4)
space: O(n^3)

*/
class Solution {
    public boolean isScramble(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }
        int n = s1.length();
        if (n == 0) {
          return true;  //two empty string
        }
        boolean[][][] dp = new boolean[n][n][n + 1];
        for (int k = 1; k <= n; k++) {
            for (int i = 0; i + k - 1 < n; i++) {
                for (int j = 0; j + k - 1 < n; j++) {
                    if (k == 1) {
                        dp[i][j][k] = s1.charAt(i) == s2.charAt(j);
                    } else {
                        for (int p = 1; p < k; p++) {
                            if ((dp[i][j][p] && dp[i + p][j + p][k - p]) ||
                                (dp[i][j + k - p][p] && dp[i + p][j][k - p])) {
                                dp[i][j][k] = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return dp[0][0][n];
    }
}
```

### 1824. Minimum Sideway Jumps \*\*

```java
/*
dp[i][j] = min jump to jump from lane i point j to point (n - 1)

recursive rule:
dp[i][j] = min(dp[k][j + 1] + cost to swap from lane i to lane k) for k in {0, 1, 2}
time: O(n)
space: O(n)
*/
class Solution {
    public int minSideJumps(int[] obstacles) {
        int n = obstacles.length;
        int[][] dp = new int[3][n];
        if (obstacles[n - 1] != 0) { //be careful, obstables[laneIdx] - 1 maps correctly to the lane
            dp[n - 1][obstacles[n - 1] - 1] = Integer.MAX_VALUE;
        }
        
        int minJump = 0;
        for (int j = n - 2; j >= 0; j--) {
            for (int i = 0; i < 3; i++) {
                dp[i][j] = Integer.MAX_VALUE;
                if (obstacles[j] - 1 != i) {
                    for (int k = 0; k < 3; k++) { //k = the lane to swap to
                        if (dp[k][j + 1] != Integer.MAX_VALUE && obstacles[j] - 1 != k) {
                            int laneSwapCost = k == i ? 0 : 1;
                            dp[i][j] = Math.min(dp[i][j], dp[k][j + 1] + laneSwapCost);
                        }
                    }
                }
                
            }
        }
        return dp[1][0];
    }
}
```



### 583. Delete Operation for Two Strings

```java
/*
dp[i][j] = min deletions to make word1[0 ... i] to word2[0 ... j]

dp[i][j] = dp[i - 1][j - 1] if w1[i] == w2[j]
         = min(dp[i - 1][j], dp[i][j - 1]) + 1

time: O(m*n)
space: O(m*n) can be optimized O(min(m, n))
*/
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        
        for (int j = 1; j <= n; j++) {
            dp[0][j] = j;
        }
        
        for (int i = 1; i <= m; i++) {
            dp[i][0] = i;
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
```

### 304. Range Sum Query 2D \*

```java
/*
dp: for each query, O(1), use O(mn) to store the cumulative sum dp
*/
class NumMatrix {
    int[][] dp; //dp[i][j] = sum of the rectangle from (0, 0) to (i, j)
    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        dp = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1] + matrix[i - 1][j - 1];
            }    
        }
        
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return dp[row2 + 1][col2 + 1] - dp[row2 + 1][col1] - dp[row1][col2 + 1] + dp[row1][col1];
    }
}
```



# DP as algorithm optimizations

### L. Ascending Triple I **
Determine if the given integer array has three indices such that i < j < k and a[i] < a[j] < a[k].
```
Examples:

{1, 5, 2, 4}, return true since i = 0, j = 2, k = 3

{4, 3, 2, 1}, return false
```

```java
/*
alg1: brute force is to n^2 enumerate all cases
alg2: use two helper dp, M1[i] = min from arr[0, i], M2[i] = max from arr[i, n - 1]
iterate through each arr[i] see if possible to find a j such that M1[j] < arr[j] < M2[j]
time: O(n)
space: O(n)
alg3: two pointer optimization https://www.cnblogs.com/grandyang/p/5194599.html
*/
public class Solution {
  public boolean existIJK(int[] arr) {
    int n = arr.length;
    int[] min = new int[n];
    int[] max = new int[n];
    
    for (int i = 0; i < n; i++) {
      min[i] = i == 0 ? arr[0] : Math.min(min[i - 1], arr[i]);
    }
    for (int i = n - 1; i >= 0; i--) {
      max[i] = i == n - 1 ? arr[n - 1] : Math.max(max[i + 1], arr[i]);
    }

    //iterate to see if possible
    for (int i = 1; i < n - 1; i++) {
      if (arr[i] > min[i] && arr[i] < max[i]) {
        return true;
      }
    }
    return false;
  }
}

```


### L. Product of Array Except Self   ***
Given an array of n integers where n > 1, nums, return an array output such that `output[i]` is equal to the product of all the elements of nums except `nums[i]`.

Solve it without division and in O(n).
```
For example, given [1,2,3,4], return [24,12,8,6].

Follow up:
Could you solve it with constant space complexity? (Note: The output array does not count as extra space for the purpose of space complexity analysis.)
```

```java
/*
alg: two pass dp

first pass: compute dp, st dp[i] = product from arr[0 ... i)


second pass: compte dp st dp[i] = product except arr[i] & dp2[i] = product form arr(i, n - 1]
                                = dp[i] * dp2[i], dp2[i] = dp2[i + 1] * nums[i + 1]
note space for dp2[i] can be optimized using O(1)
                              

time: O(n)
space: O(1)

*/
public class Solution {
  public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    if (n <= 1) {
      return nums;
    }
    int[] dp = new int[n];
    dp[0] = 1;

    //first pass
    for (int i = 1; i < n; i++) {
      dp[i] = dp[i - 1] * nums[i - 1];
    }

    //second pass
    int dp2 = 1;
    for (int i = n - 2; i >= 0; i--) {
      dp2 *= nums[i + 1];
      dp[i] = dp[i] * dp2;
    }
    return dp;
  }
}
```


### L. Replacements of A and B
Given a string with only character 'a' and 'b', replace some of the characters such that the string becomes in the forms of all the 'b's are on the right side of all the 'a's.

Determine what is the minimum number of replacements needed.

```
Examples:

"abaab", the minimum number of replacements needed is 1 (replace the first 'b' with 'a' so that the string becomes "aaaab").
```

```java
/*
let dp[i] = # of b on arr[0 ... i], dp2[i] = # of a on from arr[i ... n - 1]

then if arr[i] == 'a', # of min replacement = dp[i]
     if arr[i] == 'b', # of min replacement = dp2[i]

update a global minimum
time: O(n)
space: O(1)
*/
public class Solution {
  public int minReplacements(String input) {
    int n = input.length();
    if (n == 0) {
      return n;
    } 
    
    int[] B = new int[n];
    int[] A = new int[n];
    B[0] = input.charAt(0) == 'b' ? 1 : 0;
    for (int i = 1; i < n; i++) {
      B[i] = input.charAt(i) == 'b' ? 1 + B[i - 1] : B[i - 1];
    }
    A[n - 1] = input.charAt(n - 1) == 'a' ? 1 : 0;
    for (int i = n - 2; i >= 0; i--) {
      A[i] = input.charAt(i) == 'a' ? 1 + A[i + 1] : A[i + 1];
    }

    int min = Integer.MAX_VALUE;
    for (int i = 0; i < n; i++) {
      int nreplace = input.charAt(i) == 'a' ? B[i] + A[i] - 1 : i + 1 - B[i] + A[i];
      min = Math.min(min, nreplace);
    }
    return min;
  }
}

```

**Method2 Optimized with O(1) space**
```java
/*
alg: preprocesing two dp helper, dp1[i] = # of A from input[0, i], dp2[i] = # of B from input [0, i]

post processing iterate each index i:
  for i: (let n be size of input)
    n1 = # of A in [0, i] = dp1[i]
    n2 = # of A in [i + 1, n - 1] = dp1[n - 1] - dp1[i]
    n3 = # of B ... = dp2[i]
    n4 = # .... = dp2[n - 1] - dp2[i]
    to flip left side a, and right side b
      # flip left = total - # of A in [0, i] = (i + 1) - dp1[i]
      # flip right = # of A in [i + 1, n - 1] = n2
    to  flip left side b and right side a
      # flip left = dp1[i]
      # flip right = toal - # of A in [i + 1, n - 1] = (n - i) - dp2[i]
    notice we don't really need to compute dp2[i] and can derive the results
    dp1[i] = dp1[i - 1] if input[i] != A
           = dp1[i - 1] + 1 if input[i] == A
time: O(n)
space: O(n) --> improve to O(1) since only using dp[i - 1]
*/
public class Solution {
  public int minReplacements(String input) {
    if (input.length() < 2) {
      return 0;
    }
    //compute dp[n - 1]
    int total = 0;
    for (int i = 0; i < input.length(); i++) {
      total += input.charAt(i) == 'a' ? 1 : 0;
    }
    //corner case: if total = 0, means all b, return 0 since no need to replace
    if (total == 0) {
      return 0;
    }
    //iterate with dp[i]
    int dp = 0;
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < input.length(); i++) {
      dp += input.charAt(i) == 'a' ? 1 : 0;
      //# left flip = i + 1 - dp
      //# right flip = total - dp
      min = Math.min(min, i + 1 - dp + total - dp);
    }
    //flip all to a = size - total of a
    //flip all to b = total of a
    min = Math.min(min, Math.min(input.length() - total, total));
    return min;
  }
}

```

### Longest Bitonic Sequence  **
Given an array with all integers,  a sub-sequence of it is called Bitonic if it is first sorted in an ascending order, then sorted in a descending order. How can you find the length of the longest bitonic subsequence.

```
Examples:

{1, 3, 2, 1, 4, 6, 1}, the longest bitonic sub sequence is {1, 3, 4, 6, 1}, length is 5.
```

```java
/*
two dp:
let las[i] = longest ascending subsequence ending at arr[i], so[0 ... i]
lds[i] = longest descending subsequence ending at arr[i], so[i ... n - 1]

for each i:
  i as peak of bitonic is defined as las[i] + lds[i] - 1 #since i is overcounted

update a global minimum

time: O(n^2) to compute las and lds
space: O(n)

*/
public class Solution {
  public int longestBitonic(int[] arr) {
    // Write your solution here
    int n = arr.length;
    if (n == 0) {
      return 0;
    }

    int[] las = new int[n];
    int[] lds = new int[n];

    for (int i = 0; i < n; i++) {
      las[i] = 1;
      for (int j = i - 1; j >= 0; j--) {
        if (arr[i] > arr[j]) {
          las[i] = Math.max(las[i], las[j] + 1);
        }
      }
    }
    
    for (int i = n - 1; i >= 0; i--) {
      lds[i] = 1;
      for (int j = i + 1; j < n; j++) {
        if (arr[i] > arr[j]) {
          lds[i] = Math.max(lds[i], lds[j] + 1);
        }
      }
    }

    //post processing
    int max = 1;
    for (int i = 0; i < n; i++) {
      max = Math.max(max, las[i] + lds[i] - 1);
    }
    return max;
  }
}

```



### 44. Wildcard Matching
Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for `'?'` and `'*'`.

`'?' Matches any single character.`

`'*' Matches any sequence of characters (including the empty sequence).`

The matching should cover the entire input string (not partial).
```
Note:

s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like ? or *.
```
```
Example 1:

Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input:
s = "aa"
p = "*"
Output: true
Explanation: '*' matches any sequence.
Example 3:

Input:
s = "cb"
p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
Example 4:

Input:
s = "adceb"
p = "*a*b"
Output: true
Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
```
```java
/*
let dp[i][j] be if s[0 ... i] matches p[0 ... j]

base case:
  dp[0][0] = s[0] == p[0]

induction rule:
  if (match(s.charAt(i - 1), p.charAt(j - 1))) {
          dp[i][j] = dp[i - 1][j - 1];
        } else if (p.charAt(j - 1) == '*') {
          dp[i][j] = dp[i - 1][j - 1] || dp[i - 1][j] || dp[i][j - 1];
        }

recursion:
  f(s, p, i, j) return if s, p matches from [0 ... i], [0 ... j]

  base case:
  i == s.length() && j = p.length()

  recursive rule:
  if s[i] == p[j] || p[j] == ?:
    return f(s, p, i + 1, j + 1)
  if p[j] == '*':
    return f(s, p, i + 1, j + 1) || f(s, p, i + 1, j) || f(s, p, i, j + 1)
        *each represent: * matches s[i]; * matches s[i] and future s[i + 1 ...]; * does not match s[i], so s[i] matches p[j + 1 ...];
  
  return false

*/
class Solution {
    public boolean isMatch(String s, String p) {
        // s == input str, p = pattern str
        int m = s.length();
        int n = p.length();
        if (m == 0 && n == 0) {
          return true;
        }

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        //handle the trick base case where p = "***"
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = true;
            } else {
                break;
            }
        }
        for (int i = 1; i <= m; i++) {
          for (int j = 1; j <= n; j++) {
            if (match(s.charAt(i - 1), p.charAt(j - 1))) {
              dp[i][j] = dp[i - 1][j - 1];
            } else if (p.charAt(j - 1) == '*') {
              dp[i][j] = dp[i - 1][j - 1] || dp[i - 1][j] || dp[i][j - 1];
            }
          }
        }

        return dp[m][n];
    }
  public boolean match(char c, char p) {
    return p == '?' || p == c;
  }
}
```

### [10. Regular Expression Matching](https://leetcode.com/problems/regular-expression-matching) *****
Given an input string (s) and a pattern (p), implement regular expression matching with support for `'.'` and `'*'`.

`'.' Matches any single character.`

`'*' Matches zero or more of the preceding element.`
The matching should cover the entire input string (not partial).

```
Note:

s could be empty and contains only lowercase letters a-z.
p could be empty and contains only lowercase letters a-z, and characters like . or *.
```

```
Example 1:

Input:
s = "aa"
p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".
Example 2:

Input:
s = "aa"
p = "a*"
Output: true
Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
Example 3:

Input:
s = "ab"
p = ".*"
Output: true
Explanation: ".*" means "zero or more (*) of any character (.)".
Example 4:

Input:
s = "aab"
p = "c*a*b"
Output: true
Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
Example 5:

Input:
s = "mississippi"
p = "mis*is*p*."
Output: false
```

```java
//refer this problem to 44. Wildcard Matching
public class Solution {
    public boolean isMatch(String s, String p) {
        // s == input str, p = pattern str
        int m = s.length();
        int n = p.length();
        if (m == 0 && n == 0) {
          return true;
        }

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        //handle the trick base case where p = "***"
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                if (j == 1 || j > 1 && dp[0][j - 2]) {
                  dp[0][j] = true;
                }
            }
        }
        for (int i = 1; i <= m; i++) {
          for (int j = 1; j <= n; j++) {
            if (match(s.charAt(i - 1), p.charAt(j - 1))) {
              dp[i][j] = dp[i - 1][j - 1];
            } else if (p.charAt(j - 1) == '*') {
              //use * as a match
              // so (...a, ...a*) we can either use dp to look up (...,...) or (..., ...a*)
              dp[i][j] = (dp[i - 1][j - 1] || dp[i - 1][j]) && j > 1 && match(s.charAt(i - 1), p.charAt(j - 2));
              //ignore *
              if (!dp[i][j] && j > 1) {
                dp[i][j] = dp[i][j - 2];
              }
            }
          }
        }

        return dp[m][n];
    }
  public boolean match(char c, char p) {
    return p == '.' || p == c;
  }
}

```



# 中心开花



### 96. Merge Stones  ****

We have a list of piles of stones, each pile of stones has a certain weight, represented by an array of integers. In each move, we can **merge two adjacent piles** into one larger pile, the cost is the sum of the weights of the two piles. We merge the piles of stones until we have only one pile left. Determine the minimum total cost.

Assumptions

stones is not null and is length of at least 1
```
Examples

{4, 3, 3, 4}, the minimum cost is 28

merge first 4 and first 3, cost 7

merge second 3 and second 4, cost 7

merge two 7s, cost 14

total cost = 7 + 7 + 14 = 28
```

```java
/*
let dp[i][j] = cost of merging stones[i ... j]
  induction rule:
    dp[i][j] = min(dp[i][j], dp[i][k] + dp[k + 1][j] + sum[i ... j]), i <= k < j
  base case:
    dp[i][i] = 0
let sum[i] = sum stones from [0 ... i], so so sum from stons[i ... j] = sum[j] - sum[i] + stones[i]
time: O(n^3)
space: O(n^2)
*/
public class Solution {
  public int minCost(int[] stones) {
    int n = stones.length;
    if (n == 0) {
      return 0;
    }

    int[] sum = new int[n];
    for (int i = 0; i < n; i++) {
      sum[i] = i == 0 ? stones[0] : sum[i - 1] + stones[i];
    }

    int[][] dp = new int[n][n];
    for (int i = n - 2; i >= 0; i--) {
      for (int j = i + 1; j < n; j++) {
        dp[i][j] = Integer.MAX_VALUE;
        int s = sum[j] - sum[i] + stones[i];
        for (int k = i; k < j; k++) {
          dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + s);
        }
      }
    }
    return dp[0][n - 1];

  }
}
```

### [1312. Minimum Insertion Steps to Make a String Palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/)

Given a string s. In one step you can insert any character at any index of the string.

Return the minimum number of steps to make s palindrome.

A **Palindrome String** is one that reads the same backward as well as forward.

```java
/*
dp[i][j] = least insertion to make s[i...j] a palindrome

base case:
dp[i][i] = 0

induction rule:
dp[i][j] = dp[i + 1][j - 1] if s[i] == s[j] 
         = min(dp[i + 1][j] + 1,
               dp[i][j - 1] + 1)

time: O(n^2)
space: O(n^2) can be optimized to O(n) using 1 dimensional rolling array
*/
class Solution {
    public int minInsertions(String s) {
        int n = s.length();
        if (n == 0) {
          return 0;
        }

        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
          for (int j = i + 1; j < n; j++) {
            if (s.charAt(i) == s.charAt(j)) {
              dp[i][j] = dp[i + 1][j - 1];
            } else {
              dp[i][j] = Math.min(dp[i + 1][j], dp[i][j - 1]) + 1;
            }
          }
        }
        return dp[0][n - 1];
    }
}
```

# Cutting Rope

### E. Palindromic Partitioning \*

Given a string, we want to cut it into pieces such that each piece is a palindrome. Write a function to return the minimum number of cuts needed.

```java
/*
alg: cutting rope dp

build dp[][] such that dp[i][j] = if s[i ... j] is a panlindrome
build another dp2[] such that dp2[i] = minimum # of cut to partition dp2[0 ... i] into palindromes

note dp logic is the same as 中心开花 dp logic
	dp[i][j] = true if s[i] == s[j] and dp[i + 1][j - 1]
    		 = false otherwise
note dp2 logic is the same as the cutting rope
	dp2[i] = 0 if dp[0][i] == true
	       = min(dp2[j]) + 1 for j in [0 ... i - 1]
*/
  public int findMPPCuts(String st) {
      // isPalindrome[i][j] will be 'true' if the string from index 'i' to index 'j' is a palindrome
      boolean[][] isPalindrome = new boolean[st.length()][st.length()];

      // every string with one character is a palindrome
      for (int i = 0; i < st.length(); i++)
        isPalindrome[i][i] = true;

      // populate isPalindrome table
      for (int startIndex = st.length() - 1; startIndex >= 0; startIndex--) {
        for (int endIndex = startIndex + 1; endIndex < st.length(); endIndex++) {
          if (st.charAt(startIndex) == st.charAt(endIndex)) {
            // if it's a two character string or if the remaining string is a palindrome too
            if (endIndex - startIndex == 1 || isPalindrome[startIndex + 1][endIndex - 1]) {
              isPalindrome[startIndex][endIndex] = true;
            }
          }
        }
      }

      // now lets populate the second table, every index in 'cuts' stores the minimum cuts needed 
      // for the substring from that index till the end
      int[] cuts = new int[st.length()];
      for (int startIndex = st.length() - 1; startIndex >= 0; startIndex--) {
        int minCuts = st.length(); // maximum cuts
        for (int endIndex = st.length() - 1; endIndex >= startIndex; endIndex--) {
          if (isPalindrome[startIndex][endIndex]) {
            // we can cut here as we got a palindrome
            // also we dont need any cut if the whole substring is a palindrome
            minCuts = (endIndex == st.length() - 1) ? 0 : Math.min(minCuts, 1 + cuts[endIndex + 1]);
          }
        }
        cuts[startIndex] = minCuts;
      }

      return cuts[0];
  }
```

### 91. Decode Ways \*\*

A message containing letters from `A-Z` can be **encoded** into numbers using the following mapping:

```
'A' -> "1"
'B' -> "2"
...
'Z' -> "26"
```

To **decode** an encoded message, all the digits must be grouped then mapped back into letters using the reverse of the mapping above (there may be multiple ways). For example, `"11106"` can be mapped into:

- `"AAJF"` with the grouping `(1 1 10 6)`
- `"KJF"` with the grouping `(11 10 6)`

Note that the grouping `(1 11 06)` is invalid because `"06"` cannot be mapped into `'F'` since `"6"` is different from `"06"`.

Given a string `s` containing only digits, return *the **number** of ways to **decode** it*.

The answer is guaranteed to fit in a **32-bit** integer.

```java
/*
algorithm: recursion
    define recursion tree
        1. each level represents picking one VALID number
        2. suppose we picked up to "cur" chars, then we can have at most (n - cur) branches
            each means pick a single digit

                        "123"
                    /           \
1st:                1           12
                /       \
2nd:           1|2      1|23
            /
3rd:        1|2|3


Complexity:
    time: each node has at most 2 branches since # in range of 1 to 26, there are n level
          so O(2^n), but if we use memorization, O(n)
    space: O(# of level for call stack) = O(n)


algorithm2: dp, linear scan and look back on n - 1, n - 2. let M[i] be the # of ways to decode the string of length i

smallest problem:
    M[0] = 0
    M[1] = s[0] == 0 ? 0 : 1
    M[2] = s[0] == 0 && parseInt(s[0-1]) > 26 ? 0 : 2
induction rule:
    M[i] += M[i - 1], if s[i - 1] != 0
         += M[i - 2], if s[i - 2] != 0 && parseInt(s[i - 2 to i - 1]) <= 26

Complexity: time: O(n), space: O(n) and can be optimized to O(1)
*/
class Solution {
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] M = new int[s.length() + 1];
        M[0] = 1;   //for code simplification
        M[1] = s.charAt(0) == '0' ? 0 : 1;
        
        //induction rule
        for (int i = 2; i <= s.length(); i++) {
            if (s.charAt(i - 1) != '0') {
                M[i] += M[i - 1];
            }
            if (s.charAt(i - 2) != '0' && parseInt(s, i - 2, 2) != -1) {
                M[i] += M[i - 2];
            }
        }
        return M[s.length()];
    }
    public int parseInt(String s, int start, int k) {
        int num = 0;
        while (k > 0) {
            num = num*10 + (s.charAt(start++) - '0');
            k--;
        }
        return num > 26 ? -1 : num;
    }
    /*
    public int numDecodings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int[] dp = new int[s.length()];
        int res = dfs(s, 0, dp);
        return res == -1 ? 0 : res;
    }
    public int dfs(String s, int cur, int[] dp) {
        if (cur == s.length()) {
            return 1;
        } else if (cur > s.length()) {
            return -1;
        } else if (s.charAt(cur) == '0') { //cannot start with 0
            return -1;
        } else if (dp[cur] != 0) {
            return dp[cur];
        }
        
        //include one digit or two digit
        int includeOne = dfs(s, cur + 1, dp);
        dp[cur] += includeOne == -1 ? 0 : includeOne;
        if (cur <= s.length() - 2 && parseInt(s, cur, 2) != -1) {
            int includeTwo = dfs(s, cur + 2, dp);
            dp[cur] += includeTwo == -1 ? 0 : includeTwo;
        }
        return dp[cur];
    }
    public int parseInt(String s, int start, int k) {
        int num = 0;
        while (k > 0) {
            num = num*10 + (s.charAt(start++) - '0');
            k--;
        }
        return num > 26 ? -1 : num;
    }
    */
}
```



# Stock Problems

- [309. Best Time to Buy and Sell Stock with Cooldown](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)
  - How does the FSM ensures that final sold[n - 1], is the state of having AT MOST TWO Transactions? Ops
- [123. Best Time to Buy and Sell Stock III](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/)



```java
int getKthArray(Array arr, int k);
int getKth(Array A, Array B, int k, int l, int r) {

  int m1 = getKthArray(A, k / 2);
  int m2 = getKthArray(B, k / 2);
  
}
```

# You have to think differently

### 494. Target Sum
You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -. For each integer, you should choose one from + and - as its new symbol.

Find out how many ways to assign symbols to make sum of integers equal to target S.
```
Example 1:

Input: nums is [1, 1, 1, 1, 1], S is 3. 
Output: 5
Explanation: 

-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

There are 5 ways to assign symbols to make the sum of nums be target 3.
```

```java
/*
let dp[i][j] = to make sum j using nums[0 ... i]

base case:
dp[0][nums[0] + 1000] = 1
dp[0][-nums[0] + 1000] += 1 //in case they are same dp[0][j]

induction rule:
dp[i][j + nums[i]] = dp[i][j + nums[i]] + dp[i - 1][j - nums[i] + 1000]
*/
class Solution {
    public int findTargetSumWays(int[] nums, int S) {
        int[][] dp = new int[nums.length][2001];
        dp[0][nums[0] + 1000] = 1;
        dp[0][-nums[0] + 1000] += 1;
        for (int i = 1; i < nums.length; i++) {
            for (int sum = -1000; sum <= 1000; sum++) {
                if (dp[i - 1][sum + 1000] > 0) {
                    dp[i][sum + nums[i] + 1000] += dp[i - 1][sum + 1000];
                    dp[i][sum - nums[i] + 1000] += dp[i - 1][sum + 1000];
                }
            }
        }
        return S > 1000 ? 0 : dp[nums.length - 1][S + 1000];
    }
}
```

### 1510. stone game IV
```java

class Solution {
    public boolean winnerSquareGame(int n) {
        HashMap<Integer, Boolean> cache = new HashMap<>();
        cache.put(0, false);
        return dfs(cache, n);
    }

    public static boolean dfs(HashMap<Integer, Boolean> cache, int remain) {
        if (cache.containsKey(remain)) {
            return cache.get(remain);
        }
        int sqrt_root = (int) Math.sqrt(remain);
        for (int i = 1; i <= sqrt_root; i++) {
            // if there is any chance to make the opponent lose the game in the next round,
            // then the current player will win.
            if (!dfs(cache, remain - i * i)) {
                cache.put(remain, true);
                return true;
            }
        }
        cache.put(remain, false);
        return false;
    }
}

```



# Knapsack Problems

### E. Subset Sum

Given a set of positive numbers, determine if there exists a subset whose sum is equal to a given number ‘S’.

Example 1:

```
Input: {1, 2, 3, 7}, S=6
Output: True
The given set has a subset whose sum is '6': {1, 2, 3}
```



```
knapsack problem using dp[i][s]

dp[i][s] = if possible to make sum s using index from [0 ... i]
dp[i][s] = if (include arr[i], not include arr[i])
		 = if (dp[i - 1][s - arr[i]], dp[i - 1][s])

base case: 
	dp[0][s] = if arr[0] == s
	dp[i][0] = true
time: O(n * s)
```



### 416. Partition Equal Subset Sum \*\*

Problem Description: Given a **non-empty** array `nums` containing **only positive integers**, find if the array can be partitioned into two subsets such that the sum of elements in both subsets is equal.

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int totalSum = 0;
        // find sum of all array elements
        for (int num : nums) {
            totalSum += num;
        }
        // if totalSum is odd, it cannot be partitioned into equal sum subset
        if (totalSum % 2 != 0) return false;
        int subSetSum = totalSum / 2;
        int n = nums.length;
        Boolean[][] memo = new Boolean[n + 1][subSetSum + 1];
        return dfs(nums, n - 1, subSetSum, memo);
    }

    public boolean dfs(int[] nums, int n, int subSetSum, Boolean[][] memo) {
        // Base Cases
        if (subSetSum == 0)
            return true;
        if (n == 0 || subSetSum < 0)
            return false;
        // check if subSetSum for given n is already computed and stored in memo
        if (memo[n][subSetSum] != null)
            return memo[n][subSetSum];
        boolean result = dfs(nums, n - 1, subSetSum - nums[n - 1], memo) ||
                dfs(nums, n - 1, subSetSum, memo);
        // store the result in memo
        memo[n][subSetSum] = result;
        return result;
    }
}
```

### E. Minimum Subset Sum Difference

Problem Description:

Given a set of positive numbers, partition the set into two subsets with a minimum difference between their subset sums

**brute force recursion:**

```
rec(int[] arr, int i, int s1, int s2) => minimum difference from array[i ... n - 1] with the sum of subset1 = s1, and similarly for s2 {
	if i == n:
		return abs(s1 - s2)
	
	int diff1 = rec(arr, i, s1 + arr[i], s2);
	int diff2 = rec(arr, i, s1, s2 + arr[i]);
	return Math.min(diff1, diff2);
}
time: O(2^n)
space: O(n)
```

**top down memorization**

```
rec(int[] arr, int i, int s1, int s2, Integer[][][] dp) {
	if i == n:
		return abs(s1 - s2)
	if dp[i][s1][s2] != null:
		return dp[i][s1][s2]
	
	int diff1 = rec(arr, i, s1 + arr[i], s2, dp);
	int diff2 = rec(arr, i, s1, s2 + arr[i], dp);
	dp[i][s1][s2] = Math.min(diff1, diff2)
	return dp[i][s1][s2]
}
time: O(n * s^2)
```

**top down memorization optimized**

- notice that its no need to maintain a dp array with both the presence of s1 and s2, since s1 and s2 is complementary, so `dp[i][s1 = x]` always implies `dp[i][s1 = x][s2 = y]` (think this as when index i and sum of subset 1 is fixed, that means sum of subset 2 can only be using elements from arr[0 ... i] other than the elements used for subset 1)

```
rec(int[] arr, int i, int s1, int s2, Integer[][] dp) {
	if i == n:
		return abs(s1 - s2)
	if dp[i][s1] != null:
		return dp[i][s1][s2]
	
	int diff1 = rec(arr, i, s1 + arr[i], s2, dp);
	int diff2 = rec(arr, i, s1, s2 + arr[i], dp);
	dp[i][s1][s2] = Math.min(diff1, diff2)
	return dp[i][s1][s2]
}
time: O(n * s)
```

**bottom up**

- note we cannot just use the solution as definition of dp array, `dp[i][s1] = min difference between subset1 & 2 from arr[0 ... i]`, since at each step when compute the minimum difference, we don't know if `arr[i]` goes in s1 or s2

```
dp[i][s1] = min difference between subset1 & 2 from arr[0 ... i]

induction rule:
	dp[i][s1] = min(include or not include the arr[i] into subset 1)
			  = min(dp[i - 1][s1 - arr[i]] ????? this is problematic, dp[i - 1][s1])
```

- correct way is build a dp array as following

```
dp[i][s1] = if possible to make s1 using elements from arr[0 ... i], here max(s1) = sum(arr), but can be sum(arr) / 2 since the solution partition always yield a smaller partition with sum(s1 or s2) <= sum(arr)

after building such dp, we linear scan dp[n - 1][all possible s] to compute different possible s1 and s2, and then update a global minimum difference

induction rule:
	dp[i][s1] = if possible to make s1 including/not including arr[i]
			  = if possible(dp[i - 1][s1 - arr[i]], dp[i - 1][s1])

time: O(n * s)
space: O(n * s)
```



# LaiOffer Series

## Linear Scan and look back

### 1. Longest Ascending Subsequence

Given an array A[0]...A[n-1] of integers, find out the length of the longest ascending subsequence.

```
/*
let dp[i] = las ending at arr[i]

induction rule:
  dp[i] = Math.max(dp[j]) + 1 if arr[i] > arr[j], for j in [0 ... i - 1]
use a global max to track the max

time: O(n^2)
space: O(n)
*/
```

### 682. Longest Ascending Subsequence II \*

Given an array A[0]...A[n-1] of integers, **find out the longest ascending subsequence(instead of length)**. If there are multiple results, then return any valid result.

```
same as Longest Ascending Subsequence I, this time we are returning the sequence itself so we add some more logic to track

let dp[i][0] = las including arr[i], dp[i][1] = the previous index used

base case:
  dp[0][0] = 1;
  dp[0][1] = -1;  //-1 mark no previous index anymore

induction rule:
  dp[i][0] = Math.max(dp[i][0] + 1, 1), if arr[i] > arr[j] for j in [0 ... i - 1]
  dp[i][1] = the j in the previous step, -1 if no j used

time: O(n^2)
space: O(n)
```

### 683. Count Ascending Subsequence \*\*

intuition: if let `dp[i] = las ending(including) at i`, for each `j < i and arr[j] < arr[i]`, we know `dp[j] is the las includes/ends at arr[j]`  thus adding the count of any two `dp[j] and dp[k] where j != k and j,k < i` will not overlap.

> consider the example of `arr[1,2,5]` and currently we have `dp = [1, 2, ?]`, to find `dp[2]`, we simply adds `dp[0]` and `dp[1]` since the sequence ending at 1 `seq(1, 5)` will not include 2 `seq(1,2,5)` or `seq(2, 5)`, thus adding the count will not overlap

```java
/*
let dp[i] = las ending(including) at i
base case:
dp[0] = 1
induction rule:
dp[i] = sum(dp[j]) + 1 for each a[j] < a[i] and j < i
*/
public class Solution {
  public int numIncreasingSubsequences(int[] a) {
    int n = a.length;
    int[] dp = new int[n];
    int sum = 0;
    for (int i = 0; i < n; i++) {
      dp[i] = 1;
      for (int j = i - 1; j >= 0; j--) {
        if (a[i] > a[j]) {
          dp[i] += dp[j];
        }  
      }
      sum += dp[i];
    }
    return sum;
  }
}
```

### 217. Largest Set Of Points With Positive Slope

Given an array of 2D coordinates of points (all the coordinates are integers), find the largest number of points that can form a set such that any pair of points in the set can form a line with positive slope. Return the size of such a maximal set.

```java
/*
the problem is same as longest ascending subsequence, we just need to map the 2d into 1d score to do the dp
this task is simple, let dp[i] = las ending at points[i]
then dp[i] = max(dp[j]) + 1 where j < i and points[j].x < points[i].x and points[j].y < points[i].y

time: O(n^2 + nlogn) since we need to presort the array
space: O(n) 
*/
public class Solution {
  public int largest(Point[] points) {
    int n = points.length;
    Arrays.sort(points, new MyComp());
    int[] dp = new int[n];
    int max = 0;
    for (int i = 0; i < n; i++) {
      dp[i] = 1;
      for (int j = i - 1; j >= 0; j--) {
        if (dp[j] + 1 > dp[i] && points[j].x < points[i].x && points[j].y < points[i].y) {
          dp[i] = dp[j] + 1;
        }
      }
      if (dp[i] != 1) {
        max = Math.max(dp[i], max);
      }
    }
    return max;
  }
  class MyComp implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
      return p1.x == p2.x ? p1.y - p2.y : p1.x - p2.x;
    }
  }
}
```





### 87. Max Product Of Cutting Rope

- Key note: 这里需要考虑的是计算dp[i]时，中间切一刀把rope分为`[1 ... j][j + 1 ... i]`, `[j + 1 ... i]`为不切的部分，==`[1 ... j]`为切，或者不切的部分，切我们用`dp[j]`来索引，否则我们用`j`作为 `[1 ...  j]`部分的长度，因为有可能 `j > dp[j]`==

Problem Description:

Given a rope with positive integer-length *n*, how to cut the rope into *m* integer-length parts with length *p*[0], *p*[1], ...,*p*[*m*-1], in order to get the maximal product of *p*[0]**p*[1]* ... **p*[*m*-1]? *m* **is determined by you** and must be greater than 0 **(at least one cut must be made**). Return the max product you can have.

**Assumptions**

- n >= 2

**Examples**

- n = 12, the max product is 3 * 3 * 3 * 3 = 81(cut the rope into 4 pieces with length of each is 3).

```java
/*
algorithm: dynamic programming: linear scan and look back partitioning
let M[i] = max product of cutting the rope of length i with at least one cut

smallest problem:
M[0] = 0
M[1] = 0
M[2] = max(1, M[1]) * 1
M[3] = max(cases)       --> _ _ _ case1: _ | _ _, case2: _ _ | _
     case1 = max(1, M[1]) * 2
     case2 = max(2, M[2]) * 1
induction rule:
M[i] = max(max(j, M[j]) * (i - j)) for  1 <= j <= i - 1
*/
public class Solution {
  public int maxProduct(int length) {
    int[] M = new int[length + 1];
    for (int i = 2; i <= length; i++) {
      for (int j = i - 1; j >= 1; j--) {
        M[i] = Math.max(Math.max(j, M[j]) * (i - j), M[i]);
      }
    }
    return M[length];
  }
}
```

### 88. Array Hopper I

**Problem Description**

Given an array A of non-negative integers, you are initially positioned at index 0 of the array. **`A[i]` means the maximum jump distance from that position (you can only jump towards the end of the array).** Determine if you are able to reach the last index.

```java
/*
let dp[i] = if can reach arr[n - 1]

base case:
  dp[n - 1] = true

induction:
  dp[i] = true if dp[j] == true, for i + 1 <= j <= min(i + arr[i], n - 1)
  
time: O(n^2)
space: O(n)
*/
```

### 89. Array Hopper II

**Problem Description**

Given an array A of non-negative integers, you are initially positioned at index 0 of the array. **`A[i]` means the maximum jump distance from index `i` (you can only jump towards the end of the array).** Determine the minimum number of jumps you need to reach the end of array. If you can not reach the end of the array, return -1.

```java
/*
algorithm: dynamic programming: linear scan and look back, define dp[i] = minimum jump from index i to reach the end


base case:
  dp[n - 1] = 0

induction rule:
  dp[i] = min(dp[j]) + 1 for  i + 1 <= j <= min(i + arr[i], n - 1)
time: O(n^2)
space: O(n)
*/
```

### 100. Edit Distance \*\*\*

**Problem Description**

Given two strings of alphanumeric characters, determine the minimum number of **Replace**, **Delete**, and **Insert** operations needed to transform one string into the other.

```java
/*
rec(s1, i, s2, j)
recursive rule:
  if s1[i] == s2[j]: rec(s1, i + 1, s2, j + 1)
  else:
    remove = rec(s1, i + 1, s2, j) + 1
    replace = rec(s1, i + 1, s2, j + 1) + 1
    insert = rec(s1, i, s2, j + 1) + 1
    min(remove, replace, insert)

botom up:
  dp[i][j] = edit distance to make s1[0 ... i] to s2[0 ... j]
  dp[i][j] = dp[i - 1][j - 1] if s1[i] == s2[j]
           = min(remove = dp[i - 1][j] + 1, replace = dp[i - 1][j - 1] + 1, insert = dp[i][j - 1] + 1)
*/
```

### 101. Largest Square Of 1s \*\*\*

- Key Note: 思路是看当前的左边，左上角，上方最大可能的方形 也就是对应的`dp[i][j - 1], dp[i - 1][j - 1], dp[i - 1][j]`来决定当前的最大可能的方形

```java
/*
dp[i][j] = largest square with m[i][j] as the bottom right

dp[0][0] = 1 if m[0][0] = 1 else 0

dp[i][j] = 0 if m[i][j] = 0
         = min(dp[i - 1][j - 1], dp[i][j - 1], dp[i - 1][j]) + 1
*/
```

### 176. Longest Common Substring

```
dp[i][j] = lcs ending at s[i] and t[j]

base case:
dp[0][j] = s[0] == t[j] ? 1 : 0
dp[i][0] = ..

induction rule:
dp[i][j] = 0 if s[i] != t[j]
         = dp[i - 1][j - 1] + 1 if s[i] = t[j]

use a global max to track to maxi lcs
```

### 177. Longest Common Subsequence

```
let dp[i][j] = lcs ending at s[i] and t[j], (not necessarily including s[i], s[j]; if not intuitive, start with top-down recursive rule)

base case:
dp[0][j] = s[0] == t[j] ? 1 : 0;
dp[i][0] = s[i] == t[0] : 1 : 0;

induction rule:
dp[i][j] = dp[i - 1][j - 1] + 1 if s[i] == t[j]
         = Math.max(dp[i - 1][j], dp[i][j - 1])
```



## Composite DP

### 104. Longest Cross Of 1s \*\*\*

Given a matrix that contains only 1s and 0s, find the largest cross which contains only 1s, with the same arm lengths and the four arms joining at the central point.

Return the arm length of the largest cross.

**Example**

{ {0, 0, 0, 0},

 {1, 1, **1**, 1},

 {0, **1, 1, 1**},

 {1, 0, **1**, 1} }

the largest cross of 1s has arm length 2.

```
Algorithm:
to find the longest cross of 1s at matrix[i][j], we need to know the longest consecutive ones of its left, right, top, and bottom
for each direction we generate such dp to look up, so in total we need 4 dp arrays each cost O(n) to generate
dp1[i][j] = longest consecutive 1s from left to right horizontally ending at j
dp2[i][j] = ..... right to left horizontally ending at j
dp3[i][j] = ..... top to bottom vertivally ending at i
dp4[i][j] = ..... bottom to top vertically ending at i
time: O(n^2)
space: O(n^2)
```

### 637. Largest Square Surrounded By One \*\*

Determine the largest square surrounded by 1s in a binary matrix (a binary matrix only contains 0 and 1), return the length of the largest square.

**Example**

{{1, 0, 1, 1, 1},

 {1, **1, 1, 1**, 1},

 {1, **1, 0, 1**, 0},

 {1, **1, 1, 1**, 1},

 {1, 1, 1, 0, 0}}



The largest square surrounded by 1s has length of 3.

```java
/*
algorithm: # of squares in a M*N matrix = # of ways to select entry as  left corner * # of ways to select length of the square
                                        = M*N * min(M, N)
          brute force: iterate every entry, for every entry, iterate all arms of lengths --> the 4 sides of the square for each
          size to see if can form a sqaure of 1's, use a globalMax to track the largest such square
            for (i, j):   -->m*n
              for sizes:  -->min(m, n)
                //update globalMax if possible by iterating 4 sides of the square --> min(m, n)
          --> time O(m*n * min(m, n)^2)
          optimize time --> preprocess: generate two matricies M1, M2. such that M1[i][j] represents for ith row, longest
          consecutive 1's on jth right, M2[i][j] represents for jth column, longest consecutive 1's on ith bottom

          so our algorithm became
            for (i, j)
              for size = min(M1[i][j], M2[i][j]) to 0:
                check right corner: M2[i][j + size - 1] >= size && bot corner: M1[i + size - 1][j] >= size
                and update the globalMax
          --> time O(m*n * min(m*n)), space O(n^2)

*/
public class Solution {
  public int largestSquareSurroundedByOne(int[][] matrix) {
    //corner case
    if (matrix.length == 0 || matrix[0].length == 0) {
      return 0;
    }
    //initialize
    int m = matrix.length;
    int n = matrix[0].length;
    int[][] M1 = new int[m][n];
    int[][] M2 = new int[m][n];
    int globalMax = 0;

    //preprocessing
    for (int i = 0; i < m; i++) {
      longestOneLtoR(matrix, M1, i);  //generate each row
    }
    for (int j = 0; j < n; j++) {
      longestOneTtoB(matrix, M2, j); //generate each column
    }

    //iterate all entries to collect result
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] != 0) { //we don't collect result if entry is 0, since it cannot be the topleft corner of the square
          int MaxSize = Math.min(M1[i][j], M2[i][j]);
          for (int s = MaxSize; s >= 1; s--) { //min size is 1, iterate for each 1 <= s <= MaxSize
            if (M2[i][j + s - 1] >= s && M1[i + s - 1][j] >= s) { // if can form such square where ij is topLeft
              globalMax = Math.max(globalMax, s);
            }
          }
        }
      }
    }

    return globalMax;
  }
```

