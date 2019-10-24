/*
alg1: linear scan each entry and validate with hashset (set on rows, columns, and grids)
        time: O(n^2), space: O(n^2 + n^2 + n^2), n = 9

data structure:
    List<Set<Integer>> row;
    List<Set<Integer>> column;
    List<Set<Integer>> grid;

detail:
    preprocess to generate row, column, grid.
    linear scan each entry and validate if row, column, and corresponding grid does not contain entry i,j.
        - to map to the desired grid id:
            gridRow = i / 3
            gridColumn = j / 3
            gridId = gridRow * 3 + gridColumn * j
        grid in matrix:
        [0, 1, 2
         3, 4, 5,
         6, 7, 8]
         grid we use:
         [0, 1, 2, 3, 4, 5, 6, 7, 8]
    *notice we don't really need to preprocess to generate the row and column for hashset, we can use a hahset for each row and as we linear scan we look it up
alg2: lienar scan entry with hashset one pass
    above algorithm has the redundency that we have to 3 pass the board, we can actually do all processing in one pass, for each entry we add it to its corresponding row, column, and grid, and look the new entry up in each to see if we find duplicate
*/
class Solution {
    public boolean isValidSudoku(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return false;
        }
        
        int m = board.length;
        int n = board[0].length;
        List<Set<Integer>> grids = new ArrayList<>();
        List<Set<Integer>> rows = new ArrayList<>();
        List<Set<Integer>> cols = new ArrayList<>();
        //populate each grids, rows, cols with empty sets
        for (int i = 0; i < 9; i++) {
            grids.add(new HashSet<Integer>());
            rows.add(new HashSet<Integer>());
            cols.add(new HashSet<Integer>());
        }
        
        //linear scan
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != '.') {
                    int cur = board[i][j] - '0';
                    int gridRow = i / 3;
                    int gridCol = j / 3;
                    //check row i, check col j, check gridId = i * 3 + j
                    if (rows.get(i).contains(cur) || cols.get(j).contains(cur) || grids.get(gridRow * 3 + gridCol).contains(cur)) {
                        return false;
                    }
                    //add to rows, cols, grids
                    rows.get(i).add(cur);
                    cols.get(j).add(cur);
                    grids.get(gridRow * 3 + gridCol).add(cur);
                }
            }
        }
        return true;
    }
}