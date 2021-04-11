import java.util.Comparator;

public class OrderSizeComparator implements Comparator<RequestForQuote>{
	@Override
	public int compare(RequestForQuote rfq1, RequestForQuote rfq2) {
		return Integer.compare(rfq1.size, rfq2.size);
	}
}