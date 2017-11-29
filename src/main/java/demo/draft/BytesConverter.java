package demo.draft;

/**
 * 将byte数组转换为字符串，思路来自十六进制的转换
 * 
 * @author Anmt
 * @mail anmtme@gmail.com
 * @blog http://anmt.me
 */
public class BytesConverter {
	/**
	 * 16进制进制表
	 */
	public static final String XTABLE16 = "0123456789ABCDEF";

	/**
	 * 36进制进制表
	 */
	public static final String XTABLE36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * 62进制进制表
	 */
	public static final String XTABLE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	/**
	 * 68进制进制表
	 */
	public static final String XTABLE68 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	
	/**
	 * 94进制进制表
	 */
	public static final String XTABLE94 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	
	/**
	 * 当前的进制表
	 */
	private String xTable;
	
	/**
	 * 到x进1，x进制
	 */
	private long x;

	/**
	 * 默认的xTable是正常的16进制的， 即0123456789ABCDEF， 所以默认的xTable是正常十六进制的转换。
	 */
	public BytesConverter() {
		this.xTable = XTABLE16;
		this.x = xTable.length();
	}

	/**
	 * xTable中的字符可以从26个大小写字母中，和10个数字中随机取。xTable是从左向右开始的，最左边代表的十进制是0，依次下去。
	 * @param xTable
	 */
	public BytesConverter(String xTable) {
		this.xTable = xTable;
		this.x = xTable.length();
		// 如果x小于10，不管怎么样都抛异常，只能向高进制转换
		if( this.x < 16 ){
			throw new RuntimeException("xTable的长度不能小于16！");
		}
		// 如果xTable中存在相同的字符，则抛异常
		for (int i = 0; i < x; ++i) {
			if (xTable.indexOf(xTable.charAt(i), i + 1) != -1)
				throw new RuntimeException("xTable中不能存在重复字符！");
		}
	}

	/**
	 * 将单个十进制数转换为X进制数， 单个十进制数最大不能超过long，因此最大的X进制数也是有限制，如果输入的是负数，则会被做绝对值运算。
	 * @param tenNum 十进制数，必须是正数
	 * @return X进制数
	 */
	public String tenToX(long tenNum) {
		if (tenNum < 0) {
			tenNum = Math.abs(tenNum);
		}
		StringBuffer strb = new StringBuffer();
		// 初始化商
		// 每次条件的时候，求商并判断是否为0
		// 不为0，则求余并从xTable找到对应的字符append
		// 然后把商赋值给被除数，继续循环
		for (long quotient = 0; (quotient = tenNum / x) != 0; tenNum = quotient) {
			strb.append(xTable.charAt((int) (tenNum % x)));
		}
		// 当商为0的时候，应该还有一次余数，由于循环的原因，就直接跳出了，所以在此补上
		strb.append(xTable.charAt((int) (tenNum % x)));
		return strb.reverse().toString();
	}

	/**
	 * 将单个X进制数转换为十进制数。 输入一个X进制的“数字”，返回一个十进制的数字，进制转换时，依赖xTable字段。
	 * 注意，一个X进制的“数字”是有范围限制的，是long的最大值对应的X进制数，如果超出了这个范围，则会报异常。
	 * 在做转换时，如果X进制的值所依赖的xTable不是原来那个，返回值则为负数，但是为了混淆，会对负数做绝对值运算。
	 * @param xNum X进制数
	 * @return 十进制数
	 */
	public long xToTen(String xNum) {
		int xNumLen = xNum.length();
		long tenNum = 0;
		for (int i = 0; i < xNumLen; ++i) {
			tenNum += xTable.indexOf(xNum.charAt(xNumLen - 1 - i)) * Math.pow(x, i);
		}
		if (tenNum < 0) {
			tenNum = Math.abs(tenNum);
		}
		return tenNum;
	}

	/**
	 * 将byte数组转换为字符串
	 * @param input
	 * @return 
	 */
	public String toString(byte[] input) {
		StringBuffer strb = new StringBuffer();
		long tmpLong = 0;
		for (byte b : input) {
			// 排除负数的可能
			tmpLong = b & 255;
			// 判断其结果是否小于x，如果小于x的话，则补xTable的第一个字符（代表0的意思）
			strb.append((tmpLong < this.x ? this.xTable.charAt(0) : "") + this.tenToX(tmpLong));
		}
		return strb.toString();
	}
	
	/**
	 * 将符合规则的字符串转换为byte数组
	 * @param input
	 * @return
	 */
	public byte[] toBytes(String input) {
		int len = input.length()/2;
		byte[] result = new byte[len];
		for(int i = 0; i < len ; ++ i){
			result[i] = (byte) xToTen(input.substring(i*2, i*2+2));
		}
		return result;
	}
}
