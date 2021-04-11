import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Launcher {

	public static void main(String[] args) {
		
		// queue to store RFQs
		PriorityQueue<RequestForQuote> order_queue = new PriorityQueue<RequestForQuote>(50, new OrderSizeComparator());
		
		// client object to generate RFQs
		Client client = new Client(order_queue);
		
		// order book object to generate random prices
		OrderBook order_book = new OrderBook(5);
		
		// pricer object to generate response for RFQs
		Pricer pricer = new Pricer(order_queue, order_book);
		
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(client);
		executor.execute(order_book);
		executor.execute(pricer);
	}

}
