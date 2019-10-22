/*
algorithm1: sliding window with max check for k rounds for each window, time: O(kn) n = # of elements in the input array

algorithm2: sliding window with a max heap (lazy deletion), each time(when we move the window) poll the top entry from the pq, if it's old, pop it until we get the element that's in the current window            time: O(nlogn), space: O(n)

algorithm3: slidng window with a decreasing deque
    since each time we move the window, we dont need numbers that are "old" and "small", meaning if we have a window [2, 1, 3], we don't need 2 and 1 since it would never be part of the solutin.Likewise, if we have [1, 2, 3], we don't need 1,2. [3, 2, 1] we need preserve 2 and 1 since they are "new", and might be needed for the future window.
    
    mid level detail: maintain a decreasing deque from left ro right, so each round after "maintainance"(add new element, delete left small and old number so it's decreasing order). the maximum element would be the leftMost element on the deque
    data structure: since we need push from right and pop&peek from left, so it's a dqeue behavior --> use a deque containing indices of elements in the arr
    detail:
        for i < nums.len:
            //delete old/small left element before push from right
            //insert new element
            //peek leftMost element to retrieve the maximum of current window
    time: O(n), space: O(k)
*/
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        List<Integer> res = new ArrayList<Integer>();
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            //delete old
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) 
            {
                deque.pollFirst();    
            }
            //delete small from right before insert new
            while (!deque.isEmpty() && (nums[deque.peekLast()] <= nums[i])) {
                deque.pollLast();
            }
            //post condition: old and small elements are deleted
            //insert new
            deque.offerLast(i);
            //peel leftMost to retrieve maximum
            if (i >= k - 1) {
                res.add(nums[deque.peekFirst()]);
            }
        }
        
        int[] resArr = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            resArr[i] = res.get(i);
        }
        return resArr;
    }
}