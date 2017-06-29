import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.Random;

public class PiFinder {

	public static AtomicLong hits;
	//public static AtomicLong total;
	public static long end;
	public static int numThreads;


	public static void main(String[] args) {

		hits = new AtomicLong(0);
		//total = new AtomicLong(0);
		end = Long.parseLong(args[0]);
		numThreads = Integer.parseInt(args[1]);

		Thread[] threads = new Thread[numThreads];

		for (int i = 0; i < numThreads; i++) {
			threads[i] = new Thread(() -> {
				hits.getAndAdd(getPi(end / numThreads));
			});
		}

		for (Thread thread : threads) {
			thread.start();
			//System.out.println(thread.isAlive());
		}

		

		try {
			for (Thread thread : threads) {
				thread.join();
			}
		} catch (InterruptedException iex) { }

		System.out.println(hits.get());
		System.out.println(end);

		System.out.println(4 * ((float)hits.get() / end));


	}
	
	public static long getPi(long myEnd) {
		double x;
		double y;
		boolean currComp;
		long myTotal = 0;
		long myHits = 0;
		while (myTotal < myEnd) {	
			x = ThreadLocalRandom.current().nextDouble();
			x *= x;
			y = ThreadLocalRandom.current().nextDouble();
			y *= y;
			currComp = (x+y) < 1;
			if (currComp) {
				myHits++;
			}
			myTotal++;
		}
		return myHits;
	}
}
