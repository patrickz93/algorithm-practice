package zhh.algorithm.lucifer.basic.day1.array;

import org.junit.Assert;

/**
 * @author zhanghao
 * @date 2021-05-01 15:12
 * @desc 前缀和——解决数组区间之和的问题
 * 前缀和数组的定义中使用了左闭右开区间[)。这种表示方法的优点之一是很容易做区间的减法。
 * 题目与「子数组求和」有关 => 前缀和
 */
public class PreSum {
    /**
     * 724. 寻找数组的中心下标
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     * 如果数组不存在中心下标，返回 -1 。如果数组有多个中心下标，应该返回最靠近左边的那一个。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-pivot-index
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 时间复杂度：O(n)
     * 空间复杂度：O(n)
     * @param nums
     * @return
     */
    public int pivotIndex(int[] nums) {
        int sum = 0;
        int [] preSum = new int[nums.length+1];
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            preSum[i+1] = preSum[i] + nums[i];
        }

        for (int i = 0; i < nums.length; i++) {
            int leftSum = preSum[i];
            int rightSum = sum - preSum[i] - nums[i];
            if (leftSum == rightSum) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 设枢纽元素x左侧的元素之和为A，右侧的元素之和为B。x为枢纽元素需要A=B。
     * 这道题关注的是x左右两侧的「元素之和」，因此可以考虑用前缀和的技巧来求解。
     * 我们发现，x左侧的元素之和A就已经满足前缀和的定义，那么我们以A为核心思考解题方法。
     * x右侧的元素之和B可以直接由A求出来。我们设数组的所有元素之和为S，则B可以表示为S-A-x。枢纽元素x又需要A=B ，那么我们可以得到
     * A=B=S-A-x  ===>  2A+x=S
     * @param nums
     * @return
     */
    public int pivotIndexV1(int[] nums) {
        // 首先计算所有元素之和 S
        int S = 0;
        for (int n : nums) {
            S += n;
        }

        int A = 0; // A 为前缀和
        // 迭代计算前缀和
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (2 * A + x == S) {
                // 满足公式中的关系，x 是枢纽元素
                return i;
            }
            A += x; // 计算前缀和
        }
        return -1;
    }

    public static void main(String[] args) {
        PreSum preSum = new PreSum();
        int[] pivotIndexArr = {1, 7, 3, 6, 5, 6};
        Assert.assertEquals(3, preSum.pivotIndex(pivotIndexArr));
        int[] pivotIndexArr1 = {1, 2, 3};
        Assert.assertEquals(-1, preSum.pivotIndex(pivotIndexArr1));
        int[] pivotIndexArr2 = {2, 1, -1};
        Assert.assertEquals(0, preSum.pivotIndex(pivotIndexArr2));


    }
}