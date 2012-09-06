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


import org.iplant.pipeline.client.SC;
import org.iplant.pipeline.client.dnd.DragCreator;
import org.iplant.pipeline.client.dnd.DropListener;
import org.iplant.pipeline.client.images.Resources;
import org.iplant.pipeline.client.json.App;
import org.iplant.pipeline.client.json.IPCType;
import org.iplant.pipeline.client.json.Input;
import org.iplant.pipeline.client.json.Output;
import org.iplant.pipeline.client.json.Pipeline;
import org.iplant.pipeline.client.json.PipelineApp;
import org.iplant.pipeline.client.ui.SimpleLabel;


import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PipelineWorkspace extends Composite {
	private Image trashImg = new Image(Resources.INSTANCE.trashClose().getSafeUri().asString());
	private Workspace workspace;
	private TextBox nameBox;
	private TextArea descBox;
	private Pipeline pipeline;
	private FlowPanel userInputs = new FlowPanel();
	public PipelineWorkspace(Pipeline pipeline) {
		FlowPanel pane = new FlowPanel();
		initWidget(pane);
		this.pipeline=pipeline;
		workspace = new Workspace(pipeline);
		
		loadNonBlocks();
//		workspace.add(new ForBlock());
		pane.add(workspace);
		pane.add(trashImg);
		VerticalPanel infoPane = new VerticalPanel();
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, new SimpleLabel("Name:"));
		nameBox = new TextBox();
		nameBox.setStyleName("eta-input2");
		nameBox.setText(pipeline.getName());
		table.setWidget(0, 1, nameBox);
		table.setWidget(1, 0, new SimpleLabel("Description:"));
		descBox = new TextArea();
		descBox.setText(pipeline.getDescription());
		descBox.setStyleName("eta-input2");
		descBox.setSize("", "100px");
		table.setWidget(2, 0, descBox);
		table.getFlexCellFormatter().setColSpan(1, 0, 2);
		table.getFlexCellFormatter().setColSpan(2, 0, 2);
		infoPane.setWidth("100%");
		table.setWidth("100%");
		infoPane.add(table);
//		pane.add(infoPane);
//		pane.setCellWidth(infoPane, "300px");
		PipelineApp temp=new PipelineApp(0, -1, -1);
		temp.setPipeline(new Pipeline("Foreach", "A foreach tool that will run the apps for every item in the input list", true, -2));
		temp.getInputs().add(new Input(-1, "List of items", "Items", "", "", true, 0, "Default", "List"));
		temp.getOutputs().add(new Output("Foreach Item", "", "The item in the forloop", "${splitList($'Items')}", -1));
		table.setWidget(5, 0, new FunctionMiniBlock(temp));
		
		PipelineApp temp2=new PipelineApp(0, -1, -1);
		temp2.setPipeline(new Pipeline("If", "A  tool that will run the apps based on the conditional", true, -3));
		temp2.getInputs().add(new Input(-1, "Condition", "Condition", "", "", true, 0, "Default", "List:File"));
		
		table.setWidget(5, 1, new FunctionMiniBlock(temp2));
		
		PipelineApp temp3=new PipelineApp(0, -1, -1);
		temp3.setPipeline(new Pipeline("Switch", "A  tool that will run the apps based a selection", true, -3));
		temp3.getInputs().add(new Input(-1, "switch", "switch", "", "", true, 0, "Default", "String"));
		table.setWidget(5, 2, new FunctionMiniBlock(temp3));
		
		PipelineApp casePipe = new PipelineApp(0, -1, -1);
		casePipe.setPipeline(new Pipeline("Case", "Used in a switch statement to represent the steps when the switch value is equal to this case", true, -3));
		table.setWidget(5, 3, new FunctionMiniBlock(casePipe));


		workspace.setStyleName("pipe-workspace");
		workspace.setHeight("100%");

		trashImg.setStyleName("trash");

		DragCreator.addDrop(trashImg.getElement(), new TrashCan(), new DropListener() {
			public void drop(IPCType record) {
				trashImg.setUrl(Resources.INSTANCE.trashClose().getSafeUri().asString());
				DragCreator.getDragSource().setDragAction(DragCreator.DELETE);
			}

			public boolean dragOver(IPCType record) {
				trashImg.setUrl(Resources.INSTANCE.trashOpen().getSafeUri().asString());
				return true;
			}

			public void dragLeave(IPCType record) {
				trashImg.setUrl(Resources.INSTANCE.trashClose().getSafeUri().asString());
			}

			public boolean dragEnter(IPCType record) {
				trashImg.setUrl(Resources.INSTANCE.trashOpen().getSafeUri().asString());
				return true;
			}

		});	

	}


	public void removeBlock(Block block) {
		workspace.remove(block);
	}



	public void add(Widget wid) {
		workspace.add(wid);
	}

	public void loadPipeline(Pipeline pipeline) {
		this.pipeline = pipeline;
		loadNonBlocks();
		workspace.loadPipeline(pipeline);
		userInputs.clear();
		
	}

	private void loadNonBlocks() {
		HTML center = new HTML();
		center.setHeight("30px");
		center.setWidth("100px");
		center.setStyleName("start-block");
		center.setHTML("<div style='padding-top:5px;background:none;'>Start<div>");
		workspace.addNonBlock(center);
	}


	public void save() {
		pipeline=workspace.getPipeline();
		pipeline.setName(nameBox.getText());
		pipeline.setDescription(descBox.getText());
		if (pipeline.getName().equals("") || pipeline.getDescription().equals("")) {
			SC.alert("Error saving pipeline", "Sorry but this pipeline must have a name and a descritpion in order to save");
			return;
		}


	}


	public Pipeline getPipeline() {
		return pipeline;
	}


	public void appendApp(App app) {
		workspace.appendApp(app);
	}
}
