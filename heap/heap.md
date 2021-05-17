### [1834. Single-Threaded CPU](https://leetcode.com/problems/single-threaded-cpu)

```java
/*
sort the task by enque time, then linear scan while tracking the current processing time
if time < top.queueTime:
	add top to result
	time = top.enqueTime + top.processTime
if time >= top.queueTime:
    s = set of tasks t with t.enqueTime <= time
    construct such s
    find t in s so (t.processTime, t.index) is smallest, add t to result and update time
    find t using a min heap, and update time accordingly
    since we might change the set s as we are updating the time, we need to update s with top (top is not in the heap)
*/
class Solution {
    public int[] getOrder(int[][] tasks) {
        int n = tasks.length;
        int[] ans = new int[n];
        int[][] extTasks = new int[n][3];
        for(int i = 0; i < n; i++) {
            extTasks[i][0] = i;
            extTasks[i][1] = tasks[i][0];
            extTasks[i][2] = tasks[i][1];
        }
        Arrays.sort(extTasks, (a,b)->a[1] - b[1]);
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[2] == b[2] ? a[0] - b[0] : a[2] - b[2]);
        int time = 0;
        int ai = 0; //window [ai ... ti] is the window contains the tasks that can be handled together
        int ti = 0;
        while(ai < n) {
            while(ti < n && extTasks[ti][1] <= time) {
                pq.offer(extTasks[ti++]);
                
            }
            if(pq.isEmpty()) {
                time = extTasks[ti][1];
                continue;
            }
            int[] bestFit = pq.poll();
            ans[ai++] = bestFit[0];
            time += bestFit[2];
        }
        return ans;
    }
    
}
```



### 1642. Furthest Building You Can Reach

```java
/*
alg1: dp[][][][], however, based on the input size the runtime is too huge

alg2: greedy: linear scan with a min heap and prioritize using the ladders, then when we are short of bricks, we try to replace the smallest diff used with ladders using bricks, and use that ladder for this layer

time: O(nlogn)
space: O(n)
*/
class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> min = new PriorityQueue<>();
        int cur = 0; // current max index
        
        for (int i = 1; i < heights.length; i++) {
            int diff = heights[i] - heights[i - 1];
            if (diff > 0) {
                if (ladders > 0) {
                    min.offer(diff);
                    ladders--;
                } else {
                    //no ladder left, we we take the smallest diff ladder and use bricks instead
                    min.offer(diff); //push current diff first in case it is the smallest
                    int minDiff = min.poll();
                    if (bricks - minDiff < 0) {
                        return cur;
                    }
                    bricks -= minDiff;
                    
                }
            }
            cur++;
        }
        return cur;
    }
}
```



### 1851. Minimum Interval to Include Each Query

```java
/*
idea is following:
sort intervals by start, sort queries by start (we need to map to its original index to output)
then we use a min heap for intervals sorted by its size, then since our queries are sorted by its value,
for any query qi, we only need to look at intervals starting from the last query, intervals scanned by last query q(i - 1) are no longer needed since the queries are ascending

time: O(nlogn + qlogq + nlogn) where we sort interval for nlogn, queries for qlogq, then as we scan the heap it takes at most n operations to pop all intervals from the heap

space: O(n)

*/
class Solution {
    public int[] minInterval(int[][] intervals, int[] queries) {
        //This is a good question, actually you can not have a very good way, such as binary search or something else
        //to independently get result for each query
        //we can use priority queue to sort by size of the interval
        //but only valid intervals in the queue
        //so we need to sort intervals by starting point and also sort queries
        
        int[] res = new int[queries.length];
        List<Integer> sortedQueryIndex = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) sortedQueryIndex.add(i);
        Collections.sort(sortedQueryIndex, (a, b)->(queries[a] - queries[b]));
        int i = 0;
        Arrays.sort(intervals, (a, b)->(a[0] - b[0]));
        //[size of interval, end of interval]
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b)->(a[0] - b[0]));
        for (int j = 0; j < queries.length; j++) {
            int query = queries[sortedQueryIndex.get(j)];
            while (i < intervals.length && intervals[i][0] <= query) {
                q.add(new int[] {intervals[i][1] - intervals[i][0]+1, intervals[i][1]});
                i++;
            }
            while (!q.isEmpty() && q.peek()[1] < query) q.poll();
            if (q.isEmpty()) {
                res[sortedQueryIndex.get(j)] = -1;
            } else {
                res[sortedQueryIndex.get(j)] = q.peek()[0];
            }
        }
        return res;
    }
}
```



