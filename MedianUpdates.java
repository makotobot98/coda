import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

class Solution{
    /* Head ends here*/
    //assumption: treat x as non stream 
    static void median(String a[],int x[]) {
        
       
    }
    
    /* Tail starts here*/
    
   public static void main( String args[] ){
      

      Scanner in = new Scanner(System.in);
      
      int N;
      N = in.nextInt();
   
      String s[] = new String[N];
      int x[] = new int[N];
      
      for(int i=0; i<N; i++){
         s[i] = in.next();
         x[i] = in.nextInt();
      }
   
      median(s,x);
    }
}