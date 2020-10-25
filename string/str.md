

### 468. Validate IP Address  **
Write a function to check whether an input string is a valid IPv4 address or IPv6 address or neither.

IPv4 addresses are canonically represented in dot-decimal notation, which consists of four decimal numbers, each ranging from `0` to `255`, separated by dots (`"."`), e.g.,`172.16.254.1`;

Besides, leading zeros in the IPv4 is invalid. For example, the address `172.16.254.01` is invalid.

IPv6 addresses are represented as eight groups of four hexadecimal digits, each group representing 16 bits. The groups are separated by colons (`":"`). For example, the address `2001:0db8:85a3:0000:0000:8a2e:0370:7334` is a valid one. Also, we could omit some leading zeros among four hexadecimal digits and some low-case characters in the address to upper-case ones, so `2001:db8:85a3:0:0:8A2E:0370:7334` is also a valid IPv6 address(Omit leading zeros and using upper cases).

However, we don't replace a consecutive group of zero value with a single empty group using two consecutive colons (`::`) to pursue simplicity. For example, `2001:0db8:85a3::8A2E:0370:7334` is an invalid IPv6 address.

Besides, extra leading zeros in the IPv6 is also invalid. For example, the address `02001:0db8:85a3:0000:0000:8a2e:0370:7334` is invalid.

Note: You may assume there is no extra space or special characters in the input string.

> Example
>
> Input: "2001:0db8:85a3:0:0:8A2E:0370:7334"
>
> Output: "IPv6"
> 
> Explanation: This is a valid IPv6 address, return "IPv6".

```java
class Solution {
    String NEITHER = "Neither";
    String IPV4_STR = "IPv4";
    String IPV6_STR = "IPv6";
    
    public String validIPAddress(String IP) {
        int IPV4 = 4;
        int IPV6 = 8;
        //System.out.println(IP.split("\\.").length);
        String[] splits = IPSplit(IP);
        //System.out.println(splits.length);
        if (splits.length == IPV4) {
            return ipv4(splits);
        } else if (splits.length == IPV6) {
            return ipv6(splits);
        }
        return NEITHER;
    }
    
    public String ipv4(String[] splits) {
        for (int i = 0; i < splits.length; i++) {
            String s = splits[i];
            if (s.length() == 0) {
                return NEITHER;
            }
            if (s.charAt(0) == '0') {
                //System.out.println("123");
                //begin with zero
                if (s.length() > 1) {
                    return NEITHER;
                }
            } else {
                //System.out.println("28");
                //not begin with zero
                int n = parseInt(s);
                if (n < 0 || n > 255) {
                    return NEITHER;
                }
            }
        }
        return IPV4_STR;
    }
    
    public String ipv6(String[] splits) {
        int LEN = 4;
        for (int i = 0; i < splits.length; i++) {
            String s = splits[i];
            if (s.length() > LEN || s.length() <= 0) {
                return NEITHER;
            }
            
            int n = parseIntHex(s);
            if (n < 0) {
                return NEITHER;
            }
        }
        return IPV6_STR;
    }
    public String[] IPSplit(String IP) {
        if (IP == null || IP.length() == 0) {
            return new String[0];
        }
        
        
        if (IP.contains(".")) {
            if (IP.charAt(IP.length() - 1) == '.' || IP.charAt(0) == '.') {
                return new String[0];
            }
            return IP.split("\\.");
        } else if (IP.contains(":")) {
            if (IP.charAt(IP.length() - 1) == ':' || IP.charAt(0) == ':') {
                return new String[0];
            }
            return IP.split(":");
        } else {
            return new String[0];
        }
    }
    public int parseIntHex(String s) {
        int n = 0;
        int base = 16;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                c = Character.toUpperCase(c);
                if (c < 'A' || c > 'F') {
                    return -1;
                }
                n = n * 16 + (c - 'A');
            } else if (Character.isDigit(c)) {
                n = n * 16 + (c - '0');
            } else {
                return -1;
            }
        }
        return n;
    }
    public int parseInt(String s) {
        //System.out.println(s);
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return -1;
            }
            n = n * 10 + (c - '0');
        }
        return n;
    }
}
```

### [151. Reverse Words in a String](https://leetcode.com/problems/reverse-words-in-a-string/) ***
Given an input string, reverse the string word by word.

 
```
Example 1:

Input: "the sky is blue"
Output: "blue is sky the"
Example 2:

Input: "  hello world!  "
Output: "world! hello"
Explanation: Your reversed string should not contain leading or trailing spaces.
Example 3:

Input: "a good   example"
Output: "example good a"
Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
```

```java
/*
"hello yahoo"
"oohay olleo"
"yahoo olleo"

->preprocess: remove all leading and trailing spaces
-> reverse entire string, then reverse each word

time: O(n)
space: O(n)

*/
class Solution {
    public String reverseWords(String s) {
        int n = s.length();
        if (n == 0) {
            return s;
        }
        StringBuilder sb = removeSpace(s);
        n = sb.length();
        reverse(sb, 0, n - 1);
        int i = 0;
        for (int j = 0; j <= n; j++) {
            if (j == n || sb.charAt(j) == ' ') {
                reverse(sb, i, j - 1);
                i = j + 1;
            }
            
        }
        
        return sb.toString();
    }
    public void reverse(StringBuilder sb, int i, int j) {
        while (i < j) {
            char temp = sb.charAt(i);
            sb.setCharAt(i, sb.charAt(j));
            sb.setCharAt(j, temp);
            i++;
            j--;
        }
    }
    
    /*
    alg: two pointer
    define [0, i) removed space chars
           [i, j) spaces
           [j, n) unvisited
    
    for j < n:
        if j == ' ':
            if i == 0: j++
            if s[i - 1] == ' ': j++
        if j != ' ':
            s[i++] = s[j++]
    post process to remove the last space
    */
    public StringBuilder removeSpace(String s) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int n = s.length();
        for (int j = 0; j < n; j++) {
            if (s.charAt(j) == ' ') {
                if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') {
                    sb.append(s.charAt(j));
                }
            } else {
                sb.append(s.charAt(j));
            }
        }
        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }
}
```

