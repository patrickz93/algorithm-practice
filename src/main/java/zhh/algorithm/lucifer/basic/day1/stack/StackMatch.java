package zhh.algorithm.lucifer.basic.day1.stack;


import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author zhanghao
 * @date 2021-05-01 09:46
 * @desc 栈匹配：比较类似栈结构的匹配
 */
public class StackMatch {

    private int index = 0;

    /**
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     *
     * 示例 1：
     * 输入：s = "3[a]2[bc]"
     * 输出："aaabcbc"
     *
     *  示例 2：
     * 输入：s = "3[a2[c]]"
     * 输出："accaccacc"
     *
     *  示例 3：
     * 输入：s = "2[abc]3[cd]ef"
     * 输出："abcabccdcdcdef"
     *
     * 示例 4：
     * 输入：s = "abc3[cd]xyz"
     * 输出："abccdcdcdxyz"
     *
     *  栈操作
     * 本题中可能出现括号嵌套的情况，比如 2[a2[bc]]，这种情况下我们可以先转化成 2[abcbc]，在转化成 abcbcabcbc。我们可以把字母、数字和括号看成是独立的 TOKEN，并用栈来维护这些 TOKEN。具体的做法是，遍历这个栈：
     *
     * 1.如果当前的字符为数位，解析出一个数字（连续的多个数位）并进栈
     * 2.如果当前的字符为字母或者左括号，直接进栈
     * 3.如果当前的字符为右括号，开始出栈，一直到左括号出栈，出栈序列反转后拼接成一个字符串，此时取出栈顶的数字（此时栈顶一定是数字，想想为什么？），就是这个字符串应该出现的次数，我们根据这个次数和字符串构造出新的字符串并进栈
     * 重复如上操作，最终将栈中的元素按照从栈底到栈顶的顺序拼接起来，就得到了答案。注意：这里可以用不定长数组来模拟栈操作，方便从栈底向栈顶遍历。
     *
     * @param s
     * @return
     */
    public String decodeString (String s) {
        Stack<String> stack = new Stack<>();
        while (index < s.length()) {
            char cur = s.charAt(index);
            // 数字，需要处理多位数字的情况
            if (Character.isDigit(cur)) {
                String digit = getDigit(s);
                stack.add(digit);
            } else if (Character.isLetter(cur) || '[' == cur) {
                // 字母 或者 左括号
                stack.add(String.valueOf(cur));
                index++;
            } else {
                // 右括号
                index++;
                Stack<String> subStack = new Stack<>();
                while (!"[".equals(stack.peek())) {
                    String pop = stack.pop();
                    subStack.add(pop);
                }
                // "["
                String leftSquareCurse = stack.pop();
                int number = Integer.parseInt(stack.pop());

                StringBuilder stringBuilder = new StringBuilder();
                while (!subStack.isEmpty()) {
                    stringBuilder.append(subStack.pop());
                }
                // 出栈，顺序需要反转
                stringBuilder.reverse();

                for (int i = 0; i < number; i++ ){
                    stack.add(stringBuilder.toString());
                }
            }
        }

        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        // 出栈，顺序需要反转
        return result.reverse().toString();
    }

    private String getDigit(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        while (Character.isDigit(s.charAt(index))) {
            stringBuilder.append(s.charAt(index));
            index++;
        }

        return stringBuilder.toString();
    }

    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     *
     * 示例 1：
     * 输入：s = "()"
     * 输出：true
     *
     *  示例 2：
     * 输入：s = "()[]{}"
     * 输出：true
     * 
     * 示例 3：
     * 输入：s = "(]"
     * 输出：false
     *
     *  示例 4：
     * 输入：s = "([)]"
     * 输出：false
     *
     *  示例 5：
     * 输入：s = "{[]}"
     * 输出：true
     *
     * 提示：
     * 1 <= s.length <= 104
     * s 仅由括号 '()[]{}' 组成
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put('[', ']');
        map.put('(',')');
        map.put('{', '}');
        
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                Character leftParentheses = stack.pop();
                Character rightParentheses = map.get(leftParentheses);
                if (i == s.length()-1) {
                    return rightParentheses.equals(c) && stack.isEmpty();
                } else if (!rightParentheses.equals(c)) {
                    return false;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        StackMatch stackMatch = new StackMatch();
        String a = "3[a]2[bc]";
        Assert.assertEquals("aaabcbc", stackMatch.decodeString(a));

        String b = "3[a2[c]]";
        stackMatch.index = 0;
        Assert.assertEquals("accaccacc", stackMatch.decodeString(b));

        String c = "2[abc]3[cd]ef";
        stackMatch.index = 0;
        Assert.assertEquals("abcabccdcdcdef", stackMatch.decodeString(c));

        String d = "abc3[cd]xyz";
        stackMatch.index = 0;
        Assert.assertEquals("abccdcdcdxyz", stackMatch.decodeString(d));

        String isValid = "()))";
        Assert.assertFalse(isValid, stackMatch.isValid(isValid));

        String isValid1 = "([)]";
        Assert.assertFalse(isValid1, stackMatch.isValid(isValid1));
    }
}
