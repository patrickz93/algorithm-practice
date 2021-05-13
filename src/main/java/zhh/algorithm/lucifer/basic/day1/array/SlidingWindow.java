package zhh.algorithm.lucifer.basic.day1.array;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    /**
     * 930. 和相同的二元子数组
     * 在由若干 0 和 1  组成的数组 A 中，有多少个和为 S 的非空子数组。
     *
     * 示例：
     * 输入：A = [1,0,1,0,1], S = 2
     * 输出：4
     *
     * 思路：可变窗口
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param A
     * @param S
     * @return
     */
    private int atMost(int[] A, int S) {
        if (S < 0) {
            return 0;
        }

        int windowLeftIndex = 0, windowRightIndex = 0, num = 0;
        while (windowRightIndex < A.length) {
            S -= A[windowRightIndex];
            while (S < 0) {
                S += A[windowLeftIndex];
                windowLeftIndex++;
            }
            num += windowRightIndex - windowLeftIndex + 1;
            windowRightIndex++;
        }

        return num;
    }

    public int numSubArraysWithSum(int[] A, int S) {
        // Exact(K) = atMost(K) - atMost(K-1)，前提数组离散
        return atMost(A, S) - atMost(A, S - 1);
    }

    /**
     * 978. 最长湍流子数组
     * 当 A 的子数组 A[i], A[i+1], ..., A[j] 满足下列条件时，我们称其为湍流子数组：
     * 若 i <= k < j，当 k 为奇数时， A[k] > A[k+1]，且当 k 为偶数时，A[k] < A[k+1]；
     * 或 若 i <= k < j，当 k 为偶数时，A[k] > A[k+1] ，且当 k 为奇数时， A[k] < A[k+1]。
     * 也就是说，如果比较符号在子数组中的每个相邻元素对之间翻转，则该子数组是湍流子数组。
     * 返回 A 的最大湍流子数组的长度。
     *
     * 示例 1：
     * 输入：[9,4,2,10,7,8,8,1,9]
     * 输出：5
     * 解释：(A[1] > A[2] < A[3] > A[4] < A[5])
     *
     *  示例 2：
     * 输入：[4,8,12,16]
     * 输出：2
     *
     *  示例 3：
     * 输入：[100]
     * 输出：1
     *
     * 提示：
     * 1 <= A.length <= 40000
     * 0 <= A[i] <= 10^9
     * 链接：https://leetcode-cn.com/problems/longest-turbulent-subarray
     *
     * 空间复杂度：O(n)
     * 时间复杂度：O(1)
     * @param arr
     * @return
     */
    public int maxTurbulenceSize(int[] arr) {
        int res = 1;
        int wL = 0, wR = 0;
        while (wR < arr.length-1) {
            if (wL == wR) {
                if (arr[wL] == arr[wL+1]) {
                    wL++;
                }
                wR++;
            } else {
                if ((arr[wR] > arr[wR+1]) && arr[wR] > arr[wR-1]) {
                    wR++;
                } else if ((arr[wR] < arr[wR+1]) && (arr[wR] < arr[wR-1])) {
                    wR++;
                } else {
                    wL = wR;
                }
            }
            res = Math.max(res, wR - wL + 1);
        }

        return res;
    }

    /**
     * 76. 最小覆盖子串
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
     * 注意：如果 s 中存在这样的子串，我们保证它是唯一的答案。
     * 示例 1：
     * 输入：s = "ADOBECODEBANC", t = "ABC"
     * 输出："BANC"
     *
     *  示例 2：
     * 输入：s = "a", t = "a"
     * 输出："a"
     *
     *  提示：
     * 1 <= s.length, t.length <= 105
     * s 和 t 由英文字母组成
     *
     * 进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？
     * 链接：https://leetcode-cn.com/problems/minimum-window-substring
     *
     * 时间复杂度：每次检查是否可行会遍历整个 t 的哈希表，哈希表的大小与字符集的大小有关，设字符集大小为 C, O(C⋅∣s∣+∣t∣)
     * 空间复杂度：这里用了两张哈希表作为辅助空间，每张哈希表最多不会存放超过字符集大小的键值对，我们设字符集大小为 C ,O(C)
     * @param s
     * @param t
     * @return
     */
    private Map<Character, Integer> subStringCharMap = new HashMap<>();
    private Map<Character, Integer> cnt = new HashMap<>();
    public String minWindow(String s, String t) {

        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            subStringCharMap.put(c, subStringCharMap.getOrDefault(c, 0)+1);
        }

        String ans = s + "1";
        int wL = 0, wR = 0;
        while (wR < s.length()) {
            String windowString = s.substring(wL, wR + 1);
            char c = s.charAt(wR);
            cnt.put(c, cnt.getOrDefault(c, 0)+1);

            while (containsSubString()) {
                ans = windowString.length() < ans.length() ? windowString : ans;
                char updateChar = s.charAt(wL);
                wL++;
                cnt.put(updateChar, cnt.get(updateChar)-1);
                windowString = s.substring(wL, wR + 1);
            }

            wR++;
        }
        return ans.length() > s.length() ? "" : ans;
    }

    private boolean containsSubString() {
        Set<Map.Entry<Character, Integer>> entries = subStringCharMap.entrySet();
        for (Map.Entry<Character, Integer> entry : entries) {
            if (!cnt.containsKey(entry.getKey()) || cnt.get(entry.getKey()) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SlidingWindow slidingWindow = new SlidingWindow();

        int[] minSubArrayLenInput1 = {2,3,1,2,4,3};
        Assert.assertEquals(2, slidingWindow.minSubArrayLen(7, minSubArrayLenInput1));

        int[] numSubarraysWithSumInput = {1,0,1,0,1};
        int S = 2;
        Assert.assertEquals(4, slidingWindow.numSubArraysWithSum(numSubarraysWithSumInput,S));

        int[] maxTurbulenceSizeInput = {9,4,2,10,7,8,8,1,9};
        Assert.assertEquals(5,slidingWindow.maxTurbulenceSize(maxTurbulenceSizeInput));

        String s = "bbaa", t = "aba";
        Assert.assertEquals("baa", slidingWindow.minWindow(s, t));
    }
}
