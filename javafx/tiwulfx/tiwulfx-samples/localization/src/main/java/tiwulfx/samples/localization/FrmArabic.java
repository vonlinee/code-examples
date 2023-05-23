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
package tiwulfx.samples.localization;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 *
 * @author amrullah
 */
public class FrmArabic extends StackPane {

	@FXML
	private Text txtOnly;
	@FXML 
	private Label lblOnly1;
	@FXML 
	private Label lblOnly2;
	@FXML 
	private Label lblOnly3;
	@FXML
	private Text txtOverride;
	@FXML 
	private Label lblOverride1;
	@FXML 
	private Label lblOverride2;
	@FXML 
	private Label lblOverride3;
	@FXML
	private Text txtUseDefault;
	@FXML 
	private Label lblUseDefault1;
	@FXML 
	private Label lblUseDefault2;
	@FXML 
	private Label lblUseDefault3;

	public FrmArabic() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FrmLocalization.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.setResources(TiwulFXUtil.getLiteralBundle());
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		init();
	}

	private void init() {
		txtOnly.setText("متوفر فقط باللغة العربية");
		lblOnly1.setText(TiwulFXUtil.getLiteral("arb"));
		lblOnly2.setText(TiwulFXUtil.getLiteral("arb2"));
		lblOnly3.setText(TiwulFXUtil.getLiteral("arb3"));
		
		txtOverride.setText("Override Default Literal");
		lblOverride1.setText(TiwulFXUtil.getLiteral("s1"));
		lblOverride2.setText(TiwulFXUtil.getLiteral("s2"));
		lblOverride3.setText(TiwulFXUtil.getLiteral("s3"));
		
		txtUseDefault.setText("Use Default Literal (If not found in literal_ar.properties)");
		lblUseDefault1.setText(TiwulFXUtil.getLiteral("default1"));
		lblUseDefault2.setText(TiwulFXUtil.getLiteral("default2"));
		lblUseDefault3.setText(TiwulFXUtil.getLiteral("default3"));
	}

}
