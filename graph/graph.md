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

