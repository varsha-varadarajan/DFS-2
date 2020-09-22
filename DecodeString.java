import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/* https://leetcode.com/problems/decode-string
394. Decode String - MEDIUM
*/
class DecodeString {
    /* Approach: use a single queue for adding the entire string. 
    Receursively traverse the string by removing characters from the queue
    Start of paranthesis '[' indicates a smaller problem, and the method is called recursively.
    The return value of recursive call is appended 'num' number of times to the result string;
    A ']' indicates the end of this subproblem, and the result is returned to the parent caller.

    SC: O(n) => for storing n characters in queue + 
         < O(n/2) => for recursion stack
    TC: O(n)
    */
    public String decodeStringRecursive(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        
        Queue<Character> q = new LinkedList<>();
        for (char c: s.toCharArray()) {
            q.offer(c);
        }
        return helper(q);        
    }
    
    private String helper(Queue<Character> q) {
        StringBuffer sb = new StringBuffer();
        int num = 0;
        while (!q.isEmpty()) {
            char c = q.remove();            
            if(c == '[') {
                String sub = helper(q);
                for (int i = 0; i < num; i++) {
                    sb.append(sub);
                }
                num = 0;
            } else if (c == ']') {
                break;
            } else if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    class Value {
        String s = null;
        int digit;
        
        Value(String s, int digit) {
            this.s = s;
            this.digit = digit;
        }
    }
    
    /*
    As we see, a '[', save the number that has occured before it, and the current value of the computed string.
    The current value of the string is pushed onto the stack, since if we pop this value after seeing a ']',
    we will append the new computed string to this old string to form the result.

    TC: O(n) => string length
    SC: O(l) entries, where l => no of [ brackets, every entry having 1 nunber and upto m length string
        m => length of decoded string
    */
    public String decodeStringIterative(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        
        Stack<Value> st = new Stack<>();
        String result = "";
        
        int i = 0;
        int num = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            
            if (Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if (c == '[') {
                st.push(new Value(result, num));
                result = "";
                num = 0;
            } else if (c == ']') {
                Value v = st.pop();
                StringBuffer lastString = new StringBuffer(v.s);
                int digit = v.digit;
                for (int len = 0; len < digit; len++) {
                    lastString.append(result);
                }
                result = lastString.toString();
            } else {
                result += c;
            }
            i++;
        }
        
        return result;
    }
}