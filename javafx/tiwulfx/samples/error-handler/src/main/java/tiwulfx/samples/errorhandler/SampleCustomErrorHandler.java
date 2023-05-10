/*
 * Copyright (c) 2015, Panemu ( http://www.panemu.com/ )
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tiwulfx.samples.errorhandler;

import com.panemu.tiwulfx.common.ExceptionHandler;
import com.panemu.tiwulfx.common.ExceptionHandlerFactory;
import com.panemu.tiwulfx.dialog.MessageDialog;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import javafx.stage.Window;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author amrullah
 */
public class SampleCustomErrorHandler implements ExceptionHandlerFactory {

	private Log log = LogFactory.getLog(SampleCustomErrorHandler.class);

	@Override
	public ExceptionHandler createExceptionHandler() {
		return new ExceptionHandler() {

			@Override
			public void handleException(Throwable throwable, Window window) {
				log.error(throwable.getMessage(), throwable);
				MessageDialogBuilder.error()
						  .buttonType(MessageDialog.ButtonType.YES_NO_CANCEL)
						  .yesOkButtonText("Stacktrace")
						  .noButtonText("Report")
						  .cancelButtonText("Close")
						  .message("This is custom error handler."
									 + "\n\nYou got this error:" + throwable.getMessage()
									 + "\n\nThis error is logged in console too").show(window);
			}
		};
	}

}
