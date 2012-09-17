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
import org.iplant.pipeline.client.json.Pipeline;
import org.iplant.pipeline.client.ui.SimpleLabel;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
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
		final HTML center = new HTML();
		center.setStyleName("start-block");
		if(pipeline.getName().equals("")){
		center.setHTML("<div style='padding-top:5px;background:none;'>Click here to edit name and description<div>");
		}else{
			String tempDesc = pipeline.getDescription();
			if(tempDesc.length()>30){
				tempDesc= tempDesc.substring(0, 27)+"...";
			}
			center.setHTML("<div style='padding-top:5px;background:none;'>"+pipeline.getName()+"<br>"+tempDesc+"<div>");
		}
		center.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				final PopupPanel descPanel = new PopupPanel();
				descPanel.setStyleName("pipeline-form");
				FlowPanel form = new FlowPanel();
				HTML nameL = new HTML("Name:");
				nameL.setStyleName("pipeline-label");
				final TextBox name = new TextBox();
				name.setText(pipeline.getName());
				form.add(nameL);
				form.add(name);
				
				HTML descL = new HTML("Description:");
				descL.setStyleName("pipeline-label");
				final TextArea desc = new TextArea();
				desc.setText(pipeline.getDescription());
				form.add(descL);
				form.add(desc);
				Button save = new Button("Save");
				Button cancel = new Button("Cancel");
				cancel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						descPanel.hide();
					}
				});
				save.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						pipeline.setName(name.getText());
						pipeline.setDescription(desc.getText());
						descPanel.hide();
						String tempDesc = pipeline.getDescription();
						if(tempDesc.length()>30){
							tempDesc= tempDesc.substring(0, 27)+"...";
						}
						center.setHTML("<div style='padding-top:5px;background:none;'>"+pipeline.getName()+"<br>"+tempDesc+"<div>");
					}
				});
				form.add(save);
				form.add(cancel);
				descPanel.add(form);
				descPanel.showRelativeTo(center);
			}
		});
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
