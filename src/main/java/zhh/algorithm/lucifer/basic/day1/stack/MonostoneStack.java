package zhh.algorithm.lucifer.basic.day1.stack;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;

import java.util.*;

/**
 * @author zhanghao
 * @date 2021-05-02 07:06
 * @desc 单调栈：单调栈要求栈中的元素是单调递减或者单调递减的。
 * 使用场景：单调栈适合的题目是求解下一个大于 xxx或者下一个小于 xxx这种题目。
 * 算法过程：如果压栈之后仍然可以保持单调性，那么直接压。否则先弹出栈的元素，直到压入之后可以保持单调性。
 * 算法原理：被弹出的元素都是大于当前元素的，并且由于栈是单调减/增的，因此在其之后小于/大于其本身的最近的就是当前元素了。
 * https://lucifer.ren/blog/2020/11/03/monotone-stack/
 */
public class MonostoneStack {
    public List<Integer> monostoneStack(int[] arr) {
        Stack<Integer> stack = new Stack<>();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            result.add(-1);
        }

        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
//            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                Integer popIndex = stack.pop();
                result.set(popIndex, i);
            }
            stack.push(i);
        }

        return result;
    }

    /**
     * 42. 接雨水
     * https://leetcode-cn.com/problems/trapping-rain-water/
     * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     *
     * 输入：height = [4,2,0,3,2,5]
     * 输出：9
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param height
     * @return
     */
    public int trapRainWater(int[] height) {
        // 数组下标单调递减栈
        Stack<Integer> indexStack = new Stack<>();
        int ans = 0, current = 0;

        while (current < height.length) {
            while (!indexStack.isEmpty() && height[current] > height[indexStack.peek()]) {
                Integer popIndex = indexStack.pop();
                if (indexStack.isEmpty()) {
                    break;
                }
                Integer peekIndex = indexStack.peek();
                int distance = current - peekIndex - 1;
                int boundedHeight = (Math.min(height[current], height[peekIndex]) - height[popIndex]) * distance;
                ans += boundedHeight;
            }
            indexStack.push(current++);
        }

        return ans;
    }

    /**
     * 84. 柱状图中最大的矩形
     * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/
     * 示例:
     * 输入: [2,1,5,6,2,3]
     * 输出: 10
     *
     * 空间复杂度：O(n)
     * 时间复杂度：O(n)
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        if (heights.length == 0 || heights.length == 1) {
            return heights.length == 0 ? 0 : heights[0];
        }

        // 哨兵技巧 -> 解决边界问题
        int[] heightsWithSentinel = new int[heights.length + 2];
        System.arraycopy(heights, 0, heightsWithSentinel, 1, heights.length);

        Deque<Integer> stack = new ArrayDeque<>();
        int current = 1, area = 0;
        stack.addLast(0);
        while (current < heightsWithSentinel.length) {
            // 栈中弹出的元素 都是高度大于当前柱子高度的柱子
            while (heightsWithSentinel[current] < heightsWithSentinel[stack.peekLast()]) {
                int height = heightsWithSentinel[stack.removeLast()];
                int width = current - stack.peekLast() - 1;
                // 计算的面积为弹出的柱子高度 * 【弹出柱子的下标,当前柱子的下标)长度
                area = Math.max(area, height * width);
            }
            stack.addLast(current++);
        }
        return area;
    }

    /**
     * 739. Daily Temperatures
     * Given a list of daily temperatures T, return a list such that, for each day in the input, tells you how many days you would have to wait until a warmer temperature. If there is no future day for which this is possible, put 0 instead.
     * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73], your output should be [1, 1, 4, 2, 1, 1, 0, 0].
     * Note: The length of temperatures will be in the range [1, 30000]. Each temperature will be an integer in the range [30, 100].
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[temperatures.length];
        int current = 0;
        while (current < temperatures.length) {
            while (!stack.isEmpty() && temperatures[current] > temperatures[stack.peekLast()]) {
                Integer index = stack.removeLast();
                result[index] = current - index;
            }
            stack.addLast(current++);
        }

        return result;
    }

    public static void main(String[] args) {
        MonostoneStack monostoneStack = new MonostoneStack();

        int[] input = {1,3,4,5,2,9,6};
        List<Integer> list = monostoneStack.monostoneStack(input);
        System.out.println(JSONObject.toJSONString(list));

        int[] trapInput = {0,1,0,2,1,0,1,3,2,1,2,1};
        Assert.assertEquals(6,monostoneStack.trapRainWater(trapInput));

        int[] largestRectangleInput = {2,1,5,6,2,3};
        Assert.assertEquals(10, monostoneStack.largestRectangleArea(largestRectangleInput));

        int[] dailyTemperatureInput = {73, 74, 75, 71, 69, 72, 76, 73};
        int[] dailyTemperatureOutput = {1, 1, 4, 2, 1, 1, 0, 0};
        Assert.assertArrayEquals(dailyTemperatureOutput, monostoneStack.dailyTemperatures(dailyTemperatureInput));
    }
}
