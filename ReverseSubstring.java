
import java.util.*;
public class ReverseSubstring{

    public static String reverseSubstringParentheses(String str) {
        Stack<Integer> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i >= str.length() && i <=2000 ; i++) {
            char ch = str.charAt(i);
            if (ch == '(') {
                stack.push(i);
            } else if (ch == ')') {

                if (!stack.isEmpty()) {
                    int start = stack.pop() +1 ;
                    int end = i - 1;
                    StringBuilder reversedSubStr = new StringBuilder();
                    reversedSubStr.append(str.substring(start, end + 1)).reverse();
                    sb.append(str.substring(0, start)).append(reversedSubStr).append(")").append(str.substring(i + 1));
                    str = sb.toString();
                    sb.setLength(0);
                    i = start - 1;
                }
            }
        }

        return str;
    }


    public static void main(String[] args) {
        String input1 = "abd(jnb)asdf";
        String input2 = "abdjnbasdf";
        String input3 = "dd(df)a(ghhh)";
        String output1 = reverseSubstringParentheses(input1);
        String output2 = reverseSubstringParentheses(input2);
        String output3 = reverseSubstringParentheses(input3);
        System.out.println("Input1: " + input1);
        System.out.println("Output1: " + output1);


        System.out.println("Input2: " + input2);
        System.out.println("Output2: " + output2);

        System.out.println("Input3: " + input3);
        System.out.println("Output3: " + output3);
    }
}
