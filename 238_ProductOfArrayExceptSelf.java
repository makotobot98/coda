/*
intuition:
suppose we have nums.len == 4
res[i] = product Except Self for num[i]

res[0] =    a1 a2 a3 a4 
res[1] = a0    a2 a3 a4
res[2] = a0 a1    a3 a4
res[3] = a0 a1 a2    a4
res[4] = a0 a1 a2 a3

alg1:
looking at res[i], res[i] = product from [0 ... i - 1] * product from [i + 1 ... len - 1]
we can then assign M1[i] = product from [0 ... i - 1], M2[i] = product from [i + 1 ... len - 1]
time: O(n), space: O(n) for space for M1 and M2

alg2:
notice the diagram drawn, we can run two pass to the res array, first time compute leftside of the diagonal, and second run compute the rightside of the diagonal
time: O(n), space: O(1)
*/
class Solution {
    public int[] productExceptSelf(int[] nums) {
        if (nums == null || nums.length == 0) {
            return nums;
        }
        int[] res = new int[nums.length];
        //leftside
        int left = 1;
        for (int i = 0; i < nums.length; i++) {
            res[i] = left;
            left *= nums[i];
        }
        
        //rightside
        int right = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= right;
            right *= nums[i];
        }
        return res;
    }
}