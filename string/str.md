
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