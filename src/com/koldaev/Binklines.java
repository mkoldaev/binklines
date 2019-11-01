package com.koldaev;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static java.lang.System.out;

public class Binklines {
	
	protected static long timeInSec1;
	protected static String url_api = "https://api.binance.com/api/v1/klines?symbol=";
	protected static String quote = "USD";
	protected static String interval = "1d";
	protected static String limit = "30";
	
	public static void main(String[] args) throws JSONException, IOException, InterruptedException, UnirestException {
		if(args.length > 1) {
			interval = args[1];
			switch(interval) {
				case "1h":
					limit = "24";
					break;
				case "1d":
					limit = "30"; 
					break;
			}
		}
		if(args.length > 0) {
			checkklines(args[0].toUpperCase());
		} else {
			out.println("необходимо задать пару");
		}
	}

	private static void checkklines(String para) throws UnirestException {
		url_api += para + "&interval=" + interval + "&limit=" + limit;
		HttpResponse<JsonNode> request = Unirest.get(url_api).asJson();
		JSONArray results = request.getBody().getArray();
		out.println(results);
	}

}
