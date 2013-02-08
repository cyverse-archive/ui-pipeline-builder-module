package org.iplant.pipeline.client.json.autobeans;

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

    public List<App> getApps();

    public void setApps(List<App> apps);
}
