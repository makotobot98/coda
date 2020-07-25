
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
