package zhh.algorithm.lucifer.basic.day1.array;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;

import java.util.*;

/**
 * @author zhanghao
 * @date 2021-05-02 08:29
 * @desc 分桶 & 计数：适合用分桶思想的题目一定是不在乎顺序的
 */
public class BucketAndCount {
    /**
     * 49. 字母异位词分组
     * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
     *
     * 示例:
     * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出:
     * [
     *   ["ate","eat","tea"],
     *   ["nat","tan"],
     *   ["bat"]
     * ]
     *
     * 说明：
     * 所有输入均为小写字母。
     * 不考虑答案输出的顺序。
     * https://leetcode-cn.com/problems/group-anagrams/description/
     * @param strs
     * @return
     */
    // 时间复杂度：
    // O(n(k+∣Σ∣))，其中 n 是strs 中的字符串的数量，k 是 strs 中的字符串的的最大长度，Σ 是字符集，
    // 在本题中字符集为所有小写字母，∣Σ∣=26。需要遍历 n 个字符串，对于每个字符串，需要 O(k) 的时间计算每个字母出现的次数，O(∣Σ∣) 的时间生成哈希表的键，以及 O(1) 的时间更新哈希表，
    // 因此总时间复杂度是 O(n(k+∣Σ∣))。
    //
    // 空间复杂度：
    // O(n(k+∣Σ∣))，其中 n 是strs 中的字符串的数量，k 是 strs 中的字符串的最大长度，Σ 是字符集，
    // 在本题中字符集为所有小写字母，∣Σ∣=26。需要用哈希表存储全部字符串，而记录每个字符串中每个字母出现次数的数组需要的空间为 O(∣Σ∣)，在渐进意义下小于O(n(k+∣Σ∣))，可以忽略不计。
    //
    public List<List<String>> groupAnagrams(String[] strs) {
        // 由于互为字母异位词的两个字符串包含的字母相同，因此两个字符串中的相同字母出现的次数一定是相同的，
        // 故可以将每个字母出现的次数使用字符串表示，作为哈希表的键。
        Map<String, List<String>> sameCharacterWords = new HashMap<>();
        for (String word : strs) {
            int[] alphaArr = new int[26];
            for (char character : word.toCharArray()) {
                int index = character - 'a';
                ++alphaArr[index];
            }

            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0; i < alphaArr.length; i++) {
                if (alphaArr[i] > 0) {
                    keyBuilder.append('a'+i);
                    keyBuilder.append(alphaArr[i]);
                }
            }

            String key = keyBuilder.toString();
            if (sameCharacterWords.containsKey(key)) {
                sameCharacterWords.get(key).add(word);
            } else {
                List<String> list = new ArrayList<>();
                list.add(word);
                sameCharacterWords.put(key, list);
            }
        }

        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : sameCharacterWords.entrySet()) {
            List<String> list = sameCharacterWords.get(entry.getKey());
            result.add(list);
        }

        return result;
    }

    public static void main(String[] args) {
        BucketAndCount bucketAndCount = new BucketAndCount();
        String[] groupAnagramsInput = {"eat", "tea", "tan", "ate", "nat", "bat"};
//        List<List<String>> expect = new ArrayList<>();
//        expect.add(Arrays.asList("ate","eat","tea"));
//        expect.add(Arrays.asList("nat","tan"));
//        expect.add(Arrays.asList("bat"));
        System.out.println(JSONObject.toJSONString(bucketAndCount.groupAnagrams(groupAnagramsInput)));
    }
}
