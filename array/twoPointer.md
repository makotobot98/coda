# Two Pointer

### 11. Container With Most Water**

we can come up with brute force $$O(N^2)$$  solution by,for each i, iterating through `h[i, N - 1]`.

For a better solution, which assumedly be $$O(N)$$ since there are not many cues for sorting, merging in this problem. 

**thus the intuition for the optimal solution is to visit each `h[i]` at most constant times.**

```java
/*
Observation:
for each i, suppose we want to find the max container bounded by j > i, to maximize the container water in [i, j]
the water in this container = width * height = (j - i) * min(h[i], h[j])
- the amount of water in [i, j] is bounded by minimum between h[i] and h[j], thus if h[i] < h[j], this container is bounded by h[i]
- we can prove that for any z in [i + 1, j], with i as the left of the container, the container [i, z] will always be smaller than container [i, j]. Because min(h[i], h[z]) <= h[i] in container [i, z], and [i, z] has container width < [i, y]
- with this intuition, by initializing i = 0, j = N - 1, by comparing each (i, j) and we can remove either i or j(whoever is equal or smaller). Since if h[i] < h[j], we found the max window(container) with i as left bound of the window

time: O(N)
space: O(1)
*/
class Solution {
    public int maxArea(int[] h) {
        int i = 0;
        int j = h.length - 1;
        int max = 0;
        while (i < j) {
            if (h[i] <= h[j]) {
                max = Math.max(max, (j - i) * h[i]);
                i++;
            } else {
                max = Math.max(max, (j - i) * h[j]);
                j--;
            }
        }
        return max;
    }
}
```



### Laicode199. Max Water Trapped I**

Given a non-negative integer array representing the heights of a list of adjacent bars. Suppose each bar has a width of 1. Find the largest amount of water that can be trapped in the histogram.

**Assumptions**

- The given array is not null

**Examples**

- { 2, 1, 3, 2, 4 }, the amount of water can be trapped is 1 + 1 = 2 (at index 1, 1 unit of water can be trapped and index 3, 1 unit of water can be trapped)

```java
/*
algorithm:
  1. brute force: for each index i, the maximum water can stored at i is min(leftMax, rightMax) - heights[i]. so for each
     index i, we can seach its left and right to determine the leftMax and rightMax for i. This takes O(n^2) runtime and
     O(1) space
  2. we can preprocess to find leftMax and rightMax using dynamic programming(leftMax[i] = max[0 ... i], rightMax[i] = max[i ... N - 1]),
     then we have M1[i] be the left max height on the left of i, and M2[i] be .... right of i. After preprocessing, 
     we can linear scan the heights array and collect the gross water stored, specifically, 
     water collected at i = min(M1[i], M2[i]) - h[i]
        example:
          heights   : [2, 1, 3, 2, 4]
          M1        : [2, 2, 3, 3, 4]
          M2        : [4, 4, 4, 4, 4]
          warer/unit: [0, 1, 0, 1, 0] --> 2
  3. no need to preprocess M1 and M2 due to the nature of increasing numbers
     notice from the step2, M1 is always in increasing order from left to right, and M2 is always in increasing order from
     right to left, and for each index i, the water can stored is min(leftMax, rightMax) - height[i]
     if we start filling the solution array from i = 0 and j = len - 1
      solution[i] = min(leftMax, rightMax) - height[i]
      case1: (2, ?) ..... (?, 4)
               --> because M2[j] is at least 4 for j > i, so the min(leftMax, rightMax) at i is 2, and we finish processing i
      case2: (4, ?) ..... (?, 2)
                            -->  because M1[j] is at least 4 for j < i, so min(leftMax, rightMax) at i is 2, and we finish processing i
      by comparing from i = 0 and j = len - 1, each time move one index and add the water can be stored, we can solve the problem
      inplace without M1, and M2 as we can compute the leftMax and rightMax at dach i in place
    algorithm: two pointer scan inward, collect  sum at each i.
      while (i <= j):
        update leftMax at i and rightMax at j
        if leftMax <= rightMax
          water at i = leftMax - heights[i];
          i++;
        if rightMax >= leftMax
          water at i = rightMax - heights[i];
          j--;
*/
public class Solution {
  public int maxTrapped(int[] heights) {
    int leftMax = 0;
    int rightMax = 0;
    int i = 0;
    int j = heights.length - 1;
    int sum = 0;
    while (i <= j) {
      leftMax = Math.max(heights[i], leftMax);
      rightMax = Math.max(heights[j], rightMax);
      if (leftMax < rightMax) {
        sum += leftMax - heights[i];
        i++;
      } else {
        sum += rightMax - heights[j];
        j--;
      }
    }
    return sum;
  }
}

```

