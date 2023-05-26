package com.panemu.tiwulfx.common;

import javafx.stage.Window;

/**
 *
 * @author amrullah
 */
public interface ExceptionHandler {

	/**
	 * This method is called when there is uncatched error happens in TiwulFX
	 * components.
	 * <p>
	 * @param throwable the throwable
	 * @param window    the window on which a Message Dialog should put the
	 *                  modal. Usually it is where the error happens.
	 */
	void handleException(Throwable throwable, Window window);
}
