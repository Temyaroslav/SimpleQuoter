import java.util.PriorityQueue;
import java.util.Random;

public class Client implements Runnable{
	private static Random rand = new Random();
	
	PriorityQueue<RequestForQuote> queue;
	
	public Client(PriorityQueue<RequestForQuote> order_queue) {
		this.queue = order_queue;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				RequestForQuote rfq = new RequestForQuote();
				queue.add(rfq);
				
				String msg = rfq.toString();
				System.out.println(msg);
				
				
				Thread.sleep((rand.nextInt(5) + 1) * 1000);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
