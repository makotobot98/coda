/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
/*
1. iterative approach
        m \
        m / 2m \
        m      / 3m \
        m           / 4m
        m               ....
        ...                 \
        m                   / nm
        in total of n lists, each of length m
        time: O(1 + 2 + 3 .. + n)m = O(m * n^2)
        space: O(1)
        
2. binary reduction
        m \
        m / 2m \
        m \     -4m  
        m / 2m /     \
        m \           \
        m / 2m \      / 8m ...  
        m \     -4m  /
        m / 2m /
        ...
        m
        (n/2 * 2m) + (n/4 * 4m) + (n/8 * 8m)....
        time: (mn + mn + mn + ...) = mnlogn
        space: O(1) if iterative, O(logn) if recursion. those two complexity only works if we merge lists "inplace" in the input ListNode array. usually since size of remained lists is decreasing, we use O(n)space to store input into an arraylist, so woule be O(n)
3. k-way merge
        m
        m
        m
        m
        m
        ...
        m
        use a min heap storing each head of k lists.
        time: O(mn * logn), space: O(n)
*/
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        PriorityQueue<ListNode> pq = new PriorityQueue<>((l1, l2) -> {
            if (l1.val == l2.val) {
                return 0;
            }
            return l1.val < l2.val? -1 : 1;
        });
        for (ListNode ls : lists) {
            if (ls != null) {
                pq.offer(ls);
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode p = dummy;
        //k way merge
        while (!pq.isEmpty()) {
            ListNode min = pq.poll();
            if (min.next != null) {
                pq.offer(min.next);
            }
            p.next = min;
            p = p.next;
        }
        return dummy.next;
    }
}