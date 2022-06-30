package nowcoder.hj;

/**
 * https://blog.nowcoder.net/n/42420d1a2d324c32838f7f23e4da45f3
 */
public class HJ10_TanChiShe {

    /**
     * 贪吃蛇是一个经典游戏，蛇的身体由若干方格连接而成，身体随蛇头移动。蛇头触碰到食物时，
     * 蛇的长度会增加一格。蛇头和身体的任一方格或者游戏版图边界碰撞时，游戏结束。
     * @param args
     */

    public static void main(String[] args) {
        // 发生碰撞，游戏结束
        // 碰墙，蛇头碰身体都会导致游戏结束

        // 给定一个NM的数组ar，代表NM个方格组成的版图，贪吃蛇每次移动一个方格。若ar[i][j]=='H'，
        // 表示该方可为贪吃蛇的起始位置;若ar[i][j]=='F'，表示该方格为食物，若ar[i][j]=='E'，表示该方格为空格。

        // 贪吃蛇初始长度为1，初始移动方向为向左。输入为给定一系列贪吃蛇的移动操作，返回操作后蛇的长度，
        // 如果在操作执行完之前已经游戏结束，返回游戏结束时贪吃蛇的长度。

        int[][] arr = new int[3][4];

        System.out.println(arr.length); // 二维数组的长度是行数量

    }

    public static void game(char[][] arr, char[] ops) {
        // 找到头位置
        int headRow = 0, headCol = 0;
        for (int i = 0; i < arr.length; i++) { // 行
            for (int j = 0; j < arr[0].length; j++) { // 列
                if (arr[i][j] == 'H') {
                    headRow = i;
                    headCol = j;
                }
            }
        }
        char direction = 'O'; // O表示不移动
        // 开始操作: 第 1行为空格分隔的字母，代表贪吃蛇的移动操作
        for (int i = 0; i < ops.length; i++) {
            if (ops[i] != 'G') {
                // 确定移动的方向
                direction = ops[i];
            } else {
                // G表示移动1格
                move(arr, direction);
            }
        }
    }

    // 需要记录蛇的身体的部位的位置

    /**
     * 字母取值为 U、D、L、R、G，其中U、D、L、R分别表示贪吃蛇往上、下、左、右转向，
     * 转向时贪吃蛇不移动，G表示贪吃蛇按当前的方向移动一格。
     * 初始长度为1
     * <p>
     * 用例保证有且只有一个 H，而 F和 E会有多个
     * @param arr
     */
    public static void move(char[][] arr, char direction) {

    }
}
