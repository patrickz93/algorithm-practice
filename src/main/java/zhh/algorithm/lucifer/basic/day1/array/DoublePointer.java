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

    public static void main(String[] args) {
        DoublePointer doublePointer = new DoublePointer();
        int[] trapInput = {0,1,0,2,1,0,1,3,2,1,2,1};
        Assert.assertEquals(6,doublePointer.trapRainWater(trapInput));
    }
}
