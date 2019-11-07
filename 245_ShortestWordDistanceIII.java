/*
similar to 243 shortest Word Distance I, we just have to seperate the case that if w1 and w2 are the same words
time: O(n)
space: O(1)
*/
class Solution {
    public int shortestWordDistance(String[] words, String word1, String word2) {
        return word1.equals(word2)? shortest1(words, word1) : shortest2(words, word1, word2);
    }
    public int shortest1(String[] words, String w) {
        int prev = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(w)) {
                if (prev != -1) {
                    min = Math.min(min, i - prev);
                }
                prev = i;
            }
        }
        return min;
    }
    public int shortest2(String[] words, String w1, String w2) {
        int i1 = -1;
        int i2 = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(w1)) {
                i1 = i;
            } else if (words[i].equals(w2)) {
                i2 = i;
            }
            
            if (i1 != -1 && i2 != -1) {
                min = Math.min(min, Math.abs(i1 - i2));
            }
        }
        return min;
    }
}