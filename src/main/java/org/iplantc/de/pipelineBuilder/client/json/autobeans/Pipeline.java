package org.iplantc.de.pipelineBuilder.client.json.autobeans;

import java.util.List;

import com.google.gwt.user.client.ui.HasName;

/**
 * An AutoBean interface for a Pipeline.
 * 
 * @author psarando
 *
 */
public interface Pipeline extends HasName {

    public String getId();

    public void setId(String id);

    public String getDescription();

    public void setDescription(String description);

    public List<PipelineApp> getApps();

    public void setApps(List<PipelineApp> apps);
}
