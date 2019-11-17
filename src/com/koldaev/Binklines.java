package com.koldaev;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import static java.lang.System.out;

//аргументы в настройках запуска eclipse
//eosusdt 1M 36
//eosbtc 1M 36
public class Binklines {
	
	protected static long timeInSec1;
	protected static String url_api = "https://api.binance.com/api/v1/klines?symbol=";
	protected static String url_info = "https://api.binance.com/api/v1/exchangeInfo";
	protected static String quote = "USD";
	protected static String interval = "1d";
	protected static String limit = "30";
	protected static String insmysql, insmysql_paras, time_open_norm, time_close_norm;
	protected static String para, price_open, max_price, low_price, price_close, volume, quote_asset_volume, count_trades, print_final;
	protected static long time_open, time_close;
	protected static int time_open_int;
	static Statement st, st_paras;
	protected static BufferedReader rd;
	protected static ArrayList<String> showparas = new ArrayList<String>();
	
	static PreparedStatement paranames, paralast = null;
	static ResultSet paranamesresult, paralastresult   = null;
	
	
	
	static Connection conn, conn_paras, conn_last  = null;
	static Properties connInfo = new Properties();
	static {
		connInfo.put("characterEncoding","UTF8");
		connInfo.put("user", "root");
		connInfo.put("password", "vnfhry46");
	}
	
