package org.iplant.pipeline.client.json.autobeans;

import java.util.AbstractMap;

/**
 * An AutoBean interface for Pipeline App Input to Output mappings.
 * 
 * @author psarando
 *
 */
public interface AppMapping {

    public int getStep();

    public void setStep(int step);

    public String getId();

    public void setId(String id);

    public AbstractMap<String, String> getMap();

    public void setMap(AbstractMap<String, String> map);
}
