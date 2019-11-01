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
	protected static String time_open, price_open, max_price, low_price, price_close, volume, time_close, quote_asset_volume, count_trades, print_final;
	
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
			out.println("���������� ������ ����");
		}
	}

	private static void checkklines(String para) throws UnirestException {
		url_api += para + "&interval=" + interval + "&limit=" + limit;
		HttpResponse<JsonNode> request = Unirest.get(url_api).asJson();
		JSONArray results = request.getBody().getArray();
		results.forEach(items -> { 
			JSONArray item = (JSONArray) items;
			time_open = item.get(0).toString();
			price_open = item.get(1).toString();
			max_price = item.get(2).toString();
			low_price = item.get(3).toString();
			price_close = item.get(4).toString();
			volume = item.get(5).toString();
			time_close = item.get(6).toString();
			quote_asset_volume = item.get(7).toString();
			count_trades = item.get(8).toString();
			
			print_final = "����� ��������: "+time_open;
			print_final += ", ���� ��������: "+price_open;
			print_final += ", ����.����: "+max_price;
			print_final += ", ���.����: "+low_price;
			print_final += ", ���� ��������: "+price_close;
			print_final += ", �����: "+volume;
			print_final += ", ����� ��������: "+time_close;
			print_final += ", ����� ������: "+quote_asset_volume;
			print_final += ", ���������� ������: "+count_trades;
			
			out.println(print_final);
			out.println("");

		});
	}

}
