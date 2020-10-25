### [496. Next Greater Element I](https://leetcode.com/problems/next-greater-element-i/)
You are given two arrays (without duplicates) nums1 and nums2 where nums1’s elements are subset of nums2. Find all the next greater numbers for nums1's elements in the corresponding places of nums2.

The Next Greater Number of a number x in nums1 is the first greater number to its right in nums2. If it does not exist, output -1 for this number.

```
Example 1:
Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
Output: [-1,3,-1]
Explanation:
    For number 4 in the first array, you cannot find the next greater number for it in the second array, so output -1.
    For number 1 in the first array, the next greater number for it in the second array is 3.
    For number 2 in the first array, there is no next greater number for it in the second array, so output -1.
```

```java
/*
nums1 = [4,1,2]
nums2 = [1,3,4,2]

alg1: hashmap (value at nums1, index at nums2), time O(n^2), space: O(n)

alg2: hashmap + increasing stack, m = nums1.len, n = nums2.len, time: O(m + n) space: O(m)
    create int[] res := new int[nums1.length]
    preprocess to create and init hashmap < value at nums1, greater value at nums2 >, so (n1, -1),(n2, -1) are inserted
    create a stack
    for i < len:
        if !stack.isEmpty && nums2[i] > nums2[stack.top]:
            smallIdx := stack.pop
            if map[nums2[smalIdx]] != null: //this greater value needs to be recorded
                map[nums2[smallIdx]] = nums2[i]
        stack.offer(i)
    
    post process to add all greater element into result
            
*/
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int[] res = new int[nums1.length];
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums1) {
            map.put(n, -1);
        }
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < nums2.length; i++) {
            while (!stack.isEmpty() && nums2[i] > nums2[stack.peekFirst()]) {
                int idx = stack.pollFirst();
                //System.out.println("at i: " + i + " idx: " + idx);
                if (map.containsKey(nums2[idx])) {
                    map.put(nums2[idx], nums2[i]);
                }
            }
            stack.offerFirst(i);
        }
        
        for (int i = 0; i < nums1.length; i++) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }
}
```

### [498. Diagonal Traverse] (https://leetcode.com/problems/diagonal-traverse/)
Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as shown in the below image.

```
Example:

Input:
[
 [ 1, 2, 3 ],
 [ 4, 5, 6 ],
 [ 7, 8, 9 ]
]

Output:  [1,2,4,7,5,3,6,8,9]
```
```java
/*
alg: traverse by requirement
*/
class Solution {
    public int[] findDiagonalOrder(int[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return new int[0];
        }
        int n = matrix[0].length;
        int i = 0;
        int j = 0;
        int[] res = new int[m * n];
        int cur = 0;
        int dir = 0;
        //even going up, odd going down
        while (i != m - 1 || j != n - 1) {
            //System.out.println("i: " + i + "j: " + j);
            res[cur++] = matrix[i][j];
            if (dir == 0) {
                //up
                if (j == n - 1) {
                    i++;
                    dir = 1;
                } else if (i == 0) {
                    j++;
                    dir = 1;
                } else {
                    i--;
                    j++;
                }
            } else {
                //down
                if (i == m - 1) {
                    j++;
                    dir = 0;
                } else if (j == 0) {
                    i++;
                    dir = 0;
                } else {
                    i++;
                    j--;
                }
            }
        }
        res[cur] = matrix[m - 1][n - 1];
        return res;
    
    }
}
```


### [493. Reverse Pairs](https://leetcode.com/problems/reverse-pairs/) *****
Given an array nums, we call (i, j) an important reverse pair if `i < j and nums[i] > 2*nums[j].`

