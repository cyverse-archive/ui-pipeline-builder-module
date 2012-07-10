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

import org.iplant.pipeline.client.json.App;
import org.iplant.pipeline.client.json.IPCType;
import org.iplant.pipeline.client.json.Input;
import org.iplant.pipeline.client.json.Output;

import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Image;


public class DragCreator {

	private static IPCType draggedRecord;
	public static final int MOVE = 1;
	public static final int COPY = 2;
	public static final int DELETE = 3;

	public static native void addDrag(Element element, IPCType rec, DragListener listener) /*-{
		function handleDragStart(e) {
			var dragIcon = listener.@org.iplant.pipeline.client.dnd.DragListener::getDragImage(Lorg/iplant/pipeline/client/json/IPCType;)(rec);
			e.dataTransfer.setDragImage(dragIcon, -10, -10);
			e.dataTransfer.effectAllowed = 'all'; // only dropEffect='copy' will be dropable
			@org.iplant.pipeline.client.dnd.DragCreator::draggedRecord = rec;
			listener.@org.iplant.pipeline.client.dnd.DragListener::dragStart(Lorg/iplant/pipeline/client/json/IPCType;)(rec);
			if (element.getAttribute("data-downloadurl") != null) {
				e.dataTransfer.setData("DownloadURL", element.getAttribute("data-downloadurl"));
			} else {
				e.dataTransfer.setData('text/plain', rec.@org.iplant.pipeline.client.json.IPCType::getId()()); // required otherwise doesn't work
			}
		}

		function handleDragOver(e) {
			if (e.preventDefault)
				e.preventDefault();
			listener.@org.iplant.pipeline.client.dnd.DragListener::dragOver(Lorg/iplant/pipeline/client/json/IPCType;)(rec);

			//e.dataTransfer.dropEffect = 'move'; // See the section on the DataTransfer object.
			//this.style.border="1px dashed #84B4EA";
			return false;
		}

		function handleDragEnter(e) {
			// this / e.target is the current hover target.
			listener.@org.iplant.pipeline.client.dnd.DragListener::dragEnter(Lorg/iplant/pipeline/client/json/IPCType;)(rec);
			return false;
		}

		function handleDragLeave(e) {
			listener.@org.iplant.pipeline.client.dnd.DragListener::dragLeave(Lorg/iplant/pipeline/client/json/IPCType;)(rec);
		}
		function handleDrop(e) {
			if (e.stopPropagation)
				e.stopPropagation(); // stops the browser from redirecting...why???

			var data = e.dataTransfer.getData('text/plain');
			if(isNaN(data)){
				//item is an json app from iplant
				var obj = eval("("+data+")");
				var app =@org.iplant.pipeline.client.dnd.DragCreator::createApp(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
				@org.iplant.pipeline.client.dnd.DragCreator::draggedRecord =app; 
			}
			// this / e.target is current target element.
			listener.@org.iplant.pipeline.client.dnd.DragListener::drop(Lorg/iplant/pipeline/client/json/IPCType;)(rec);

			// See the section on the DataTransfer object.

			return false;
		}

		function handleDragEnd(e) {
			listener.@org.iplant.pipeline.client.dnd.DragListener::dragEnd(Lorg/iplant/pipeline/client/json/IPCType;)(rec);
		}

		element.addEventListener('dragstart', handleDragStart, false);
		element.addEventListener('dragenter', handleDragEnter, false);
		element.addEventListener('dragover', handleDragOver, false);
		element.addEventListener('dragleave', handleDragLeave, false);
		element.addEventListener('drop', handleDrop, false);
		element.addEventListener('dragend', handleDragEnd, false);
	}-*/;