### 6. ZigZag Conversion
The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
```
P   A   H   N
A P L S I I G
Y   I   R
And then read line by line: "PAHNAPLSIIGYIR"

Write the code that will take a string and make this conversion given a number of rows:

string convert(string s, int numRows);
Example 1:

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"
Example 2:

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:

P     I    N
A   L S  I G
Y A   H R
P     I
```

```java
/*
alg: iterative following the requirements

create a list of strings ls, track the global index read cur at s
for each iteration:
    for i < ls.len: append s[cur++] to ls[i]
    set i = ls.len - 2
    for i > 0: append s[cur++] to ls[i]
    
    break at anytime when cur == s.length

time: O(n)
space: O(n)
*/
class Solution {
    public String convert(String s, int numRows) {
        int n = s.length();
        if (n == 0) {
            return s;
        }
        List<StringBuilder> ls = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            ls.add(new StringBuilder());
        }
        
        int cur = 0;
        while (cur != n) {
            for (int i = 0; i < numRows && cur != n; i++) {
                StringBuilder sb = ls.get(i);
                sb.append(s.charAt(cur++));
            }
            for (int i = numRows - 2; i > 0 && cur != n; i--) {
                StringBuilder sb = ls.get(i);
                sb.append(s.charAt(cur++));
            }
        }
        
        StringBuilder res = new StringBuilder();
        for (StringBuilder sb : ls) {
            res.append(sb);
        }
        return res.toString();
    }
}
```

# Sliding Window

# Recursion
### 763. Partition Labels ***
A string S of lowercase English letters is given. We want to partition this string into as many parts as possible so that each letter appears in at most one part, and return a list of integers representing the size of these parts.

```
Example 1:

Input: S = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
```

```java
/*
recursion & hashmap<letter, rightmost appeared idx>

int f(idx, curRightIdx, s, map): returns the maximum window(so each letter in window appear once) right in the range[idx, curRightIdx]

recursive rule:
    finx maxIdx in range of [idx + 1, map[idx]], and map[maxIdx] > all map[i] in that range
    recursively find the wubdiw right calling f(maxIdx + 1, map[maxIdx], s, map)
base case:
    if maxIdx < curRightIdx: no need furthur search, return curRightIdx

time: O(n) since we searched each idx at most once, and load each idx to map once
space: O(1)
*/
class Solution {
    public List<Integer> partitionLabels(String S) {
        int[] seen = new int[26];
        int n = S.length();
        
        Arrays.fill(seen, Integer.MIN_VALUE);
        for (int i = 0; i < n; i++) {
            seen[S.charAt(i) - 'a'] = i;
        }
        
        //recursively build the window
        List<Integer> res = new ArrayList<>();
        int cur = 0;
        int prevWindowRight = -1;
        while (cur < n) {
            int curWindowRight = rec(cur, seen[S.charAt(cur) - 'a'], S, seen);
            res.add(curWindowRight - prevWindowRight);
            prevWindowRight = curWindowRight;
            cur = curWindowRight + 1;
        }
        return res;
    }
    //[idx, curRight] is the current minimum window
    int rec(int idx, int curRight, String s, int[] map) {
        
        int maxRight = map[s.charAt(idx) - 'a'];
        
        for (int i = idx + 1; i <= curRight; i++) {
            char c = s.charAt(i);
            if (map[c - 'a'] > maxRight) {
                maxRight = map[c - 'a'];
            }
        }
        
        if (maxRight <= curRight) {
            return curRight;
        }
        
        return rec(curRight + 1, maxRight, s, map);
    }
}
```

### 187. Repeated DNA Sequences *****
```java
class Solution {
  public List<String> findRepeatedDnaSequences(String s) {
    int L = 10, n = s.length();
    if (n <= L) return new ArrayList();

    // rolling hash parameters: base a
    int a = 4, aL = (int)Math.pow(a, L);

    // convert string to array of integers
    Map<Character, Integer> toInt = new
            HashMap() {{put('A', 0); put('C', 1); put('G', 2); put('T', 3); }};
    int[] nums = new int[n];
    for(int i = 0; i < n; ++i) nums[i] = toInt.get(s.charAt(i));

    int h = 0;
    Set<Integer> seen = new HashSet();
    Set<String> output = new HashSet();
    // iterate over all sequences of length L
    for (int start = 0; start < n - L + 1; ++start) {
      // compute hash of the current sequence in O(1) time
      if (start != 0)
        h = h * a - nums[start - 1] * aL + nums[start + L - 1];
      // compute hash of the first sequence in O(L) time
      else
        for(int i = 0; i < L; ++i) h = h * a + nums[i];
      // update output and hashset of seen sequences
      if (seen.contains(h)) output.add(s.substring(start, start + L));
      seen.add(h);
    }
    return new ArrayList<String>(output);
  }
}
```