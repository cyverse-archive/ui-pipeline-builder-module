package org.iplant.pipeline.client.json;

import java.util.Collections;
import java.util.Vector;

import com.google.gwt.json.client.JSONArray;


public class App extends IPCType{
	/**
	 * 
	 */
	private static final long serialVersionUID = 716607128574843142L;
	private Vector<Input> inputs;
	private Vector<Output> outputs;

	private String name;
	private String description;
	private String creator;
	private String program;
	private boolean stared = false;
	private JSONArray inputJson;
	private JSONArray outputJson;

	private int publicId;
	private String ID;

	public App(int id) {
		this.id = id;
		inputs = new Vector<Input>();
		outputs = new Vector<Output>();
	}

	public App() {
		this.id = 0;
		inputs = new Vector<Input>();
		outputs = new Vector<Output>();
	}

	public Vector<Input> getInputs() {
		Collections.sort(inputs);
		return inputs;
	}

	public Vector<Output> getOutputs() {
		return outputs;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getCreator() {
		return creator;
	}

	public int getPublicId() {
		return publicId;
	}

	public void addInput(Input input) {
		inputs.add(input);
	}

	public void addOutput(Output output) {
		outputs.add(output);
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setPublicId(int publicId) {
		this.publicId = publicId;
	}

	public void setInput(int inputId, String value) {
		for (Input input : inputs) {
			if (input.getId() == inputId) {
				input.setValue(value);
				return;
			}
		}
	}

	public void setStared(boolean stared) {
		this.stared = stared;
	}

	public boolean isStared() {
		return stared;
	}

	public void setInputs(Vector<Input> inputs2) {
		inputs=inputs2;
	}

	public void setID(String id) {
		ID=id;
	}
	
	public String getID(){
		return ID; 
	}

	public JSONArray getInputJson() {
		return inputJson;
	}

	public void setInputJson(JSONArray inputJson) {
		this.inputJson = inputJson;
	}

	public JSONArray getOutputJson() {
		return outputJson;
	}

	public void setOutputJson(JSONArray outputJson) {
		this.outputJson = outputJson;
	}
}
