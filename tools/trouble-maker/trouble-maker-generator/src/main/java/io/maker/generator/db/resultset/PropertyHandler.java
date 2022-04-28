package io.maker.generator.db.resultset;

public interface PropertyHandler {

	/**
	 * Test whether this <code>PropertyHandler</code> wants to handle setting
	 * <code>value</code> into something of type <code>parameter</code>.
	 *
	 * @param parameter The type of the target parameter.
	 * @param value     The value to be set.
	 * @return true is this property handler can/wants to handle this value; false
	 *         otherwise.
	 */
	boolean match(Class<?> parameter, Object value);

	/**
	 * Do the work required to store <code>value</code> into something of type
	 * <code>parameter</code>. This method is called only if this handler responded
	 * <code>true</code> after a call to {@link #match(Class, Object)}.
	 *
	 * @param parameter The type of the target parameter.
	 * @param value     The value to be set.
	 * @return The converted value or the original value if something doesn't work
	 *         out.
	 */
	Object apply(Class<?> parameter, Object value);
}