### 912. Quick Sort

```java
/*
quick sort(arr, l, r):
recursive rule:
    pick arr[l] as pivot
    i := l + 1
    j := r
    partition [i, j] s.t. arr[l + 1, i) <= arr[pivot], arr(j, r] > arr[pivot]
    swap arr[pivot] and arr[j]
    recursively sort(arr, l, pivot - 1) and sort(arr, pivot + 1, r)
*/
class Solution {
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }
    public void quickSort(int[] arr, int l, int r) {
        if (l > r) {
            return;
        }
        
        int p = l;
        int i = l + 1;
        int j = r;
        while (i <= j) {
            if (arr[i] <= arr[p]) {
                i++;
            } else if (arr[j] > arr[p]) {
                j--;
            } else {
                swap(arr, i++, j--);
            }
        }
        swap(arr, p, j);
        p = j;
        quickSort(arr, l, p - 1);
        quickSort(arr, p + 1, r);
    }
    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```

### 986. Interval List Intersection *

```java
/*
observation:
- say two overlapping intervals i1 and i2 in l1 and l2, and i1.end < i2.end. Once we computed the overlapping interval between i1 and i2, we can safely discard i1, and still need to keep i2. This gives the intuition for using two pointer
- result overlapping intervals are non overlapping between each other. So once we computed one overlapping interval, we can immediately push it into the result set

alg: two pointer, time O(m + n), space: O(s) s = # of overlapping intervals
*/
class Solution {
    public int[][] intervalIntersection(int[][] firstList, int[][] secondList) {
        List<List<Integer>> res = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < firstList.length && j < secondList.length) {
            int[] i1 = firstList[i];
            int[] i2 = secondList[j];
            if (!(i1[1] < i2[0] || i2[1] < i1[0])) {
                //if i1 and i2 are overlapping
                int start = Math.max(i1[0], i2[0]);
                int end = Math.min(i1[1], i2[1]);
                res.add(Arrays.asList(start, end));
            }
            
            if (i1[1] < i2[1]) {
                i++;
            } else {
                j++;
            }
        }
        
        //add to result
        int[][] resArr = new int[res.size()][2];
        for (int k = 0; k < resArr.length; k++) {
            List<Integer> l = res.get(k);
            resArr[k][0] = l.get(0);
            resArr[k][1] = l.get(1);
        }
        return resArr;
    }
}
```



### 287. [Find the Duplicate Number](https://leetcode.com/problems/find-the-duplicate-number/) ***

Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.

> **Example 1:**
> 
> Input: `[1,3,4,2,2]`
>
> Output: 2
>
> **Example 2:**
>
> Input: `[3,1,3,4,2]`
>
> Output: 3
>
> Note:

You must not modify the array (assume the array is read only).
You must use only constant, O(1) extra space.
Your runtime complexity should be less than O(n2).
There is only one duplicate number in the array, but it could be repeated more than once.
```java
/*
alg: two pointer decycle. visualize the duplicates as cycles in the linkedlist, since given range of numbers, each nums[i] effectively map to a unique linkedlist node except the duplicate number. so the problem is the same as to find the cycle node in a linkedlist


1. find the intersection point of slow and fast pointer

2. find the cycle point

time: O(n), space: O(1)
*/
class Solution {
    public int findDuplicate(int[] nums) {
        int s = 0;
        int f = 0;
        do {
            s = nums[s];
            f = nums[nums[f]];
        } while (s != f);
        
        f = 0;
        while (s != f) {
            s = nums[s];
            f = nums[f];
        }
        return s;
    }
}
```

### [15. Three Sum] (https://leetcode.com/problems/3sum/) ***

Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note:

The solution set must not contain duplicate triplets.

```
Example:

Given array nums = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]
```

