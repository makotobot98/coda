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