	public static void main(String[] args) throws JSONException, IOException, InterruptedException, UnirestException, SQLException, NullPointerException {

	
		conn = DriverManager.getConnection("jdbc:mysql://localhost/klines?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		out.println("first start");

		//информацию по доступным парам получаем только раз в сутки
		conn_paras = DriverManager.getConnection("jdbc:mysql://localhost/klines?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		conn_last = DriverManager.getConnection("jdbc:mysql://localhost/klines?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		//getparas(); //этот метод запускается только раз в сутки после truncate таблицы paras
		getparasfrommysql();		
		System.exit(0);
		
		if(args.length > 1) interval = args[1];
		if(args.length == 2) {
			switch(interval) {
				case "1h":
					limit = "24"; //часовые свечи за сутки
					break;
				case "1d":
					limit = "30"; //дневные свечи за месяц
					break;
				case "1w":
					limit = "26"; //недельные свечи за полгода
					break;
				case "1M":
					limit = "12"; //месячные свечи за год
					break;
			}
		} else if(args.length == 3) {
			limit = args[2];
		}
		out.println("интервал "+interval+", лимит на "+limit+" свечей");
		
		if(args.length > 0) { 
			para = args[0].toUpperCase();
			out.println("смотрим "+para);
			//checkklines(para);
		} else {
			out.println("без явного задания пары смотрим BTCUSDT");
			//checkklines("BTCUSDT");
		}
	}
	
	private static void getparasfrommysql() throws SQLException, InterruptedException, NullPointerException, UnirestException {
		// TODO Auto-generated method stub
		paranames = conn_paras.prepareStatement("SELECT para FROM paras");
		if (paranames.execute()) {
			paranamesresult = paranames.getResultSet();
        	while(paranamesresult.next()) {
        		Long lasttime = 0L;
        		TimeUnit.MILLISECONDS.sleep(500);
        		para = paranamesresult.getString("para");
        		out.println("тянем пару "+para);
        		//ниже меняем интервал для последовательного заполнения статистики
        		//interval = "1d";
        		interval = "1h";
        		limit = "400";
        		
        		String mysqllast = "SELECT * FROM klines.kline_" + interval.toLowerCase() + " where para = \"" + para + "\" order by open_milliseconds desc LIMIT 1";
        		out.println(mysqllast);
        		paralast = conn_last.prepareStatement(mysqllast);
        		if (paralast.execute()) {
        			paralastresult = paralast.getResultSet();
        			while(paralastresult.next()) {
        				lasttime = Long.parseLong(paralastresult.getString("open_milliseconds"));
        				lasttime += 0000000000001L;
        				out.println(lasttime);
        			}
        		}
				checkklines(para,lasttime);
        	}
		}
	}
	
private static void checkklines(String para, Long starttime) throws UnirestException, NullPointerException, InterruptedException {
		
		url_api = "https://api.binance.com/api/v1/klines?symbol=";
		url_api += para + "&interval=" + interval + "&limit=" + limit;
		if(starttime > 0) url_api += "&startTime="+starttime;
		HttpResponse<JsonNode> request = Unirest.get(url_api).asJson();
		JSONArray results = request.getBody().getArray();
		
		out.println(url_api);
		//TimeUnit.SECONDS.sleep(1);
		out.println(results);
		
		results.forEach(items -> { 
			JSONArray item = (JSONArray) items;
			time_open = (long) item.get(0);
			price_open = item.get(1).toString();
			max_price = item.get(2).toString();
			low_price = item.get(3).toString();
			price_close = item.get(4).toString();
			volume = item.get(5).toString();
			time_close = (long) item.get(6);
			quote_asset_volume = item.get(7).toString();
			count_trades = item.get(8).toString();
			
			try {
				st=conn.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			insmysql = "INSERT INTO `kline_"+interval.toLowerCase()+"` (`para`, `time_open`, `time_close`, `price_open`, `max_price`, `low_price`, `price_close`, `volume`, `count_trades`, `open_milliseconds`) VALUES ";
			time_open_norm = convertSecondsToHMmSs(time_open);
			time_close_norm = convertSecondsToHMmSs(time_close);
			time_open_int = (int) (time_open/1000);
			
			print_final = "Открытие в "+time_open_norm;
			print_final += ", откр цена: "+price_open;
			print_final += ", макс.цена: "+max_price;
			print_final += ", мин.цена: "+low_price;
			print_final += ", закр цена : "+price_close;
			print_final += ", объем: "+volume;
			print_final += ", закрытие в "+time_close_norm;
			//print_final += ", квота ордера: "+quote_asset_volume;
			print_final += ", сделки: "+count_trades;
			
			out.println(print_final);
			
			insmysql += "(\""+para+"\",\""+time_open_norm+"\",\""+time_close_norm+"\",\""+price_open+"\",\""+max_price+"\",\""+low_price+"\",\""+price_close+"\",\""+volume+"\",\""+item.get(7).toString()+"\","+time_open+");";
			out.println(insmysql);
			try {
				st.execute(insmysql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				out.println(e.getMessage());
			} catch (NullPointerException n) {
				out.println(n.getMessage());
			}
			out.println("");

		});
	}

	private static void getparas() throws JSONException, IOException, SQLException, NullPointerException {
		try {
			st_paras=conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			out.println(e1.getMessage());
		}
		JSONObject json = readJsonFromUrl(url_info);
		JSONArray res = json.getJSONArray("symbols");
		res.forEach(item -> { 
		    JSONObject obj = (JSONObject) item;
		    String para = obj.getString("symbol").intern();
		    String status = obj.getString("status").intern();
		    String quoteAsset = obj.getString("quoteAsset").intern();
		    String baseAsset = obj.getString("baseAsset").intern();
		    if(status == "TRADING" && (quoteAsset == "USDT" || quoteAsset == "BTC")) {
		    	showparas.add(para); //получаем только пары живые с торгами
		    	insmysql_paras = "INSERT INTO `paras` (`para`, `status`, `quoteAsset`, `baseAsset`) VALUES ";
			    insmysql_paras += "(\""+para+"\",\""+status+"\",\""+quoteAsset+"\",\""+baseAsset+"\");";
			    try {
					st_paras.execute(insmysql_paras);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException n) {
					out.println(n.getMessage());
				}
		    }
		});
		out.println(showparas);
	}

	public static String convertSecondsToHMmSs(long millis) {
		//out.println(millis);
		Date date = new Date(millis);
		SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formatted = formatter.format(date );
		return formatted;
	}

	protected static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	      rd.close();
	    }
	  }
	
	protected static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

}
