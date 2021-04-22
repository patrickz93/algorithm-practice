package zhh.algorithm.lucifer.basic.day1.array;

import com.alibaba.fastjson.JSONObject;

/**
 * @author zhanghao
 * @date 2021-04-01 07:35
 * @desc
 * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
 *
 * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
 *
 *  
 *
 * 示例 1：
 *
 * 输入：nums = [2,0,2,1,1,0]
 * 输出：[0,0,1,1,2,2]
 * 示例 2：
 *
 * 输入：nums = [2,0,1]
 * 输出：[0,1,2]
 * 示例 3：
 *
 * 输入：nums = [0]
 * 输出：[0]
 * 示例 4：
 *
 * 输入：nums = [1]
 * 输出：[1]
 *  
 *
 * 提示：
 *
 * n == nums.length
 * 1 <= n <= 300
 * nums[i] 为 0、1 或 2
 *  
 *
 * 进阶：
 *
 * 你可以不使用代码库中的排序函数来解决这道题吗？
 * 你能想出一个仅使用常数空间的一趟扫描算法吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sort-colors
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class ColorSort {
    public int[] onePtr(int[] arr) {
        int ptr = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                swap(i, ptr, arr);
                ++ptr;
            }
        }

        for (int j = ptr; j < arr.length; j++) {
            if (arr[j] == 1) {
                swap(j,ptr,arr);
                ++ptr;
            }
        }

        return arr;
    }

    private void swap(int i, int j, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 如果找到1，交换arr[p1]和arr[i]，p1++
     * 如果找到0，首先要交换arr[p0]和arr[i]，p0++
     *  只交换arr[p0]可能会将之前交换好的1，交换出去，所以此处需要判断p0与p1的位置
     *      如果p1>p0，还需要交换arr[i]与arr[p1]，p1++
     * @param arr
     * @return
     */
    public int[] twoPtr(int[] arr) {
        int ptr0 = 0, ptr1 = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                swap(i, ptr1, arr);
                ++ptr1;
            }

            if (arr[i] == 0) {
                swap(i,ptr0, arr);
                if (ptr0 < ptr1) {
                    swap(i,ptr1, arr);
                }
                ++ptr0;
                ++ptr1;
            }
        }

        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {2,0,2,1,1,0};
        ColorSort colorSort = new ColorSort();
//        int[] sorted = colorSort.onePtr(arr);
        int[] sorted = colorSort.twoPtr(arr);
        System.out.println(JSONObject.toJSONString(sorted));
    }
}
