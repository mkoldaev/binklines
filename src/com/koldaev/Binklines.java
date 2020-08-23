package com.koldaev;

import java.io.IOException;
import java.sql.SQLException;
import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;
import redis.clients.jedis.exceptions.JedisException;

import static java.lang.System.out;

//максимум три аргумента в настройках запуска eclipse: 4h eosusdt yes
//если задан третий с любым словом, то после обработки, происходит выход
public class Binklines extends Fundament {

	public static void main(String[] args) throws JSONException, IOException, InterruptedException, UnirestException, SQLException, NullPointerException, JedisException {

		//подготавливаем коннекторы к базе
		setconns();

		if (args.length > 0) {
			interval = args[0];
			out.println("Задан аргумент интервала: "+interval);
		} else {
			out.println("Аргумент интервала не задан");
			out.println("Входим из программы");
			System.exit(0);
		}
		if (args.length > 1) {
			apipara = args[1].toUpperCase();
			out.println("Задан аргумент пары: "+apipara);
		}
		if (args.length > 2) {
			out.println("Задан аргумент обновления таблиц с парами");
			getparas(); //этот метод запускается только раз в сутки после truncate таблицы paras
			out.println("Входим из программы");
			System.exit(0);
		}

		
		// ниже основной метод сбора статистики
		getparasfrommysql();
		System.exit(0);

	}

}
