import java.util.PriorityQueue;

public class Pricer implements Runnable{
	PriorityQueue<RequestForQuote> queue;
	OrderBook order_book;
	
	public Pricer(PriorityQueue<RequestForQuote> queue, OrderBook order_book) {
		this.queue = queue;
		this.order_book = order_book;
	}

	@Override
	public void run() {
		try {
			while(true) {
				if(!queue.isEmpty()) {
					RequestForQuote rfq = queue.remove();
					Response response = order_book.processRfq(rfq);
					
					String msg = response.toString();
					System.out.println(msg);
					System.out.println();
				}
				
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
