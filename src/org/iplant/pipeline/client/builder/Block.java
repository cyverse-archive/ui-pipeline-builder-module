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

import java.util.Vector;

import org.iplant.pipeline.client.dnd.DragCreator;
import org.iplant.pipeline.client.dnd.DragListener;
import org.iplant.pipeline.client.images.Resources;
import org.iplant.pipeline.client.json.IPCType;
import org.iplant.pipeline.client.json.Input;
import org.iplant.pipeline.client.json.Output;
import org.iplant.pipeline.client.json.PipeApp;
import org.iplant.pipeline.client.json.PipeComponent;
import org.iplant.pipeline.client.json.PipelineApp;
import org.iplant.pipeline.client.json.UserApp;
import org.iplant.pipeline.client.ui.Button;
import org.iplant.pipeline.client.ui.Seprator;


import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Block extends Composite implements DragListener {
	private PipeComponent app;
	private HorizontalPanel inputPanel;
	private HorizontalPanel outputPanel;
	HorizontalPanel startBlock = new HorizontalPanel();
	private BlockChangeListener listener;
	FlowPanel temp = new FlowPanel();
	private Workspace workspace;
	public Block(PipeComponent app, BlockChangeListener listener) {
		this.listener = listener;
		startBlock.setStyleName("block");
		if (app instanceof PipelineApp)
			startBlock.addStyleName("function");
		inputPanel = new HorizontalPanel();
		outputPanel = new HorizontalPanel();
		outputPanel.setVisible(false);
		inputPanel.setVisible(false);
		inputPanel.setStyleName("input-panel");
		outputPanel.setStyleName("output-panel");
		this.app = app;
		HTML expandInputs = new HTML();
		expandInputs.setStyleName("left-expand");
		expandInputs.setHTML("<div class='arrow2'></div>");
		startBlock.add(expandInputs);
		expandInputs.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				inputPanel.setVisible(!inputPanel.isVisible());
			}
		});

		startBlock.add(expandInputs);
		startBlock.add(inputPanel);
		HTML img = new HTML("<img src='"+Resources.INSTANCE.wrench().getSafeUri().asString()+"'/>");
		startBlock.add(img);
		img.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				edit();
			}
		});
		img.setStyleName("center");
		img.setHeight("30px");
		HTML center = new HTML();
		center.setHeight("30px");
		startBlock.add(center);
		center.setStyleName("center");
		center.setHTML("<div style='padding-top:5px;background:none;'>" + app.getName() + "<div>");

		startBlock.add(outputPanel);

		HTML expandOutputs = new HTML();
		expandOutputs.setStyleName("right-expand");
		expandOutputs.setHTML("<div class='arrow2'></div>");
		startBlock.add(expandOutputs);
		expandOutputs.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				outputPanel.setVisible(!outputPanel.isVisible());
			}
		});

		temp.add(new Arrow());
		temp.add(startBlock);
		initWidget(temp);
		center.getElement().setAttribute("draggable", "true");

		DragCreator.addDrag(center.getElement(), app, this);
		Vector<Input> inputs = app.getInputs();
		if (listener == null)
			return;
		Vector<PipeComponent> apps = listener.getPreviousApps(app.getPosition());
		outer: for (Input input : inputs) {
			if (input.getType().startsWith("File") || input.getType().startsWith("List")||app.getName().equals("Switch")) {
				InputBlock block = new InputBlock(input);
				inputPanel.add(block);
				if (!input.getValue().equals("")) {
					block.setInputValue(new Output(input.getValue(), "", "", input.getValue(), -1));
					continue outer;
				}
				if (input.getType().equals("File"))
					continue outer;
				for (PipeComponent wrap : apps) {
					for (Output output : wrap.getOutputs()) {
						if (input.getType().length() <= 5 || input.getType().substring(5).startsWith(output.getType())) {
							block.setInputValue(output);
							continue outer;
						}
					}
				}
			}
		}
		for (Output output : app.getOutputs()) {
			outputPanel.add(new OutputBlock(output));
		}

	}


	public void edit() {
		if (app instanceof PipeApp) {
			final PipeApp app = (PipeApp) this.app;
			HorizontalPanel bar = new HorizontalPanel();
			bar.add(new Seprator());
			bar.add(new Button("Job Options").setClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
//					options.show();
				}
			}));
			bar.add(new Seprator());
			bar.add(new OutputBlock(new Output("User Input", "", "Make the user fill this field out", "", -2)));
			bar.add(new Seprator());
//			Inputs inputs = new Inputs(app.getApp());
//			SC.ask("Configure inputs for step: " + app.getApp().getName(), inputs, bar, new ValueListener<Boolean>() {
//				public void returned(Boolean ret) {
//					if (ret) {
//						app.setJobOptions(options.getSpecs());
//					}
//				}
//			});
		} else if (app instanceof PipelineApp) {
			if(workspace==null){
			PipelineApp app = (PipelineApp) this.app;
			workspace = new Workspace(app.getPipeline());
			// temp.getElement().getStyle().setPosition(Position.ABSOLUTE);
			workspace.setStyleName("sub-workspace");
			
			temp.add(workspace);
			}else{
				temp.remove(workspace);
				workspace=null;
			}
		}
	}

	public void dragStart(IPCType record) {
		getElement().getStyle().setOpacity(.3);
	}

	public void dragEnter(IPCType record) {
	}

	public void dragOver(IPCType record) {
		IPCType rec = DragCreator.getDragSource();
		if (rec instanceof Output) {
			inputPanel.setVisible(true);
		} else if ((rec instanceof PipeComponent || rec instanceof UserApp) && !rec.equals(app)) {
			addStyleName("hoverO");
		}
	}

	public void dragLeave(IPCType record) {
		removeStyleName("hoverO");
	}

	public void drop(IPCType record) {
		getElement().getStyle().setOpacity(1);
		IPCType rec = DragCreator.getDragSource();

		if (rec instanceof PipeComponent) {
			// assume that the source is being moved
			PipeComponent src = (PipeComponent) rec;
			if(src.getPosition()>=0)
			listener.blockMoved(src, app.getPosition());
			else{
				listener.blockAdded(src, app.getPosition());
			}
		} else if (rec instanceof UserApp) {
			UserApp appU = (UserApp) rec;
			//TODO:when i get the json information add this app to the workspace
//			appService.getAppFromId(appU.getAppId(), new MyAsyncCallback<App>() {
//				@Override
//				public void success(App result) {
//					PipeApp wrap = new PipeApp(-1, result.getId(), app.getPosition());
//					wrap.setApp(result);
//					listener.blockAdded(wrap, app.getPosition());
//				}
//			});
		}
		removeStyleName("hoverO");

	}

	public void dragEnd(IPCType record) {
		getElement().getStyle().setOpacity(1);
		int action = record.getDragAction();
		if (action == DragCreator.DELETE) {
			listener.blockRemoved(app);
		}
		removeStyleName("hoverO");

	}

	public Element getDragImage(IPCType record) {
		return startBlock.getElement();
	}

}
