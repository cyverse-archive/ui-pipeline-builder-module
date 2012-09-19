package org.iplant.pipeline.client;

import org.iplant.pipeline.client.builder.FunctionMiniBlock;
import org.iplant.pipeline.client.builder.PipelineCreator;
import org.iplant.pipeline.client.json.Pipeline;
import org.iplant.pipeline.client.json.PipelineApp;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class Test implements EntryPoint {

	@Override
	public void onModuleLoad() {
		final PipelineCreator creator = new PipelineCreator();
		RootPanel.get("pipeline-builder").add(creator);

		HTML testApp = new HTML("APP");
		testApp.setStyleName("block");
		RootPanel.get().add(testApp);
		testApp.getElement().setAttribute("draggable", "true");
		addDrag(testApp.getElement());
		PipelineApp casePipe = new PipelineApp(0, -1, -1);
		casePipe.setPipeline(new Pipeline("Foreach", "Used in a switch statement to represent the steps when the switch value is equal to this case", true, -1));
		RootPanel.get().add(new FunctionMiniBlock(casePipe));
		Button getJson = new Button("Get json");
		getJson.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				creator.loadPipeline(creator.getPipelineJson());
			}
		});
		RootPanel.get().add(getJson);
	}

	public static native void addDrag(Element element) /*-{
		var jsonn = "{\"deleted\": false,   \"description\": \"Some app description.\", \"disabled\": false,    \"id\": \"81C0CCEE-439C-4516-805F-3E260E336EE4\",    \"integrator_email\": \"nobody@iplantcollaborative.org\",    \"integrator_name\": \"Nobody\",    \"is_favorite\": false,    \"is_public\": true,    \"name\": \"Jills Extract First Line\",    \"pipeline_eligibility\": {        \"is_valid\": true,        \"reason\": \"\"    },    \"rating\": {        \"average\": 4,        \"comment_id\": 27,        \"user\": 4    },    \"wiki_url\": \"https://pods.iplantcollaborative.org/wiki/some/doc/link\",    \"inputs\": [        {            \"data_object\": {                \"cmdSwitch\": \"\",                \"description\": \"\",                \"file_info_type\": \"File\",                \"format\": \"Unspecified\",                \"id\": \"A6210636-E3EC-4CD3-97B4-CAD15CAC0913\",                \"multiplicity\": \"One\",                \"name\": \"Input File\",                \"order\": 1,                \"required\": true,                \"retain\": false            },            \"description\": \"\",            \"id\": \"A6210636-E3EC-4CD3-97B4-CAD15CAC0913\",            \"isVisible\": true,            \"label\": \"Input File\",            \"name\": \"\",            \"type\": \"Input\",            \"value\": \"\"        }    ],    \"outputs\": [        {            \"data_object\": {                \"cmdSwitch\": \"\",                \"description\": \"\",                \"file_info_type\": \"File\",                \"format\": \"Unspecified\",                \"id\": \"FE5ACC01-0B31-4611-B81E-26E532B459E3\",                \"multiplicity\": \"One\",                \"name\": \"head_output.txt\",                \"order\": 3,                \"required\": true,                \"retain\": true            },            \"description\": \"\",            \"id\": \"FE5ACC01-0B31-4611-B81E-26E532B459E3\",            \"isVisible\": false,            \"label\": \"head_output.txt\",            \"name\": \"\",            \"type\": \"Output\",            \"value\": \"\"        }    ] }";
		function handleDragStart(e) {
			e.dataTransfer.effectAllowed = 'all'; // only dropEffect='copy' will be dropable
			e.dataTransfer.setData('Text', jsonn); // required otherwise doesn't work
			var dragIcon = document.createElement('img');
				dragIcon.src = '/images/add.png';
				dragIcon.width = 20;
				e.dataTransfer.setDragImage(dragIcon, 10, 10);
		}
		function addEvent(el, type, fn) {
			if (el && el.nodeName || el === window) {
				el.addEventListener(type, fn, false);
			} else if (el && el.length) {
				for ( var i = 0; i < el.length; i++) {
					addEvent(el[i], type, fn);
				}
			}
		}
		addEvent(element, 'dragstart', handleDragStart);

	}-*/;
}
