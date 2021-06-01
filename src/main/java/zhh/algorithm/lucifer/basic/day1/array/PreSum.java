package zhh.algorithm.lucifer.basic.day1.array;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanghao
 * @date 2021-05-01 15:12
 * @desc 前缀和——解决数组区间之和的问题
 * 前缀和数组的定义中使用了左闭右开区间[)。这种表示方法的优点之一是很容易做区间的减法。
 * 题目与「子数组求和」有关、且数组是连续的 => 前缀和
 * https://www.cxyxiaowu.com/11326.html
 * https://github.com/azl397985856/leetcode/blob/master/selected/atMostK.md
 */
public class PreSum {

    /**
     * 母体0
     * 有 N 个的正整数放到数组 A 里，现在要求一个新的数组 B，新数组的第 i 个数 B[i]是原数组 A 第 0 到第 i 个数的和。
     * 使用公式 pre[𝑖]=pre[𝑖−1]+nums[𝑖]得到每一位前缀和的值，从而通过前缀和进行相应的计算和解题。
     * @param nums
     * @return
     */
    public int[] preSum0(int[] nums) {
        int [] preSum = new int[nums.length];
        preSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            preSum[i] = preSum[i-1] + nums[i];
        }
        return preSum;
    }

    /**
     * 母体1
     * 求一个数组的连续子数组总个数
     * 一种思路是总的连续子数组个数等于：
     * 以索引为 0 结尾的子数组个数 + 以索引为 1 结尾的子数组个数 + ... + 以索引为 n - 1 结尾的子数组个数，这无疑是完备的。
     * 输入：[1,3,4]
     * 输出：6
     * 说明：[1],[3],[1,3],[4],[3,4],[1,3,4]
     */
    public int preSum1(int[] nums) {
        int preSum = 0;
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            ++preSum;
            ans += preSum;
        }
        return ans;
    }

    /**
     * 母体2
     * 求一个数组相邻差为 1 连续子数组的总个数，即索引差 1 的同时，值也差 1。
     * @param nums
     * @return
     */
    public int preSum2(int[] nums) {
        int preSum = 1;
        int ans = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] == 1) {
                preSum += 1;
            } else {
                preSum = 1;
            }
            ans += preSum;
        }
        return ans;
    }

    /**
     * 母体3
     * 求出不大于 k 的子数组的个数,不大于 k 指的是子数组的全部元素都不大于 k
     * 输入：[1,3,4],k=3
     * 输出：3
     * 说明：[1],[1,3],[3]
     * @param nums
     * @return
     */
    public int atMostK(int[] nums, int k) {
        int preSum = 0;
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= k) {
                preSum += 1;
            } else {
                preSum = 0;
            }
            ans += preSum;
        }
        return ans;
    }

    /**
     * 母体4
     * 求出子数组最大值刚好是 k 的子数组的个数
     * 输入：[1,3,4],k=3
     * 输出：2
     * 说明：[3],[1,3]
     * 实际上是 exactK 可以直接利用 atMostK，即 atMostK(k) - atMostK(k - 1)
     * @param nums
     * @param k
     * @return
     */
    public int exactK(int[] nums, int k) {
        return atMostK(nums, k) - atMostK(nums, k-1);
    }

    /**
     * 母体5
     * 求出子数组最大值刚好是 介于 k1 和 k2 的子数组的个数
     * 实际上是 betweenK 可以直接利用 atMostK，即 atMostK(k1, nums) - atMostK(k2 - 1, nums)，其中 k1 > k2。
     * 前提是值是离散的， 比如上面我出的题都是整数。 因此我可以直接 减 1，因为 1 是两个整数最小的间隔。
     * 因此不难看出 exactK 其实就是 betweenK 的特殊形式。 当 k1 == k2 的时候， betweenK 等价于 exactK。
     * @param nums
     * @param k1
     * @param k2
     * @return
     */
    public int betweenK(int[] nums, int k1, int k2) {
        return atMostK(nums, k1) - atMostK(nums, k2-1);
    }

    /**
     * 724. 寻找数组的中心下标
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     * 如果数组不存在中心下标，返回 -1 。如果数组有多个中心下标，应该返回最靠近左边的那一个。
     *
     * 链接：https://leetcode-cn.com/problems/find-pivot-index
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

    /**
     * 467. 环绕字符串中唯一的子字符串
     * 把字符串 s 看作是“abcdefghijklmnopqrstuvwxyz”的无限环绕字符串，所以 s 看起来是这样的："...zabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd....". 
     * 现在我们有了另一个字符串 p 。你需要的是找出 s 中有多少个唯一的 p 的非空子串，尤其是当你的输入是字符串 p ，你需要输出字符串 s 中 p 的不同的非空子串的数目。 
     * 注意: p 仅由小写的英文字母组成，p 的大小可能超过 10000。
     *  
     * 示例 1:
     * 输入: "a"
     * 输出: 1
     * 解释: 字符串 S 中只有一个"a"子字符。
     *  
     * 示例 2:
     * 输入: "cac"
     * 输出: 2
     * 解释: 字符串 S 中的字符串“cac”只有两个子串“a”、“c”。.
     *  
     * 示例 3:
     * 输入: "zab"
     * 输出: 6
     * 解释: 在字符串 S 中有六个子串“z”、“a”、“b”、“za”、“ab”、“zab”。.
     *
     * 链接：https://leetcode-cn.com/problems/unique-substrings-in-wraparound-string
     *
     * 时间复杂度：O(n),p的字符串长度
     * 空间复杂度：O(K),K为字符集长度，即26
     * @param p
     * @return
     */
    public int findSubstringInWraproundString(String p) {
        p = "^"+p;
        int preSum = 1;
        // 用map记录以key为结尾的字符串的数量的最大值，可以有效去重
        Map<Character, Integer> lengthMap = new HashMap<>(26);
        for (int i = 1; i < p.length(); i++) {
            // 相邻字符的差值是1 或者头尾相连的情况(za)
            int diff = p.charAt(i) - p.charAt(i - 1);
            if ((diff == 1) || (diff == -25)) {
                preSum += 1;
            } else {
                preSum = 1;
            }
            int value = Math.max(preSum, lengthMap.getOrDefault(p.charAt(i), 0));
            lengthMap.put(p.charAt(i), value);
        }
        return lengthMap.values().stream().reduce(Integer::sum).orElse(0);
    }

    /**
     * 795. 区间子数组个数
     * 给定一个元素都是正整数的数组A ，正整数 L 以及 R (L <= R)。
     * 求连续、非空且其中最大元素满足大于等于L 小于等于R的子数组个数。
     *
     * 例如 :
     * 输入:
     * A = [2, 1, 4, 3]
     * L = 2
     * R = 3
     * 输出: 3
     * 解释: 满足条件的子数组: [2], [2, 1], [3].
     *
     * 注意:
     * L, R  和 A[i] 都是整数，范围在 [0, 10^9]。
     * 数组 A 的长度范围在[1, 50000]。
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * 思路：
     *  求连续字数组个数 => 前缀和
     *  母体3和母体5，可以获得betweenK的数量，直接套用公式即可
     *
     * 链接：https://leetcode-cn.com/problems/number-of-subarrays-with-bounded-maximum
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        return betweenK(nums, right, left);
    }

    public static void main(String[] args) {
        PreSum preSum = new PreSum();
        int[] a = {1,2,3,4,5,6};
        int[] b = {1, 3, 6, 10, 15, 21};
        Assert.assertArrayEquals(b, preSum.preSum0(a));
        int[] preSum1 = {1,3,4};
        Assert.assertEquals(6,preSum.preSum1(preSum1));
        Assert.assertEquals(3,preSum.atMostK(preSum1, 3));
        Assert.assertEquals(2,preSum.exactK(preSum1,3));

        int[] pivotIndexArr = {1, 7, 3, 6, 5, 6};
        Assert.assertEquals(3, preSum.pivotIndex(pivotIndexArr));
        int[] pivotIndexArr1 = {1, 2, 3};
        Assert.assertEquals(-1, preSum.pivotIndex(pivotIndexArr1));
        int[] pivotIndexArr2 = {2, 1, -1};
        Assert.assertEquals(0, preSum.pivotIndex(pivotIndexArr2));

        int[] subArraySumArr = {1,1,1};
        Assert.assertEquals(2, preSum.subArraySum(subArraySumArr, 2));
        Assert.assertEquals(2, preSum.subArraySumHash(subArraySumArr,2));

        String findSubstringInWraproundStringP1 = "a";
        Assert.assertEquals(1,preSum.findSubstringInWraproundString(findSubstringInWraproundStringP1));
        String findSubstringInWraproundStringP2 = "cac";
        Assert.assertEquals(2,preSum.findSubstringInWraproundString(findSubstringInWraproundStringP2));
        String findSubstringInWraproundStringP3 = "zab";
        Assert.assertEquals(6,preSum.findSubstringInWraproundString(findSubstringInWraproundStringP3));

        int[] numSubarrayBoundedMaxInput = {2, 1, 4, 3};
        Assert.assertEquals(3, preSum.numSubarrayBoundedMax(numSubarrayBoundedMaxInput, 2, 3));
    }
}