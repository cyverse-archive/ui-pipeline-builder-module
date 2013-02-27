package org.iplantc.core.pipelineBuilder.client.json.autobeans;

import java.util.List;

/**
 * An AutoBean interface for a Pipeline App.
 * 
 * @author psarando
 *
 */
public interface PipelineApp {

    public String getId();

    public void setId(String id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public int getStep();

    public void setStep(int step);

    public List<PipelineAppMapping> getMappings();

    public void setMappings(List<PipelineAppMapping> mappings);

    public List<PipelineAppData> getInputs();

    public void setInputs(List<PipelineAppData> inputs);

    public List<PipelineAppData> getOutputs();

    public void setOutputs(List<PipelineAppData> outputs);
}
