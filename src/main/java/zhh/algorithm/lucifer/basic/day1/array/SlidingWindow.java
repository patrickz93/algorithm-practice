package zhh.algorithm.lucifer.basic.day1.array;

import org.junit.Assert;

/**
 * @author zhanghao
 * @date 2021-05-08 06:47
 * @desc 滑动窗口——通常用来解决一些连续问题
 * 比如题目求解“连续子串 xxxx”，“连续子数组 xxxx”
 * 从类型上说主要有：
 *  1.固定窗口大小
 *  2.可变窗口
 *      2.1 窗口大小不固定，求解最大的满足条件的窗口
 *      2.2 窗口大小不固定，求解最小的满足条件的窗口
 * https://github.com/azl397985856/leetcode/blob/master/thinkings/slide-window.md
 *
 * 代码模板——伪代码
 *  初始化慢指针 = 0
 *  初始化 ans
 *  for 快指针 in 可迭代集合
 *    更新窗口内信息
 *    while 窗口内不符合题意
 *       扩展或者收缩窗口
 *       慢指针移动
 *    更新答案
 *  返回 ans
 */
public class SlidingWindow {
    /**
     * 209. 长度最小的子数组
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     *
     * 示例 1：
     * 输入：target = 7, nums = [2,3,1,2,4,3]
     * 输出：2
     * 解释：子数组 [4,3] 是该条件下的长度最小的子数组。
     *
     *  示例 2：
     * 输入：target = 4, nums = [1,4,4]
     * 输出：1
     *
     *  示例 3：
     * 输入：target = 11, nums = [1,1,1,1,1,1,1,1]
     * 输出：0
     *
     *  提示：
     * 1 <= target <= 109
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 105
     *
     *  @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        // 时间复杂度：O(n)
        // 空间复杂度：O(1)
        int minSubArrayLen = Integer.MAX_VALUE;
        int windowLeftIndex = 0, windowRightIndex = 0;
        int sum = 0;

        while (windowRightIndex < nums.length) {
            sum += nums[windowRightIndex];
            while (sum >= target) {
                minSubArrayLen = Math.min(minSubArrayLen, windowRightIndex-windowLeftIndex+1);
                sum -= nums[windowLeftIndex];
                windowLeftIndex++;
            }

            windowRightIndex++;
        }

        return minSubArrayLen == Integer.MAX_VALUE ? 0 : minSubArrayLen;
    }

    public static void main(String[] args) {
        SlidingWindow slidingWindow = new SlidingWindow();

        int[] minSubArrayLenInput1 = {2,3,1,2,4,3};
        Assert.assertEquals(2, slidingWindow.minSubArrayLen(7, minSubArrayLenInput1));
    }
}
