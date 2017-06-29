import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.ArrayList;
import java.util.Random;

public class PiFinder {

	public static AtomicInteger hits;
	public static AtomicInteger total;
	public static int end;
	public static int numThreads;


	public static void main(String[] args) {

		hits = new AtomicInteger(0);
		total = new AtomicInteger(0);
		end = Integer.parseInt(args[0]);
		numThreads = Integer.parseInt(args[1]);

		ArrayList<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < numThreads; i++) {
			threads.add(new Thread(() -> {
				double x;
				double y;
				while (total.get() < end) {	
					x = ThreadLocalRandom.current().nextDouble();
					x *= x;
					y = ThreadLocalRandom.current().nextDouble();
					y *= y;
					if (total.get() >= end) {
						return;
					} else {
						if ((x + y) < 1) {
							hits.getAndIncrement();
						}
						total.getAndIncrement();
					}
				}
			}));
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
		System.out.println(total.get());

		System.out.println(4 * ((double)hits.get() / total.get()));


	}
}
