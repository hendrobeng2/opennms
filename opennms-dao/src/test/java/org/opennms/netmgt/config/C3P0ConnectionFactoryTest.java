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
package org.opennms.netmgt.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

import org.apache.commons.io.IOUtils;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.opennms.test.ConfigurationTestUtils;

/**
 * 
 * @author <a href="mailto:david@opennms.org">David Hustace</a>
 */
public class C3P0ConnectionFactoryTest extends TestCase {
    public void testMarshalDataSourceFromConfig() throws Exception {
        makeFactory("opennms");
        C3P0ConnectionFactory factory2 = makeFactory("opennms2");
        
        Connection conn = null;
        Statement s = null;
        try {
            conn = factory2.getConnection();
            s = conn.createStatement();
            s.execute("select * from pg_proc");
        } finally {
            if (s != null) {
                s.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    private C3P0ConnectionFactory makeFactory(String database) throws MarshalException, ValidationException, PropertyVetoException, SQLException, IOException {
        InputStream stream = ConfigurationTestUtils.getInputStreamForResource(this, "opennms-datasources.xml");
        try {
            return new C3P0ConnectionFactory(stream, database);
        } finally {
            IOUtils.closeQuietly(stream);
        }
    }
}
