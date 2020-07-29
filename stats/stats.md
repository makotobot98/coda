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

### [478. Generate Random Point in a Circle](https://leetcode.com/problems/generate-random-point-in-a-circle/) ****
Given the radius and x-y positions of the center of a circle, write a function randPoint which generates a uniform random point in the circle.
```
Note:

input and output values are in floating-point.
radius and x-y position of the center of the circle is passed into the class constructor.
a point on the circumference of the circle is considered to be in the circle.
randPoint returns a size 2 array containing x-position and y-position of the random point, in that order.
Example 1:

Input: 
["Solution","randPoint","randPoint","randPoint"]
[[1,0,0],[],[],[]]
Output: [null,[-0.72939,-0.65505],[-0.78502,-0.28626],[-0.83119,-0.19803]]
Example 2:

Input: 
["Solution","randPoint","randPoint","randPoint"]
[[10,5,-7.5],[],[],[]]
Output: [null,[11.52438,-8.33273],[2.46992,-16.21705],[11.13430,-12.42337]]
```

```java
/*
alg: Rejection Sampling
generate points instead on a square bounded by 2 * radius, then if the generated point can have a eucalidean distance(sqrt(x - xcenter)^2 + (y - ycenter)^2) less than the radius, we then accept the sample
*/
class Solution {
    double xc;
    double yc;
    double rad;
    public Solution(double radius, double x_center, double y_center) {
        this.xc = x_center;
        this.yc = y_center;
        this.rad = radius;
    }
    
    public double[] randPoint() {
        double x0 = xc - rad;
        double y0 = yc - rad;
        while (true) {
            double x = x0 + Math.random() * rad * 2;
            double y = y0 + Math.random() * rad * 2;
            if (Math.sqrt(Math.pow((x - xc), 2) + Math.pow((y - yc), 2)) <= rad) {
                return new double[]{x, y};
            }
        }
        
    }
}
```