```java
/*
algorithm: two sum extension

preprocess: sort the input array
pick a num, then run two sum for the remaining range of numbers. after each two sum, move the num to the next non-duplicate number to ensure non-duplicate solutions

time: O(nlogn + n^2) for sorting + complexity for run two pointer
space: O(logn) for cost for sortling
*/
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int i = 0;
        int n = nums.length;
        while (i < n) {
            int j = i + 1;
            List<List<Integer>> sums = twoSum(nums, j, n - 1, -nums[i]); //nums[i] + twoSum = 0
            for (List<Integer> sum : sums) {
                sum.add(nums[i]);
                res.add(sum);
            }
            
            i++;
            while (i < n && nums[i] == nums[i - 1]) {
                i++;
            }
        }
        return res;
    }
    public List<List<Integer>> twoSum(int[] nums, int l, int r, int target) {
        List<List<Integer>> res = new ArrayList<>();
        
        while (l < r) {
            int sum = nums[l] + nums[r];
            if (sum == target) {
                List<Integer> sumList = new ArrayList<>();
                sumList.add(nums[l]);
                sumList.add(nums[r]);
                res.add(sumList);
                l++;
                while (l < r && nums[l] == nums[l - 1]) {
                    l++;
                }
            } else if (sum < target) {
                l++;
            } else {
                r--;
            }
        }
        return res;
    }
}
```



```java
vector<vector<int>> twoSum(int[] arr, int target) {
    vector<vector<int>> res;
    hashset<int> set;
    for (int i : arr) {
        if (set.contains(target - i)) {
            vector<int> sum = {i, target - i};
            res.push_back(sum);
        } else {
            set.add(i);
        }
    }
    return res;
}
/*
1 1 2 3 6 8 9,   t = 10
    i j     k

s = 10 > 7
j--

s < 7:
i++

time: O(nlogn)
space: O(logn)
*/
vector<vector<int>> twoSum(int[] arr, int target) {
    vector<vector<int>> res;
    hashset<int> set;
    mergeSort(arr);
    int i = 0;
    int j = arr.length - 1;
    while (i < j) {
        int s = arr[i] + arr[j];
        if (s == target) {
            vector<int> v = {arr[i], arr[j]};
            i++;
            while (i < arr.length && arr[i] == arr[i - 1]) {
                i++;
            }
        } else if (s < target) {
            i++;
        } else {
            j--;
        }
    }
    return res;
}
```

### [16. 3Sum Closest](https://leetcode.com/problems/3sum-closest/)
Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.

```
Example 1:

Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
```

```java
/*
3 sum variation:

looking for s such that s = a + b + c, where {abc} are in nums array, and s is the closest to input target

1. sort the input array
2. run two sum two pointer and update a global minimum(absolute distance to target)

time: O(nlogn + n^2)
space: O(logn) for sorting

*/
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        if (n < 3) {
            return -1;
        }
        int min = nums[n - 1] + nums[n - 2] + nums[n - 3];
        int i = 0;
        while (i < n - 2) {
            int twoSum = target - nums[i];
            int minTwoSum = twoSum(nums, twoSum, i + 1, n - 1);
            int minThreeSum = minTwoSum + nums[i];
            min = Math.abs(min - target) <  Math.abs(minThreeSum - target) ? min : minThreeSum;
            i++;
            while (i < n - 2 && nums[i] == nums[i - 1]) {
                i++;
            }
        }
        return min;
    }
    //find closest two sum to t given range arr[l ... r]
    public int twoSum(int[] arr, int t, int l, int r) {
        int res = arr[l] + arr[r];
        while (l < r) {
            int s = arr[l] + arr[r];
            res = Math.abs(res - t) < Math.abs(s - t) ? res : s;
            if (s < t) {
                l++;
            } else {
                r--;
            }
        }
        return res;
    }
}
```



### 1658. Minimum Operations to Reduce X to Zero ***

```java
/*
alg: two pointer

this is a reversed alg, instead of finding from left and right end of arr to find x, let total = sum(arr), we need to find the consecutive subarray of arr that sum to total - x. 
- Since all numbers in arr is *positive and we need to find a *consecutive subarray sum, we can use two pointer to maintain a sliding window 
  s.t. sum(window) <= total - x
- slide such window and keep track of the minimum window size s.t. sum(window) == total - x

time: O(n)
space: O(1)
*/

class Solution {
    public int minOperations(int[] nums, int x) {
        int i = 0;
        int j = 0;
        int total = 0;
        for (int k : nums) {
            total += k;
        }
        
        int min = Integer.MAX_VALUE;
        int curSum = 0;
        while (i < nums.length && j < nums.length) { //condition on i incase of arr = [1, 1], x = 3
            if (curSum + nums[j] > total - x) { // if window too big
                curSum -= nums[i];
                i++;
            } else {
                curSum += nums[j];
                if (curSum == total - x) {
                    int windowSize = j - i + 1;
                    min = Math.min(min, nums.length - windowSize);
                }
                j++;
            }
            
            
        }
        return min == Integer.MAX_VALUE? -1 : min;
    }
}
```



