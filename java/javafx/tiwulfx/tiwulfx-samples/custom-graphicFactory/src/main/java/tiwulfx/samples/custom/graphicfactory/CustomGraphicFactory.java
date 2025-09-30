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
package tiwulfx.samples.custom.graphicfactory;

import com.panemu.tiwulfx.common.GraphicFactory;
import javafx.scene.Node;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;

/**
 *
 * @author Heru Tri Julianto <heru.trijulianto@panemu.com>
 */
public class CustomGraphicFactory extends GraphicFactory {

	/**
	 * custom button icon using frontawesomeFX by Jens Deters
	 *
	 * @return
	 */
	@Override
	public Node createAddGraphic() {
		FontAwesomeIcon fai = new FontAwesomeIcon();
		fai.setIcon(FontAwesomeIcons.PLUS_CIRCLE);
		fai.setGlyphStyle("-fx-fill: linear-gradient(#70b4e5 0%, #247cbc 70%, #2c85c1 85%);");
		fai.setSize("22");
		return fai;
	}

	@Override
	public Node createReloadGraphic() {
		FontAwesomeIcon fai = new FontAwesomeIcon();
		fai.setIcon(FontAwesomeIcons.REFRESH);
		fai.setGlyphStyle("-fx-fill: linear-gradient(to bottom, #b4e391 0%,#61c419 50%,#b4e391 100%);");
		fai.setSize("22");
		return fai;
	}

	@Override
	public Node createEditGraphic() {
		FontAwesomeIcon fai = new FontAwesomeIcon();
		fai.setIcon(FontAwesomeIcons.PENCIL);
		fai.setGlyphStyle("-fx-fill: linear-gradient(to bottom, #f0b7a1 0%,#8c3310 50%,#752201 51%,#bf6e4e 100%);");
		fai.setSize("22");
		return fai;
	}

}