You need to return the number of important reverse pairs in the given array.
```
Example1:

Input: [1,3,2,3,1]
Output: 2
Example2:

Input: [2,4,3,5,1]
Output: 3
Note:
The length of the given array will not exceed 50,000.
All the numbers in the input array are in the range of 32-bit integer.
```
```java
/*
merge sort: since merge sort essentially is equivalent to the problem of comparing elements in the array, and each pair of element will be compared at most once and will be sorted and thus not be compared again, thus we can compute the count of reverse pairs at each merging step and aggregate the result

recursive rule:
    split the nums into left and right
    recursively mergeSort left half and collect the count of reverse pairs found
    recursively mergeSort .... right half
        //count # of reversed pair with i = elements from left half, j = elements from right half, note this relation is *transitive*, thus can be computed in linear time
    merge and count the reverse pair during comparing left and right half
    
    return the aggregating counts of left, right, and reverse pairs during the merge process

time: O(nlogn)
space: O(n) for merge helper array

*/
class Solution {
    public int reversePairs(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        
        int[] helper = new int[nums.length];
        return mergeSort(nums, helper, 0, nums.length - 1);
    }
    public int mergeSort(int[] nums, int[] helper, int l, int r) {
        if (l >= r) {
            return 0;
        }
        
        int mid = l + (r - l) / 2;
        int lcount = mergeSort(nums, helper, l, mid);   //left bounded by [l ... mid]
        int rcount = mergeSort(nums, helper, mid + 1, r);   //right bounded by [mid + 1 ... r]
        
        //compute the count from two sorted array, based on the transitive nature
        // if nums[i] > nums[i - k], then j such that nums[i - k] > 2 * nums[j] can beadded to nums[i]'s count
        int count = 0;
        int j = mid + 1;
        for (int i = l; i <= mid; i++) {
            while (j <= r && (long) nums[i] > (long)2L * nums[j]) {
                j++;
            } //j > r || nums[i] <= 2 * nums[j]
            count += j - (mid + 1); //add every thing besides nums[j]
        }
        merge(nums, helper, l, mid, mid + 1, r);
        return lcount + rcount + count;
    }
    public void merge(int[] nums, int[] helper, int l1, int r1, int l2, int r2) {
        copyArr(nums, helper, l1, r2);
        int count = 0;
        int sortIdx = l1;
        int rightSize = r2 - l2 + 1;
        while (l1 <= r1 && l2 <= r2) {
            if (helper[l1] > helper[l2]) {
                nums[sortIdx++] = helper[l2++]; 
            } else {
                nums[sortIdx++] = helper[l1++];
            }
        }//post condition: l1 > r1 || l2 > r2
        while (l1 <= r1) {
            nums[sortIdx++] = helper[l1++];
        }
        //l2 > r2 case already sorted
    }
    public void copyArr(int[] src, int[] dest, int l, int r) {
        while (l <= r) {
            dest[l] = src[l];
            l++;
        }
    }
}
```

### 442. Find All Duplicates in an Array ****
Given an array of integers, 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.

Find all the elements that appear twice in this array.

Could you do it without extra space and in O(n) runtime?
```
Example:
Input:
[4,3,2,7,8,2,3,1]

Output:
[2,3]
```
```java
/*
alg: swap each nums[i] to position i - 1, continue until the element in in position or we find a duplicate
    - when find a duplicate, to avoid finding it again, we set the duplicate to -1, special placeholder

time: O(n)
space: O(1)
*/
class Solution {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        /*
        index map:
            for each nums[i], its corresp location index = nums[i] - 1
        */
        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != -1 && nums[i] - 1 != i) {
                //check if duplicate
                if (nums[i] == nums[nums[i] - 1]) {
                    res.add(nums[i]);
                    nums[i] = -1;
                } else {
                    swap(nums, i, nums[i] - 1);
                }
            }
        }
        return res;
    }
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

### 435. Non-overlapping Intervals
Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

```
Example 1:

Input: [[1,2],[2,3],[3,4],[1,3]]
Output: 1
Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
Example 2:

Input: [[1,2],[1,2],[1,2]]
Output: 2
Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
Example 3:

Input: [[1,2],[2,3]]
Output: 0
Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
```
```java
/*
best first search(greedy):
    preprocess sort the interval by j then by i
    linear scan(from left to right) the sorted intervals, and keep a global interval right end, remove excessive intervals one by one
    
time: O(nlogn)
space: O(n)
*/
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        int n = intervals.length;
        if (n == 0) {
            return n;
        }
        Arrays.sort(intervals, (i1, i2) -> {
            return i1[1] - i2[1] == 0 ? i1[0] - i2[0] : i1[1] - i2[1];
        });
        
        int res = 0;
        int r = intervals[0][1];
        
        for (int i = 1; i < intervals.length; i++) {
            int[] it = intervals[i];
            if (it[0] < r) {
                res++;
            } else {
                r = it[1];
            }
        }
        return res;
    }
}
```

### 835. Image Overlap
```java
/*
One important insight is that shifting one matrix to a direction is equivalent to shifting the other matrix to the opposite direction, in the sense that we would have the same overlapping zone at the end.

we iterate all possible # of shifts and count the overlapping region at each step. (we count moving left and up by equivalently shifting B right and down, so index is positive)
time: O(n^4)
space: O(1)
*/
class Solution {
    protected int shiftAndCount(int xShift, int yShift, int[][] M, int[][] R) {
        int count = 0;
        int rRow = 0;
        // count the cells of ones in the overlapping zone.
        for (int mRow = yShift; mRow < M.length; ++mRow) {
            int rCol = 0;
            for (int mCol = xShift; mCol < M.length; ++mCol) {
                if (M[mRow][mCol] == 1 && M[mRow][mCol] == R[rRow][rCol])
                    count += 1;
                rCol += 1;
            }
            rRow += 1;
        }
        return count;
    }

