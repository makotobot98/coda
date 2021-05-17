### 1837. Sum of Digits in Base K

```java
class Solution {
    public int sumBase(int n, int k) {
        if (n < k) {
            return n;
        }
        return n % k + sumBase(n / k, k);
    }
}
```



### 136. Single Number

Given a non-empty array of integers, every element appears twice except for one. Find that single one.

Note:

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

> **Example 1:**
>
> Input: [2,2,1]
>
> Output: 1
>
> **Example 2:**
> 
> Input: [4,1,2,1,2]
>
> Output: 4

```java
bitwise xor, all pairs of num will be xor to zero, resulting the remaining number be the single num
*/
class Solution {
    public int singleNumber(int[] nums) {
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            res ^= nums[i];
        }
        return res;
    }
}
```

### 137. Single Number II
Given a non-empty array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

Note:

Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

> **Example 1:**
>
> Input: [2,2,3,2]
>
> Output: 3
> 
> **Example 2:**
>
> Input: [0,1,0,1,0,1,99]
>
> Output: 99

```java
class Solution {
    public int singleNumber(int[] nums) {
        int once = 0;
        int twice = 0;
        for (int num : nums) {
            once = ~twice & (once ^ num);
            twice = ~once & (twice ^ num);
        }
        return once;
    }
}
```

### 260. Single Number III
Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice. Find the two elements that appear only once.

> Example:
>
> Input:  [1,2,1,3,2,5]
> Output: [3,5]
> Note:

The order of the result is not important. So in the above example, [5, 3] is also correct.
Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?

```java
class Solution {
    public int[] singleNumber(int[] nums) {
        int xor = 0;
        for (int n : nums) {
            xor ^= n;
        }
        //xor = a ^ b, where a,b are the only single numbers
        int diff = xor & (-xor);    //take out the right most bit difference
        
        int a = 0;
        int b = 0;
        for (int n : nums) {
            if ((n & diff) != 0) {
                a ^= n;
            }
        }
        b = xor ^ a;
        int[] res = new int[2];
        res[0] = a;
        res[1] = b;
        return res;
    }
}
```

### 1829. Maximum XOR for Each Query \*

```java
/*
arr = [0,1,1,3], k = 2
let xorSum = 0 xor 1 xor 1 xor 3

suppose xorSum = 000 ... 1101 1101, k = 2

we want the last 2 bit in xor be all 1's, meaning (01) be 11

let m1 = such number after we flipped all 1's in the first k digit
we have xorSum ^ k = m1, 
and we can see that m1 - xorSum = k, because m1 is same bit as xorSum besides bits in first k bit

we can compute xorSum by iterating all elements in the array, then after each iteration, by xor property, update xorSum by xorSum = xorSum ^ lastElement.
time: O(n)
space: O(1)
*/
class Solution {
    public int[] getMaximumXor(int[] nums, int maximumBit) {
        int xorSum = 0;
        for (int i : nums) {
            xorSum ^= i;
        }
        
        int mask = 0;
        for (int i = 0; i < maximumBit; i++) {
            mask <<= 1;
            mask ^= 1;
        }
        
        
        int[] res = new int[nums.length];
        for (int i = nums.length - 1; i >= 0; i--) {
            int m1 = xorSum | mask;
            res[nums.length - i - 1] = m1 - xorSum;
            xorSum ^= nums[i];
        }
        return res;
    }
}
```

