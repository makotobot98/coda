/*
alg: recursion
    when constructing bst with i as root, we have left be [0 ... i - 1], right [i + 1 ... n]
    
    suppose recursive function f returns # of unique BST from [0, n]
    for i in [1, n]:
        pick a number i as root, so root = i
        # of ways to make BST with i as root  
            = # of BST formed from [0 ... i - 1] * # of BST formed from [i + 1 ... n]
            = f(i - 1) * # of BST formed from [i + 1 ... n]
            = f(i - 1) * f(n - i)  //since # BST formed from [123] = [456], structure of BST would be the same, so we can map # of BST from [456] to [123]
    
*/
class Solution {
    public int numTrees(int n) {
        if (n <= 1) {
            return n;
        }    
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[0] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                //root = j
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        return dp[n];
    }
}