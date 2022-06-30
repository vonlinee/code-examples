package leetcode.primary;

/**
 * 
 * @since created on 2022年6月21日
 */
public class C1_MergeUser {

	public static class User {
		public String a;
		public String b;
		public String c;

		public User(String a, String b, String c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}

	// 如果两个user，a字段一样、或者b字段一样、或者c字段一样，就认为是一个人//请合并users，返回合并之后的用户数量
	// 使用并查集
	// https://www.runoob.com/data-structures/union-find-basic.html
	
	
}
