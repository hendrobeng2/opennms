/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * OpenNMS(R) is Copyright (C) 1999-2011 The OpenNMS Group, Inc.  All rights reserved.
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 *     along with OpenNMS(R).  If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information contact: 
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/
package org.opennms.core.soa.config;

import org.opennms.core.soa.support.ReferenceFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * <p>ReferenceBeanDefinitionParser class.</p>
 *
 * @author ranger
 * @version $Id: $
 */
public class ReferenceBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
        
    /** Constant <code>INTERFACE_ATTR="interface"</code> */
    public static final String INTERFACE_ATTR = "interface";
    public static final String FILTER_ATTR = "filter";
    
    /** {@inheritDoc} */
    protected Class<?> getBeanClass(Element element) {
        return ReferenceFactoryBean.class;
    }
    
    /** {@inheritDoc} */
    public void doParse(Element element, ParserContext context, BeanDefinitionBuilder bean){
        
        bean.addPropertyReference("serviceRegistry", Constants.SERVICE_REGISTRY_BEAN_NAME);
        
        String serviceInterface = element.getAttribute(INTERFACE_ATTR);
        if (StringUtils.hasText(serviceInterface)) {
            bean.addPropertyValue("serviceInterface", serviceInterface);
        }
        
        String filter = element.getAttribute(FILTER_ATTR);
        if (StringUtils.hasText(filter)) {
            bean.addPropertyValue("filter", filter);
        }
    }
}

