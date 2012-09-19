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

import org.iplant.pipeline.client.Resources;
import org.iplant.pipeline.client.dnd.DragCreator;
import org.iplant.pipeline.client.dnd.DropListener;
import org.iplant.pipeline.client.json.App;
import org.iplant.pipeline.client.json.IPCType;
import org.iplant.pipeline.client.json.Pipeline;
import org.iplant.pipeline.client.ui.SimpleLabel;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PipelineWorkspace extends Composite {
	private Image trashImg = new Image(Resources.INSTANCE.trashClose().getSafeUri().asString());
	private Workspace workspace;
	private TextBox nameBox;
	private TextBox descBox;
	private Pipeline pipeline;
	private FlowPanel userInputs = new FlowPanel();
	private SimpleLabel nameLabel;
	private SimpleLabel descLabel;

	public PipelineWorkspace(Pipeline pipeline) {
		FlowPanel pane = new FlowPanel();
		initWidget(pane);
		this.pipeline = pipeline;
		workspace = new Workspace(pipeline);

		// workspace.add(new ForBlock());
		pane.add(workspace);
		pane.add(trashImg);
		VerticalPanel infoPane = new VerticalPanel();
		FlexTable table = new FlexTable();
		table.setWidget(0, 0, new SimpleLabel("Name:"));
		nameBox = new TextBox();
		nameBox.setText(pipeline.getName());
		table.setWidget(0, 1, nameBox);
		table.setWidget(1, 0, new SimpleLabel("Description:"));
		descBox = new TextBox();
		descBox.setText(pipeline.getDescription());
		loadNonBlocks();
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
		final FlowPanel center = new FlowPanel();
		center.setStyleName("start-block");
		if (pipeline.getName().equals("")) {
			pipeline.setName("Click to edit name");
			pipeline.setDescription("and description");
		}
		nameLabel = new SimpleLabel(pipeline.getName());
		descLabel = new SimpleLabel(pipeline.getDescription());
		
		String nameT = pipeline.getName();
		if(nameT.length()>30){
			nameLabel.setToolTip(nameT);
			nameT= nameT.substring(0, 27)+"...";
		}
		nameLabel.setText(nameT);
		
		String descT = pipeline.getDescription();
		if(descT.length()>30){
			nameLabel.setToolTip(descT);
			descT= descT.substring(0, 27)+"...";
		}
		descLabel.setText(descT);
		
		
		center.add(nameLabel);
		center.add(descLabel);
		nameLabel.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				center.remove(0);
				nameBox.setText(pipeline.getName());
				center.insert(nameBox, 0);
				nameBox.setFocus(true);
			}
		}, ClickEvent.getType());

		nameBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				center.remove(0);
				pipeline.setName(nameBox.getText());
				String nameT = pipeline.getName();
				if(nameT.length()>30){
					nameLabel.setToolTip(nameT);
					nameT= nameT.substring(0, 27)+"...";
				}
				nameLabel.setText(nameT);
				center.insert(nameLabel, 0);
			}
		});
		nameBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					nameBox.setFocus(false);
				}
			}
		});
		
		descBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {
					descBox.setFocus(false);
				}
			}
		});
		descLabel.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				center.remove(1);
				descBox.setText(pipeline.getDescription());
				center.insert(descBox, 1);
				descBox.setFocus(true);
			}
		}, ClickEvent.getType());

		descBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				center.remove(1);
				String descT = descBox.getText();
				if(descT.length()>30){
					descLabel.setToolTip(descT);
					descT= descT.substring(0, 27)+"...";
				}
				descLabel.setText(descT);
				pipeline.setDescription(descBox.getText());
				center.insert(descLabel, 1);
			}
		});

		workspace.addNonBlock(center);
	}

	public Pipeline getPipeline() {
		return pipeline;
	}

	public void appendApp(App app) {
		workspace.appendApp(app);
	}
}
