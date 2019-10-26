/*
assumption: input arr is alwasy big enough to accomandate the compression, and when occurance == 1, we dont append 1
alg: two pointer

define interval: 
    [0 ... i) chars handled
    [i ... j) not needed chars
    [j ... len - 1] unvisited

    while j < len:
        set a j_next to j + 1, move j_next while arr[j_next] == arr[j]
        if j_next - j > 1:
            //append the letter + occurance to arr[i]
            //increment j = j_next
            //increment i # appended
time: O(n)
space: O(1) in place
*/
class Solution {
    public int compress(char[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int i = 0;
        int j = 0;
        while (j < arr.length) {
            int j_next = j + 1;
            while (j_next < arr.length && arr[j_next] == arr[j]) {
                j_next++;
            }//post: j == arr.length || arr[j_next] != arr[j]
            
            //copy the letter
            arr[i++] = arr[j];
            
            //append the occurance
            if (j_next - j > 1) {
                //appen to arr[i]
                int occ = j_next - j;
                String occStr = String.valueOf(occ);
                for (int k = 0; k < occStr.length(); k++) {
                    arr[i++] = occStr.charAt(k);
                }
            }
            //increment j
            j = j_next;
        }
        return i;
    }
}