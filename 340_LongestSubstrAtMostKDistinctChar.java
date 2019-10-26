/*
algorithm: sliding window with a count map, as we slide the window from left to right, 
maintian a hashmap<Chracter, remainingCount>, when map.size > k, we delete from windowLeft 
until we reach a valid size

above approach is a greedy approach as we are maintaing a maximum window starting from each
index as we slide the window through input string. maintain a global max to track the maximum size of the window.

detail:
    1. initialize an empty count map, [windowLeft, windowRight] define the dimension of window
    2. while windowRight < len:
            //add windowRight to the window
            //if map.size() > k, delete windowLeft until valid
            //update the globalmax

time: O(n)
space: O(k)
*/
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> countMap = new HashMap<>();
        int windowLeft = 0;
        int globalMax = 0;
        for (int windowRight = 0; windowRight < s.length(); windowRight++) {
            //add windowRight
            char right = s.charAt(windowRight);
            countMap.put(right, countMap.getOrDefault(right, 0) + 1);
            //delete windowLeft if oversize
            while (countMap.size() > k) {
                char left = s.charAt(windowLeft++);
                int count = countMap.get(left);
                if (count == 1) { //if we are deleting last one, remove it from map
                    countMap.remove(left);
                } else {//not last one
                    countMap.put(left, count - 1);
                }
            }
            //update max
            globalMax = Math.max(globalMax, windowRight - windowLeft + 1);
        }
        return globalMax;
    }
}