### 696. Count Binary Substrings

Give a string `s`, count the number of non-empty (contiguous) substrings that have the same number of 0's and 1's, and all the 0's and all the 1's in these substrings are grouped consecutively.

Substrings that occur multiple times are counted the number of times they occur.

**Example 1:**

```
Input: "00110011"
Output: 6
Explanation: There are 6 substrings that have equal number of consecutive 1's and 0's: "0011", "01", "1100", "10", "0011", and "01".
Notice that some of these substrings repeat and are counted the number of times they occur.
Also, "00110011" is not a valid substring because all the 0's (and 1's) are not grouped together.
```

**Example 2:**

```
Input: "10101"
Output: 4
Explanation: There are 4 substrings: "10", "01", "10", "01" that have equal number of consecutive 1's and 0's.
```

```java
/*
e.g. for 11100011, we can try to find 111000 first then by adding min(# of 1's, # of 0's) counts to the result. After which we want to keep 000 and search the right half of the string, which is 11 in this case

alg: two pointer linear scan
set i be the first idx of 0 or 1, j be the idx of opposite to i,
11100011
i  j
11100011
   i  j
11100011
      i j
time: O(n)
*/
class Solution {
    public int countBinarySubstrings(String s) {
        int i = 0;
        int j = 0;
        //init j
        while (j < s.length() && s.charAt(i) == s.charAt(j)) {
            j++;
        }
        
        int res = 0;
        //linear scan
        while (j < s.length()) {
            int j_next = j + 1;
            while (j_next < s.length() && s.charAt(j) == s.charAt(j_next)) {
                j_next++;
            }
            int l = j - i; // # of 1/0 on [i ... j - 1]
            int r = j_next - j;
            res += Math.min(l, r);
            i = j;
            j = j_next;
        }
        return res;
    }
}
```



### 1229. Meeting Scheduler \*\*

```java
/*
alg: two pointer

the problem is same as finding a intersection of two people's interval. and we want to find an intersection such that the len(intersect) >= duration
time: O(nlogn + n) since we need to sort
space: O(1)
*/
class Solution {
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        Arrays.sort(slots1, (i1, i2) -> i1[0] - i2[0]);
        Arrays.sort(slots2, (i1, i2) -> i1[0] - i2[0]);
        int i = 0;
        int j = 0;
        while (i < slots1.length && j < slots2.length) {
            int[] i1 = slots1[i];
            int[] i2 = slots2[j];
            if (i1[1] < i2[0]) {
                i++;
            } else if (i2[1] < i1[0]) {
                j++;
            } else {
                int start = Math.max(i1[0], i2[0]);
                int end = Math.min(i1[1], i2[1]);
                int overlap = end - start;
                if (overlap >= duration) {
                    return Arrays.asList(start, Math.min(end, start + duration));
                }
                
                if (i1[1] < i2[1]) {
                    i++;
                } else {
                    j++;
                }
            }
        }
        return new ArrayList<Integer>();
    }
}
```

### 1564. Put Boxes Into the Warehouse I \*

```java
/*
sort the boxes, then maintain a decreasing order for warehouse from left to right
time: O(mlogm + m + n), m = len(boxes), n = len(warehouse)
space: O(m) for sortin
*/
class Solution {
    public int maxBoxesInWarehouse(int[] boxes, int[] warehouse) {
        Arrays.sort(boxes);
        int i = boxes.length - 1;
        int j = 0;
        int min = Integer.MAX_VALUE;    //use min to maintain a decresing order
        int cnt = 0;
        while (i >= 0 && j < warehouse.length) {
            min = Math.min(min, warehouse[j]);
            while (i >= 0 && boxes[i] > min) {
                i--;
            }
            
            if (i >= 0) {
                cnt++;
            }
            i--;
            j++;
        }
        return cnt;
    }
}
```



