# Greedy

### **June Google Onsite** [402. Remove Digits](https://leetcode.com/problems/remove-k-digits/)
Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.

Note:

The length of num is less than 10002 and will be ≥ k.
The given num does not contain any leading zero.

```
Example 1:

Input: num = "1432219", k = 3
Output: "1219"
Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
Example 2:

Input: num = "10200", k = 1
Output: "200"
Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
Example 3:

Input: num = "10", k = 2
Output: "0"
Explanation: Remove all the digits from the number and it is left with nothing which is 0.
```

```java
/*
brute force: dfs with C(len, k) runtime
algorithm: greedy

lemma:
1. given two numbers after removed k digits n1 = [d1, d2, ... dx], n2 = [d2, ...., dy]: if d2 < d1, and index of d2 > d1, then n2 < n1

the algorithm works as following:
use a stack to track the most recent numbers to do the comparison
starting from index i from left to right of input num:
    while i < len:
        if k > 0:
            //we still have elements to remove
            while (k > 0 && num[i] < num[stackTop]):
                k--;
                stack.pop();
        stack.push(i)
    //post processing for monotonically increasing elements if k > 0:
    while k > 0:
        stack.pop

time: O(n)
space: O(n)
*/
class Solution {
    public String removeKdigits(String num, int k) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < num.length(); i++) {
            while (k > 0 && !stack.isEmpty() && num.charAt(i) < num.charAt(stack.peekFirst())) {
                k--;
                stack.pollFirst();
            }
            if (stack.isEmpty() && num.charAt(i) == '0') {
                continue;
            }
            stack.offerFirst(i);
        }
        
        while (k > 0 && !stack.isEmpty()) {
            stack.pollFirst();
            k--;
        }
        if (stack.isEmpty()) {
                return "0";
        }
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(num.charAt(stack.pollFirst()));
        }
        return res.reverse().toString();
    }
}
```