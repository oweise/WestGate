@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(type=java.util.Date.class, 
        value=DateAdapter.class)
})
package de.innovationgate.wga.services.rest.v1.types;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
