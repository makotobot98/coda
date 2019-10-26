/*
reverse: I love yahoo --> output: yahoo love I

transform output:
    ()' denotes the reverse of that word
    yahoo love I = (oohay)' (evol)' (I)' = (I love yahoo)'

algorithm:
    we need to perform two tasks:
        1. remove leading/trailing zero and intermidiate spaces
        2. reverse the words (as described above)

for 1:
    use two pointer to remove the space in place:
        let [0, i) be chars processed (removed space)
            [i, j) spaces
            [j, len) unvisited
        for j < len:
            if s[j] == ' ':
                if i == 0:
                    //heading zero
                    j++
                if i != 0:
                    if s[i - 1] == ' ':
                        //duplicate zero between words
                        j++;
                    if s[i - 1] != ' ':
                        //first space between words
                        arr[i++] = arr[j++]
            if s[j] != ' ':
                arr[i++] = arr[j++]
    above two pointer also removes all traling zero except last one, so we need to postprocessing to delete last space if exist

for 2:
    reverse the whole string --> reverse each word between sapce


time: O(n)
space: O(1)
*/
class Solution {
    public String reverseWords(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }   
        char[] arr = s.toCharArray();
        int end = removeZeros(arr);
        reverse(arr, end);  //reverse arr[0 ... end)
        return new String(arr, 0, end);
    }
    //return the length of processed array, so arr[0 ... output) is the words w/t spaces
    public int removeZeros(char[] arr) {
        int i = 0;
        int j = 0;
        //we can compress the if else mentioned in the pseudo code
        while (j < arr.length) {
            if (arr[j] == ' ' && (i == 0 || arr[i - 1] == ' ')) {
                j++;
            } else {
                arr[i++] = arr[j++];
            }
        }
        //post processing for last char, note processed array are in the interval [0, i)
        if (i > 0 && arr[i - 1] == ' ') {
            i--;
        }
        return i;
    }
    //reverse the whole arr[0 ... end], then reverse each word seperated by ' '
    //algorithm: two pointer i,j
    //      for each round: move j so j + 1 == ' ', then reverse [i ... j]
    //          then reasisgn i = j + 2, j = i
    public void reverse(char[] arr, int end) {
        int i = 0;
        int j = 0;
        while (j < end) {
            while (j + 1 != end && arr[j + 1] != ' ') {
                j++;
            }//post: j + 1 == end || arr[j + 1] == ' '
            reverseHelper(arr, i, j);
            i = j + 2;
            j = i;
        }
        
        //reverse the whole word
        reverseHelper(arr, 0, end - 1);
    }
    //simply reverse [start,end]
    public void reverseHelper(char[] arr, int start, int end) {
        while (start < end) {
            char temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
}