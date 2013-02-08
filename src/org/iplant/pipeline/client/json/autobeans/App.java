package org.iplant.pipeline.client.json.autobeans;

import java.util.List;

/**
 * An AutoBean interface for a Pipeline App.
 * 
 * @author psarando
 *
 */
public interface App {

    public String getId();

    public void setId(String id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public int getStep();

    public void setStep(int step);

    public List<AppMapping> getMappings();

    public void setMappings(List<AppMapping> mappings);

    public List<AppData> getInputs();

    public void setInputs(List<AppData> inputs);

    public List<AppData> getOutputs();

    public void setOutputs(List<AppData> outputs);
}
