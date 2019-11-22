class Solution {
    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] res = new ListNode[k];
        ListNode p = root;
        int N = 0;
        while (p != null) {
            N++;
            p = p.next;
        }
        int min = N / k;
        int rem = N % k;
        //for each group up to k group
        for (int i = 0; i < k; i++) {
            ListNode dummy = new ListNode(0);
            dummy.next = root;
            p = dummy;
            //for each group i, we add min + 1(if i < rem) nodes to that group
            for (int j = 0; j < min + (i < rem ? 1 : 0); j++) {
                p.next = root;
                p = p.next;
                root = root.next;
            }
            //at this point we have dummy -> newHead -> .... -> p -> root
            //disconnect p from root
            p.next = null;
            res[i] = dummy.next;
        }
        return res;
    }
}