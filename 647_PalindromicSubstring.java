/*
alg1: brute force to scan each possible substr starting at each idx i. time: O(n^3)
alg2: dynamic programming
    let M[i][j] = if s[i ... j] is a palindrome
    smallest problem:
        M[i][i] = true
    induction rule:
        M[i][j] = false if s[i] != s[j]
                = true if s[i] == s[j] && (i + 1 == j || M[i + 1][j - 1])
    use a global count to track # of substr found
    time: O(n^2)
    space: O(n^2)
*/
class Solution {
    public int countSubstrings(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int count = 0;
        int n = s.length();
        boolean[][] M = new boolean[n][n];
        //base case
        for (int i = 0; i < n; i++) {
            M[i][i] = true;
            count++;
        }
        
        //induction rule
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (i + 1 == j || M[i + 1][j - 1]) {
                        M[i][j] = true;
                        count++;
                    }
                }
            }
        }
        return count;
    }
}