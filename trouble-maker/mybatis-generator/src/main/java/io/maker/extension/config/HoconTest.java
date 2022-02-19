package io.maker.extension.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * HOCON（Human-Optimized Config Object Notation）是一个易于使用的配置文件格式，具有面向对象风格
 */
public class HoconTest {

    //如需从res目录默认加载，则必须命名为application.conf
    private static final Config config = ConfigFactory.load();
    public static void main(String[] args) {
        System.out.println("Start Reading HOCON file:");
        String date_user_name = config.getString("productDatabase.user");
        System.out.println("date_user_name:" + date_user_name);
        String data_user_pass = config.getString("productDatabase.password");
        System.out.println("data_user_pass:" + data_user_pass);
        String driver = config.getString("productDatabase.diver");
        System.out.println("data_user_pass:" + driver);
        String jdbcurl = config.getString("productDatabase.jdbcurl");
        System.out.println("data_user_pass:" + jdbcurl);
    }

}

//    一个 key 是一个键值对字符串中的前一个值
//    一个 value 可以是字符串、数字、对象、数组或者布尔值并紧随 key 的后面
//    一个 key-value separator 把键和值分离，可以是 : 或者 =

//    HOCON(Human-Optimized Config Object Notation)是一个易于使用的配
//    置文件格式。它被用于 Sponge 以及利用 Sponge API 的独立插件以储存重要的数据，
//    比如配置或者玩家数据。HOCON 文件通常以 .conf 作为后缀名。
