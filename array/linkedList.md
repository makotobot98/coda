### 708. Insert into a Sorted Circular Linked List ***
Given a node from a `Circular Linked List` which is sorted in ascending order, write a function to insert a value insertVal into the list such that it remains a sorted circular list. The given node can be a reference to any single node in the list, and may not be necessarily the smallest value in the circular list.

If there are multiple suitable places for insertion, you may choose any place to insert the new value. After the insertion, the circular list should remain sorted.

If the list is empty (i.e., given node is `null`), you should create a new single circular list and return the reference to that single node. Otherwise, you should return the original given node.

```
Example 1:
[1,3,3], insert 2 we get [1,2,3,3]
```

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _next) {
        val = _val;
        next = _next;
    }
};
*/
/*
two pointer: we essentially looking for a pair of node such that insert node is in between. in case if insert node is the biggest/smallest, we track the node with maximum value met

*/
class Solution {
    public Node insert(Node head, int insertVal) {
        Node i = new Node(insertVal);
        if (head == null) {
            i.next = i;
            return i;
        } else if (head.next == head) {
            i.next = head;
            head.next = i;
            return head;
        }
        
        Node prev = head;
        Node next = prev.next;
        Node max = Math.max(head.val, next.val) == head.val ? head : next;
        boolean needInsert = true;
        
        do {
            if (prev.val > i.val || next.val <= i.val) {
                prev = prev.next;
                next = next.next;
                if (prev != head && next.val >= max.val) {  //consider [1,3,3] and we insert 4, max will = first 3
                    max = next; //update max
                }
            } else {
                //prev.val <= i.val && next.val > i.val
                prev.next = i;
                i.next = next;
                needInsert = false;
                break;
            }
        } while (prev != head);
        
        if (needInsert) {
            i.next = max.next;
            max.next = i;
        }
        return head;
    }
}
```





### 82. Remove Duplicates from Sorted List II

```java
/*
two pointer linear scan: one pointer's next pointing at the first non duplicate node, one pointer traverse




define i = first non duplicate node, so [head, i] are non duplicates, [i.next, j) are duplicates, [j, tail] = unvisited

at each iteration:
    update j = j.next, move j to first non duplicate pointer such that j.val != i.next.val
    
    if (i.next == null): we r done
    if (i.next.next != j): [i.next ... j) are duplicates
        update i.next = j
        since j could still be duplicate, we dont move i, simply update i.next
    if (i.next.next == j): update i = i.next
    

time: O(n)
space: O(1)
*/
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode dummy = new ListNode(Integer.MAX_VALUE);
        dummy.next = head;
        ListNode i = dummy;
        ListNode j = head;
        while (j != null) {
            j = j.next;
            while (j != null && j.val == i.next.val) {
                j = j.next;
            }
            
            if (i.next == null) {
                break;
            } else if (i.next.next != j) {
                i.next = j;
            } else {
                i = i.next;
            }
        }
        return dummy.next;
    }
}
```

### 109. Convert Sorted List to Binary Search Tree *

```java
/*
divide and conquer, T(N) = 2T(N/2) + N, by master thm with k = 0, we derive the runtime to O(nlogn)
note we can, convert the linkedlist into an array first, then we can access the mid point of the aray in O(1) time, so T'(N) = 2T'(N/2) + 1, thus we have O(N) runtime

*/
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) {
            return null;
        } else if (head.next == null) {
            return new TreeNode(head.val);
        }
        
        ListNode[] arr = getMidPrev(head);
        ListNode prev = arr[0];
        ListNode mid = arr[1];
        ListNode right = mid.next;
        mid.next = null;
        if (prev != null) {
            prev.next = null;
        }
        TreeNode root = new TreeNode(mid.val);
        root.left = prev == null ? null : sortedListToBST(head);
        root.right = sortedListToBST(right);
        return root;
        
    }
    //assuming head is not null
    public ListNode[] getMidPrev(ListNode head) {
        ListNode prev = null;
        ListNode s = head;
        ListNode f = head;
        while (f != null && f.next != null && f.next.next != null) {
            prev = s;
            s = s.next;
            f = f.next.next;
        }
        return new ListNode[]{prev, s};
    }
    
}
```

