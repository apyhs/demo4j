package demo.xml;

import kit4j.*;
import kit4j.jdbc.C3p0Conf;
import kit4j.jdbc.DbConf;
import kit4j.jdbc.DbKit;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CheckDomainDemo1 {
	
//域名查询结果有三个状态：
//210：可以注册
//211：已经注册
//212：参数错误
//214：未知错误
	public static void main(String[] args) {
		DbConf dbConf = new C3p0Conf(PropKit.load("conf.properties"));
		DbKit.init(dbConf.getDataSource());
		
		final String tableName = "domain_me";
		final String suffix = ".me";
		createTable(tableName);
		
		// abcdefghijklmnopqrstuvwxyz
		JKit.exhaustion(StrKit.list(
				StrKit.toList("abcdefghijklmnopqrstuvwxyz"),
				StrKit.toList("abcdefghijklmnopqrstuvwxyz"),
				StrKit.toList("abcdefghijklmnopqrstuvwxyz"))
				, new Function<Void, String>() {
					@Override
					public Void callback(String... args) {
						try {
							String nowDomain = args[0] + suffix;
							String data = HttpKit.get("http://panda.www.net.cn/cgi-bin/check.cgi?area_domain=" + nowDomain);
							Document document = DocumentHelper.parseText(data);
							Element root = document.getRootElement();
							String sql = "insert into " + tableName + " values(?, ?, ?, ?, ?)";
							String res = root.elementText("original");
							String result = res.contains("210") ? "可注册" : "已被注册";
							DbKit.update(sql, root.elementText("key"), TimeKit.ts(), root.elementText("returncode"), res, result);
							return null;
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				});
	}
	
	public static void createTable(String tableName) {
		String sql = "CREATE TABLE IF NOT EXISTS `" + tableName + "` (" + 
			" `domain` varchar(200) NOT NULL," + 
			" `ts` int(11) NOT NULL," + 
			" `status` varchar(50) NOT NULL," + 
			" `response` text NOT NULL," + 
			" `result` varchar(200) NOT NULL," + 
			" PRIMARY KEY (`domain`)" + 
			") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		DbKit.update(sql);
	}
}
