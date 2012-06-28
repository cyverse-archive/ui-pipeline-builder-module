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

import org.iplant.pipeline.client.json.Input;
import org.iplant.pipeline.client.json.Pipeline;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class PipelineCreator extends Composite {

	PipelineWorkspace workspace;
	HorizontalPanel main = new HorizontalPanel();
	private int pipelineId = 0;
//	private InputsTable inputs;
	private int inputsInt = -1;

	public PipelineCreator() {
		workspace = new PipelineWorkspace(new Pipeline("", "", false, 0));
		workspace.setHeight("100%");
		workspace.setWidth("100%");
		main.setStyleName("pipe-table");
		main.add(workspace);
		main.setCellHeight(workspace, "100%");
		main.setHeight("100%");
//		inputs = new InputsTable(false);
//		inputs.getScrollPane().setHeight("500px");
		initWidget(main);
	}



//	public void setupInputs() {
//		HorizontalPanel bar = new HorizontalPanel();
//		inputs.setData(workspace.getPipeline().getInputs());
//		bar.add(new ImgButton(Resources.INSTANCE.add(), "Add new Input").setClickHandler(new ClickHandler() {
//			public void onClick(ClickEvent event) {
//				Input newInput = new Input();
//				newInput.setId(inputsInt--);
//				newInput.setName("Untitled " + ((inputsInt * -1) - 1));
//				addInput(newInput);
//			}
//
//		}));
//		bar.add(new Seprator());
//		SC.ask("Setup your inputs", inputs,bar,new ValueListener<Boolean>() {
//			@Override
//			public void returned(Boolean ret) {
//				if(ret){
//					//save the inputs back to the pipeline
//					workspace.saveInputs(inputs.getData());
//				}
//			}
//		});
//	}

	private void addInput(Input input) {
//		inputs.addInput(input);
	}

	/**
	 * @param result
	 */
	private void loadPipeline(Pipeline result) {
		main.remove(workspace);
		workspace = new PipelineWorkspace(result);
		workspace.setHeight("100%");
		workspace.setWidth("100%");
		main.insert(workspace, 0);
	}

}
