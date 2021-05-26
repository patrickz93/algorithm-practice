package zhh.algorithm.lucifer.basic.day1.array;

import org.junit.Assert;

import java.util.*;

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
 *
 *  leetcode 3,209,424,438,567
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

    /**
     * 1658. 将 x 减到 0 的最小操作数
     * 给你一个整数数组 nums 和一个整数 x 。每一次操作时，你应当移除数组 nums 最左边或最右边的元素，然后从 x 中减去该元素的值。请注意，需要 修改 数组以供接下来的操作使用。
     * 如果可以将 x 恰好 减到 0 ，返回 最小操作数 ；否则，返回 -1 。
     *  
     * 示例 1：
     * 输入：nums = [1,1,4,2,3], x = 5
     * 输出：2
     * 解释：最佳解决方案是移除后两个元素，将 x 减到 0 。
     *
     * 示例 2：
     * 输入：nums = [5,6,7,8,9], x = 4
     * 输出：-1
     *
     * 示例 3：
     * 输入：nums = [3,2,20,1,1,3], x = 10
     * 输出：5
     * 解释：最佳解决方案是移除后三个元素和前两个元素（总共 5 次操作），将 x 减到 0 。
     *
     * 提示：
     * 1 <= nums.length <= 105
     * 1 <= nums[i] <= 104
     * 1 <= x <= 109
     *
     * 链接：https://leetcode-cn.com/problems/minimum-operations-to-reduce-x-to-zero
     *
     * 时间复杂度：O(n)
     * 空间复杂度：O(1)
     * @param nums
     * @param x
     * @return
     */
    public int minOperations(int[] nums, int x) {
        // 即：我们剩下的数组一定是原数组的中间部分。
        // 那是不是就是说，我们只要知道数据中子序和等于 sum(nums) - x 的长度。用 nums 的长度减去它就好了？
        // 由于我们的目标是最小操作数，因此我们只要求和为定值的最长子序列，这是一个典型的滑动窗口问题。
        int numsSum = 0;
        for (int num : nums) {
            numsSum += num;
        }
        if (numsSum == x) {
            return nums.length;
        }
        int wL = 0, wR = 0, tempSum = 0;
        int maxSubStringLen = -1;
        while (wR < nums.length) {
            tempSum += nums[wR];
            while ((numsSum - x <= tempSum) && (wL <= wR) ) {
                if (numsSum - x == tempSum) {
                    maxSubStringLen = Math.max(maxSubStringLen, wR - wL + 1);
                }
                tempSum -= nums[wL];
                wL++;
            }
            wR++;
        }
        if (maxSubStringLen < 0) {
            return -1;
        } else {
            return nums.length - maxSubStringLen;
        }
    }

    /**
     * 3. 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     *
     * 示例 2:
     * 输入: s = "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     *
     * 示例 3:
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     * 示例 4:
     * 输入: s = ""
     * 输出: 0
     *
     * 提示：
     * 0 <= s.length <= 5 * 104
     * s 由英文字母、数字、符号和空格组成
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
     *
     * 时间复杂度：O(NK),N为字符串长度，K为自创不一致字母的个数
     * 空间复杂度：O(K),K为子串不一致字母的个数
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if ("".equals(s)) {
            return 0;
        }

        Map<Character, Integer> cnt = new HashMap<>();
        int wL = 0, wR = 1;
        cnt.put(s.charAt(wL), 1);
        int length = 1;
        while (wR < s.length()) {
            cnt.put(s.charAt(wR), cnt.getOrDefault(s.charAt(wR), 0)+1);
            while (!unique(cnt)) {
                cnt.put(s.charAt(wL), cnt.get(s.charAt(wL))-1);
                if (cnt.get(s.charAt(wL)) == 0) {
                    cnt.remove(s.charAt(wL));
                }
                wL++;
            }
            length = Math.max(length, cnt.keySet().size());
            wR++;
        }

        return length;
    }

    private boolean unique(Map<Character, Integer> cnt) {
        for (Map.Entry<Character, Integer> entry : cnt.entrySet()) {
            Integer value = entry.getValue();
            if (value > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 424. 替换后的最长重复字符
     * 给你一个仅由大写英文字母组成的字符串，你可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。在执行上述操作后，找到包含重复字母的最长子串的长度。
     * 注意：字符串长度 和 k 不会超过 104。
     *
     * 示例 1：
     * 输入：s = "ABAB", k = 2
     * 输出：4
     * 解释：用两个'A'替换为两个'B',反之亦然。
     *
     * 示例 2：
     * 输入：s = "AABABBA", k = 1
     * 输出：4
     * 解释：
     * 将中间的一个'A'替换为'B',字符串变为 "AABBBBA"。
     * 子串 "BBBB" 有最长重复字母, 答案为 4。
     * 链接：https://leetcode-cn.com/problems/longest-repeating-character-replacement
     *
     * 时间复杂度：O(NK),N为字符串长度，K为自创不一致字母的个数
     * 空间复杂度：O(K),K为子串不一致字母的个数
     * @param s
     * @param k
     * @return
     */
    public int characterReplacement(String s, int k) {
        if ("".equals(s)) {
            return 0;
        }

        Map<Character, Integer> windowCharCnt = new HashMap<>();
        int wL = 0, wR = 1;
        windowCharCnt.put(s.charAt(wL), 1);
        int ans = 1;
        while (wR < s.length()) {
            char rightChar = s.charAt(wR);
            Integer rightCharCnt = windowCharCnt.getOrDefault(rightChar, 0);
            windowCharCnt.put(rightChar, rightCharCnt+1);
            while (leftMove(windowCharCnt, k)) {
                char leftChar = s.charAt(wL);
                Integer leftCharCnt = windowCharCnt.get(leftChar);
                windowCharCnt.put(leftChar, leftCharCnt -1);
                wL++;
            }
            ans = Math.max(ans, wR - wL + 1);
            wR++;
        }

        return ans;
    }

    private boolean leftMove (Map<Character, Integer> cnt, int k) {
        int sum = 0;
        int maxCnt = 1;
        for (Map.Entry<Character, Integer> entry : cnt.entrySet()) {
            Integer charCnt = entry.getValue();
            sum += charCnt;
            maxCnt = Math.max(maxCnt, charCnt);
        }

        return sum - maxCnt > k;
    }

    /**
     * 438. 找到字符串中所有字母异位词
     * 定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
     * 字符串只包含小写英文字母，并且字符串 s 和 p 的长度都不超过 20100。
     *
     * 说明：
     * 字母异位词指字母相同，但排列不同的字符串。
     * 不考虑答案输出的顺序。
     *
     *  示例 1:
     * 输入:
     * s: "cbaebabacd" p: "abc"
     * 输出:
     * [0, 6]
     * 解释:
     * 起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词。
     * 起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。
     *
     * 示例 2:
     * 输入:
     * s: "abab" p: "ab"
     * 输出:
     * [0, 1, 2]
     * 解释:
     * 起始索引等于 0 的子串是 "ab", 它是 "ab" 的字母异位词。
     * 起始索引等于 1 的子串是 "ba", 它是 "ab" 的字母异位词。
     * 起始索引等于 2 的子串是 "ab", 它是 "ab" 的字母异位词。
     *
     * 链接：https://leetcode-cn.com/problems/find-all-anagrams-in-a-string
     *
     * 时间复杂度：O(n*C),C是字符串p的长度
     * 空间复杂度：O(∣Σ∣),∣Σ∣=26,是所有小写字母的长度
     * @param s
     * @param p
     * @return
     */
    public List<Integer> findAnagrams(String s, String p) {
        if ("".equals(s)) {
            return new ArrayList<>();
        }

        int[] charCnt = new int[26];
        for (char pChar : p.toCharArray()) {
            ++charCnt[pChar - 'a'];
        }

        int wL = 0, wR = 0;
        List<Integer> ans = new ArrayList<>();
        int[] windowCharCnt = new int[26];
        while (wR < s.length()) {
            ++windowCharCnt[s.charAt(wR)-'a'];
            if (wR - wL + 1 == p.length()) {
                if (Arrays.equals(windowCharCnt, charCnt)) {
                    ans.add(wL);
                }
                --windowCharCnt[s.charAt(wL)-'a'];
                ++wL;
            }
            ++wR;
        }

        return ans;
    }

    /**
     * 567. 字符串的排列
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
     * 换句话说，第一个字符串的排列之一是第二个字符串的 子串 。
     *
     * 示例 1：
     * 输入: s1 = "ab" s2 = "eidbaooo"
     * 输出: True
     * 解释: s2 包含 s1 的排列之一 ("ba").
     *
     * 示例 2：
     * 输入: s1= "ab" s2 = "eidboaoo"
     * 输出: False
     *
     * 提示：
     * 输入的字符串只包含小写字母
     * 两个字符串的长度都在 [1, 10,000] 之间
     *
     * 链接：https://leetcode-cn.com/problems/permutation-in-string
     *
     * 时间复杂度：O(s1+s2+∣Σ∣),∣Σ∣=26,是所有小写字母的长度
     * 空间复杂度：O(∣Σ∣),∣Σ∣=26,是所有小写字母的长度
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s2.length() < s1.length()) {
            return false;
        }

        Map<Character, Integer> mapCnt = new HashMap<>();
        for (char c : s1.toCharArray()) {
            mapCnt.put(c, mapCnt.getOrDefault(c, 0)+1);
        }

        int wL = 0, wR = 0;
        int[] cnt = new int[26];
        boolean inclusion = true;
        while (wR < s2.length()) {
            while (wR - wL + 1 <= s1.length()) {
                char rightChar = s2.charAt(wR);
                ++cnt[rightChar - 'a'];
                wR++;
            }

            for (Map.Entry<Character,Integer> entry : mapCnt.entrySet()) {
                char charValue = entry.getKey();
                Integer chatCnt = entry.getValue();
                if (cnt[charValue - 'a'] != chatCnt) {
                    inclusion = false;
                    break;
                }
            }

            if (inclusion) {
                return true;
            }

            char leftChar = s2.charAt(wL);
            --cnt[leftChar - 'a'];
            wL++;
            inclusion = true;
        }

        return false;
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

        int[] minOperationsInput1 = {3,2,20,1,1,3}; int minOperationsOutput1 = 5; int x1 = 10;
        Assert.assertEquals(minOperationsOutput1,slidingWindow.minOperations(minOperationsInput1,x1));
        int[] minOperationsInput2 = {5,6,7,8,9}; int minOperationsOutput2 = -1; int x2 = 4;
        Assert.assertEquals(minOperationsOutput2,slidingWindow.minOperations(minOperationsInput2,x2));
        int[] minOperationsInput3 = {1,1,4,2,3}; int minOperationsOutput3 = 2; int x3 = 5;
        Assert.assertEquals(minOperationsOutput3,slidingWindow.minOperations(minOperationsInput3,x3));
        int[] minOperationsInput4 = {8828, 9581, 49, 9818, 9974, 9869, 9991, 10000, 10000, 10000, 9999, 9993, 9904, 8819, 1231, 6309};
        int minOperationsOutput4 = 16; int x4 = 134365;
        Assert.assertEquals(minOperationsOutput4, slidingWindow.minOperations(minOperationsInput4, x4));

        String longestSubStringInput1 = "abcabcbb"; int longestSubStringOutput1 = 3;
        Assert.assertEquals(longestSubStringOutput1,slidingWindow.lengthOfLongestSubstring(longestSubStringInput1));
        String longestSubStringInput2 = "bbbbb"; int longestSubStringOutput2 = 1;
        Assert.assertEquals(longestSubStringOutput2,slidingWindow.lengthOfLongestSubstring(longestSubStringInput2));
        String longestSubStringInput3 = "pwwkew"; int longestSubStringOutput3 = 3;
        Assert.assertEquals(longestSubStringOutput3,slidingWindow.lengthOfLongestSubstring(longestSubStringInput3));
        String longestSubStringInput4 = "aab"; int longestSubStringOutput4 = 2;
        Assert.assertEquals(longestSubStringOutput4,slidingWindow.lengthOfLongestSubstring(longestSubStringInput4));

        String characterReplacementInput1 = "ABAB"; int k1 = 2; int characterReplacementOutput1 = 4;
        Assert.assertEquals(characterReplacementOutput1, slidingWindow.characterReplacement(characterReplacementInput1,k1));
        String characterReplacementInput2 = "AABABBA"; int k2 = 1; int characterReplacementOutput2 = 4;
        Assert.assertEquals(characterReplacementOutput2, slidingWindow.characterReplacement(characterReplacementInput2,k2));

        String findAnagramsS1 = "cbaebabacd"; String findAnagramsP1 = "abc"; List<Integer> findAnagramsOutput1 = Arrays.asList(0,6);
        Assert.assertArrayEquals(findAnagramsOutput1.toArray(), slidingWindow.findAnagrams(findAnagramsS1, findAnagramsP1).toArray());
        String findAnagramsS2 = "abab"; String findAnagramsP2 = "ab"; List<Integer> findAnagramsOutput2 = Arrays.asList(0,1,2);
        Assert.assertArrayEquals(findAnagramsOutput2.toArray(), slidingWindow.findAnagrams(findAnagramsS2, findAnagramsP2).toArray());

        String checkInclusionInput1S1 = "ab", checkInclusionInput1S2 = "eidbaooo";
        Assert.assertTrue(slidingWindow.checkInclusion(checkInclusionInput1S1,checkInclusionInput1S2));
        String checkInclusionInput2S1 = "ab", checkInclusionInput2S2 = "eidboaoo";
        Assert.assertFalse(slidingWindow.checkInclusion(checkInclusionInput2S1,checkInclusionInput2S2));
    }
}
