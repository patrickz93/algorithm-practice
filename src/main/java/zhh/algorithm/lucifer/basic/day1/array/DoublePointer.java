package zhh.algorithm.lucifer.basic.day1.array;

import org.junit.Assert;

/**
 * @author zhanghao
 * @date 2021-05-03 09:00
 * @desc 双指针
 */
public class DoublePointer {
    /**
     * 42. 接雨水
     * https://leetcode-cn.com/problems/trapping-rain-water/
     * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     *
     * 输入：height = [4,2,0,3,2,5]
     * 输出：9
     *
     * 只关心左右两侧较小的那一个，并不需要两者都计算出来。具体来说：
     * 如果 l[i + 1] < r[i] 那么 最终积水的高度由 i 的左侧最大值决定。
     * 如果 l[i + 1] >= r[i] 那么 最终积水的高度由 i 的右侧最大值决定。
     * 因此我们不必维护完整的两个数组，而是可以只进行一次遍历，同时维护左侧最大值和右侧最大值，使用常数变量完成即可
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     *
     * @param height
     * @return
     */
    public int trapRainWater(int[] height) {
        int leftIndex = 0, rightIndex = height.length - 1;
        int leftMaxHeight = 0, rightMaxHeight = 0;
        int ans = 0;
        while (leftIndex < rightIndex) {
            if (height[leftIndex] < height[rightIndex]) {
                leftMaxHeight = Math.max(leftMaxHeight, height[leftIndex]);
                ans += (leftMaxHeight - height[leftIndex]);
                leftIndex++;
            } else {
                rightMaxHeight = Math.max(rightMaxHeight, height[rightIndex]);
                ans += (rightMaxHeight - height[rightIndex]);
                rightIndex--;
            }
        }
        return ans;
    }

    /**
     * 11. 盛最多水的容器
     * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * 说明：你不能倾斜容器。
     *
     * 示例 1：
     *
     * 输入：[1,8,6,2,5,4,8,3,7]
     * 输出：49
     * 解释：图中垂直线代表输入数组 [1,8,6,2,5,4,8,3,7]。在此情况下，容器能够容纳水（表示为蓝色部分）的最大值为 49。
     *
     *  示例 2：
     * 输入：height = [1,1]
     * 输出：1
     *
     * 示例 3：
     * 输入：height = [4,3,2,1,4]
     * 输出：16
     *
     *  示例 4：
     * 输入：height = [1,2,1]
     * 输出：2
     *
     * 提示：
     * n = height.length
     * 2 <= n <= 3 * 104
     * 0 <= height[i] <= 3 * 104
     *
     * 链接：https://leetcode-cn.com/problems/container-with-most-water
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int left = 0, right = height.length-1;
        int maxArea = 0;
        while (right > left) {
            maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));
            if (height[left] > height[right]) {
                --right;
            } else {
                ++left;
            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        DoublePointer doublePointer = new DoublePointer();
        int[] trapInput = {0,1,0,2,1,0,1,3,2,1,2,1};
        Assert.assertEquals(6,doublePointer.trapRainWater(trapInput));

        int[] maxAreaInput1 = {1,8,6,2,5,4,8,3,7};
        Assert.assertEquals(49, doublePointer.maxArea(maxAreaInput1));
        int[] maxAreaInput2 = {1,1};
        Assert.assertEquals(1, doublePointer.maxArea(maxAreaInput2));
        int[] maxAreaInput3 = {4,3,2,1,4};
        Assert.assertEquals(16, doublePointer.maxArea(maxAreaInput3));
        int[] maxAreaInput4 = {1,2,1};
        Assert.assertEquals(2, doublePointer.maxArea(maxAreaInput4));
    }
}
