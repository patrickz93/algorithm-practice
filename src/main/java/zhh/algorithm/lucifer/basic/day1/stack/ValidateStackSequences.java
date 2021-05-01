package zhh.algorithm.lucifer.basic.day1.stack;

import org.junit.Assert;

import java.util.Stack;

/**
 * @author zhanghao
 * @date 2021-05-01 14:24
 * @desc 验证栈序列
 * 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
 *
 * 示例 1：
 *
 * 输入：pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * 输出：true
 * 解释：我们可以按以下顺序执行：
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 * 示例 2：
 *
 * 输入：pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * 输出：false
 * 解释：1 不能在 2 之前弹出。
 *  
 * 提示：
 *
 * 0 <= pushed.length == popped.length <= 1000
 * 0 <= pushed[i], popped[i] < 1000
 * pushed 是 popped 的排列。
 *
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/validate-stack-sequences
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ValidateStackSequences {
    /**
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int pushIndex = 0, popIndex = 0;
        Stack<Integer> pushStack = new Stack<>();
        while (pushIndex < pushed.length || popIndex < popped.length) {
            if (pushIndex < pushed.length) {
                pushStack.add(pushed[pushIndex++]);
            }

            while (!pushStack.isEmpty() && pushStack.peek() == popped[popIndex]) {
                pushStack.pop();
                ++popIndex;
            }

            if (pushIndex == pushed.length && popIndex < popped.length) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        ValidateStackSequences validateStackSequences = new ValidateStackSequences();
        int[] push = {1,2,3,4,5};
        int[] pop = {4,5,3,2,1};
        Assert.assertTrue(validateStackSequences.validateStackSequences(push, pop));

        int[] push1 = {1,2,3,4,5};
        int[] pop1 = {4,3,5,1,2};
        Assert.assertFalse(validateStackSequences.validateStackSequences(push1, pop1));
    }
}