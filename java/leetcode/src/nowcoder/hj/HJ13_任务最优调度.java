package nowcoder.hj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://blog.nowcoder.net/n/d1ecc1863dc240a2ab19ad079431519e
 */
public class HJ13_任务最优调度 {

    public static void main(String[] args) {
        int[] arr = {2, 2, 2, 3};
        int N = 2;

        task_schedule(arr, N);
    }

    /**
     * 题目理解：由于存在冷却时间，因此在冷却时间内执行其他任务
     * 合理利用好时间
     */

    /**
     * 任务执行规则如下:
     * 1、任务可以按任意顺序执行，且每个任务执行耗时间均为1个时间单位。
     * 2、两个同类型的任务之间必须有长度为N个单位的冷却时间，比如N为2时，在时间K执行了类型3的任务，那么K+1和K+2两个时间不能执行类型3任务。
     * 3、系统在任何一个单位时间内都可以执行一个任务，或者等待状态。说明:数组最大长度为1000，速度最大值1000。
     * @param tasks
     * @param N
     */
    public static void task(int[] tasks, int N) {
        // 先排序，让同类型的任务放到一起
        for (int i = 0; i < tasks.length; i++) {
            for (int j = i + 1; j < tasks.length; j++) {
                if (tasks[i] > tasks[j]) {
                    int tmp = tasks[i];
                    tasks[i] = tasks[j];
                    tasks[j] = tmp;
                }
            }
        }
        // 上次执行的任务类型
        int lastType = -1;
        // 花费的时间
        int time = 0;
        // 已完成的任务数
        int finishedTasks = 0;
        // 数组下标
        int index = 0;
        // 当前剩下的冷却时间
        int interval = N;
        while (finishedTasks < tasks.length) {
            // 任务已执行 或者 同类型的任务在冷却时间内
            if (tasks[index] == 0) {
                continue;
            }
            if (lastType != tasks[index]) {
                time++;
                lastType = tasks[index];
                tasks[index] = 0; // 表示此任务已执行
                finishedTasks++;
                interval--; // 执行一次任务表明冷却时间减一
            }
            if (lastType == tasks[index] && interval == 0) {
                time++;
                lastType = tasks[index];
                tasks[index] = 0; // 表示此任务已执行
                finishedTasks++;
                interval = N;
            }
            // 在冷却时间内等待
            // 如果没有任务执行，那么冷却时间-1
            if (tasks[index] != 0) {
                interval--;
            }
            // 下一个任务
            index++;
            if (index >= tasks.length) {
                index = 0;
            }
        }
        System.out.println("最短时间 = " + time);
    }

    // 假设N=2,对于2,2,2,3任务中次数最大值为3，对应的任务为2，每次执行任务2之后都有2个等待，
    // 我们只需要用其他任务去填充这些等待即可，结果为：(3-1)*(2+1)+1
    public static void task_schedule(int[] tasks, int N) {
        // 记录各个任务出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        // 任务次数最大值
        int max = 0;
        // 任务次数最大值对应的任务个数
        int max_N = 0;
        for (int i = 0; i < tasks.length; i++) {
            Integer integer = map.get(tasks[i]);
            int tmp_max = (integer == null ? 0 : integer) + 1;
            map.put(tasks[i], tmp_max);
            if (tmp_max > max) {
                max = tmp_max;
            }
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max_N) {
                max_N++;
            }
        }
        int time = Math.max((max - 1) * (N + 1) + max_N, tasks.length);
        System.out.println("最短时间 = " + time);
    }
}
