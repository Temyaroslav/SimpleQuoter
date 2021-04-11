import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class OrderTree {
	private static Random rand = new Random();
	
	private TreeMap<Integer, Order> tmap;
	private double tick_size = 0.1f;
	private boolean is_bid_tree;
	
	public OrderTree(int tree_type) {
		is_bid_tree = (tree_type == 1) ? true : false;
		if (is_bid_tree) {
			tmap = new TreeMap<Integer, Order>(Collections.reverseOrder());
		}
		else {
			tmap = new TreeMap<Integer, Order>();
		}
	}
	
	public void buildTree(int n_nodes, double price) {
		// root parameters of bid/ask tree
		int multiplier = is_bid_tree ? -1 : 1;
		double px = price + multiplier * getRandomL2PriceTick();
		int qx = getL2SizeTick(150);
		
		for (int i = 0; i < n_nodes; i++) {
			px = Util.round(px, 2);
			tmap.put(i, new Order(px, qx, is_bid_tree ? 1 : 0));
			px += multiplier * getRandomL2PriceTick();
			qx = getL2SizeTick(qx);
		}
	}
	
	public synchronized void updateTree(double price) {
		// root parameters of bid/ask tree
		int multiplier = is_bid_tree ? -1 : 1;
		double px = price + multiplier * getRandomL2PriceTick();
		int qx = getL2SizeTick(150);
		
		Iterator<Map.Entry<Integer, Order>> iterator = tmap.entrySet().iterator();
		Map.Entry<Integer, Order> entry = null;
		
		while(iterator.hasNext()) {
			entry = iterator.next();
			
			Order order = entry.getValue();
			
			px = Util.round(px, 2);
			order.updatePrice(px);
			order.updateQuantity(qx);
			px += multiplier * getRandomL2PriceTick();
			qx = getL2SizeTick(qx);
		}
	}
	
	public synchronized Response calcWtdAvgPrice(int qx) {
		Iterator<Map.Entry<Integer, Order>> iterator = tmap.entrySet().iterator();
		Map.Entry<Integer, Order> entry = null;
		
		int remaining = qx;
		double mkt_volume = 0.0f;  // sum of price*quantity
		int volume = 0;  // sum of quantity
		while (iterator.hasNext() && remaining > 0) {
			entry = iterator.next();
			
			Order order = entry.getValue();
			
			mkt_volume += order.price * order.quantity;
			volume += order.quantity;
			remaining -= order.quantity;
		}
		
		return new Response(Util.round(mkt_volume / volume, 2), 
				(remaining < 0) ? qx : qx - remaining, 
				is_bid_tree ? 1 : 0);
	}
	
	private double getRandomL2PriceTick() {
		return tick_size * (1 + 6 * rand.nextDouble());
	}
	
	private int getL2SizeTick(int qx) {
		return (int)(qx * (1.5 + 0.5 * rand.nextDouble()));
	}
	
	private String orderTreeSide() {
		return is_bid_tree ? "BID" : "ASK";
	}
	
	@Override
	public String toString() {
		Iterator<Map.Entry<Integer, Order>> iterator = tmap.entrySet().iterator();
		Map.Entry<Integer, Order> entry = null;
		
		String tmap_side = orderTreeSide();
		String output = tmap_side + " ORDERS: \r\n";
		
		while(iterator.hasNext()) {
			entry = iterator.next();
			Order order = entry.getValue();
			output += order.orderSide() + " " + order.quantity + " @ " + order.price + "\r\n";
		}
		
		return output;
	}
}
