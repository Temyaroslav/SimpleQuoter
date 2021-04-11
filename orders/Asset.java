import org.apache.commons.math3.distribution.NormalDistribution;

public class Asset {
	private NormalDistribution norm_dist;
	double mid_price;
	
	public Asset() {
		norm_dist = new NormalDistribution(95.0f, 0.5f);
	}
	
	public void tickMidPrice() {
		mid_price =  Util.round(norm_dist.sample(), 2);
	}
	
}
