
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