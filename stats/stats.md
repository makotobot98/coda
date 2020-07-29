### 470. Implement Rand10() Using Rand7()

Given a function rand7 which generates a uniform random integer in the range 1 to 7, write a function rand10 which generates a uniform random integer in the range 1 to 10.

Do NOT use system's Math.random().

 
```
Example 1:

Input: 1
Output: [7]
Example 2:

Input: 2
Output: [8,4]
Example 3:

Input: 3
Output: [8,1,10]
```

```java
/*
rejection sampling:
with rand7() we can generate range of [0, 7^1 - 1], [0, 7^2 - 1], [0, 7^3 - 1], here we use [0, 7^1]. so row twice first time we roll for the
first bit, then second we roll for the significant bit

e.g., if we roll first time = 4, second time = 2, then we generated 2*7^1 + 4 * 7^0 = 18, notice with this algrithm numbers from [0 ... 48] can be generated evenly. now we pick the range [0 ... 39] to be the desired projection, and if we roll to # other than this range, we reject it and roll again

time: O(1 -> inf)

*/
class Solution extends SolBase {
    public int rand10() {
        int res = 50;
        while (res >= 40) {
            int first = rand7() - 1;
            int sec = rand7() - 1;
            res = sec * 7 + first;
        }
        return (res % 10) + 1;
    }
}
```