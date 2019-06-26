package com.wtwd.utils;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;


public class PayMethod {


	public static void A() {
		Stripe.apiKey = "sk_test_Xdypv2QpRlx83iYVPax79XyD";

		RequestOptions requestOptions = RequestOptions.builder()
				.setApiKey(Stripe.apiKey).build();
		try {
			System.out.println("1");
			Charge.retrieve("ch_1A2VpX2eZvKYlo2CZAuR60K4", requestOptions);
			System.out.println("2");
		} catch (AuthenticationException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			System.out.println("AuthenticationException");
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			System.out.println("InvalidRequestException");
			e.printStackTrace();
		} catch (APIConnectionException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			System.out.println("APIConnectionException");
			e.printStackTrace();
		} catch (CardException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			System.out.println("CardException");
			e.printStackTrace();
		} catch (APIException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			System.out.println("APIException");
			e.printStackTrace();
		}

	}

	// 创建一个费用
	public static void createCharge() {
		Stripe.apiKey = "sk_test_Xdypv2QpRlx83iYVPax79XyD";

		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("amount", 2000);
		chargeParams.put("currency", "usd");
		chargeParams.put("description", "Charge for madison.moore@example.com");
		chargeParams.put("source", "tok_1A1ujuHuXZjGuTLsdbTgLdlR");
		// ^ obtained with Stripe.js
		try {
			Charge.create(chargeParams);
		} catch (AuthenticationException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			e.printStackTrace();
		} catch (APIConnectionException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			e.printStackTrace();
		} catch (CardException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			e.printStackTrace();
		} catch (APIException e) {
			System.out.println("Status is: " + e.getStatusCode());
			System.out.println("Message is: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 如果创建费用失败了重新请求
	public static void RetrieveCharge() {
		Stripe.apiKey = "sk_test_Xdypv2QpRlx83iYVPax79XyD";

		try {
			Charge.retrieve("ch_1A2rlnHuXZjGuTLsDQgXidcx");
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 更新一个费用
	public static void updateCharge() {
		Stripe.apiKey = "sk_test_Xdypv2QpRlx83iYVPax79XyD";
		try {
			Charge ch = Charge.retrieve("ch_1A2rijHuXZjGuTLseREjZ5Kt");
			Map<String, Object> updateParams = new HashMap<String, Object>();
			updateParams.put("description",
					"Charge for madison.moore@example.com");
			ch.update(updateParams);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
