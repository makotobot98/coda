### 218. The Skyline Problem \* (review for merge sort)

### [493. Reverse Pairs](https://leetcode.com/problems/reverse-pairs/) *****

Given an array nums, we call (i, j) an important reverse pair if `i < j and nums[i] > 2*nums[j].`

You need to return the number of important reverse pairs in the given array.

```
Example1:

Input: [1,3,2,3,1]
Output: 2
Example2:

Input: [2,4,3,5,1]
Output: 3
Note:
The length of the given array will not exceed 50,000.
All the numbers in the input array are in the range of 32-bit integer.
```

```java
/*
merge sort: since merge sort essentially is equivalent to the problem of comparing elements in the array, and each pair of element will be compared at most once and will be sorted and thus not be compared again, thus we can compute the count of reverse pairs at each merging step and aggregate the result

recursive rule:
    split the nums into left and right
    recursively mergeSort left half and collect the count of reverse pairs found
    recursively mergeSort .... right half
        //count # of reversed pair with i = elements from left half, j = elements from right half, note this relation is *transitive*, thus can be computed in linear time
    merge and count the reverse pair during comparing left and right half
    
    return the aggregating counts of left, right, and reverse pairs during the merge process

time: O(nlogn)
space: O(n) for merge helper array

*/
class Solution {
    public int reversePairs(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        
        int[] helper = new int[nums.length];
        return mergeSort(nums, helper, 0, nums.length - 1);
    }
    public int mergeSort(int[] nums, int[] helper, int l, int r) {
        if (l >= r) {
            return 0;
        }
        
        int mid = l + (r - l) / 2;
        int lcount = mergeSort(nums, helper, l, mid);   //left bounded by [l ... mid]
        int rcount = mergeSort(nums, helper, mid + 1, r);   //right bounded by [mid + 1 ... r]
        
        //compute the count from two sorted array, based on the transitive nature
        // if nums[i] > nums[i - k], then j such that nums[i - k] > 2 * nums[j] can beadded to nums[i]'s count
        int count = 0;
        int j = mid + 1;
        for (int i = l; i <= mid; i++) {
            while (j <= r && (long) nums[i] > (long)2L * nums[j]) {
                j++;
            } //j > r || nums[i] <= 2 * nums[j]
            count += j - (mid + 1); //add every thing besides nums[j]
        }
        merge(nums, helper, l, mid, mid + 1, r);
        return lcount + rcount + count;
    }
    public void merge(int[] nums, int[] helper, int l1, int r1, int l2, int r2) {
        copyArr(nums, helper, l1, r2);
        int count = 0;
        int sortIdx = l1;
        int rightSize = r2 - l2 + 1;
        while (l1 <= r1 && l2 <= r2) {
            if (helper[l1] > helper[l2]) {
                nums[sortIdx++] = helper[l2++]; 
            } else {
                nums[sortIdx++] = helper[l1++];
            }
        }//post condition: l1 > r1 || l2 > r2
        while (l1 <= r1) {
            nums[sortIdx++] = helper[l1++];
        }
        //l2 > r2 case already sorted
    }
    public void copyArr(int[] src, int[] dest, int l, int r) {
        while (l <= r) {
            dest[l] = src[l];
            l++;
        }
    }
}
```

