package io.maker.dashboard.redis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 
 * @since created on 2022年7月21日
 */
public class RedisBloomFilter {

	private static int size = 1000000;

	private static BloomFilter<Integer> bloomFilter = 
			BloomFilter.create(Funnels.integerFunnel(), size);

	public static void main(String[] args) {
		for (int i = 0; i < size; i++) {
			bloomFilter.put(i);
		}
		for (int i = 0; i < size; i++) {
			// 可能包含
			if (!bloomFilter.mightContain(i)) {
				System.out.println("有坏人逃脱了");
			}
		}
		List<Integer> list = new ArrayList<Integer>(1000);
		for (int i = size + 10000; i < size + 20000; i++) {
			if (bloomFilter.mightContain(i)) {
				list.add(i);
			}
		}
		DecimalFormat df = new DecimalFormat("0.00000");
		// 设置精确到小数点后 2 位
		String result = df.format((float) list.size() / (float) size);
		System.out.println("有误伤的数量：" + list.size() + ", 误伤率 = " + result + "%");
	}
}