### 1855. Maximum Distance Between a Pair of Values \*\*

```java
/*
alg: since both arrays are non-increasing, use two pointer
time: O(n)
space: O(1)
*/
class Solution {
    public int maxDistance(int[] nums1, int[] nums2) {
        int i = 0;
        int j = 0;
        int max = 0;
        while (i < nums1.length && j < nums2.length) {
            if (i >= j) { //j must > i at all time
                j++;
            } else if (nums2[j] >= nums1[i]) {
                max = Math.max(j - i, max);
                j++;
            } else {
                i++;
            }
        }
        return max;
    }
}
```



### E. Squaring a Sorted Array

Given a sorted array, create a new array containing **squares of all the numbers of the input array** in the **sorted order**.

**Example 1:**

```
Input: [-2, -1, 0, 2, 3]
Output: [0, 1, 4, 4, 9]
```

```java
class SortedArraySquares {

  public static int[] makeSquares(int[] arr) {
    int i = 0;
    int j = arr.length - 1;
    while (i <= j) {
      if (Math.abs(arr[i]) > Math.abs(arr[j])) {
        swap(arr, i, j);
      }
      arr[j] = arr[j] * arr[j];
      j--;
    }
    return arr;
  }
  public static void swap(int[] arr, int i, int j) {
    int tmp = arr[i];
    arr[i] = arr[j];
    arr[j] = tmp;
  }
}

```



### E. Triplets with Smaller Sum \*\*

```java
import java.util.*;
/*
viariation for 3 sum, presort the array, fix one element x, then count for # of pair(y, z) s.t. y + z < target - x
time: O(nlogn + n^2)
space: O(n) for sorting
*/
class TripletWithSmallerSum {

  public static int searchTriplets(int[] arr, int target) {
    Arrays.sort(arr);
    int i = 0;
    int cnt = 0;
    while (i < arr.length - 2) {
      cnt += twoSum(arr, target - arr[i], i + 1, arr.length - 1); //search in range of [i + 1 ... len - 1]
      //skip dups
      int i_next = i + 1;
      while (i_next < arr.length && arr[i_next] == arr[i]) {
        i_next++;
      }
      i = i_next;
    }
    
    return cnt;
  }
  public static int twoSum(int[]arr, int t, int i, int j) {
    int cnt = 0;
    while (i < j) {
      if (arr[i] + arr[j] < t) {
        cnt += (j - i);
        //skip dups
        int i_next = i + 1;
        while (i_next < j && arr[i_next] == arr[i]) {
          i_next++;
        }
        i = i_next;
      } else {
        j--;
      }
    }
    return cnt;
  }
}
```

### E. Comparing Strings containing Backspaces \*\*\*

