/*******************************************************************************
 * Copyright (c) 2020, 2024 Eurotech and/or its affiliates and others
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
package org.eclipse.kura.web.server;

import java.util.Set;

import org.eclipse.kura.web.UserManager;
import org.eclipse.kura.web.shared.GwtKuraException;
import org.eclipse.kura.web.shared.model.GwtUserConfig;
import org.eclipse.kura.web.shared.model.GwtXSRFToken;
import org.eclipse.kura.web.shared.service.GwtUserService;

public class GwtUserServiceImpl extends OsgiRemoteServiceServlet implements GwtUserService {

    private static final long serialVersionUID = 6065248347373180366L;
    private final UserManager userManager;

    public GwtUserServiceImpl(final UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void createUser(final GwtXSRFToken token, final String userName) throws GwtKuraException {
        checkXSRFToken(token);

        this.userManager.createUser(userName);
    }

    @Override
    public void deleteUser(final GwtXSRFToken token, final String userName) throws GwtKuraException {
        checkXSRFToken(token);

        this.userManager.deleteUser(userName);

    }

    @Override
    public Set<String> getDefinedPermissions(final GwtXSRFToken token) throws GwtKuraException {
        checkXSRFToken(token);

        return this.userManager.getDefinedPermissions();
    }

    @Override
    public Set<GwtUserConfig> getUserConfig(final GwtXSRFToken token) throws GwtKuraException {
        checkXSRFToken(token);

        return this.userManager.getUserConfig();
    }

    @Override
    public GwtUserConfig getUserConfigOrDefault(GwtXSRFToken token, String name) throws GwtKuraException {
        checkXSRFToken(token);

        return this.userManager.getUserDefaultConfig(name);

    }

    @Override
    public void setUserConfig(final GwtXSRFToken token, final Set<GwtUserConfig> userConfig) throws GwtKuraException {
        checkXSRFToken(token);

        this.userManager.setUserConfig(userConfig);

    }

}
