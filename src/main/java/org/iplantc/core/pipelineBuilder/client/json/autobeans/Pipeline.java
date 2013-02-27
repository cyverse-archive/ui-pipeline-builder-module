package org.iplantc.core.pipelineBuilder.client.json.autobeans;

import java.util.List;

/**
 * An AutoBean interface for a Pipeline.
 * 
 * @author psarando
 *
 */
public interface Pipeline {

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public List<PipelineApp> getApps();

    public void setApps(List<PipelineApp> apps);
}
