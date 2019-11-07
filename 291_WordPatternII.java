/*
algorithm: dfs

each time select a word from str, and add it to the Map<Character, wordStr>, there are n - curIdx ways to select such word where n = len of str. we can make at most m selections where m = pattern.len

time: O(P(n, m))
space: O(n)

                    redblueredblue, pattern = "abab" we have in total 4 selection
            /           |       |       |    
s1:         r           re      red     redb ...
        /   |    \
s2:   r-e   r-ed r-edb...

        
data structure:
    Map<Character, wordStr> with backtrack
    Set<wordStr> to track which word has been assigned a pattern
    
*/
class Solution {
    public boolean wordPatternMatch(String pattern, String str) {
        if (pattern == null || str == null) {
            return true;
        } 
        Map<Character, String> map = new HashMap<>();
        Set<String> used = new HashSet<>();
        return dfs(pattern, str, 0, 0, map, used);
    }
    public boolean dfs(String pattern, String s, int p_cur, int s_cur, Map<Character, String> map, Set<String> used)
    {
        if (p_cur == pattern.length() && s_cur == s.length()) {
            return true;
        } else if (p_cur == pattern.length() || s_cur == s.length()) {
            return false;
        }
        
        char p = pattern.charAt(p_cur);
        //check if pattern[p_cur] is already picked
        String match = map.get(p);
        if (match != null) {
            //if not enough len for match in s
            if (s_cur + match.length() - 1 >= s.length()) { 
                return false;
            }
            String s_match = s.substring(s_cur, s_cur + match.length());
            if (!match.equals(s_match)) {
                return false;
            }
            return dfs(pattern, s, p_cur + 1, s_cur + match.length(), map, used);
        }
        //pattern[p_cur] is not picked yet
        //pick a valid word from s[s_cur ... used] assigning to p_cur
        for (int i = s_cur; i < s.length(); i++) {
            String word = s.substring(s_cur, i + 1);
            if (used.contains(word)) {
                //if already used this word for other pattern
                continue;
            }
            
            map.put(p, word);
            used.add(word);
            if (dfs(pattern, s, p_cur + 1, s_cur + word.length(), map, used)) {
                return true;
            }
            //backtrack
            map.remove(p);
            used.remove(word);
        }
        return false;
    }
}