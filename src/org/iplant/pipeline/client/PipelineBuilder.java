package org.iplant.pipeline.client;

import org.iplant.pipeline.client.builder.PipelineCreator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PipelineBuilder implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("pipeline-builder").add(new PipelineCreator());
	}
}