Given two strings containing backspaces (identified by the character ‘#’), check if the two strings are equal.

**Example 1:**

```
Input: str1="xy#z", str2="xzz#"
Output: true
Explanation: After applying backspaces the strings become "xz" and "xz" respectively.
```

**Example 2:**

```
Input: str1="xy#z", str2="xyz#"
Output: false
Explanation: After applying backspaces the strings become "xz" and "xy" respectively.
```

**Example 3:**

```
Input: str1="xp#", str2="xyz##"
Output: true
Explanation: After applying backspaces the strings become "x" and "x" respectively.
In "xyz##", the first '#' removes the character 'z' and the second '#' removes the character 'y'.
```

**Example 4:**

```
Input: str1="xywrrmp", str2="xywrrmu#p"
Output: true
Explanation: After applying backspaces the strings become "xywrrmp" and "xywrrmp" respectively.
```



intuition: it is not easy to do it scanning from left to right, since the backspace can be stacked and characters we have seen in the past need to removed based on the `#`, thus we should scan it **backwards**. check out the solution to see how the solution handles situations where`#` can be stacked and chained.

```java

class BackspaceCompare {

  public static boolean compare(String str1, String str2) {
    // use two pointer approach to compare the strings
    int index1 = str1.length() - 1, index2 = str2.length() - 1;
    while (index1 >= 0 || index2 >= 0) {

      int i1 = getNextValidCharIndex(str1, index1);
      int i2 = getNextValidCharIndex(str2, index2);

      if (i1 < 0 && i2 < 0) // reached the end of both the strings
        return true;

      if (i1 < 0 || i2 < 0) // reached the end of one of the strings
        return false;

      if (str1.charAt(i1) != str2.charAt(i2)) // check if the characters are equal
        return false;

      index1 = i1 - 1;
      index2 = i2 - 1;
    }

    return true;
  }

  private static int getNextValidCharIndex(String str, int index) {
    int backspaceCount = 0;
    while (index >= 0) {
      if (str.charAt(index) == '#') // found a backspace character
        backspaceCount++;
      else if (backspaceCount > 0) // a non-backspace character
        backspaceCount--;
      else
        break;

      index--; // skip a backspace or a valid character
    }
    return index;
  }

  public static void main(String[] args) {
    System.out.println(BackspaceCompare.compare("xy#z", "xzz#"));
    System.out.println(BackspaceCompare.compare("xy#z", "xyz#"));
    System.out.println(BackspaceCompare.compare("xp#", "xyz##"));    
    System.out.println(BackspaceCompare.compare("xywrrmp", "xywrrmu#p"));
  }
}

```



### E. Minimum Window Sort \*\*\*\*

Given an array, find the length of the smallest subarray in it which when sorted will sort the whole array.

**Example 1:**

```
Input: [1, 2, 5, 3, 7, 10, 9, 12]
Output: 5
Explanation: We need to sort only the subarray [5, 3, 7, 10, 9] to make the whole array sorted
```

**Example 2:**

```
Input: [1, 3, 2, 0, -1, 7, 10]
Output: 5
Explanation: We need to sort only the subarray [1, 3, 2, 0, -1] to make the whole array sorted
```

**Example 3:**

```
Input: [1, 2, 3]
Output: 0
Explanation: The array is already sorted
```

**Example 4:**

```
Input: [3, 2, 1]
Output: 3
Explanation: The whole array needs to be sorted.
```

As we know, once an array is sorted (in ascending order), the smallest number is at the beginning and the largest number is at the end of the array. So if we start from the beginning of the array to find the first element which is out of sorting order i.e., which is smaller than its previous element, and similarly from the end of array to find the first element which is bigger than its previous element, will sorting the subarray between these two numbers result in the whole array being sorted?

Let’s try to understand this with Example-2 mentioned above. In the following array, what are the first numbers out of sorting order from the beginning and the end of the array:

```
    [1, 3, 2, 0, -1, 7, 10]
```

1. Starting from the beginning of the array the first number out of the sorting order is ‘2’ as it is smaller than its previous element which is ‘3’.
2. Starting from the end of the array the first number out of the sorting order is ‘0’ as it is bigger than its previous element which is ‘-1’

As you can see, sorting the numbers between ‘3’ and ‘-1’ will not sort the whole array. To see this, the following will be our original array after the sorted subarray:

```
    [1, -1, 0, 2, 3, 7, 10]
```

The problem here is that the smallest number of our subarray is ‘-1’ which dictates that we need to include more numbers from the beginning of the array to make the whole array sorted. We will have a similar problem if the maximum of the subarray is bigger than some elements at the end of the array. To sort the whole array we need to include all such elements that are smaller than the biggest element of the subarray. So our final algorithm will look like:

1. From the beginning and end of the array, find the first elements that are out of the sorting order. The two elements will be our candidate subarray.
2. Find the maximum and minimum of this subarray.
3. Extend the subarray from beginning to include any number which is bigger than the minimum of the subarray.
4. Similarly, extend the subarray from the end to include any number which is smaller than the maximum of the subarray.

```java
class ShortestWindowSort {

  public static int sort(int[] arr) {
    int low = 0, high = arr.length - 1;
    // find the first number out of sorting order from the beginning
    while (low < arr.length - 1 && arr[low] <= arr[low + 1])
      low++;

    if (low == arr.length - 1) // if the array is sorted
      return 0;

    // find the first number out of sorting order from the end
    while (high > 0 && arr[high] >= arr[high - 1])
      high--;

    // find the maximum and minimum of the subarray
    int subarrayMax = Integer.MIN_VALUE, subarrayMin = Integer.MAX_VALUE;
    for (int k = low; k <= high; k++) {
      subarrayMax = Math.max(subarrayMax, arr[k]);
      subarrayMin = Math.min(subarrayMin, arr[k]);
    }

    // extend the subarray to include any number which is bigger than the minimum of the subarray 
    while (low > 0 && arr[low - 1] > subarrayMin)
      low--;
    // extend the subarray to include any number which is smaller than the maximum of the subarray
    while (high < arr.length - 1 && arr[high + 1] < subarrayMax)
      high++;

    return high - low + 1;
  }

  public static void main(String[] args) {
    System.out.println(ShortestWindowSort.sort(new int[] { 1, 2, 5, 3, 7, 10, 9, 12 }));
    System.out.println(ShortestWindowSort.sort(new int[] { 1, 3, 2, 0, -1, 7, 10 }));
    System.out.println(ShortestWindowSort.sort(new int[] { 1, 2, 3 }));
    System.out.println(ShortestWindowSort.sort(new int[] { 3, 2, 1 }));
  }
}
```

# Fast & Slow Pointer

### Notes:

- for questions involving cycle in array/linkedlist, fast & slow pointers can be a good intuition

### 457. Circular Array Loop \*\*\*

You are playing a game involving a **circular** array of non-zero integers `nums`. Each `nums[i]` denotes the number of indices forward/backward you must move if you are located at index `i`:

- If `nums[i]` is positive, move `nums[i]` steps **forward**, and
- If `nums[i]` is negative, move `nums[i]` steps **backward**.

Since the array is **circular**, you may assume that moving forward from the last element puts you on the first element, and moving backwards from the first element puts you on the last element.

A **cycle** in the array consists of a sequence of indices `seq` of length `k` where:

- Following the movement rules above results in the repeating index sequence `seq[0] -> seq[1] -> ... -> seq[k - 1] -> seq[0] -> ...`
- Every `nums[seq[j]]` is either **all positive** or **all negative**.
- `k > 1`

Return `true` *if there is a **cycle** in* `nums`*, or* `false` *otherwise*.

 

**Example 1:**

```
Input: nums = [2,-1,1,2,2]
Output: true
Explanation:
There is a cycle from index 0 -> 2 -> 3 -> 0 -> ...
The cycle's length is 3.
```

**Example 2:**

```
Input: nums = [-1,2]
Output: false
Explanation:
The sequence from index 1 -> 1 -> 1 -> ... is not a cycle because the sequence's length is 1.
By definition the sequence's length must be strictly greater than 1 to be a cycle.
```

**Example 3:**

```
Input: nums = [-2,1,-1,-2,-2]
Output: false
Explanation:
The sequence from index 1 -> 2 -> 1 -> ... is not a cycle because nums[1] is positive, but nums[2] is negative.
Every nums[seq[j]] must be either all positive or all negative.
```

```java
/*
alg: slow and fast pointer.
starting from an index i with s = f = i, if s and f meets, there will be a cycle. We make sure that our s and f always runs on 1 direction by checking the product of nums[f_next] * nums[f_next_next] > 0. if we found the product to be < 0, there will be no cycle for index i. **we then mark elements on the path that can be reached by i IN THE DIRECTION of nums[i] to be visited**.
    - consider [-1, 1, 2], a loop is index = {1, 2}, but a loop is not valid for index {0, 1, 2}, at index 0, we only want to mark index = 0 to be visited instead of all {-1, 1, 2}. so we only mark indicies that are on the same direction to be visited
time: O(n)
space: O(1) as we can in place mark the indices
*/
class Solution {
    public boolean circularArrayLoop(int[] nums) {
        //boolean[] visited = new boolean[nums.length];
        int n = nums.length;
        for (int i = 0; i < nums.length; i++) {
            int s = i;
            int f = i;
            while (true) {
                int snext = move(n, s, nums[s]);
                int fnext = move(n, f, nums[f]);
                int fnextnext = move(n, fnext, nums[fnext]);
                if (f == fnext || fnext == fnextnext || nums[f] * nums[fnext] < 0 || nums[f] * nums[fnextnext] < 0) {
                    //if opposite direction or self loop
                    break;
                } else if (snext < i || fnext < i || fnextnext < i) { 
                    //if visited
                    break;
                }
                s = snext;
                f = fnextnext;
                if (s == f) {
                    return true;
                }
            }
            
            s = i;
            while (nums[s] * nums[move(n, s, nums[s])] > 0) {
                //set all numbers on the same direction of that path to visited
                int next = move(n, s, nums[s]);
                nums[s] = 0;
                s = next;
            }
            
            
            
        }
        return false;
    }
    public int move(int n, int cur, int i) {
        return (n + (cur + i) % n) % n;
    }
}
```



### E. Happy Number \*

Any number will be called a happy number if, after repeatedly replacing it with a number equal to the **sum of the square of all of its digits, leads us to number ‘1’**. All other (not-happy) numbers will never reach ‘1’. Instead, they will be stuck in a cycle of numbers which does not include ‘1’.

**Example 1:**

```
Input: 23   
Output: true (23 is a happy number)  
Explanations: Here are the steps to find out that 23 is a happy number:
```

1. 2^2 + 3 ^222+32 = 4 + 9 = 13
2. 1^2 + 3^212+32 = 1 + 9 = 10
3. 1^2 + 0^212+02 = 1 + 0 = 1

**Example 2:**

```
Input: 12   
Output: false (12 is not a happy number)  
Explanations: Here are the steps to find out that 12 is not a happy number:
```

1. 1^2 + 2 ^212+22 = 1 + 4 = 5
2. 5^252 = 25
3. 2^2 + 5^222+52 = 4 + 25 = 29
4. 2^2 + 9^222+92 = 4 + 81 = 85
5. 8^2 + 5^282+52 = 64 + 25 = 89
6. 8^2 + 9^282+92 = 64 + 81 = 145
7. 1^2 + 4^2 + 5^212+42+52 = 1 + 16 + 25 = 42
8. 4^2 + 2^242+22 = 16 + 4 = 20
9. 2^2 + 0^222+02 = 4 + 0 = 4
10. 4^242 = 16
11. 1^2 + 6^212+62 = 1 + 36 = 37
12. 3^2 + 7^232+72 = 9 + 49 = 58
13. 5^2 + 8^252+82 = 25 + 64 = 89

Step ‘13’ leads us back to step ‘5’ as the number becomes equal to ‘89’, this means that we can never reach ‘1’, therefore, ‘12’ is not a happy number.

```java
/*
alg1: hashset to track # seen, and when a cycle occurs (next iteration is a seen number), return false. However, this approach involevs non-constant space usage

alg2: fast and slow pointer. since each number is directed to exactly a single number, this follows a linked list structure. We know that there is possible cycle in the linkedlist or else the cycle can be at number 1. Using fast&slow pointer allows O(1) space
*/
class HappyNumber {

  public static boolean find(int num) {
    int slow = num, fast = num;
    do {
      slow = findSquareSum(slow); // move one step
      fast = findSquareSum(findSquareSum(fast)); // move two steps
    } while (slow != fast); // found the cycle

    return slow == 1; // see if the cycle is stuck on the number '1'
  }

  private static int findSquareSum(int num) {
    int sum = 0, digit;
    while (num > 0) {
      digit = num % 10;
      sum += digit * digit;
      num /= 10;
    }
    return sum;
  }

  public static void main(String[] args) {
    System.out.println(HappyNumber.find(23));
    System.out.println(HappyNumber.find(12));
  }
}
```

### E. Palindrome LinkedList

Given the head of a **Singly LinkedList**, write a method to check if the **LinkedList is a palindrome** or not.

Your algorithm should use **constant space** and the input LinkedList should be in the original form once the algorithm is finished. The algorithm should have O(N) time complexity where ‘N’ is the number of nodes in the LinkedList.

**Example 1:**

```
Input: 2 -> 4 -> 6 -> 4 -> 2 -> null
Output: true
```

**Example 2:**

```
Input: 2 -> 4 -> 6 -> 4 -> 2 -> 2 -> null
Output: false
```

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
          return true;
        }

        ListNode mid = findMid(head);
        ListNode right = mid.next;
        mid.next = null;
        right = reverse(right);
        return checkEquals(head, right);
    }
    public static boolean checkEquals(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
          if (l1.val != l2.val) {
            return false;
          }
          l1 = l1.next;
          l2 = l2.next;
        }
        return true;
    }
    public static ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
          ListNode next = head.next;
          head.next = prev;
          prev = head;
          head = next;
        }
        return prev;
     }
     public static ListNode findMid(ListNode head) {
        ListNode s = head;
        ListNode f = head;
        while (f != null && f.next != null && f.next.next != null) {
          s = s.next;
          f = f.next.next;
        }
        return s;
     }
}
```



