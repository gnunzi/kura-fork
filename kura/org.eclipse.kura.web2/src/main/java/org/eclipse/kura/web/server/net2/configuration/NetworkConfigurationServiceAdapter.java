/*******************************************************************************
 * Copyright (c) 2023 Eurotech and/or its affiliates and others
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *  Eurotech
 *******************************************************************************/
package org.eclipse.kura.web.server.net2.configuration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.kura.KuraException;
import org.eclipse.kura.configuration.ComponentConfiguration;
import org.eclipse.kura.configuration.ConfigurationService;
import org.eclipse.kura.web.server.util.ServiceLocator;
import org.eclipse.kura.web.shared.GwtKuraException;
import org.eclipse.kura.web.shared.model.GwtNetInterfaceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapter to retrieve NetworkConfigurationService properties and
 * convert them to a {@link GwtNetInterfaceConfig} object, and viceversa.
 *
 */
public class NetworkConfigurationServiceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(NetworkConfigurationServiceAdapter.class);

    private static final String NETWORK_CONFIGURATION_SERVICE_PID = "org.eclipse.kura.net.admin.NetworkConfigurationService";
    private static final String NET_INTERFACES = "net.interfaces";

    private final ConfigurationService configurationService;
    private final Map<String, Object> netConfServProperties;

    public NetworkConfigurationServiceAdapter() throws GwtKuraException, KuraException {
        this.configurationService = ServiceLocator.getInstance().getService(ConfigurationService.class);
        ComponentConfiguration config = configurationService
                .getComponentConfiguration(NETWORK_CONFIGURATION_SERVICE_PID);
        this.netConfServProperties = config.getConfigurationProperties();
    }

    /**
     * 
     * @param ifname
     * @return a new {@link GwtNetInterfaceConfig} with properties read from the
     *         NetworkConfigurationService
     */
    public GwtNetInterfaceConfig getGwtNetInterfaceConfig(String ifname) {
        GwtNetInterfaceConfig gwtConfig = new GwtNetInterfaceConfigBuilder(this.netConfServProperties)
                .forInterface(ifname).build();
        logger.debug("GWT Network Configuration for interface {}:\n{}\n", ifname, gwtConfig.getProperties());
        return gwtConfig;
    }

    /**
     * Updates the {@link ConfigurationService} with the properties found in input
     * {@code gwtConfig}
     * 
     * @param gwtConfig the configuration containing the properties to update
     * @throws KuraException
     */
    public void updateConfiguration(GwtNetInterfaceConfig gwtConfig) throws KuraException {
        NetworkConfigurationServicePropertiesBuilder builder = new NetworkConfigurationServicePropertiesBuilder(
                gwtConfig);
        Map<String, Object> newProperties = builder.build();
        newProperties.put(NET_INTERFACES, addNetworkInterfaceIfNotPresent(gwtConfig.getName()));

        logger.debug("Updating Network Configuration Service with properties:\n{}\n", newProperties);

        this.configurationService.updateConfiguration(NETWORK_CONFIGURATION_SERVICE_PID, newProperties);
    }

    private String addNetworkInterfaceIfNotPresent(String ifname) {
        List<String> ifnames = new LinkedList<>();

        String netInterfaces = (String) this.netConfServProperties.get(NET_INTERFACES);
        String[] interfaces = netInterfaces.split(",");

        for (String name : interfaces) {
            ifnames.add(name.trim());
        }

        if (!ifnames.contains(ifname)) {
            ifnames.add(ifname);
        }

        StringBuilder result = new StringBuilder();

        for (String name : ifnames) {
            result.append(name);
            result.append(",");
        }

        return String.join(",", ifnames.stream().collect(Collectors.toList()));
    }

}