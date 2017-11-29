package demo.draft;

/**
 * 错误码<br />
 * 设计思路：<br />
 * 0 ：　一定是成功<br />
 * 4开头　：　请求错误<br />
 * 5开头　：　服务器繁忙<br />
 * 个位为预留位，十位为变动位<br />
 */
public interface Code {
	
	/**
	 * 操作成功<br />
	 * 任何成功的操作，均返回此状态码<br />
	 */
	public static final Integer SUCCESS = 0;

	/**
	 * failure
	 */
	public static final Integer FAILURE = 1;
	
	/**
	 * 错误的请求<br />
	 * 未具体描述的错误请求，可以当作所有错误请求的超类<br />
	 */
	public static final Integer BAD_REQUEST = 40000;
	
	/**
	 * 服务器繁忙<br />
	 * 未具体描述的服务器繁忙，可以当作所有服务器繁忙的超类<br />
	 */
	public static final Integer SERVER_BUSY = 50000;
}
