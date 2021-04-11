import java.sql.Timestamp;

public class Order {
	protected Timestamp timestamp;
	protected String ticker = "GOV10Y";
	protected int quantity;
	protected double price;
	protected int side;
	
	public Order(double price, int quantity, int side) {
		timestamp = new Timestamp(System.currentTimeMillis());
		this.price = price;
		this.quantity = quantity;
		this.side = side;
	}
	
	public void updatePrice(double px) {
		price = px;
	}
	
	public void updateQuantity(int qx) {
		quantity = qx;
	}
	
	public String orderSide() {
		return (side == 1) ? "BID" : "ASK";
	}
}
