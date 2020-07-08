> Ratings: * to ***** (not so-so to review -> super worthy to review)

# Linear Scan snd Look Back 

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

### [368. Largest Divisible Subset](https://leetcode.com/problems/largest-divisible-subset/)   **

Given a set of distinct positive integers, find the largest subset such that every pair (Si, Sj) of elements in this subset satisfies:

Si % Sj = 0 or Sj % Si = 0.

If there are multiple solutions, return any subset is fine.

>Example 1:
>
>Input: [1,2,3]
>
>Output: [1,2] (of course, [1,3] will also be ok)

### [174. Dungeon Game](https://leetcode.com/problems/dungeon-game/) **

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