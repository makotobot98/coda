/*
algorithm1: brute force
    for i
        for j from [i ... len - 1]:                 --> O(n)
            compute sum of the subarray [i ... j]   --> O(n)        (3)
    time: O(n^3), space: O(1)
algorithm: dynamic programming
    to simplify (3), we can compuye sum of [i ... j] in O(1) if we store a dp array where
    M[i] = sum from (i ... len - 1], then to compute the sum of a subarray[i ... j]
        sum of subarray[i ... j] = nums[i] + M[i] - M[j]
    
    time: O(n^2), space: O(n) for the dp array usage

algorithm2: dynamic programming using O(1) space, we could also define M[i] = sum from [givenStartIndex ... i]
    for i:
        we build M starting from i, then we only need O(1) space 

algorithm3: dynamic programming & hashmap look up
    since we are looking for a subarray sum = k, meaning we looking for all i,j such that:
        subarraySum[i ... j] == k
        M[j] - M[i] = k; M[i] = sum of nums[0 ... i], so M[i] = M[i - 1] + nums[i], j > i
    =>  notice j > i, meaning when at j, if we can found M[i]'s such that M[i] = M[j] - k,
        we then found a subarray which sums to k
    => use a hashmap<sum, occurance_count> for O(1) look up
       so when we found there exist M[i] = k - M[j], j > i. we then found map.get(k - M[j]) number of M[i]'s, and each subarray[i ... j] contributes to the total count
*/
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap < Integer, Integer > map = new HashMap < > ();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}