/*
custom define the comparator
for s1 and s2:
    1. letter-logs has higher priority than the digit-log
    2. if both are letter log
        a. compare lexicographical order, if sam lexicographical order,
            lexicographical order if the identifier
    3. if both are digit
        a. compare their index in the original logs array, which we can use merge sort to
            preserve the stability of the original logs
*/
class Solution {
    public String[] reorderLogFiles(String[] logs) {
        if (logs == null || logs.length < 2) {
            return logs;
        }
        Arrays.sort(logs, (log1, log2) -> {
            String[] split1 = log1.split(" ", 2);
            String[] split2 = log2.split(" ", 2);
            boolean isDigit1 = Character.isDigit(split1[1].charAt(0));
            boolean isDigit2 = Character.isDigit(split2[1].charAt(0));
            if (!isDigit1 && !isDigit2) {
                int res = split1[1].compareTo(split2[1]);
                return res == 0 ? split1[0].compareTo(split2[0]) : res;
            }
            return isDigit1 ? (isDigit2 ? 0 : 1) : -1;
        });
        return logs;
    }
}