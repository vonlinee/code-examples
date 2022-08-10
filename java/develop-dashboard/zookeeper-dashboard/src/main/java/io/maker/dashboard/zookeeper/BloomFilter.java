package io.maker.dashboard.zookeeper;

/**
 * 
 * @since created on 2022年7月21日
 */
public class BloomFilter {

	// hash 1
	public int h1(String s, int arrSize) {
		int hash = 0;
		for (int i = 0; i < s.length(); i++) {
			hash = (hash + ((int) s.charAt(i)));
			hash = hash % arrSize;
		}
		return hash;
	}

	// hash 2
	public int h2(String s, int arrSize) {
		int hash = 1;
		for (int i = 0; i < s.length(); i++) {
			hash = hash + (int) Math.pow(19, i) * (int) s.charAt(i);
			hash = hash % arrSize;
		}
		return hash % arrSize;
	}

	// hash 3
	public int h3(String s, int arrSize) {
		int hash = 7;
		for (int i = 0; i < s.length(); i++) {
			hash = (hash * 31 + s.charAt(i)) % arrSize;
		}
		return hash % arrSize;
	}

	// hash 4
	public int h4(String s, int arrSize) {
		int hash = 3;
		int p = 7;
		for (int i = 0; i < s.length(); i++) {
			hash += hash * 7 + s.charAt(0) * Math.pow(p, i);
			hash = hash % arrSize;
		}
		return hash;
	}

	// lookup operation
	public boolean lookup(boolean[] bitarray, int arrSize, String s) {
		int a = h1(s, arrSize);
		int b = h2(s, arrSize);
		int c = h3(s, arrSize);
		int d = h4(s, arrSize);
		if (bitarray[a] && bitarray[b] && bitarray[c] && bitarray[d])
			return true;
		else
			return false;
	}

	// insert operation
	public void insert(boolean[] bitarray, int arrSize, String s) {
		// check if the element in already present or not
		if (lookup(bitarray, arrSize, s))
			System.out.println(s + " 已存在!");
		else {
			int a = h1(s, arrSize);
			int b = h2(s, arrSize);
			int c = h3(s, arrSize);
			int d = h4(s, arrSize);
			bitarray[a] = true;
			bitarray[b] = true;
			bitarray[c] = true;
			bitarray[d] = true;
			System.out.println(s + " 插入成功!");
		}
	}

	// Driver Code
	public static void main(String[] args) {
		boolean[] bitarray = new boolean[100];
		int arrSize = 100;
		String[] sarray = { "abound", "abounds", "abundance", "abundant", "accessible", "bloom", "blossom", "bolster",
				"bonny", "bonus", "bonuses", "coherent", "cohesive", "colorful", "comely", "comfort", "gems",
				"generosity", "generous", "generously", "genial", "bluff", "cheater", "hate", "war", "humanity",
				"racism", "hurt", "nuke", "gloomy", "facebook", "geeksforgeeks", "twitter" };

		BloomFilter filter = new BloomFilter();
		for (int i = 0; i < 33; i++) {
			filter.insert(bitarray, arrSize, sarray[i]);
		}
	}
}
