/*
alg: sliding window of size s1, slide in s2, with a countmap.

detail:
    define window dimension be [i, j), use a hashset to look up if each char in s2 is in s1
    initialize countmap with counts of chars in s1
    for j < len:
        if window size is < s1.size():
            add s2[j] into window:
                if s2[j] in countmap, countmap[s2[j]]--, if countmap[s2[j]] == 0, we have a total match of char s2[j], matchCount++
                if s2[j] not in countmap, do noting
            j++
        if window size == s1.size():
            //need to remove window leftmost, s1[i]
            add s2[j] into window:
                as above
            remove s1[i] from the window:
                if s2[i] in count map, countmap[s2[i]]++, if countmap[s2[i]] == 0, we have lost a total match of char s2[i], thus matchCount--
                else do nothing
        if window size == s1.size() and matchCount == countmap size:
            //current window is valid substring of permutation of s1
            return true

let m = len of s1, n = len of s2
time: O(n + m)
space: O(m)

we can optimize space usage using a int[26] countMap
*/
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        Map<Character, Integer> countMap = new HashMap<>();
        getCountMap(s1, countMap);
        int matchCount = 0;
        int i = 0;
        for (int j = 0; j < s2.length(); j++) {
            //add s2[j] into window
            char c = s2.charAt(j);
            Integer count = countMap.get(c);
            //if count of s2[j] is in the map
            if (count != null) {
                if (count == 1) {
                    matchCount++;
                }
                countMap.put(c, count - 1);
            }
            
            //remove s2[i] from window if window is big enough
            if (j - i + 1 > s1.length()) {
                c = s2.charAt(i++);
                count = countMap.get(c);
                if (count != null) {
                    if (count == 0) {
                        matchCount--;
                    }
                    countMap.put(c, count + 1);
                }
            }
            
            if (j - i + 1 == s1.length() && matchCount == countMap.size()) {
                return true;
            }
            
        }
        return false;
    }
    public void getCountMap(String s, Map<Character, Integer> map) {
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
    }
}