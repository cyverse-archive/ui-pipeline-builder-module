package org.iplant.pipeline.client.json.autobeans;

/**
 * An AutoBean interface for Pipeline App Inputs and Outputs.
 * 
 * @author psarando
 *
 */
public interface AppData {

    public String getId();

    public void setId(String id);

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public boolean getRequired();

    public void setRequired(boolean required);

    public String getFormat();

    public void setFormat(String format);
}
