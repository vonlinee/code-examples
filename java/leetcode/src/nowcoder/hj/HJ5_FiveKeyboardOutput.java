package nowcoder.hj;

import java.util.Scanner;

/**
 * https://blog.nowcoder.net/n/c7bb482cddb647b5965c2f55ef13f7da
 */
public class HJ5_FiveKeyboardOutput {

    static int keyboardCount = 0, screenCount = 0, selectedCount = 0;

    public static void main(String[] args) {
//        输入描述:
//        输入为一行，为简化解析，用数字 12345代表 a,ctrl-c,ctrl-x,ctrl-v,ctrl-a五个键的输入，数字用空格分隔
//        输出描述:
//        输出一个数字，为最终屏目上字母的数量。
        solution();
    }

    public static void solution1() {
        int[] ops = {1, 1, 1};

        for (int i = 0; i < ops.length; i++) {
            keyboard(ops[i]);
        }
        System.out.println("屏幕上的字母个数 = " + screenCount);
    }

    /**
     * 有一个特殊的 5键键盘，上面有 a,ctrl-c,ctrl-x,ctrl-v,ctrl-a五个键。
     * a键在屏幕上输出一个字母 a;
     * ctrl-c将当前选择的字母复制到剪贴板;
     * ctrl-x将当前选择的 字母复制到剪贴板，并清空选择的字母;
     * ctrl-v将当前剪贴板里的字母输出到屏幕;
     * ctrl-a 选择当前屏幕上所有字母。
     * <p>
     * 注意:
     * 1、剪贴板初始为空，新的内容被复制到剪贴板时会覆盖原来的内容
     * 2、当屏幕上没有字母时，ctrl-a无效
     * 3、当没有选择字母时，ctrl-c和 ctrl-x无效
     * 4、当有字母被选择时，a和ctrl-v这两个有输出功能的键会先清空选择的字母，再进行输出
     * <p>
     * <p>
     * 理解：单独按ctrl-c是没有选则字母的，按ctrl-a才会选择
     */
    public static void keyboard(int operation) {
        if (operation == 1) {
            screenCount++;
        }
        if (operation == 2) {
            if (selectedCount == 0) {
                System.out.println("未选中字符，ctrl-c无效");
            }
            keyboardCount += selectedCount;
        }
        if (operation == 3) {
            if (selectedCount == 0) {
                System.out.println("未选中字符，ctrl-x无效");
                return;
            }
            keyboardCount += selectedCount;
            screenCount = 0; // 清空选择的字幕
        }
        if (operation == 4) {
            // 将当前剪贴板里的字母输出到屏幕;
            screenCount += keyboardCount;
            keyboardCount = 0;
        }
        if (operation == 5) {
            selectedCount = screenCount; // 选择当前屏幕上所有字母
        }
    }

    public static void solution() {
        Scanner in = new Scanner(System.in);
        String dataLine = in.nextLine();
        // 输入按空格分开
        String[] dataArr = dataLine.split(" ");
        String result = "";
        String buffer = "";
        boolean choice = false;
        for (int i = 0; i < dataArr.length; i++) {
            if ("1".equals(dataArr[i])) {
                if (choice) {
                    result = "a";
                    choice = false;
                } else {
                    result += "a";
                }
            } else if ("2".equals(dataArr[i])) {
                if (choice) {
                    buffer = result;
                }
            } else if ("3".equals(dataArr[i])) {
                if (choice) {
                    buffer = result;
                    result = "";
                }
            } else if ("4".equals(dataArr[i])) {
                result += buffer;
                buffer = "";
                choice = false;
            } else if ("5".equals(dataArr[i])) {
                choice = true;
            }
        }
        System.out.println(result);
        System.out.println(result.length());
        in.close();
    }
}

