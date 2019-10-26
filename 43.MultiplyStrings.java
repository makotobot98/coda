/*
algorithm: string concatination
    1. multiply one digit of num1 to all digits of num2 (from base 10 power 1 to n, so from num2[len - 1 ... 0]), for next digit of num1, do the same
    except append one zero at the start
        continue for all digit in num1
    2. add string to the result
    3. reverse the result
    
    "10"
    "23"
    
    -> multiply 3 to 10 = 30, pad 0 zero -> 30 -> add to res = 03   (res in reverse order)
    -> multiply 2 to 10 = 2, pad 1 zero -> 200 -> add to res = 03
                                                              +002
                                                              = 032
    -> reverse 320

Complexity:
    rime: O(# of multiplication = m*n), m,n denotes length of num1, num2
    space: O(m + n), used to store and update the results in a stringbuilder

*/
class Solution {
    public String multiply(String num1, String num2) {
        String s = num1.length() < num2.length() ? num1 : num2; //assign s to short string
        String l = s == num1 ? num2 : num1; //assign l to long string
        
        StringBuilder res = new StringBuilder("0");
        for (int i = s.length() - 1; i >= 0; i--) {
            StringBuilder curNum = new StringBuilder();
            
            //padd zero
            for (int numZero = 0; numZero < s.length() - i - 1; numZero++) {
                curNum.append(0);
            }
            
            //multiply s[i] to l, then reverse
                // 2 * "123", i = 1 --> "0642" = 2460
            if (s.charAt(i) == '0') {
                continue;
            }
            
            multiplyNums(s.charAt(i) - '0', l, curNum);
            

            //System.out.println("i: " + i + " " + curNum);
            //add to res
                //2460 + 230 = "0642" + "032" = "0962" = 2960
            res = add(res, curNum);
        }
        return res.reverse().toString();
    }
    //multiply num to l, 2 * "230" = 460 = "064"
    public void multiplyNums(int num, String l, StringBuilder res) {
        int prod = 0;
        for (int i = l.length() - 1; i >= 0; i--) {
            prod += num * (l.charAt(i) - '0');
            res.append(prod % 10);
            prod /= 10;
        }
        //post processing for prod != 0
        if (prod != 0) {
            res.append(prod);
        }
    }
    
    //add num to res
    //input: "002", "03" (same as 200 + 30)
    public StringBuilder add(StringBuilder a, StringBuilder b) {
        StringBuilder res = new StringBuilder();
        int sum = 0;
        int i = 0;
        int j = 0;
        while (i < a.length() || j < b.length()) {
            if (i < a.length()) {
                sum += (a.charAt(i) - '0');
                i++;
            }
            if (j < b.length()) {
                sum += (b.charAt(j) - '0');
                j++;
            }
            res.append(sum % 10);
            sum /= 10;
        }
        //post processing for sun != 0
        if (sum != 0) {
            res.append(sum);
        }
        return res;
    }
    
}