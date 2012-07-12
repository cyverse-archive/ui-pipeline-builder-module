/* * Copyright 2012 Oregon State University.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 */
package org.iplant.pipeline.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;

public class LabelButton extends Button{
	/**
	 * 
	 */
	public LabelButton(String label) {
		super(label);
		this.title=label;
		setStyleName("eta-label-button");
	}
	
	public void setStyleDependentName(String styleSuffix, boolean add) {
	}
	
	public Button setClickHandler(ClickHandler hand) {
		return this;
	}

	public void onMouseUp(MouseUpEvent event) {
	}

	public void onMouseDown(MouseDownEvent event) {
	}

	public void onClick(ClickEvent event) {
	}

	/**
	 * @return
	 */
	public String getText() {
		return title;
	}
}