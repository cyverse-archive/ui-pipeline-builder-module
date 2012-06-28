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

import org.iplant.pipeline.client.SC;
import org.iplant.pipeline.client.ValueListener;
import org.iplant.pipeline.client.dnd.DragCreator;
import org.iplant.pipeline.client.dnd.DropListener;
import org.iplant.pipeline.client.json.IPCType;
import org.iplant.pipeline.client.json.PipeComponent;
import org.iplant.pipeline.client.json.Pipeline;
import org.iplant.pipeline.client.json.PipelineApp;
import org.iplant.pipeline.client.json.UserApp;


import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class Workspace extends FlowPanel implements DropListener, BlockChangeListener {
	private Vector<PipeComponent> wrappers;
	private int offset = 0;
	private Pipeline pipe;
	private int id = 0;
	private static int onId = 0;

	public Workspace(Pipeline pipeline) {
		id = onId++;
		loadPipeline(pipeline);
		pipe = pipeline;
		DragCreator.addDrop(getElement(), null, this);
	}

	public void dragEnter(IPCType recod) {
		IPCType record = DragCreator.getDragSource();
		if (record instanceof UserApp) {
			// addStyleName("hover");
		}
	}

	public void dragOver(IPCType recor) {
		IPCType record = DragCreator.getDragSource();
		if (record instanceof UserApp) {
			// addStyleName("hover");
		}
	}

	public void dragLeave(IPCType record) {
		removeStyleName("hover");
	}

	public void drop(IPCType reco) {
		IPCType record = DragCreator.getDragSource();
		
		
			if(pipe.getName().equals("Switch")){
				if(record instanceof PipelineApp){
					PipelineApp droping = (PipelineApp)record;
					if(droping.getName().equals("Case")){
						SC.ask("What is the value of the case?", new ValueListener<String>() {
							@Override
							public void returned(String ret) {
								if(ret!=null&& !ret.equals("")){
									PipelineApp casePipe = new PipelineApp(0, -1, -1);
									casePipe.setPipeline(new Pipeline("Case:"+ret, "", true, -1));
									wrappers.add(casePipe);
									add(new Block(casePipe, Workspace.this));
									for (int i = 0; i < wrappers.size(); i++) {
										wrappers.get(i).setPosition(i);
									}
								}
							}
						});
					}
				}
				removeStyleName("hover");
				return;
			}
		
		
		// if (record instanceof PipeComponent) {
		// PipeComponent wrap = (PipeComponent) record;
		// if (wrap.getPosition() == -1) {
		// // this is new and being dragged on
		// wrap.setPosition(wrappers.size());
		// wrappers.add(wrap);
		// add(FunctionBlock.getBlock(wrap, this));
		// } else {
		// // this is being moved handle it!
		// Widget wid = getWidget(wrap.getPosition() + offset);
		// remove(wrap.getPosition() + offset);
		// add(wid);
		// wrappers.remove(wrap.getPosition());
		// wrappers.add(wrap);
		// for (int i = 0; i < wrappers.size(); i++) {
		// wrappers.get(i).setPosition(i);
		// }
		// }
		// } else

		if (record instanceof UserApp) {
			UserApp wrapper = (UserApp) record;
			//TODO:replace this with the data from ipc app json
//			wrapperService.getAppFromId(wrapper.getAppId(), new MyAsyncCallback<App>() {
//				@Override
//				public void success(App result) {
//					PipeApp wrap = new PipeApp(-1, result.getId(), wrappers.size());
//					wrap.setApp(result);
//					wrappers.add(wrap);
//					add(new Block(wrap, Workspace.this));
//					for (int i = 0; i < wrappers.size(); i++) {
//						wrappers.get(i).setPosition(i);
//					}
//				}
//			});
		} else if (record instanceof PipeComponent) {
			// assume this is being moved and add to the bottom of the stack
			PipeComponent wrap = (PipeComponent) record;
			if (wrap.getPosition() >= 0) {
				Widget wid = getWidget(wrap.getPosition() + offset);
				remove(wrap.getPosition() + offset);
				add(wid);
				wrappers.remove(wrap.getPosition());
				wrappers.add(wrap);
				for (int i = 0; i < wrappers.size(); i++) {
					wrappers.get(i).setPosition(i);
				}
			} else {
				wrappers.add(wrap);
				add(new Block(wrap, Workspace.this));
				for (int i = 0; i < wrappers.size(); i++) {
					wrappers.get(i).setPosition(i);
				}
			}
		}
		removeStyleName("hover");
	}

	public void dragEnd(IPCType record) {
		removeStyleName("hover");
	}

	public void blockMoved(PipeComponent wrapper, int before) {
		if (wrapper.getPosition() == before)
			return;
		Widget wid = getWidget(wrapper.getPosition() + offset);
		remove(wrapper.getPosition() + offset);
		wrappers.remove(wrapper.getPosition());
		if (wrapper.getPosition() > before) {
			insert(wid, before + offset);
			wrappers.insertElementAt(wrapper, before);
		} else {
			insert(wid, before + offset - 1);
			wrappers.insertElementAt(wrapper, before - 1);
		}
		for (int i = 0; i < wrappers.size(); i++) {
			wrappers.get(i).setPosition(i);
		}
	}

	public void blockRemoved(PipeComponent wrapper) {
		remove(wrapper.getPosition() + offset);
		wrappers.remove(wrapper.getPosition());
		for (int i = 0; i < wrappers.size(); i++) {
			wrappers.get(i).setPosition(i);
		}
	}

	public void blockAdded(PipeComponent wrapper, int before) {
		insert(new Block(wrapper, this), before + offset);
		wrappers.insertElementAt(wrapper, before);
		for (int i = 0; i < wrappers.size(); i++) {
			wrappers.get(i).setPosition(i);
		}
		removeStyleName("hover");
	}

	public Vector<PipeComponent> getPreviousApps(int position) {
		Vector<PipeComponent> ret = new Vector<PipeComponent>();
		for (int i = position - 1; i >= 0; i--) {
			ret.add(wrappers.get(i));
		}
		return ret;
	}

	public void addNonBlock(Widget w) {
		insert(w, 0);
		offset++;
	}

	/**
	 * @param pipeline
	 */
	public void loadPipeline(Pipeline pipeline) {
		pipe = pipeline;
		wrappers = pipeline.getSteps();

		clear();
		for (PipeComponent wrapper : wrappers) {
			// if (wrapper instanceof PipeApp)
			add(new Block(wrapper, this));
		}
	}

	public int getId() {
		return id;
	}

	public Pipeline getPipeline() {
		return pipe;
	}
}
