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