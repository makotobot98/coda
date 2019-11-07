/*
alg: hashmap<word, List<soetedIndexCount>>, each time we requst two words, run binary search for each word in the set to locate the shortest distance. time O(nlogm), space: O(s), s = size of the input (word length * # of words), m,n = length of two words
we can also run two pointer to find the minimum shortest distance. time O(m + n), space: O(s)


*/
class WordDistance {
    Map<String, List<Integer>> map;
    public WordDistance(String[] words) {
        map = new HashMap<>();
        //add each <word, index> into the map
        if (words != null) {
            for (int i = 0; i < words.length; i++) {
                List<Integer> ls = map.getOrDefault(words[i], new ArrayList<Integer>());
                ls.add(i);
                map.put(words[i], ls);
            }
        }
        /* no need to sort since we are adding in ascending order
        //sort each list
        for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
            List<Integer> ls = entry.getValue();
            Collections.sort(ls);
        }
        */
    }
    
    public int shortest(String word1, String word2) {
        List<Integer> l1 = map.get(word1);
        List<Integer> l2 = map.get(word2);
        if (l1 == null || l2 == null) {
            return -1;
        }
        
        int i = 0;  //start of l1
        int j = 0;  //start of l2
        int dist = Integer.MAX_VALUE;
        while (i < l1.size() && j < l2.size()) {
            int index1 = l1.get(i);
            int index2 = l2.get(j);
            if (index1 == index2) {
                //if requested same word
                return 0;
            } else if (index1 < index2) {
                //increment i since l2[j + 1] is furthur
                dist = Math.min(dist, index2 - index1);
                i++;
            } else {
                //increment j
                dist = Math.min(dist, index1 - index2);
                j++;
            }
        }
        return dist;
    }
}

/**
 * Your WordDistance object will be instantiated and called as such:
 * WordDistance obj = new WordDistance(words);
 * int param_1 = obj.shortest(word1,word2);
 */