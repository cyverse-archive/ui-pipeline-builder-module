package org.iplant.pipeline.client.json.autobeans;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

/**
 * A Pipeline AutoBeanFactory.
 * 
 * @author psarando
 * 
 */
public interface PipelineAutoBeanFactory extends AutoBeanFactory {

    AutoBean<Pipeline> pipeline();

    AutoBean<App> app();

    AutoBean<AppMapping> appMapping();

    AutoBean<AppData> appData();
}
