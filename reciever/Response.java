import java.time.LocalTime;

public class Response {
	protected LocalTime timestamp;
	protected String ticker = "GOV10Y";
	protected double price;
	protected int size;
	protected int side;
	
	public Response(double px, int qx, int side) {
		timestamp = LocalTime.now().withNano(0);
		
		this.price = px;
		this.size = qx;
		this.side = side;
	}
	
	@Override
	public String toString() {
		String str_side = (side == 1)? "BID" : "OFFER";
		return String.format("%s: <<< %s of %s %s @ %s", timestamp, size, ticker, str_side, price);
	}
	
}
