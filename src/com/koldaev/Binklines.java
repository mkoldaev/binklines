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
	protected static String limit = "500"; // лимит по кол-ву свечей
	protected static String insmysql, insmysql_paras, time_open_norm, time_close_norm;
	protected static String para, price_open, max_price, low_price, price_close, volume, quote_asset_volume,
			count_trades, print_final;
	protected static long time_open, time_close;
	protected static int time_open_int;
	static Statement st, st_paras;
	protected static BufferedReader rd;
	protected static ArrayList<String> showparas = new ArrayList<String>();

	static PreparedStatement paranames, paralast = null;
	static ResultSet paranamesresult, paralastresult = null;

	static Connection conn, conn_paras, conn_last = null;
	static Properties connInfo = new Properties();
	static {
		connInfo.put("characterEncoding", "UTF8");
		connInfo.put("user", "root");
		connInfo.put("password", "vnfhry46");
	}

	public static void main(String[] args) throws JSONException, IOException, InterruptedException, UnirestException,
			SQLException, NullPointerException {


		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/klines?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		out.println("first start");

		// информацию по доступным парам получаем только раз в сутки
		conn_paras = DriverManager.getConnection(
				"jdbc:mysql://localhost/klines?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		conn_last = DriverManager.getConnection(
				"jdbc:mysql://localhost/klines?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC", connInfo);
		// ниже метод обновления списка существующих пар
		//getparas(); System.exit(0); //этот метод запускается только раз в сутки после truncate таблицы paras

		//interval = "1d";
		//interval = "1h";
		//interval = "1w";
		interval = "1M";
		
		// ниже основной метод сбора статистики
		getparasfrommysql();
		System.exit(0);

		if (args.length > 1)
			interval = args[1];
		if (args.length == 2) {
			switch (interval) {
			case "1h":
				limit = "24"; // часовые свечи за сутки
				break;
			case "1d":
				limit = "30"; // дневные свечи за месяц
				break;
			case "1w":
				limit = "26"; // недельные свечи за полгода
				break;
			case "1M":
				limit = "12"; // месячные свечи за год
				break;
			}
		} else if (args.length == 3) {
			limit = args[2];
		}
		out.println("интервал " + interval + ", лимит на " + limit + " свечей");

		if (args.length > 0) {
			para = args[0].toUpperCase();
			out.println("смотрим " + para);
			// checkklines(para);
		} else {
			out.println("без явного задания пары смотрим BTCUSDT");
			// checkklines("BTCUSDT");
		}
	}

	//здесь должен быть входной параметр long взятый из базы
	private static boolean getcurrenttime(long basemillis) {

		long currentmiisecunds = System.currentTimeMillis(); // 13 знаков
		long unixTime = currentmiisecunds / 1000L; // 10 знаков
		boolean needbin = false;
		
		Date currentDate = new Date(currentmiisecunds);
		DateFormat df_year = new SimpleDateFormat("YYYY");
		DateFormat df_month = new SimpleDateFormat("M");
		DateFormat df_week = new SimpleDateFormat("w");
		DateFormat df_day = new SimpleDateFormat("d");
		DateFormat df_hour = new SimpleDateFormat("H");
		
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
		
		out.println("текущий год: "+int_year);
		out.println("текущий месяц: "+int_month);
		out.println("текущая неделя: "+int_week);
		out.println("текущий день: "+int_day);
		out.println("текущий час: "+int_hour);
		
		out.println("");
		
		out.println("крайний год в базе: "+base_year);
		out.println("крайний месяц в базе: "+base_month);
		out.println("крайняя неделя в базе: "+base_week);
		out.println("крайний день в базе: "+base_day);
		out.println("крайний час в базе: "+base_hour);
		
		out.println("");
		
		switch (interval) {
			case "1M":
				if((int_year == base_year) & (int_month == base_month)) { out.println("месяц совпадает, бин не получаем"); } else { out.println("месяц НЕ совпадает, смотрим бин"); needbin = true; }
			break;
			case "1w":
				if((int_year == base_year) & (int_week == base_week)) { out.println("неделя совпадает, бин не получаем"); } else { out.println("неделя НЕ совпадает, смотрим бин"); needbin = true; }
			break;
			case "1d":
				if((int_year == base_year) & (int_day == base_day)) { out.println("день совпадает, бин не получаем"); } else { out.println("день НЕ совпадает, смотрим бин"); needbin = true; }
			break;
			case "1h":
				if((int_year == base_year) & (int_hour == base_hour)) { out.println("час совпадает, бин не получаем"); } else { out.println("час НЕ совпадает, смотрим бин"); needbin = true; }
			break;
		}

		// проверяем здесь https://www.unixtimestamp.com
		// на примере 1580515200000 - 01.02.2020
		// текущее значение 1582014462597
		out.println("");
		//out.println("Binance milliseconds: " + currentmiisecunds);
		//out.println("Обычный UnixTime: " + unixTime);
		// здесь нужно сравнивать интервал
		//out.println(needbin);
		
		return needbin;
	}

	private static void getparasfrommysql()
			throws SQLException, InterruptedException, NullPointerException, UnirestException {
		
		paranames = conn_paras.prepareStatement("SELECT para FROM paras where status = \"TRADING\"");
		if (paranames.execute()) {
			paranamesresult = paranames.getResultSet();
			while (paranamesresult.next()) {
				Long lasttime = 0L;
				Long base_lasttime = 0L;
				para = paranamesresult.getString("para");
				out.println("тянем пару " + para);

				//здесь нужно обязательно учитывать статус TRADING, иначе будут инсертиться дубликаты, п.ч. последняя статистика ушедшей пары не будет совпадать с текущим временем
				String mysqllast = "SELECT * FROM klines.kline_" + interval.toLowerCase() + " where para = \"" + para
						+ "\" order by open_milliseconds desc LIMIT 1";
				
				out.println(mysqllast);
				paralast = conn_last.prepareStatement(mysqllast);
				if (paralast.execute()) {
					paralastresult = paralast.getResultSet();
					while (paralastresult.next()) {
						base_lasttime = Long.parseLong(paralastresult.getString("open_milliseconds"));
						// здесь вычисляется последняя миллисекунда в статистике из базы и специально
						// инкрементируется, чтобы повторно не вставлять уже имеющиеся данные
						lasttime += 0000000000001L;
						//out.println(lasttime);
					}
				}

				// здесь нужно проверять текущее время по интервалу и если оно не вышло, то не
				// посылаем лишний запрос на бин
				// сначала достаем текущее время
				if(getcurrenttime(base_lasttime)) checkklines(para,lasttime);
				// пока только имитируем запрос к бину
				//testcheckklines(para,lasttime);
			}
		}
	}

    private static void testcheckklines(String para2, Long lasttime) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(500); // здесь задержка в полсекунды; наверно, чтобы по api не заблочили
		out.println("");
		out.println("Вызываем пару "+para2+ " с милями "+lasttime+ " и интервалом "+interval);
		out.println("");
	}

	//startTime 1580515200001 соответствует 1 февраля 2020 года. Для unixtime нужно убавлять 3 посл.символа, т.е. 1580515200
	private static void checkklines(String para, Long starttime)
			throws UnirestException, NullPointerException, InterruptedException {
		
		TimeUnit.MILLISECONDS.sleep(500); // здесь задержка в полсекунды; наверно, чтобы по api не заблочили
		out.println("Вызываем пару "+para+ " с милями "+starttime+ " и интервалом "+interval);

		url_api = "https://api.binance.com/api/v1/klines?symbol=";
		url_api += para + "&interval=" + interval + "&limit=" + limit;
		if (starttime > 0)
			url_api += "&startTime=" + starttime;
		HttpResponse<JsonNode> request = Unirest.get(url_api).asJson();
		JSONArray results = request.getBody().getArray();

		out.println(url_api);
		// TimeUnit.SECONDS.sleep(1);
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
				st = conn.createStatement();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			insmysql = "INSERT INTO `kline_" + interval.toLowerCase()
					+ "` (`para`, `time_open`, `time_close`, `price_open`, `max_price`, `low_price`, `price_close`, `volume`, `count_trades`, `open_milliseconds`) VALUES ";
			time_open_norm = convertSecondsToHMmSs(time_open);
			time_close_norm = convertSecondsToHMmSs(time_close);
			time_open_int = (int) (time_open / 1000);

			print_final = "Открытие в " + time_open_norm;
			print_final += ", откр цена: " + price_open;
			print_final += ", макс.цена: " + max_price;
			print_final += ", мин.цена: " + low_price;
			print_final += ", закр цена : " + price_close;
			print_final += ", объем: " + volume;
			print_final += ", закрытие в " + time_close_norm;
			// print_final += ", квота ордера: "+quote_asset_volume;
			print_final += ", сделки: " + count_trades;

			out.println(print_final);

			insmysql += "(\"" + para + "\",\"" + time_open_norm + "\",\"" + time_close_norm + "\",\"" + price_open
					+ "\",\"" + max_price + "\",\"" + low_price + "\",\"" + price_close + "\",\"" + volume + "\",\""
					+ item.get(7).toString() + "\"," + time_open + ");";
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
		res.forEach(item -> {
			JSONObject obj = (JSONObject) item;
			String para = obj.getString("symbol").intern();
			String status = obj.getString("status").intern();
			String quoteAsset = obj.getString("quoteAsset").intern();
			String baseAsset = obj.getString("baseAsset").intern();
			if (status == "TRADING" && (quoteAsset == "USDT" || quoteAsset == "BTC" || quoteAsset == "RUB")) {
				showparas.add(para); // получаем только пары живые с торгами
				insmysql_paras = "INSERT INTO `paras` (`para`, `status`, `quoteAsset`, `baseAsset`) VALUES ";
				insmysql_paras += "(\"" + para + "\",\"" + status + "\",\"" + quoteAsset + "\",\"" + baseAsset + "\");";
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
