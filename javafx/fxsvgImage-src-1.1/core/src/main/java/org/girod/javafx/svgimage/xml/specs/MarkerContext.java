/*
Copyright (c) 2022, Hervé Girod
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

Alternatively if you have any questions about this project, you can visit
the project website at the project page on https://github.com/hervegirod/fxsvgimage
 */
package org.girod.javafx.svgimage.xml.specs;

import javafx.scene.Node;
import javafx.scene.paint.Paint;

/**
 * The context of the usage of a marker element.
 *
 * @since 1.0
 */
public class MarkerContext {
   private Node contextNode = null;
   private MarkerSpec markerStart = null;
   private MarkerSpec markerMid = null;
   private MarkerSpec markerEnd = null;
   private Paint contextFill = null;
   private Paint contextStroke = null;

   public MarkerContext() {
   }

   public boolean isEmpty() {
      return markerStart == null && markerMid == null && markerEnd == null;
   }

   public void setMarkerStart(MarkerSpec markerStart) {
      this.markerStart = markerStart;
   }

   public boolean hasMarkerStart() {
      return markerStart != null;
   }

   public MarkerSpec getMarkerStart() {
      return markerStart;
   }

   public void setMarkerMid(MarkerSpec markerMid) {
      this.markerMid = markerMid;
   }

   public boolean hasMarkerMid() {
      return markerMid != null;
   }

   public MarkerSpec getMarkerMid() {
      return markerMid;
   }

   public boolean hasMarkerEnd() {
      return markerEnd != null;
   }

   public MarkerSpec getMarkerEnd() {
      return markerEnd;
   }

   public void setMarkerEnd(MarkerSpec markerEnd) {
      this.markerEnd = markerEnd;
   }

   public void setContextFill(Paint fill) {
      this.contextFill = fill;
   }

   public Paint getContextFill() {
      return contextFill;
   }

   public void setContextNode(Node node) {
      this.contextNode = node;
   }

   public Node getContextNode() {
      return contextNode;
   }

   public void setContextStroke(Paint stroke) {
      this.contextStroke = stroke;
   }

   public Paint getContextStroke() {
      return contextStroke;
   }
}
