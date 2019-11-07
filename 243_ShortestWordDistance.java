/*
alg: sliding window. we can maintain a window containing both word1 and word2, and try to minimize the window after we found
a valid window, each word enters and exit the window exactly once, time : O(s), space: O(s)
*/
public class Solution {
  public int shortestDistance(String[] words, String word1, String word2) {
    int i = 0;
    int j = 0;
    int c1 = 0; //count of occ of word1
    int c2 = 0; //count of occ of word2
    int min = Integer.MAX_VALUE;
    int[] arr = new int[words.length];
    for (int k = 0; k < words.length; k++) {
      if (words[k].equals(word1)) {
        arr[k] = 1;
      } else if (words[k].equals(word2)) {
        arr[k] = 2;
      }
    }
    //arr[i] = 1 if arr[i] = word1, arr[i] == 2 if arr[i] = word2
    while (j < arr.length) {
      while (j < arr.length && (c1 == 0 || c2 == 0)) {
        if (arr[j] == 1) {
          c1++;
        } else if (arr[j] == 2) {
          c2++;
        }
        j++;
      } //post: j == arr.length || (c1 != 0 && c2 != 0)

      //shrink the window
      while (c1 != 0 && c2 != 0) {
        //System.out.println("i: " + i + ",j: " + j);
        min = Math.min(min, j - i - 1);
        if (arr[i] == 1) {
          c1--;
        } else if (arr[i] == 2) {
          c2--;
        }
        i++;
      } //c1 == 0 || c2 == 0
    }
    return min;
  }
}