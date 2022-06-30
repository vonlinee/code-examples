package nowcoder.hj;

import java.util.*;

/**
 * 给定两个数组arr1和arr2,其中arr2中元素互不相同，无重复。且arr2中出现的元素在arr1中都存在。
 * 要求对arr1进行重新排序，满足以下几点要求：
 * arr2中出现过的元素保持相对位置不变；
 * arr2中未出现过的元素放到arr1末尾，且采用升序排列。
 * 给出的用例如下：
 * arr1=[2,5,6,7,3,19,4,2,5,9]
 * arr2=[2,3,5,7,6]
 * 期望输出：
 * [2,2,3,5,5,7,6,4,9,19]
 */
public class HJ999_Order {

    public static void main(String[] args) {
        int[] arr1 = {};
        int[] arr2 = {};
    }

    /**
     * 力扣有原题1122。做法：哈希表求补集,桶计数模拟
     * 计数排序
     * @param arr1
     * @param arr2
     * @return
     */
    public static int[] relativeSortArray(int[] arr1, int[] arr2) {
        int n = arr1.length, m = arr2.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            map.put(arr2[i], arr2[i]);
        }
        //存放(a,b)的补集
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!map.containsKey(arr1[i])) list.add(arr1[i]);
        }
        Collections.sort(list);
        //建桶
        int[] b = new int[1010];
        for (int i = 0; i < n; i++) b[arr1[i]]++;

        int[] res = new int[n];
        int x = 0;
        for (int i = 0; i < m; i++) {
            while (--b[arr2[i]] >= 0) res[x++] = arr2[i];
        }
        for (int i = 0; i < list.size(); i++) res[x++] = list.get(i);
        return res;
    }

}
