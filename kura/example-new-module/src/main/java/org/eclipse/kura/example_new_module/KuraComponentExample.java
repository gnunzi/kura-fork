package org.eclipse.kura.example_new_module;


import java.util.Map;

import org.eclipse.kura.configuration.ConfigurableComponent;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Example of Kura Component.
 */
@Component(immediate = true, //
        configurationPolicy = ConfigurationPolicy.REQUIRE//
        )
public class KuraComponentExample implements ConfigurableComponent {
    private static final Logger logger = LoggerFactory.getLogger(KuraComponentExample.class);
    private ComponentContext componentContext;

    @Activate
    protected void activate(ComponentContext componentContext, Map<String, Object> properties) {
        logger.info("Activating");
        this.componentContext = componentContext;
    }

    @Deactivate
    protected void deactivate(ComponentContext componentContext) {
        logger.info("Deactivating");
    }

    @Modified
    public void updated(Map<String, Object> properties) {
        logger.info("Updating");
    }

}