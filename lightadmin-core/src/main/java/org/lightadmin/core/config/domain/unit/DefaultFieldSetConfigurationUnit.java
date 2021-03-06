package org.lightadmin.core.config.domain.unit;

import org.lightadmin.api.config.unit.FieldSetConfigurationUnit;
import org.lightadmin.core.config.bootstrap.parsing.configuration.DomainConfigurationUnitType;
import org.lightadmin.core.config.domain.field.FieldMetadata;
import org.lightadmin.core.config.domain.field.Persistable;
import org.lightadmin.core.config.domain.field.PersistentFieldMetadata;
import org.lightadmin.core.persistence.metamodel.DomainTypeAttributeMetadataAware;
import org.lightadmin.core.persistence.metamodel.DomainTypeEntityMetadata;
import org.lightadmin.core.persistence.metamodel.DomainTypeEntityMetadataAware;

import java.util.Iterator;
import java.util.Set;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static org.lightadmin.core.config.domain.field.FieldMetadataUtils.addPrimaryKeyPersistentField;
import static org.lightadmin.core.config.domain.field.FieldMetadataUtils.getPersistentField;

public class DefaultFieldSetConfigurationUnit extends DomainTypeConfigurationUnit implements FieldSetConfigurationUnit, DomainTypeEntityMetadataAware {

    private static final long serialVersionUID = 1L;

    private final DomainConfigurationUnitType configurationUnitType;

    private Set<FieldMetadata> fields = newLinkedHashSet();

    public DefaultFieldSetConfigurationUnit(Class<?> domainType, DomainConfigurationUnitType configurationUnitType) {
        super(domainType);
        this.configurationUnitType = configurationUnitType;
    }

    public void addField(FieldMetadata fieldMetadata) {
        fields.add(fieldMetadata);
    }

    public Set<FieldMetadata> getFields() {
        return newLinkedHashSet(fields);
    }

    @Override
    public FieldMetadata getField(String fieldName) {
        return getPersistentField(fields, fieldName);
    }

    @Override
    public Iterator<FieldMetadata> iterator() {
        return getFields().iterator();
    }

    @Override
    public boolean isEmpty() {
        return fields.isEmpty();
    }

    @Override
    public DomainConfigurationUnitType getDomainConfigurationUnitType() {
        return configurationUnitType;
    }

    @Override
    public void setDomainTypeEntityMetadata(DomainTypeEntityMetadata domainTypeEntityMetadata) {
        final PersistentFieldMetadata primaryKeyField = getPersistentField(fields, domainTypeEntityMetadata.getIdAttribute().getName());

        if (primaryKeyField != null) {
            primaryKeyField.setPrimaryKey(true);
        } else {
            fields = addPrimaryKeyPersistentField(fields, domainTypeEntityMetadata.getIdAttribute());
        }

        for (FieldMetadata field : fields) {
            if (field instanceof DomainTypeAttributeMetadataAware) {
                ((DomainTypeAttributeMetadataAware) field).setAttributeMetadata(domainTypeEntityMetadata.getAttribute(((Persistable) field).getField()));
            }
        }
    }
}