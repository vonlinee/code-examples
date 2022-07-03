package leetcode.problem;

public class P1975_MaximumMatrixSum {

    public String removeDuplicates(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        // 先确定i，然后在i的左右两边找是否有相邻的重复的数
        while (i < chars.length) {
            if (chars[i] == 0) continue;
            // 在i的左边找是否有相邻的重复的数
            int left = -1;
            for (int j = i - 1; j > 0; j--) {
                if (chars[j] == 0) continue;
                left = j;
                break;
            }
            // 在i的右边找是否有相邻的重复的数
            int right = -1;
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[j] == 0) continue;
                right = j;
                break;
            }
            // 左边和右边只要有一个即可
            if (left > 0) {
                chars[i] = 0;
                chars[left] = 0;
            }
            if (right > 0) {
                chars[i] = 0;
                chars[right] = 0;
            }
            i++;
        }
        // 得到结果
        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            if (c == 0) continue;
            result.append(c);
        }
        return result.toString();
    }

    public String removeDuplicates1(String s) {
        char[] chars = s.toCharArray();
        int maxLength = 0;
        for (int i = 0; i < chars.length - 1; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (chars[i] > chars[j]) {
                    maxLength = chars[i];
                }
            }
        }
        // 计数排序
        String[] arr = new String[maxLength + 1];
        for (int i = 0; i < chars.length; i++) {
            arr[chars[i]] += (i + "-");
        }
        //
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null || arr[i].length() == 0) continue;
            // 得到同一个值的所有下标
            String[] split = arr[i].split("-");
            // 将下标相邻的去掉
            for (int j = 0; j < split.length - 1; j++) {
                for (int k = j + 1; k < split.length; k++) {
                    // 相同的下标
                    if (split[j].equals(split[k])) {
                        chars[Integer.parseInt(split[j])] = 0;
                        chars[Integer.parseInt(split[k])] = 0;
                    }
                }
            }
        }
        // 遍历chars得到结果
        StringBuilder result = new StringBuilder();
        for (char c : chars) {
            if (c == 0) continue;
            result.append(c);
        }
        return result.toString();
    }
}
