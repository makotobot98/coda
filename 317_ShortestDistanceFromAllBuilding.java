/*
alg1: best first search from each 0, time: O(m^2 * n^2 )
alg2: best first search from each 1, time" O(kmn)

detail:
    for each 1-entry:
        initial state: queue of a 1-entry, set of 1-entry
        expand/generate:
            expand: expand all nodes of this level, res[i][j] += level
            generate: generate all 0-neighbors
        terminate: queue is empty
    post processing: linear scan res[][], get the minimum
    
to avoid edge case that there are some entry cannot reach all building, and some building cannot be reached by any entry. we store each res[][] for each round of bfs, meaning if we have k buildings, we store k res[][], and when post processing, we can determine based on the res[i][j] if we met the edge case
*/
class Solution {
    public int shortestDistance(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        List<List<Integer>> buildings = new ArrayList<>();
        //collect all buiding entry
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    buildings.add(Arrays.asList(i, j));
                }
            }
        }
        List<Map<List<Integer>, Integer>> dists = new ArrayList<>();
        
        //bfs
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (List<Integer> building : buildings) {
            Map<List<Integer>, Integer> dist = new HashMap<>();
            dists.add(dist);
            Queue<List<Integer>> queue = new LinkedList<>();
            queue.offer(building);
            int level = 0;
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    List<Integer> p = queue.poll();
                    //add level to distance
                    int cur_i = p.get(0);
                    int cur_j = p.get(1);
                    for (int[] dir : dirs) {
                        int r = cur_i + dir[0];
                        int c = cur_j + dir[1];
                        List<Integer> neighbor = Arrays.asList(r, c);
                        if (r >= 0 && r < m && c >= 0 && c < n && grid[r][c] != 1 && grid[r][c] != 2 && !dist.containsKey(neighbor)) {
                            queue.offer(neighbor);
                            dist.put(neighbor, level + 1);
                        }
                    }
                }
                level++;
            }
        }
        
        //post processing
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                //if entry i,j is a valid starting point
                if (grid[i][j] == 0) {
                    //iterate each distance in each map
                    int totalDistance = 0;
                    for (Map<List<Integer>, Integer> dist : dists) {
                        Integer d = dist.get(Arrays.asList(i, j));
                        if (d == null) {
                            //if cannot reach each buiding from i,j
                            totalDistance = Integer.MAX_VALUE;
                            break;
                        }
                        totalDistance += d;
                    }
                    min = Math.min(totalDistance, min);
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }
}