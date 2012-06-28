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
package org.iplant.pipeline.client.dnd;

import org.iplant.pipeline.client.json.IPCType;

import com.google.gwt.dom.client.Element;


public interface DragListener {
	
	public void dragStart(IPCType record);
	public void dragEnter(IPCType record);
	public void dragOver(IPCType record);
	public void dragLeave(IPCType record);
	public void drop(IPCType record);
	public void dragEnd(IPCType record);
	public Element getDragImage(IPCType record);
	
}
