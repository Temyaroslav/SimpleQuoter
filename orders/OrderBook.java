import java.util.logging.Level;

public class OrderBook extends Asset implements Runnable{
	OrderTree bid_tree = new OrderTree(1);
	OrderTree ask_tree = new OrderTree(0);
	
	public OrderBook(int n_orders) {		
		// tick mid price which is the "root" of both bid and ask tree
		this.tickMidPrice();
		
		// build initial bid/ask trees
		bid_tree.buildTree(n_orders, this.mid_price);
		ask_tree.buildTree(n_orders, this.mid_price);
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				this.tickMidPrice();
				bid_tree.updateTree(mid_price);
				ask_tree.updateTree(mid_price);
				MyLogger.log(Level.FINE, "\r\n" + bid_tree.toString() + "\r\n" + ask_tree.toString());
				
				Thread.sleep(1 * 1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Response processRfq(RequestForQuote rfq) {
		Response response = null;
		if (rfq.side == 1) {
			response = ask_tree.calcWtdAvgPrice(rfq.size);
		}
		else {
			response = bid_tree.calcWtdAvgPrice(rfq.size);
		}
		
		return response;
	}
	
}
