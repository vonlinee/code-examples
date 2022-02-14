package code.example.java.multithread.juc.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
	
	public static void main(String[] args) {
		long[] array = new long[2000];
		
		long expectedSum = 0;
		for(int i = 0; i < array.length ; i++) {
			array[i] = randomInt();
			expectedSum += array[i];
		}
		
		System.out.println(expectedSum);
		
		ForkJoinTask<Long> fjtask;
	}
	
	static Random random = new Random(0);
	
	public static long randomInt() {
		return random.nextInt(10000);
	}
}

class SumTask extends RecursiveTask<Long> {
	
	static final int THRESHOLD = 500; //阈值
	long[] array;
	int start;
	int end;
	
	public SumTask(long[] array, int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Long compute() {
		if (end - start < THRESHOLD) {
			long sum = 0;
			for(int i = 0; i<end; i++) {
				sum +=this.array[i];
				try {
					Thread.sleep(2);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return sum;
		} else { //任务太大进行拆分
			int middle = (end - start) / 2;
			
		}
		return null;
	}
	
}
