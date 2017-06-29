import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Random;

public class PiFinder {

	public int hits;
	public int total;
	public int end;
	public int numThreads;


	public static void main(String[] args) {

		hits = 0;
		total = 0;
		end = Integer.parseInt(args[0]);
		numThreads = Integer.parseInt(args[1]);

		ArrayList<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < numThreads; i++) {
			threads.add(new Thread(() -> {
				double x;
				double y;
				while (total < end) {	
					x = ThreadLocalRandom.current().nextDouble();
					x *= x;
					y = ThreadLocalRandom.current().nextDouble();
					y *= y;
					if (total >= end) {
						return;
					} else {
						if ((x + y) < 1) {
							hits++;
						}
						total++;
					}
				}
			}));
		}

		for (Thread thread : threads) {
			thread.start();
			System.out.println(thread.isAlive());
		}

		

		try {
			for (Thread thread : threads) {
				thread.join();
			}
		} catch (InterruptedException iex) { }

		System.out.println(hits);
		System.out.println(total);

		System.out.println(4 * (double)hits / total);


	}
}
