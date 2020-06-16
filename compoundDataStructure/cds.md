> Ratings: * to ***** (not so-so to review -> super worthy to review)

# [1429. First Unique Number](https://leetcode.com/explore/featured/card/30-day-leetcoding-challenge/531/week-4/3313/) ***

You have a queue of integers, you need to retrieve the first unique integer in the queue.

Implement the FirstUnique class:

`FirstUnique(int[] nums)` Initializes the object with the numbers in the queue.
`int showFirstUnique()` returns the value of the first unique integer of the queue, and returns -1 if there is no such integer.
`void add(int value)` insert `value` to the queue.

> Example 1:
>
> Input: 
>
> `["FirstUnique","showFirstUnique","add","showFirstUnique","add","showFirstUnique","add","showFirstUnique"]`
>
> `[[[2,3,5]],[],[5],[],[2],[],[3],[]]`
>
> Output: 
>
> `[null,2,null,2,null,3,null,-1]`
>
> Explanation: 
>
> `FirstUnique firstUnique = new FirstUnique([2,3,5]);`
>
> `firstUnique.showFirstUnique(); // return 2`
>
> `firstUnique.add(5);            // the queue is now [2,3,5,5]`
>
> `firstUnique.showFirstUnique(); // return 2`
>
> `firstUnique.add(2);            // the queue is now [2,3,5,5,2]`
>
> `firstUnique.showFirstUnique(); // return 3`
>
> `firstUnique.add(3);            // the queue is now [2,3,5,5,2,3]`
>
> `firstUnique.showFirstUnique(); // return -1`

```java

class FirstUnique {
    Queue<Integer> queue;
    Map<Integer, Integer> map;
    public FirstUnique(int[] nums) {
        queue = new LinkedList<>();
        map = new HashMap<>();
        for (int i : nums) {
            add(i);
        }
    }
    
    public int showFirstUnique() {
        return queue.isEmpty() ? -1 : queue.peek();
    }
    
    public void add(int value) {
        Integer occ = map.get(value);
        if (occ == null) {
            map.put(value, 1);
            queue.offer(value);
        } else if (occ == 1) {
            map.put(value, occ + 1);
            while (!queue.isEmpty() && map.get(queue.peek()) > 1) {
                queue.poll();
            }
        }
    }
}

```
