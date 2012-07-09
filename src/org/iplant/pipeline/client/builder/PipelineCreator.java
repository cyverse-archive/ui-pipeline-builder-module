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
package org.iplant.pipeline.client.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.iplant.pipeline.client.json.App;
import org.iplant.pipeline.client.json.Input;
import org.iplant.pipeline.client.json.PipeApp;
import org.iplant.pipeline.client.json.PipeComponent;
import org.iplant.pipeline.client.json.Pipeline;

import com.google.gwt.dev.jjs.ast.js.JsonArray;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PipelineCreator extends Composite {

	PipelineWorkspace workspace;
	HorizontalPanel main = new HorizontalPanel();

	// private int pipelineId = 0;
	// private InputsTable inputs;
	// private int inputsInt = -1;

	public PipelineCreator() {
		workspace = new PipelineWorkspace(new Pipeline("", "", false, 0));
		workspace.setHeight("100%");
		workspace.setWidth("100%");
		main.setStyleName("pipe-table");
		main.add(workspace);
		main.setCellHeight(workspace, "100%");
		main.setHeight("100%");
		// inputs = new InputsTable(false);
		// inputs.getScrollPane().setHeight("500px");
		initWidget(main);
	}

	// public void setupInputs() {
	// HorizontalPanel bar = new HorizontalPanel();
	// inputs.setData(workspace.getPipeline().getInputs());
	// bar.add(new ImgButton(Resources.INSTANCE.add(),
	// "Add new Input").setClickHandler(new ClickHandler() {
	// public void onClick(ClickEvent event) {
	// Input newInput = new Input();
	// newInput.setId(inputsInt--);
	// newInput.setName("Untitled " + ((inputsInt * -1) - 1));
	// addInput(newInput);
	// }
	//
	// }));
	// bar.add(new Seprator());
	// SC.ask("Setup your inputs", inputs,bar,new ValueListener<Boolean>() {
	// @Override
	// public void returned(Boolean ret) {
	// if(ret){
	// //save the inputs back to the pipeline
	// workspace.saveInputs(inputs.getData());
	// }
	// }
	// });
	// }

	// private void addInput(Input input) {
	// // inputs.addInput(input);
	// }

	// /**
	// * @param result
	// */
	// private void loadPipeline(Pipeline result) {
	// main.remove(workspace);
	// workspace = new PipelineWorkspace(result);
	// workspace.setHeight("100%");
	// workspace.setWidth("100%");
	// main.insert(workspace, 0);
	// }

	/**
	 * This will return the json that represents the pipeline that is being
	 * built.
	 * 
	 * @return the json in sting format of the new pipeline
	 */
	public JSONObject getPipelineJson() {
		JSONObject ret = new JSONObject();
		JSONArray appsArray = new JSONArray();
		Vector<PipeComponent> steps = workspace.getPipeline().getSteps();
		int i=0;
		for (PipeComponent step : steps) {
			App app = ((PipeApp) step).getApp();
			JSONObject jsonApp = new JSONObject();
			jsonApp.put("id", new JSONString(app.getID()));
			jsonApp.put("name", new JSONString(app.getName()));
			jsonApp.put("description", new JSONString(app.getDescription()));
			jsonApp.put("step", new JSONNumber(step.getPosition()));
			HashMap<PipeComponent, ArrayList<Input>> mappings = new HashMap<PipeComponent, ArrayList<Input>>();
			for (Input input : step.getInputs()) {
				if (input.getMapped() != null) {
					PipeComponent parent = input.getMapped().getParent();
					ArrayList<Input> maps = mappings.get(parent);
					if (maps == null)
						maps = new ArrayList<Input>();
					maps.add(input);
					mappings.put(parent, maps);
				}
			}
			JSONArray jsonMappings = new JSONArray();
			jsonApp.put("mappings", jsonMappings);
			Iterator<PipeComponent> it = mappings.keySet().iterator();
			int mappingsI =0;
			while (it.hasNext()) {
				PipeComponent mappedTo = it.next();
				App mappedApp = ((PipeApp) mappedTo).getApp();
				JSONObject jsonMap = new JSONObject();
				jsonMap.put("step", new JSONNumber(mappedTo.getPosition()));
				jsonMap.put("id", new JSONString(mappedApp.getID()));
				ArrayList<Input> inputs = mappings.get(mappedTo);
				int mapI=0;
				JSONArray jsonMapA = new JSONArray();
				for (Input input : inputs) {
					JSONObject mapO = new JSONObject();
					mapO.put(input.getID(), new JSONString(input.getMapped().getID() ));
					jsonMapA.set(mapI++, mapO);
				}
				jsonMap.put("map", jsonMapA);
				jsonMappings.set(mappingsI++, jsonMap);
			}
			appsArray.set(i++,jsonApp);
		}
		ret.put("apps", appsArray);
		return ret;
	}

}