package me.dwliu.framework.core.tool.bean;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * https://adongs.com/articles/2020/03/20/1584696128501.html
 *
 * @author liudw
 * @date 2023/5/5 21:55
 **/
public class BodyReaderRequestWrapper extends HttpServletRequestWrapper {
	private final byte[] body;

	/**
	 * @param request
	 */
	public BodyReaderRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		body = StreamUtils.copyToByteArray(request.getInputStream());
	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() {
		final ByteArrayInputStream byteArrayIns = new ByteArrayInputStream(body);
		ServletInputStream servletIns = new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}

			@Override
			public int read() {
				return byteArrayIns.read();
			}
		};
		return servletIns;
	}
}
