package com.github.kahlkn.demo._function.inetlocation;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Internet Protocol (IP) Location，即IP的物理地址，基于GeoLite数据库
 */
public class InetLocation {
	
	/**
	 * 语言：de、pt-BR、fr、en、ru、zh-CN、es、ja
	 */
	private String lang;

	/**
	 * IP数据库的路径
	 */
	private File dbPath;

	/**
	 * 对应的IP地址
	 */
	private InetAddress ip;

	/**
	 * 国家
	 */
	private Country country;

	/**
	 * 区域
	 */
	private Subdivision subdivision;

	/**
	 * 城市
	 */
	private City city;

	/**
	 * 邮编
	 */
	private Postal postal;

	/**
	 * 经纬度
	 */
	private Location location;
	
	/**
	 * 摘要
	 */
	private String summary;

	public String getLang() {
		return lang;
	}

	public File getDbPath() {
		return dbPath;
	}

	public InetAddress getIp() {
		return ip;
	}

	public Country getCountry() {
		return country;
	}

	public Subdivision getSubdivision() {
		return subdivision;
	}

	public City getCity() {
		return city;
	}

	public Postal getPostal() {
		return postal;
	}

	public Location getLocation() {
		return location;
	}

	public String getSummary() {
		return summary;
	}

	public static InetLocation getInetLocation(String ip) throws IOException, GeoIp2Exception{
		return new InetLocation(ip, new File("ip.mmdb"), "en");
	}

	public static InetLocation getInetLocation(String ip, String lang) throws IOException, GeoIp2Exception{
		return new InetLocation(ip, new File("ip.mmdb"), lang);
	}

	public static InetLocation getInetLocation(String ip, File dbPath) throws IOException, GeoIp2Exception{
		return new InetLocation(ip, dbPath, "en");
	}

	public InetLocation(String ip, File dbPath, String lang) throws IOException, GeoIp2Exception{
		// http://dev.maxmind.com/geoip/geoip2/geolite2/
		this.ip = InetAddress.getByName(ip);
		this.dbPath = dbPath;
		this.lang = lang;
		DatabaseReader dbReader = new DatabaseReader.Builder(this.dbPath).build();
		CityResponse response = dbReader.city(this.ip);
		this.country = response.getCountry();
		this.subdivision = response.getMostSpecificSubdivision();
		this.city = response.getCity();
		this.postal = response.getPostal();
		this.location = response.getLocation();
		this.summary = String.format("%s%s%s%s%s%s%s", 
				this.isNotBlank(this.country.getNames().get(this.lang), "", ""), 
				this.isNotBlank(this.country.getIsoCode(), "[", "]"), 
				this.isNotBlank(this.subdivision.getNames().get(this.lang), " ", ""), 
				this.isNotBlank(this.subdivision.getIsoCode(), "[", "]"), 
				this.isNotBlank(this.city.getNames().get(this.lang), " ", " "), 
				this.isNotBlank(this.location.getLatitude().toString(), "(", ","),
				this.isNotBlank(this.location.getLongitude().toString(), " ", ")"));
	}

	@Override
	public String toString() {
		return this.summary;
	}

	private Boolean isNotBlank(String str){
		return (str != null) && (str.trim().length() > 0);
	}

	private String isNotBlank(String str, String prefix, String suffix){
		return this.isNotBlank(str) ? prefix + str + suffix : "";
	}

}
