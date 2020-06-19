### 275. H-Index II *
Given an array of citations sorted in `ascending order` (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at `least h` citations each, and the other N − h papers have `no more than` h citations each."

> Example:
>
> Input: citations = `[0,1,3,5,6]`
>
> Output: 3 
>
> Explanation: `[0,1,3,5,6]` means the researcher has 5 papers in total and each of them had 
             received 0, 1, 3, 5, 6 citations respectively. 
             Since the researcher has 3 papers with **at least 3** citations each and the remaining 
             two with **no more than 3** citations each, her h-index is 3.

```java
class Solution {
  public int hIndex(int[] citations) {
    int idx = 0, n = citations.length;
    int pivot, left = 0, right = n - 1;
    while (left <= right) {
      pivot = left + (right - left) / 2;
      if (citations[pivot] == n - pivot) return n - pivot;
      else if (citations[pivot] < n - pivot) left = pivot + 1;
      else right = pivot - 1;
    }
    return n - left;
  }
}

```

### L. Search In Bitonic Array ***
Search for a target number in a bitonic array, return the index of the target number if found in the array, or return -1.

A bitonic array is a combination of two sequence: the first sequence is a monotonically increasing one and the second sequence is a monotonically decreasing one.

> Examples:
>
> array = {1, 4, 7, 11, 6, 2, -3, -8}, target = 2, return 5.

```java
/*
if target == arr[mid]: return mid
arr[mid] >= arr[mid - 1]:
    if (target < arr[mid] && target >= arr[l]): r = mid - 1
    else: l = mid + 1
else:
    if (mid == 0 || target < arr[mid] && target >= arr[r]): l = mid + 1
    else: r = mid - 1
  
*/
public class Solution {
  public int search(int[] arr, int target) {
    int l = 0; int r = arr.length - 1;
    while (l <= r) {
      int mid = l + (r - l) / 2;
      if (arr[mid] == target) {
        return mid;
      } else if (mid > 0 && arr[mid] >= arr[mid - 1]) {
        if (target < arr[mid] && target >= arr[l]) r = mid - 1;
        else l = mid + 1;
      } else {
        if (mid == 0 || target < arr[mid] && target >= arr[r]) l = mid + 1;
        else r = mid - 1;
      }
    }
    return -1;
  }
}
```