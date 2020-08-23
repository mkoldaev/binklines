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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import static java.lang.System.exit;
import static java.lang.System.out;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public class Fundament {
	
	protected static long timeInSec1;
	protected static String url_api = "https://api.binance.com/api/v1/klines?symbol=";
	protected static String url_info = "https://api.binance.com/api/v1/exchangeInfo";
	protected static String quote = "USD";
	
	protected static String interval = "1h"; //1min 5m 15m 1h 4h 1d 1w 1M
	
	protected static String limit = "500"; // лимит по кол-ву свечей
	protected static String insmysql, insmysql_paras, time_open_norm, time_close_norm, update_avg;
	protected static String para, price_open, max_price, low_price, price_close, volume, quote_asset_volume, count_trades, print_final;
	protected static long time_open, time_close;
	protected static int time_open_int, time_close_int;
	static Statement st, st_paras, st_avg;
	protected static BufferedReader rd; 
	protected static ArrayList<String> showparas = new ArrayList<String>();

	protected final static long min_milis =Long.parseLong("0000000000001");
	protected final static long plus_1d  = Long.parseLong("0000086400000");
	protected final static long plus_4h  = Long.parseLong("0000014400000");
	protected final static long plus_1h  = Long.parseLong("0000003600000");
	protected final static long plus_15m = Long.parseLong("0000000900000");
	protected final static long plus_5m  = Long.parseLong("0000000300000");
	protected final static long plus_1m  = Long.parseLong("0000000060000");
	protected static String intervaltobase, intervaltoapi, ticksize, stepsize, maxprice, minprice;
	protected static String apipara = "emptystringpara";

	static PreparedStatement paranames, paralast = null;
	static ResultSet paranamesresult, paralastresult = null;

	static Connection conn, conn_paras, conn_last = null;
	static Properties connInfo = new Properties();
	static {
		connInfo.put("characterEncoding", "UTF8");
		connInfo.put("user", "root");
		connInfo.put("password", "vnfhry46");
	}
	
	protected final static DateFormat df_year = new SimpleDateFormat("y");
	protected final static DateFormat df_month = new SimpleDateFormat("M");
	protected final static DateFormat df_week = new SimpleDateFormat("W");
	protected final static DateFormat df_day = new SimpleDateFormat("d");
	protected final static DateFormat df_hour = new SimpleDateFormat("H");
	
	protected static Jedis jedis;
	
	protected static void setconns() throws SQLException {
		String host = "localhost";
		//host = "dockerhub.ru:3311"; //для локального запуска - нужно будет комментировать
		conn = DriverManager.getConnection("jdbc:mysql://"+host+"/klines?allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		conn_paras = DriverManager.getConnection("jdbc:mysql://"+host+"/klines?allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		conn_last = DriverManager.getConnection("jdbc:mysql://"+host+"/klines?allowPublicKeyRetrieval=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		jedis = JedisFactory.getInstance().getJedisPool().getResource();
	}
	
	protected static boolean getcurrenttime(long basemillis) {

		long currentmiisecunds = System.currentTimeMillis(); // 13 знаков
		boolean needbin = false;
		Date currentDate = new Date(currentmiisecunds);
		int int_year = Integer.parseInt(df_year.format(currentDate));
		int int_month = Integer.parseInt(df_month.format(currentDate));
		int int_week = Integer.parseInt(df_week.format(currentDate));
		int int_day = Integer.parseInt(df_day.format(currentDate));
		int int_hour = Integer.parseInt(df_hour.format(currentDate));
		int base_year = Integer.parseInt(df_year.format(basemillis));
		int base_month = Integer.parseInt(df_month.format(basemillis));
		int base_week = Integer.parseInt(df_week.format(basemillis));
		int base_day = Integer.parseInt(df_day.format(basemillis));
		int base_hour = Integer.parseInt(df_hour.format(basemillis));

		out.println("текущая дата: "+int_year+"."+int_month+"."+int_day+" "+int_hour);
		out.println("бинансовая дата: "+base_year+"."+base_month+"."+base_day+" "+base_hour);

		switch (interval) {
			case "1M":
				//если текущий год равен полученному из бинанса и текущий месяц равен бинансовому, ничего не делаем
				if((int_year == base_year) & (int_month == base_month)) { } else { needbin = true; }
			break;
			case "1w":
				//если текущий год равен полученному из бинанса и текущая неделя равна бинансовой, ничего не делаем
				if((int_year == base_year) & (int_week == base_week)) { } else { needbin = true; }
			break;
			case "1d":
				//если текущий год, месяц и день равен бинансовому, ничего не делаем
				if((int_year == base_year) & (int_month == base_month) & (int_day == base_day)) { } else { needbin = true; }
			break;
			case "1h":
				//если текущий год, месяц, день и час равен бинансовому, ничего не делаем
				if((int_year == base_year) & (int_month == base_month) & (int_day == base_day) & (int_hour == base_hour)) { } else { needbin = true; }
			break;
		}
		
		// проверяем здесь https://www.unixtimestamp.com
		// на примере 1580515200000 - 01.02.2020
		// текущее значение 1582014462597
		//out.println("");
		return needbin;
		
	}
	


	protected static void getparasfrommysql()
			throws SQLException, InterruptedException, NullPointerException, UnirestException {

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formatted = formatter.format(date);
		out.println("\nначало загрузки свечей по UTC: "+formatted);

		String sqlmysql = "SELECT para FROM paras where status = \"TRADING\"";
		if(!apipara.equals("emptystringpara")) sqlmysql += " and para = \"" + apipara + "\"";
		//out.println(sqlmysql);
		
		paranames = conn_paras.prepareStatement(sqlmysql);
		if (paranames.execute()) {
			paranamesresult = paranames.getResultSet();
			while (paranamesresult.next()) {
				Long lasttime = 0L;
				Long base_lasttime = 0L;
				para = paranamesresult.getString("para");
				//out.println("");
				out.println("тянем пару " + para);

				//здесь нужно обязательно учитывать статус TRADING, иначе будут инсертиться дубликаты, п.ч. последняя статистика ушедшей пары не будет совпадать с текущим временем
				String mysqllast = "SELECT * FROM klines.kline_" + interval.toLowerCase() + " where para = \"" + para
						+ "\" order by open_milliseconds desc LIMIT 1";
				
				//out.println(mysqllast);
				paralast = conn_last.prepareStatement(mysqllast);
				if (paralast.execute()) {
					paralastresult = paralast.getResultSet();
					while (paralastresult.next()) {
						//здесь нужно сверять по закрытию свечи в базе, а не по открытию!
						base_lasttime = Long.parseLong(paralastresult.getString("close_milliseconds"));
						// здесь вычисляется последняя миллисекунда в статистике из базы и специально
						// инкрементируется, чтобы повторно не вставлять уже имеющиеся данные
						lasttime = base_lasttime += min_milis;
						//out.println(lasttime);
					}
				}

				// здесь нужно проверять текущее время по интервалу и если оно не вышло, то не
				// посылаем лишний запрос на бин
				// сначала достаем текущее время
				if(getcurrenttime(base_lasttime) == true) checkklines(para,lasttime);

				// пока только имитируем запрос к бину
				//if(getcurrenttime(base_lasttime)) testcheckklines(para,lasttime);
			}
		}
		date = new Date();
		formatted = formatter.format(date);
		out.println("завершение загрузки свечей по UTC: "+formatted+"\n");
	}
	


    protected static void testcheckklines(String para2, Long lasttime) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(500); // здесь задержка в полсекунды; наверно, чтобы по api не заблочили
		out.println("");
		out.println("Вызываем пару "+para2+ " с милями "+lasttime+ " и интервалом "+interval);
		out.println("");
	}

	//startTime 1580515200001 соответствует 1 февраля 2020 года. Для unixtime нужно убавлять 3 посл.символа, т.е. 1580515200
    //данные приходят начиная с самой древней даты, поэтому нужно при вставке проверять на уникальность ?? на самом деле нет
    protected static void checkklines(String para, Long starttime) throws UnirestException, NullPointerException, InterruptedException, JSONException {

		TimeUnit.MILLISECONDS.sleep(500); // здесь задержка в полсекунды; наверно, чтобы по api не заблочили
		//out.println("Вызываем пару "+para+ " с милями "+starttime+ " и интервалом "+interval);

		intervaltoapi = interval;
		if (interval.equals("1min")) {
			//out.println("Задан минимальный интервал");
			intervaltoapi = "1m";
		}

		url_api = "https://api.binance.com/api/v1/klines?symbol=";
		url_api += para + "&interval=" + intervaltoapi + "&limit=" + limit;
		if (starttime > 0) url_api += "&startTime=" + starttime;
		HttpResponse<JsonNode> request = Unirest.get(url_api).asJson();
		JSONArray results = request.getBody().getArray();

		//здесь нужно делать дополнительную проверку
		//например, если за день свечи берем, то только за предыдущий день, а не текущий
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
				st = conn.createStatement();
				st_avg = conn.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			intervaltobase = interval.toLowerCase();
			insmysql = "INSERT INTO `kline_" + intervaltobase + "` (`para`, `time_open`, `time_close`, `price_open`, `max_price`, `low_price`, `price_close`, `volume`, `count_trades`, `open_milliseconds`, `close_milliseconds`) VALUES ";
			time_open_norm = convertSecondsToHMmSs(time_open);
			time_close_norm = convertSecondsToHMmSs(time_close);
			time_open_int = (int) (time_open / 1000);
			time_close_int = (int) (time_close / 1000);

			print_final = "Открытие в " + time_open_norm;
			print_final += ", откр цена: " + price_open;
			print_final += ", макс.цена: " + max_price;
			print_final += ", мин.цена: " + low_price;
			print_final += ", закр цена : " + price_close;
			print_final += ", объем: " + volume;
			print_final += ", закрытие в " + time_close_norm;
			// print_final += ", квота ордера: "+quote_asset_volume;
			print_final += ", сделки: " + count_trades;

			//out.println(print_final);

			insmysql += "(\"" + para + "\",\"" + time_open_norm + "\",\"" + time_close_norm + "\",\"" + price_open
					+ "\",\"" + max_price + "\",\"" + low_price + "\",\"" + price_close + "\",\"" + volume + "\",\""
					+ item.get(7).toString() + "\"," + time_open + "," + time_close + ");";
			//здесь добавляем в таблицу среднее значение через добавленную нашу функцию в базе - avg_two_crypto
			//средняя цена рассчитывается с фитиля - середины между максимальной и минимальной цены за отрезок времени
			//это нужно для прогноза тренда монеты
			update_avg = "update kline_"+intervaltobase+" set price_avg = avg_two_crypto(low_price,max_price) where price_avg is NULL";
			try {
				st.execute(insmysql);
				st_avg.execute(update_avg);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				out.println(e.getMessage());
			} catch (NullPointerException n) {
				out.println(n.getMessage());
			}

		});
	}


	protected static void getparas() throws JSONException, IOException, SQLException, NullPointerException, JedisException {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formatted = formatter.format(date);
		out.println("\nначало сбора пар по UTC: "+formatted);
		try {
			st_paras = conn.createStatement();
			st_paras.executeUpdate("TRUNCATE paras");
			out.println("таблица с парами очищена и обновляется");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			out.println(e1.getMessage());
		}
		JSONObject json = readJsonFromUrl(url_info);
		JSONArray res = json.getJSONArray("symbols");
		//здесь нужно выводить в лог кол-во пар и время сбора!
		res.forEach(item -> {
			JSONObject obj = (JSONObject) item;
			String para = obj.getString("symbol").intern();
			String status = obj.getString("status").intern();
			String quoteAsset = obj.getString("quoteAsset").intern();
			String baseAsset = obj.getString("baseAsset").intern();
			if (status == "TRADING" && (quoteAsset == "USDT" || quoteAsset == "BTC")) {
				JSONArray filtersa = obj.getJSONArray("filters");
			    ticksize = filtersa.getJSONObject(0).getString("tickSize");
			    stepsize = filtersa.getJSONObject(2).getString("stepSize");
			    minprice = filtersa.getJSONObject(0).getString("minPrice");
			    maxprice = filtersa.getJSONObject(0).getString("maxPrice");
			    //out.println(para+": ticksize: "+ticksize+"; stepsize: "+stepsize+"; minprice: "+minprice+"; maxprice: "+maxprice);
				showparas.add(para); // получаем только пары живые с торгами
				insmysql_paras = "INSERT INTO `paras` (`para`, `status`, `quoteAsset`, `baseAsset`, `ticksize`, `stepsize`, `minprice`, `maxprice`) VALUES ";
				insmysql_paras += "(\"" + para + "\",\"" + status + "\",\"" + quoteAsset + "\",\"" + baseAsset + 
						"\",\"" + ticksize + "\",\"" + stepsize + "\",\"" + minprice + "\",\"" + maxprice +
						"\");";
				try {
					st_paras.execute(insmysql_paras);
		    		jedis.set(para+"_TICKSIZE",ticksize);
		    		jedis.set(para+"_STEPSIZE",stepsize);
		    		jedis.set(para+"_MINPRICE",minprice);
		    		jedis.set(para+"_MAXPRICE",maxprice);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException n) {
					out.println(n.getMessage());
				} catch (JedisException en) {
					out.println(en.getMessage());
				}
			}
		});
		out.println("Кол-во живых пар с USDT и BTC: " + showparas.size());
		date = new Date();
		formatted = formatter.format(date);
		out.println("завершение сбора пар по UTC: "+formatted+"\n");
	}

	protected static String convertSecondsToHMmSs(long millis) {
		// out.println(millis);
		Date date = new Date(millis);
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formatted = formatter.format(date);
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