    public int largestOverlap(int[][] A, int[][] B) {
        int maxOverlaps = 0;

        for (int yShift = 0; yShift < A.length; ++yShift)
            for (int xShift = 0; xShift < A.length; ++xShift) {
                // move one of the matrice up and left and vice versa.
                // (equivalent to move the other matrix down and right)
                maxOverlaps = Math.max(maxOverlaps, shiftAndCount(xShift, yShift, A, B));
                maxOverlaps = Math.max(maxOverlaps, shiftAndCount(xShift, yShift, B, A));
            }

        return maxOverlaps;
    }
}
```


### 57. Insert Interval
Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

You may assume that the intervals were initially sorted according to their start times.
```
Example 1:

Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
```

```java
/*
alg: recursion


recursively insert interval i into intervals in index range [cur ... len - 1]

recursive rule:
    get current interval intervals[cur]
    if i.end <= cur_interval.end: merge i and cur as new_interval
    recursively insert the interval in rnage [cur + 1 ... len - 1]

base case:
    if cur == len, simply insert interval at the last
    if i.end < cur_interval.start, insert at position cur
    
time: O(n)
space: O(n)

*/
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<List<Integer>> ls = new ArrayList<>();
        rec(intervals, 0, ls, newInterval);
        
        int s = ls.size();
        int[][] res = new int[s][2];
        for (int i = 0; i < s; i++) {
            List<Integer> in = ls.get(i);
            res[i][0] = in.get(0);
            res[i][1] = in.get(1);
        }
        
        return res;
    }
    
    public void rec(int[][] intervals, int cur, List<List<Integer>> ls, int[] i) {
        if (cur == intervals.length) {
            ls.add(Arrays.asList(i[0], i[1]));
            return;
        } 
        
        int[] curInterval = intervals[cur];
        
        if (i[1] < curInterval[0]) {
            //i and cur are non overlapping, and insert position is at cur
            //insert at pos = cur
            ls.add(Arrays.asList(i[0], i[1]));
            
            //append the rest of the intervals
            for (int k = cur; k < intervals.length; k++) {
                ls.add(Arrays.asList(intervals[k][0], intervals[k][1]));
            }
            return;
        } 
        
        if (i[0] > curInterval[1]) {    //i and cur are non overlapping, add cur to result list
            ls.add(Arrays.asList(curInterval[0], curInterval[1]));
            rec(intervals, cur + 1, ls, i);
        } else {
            //merge interval i and cur
            i[0] = Math.min(i[0], curInterval[0]);
            i[1] = Math.max(i[1], curInterval[1]);
            rec(intervals, cur + 1, ls, i);
        }
    }
}
```


### 1094. Car Pooling

You are driving a vehicle that has capacity empty seats initially available for passengers.  The vehicle only drives east (ie. it cannot turn around and drive west.)

Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains information about the i-th trip: the number of passengers that must be picked up, and the locations to pick them up and drop them off.  The locations are given as the number of kilometers due east from your vehicle's initial location.

Return true if and only if it is possible to pick up and drop off all passengers for all the given trips. 

 
```
Example 1:

Input: trips = [[2,1,5],[3,3,7]], capacity = 4
Output: false
Example 2:

Input: trips = [[2,1,5],[3,3,7]], capacity = 5
Output: true
```

```java
/*

algorithm: best first search
pre sort trips according to the start location
use a min heap sort by end location
for i : trips: 
    if pq.isNotEmpty && pq.top.endLocation <= trip[i].startLocation: poll trips that are done, update the capacity
    add trip[i] to pq, update the capacity
    
    at any iteration, if capacity cannot be updated (say cant fit more ppl), return false
    
time: O(nlogn)
*/

class Solution {
    public boolean carPooling(int[][] trips, int capacity) {
        Arrays.sort(trips, (t1, t2) -> t1[1] - t2[1]);
        PriorityQueue<int[]> pq = new PriorityQueue<>((t1, t2) -> t1[2] - t2[2]);
        
        for (int[] t : trips) {
            while (!pq.isEmpty() && pq.peek()[2] <= t[1]) {
                int[] prev = pq.poll();
                capacity += prev[0];
            }
            
            if (capacity - t[0] < 0) {
                return false;
            }
            capacity -= t[0];
            pq.offer(t);
        }
        return true;
    }
}
```


