package com.koldaev;

import java.io.IOException;
import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Binklines {
	
	protected static long timeInSec1;
	protected static String url_api = "https://www.binance.com/info-api/v1/public/agg_kline?base=";
	protected static String quote = "USD";
	protected static String type = "DAY";
	protected static String limit = "30";
	
	public static void main(String[] args) throws JSONException, IOException, InterruptedException, UnirestException {
		if(args.length > 1) {
			type = args[1];
			switch(type) {
				case "HOUR":
					limit = "24";
					break;
				case "DAY":
					limit = "30";
					break;
			}
		}
		checkklines(args[0]);
	}

	private static void checkklines(String para) {
		url_api += para + "quote=" + quote + "&type=" + type + "&limit=30";
	}

}
