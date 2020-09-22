import java.util.LinkedList;
import java.util.Queue;

/* https://leetcode.com/problems/number-of-islands/
200. Number of Islands - MEDIUM
*/

class NumberOfIslands {
    /* Approach: find connected components in a graph
    use dfs recursive to visit elements in the grid, mark them visited as we see them.
    Run dfs on every element in the grid. If we find an element that is unvisited, 
    it is a new component and hence a new island.

    TC: O(m*n) [for nested loop] + O(m*n) [dfs] [dfs will be less than m*n since we are only visiting lands (1), worst case will be m*n]
    SC: O(m*n) [worst case complete land - stack space]
    */
    public int numIslandsDFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int count = 0;
        int[][] dirs = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != '0' && grid[i][j] != 'x') {
                    count++;
                    dfs(grid, i, j, dirs);
                }
            }
        }
        return count;
    }
    
    /* Approach: find connected components in a graph
    use bfs to visit elements in the grid, mark them visited as we see them.
    Run bfs on every element in the grid. If we find an element that is unvisited, 
    it is a new component and hence a new island.

    TC: O(m*n) [for nested loop] + O(m*n) [bfs] 
    SC: O(m*n) [queue] will be less than this.
    */
    public int numIslandsBFS(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        int count = 0;
        int[][] dirs = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != '0' && grid[i][j] != 'x') {
                    // increment island count
                    count++;
                    
                    // BFS
                    Queue<int[]> q = new LinkedList<>();
                    
                    q.offer(new int[] {i, j});
                    
                    while (!q.isEmpty()) {
                        
                        int size = q.size();
                        
                        for (int k = 0; k < size; k++) {
                            int[] node = q.remove();
                            grid[node[0]][node[1]] = 'x';
                            
                            for (int[] dir: dirs) {
                                int r = node[0] + dir[0];
                                int c = node[1] + dir[1];
                                
                                if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length && grid[r][c] != 'x' && grid[r][c] != '0') {
                                    q.offer(new int[]{r,c});
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }
    
    private void dfs(char[][] grid, int x, int y, int[][] dirs) {
        if (grid[x][y] == '0' || grid[x][y] == 'x') {
            return;
        }
        
        grid[x][y] = 'x';
        
        for (int[] dir: dirs) {
            int r = x + dir[0];
            int c = y + dir[1];
            
            if (r >= 0 && r < grid.length && c >= 0 && c < grid[0].length) {
                dfs(grid, r, c, dirs);
            }
        }
    }
}