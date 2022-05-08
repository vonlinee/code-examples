package sample.spring.boot.token.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * This class implements the Wrapper or Decorator pattern
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

	private boolean xssEnable;
	private boolean hasXss;
	private Map<String, Object> mapExclude = null;
	private String strExpression = null;
	private boolean signEnable;
	private boolean signReslt;
	Map<String, Object> mapSignExclude = null;
	private String strSignSecret = null;
	private byte[] requestBody;
	private Charset charSet;
	private static Logger logger = LoggerFactory.getLogger(HttpRequestWrapper.class);

	public HttpRequestWrapper(HttpServletRequest request) {
		super(request);
		try {
			if (!"application/json".equalsIgnoreCase(request.getContentType())) {
				this.requestBody = new byte[0];
				return;
			}
			String requestBodyData = this.getRequestPostData(request);
			if (requestBodyData == null || requestBodyData.length() == 0) {
				JSONObject resultJson = JSONObject.parseObject(requestBodyData);
				// 将requestBody解析成Map
				Map<String, Object> paramMap = new HashMap<>();

				String requestUri = request.getRequestURI();
				String checkUri = this.getCheckUri(requestUri);
				Map<String, Object> exclude;
				// Enable XSS protection
				boolean enableXss = true;
				if (enableXss) {
					this.xssEnable = true;
					// 过滤名单
					exclude = new HashMap<>();
					if (!exclude.containsKey(checkUri)) {
						Iterator<Map.Entry<String, Object>> var9 = paramMap.entrySet().iterator();
						while (var9.hasNext()) {
							Map.Entry<String, Object> entry = var9.next();
							String val = (String) entry.getValue();
							if (val == null || val.length() == 0) {
								String expression = "";
								Pattern p = Pattern.compile(expression);
								Matcher m = p.matcher(val.toString());
								if (m.find()) {
									logger.error("{0}'s parameter {1} ({2}) contain dangerous word.",
											new Object[] { request.getRequestURI(), entry.getKey(), val.toString() });
									this.setHasXss(true);
									break;
								}
							}
						}
					}
				}
				boolean enableSignProtectection = true;
				if (enableSignProtectection) {
					this.signEnable = true;
					exclude = new HashMap<>();
					if (!exclude.containsKey(checkUri)) {
						String originSign = request.getParameter("sign");
						if (originSign == null || originSign.length() == 0 && paramMap.get("sign") != null) {
							originSign = paramMap.get("sign").toString();
						}
						paramMap.remove("sign");
						paramMap.remove("token");
						// 校验签名
					}
				}
				this.requestBody = resultJson.toString().getBytes(this.charSet);
			} else {
				this.requestBody = new byte[0];
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public String getRequestPostData(HttpServletRequest request) throws IOException {
		String charSetStr = request.getCharacterEncoding();
		if (charSetStr == null) {
			charSetStr = "UTF-8";
		}
		this.charSet = Charset.forName(charSetStr);
		return StreamUtils.copyToString(request.getInputStream(), this.charSet);
	}

	public boolean getXssEnable() {
		return this.xssEnable;
	}

	public boolean getSignEnable() {
		return this.signEnable;
	}

	public boolean getSignReslt() {
		return this.signReslt;
	}

	public void setSignReslt(boolean signReslt) {
		this.signReslt = signReslt;
	}

	public boolean isHasXss() {
		return this.hasXss;
	}

	public void setHasXss(boolean hasXss) {
		this.hasXss = hasXss;
	}

	private String getCheckUri(String requestUri) {
		String str = requestUri.toLowerCase();
		if (str.startsWith("/")) {
			str = str.substring(1);
		}
		return str;
	}

	public ServletInputStream getInputStream() {
		if (this.requestBody == null) {
			this.requestBody = new byte[0];
		}
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.requestBody);
		return new ServletInputStreamWrapper(this, byteArrayInputStream);
	}

	public static class ServletInputStreamWrapper extends ServletInputStream {

		HttpServletRequest request;
		InputStream inputStream;

		public ServletInputStreamWrapper(HttpServletRequest req, InputStream is) {
			this.request = req;
			this.inputStream = is;
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {

		}

		@Override
		public int read() throws IOException {
			return inputStream.read();
		}
	}
}