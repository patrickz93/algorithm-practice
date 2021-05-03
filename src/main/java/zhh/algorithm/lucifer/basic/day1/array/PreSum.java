package zhh.algorithm.lucifer.basic.day1.array;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghao
 * @date 2021-05-01 15:12
 * @desc 前缀和——解决数组区间之和的问题
 * 前缀和数组的定义中使用了左闭右开区间[)。这种表示方法的优点之一是很容易做区间的减法。
 * 题目与「子数组求和」有关 => 前缀和
 * https://www.cxyxiaowu.com/11326.html
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

        // A 为前缀和
        int A = 0;
        // 迭代计算前缀和
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (2 * A + x == S) {
                // 满足公式中的关系，x 是枢纽元素
                return i;
            }
            // 计算前缀和
            A += x;
        }
        return -1;
    }

    /**
     * 给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。
     *
     * 示例 1 :
     *
     * 输入:nums = [1,1,1], k = 2
     * 输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
     *
     *  空间复杂度：O(n)
     *  时间复杂度：O(n²)
     * @param nums
     * @param k
     * @return
     */
    public int subArraySum(int[] nums, int k) {
        int [] preSum = new int[nums.length+1];
        for (int i = 0; i < nums.length; i++) {
            preSum[i+1] = preSum[i] + nums[i];
        }

        // sum of nums[i..j) = sum of nums[0..j) - sum of nums[0..i)
        int count = 0;
        for (int i = 0; i <= nums.length; i++) {
            for (int j = i+1; j <= nums.length; j++) {
                // 前缀和相减求子数组之和
                if (preSum[j] - preSum[i] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 在计算前缀和的同时把前缀和的每个值出现的次数都记录在哈希表中
     * 空间复杂度：O(n)
     * 时间复杂度：O(n)
     * @param nums
     * @param k
     * @return
     */
    public int subArraySumHash(int[] nums, int k) {
        // 前缀和 -> 该前缀和（的值）出现的次数
        Map<Integer, Integer> preSum = new HashMap<>();
        // base case，前缀和 0 出现 1 次
        preSum.put(0, 1);

        // 前缀和
        int sum = 0;
        int res = 0;
        for (int n : nums) {
            // 计算前缀和
            sum += n;
            // 查找有多少个 sum[i] 等于 sum[j] - k
            if (preSum.containsKey(sum - k)) {
                res += preSum.get(sum - k);
            }
            // 更新 sum[j] 的个数
            if (preSum.containsKey(sum)) {
                preSum.put(sum, preSum.get(sum) + 1);
            } else {
                preSum.put(sum, 1);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        PreSum preSum = new PreSum();
        int[] pivotIndexArr = {1, 7, 3, 6, 5, 6};
        Assert.assertEquals(3, preSum.pivotIndex(pivotIndexArr));
        int[] pivotIndexArr1 = {1, 2, 3};
        Assert.assertEquals(-1, preSum.pivotIndex(pivotIndexArr1));
        int[] pivotIndexArr2 = {2, 1, -1};
        Assert.assertEquals(0, preSum.pivotIndex(pivotIndexArr2));

        int[] subArraySumArr = {1,1,1};
        Assert.assertEquals(2, preSum.subArraySum(subArraySumArr, 2));
        Assert.assertEquals(2, preSum.subArraySumHash(subArraySumArr,2));
    }
}