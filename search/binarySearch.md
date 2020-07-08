### 275. H-Index II *
Given an array of citations sorted in `ascending order` (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at `least h` citations each, and the other N − h papers have `no more than` h citations each."

> Example:
>
> Input: citations = `[0,1,3,5,6]`
>
> Output: 3 
>
> Explanation: `[0,1,3,5,6]` means the researcher has 5 papers in total and each of them had 
             received 0, 1, 3, 5, 6 citations respectively. 
             Since the researcher has 3 papers with **at least 3** citations each and the remaining 
             two with **no more than 3** citations each, her h-index is 3.

```java
class Solution {
  public int hIndex(int[] citations) {
    int idx = 0, n = citations.length;
    int pivot, left = 0, right = n - 1;
    while (left <= right) {
      pivot = left + (right - left) / 2;
      if (citations[pivot] == n - pivot) return n - pivot;
      else if (citations[pivot] < n - pivot) left = pivot + 1;
      else right = pivot - 1;
    }
    return n - left;
  }
}

```

### L. Search In Bitonic Array ***
Search for a target number in a bitonic array, return the index of the target number if found in the array, or return -1.

A bitonic array is a combination of two sequence: the first sequence is a monotonically increasing one and the second sequence is a monotonically decreasing one.

> Examples:
>
> array = {1, 4, 7, 11, 6, 2, -3, -8}, target = 2, return 5.

```java
/*
if target == arr[mid]: return mid
arr[mid] >= arr[mid - 1]:
    if (target < arr[mid] && target >= arr[l]): r = mid - 1
    else: l = mid + 1
else:
    if (mid == 0 || target < arr[mid] && target >= arr[r]): l = mid + 1
    else: r = mid - 1
  
*/
public class Solution {
  public int search(int[] arr, int target) {
    int l = 0; int r = arr.length - 1;
    while (l <= r) {
      int mid = l + (r - l) / 2;
      if (arr[mid] == target) {
        return mid;
      } else if (mid > 0 && arr[mid] >= arr[mid - 1]) {
        if (target < arr[mid] && target >= arr[l]) r = mid - 1;
        else l = mid + 1;
      } else {
        if (mid == 0 || target < arr[mid] && target >= arr[r]) l = mid + 1;
        else r = mid - 1;
      }
    }
    return -1;
  }
}
```

### [441. Arranging Coins](https://leetcode.com/problems/arranging-coins/)
You have a total of n coins that you want to form in a staircase shape, where every k-th row must have exactly k coins.

Given n, find the total number of full staircase rows that can be formed.

n is a non-negative integer and fits within the range of a 32-bit signed integer.
```
Example 1:

n = 5

The coins can form the following rows:
¤
¤ ¤
¤ ¤

Because the 3rd row is incomplete, we return 2.

Example 2:

n = 8

The coins can form the following rows:
¤
¤ ¤
¤ ¤ ¤
¤ ¤

Because the 4th row is incomplete, we return 3.
```

```java
/*


n: 1 2 3 4 5 6 7 8
s: 1 1 2 2 2 3 3 3

given row i, # of coins needed to make to row i = i(i + 1) / 2

binary search:
    l = 0, r = n
    
    if mid*mid + 1 / 2 == n: return mid
    if < n: l = mid
    else: r = mid - 1
*/
class Solution {
    public int arrangeCoins(int n) {
        int l = 0;
        int r = n;
        
        while (l + 1 < r) {
            long mid = l + (r - l) / 2;
            long sum = mid * (mid + 1) / 2;
            if (sum == n) {
                return (int) mid;
            } else if (sum < n) {
                l = (int) mid;
            } else {
                r = (int) mid - 1;
            }
        }//l + 1 >= r
        long sum = (long) r * (r + 1) / 2; //tricky here, must cast
        return sum <= (long) n ? r : l;
    }
}
```