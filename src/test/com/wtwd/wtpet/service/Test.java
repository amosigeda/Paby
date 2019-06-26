package test.com.wtwd.wtpet.service;

import java.text.SimpleDateFormat;

import com.godoing.rose.lang.SystemException;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.wtwd.common.bean.devicedown.CmdDownSetImpl;
import com.wtwd.common.lang.Constant;

public class Test {
	
	static SimpleDateFormat ymdThms = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	static SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static void main(String[] args) throws SystemException {
		
		
		/*//查询交易详情
		Payment payment = queryPaymentDetails("PAYID-LOVSFBI1W746027E8397740N");
		String email = payment.getPayer().getPayerInfo().getEmail();
		String zipCode = payment.getPayer().getPayerInfo().getShippingAddress().getPostalCode(); // 邮编
		String payerId = payment.getPayer().getPayerInfo().getPayerId(); //customerId,  "6EF59T746A6QE",
		String payState = payment.getState(); // 支付状态  "approved"
		String createTime = payment.getCreateTime();
		String message = payment.getFailureReason();
		//String amount = null;
		String currency = null;
		List<com.paypal.api.payments.Transaction> transactions = payment.getTransactions();
		for (com.paypal.api.payments.Transaction transaction : transactions) {
			String money = transaction.getAmount().getTotal();
			//amount = money.replace(".", "");
			currency = transaction.getAmount().getCurrency();
		}
		if (message == null) {
			message = "ok";
		}
		if ("approved".equals(payState)) {
			payState = "200";
		}								
		Date time2date;
		try {
			time2date = ymdThms.parse(createTime);
			createTime = ymdhms.format(time2date);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
	}
	
	private static Payment queryPaymentDetails(String pay_id) {
		APIContext apiContext = new APIContext(Constant.clientID, Constant.clientSecret, Constant.mode);
		Payment payment = null;
		try {
			payment = Payment.get(apiContext, pay_id);
		} catch (PayPalRESTException e) {
			e.getMessage();
		}
		return payment;
	}
	
	
}
