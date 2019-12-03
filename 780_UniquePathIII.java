/*
alg: dfs + backtrack
each time select a direction to go, we have 4 ways to choose. there are in total m*n levels
time: O(4^(m*n))
space: O(mn)

data structure: 
    1. hashset with backtrack 
    
detail:
    at each i,j
        1. add i,j into the hashset
        2. try four direction that's not visited
*/

class Solution {
    public int uniquePathsIII(int[][] grid) {
        int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        Set<List<Integer>> set = new HashSet<>();
        int[] count = new int[1];
        //locate starting point, # of 0's
        int nzeros = 0;
        int start_i = 0;
        int start_j = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0 || grid[i][j] == 1) {
                    nzeros++;
                    if (grid[i][j] == 1) {
                        start_i = i;
                        start_j = j;
                    }
                }
            }
        }
        dfs(grid, start_i, start_j, nzeros, count, set, dirs);
        return count[0];
    }
    public void dfs(int[][] grid, int i, int j, int total, int[] count, Set<List<Integer>> visited, int[][] dirs) 
    {
        if (grid[i][j] == 2 && visited.size() == total) {
            count[0]++;
            return;
        } else if (grid[i][j] == 2 || grid[i][j] == -1) {
            return;
        }    
        
        List<Integer> curPoint = Arrays.asList(i, j);
        visited.add(curPoint);
        for (int[] dir : dirs) {
            int i_next = i + dir[0];
            int j_next = j + dir[1];
            if (i_next >= 0 && i_next < grid.length && j_next >= 0 && j_next < grid[0].length && !visited.contains(Arrays.asList(i_next, j_next))) {
                dfs(grid, i_next, j_next, total, count, visited, dirs);
            }
        }
        visited.remove(curPoint);
    }
}