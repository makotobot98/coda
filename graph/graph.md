### [787. Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/) ***
There are `n` cities connected by `m` flights. Each flight starts from city u and arrives at `v` with a price `w`.

Now given all the cities and flights, together with starting city src and the destination dst, your task is to find the cheapest price from src to dst with `up to k stops`. If there is no such route, output -1.

![](/rsrc/787eg.png)

```java
/*
algorithm: dijkistra variation

data structure:
    min heap ordered based in the distance
    (map is not needed for the deduplication)


algorithm: tuple(a, b, c) = (curCityId, distanceFromSrc, nstops)
    1. init min heap with (src, 0, 0), init a DGraph using adjacency list
    2. while heap is not empty:
        2.1 poll the current city tuple
                - if tuple.nstop == K + 1, continue
                - if tuple.cityId == dst, return distance
        2.2 expand: for each neighbor n of cur:
                - push (n, cur.distance+ weight, cur.nstops + 1) to the heap
    3. no duplication is needed since provided no cycle
                    
*/

class Solution {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        PriorityQueue<Tuple> pq = new PriorityQueue<>();
        Map<Integer, List<Pair>> graph = new HashMap<>();
        makeGraph(flights, graph);
        pq.offer(new Tuple(src, 0, 0));
        
        while (!pq.isEmpty()) {
            Tuple curTuple = pq.poll();
            if (curTuple.id == dst && curTuple.k <= K + 1) {
                return curTuple.price;
            } else if (curTuple.k > K + 1) {
                continue;
            }
            
            //expand
            for (Pair p : graph.getOrDefault(curTuple.id, new ArrayList<Pair>())) {
                Tuple t = new Tuple(p.id, curTuple.price + p.price, curTuple.k + 1);
                pq.offer(t);
            }
        }
        
        return -1;
    }
    public void makeGraph(int[][] flights, Map<Integer, List<Pair>> map) {
        for (int[] f : flights) {
            int from = f[0];
            int to = f[1];
            int price = f[2];
            
            
            List<Pair> ls = map.getOrDefault(from, new ArrayList<Pair>());
            ls.add(new Pair(to, price));
            map.put(from, ls);
        }
    }
    class Pair {
        int id;
        int price;
        public Pair(int id, int price) {
            this.id = id;
            this.price = price;
        }
    }
    class Tuple implements Comparable<Tuple> {
        int id;
        int price;
        int k;
        public Tuple(int id, int price, int k) {
            this.id = id;
            this.price = price;
            this.k = k;
        }
        
        @Override
        public int compareTo(Tuple t1) {
            return this.price - t1.price;
        }
    }
}
```

### [332. Reconstruct Itinerary](https://leetcode.com/problems/reconstruct-itinerary/)

Given a list of airline tickets represented by pairs of departure and arrival airports [from, to], reconstruct the itinerary in order. All of the tickets belong to a man who departs from `JFK`. Thus, the itinerary must begin with `JFK`.

Note:

If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string. For example, the itinerary `["JFK", "LGA"]` has a smaller lexical order than `["JFK", "LGB"].`
All airports are represented by three capital letters (IATA code).
You may assume all tickets form at least one valid itinerary.
One must use all the tickets once and only once.
Example 1:

```
Input: [["MUC", "LHR"], ["JFK", "MUC"], ["SFO", "SJC"], ["LHR", "SFO"]]
Output: ["JFK", "MUC", "LHR", "SFO", "SJC"]
```

Example 2:

```
Input: [["JFK","SFO"],["JFK","ATL"],["SFO","ATL"],["ATL","JFK"],["ATL","SFO"]]
Output: ["JFK","ATL","JFK","SFO","ATL","SFO"]
Explanation: Another possible reconstruction is ["JFK","SFO","ATL","JFK","ATL","SFO"].
             But it is larger in lexical order.
```

```java
/*
dfs & topological sort:
    recursive rule:
        while min heap is non empty:
            pick the smallest dest d from min heap
            dfs
        add cur to the result
    base case:
        when pq is empty: write cur to result, return

time: O(V + E)
space: O(V + E)
*/
class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> res = new ArrayList<>();
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        buildGraph(tickets, map);
        dfs("JFK", map, res);
        reverse(res);
        return res;
    }
    public void dfs(String from, Map<String, PriorityQueue<String>> map, List<String> res) 
    {
        PriorityQueue<String> neighbors = map.getOrDefault(from, new PriorityQueue<String>());
        if (neighbors.isEmpty()) {
            res.add(from);
            return;
        }
        
        //for each neighbor, visit
        while (!neighbors.isEmpty()) {
            String to = neighbors.poll();
            dfs(to, map, res);
        }
        res.add(from);
    }
    public void buildGraph(List<List<String>> tickets, Map<String, PriorityQueue<String>> map)
    {
        for (List<String> t : tickets) {
            String from = t.get(0);
            String to = t.get(1);
            PriorityQueue<String> l = map.getOrDefault(from, new PriorityQueue<String>());
            l.offer(to);
            map.put(from, l);
        }    
    }
    public void reverse(List<String> ls) {
        int i = 0;
        int j = ls.size() - 1;
        while (i < j) {
            String temp = ls.get(i);
            ls.set(i, ls.get(j));
            ls.set(j, temp);
            i++;
            j--;
        }
    }
}

```