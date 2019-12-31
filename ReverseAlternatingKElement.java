import java.util.*;

class ListNode {
  int value = 0;
  ListNode next;

  ListNode(int value) {
    this.value = value;
  }
}
/*
alg: recursion
recursive step:
  1. get the kth node
    1.1 kth != null: 
      1.1.1 preserve kth.next, reverse head to kth
      1.1.2 skip following k nodes containing kth.next
      1.1.3 recursively reverse the rest of list
    1.2 kth == null:
      1.2.1 since cannot find k nodes to reverse, simply reverse head to the last not null node
*/
class ReverseEveryKElements {

  public static ListNode reverse(ListNode head, int k) {
    if (head == null) {
      return head;
    }
    ListNode kth = getKth(head, k);
    if (kth == null) {
      return reverse(head);
    }
    //kth != null
    ListNode knext = kth.next;
    kth.next = null;
    //reverse[head ... kth], newHead = new head, newTail = head
    ListNode newHead = reverse(head);
    //connect newTail to knext
    head.next = knext;
    //skip following kth
    ListNode skipKNext = getKth(knext, k);
    //current list: newHead -> ... -> newTail -> knext -> ... -> skipKNext(kth node from knext) -> ...
    if (skipKNext == null || skipKNext.next == null) {
      return newHead;
    }
    skipKNext.next = reverse(skipKNext.next, k);
    return newHead;
  }
  //iterative alg: return the kth node, if not return null
  public static ListNode getKth(ListNode head, int k) {
    if (head == null) {
      return head;
    }
    while (k > 1) {
      head = head.next;
      if (head == null) {
        return head;
      }
      k--;
    }
    return head;
  }
  //iteratively return the given list, return the newHead 
  public static ListNode reverse(ListNode head) {
    if (head == null) {
      return head;
    }
    ListNode prev = null;
    while (head != null) {
      ListNode next = head.next;
      head.next = prev;
      //update
      prev = head;
      head = next;
    }
    return prev;
  }

  public static void main(String[] args) {
    ListNode head = new ListNode(1);
    head.next = new ListNode(2);
    head.next.next = new ListNode(3);
    head.next.next.next = new ListNode(4);
    head.next.next.next.next = new ListNode(5);
    head.next.next.next.next.next = new ListNode(6);
    head.next.next.next.next.next.next = new ListNode(7);
    head.next.next.next.next.next.next.next = new ListNode(8);

    ListNode result = ReverseEveryKElements.reverse(head, 2);
    System.out.print("Nodes of the reversed LinkedList are: ");
    while (result != null) {
      System.out.print(result.value + " ");
      result = result.next;
    }
  }
}