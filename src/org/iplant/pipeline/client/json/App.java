package org.iplant.pipeline.client.json;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;


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
	private boolean isPublic;
	private String program;
	private boolean stared = false;
	private int creatorId;
	private int rating = 0;
	private HashMap<String, String> envVars;

	public void setEnvVars(HashMap<String, String> envVars) {
		this.envVars = envVars;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	private int publicId;
	private String ID;

	public App(int id) {
		this.id = id;
		inputs = new Vector<Input>();
		outputs = new Vector<Output>();
		envVars = new HashMap<String, String>();
	}

	public App() {
		this.id = 0;
		inputs = new Vector<Input>();
		outputs = new Vector<Output>();
		envVars = new HashMap<String, String>();
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

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
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

	public int getRating() {
		return rating;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public int getCreatorId() {
		return creatorId;
	}


	public void addVar(String name, String value) {
		envVars.put(name, value);
	}

	public HashMap<String, String> getEnvVars() {
		return envVars;
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
}
