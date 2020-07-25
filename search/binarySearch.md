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

### 4. Median of Two Sorted Arrays  ****
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays. The overall run time complexity should be `O(log (m+n))`.

You may assume nums1 and nums2 cannot be both empty.
```
Example 1:

nums1 = [1, 3]
nums2 = [2]

The median is 2.0
Example 2:

nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
```

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        boolean even = (m + n) % 2 == 0;
        //if [1,2,3,4] we finding k = 2 & 3th = 2 since helper will find both 2 and 2 + 1, if [1,2,3] we finding k = 2th smallest element
        int kth = even ? (m + n) / 2 : ((m + n) / 2) + 1;
        return kth(nums1, nums2, kth, 0, m - 1, 0, n - 1, even);
    }
    /*
    A: bounded by l1 ... r1, size m; B: bounded by l2 ... r2, size n
    algorithm: recursion, where k represents the kth element (starting from k = 1, k = 2...)
    recursive rule:
        find min(li + k/2, ri)th in A, B, we call this index m1 and m2 respectively
        if A[m1] < B[m2]: 
            1. all A[l1 ... m1] can be discarded, so there are on average k/2 elemnts removed(occasionally r2 - r1 + 1 elements removed)
            2. we recursively return kth(a, b, k - sizeOfRemoved, m1 + 1, r1, l2, r2)
            
        if A[m1] > B[m2]: all B[l2 ... m2] ...
        if A[m1] == B[m2]: either A[l1 ... m1] or B[l2 ... m2] can be discarded, we only need to keep any of the A[m1] or B[m2]
    base case:
        if l1 == a.len: return B[l1 + k - 1]
        if l2 == b.len: return A[l2 + k - 1]
        if k == 1: return a[l1] < b[l2] ? a[l1] : b[l2]
    use a flag 'two' to determine if we need to find both (n / 2) & (n / 2 + 1)th element
    */
    public double kth(int[] a, int[] b, int k, int l1, int r1, int l2, int r2, boolean two) {
        //System.out.println("k: " + k + ", l1: " + l1 + ",l2 " + l2 + ", two: " + two);
        if (l1 == a.length) {
            return two ? ((b[l2 + k - 1] + b[l2 + k]) / 2.0) : b[l2 + k - 1];
        } else if (l2 == b.length) {
            return two ? ((a[l1 + k - 1] + a[l1 + k]) / 2.0) : a[l1 + k - 1];
        } else if (k == 1) {
            if (two) {
                double first = a[l1] < b[l2] ? a[l1++] : b[l2++];
                double second;
                if (l1 == a.length || l2 == b.length) {
                    second = l1 == a.length ? b[l2] : a[l1];
                } else {
                    second = a[l1] < b[l2] ? a[l1] : b[l2];
                }
                //System.out.println(first + " " + second);
                return (first + second) / 2.0;
            }
            return a[l1] < b[l2] ? a[l1] : b[l2];
        }
        
        int m1 = Math.min(l1 - 1 + k / 2, r1);
        int m2 = Math.min(l2 - 1 + k / 2, r2);
        if (a[m1] <= b[m2]) {
            return kth(a, b, k - (m1 - l1 + 1), m1 + 1, r1, l2, r2, two);
        }
        return kth(a, b, k - (m2 - l2 + 1), l1, r1, m2 + 1, r2, two);
    }
}
```