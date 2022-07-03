package nowcoder.hj;

import java.util.List;

/**
 * https://blog.nowcoder.net/n/8d06725b779746298813e0189c8eaf72
 * 
 * @since created on 2022年6月30日
 */
public class HJ19_RectangleArea {

	public static void main(String[] args) {
		
	}

//	【矩形相交的面积】在坐标系中，给定3个矩形，求相交区域的面积。
//	输入描述:
//	3行输入分别为3个矩形的位置，分别代表“左上角x坐标”，“左上角y坐标”，“矩形宽”，“矩形高” -1000<=x,y<1000
//	输出描述:
//	输出3个矩形相交的面积，不相交的输出0

	public static class Rectangle {
		public int x, y;
		public int width, height;

		public Rectangle(int x, int y, int width, int height) {
			super();
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}

	// 可以先求两个区间的交集，然后再和第三个区间进行相交
	
	public static void area(List<Rectangle> rectangles) {
		// 求出相交部分
		for (int i = 0; i < rectangles.size(); i++) {
			
		}
	}
	
	// 不知道谁在上，谁在下
	public static Rectangle merge(Rectangle left, Rectangle right) {
		// 确保大小关系
		if (left.x > right.x) {
			Rectangle temp = left;
			left = right;
			right = temp;
		}
		return null;
	}

}
