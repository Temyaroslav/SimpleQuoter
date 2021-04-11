import java.util.Random;
import java.time.LocalTime;

import org.apache.commons.math3.distribution.NormalDistribution;

public class RequestForQuote {
	private static Random rand_side = new Random();
	private static NormalDistribution norm_dist = new NormalDistribution(1000.0f, 250.0f);
	
	protected LocalTime timestamp;
	protected String ticker;
	protected int size;
	protected int side;
	
	public RequestForQuote() {
		timestamp = LocalTime.now().withNano(0);
		ticker = "GOV10Y";
		size = (int)norm_dist.sample();
		side = rand_side.nextInt(2);
	}
	
	@Override
	public String toString() {
		String str_side = (side == 1) ? "BUY" : "SELL";
		return String.format("%s: >>> please quote %s of %s to %s", timestamp, size, ticker, str_side);
	}
}
