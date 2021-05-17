# Recursion

### 1306. Jump Game III 

```java
/*
recursion

rec(arr, i, visited): return if can reach a 0 from i

recursive rule:
    if rec(arr, i + arr[i] & i - arr[i], dp):
        return true
    return false
    
2
*/
class Solution {
    public boolean canReach(int[] arr, int start) {
        Set<Integer> set = new HashSet<Integer>();
        set.add(start);
        return rec(arr, start, set);
    }
    public boolean rec(int[] arr, int i, Set<Integer> set) {
        if (arr[i] == 0) {
            return true;
        }
        
        if (!set.contains(i + arr[i]) && i + arr[i] < arr.length && i + arr[i] >= 0) {
            set.add(i + arr[i]);
            if (rec(arr, i + arr[i], set)) {
                return true;
            }
        }
        
        if (!set.contains(i - arr[i]) && i - arr[i] < arr.length && i - arr[i] >= 0) {
            set.add(i - arr[i]);
            if (rec(arr, i - arr[i], set)) {
                return true;
            }
        }
        return false;
    }
}
```



### 337. House Robber III \*\*\*

```java
class Solution {
    public int rob(TreeNode root) {
        int[] res = rec(root);
        return Math.max(res[0], res[1]);
    }
    public int[] rec(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        
        int[] l = rec(root.left);
        int[] r = rec(root.right);
        //rob root
        int rob = root.val + l[1] + r[1];
        
        //not rob root
        int nrob = Math.max(l[0], l[1]) + Math.max(r[0], r[1]);
        return new int[]{rob, nrob};
    }
}
```



### 394. Decode String \*\*\*

```java
/*
recursion:
define rec(s, i, sb) where we are decoding string in from s[i ....], we keep i as global

recursive rule:
    case #: parse the number, decode the inner string with rec(s, i = non number offset, sb)
    case c: return c + rec(s, i + 1, sb)
    case ]: return empty string
*/
class Solution {
    public String decodeString(String s) {
        return rec(s, new int[1], 0);
    }
    public String rec(String s, int[] i, int b) {
        if (i[0] >= s.length()) {
            return "";
        }
        
        char c = s.charAt(i[0]);
        if (c == ']') {
            if (b - 1 == 0) {
                i[0] += 1;
                return "";
            }
            return rec(s, i, b - 1);
        } else if (c == '[') {
            i[0] += 1;
            return rec(s, i, b + 1);  
        } else if (Character.isDigit(c)) {
            int num = parseDigit(s, i);
            String str = rec(s, i, b);
            StringBuilder sb = new StringBuilder();
            while (num > 0) {
                sb.append(str);
                num--;
            }
            return sb.toString() + rec(s, i, b);
        } else {
            i[0] += 1;
            return c + rec(s, i, b);
        }
    }
    
    public int parseDigit(String s, int[] i) {
        int num = 0;
        while (i[0] < s.length() && Character.isDigit(s.charAt(i[0]))) {
            num = num * 10 + (s.charAt(i[0]++) - '0');
        }
        return num;
    }
}
```

#### Better Iterative Solution with stack

```java
class Solution {
    String decodeString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder currentString = new StringBuilder();
        int k = 0;
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                k = k * 10 + ch - '0';
            } else if (ch == '[') {
                // push the number k to countStack
                countStack.push(k);
                // push the currentString to stringStack
                stringStack.push(currentString);
                // reset currentString and k
                currentString = new StringBuilder();
                k = 0;
            } else if (ch == ']') {
                StringBuilder decodedString = stringStack.pop();
                // decode currentK[currentString] by appending currentString k times
                for (int currentK = countStack.pop(); currentK > 0; currentK--) {
                    decodedString.append(currentString);
                }
                currentString = decodedString;
            } else {
                currentString.append(ch);
            }
        }
        return currentString.toString();
    }
}
```

### 96. Unique Binary Search Trees \*

```java
class Solution {
    public int numTrees(int n) {
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int k = 2; k < i; k++) {
                dp[i] += dp[k - 1] * dp[i - k];
            }
            dp[i] += 2 * dp[i - 1]; //k = 1 and k = i as root
        }
        return dp[n];
    }
}

/*
# of bst with i being root, in range of [1 ... i ... n]
= # of bst in range of [0 ... i - 1] * # of bst in range of [i + 1 ... n]

# of bst in range of [i + 1 ... n] = # of bst in range [0 ... (n - (i + 1)] = [1 ... n - i]

let dp[i] = # of unique bst in range [1 ... i]
dp[i] = sum(dp[k - 1] * dp[i - k]) for k in [1 ... i]
time: O(n^2)
space: O(n)
*/
```



## Tree Recursion

### L. Construct Binary Tree from Inorder and Postorder Traversal

Given inorder and postorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.
```
For example, given

inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7
```

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
/*
algorithm: recursion with inordr map

create a map maping tuples of (value at inorder, index at inorder) provided that inorder indices are unique
buld with post order arrary form postorder[len - 1] to left

recursion(int[] inorder, int[] postorder, Map<Integer, Integer> map, int[] cur, int l, int r):
    : map stores the inorder mapping, cur is the global index at postorder, l & r defines the boudaries in inorder array
    
recursive rule:
    root := postoder[cur[0]]    #build current root
    cur[0]--
    inorderIdx := map[root.val]
    recursively build right subtree with boundary(inorderIdx + 1, r)
    .... left subtree with boundary(l, inorderIdx - 1)

base case:
    if l > r: return null

time: O(n)
sapce: O(n)
*/
class Solution {
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        int[] cur = {postorder.length - 1};
        return rec(map, postorder, cur, 0, postorder.length - 1);
    }
    public TreeNode rec(Map<Integer, Integer> map, int[] postorder, int[] cur, int l, int r) {
        if (l > r) {
            return null;
        }
        
        TreeNode root = new TreeNode(postorder[cur[0]--]);
        int inorderIdx = map.get(root.val);
        root.right = rec(map, postorder, cur, inorderIdx + 1, r);
        root.left = rec(map, postorder, cur, l, inorderIdx - 1);
        return root;
    }
}
```