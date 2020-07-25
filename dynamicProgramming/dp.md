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


## 中心开花
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

## Stock Problems
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