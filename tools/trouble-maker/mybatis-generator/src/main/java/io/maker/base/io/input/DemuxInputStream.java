package io.maker.base.io.input;

import java.io.IOException;
import java.io.InputStream;

/**
 * TemplateData written to this stream is forwarded to a stream that has been
 * associated with this thread.
 *
 * @author <a href="mailto:peter@apache.org">Peter Donald</a>
 * @version $Revision: 437567 $ $Date: 2006-08-28 08:39:07 +0200 (Mo, 28 Aug
 *          2006) $
 */
public class DemuxInputStream extends InputStream {
	private InheritableThreadLocal<InputStream> m_streams = new InheritableThreadLocal<>();

	/**
	 * Bind the specified stream to the current thread.
	 *
	 * @param input the stream to bind
	 * @return the InputStream that was previously active
	 */
	public InputStream bindStream(InputStream input) {
		InputStream oldValue = getStream();
		m_streams.set(input);
		return oldValue;
	}

	/**
	 * Closes stream associated with current thread.
	 *
	 * @throws IOException if an error occurs
	 */
	public void close() throws IOException {
		InputStream input = getStream();
		if (null != input) {
			input.close();
		}
	}

	/**
	 * Read byte from stream associated with current thread.
	 *
	 * @return the byte read from stream
	 * @throws IOException if an error occurs
	 */
	public int read() throws IOException {
		InputStream input = getStream();
		if (null != input) {
			return input.read();
		} else {
			return -1;
		}
	}

	/**
	 * Utility method to retrieve stream bound to current thread (if any).
	 *
	 * @return the input stream
	 */
	private InputStream getStream() {
		return (InputStream) m_streams.get();
	}
}
