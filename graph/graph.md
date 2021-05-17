# Uncategorized

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



### 721. Accounts Merge\*\*\*\*



```java
/*
alg: build a graph and dfs for connected components

build a bidirectional graph so all emails under the same account will have an edge to the first email(this is arbitrary, can be 2nd, or last ..). With usage of HashMap<email, ListOfEmailSameAcc>, we can manage to build a graph by iterating accounts

after which we run dfs to add all emails in the same connected component

time: O(sum(aloga)), where a = length accounts[i] for each acc
space: O(sum(a))
*/
class Solution {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, List<String>> graph = buildGraph(accounts);
        List<List<String>> res = new ArrayList<>();
        
        //dfs
        Set<String> seen = new HashSet<>();
        for (List<String> acc : accounts) {
            String name = acc.get(0);
            String emailFirst = acc.get(1);
            if (!seen.contains(emailFirst)) { // only dfs on emails that have not been traversed yet
                List<String> ls = new ArrayList<>();
                ls.add(name);
                seen.add(emailFirst);
                dfs(emailFirst, graph, ls, seen);
                //append the merged account
                Collections.sort(ls);
                res.add(ls);
            }
        }
        return res;
    }
    
    public void dfs(String cur, Map<String, List<String>> graph, List<String> ls, Set<String> set) {
        ls.add(cur);
        for (String e : graph.get(cur)) {
            if (!set.contains(e)) {
                set.add(e);
                dfs(e, graph, ls, set);
            }
        }
    }
    
    public Map<String, List<String>> buildGraph(List<List<String>> accounts) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (List<String> acc : accounts) {
            String name = acc.get(0);
            
            String emailFirst = acc.get(1);
            for (int i = 1; i < acc.size(); i++) {
                String email = acc.get(i);
                map.computeIfAbsent(email, x -> new ArrayList<String>()).add(emailFirst);
                map.computeIfAbsent(emailFirst, x -> new ArrayList<String>()).add(email);
            }
        }
        
        return map;
    }
}
```



### 1192. Critical Connections in a Network (dfs cycle detection)

```java
/*
the problem is the same as finding an edge that's not part of the cycle. dfs backtracking with height

suppose we pass a height parameter h as we run the dfs in the graph, for any node cur, if we found cur.neighbor.h != null, we have found a cycle, then as we backtracking, if cur.h > found cycle height then cur is part of the cycle, else cur to cur.neighbor is a critical connection

alg:
    1. build a bidirectional graph
    2. run dfs to find the non cycle edge
    recursive rule:
        for neighbor n:
            if height of n < cur || min height found in n < cur:
                (n, cur) is a cycle edge
            else:
                add (n, cur) to result
        return minHeightFound in cur
*/
class Solution {
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        Map<Integer, List<Integer>> graph = buildGraph(connections);
        List<List<Integer>> res = new ArrayList<>();
        int[] minHeights = new int[n];
        Arrays.fill(minHeights, Integer.MAX_VALUE);
        dfs(0, -1, minHeights, 0, graph, res);
        return res;
    }
    //return the found cycle height
    public int dfs(int cur, int prev, int[] minHeights, int h, Map<Integer, List<Integer>> graph, List<List<Integer>> res) 
    {
        
        if (minHeights[cur] != Integer.MAX_VALUE) {
            return minHeights[cur];
        }
        
        minHeights[cur] = h;
        int foundMinHeight = h; //track the minimum height found, since there could be multiple cycles, track the cycle with the min height can assure we find all cycle edges
        for (int n : graph.get(cur)) {
            if (n == prev) {
                continue;
            }
            int height = dfs(n, cur, minHeights, h + 1, graph, res);
            if (h < height) {
                res.add(Arrays.asList(cur, n));
            } else {
                foundMinHeight = Math.min(foundMinHeight, height);
            }
        }
        minHeights[cur] = foundMinHeight; //update min height
        return foundMinHeight;
    }
    public Map<Integer, List<Integer>> buildGraph(List<List<Integer>> ls) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (List<Integer> l : ls) {
            map.computeIfAbsent(l.get(0), k -> new ArrayList<Integer>()).add(l.get(1));
            map.computeIfAbsent(l.get(1), k -> new ArrayList<Integer>()).add(l.get(0));
        }
        return map;
    }
}
```



# Connected Component

### BinarySearch 656. Gene Mutation Groups

You are given a list of unique strings `genes` where each element has the same length and contains characters `"A"`, `"C"`, `"G"` and/or `"T"`.

- If strings `a` and `b` are the same string except for one character, then `a` and `b` are in the same mutation group.
- If strings `a` and `b` are in a group and `b` and `c` are in a group, then `a` and `c` are in the same group.

Return the total number of mutation groups.

```
Example 1
Input:
	genes = ["ACGT", "ACCT", "AGGT", "TTTT", "TTTG"]
Output
	2
	
Explanation:
There are two mutation groups:
- ["ACGT", "ACCT", "AGGT"]
- ["TTTT", "TTTG"]
```

