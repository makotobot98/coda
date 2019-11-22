/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */

/*
M[i] = sum from 0th ListNode to ith ListNode
at each ListNode i, we are looking for j such that M[i] - M[j] = 0
if so, then [j + 1 ... i] are the remaining ListNode


alg1: use two hashmap, map1 = map from M[i] to node i
                      map2 = map from node i to M[i], use treemap so the listnode can be retrieved in ascending order by index

for i:
    compute M[i] and see if we can find M[j]
    found:
        1. get index of M[j], then [j + 1 ... i] are nodes to discard
        *note the mapping from M[i] to ListNode must be 1 to 1 (can prove)
        2. delete each ListNode within that range, look for that index from map2, delete from both maps
    not found:
        2. add (M[i], i) to map1, (i, M[i]) to map2

post processing to concatenate all nodes from map2


alg2: one pass with a hashset marking which index of the ListNode is removed

time: O(n), space: O(n)
*/
class Solution {
    public ListNode removeZeroSumSublists(ListNode head) {
        //map<M[i], List<index of i>>
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> initial = new ArrayList<>();
        initial.add(-1);
        //don't do Arrays.asList(-1), as the list returned does not support add operation
        //https://stackoverflow.com/questions/5755477/java-list-add-unsupportedoperationexception
        map.put(0, initial);
        //set, removed indicies
        Set<Integer> removed =  new HashSet<>();
        int i = 0;
        //traverser
        ListNode p = head;
        //M[i]
        int sum = 0;
        //linear scan generating M[i] and delete as we go
        while (p != null) {
            //System.out.println(p.val);
            sum += p.val;
            //System.out.println("    sum: " + sum);
            List<Integer> ls = map.get(sum);
            
            if (ls == null) {
                //if exist such M[j]
                ls = new ArrayList<Integer>();
            }
            //System.out.println("    res");
            //remove all indices already in removed
            while (!ls.isEmpty() && removed.contains(ls.get(ls.size() - 1))) {
                ls.remove(ls.size() - 1);
            }
            if (!ls.isEmpty()) {
                int j = ls.get(0);
                //remove j + 1 ... i
                for (int idx = j + 1; idx <= i; idx++) {
                    removed.add(idx);
                }
            }
                
            ls.add(i);
            map.put(sum, ls);
            //increment the index counter i, and traverser p
            i++;
            p = p.next;
        }
        
        //post processing to add all non removed listNodes
        ListNode dummy = new ListNode(0);
        p = dummy;
        i = 0;
        while (head != null) {
            if (!removed.contains(i)) {
                p.next = head;
                p = p.next;
            }
            i++;
            head = head.next;
        }
        p.next = null;
        return dummy.next;
    }
}