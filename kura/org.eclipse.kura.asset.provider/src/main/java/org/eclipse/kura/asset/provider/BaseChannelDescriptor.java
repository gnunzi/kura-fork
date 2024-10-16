/*******************************************************************************
 * Copyright (c) 2016, 2024 Eurotech and/or its affiliates and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Eurotech
 *  Amit Kumar Mondal
 *
 *******************************************************************************/
package org.eclipse.kura.asset.provider;

import static org.eclipse.kura.asset.provider.AssetConstants.NAME;
import static org.eclipse.kura.asset.provider.AssetConstants.TYPE;
import static org.eclipse.kura.asset.provider.AssetConstants.VALUE_OFFSET;
import static org.eclipse.kura.asset.provider.AssetConstants.VALUE_SCALE;
import static org.eclipse.kura.asset.provider.AssetConstants.VALUE_TYPE;
import static org.eclipse.kura.asset.provider.AssetConstants.VALUE_UNIT;

import java.util.List;

import org.eclipse.kura.channel.ChannelType;
import org.eclipse.kura.channel.ScaleOffsetType;
import org.eclipse.kura.configuration.metatype.Option;
import org.eclipse.kura.core.configuration.metatype.Tad;
import org.eclipse.kura.core.configuration.metatype.Toption;
import org.eclipse.kura.core.configuration.metatype.Tscalar;
import org.eclipse.kura.driver.ChannelDescriptor;
import org.eclipse.kura.type.DataType;
import org.eclipse.kura.util.collection.CollectionUtil;

/**
 * The Class BaseChannelDescriptor returns the basic channel descriptor required
 * for a channel<br/>
 * <br/>
 *
 * The basic descriptions include the following
 * <ul>
 * <li>name</li> denotes the name of the channel
 * <li>type</li>
 * <li>value.type</li>
 * </ul>
 *
 * The <b><i>type</i></b> would be one of the following:
 * <ul>
 * <li>READ</li>
 * <li>WRITE</li>
 * <li>READ_WRITE</li>
 * </ul>
 *
 * The <b><i>value.type</i></b> would be one of the following:
 * <ul>
 * <li>INTEGER</li>
 * <li>DOUBLE</li>
 * <li>FLOAT</li>
 * <li>LONG</li>
 * <li>BOOLEAN</li>
 * <li>STRING</li>
 * <li>BYTE_ARRAY</li>
 * </ul>
 * 
 * The <b><i>scaleoffset.type</i></b> would be one of the following:
 * <ul>
 * <li>DEFINED_BY_VALUE_TYPE</li>
 * <li>INTEGER</li>
 * <li>DOUBLE</li>
 * <li>FLOAT</li>
 * <li>LONG</li>
 * </ul>
 *
 * @see org.eclipse.kura.asset.AssetConfiguration
 */
public class BaseChannelDescriptor implements ChannelDescriptor {

    private static final BaseChannelDescriptor INSTANCE = new BaseChannelDescriptor();

    protected final List<Tad> defaultElements;

    protected static void addOptions(Tad target, Enum<?>[] values) {
        final List<Option> options = target.getOption();
        for (Enum<?> value : values) {
            final String name = value.name();
            final Toption option = new Toption();
            option.setLabel(name);
            option.setValue(name);
            options.add(option);
        }
    }

    /**
     * Instantiates a new base asset channel descriptor.
     */
    protected BaseChannelDescriptor() {
        this.defaultElements = CollectionUtil.newArrayList();

        final Tad enabled = new Tad();
        enabled.setId(AssetConstants.ENABLED.value());
        enabled.setName(AssetConstants.ENABLED.value().substring(1));
        enabled.setType(Tscalar.BOOLEAN);
        enabled.setDefault("true");
        enabled.setDescription("Determines if the channel is enabled or not");
        enabled.setCardinality(0);
        enabled.setRequired(true);

        this.defaultElements.add(enabled);

        final Tad name = new Tad();
        name.setId(NAME.value());
        name.setName(NAME.value().substring(1));
        name.setType(Tscalar.STRING);
        name.setDefault("Channel-1");
        name.setDescription("Name of the Channel");
        name.setCardinality(0);
        name.setRequired(true);

        this.defaultElements.add(name);

        final Tad type = new Tad();
        type.setName(TYPE.value().substring(1));
        type.setId(TYPE.value());
        type.setDescription("Type of the channel");
        type.setType(Tscalar.STRING);
        type.setRequired(true);
        type.setDefault(ChannelType.READ.name());

        addOptions(type, ChannelType.values());

        this.defaultElements.add(type);

        final Tad valueType = new Tad();
        valueType.setName(VALUE_TYPE.value().substring(1));
        valueType.setId(VALUE_TYPE.value());
        valueType.setDescription("Value type of the channel");
        valueType.setType(Tscalar.STRING);
        valueType.setRequired(true);
        valueType.setDefault(DataType.INTEGER.name());

        addOptions(valueType, DataType.values());

        this.defaultElements.add(valueType);

        final Tad scaleOffsetType = new Tad();
        scaleOffsetType.setName(AssetConstants.SCALE_OFFSET_TYPE.value().substring(1));
        scaleOffsetType.setId(AssetConstants.SCALE_OFFSET_TYPE.value());
        scaleOffsetType.setDescription("Scale/Offset type of the channel");
        scaleOffsetType.setType(Tscalar.STRING);
        scaleOffsetType.setRequired(false);
        scaleOffsetType.setDefault(ScaleOffsetType.DEFINED_BY_VALUE_TYPE.name());

        addOptions(scaleOffsetType, ScaleOffsetType.values());

        this.defaultElements.add(scaleOffsetType);

        final Tad valueScale = new Tad();
        valueScale.setName(VALUE_SCALE.value().substring(1));
        valueScale.setId(VALUE_SCALE.value());
        valueScale.setDescription("Scale to be applied to the numeric value of the channel");
        valueScale.setType(Tscalar.DOUBLE);
        valueScale.setRequired(false);

        this.defaultElements.add(valueScale);

        final Tad valueOffset = new Tad();
        valueOffset.setName(VALUE_OFFSET.value().substring(1));
        valueOffset.setId(VALUE_OFFSET.value());
        valueOffset.setDescription("Offset to be applied to the numeric value of the channel");
        valueOffset.setType(Tscalar.DOUBLE);
        valueOffset.setRequired(false);

        this.defaultElements.add(valueOffset);

        final Tad valueUnit = new Tad();
        valueUnit.setName(VALUE_UNIT.value().substring(1));
        valueUnit.setId(VALUE_UNIT.value());
        valueUnit.setDescription("Unit associated to the value of the channel");
        valueUnit.setType(Tscalar.STRING);
        valueUnit.setRequired(false);
        valueUnit.setDefault("");

        this.defaultElements.add(valueUnit);
    }

    /** {@inheritDoc} */
    @Override
    public Object getDescriptor() {
        return this.defaultElements;
    }

    public static BaseChannelDescriptor get() {
        return INSTANCE;
    }
}
