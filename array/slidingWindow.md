### 713. Subarray Product Less Than K ****
#### approach 1 (sliding window)
```java
/*
alg: sliding window bounded by [i, j)

for j in 1 : len
    if nums[j] >= k: discard prev windows, set i to j + 1
    else if prev * nums[j] >= k and nums[j] < k:
        need to remove i, but first we count contiguous subarray from [i, j - 1]
        count += (j - i)
        i++
    while prev * nums[j] < k:
        j++
    
    //here prev * nums[j] >= k, need to move i
    
    windowSize := j - i
    count += windowSize * (windowSize + 1) / 2      //follows geometric series

post processing for last window: count += indowSize * (windowSize + 1) / 2


time: O(n)
space: O(1)
*/
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        long prev = 1;
        long count = 0;
        int i = 0;
        int j = 0;
        while (j < nums.length) {
            if (nums[j] >= k) {
                long len = j - i;
                count = count + (len * (len + 1) / 2);
                i = j + 1;
                prev = 1;   //reset
                j++;
            } else if (prev * nums[j] >= k) {
                prev /= nums[i];   //remove i from prefix sum
                count += (j - i);
                //remove window left
                i++;
            } else {
                prev *= nums[j];
                j++;
            }
        }
        
        //post processing
        if (i < nums.length) {
            long len = j - i;
            count = count + (len * (len + 1) / 2);
        }
        return (int) count;
    }
}
```

#### approach 2: more neat sliding window
```java
class Solution {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k <= 1) return 0;
        int prod = 1, ans = 0, left = 0;
        for (int right = 0; right < nums.length; right++) {
            prod *= nums[right];
            while (prod >= k) prod /= nums[left++];
            ans += right - left + 1;
        }
        return ans;
    }
}
```