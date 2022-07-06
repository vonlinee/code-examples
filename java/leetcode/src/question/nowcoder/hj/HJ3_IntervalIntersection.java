package question.nowcoder.hj;
/**
 * https://blog.nowcoder.net/n/fd28c4bd1367426eb973a3e62e79a24e
3、标题:区间交集
【区间交集】给定一组闭区间，其中部分区间存在交集。任意两个给定区间的交集，称为公共区间(如:[1,2],[2,3]的公共区间为[2,2]，[3,5],[3,6]的公共区间为
[3,5])。公共区间之间 若存在交集，则需要合并(如:[1,3],[3,5]区间存在交集[3,3]，需合并为[1,5])。按升序排列 输出合并后的区间列表。
输入描述: 一组区间列表，区间数为 N: 0<=N<=1000;区间元素为 X: -10000<=X<=10000。
输出描述: 升序排列的合并区间列表
备注:
1、区间元素均为数字，不考虑字母、符号等异常输入。
2、单个区间认定为无公共区间。
 * 
 * 
 * @since created on 2022年6月22日
 */
public class HJ3_IntervalIntersection {
	
	// 考察动态规划
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 两个区间的交集和第三个区间仍有交集
	 * 比如：[0, 2], [1, 3], [3, 4]该如何取交集？
	 * 1.如果[0, 2], [1, 3]先取交集，再和剩下的[3, 4]取，结果就是[1, 2], [3, 4]
	 * 2.如果[3, 4], [1, 3]先取交集，再和剩下的[0, 2]取，结果就是[0, 2], [3, 3]
	 * 可以看到结果是不一样的
	 * @param intervals 交集 {{1, 2}, {4, 5}, {7, 3}};
	 * @param curIndex
	 */
	public static void intervalIntersection(int[][] intervals, int curIndex) {
		for (int i = 0; i < intervals.length; i++) {
			//从第一个区间向后找交集
			for (int j = i + 1; j < intervals.length; j++) {
				
			}
		}
	}
}