	public static native void addDrop(Element element, IPCType rec, DropListener listener) /*-{

		function handleDragOver(e) {
			if(e.dataTransfer.getData('text/plain')!=''){
				alert(e.dataTransfer.getData('text/plain'));
				return;
			}
			
			
			listener.@org.iplant.pipeline.client.dnd.DropListener::dragOver(Lorg/iplant/pipeline/client/json/IPCType;)(rec);

			if (e.preventDefault) {
				e.preventDefault(); // Necessary. Allows us to drop.
			}

			//e.dataTransfer.dropEffect = 'move'; // See the section on the DataTransfer object.
			//this.style.border="1px dashed #84B4EA";
			return false;
		}

		function handleDragEnter(e) {
			// this / e.target is the current hover target.
			if(e.dataTransfer.getData('text/plain')!==''){
				alert(e.dataTransfer.getData('text/plain'));
				return;
			}
			listener.@org.iplant.pipeline.client.dnd.DropListener::dragEnter(Lorg/iplant/pipeline/client/json/IPCType;)(rec);
		}

		function handleDragLeave(e) {
			listener.@org.iplant.pipeline.client.dnd.DropListener::dragLeave(Lorg/iplant/pipeline/client/json/IPCType;)(rec);
		}
		function handleDrop(e) {
			// this / e.target is current target element.
			var data = e.dataTransfer.getData('text/plain');
			if(isNaN(data)){
				//item is an json app from iplant
				var obj = eval("("+data+")");
				var app =@org.iplant.pipeline.client.dnd.DragCreator::createApp(Lcom/google/gwt/core/client/JavaScriptObject;)(obj);
				@org.iplant.pipeline.client.dnd.DragCreator::draggedRecord =app; 
			}

			listener.@org.iplant.pipeline.client.dnd.DropListener::drop(Lorg/iplant/pipeline/client/json/IPCType;)(rec);

			if (e.stopPropagation) {
				e.stopPropagation(); // stops the browser from redirecting.
			}
	
			// See the section on the DataTransfer object.

			return false;
		}

		element.addEventListener('dragenter', handleDragEnter, false);
		element.addEventListener('dragover', handleDragOver, false);
		element.addEventListener('dragleave', handleDragLeave, false);
		element.addEventListener('drop', handleDrop, false);
	}-*/;

	public static IPCType getDragSource() {
		return draggedRecord;
	}

	private static App createApp(String name,String description,String id){
		App app = new App();
		app.setName(name);
		app.setDescription(description);
		app.setId(1);
		app.setID(id);
		return app;
	}
	private static App createApp(com.google.gwt.core.client.JavaScriptObject json){
		return createApp(new JSONObject(json));
	}
	public static App createApp(JSONObject json){
		App app = new App();
		app.setName(((JSONString)json.get("name")).stringValue());
		app.setDescription(((JSONString)json.get("description")).stringValue());
		app.setId(1);
		app.setID(((JSONString)json.get("id")).stringValue());
		JSONArray inputs = (JSONArray) json.get("inputs");
		app.setInputJson(inputs);
		for(int i=0;i<inputs.size();i++){
			Input input = new Input();
			JSONObject obj = (JSONObject) inputs.get(i);
			JSONObject dataObj = (JSONObject) obj.get("data_object");
			if(dataObj!=null){
				input.setName(((JSONString)dataObj.get("name")).stringValue());
				input.setDescription(((JSONString)dataObj.get("description")).stringValue());
				input.setId(1);
				input.setRequired(((JSONBoolean)dataObj.get("required")).booleanValue());
				input.setType("File:"+((JSONString)dataObj.get("format")).stringValue());
				input.setID(((JSONString)dataObj.get("id")).stringValue());
				app.addInput(input);
			}
		}
		JSONArray outputs = (JSONArray) json.get("outputs");
		app.setOutputJson(outputs);
		for(int i=0;i<outputs.size();i++){
			Output output = new Output();
			JSONObject obj = (JSONObject) outputs.get(i);
			JSONObject dataObj = (JSONObject) obj.get("data_object");
			if(dataObj!=null){
				output.setName(((JSONString)dataObj.get("name")).stringValue());
				output.setDescription(((JSONString)dataObj.get("description")).stringValue());
				output.setId(1);
				output.setType(((JSONString)dataObj.get("format")).stringValue());
				output.setID(((JSONString)dataObj.get("id")).stringValue());
				app.addOutput(output);
			}
		}
		
		return app;
	}
	
	public static Element getImageElement(String src) {
		Image img = new Image(src);
		img.setWidth("20px");
		img.setHeight("20px");
		return img.getElement();
	}
}
