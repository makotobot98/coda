/*
alg: dfs and backtrack
each time select a char, we can either abbreviate or not abbreviate
given word of length n, each level we select a letter, there are n levels
  each node has two branches, meaning we abbreviate or not abbreviate that word

time: O(2^n)
space: O(n)
*/

public class Solution {
  public List<String> generateAbbreviations(String word) {
    List<String> res = new ArrayList<>();
    if (word == null) {
      return res;
    }
    dfs(word, 0, 0, new StringBuilder(), res);
    return res;
  }
  public void dfs(String word, int cur, int count, StringBuilder prefix, List<String> res) {
    if (cur == word.length()) {
      res.add(prefix.toString());
      return;
    }

    //not abbreviate
    prefix.append(word.charAt(cur));
    dfs(word, cur + 1, 0, prefix, res);
    prefix.deleteCharAt(prefix.length() - 1);

    //abbreviate
    //need to check the previous letter, if it's a num, append by 1, if not a num, append 1
    //if prev is a num
    if (prefix.length() > 0 && Character.isDigit(prefix.charAt(prefix.length() - 1))) {
      int num = count;
      while (num > 0) {
        prefix.deleteCharAt(prefix.length() - 1);
        num /= 10;
      }
      prefix.append(String.valueOf(count + 1));
      dfs(word, cur + 1, count + 1, prefix, res);
      //backtrack
      num = count + 1;
      while (num > 0) {
        prefix.deleteCharAt(prefix.length() - 1);
        num /= 10;
      }
      prefix.append(String.valueOf(count));
    } else {
      //if prev is not a num
      prefix.append('1');
      dfs(word, cur + 1, 1, prefix, res);
      prefix.deleteCharAt(prefix.length() - 1);
    }
    
  }
}
