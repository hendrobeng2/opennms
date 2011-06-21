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
package org.opennms.netmgt.dao.support;

import org.opennms.netmgt.snmp.SnmpObjId;
import org.opennms.netmgt.snmp.SnmpUtils;
import org.opennms.netmgt.snmp.SnmpValue;

/**
 * <p>HostFileSystemStorageStrategy class.</p>
 *
 * @author <a href="mailto:agalue@opennms.org">Alejandro Galue</a>
 */
public class HostFileSystemStorageStrategy extends IndexStorageStrategy {

    /** Constant <code>HR_STORAGE_DESC=".1.3.6.1.2.1.25.2.3.1.3"</code> */
    public static String HR_STORAGE_DESC = ".1.3.6.1.2.1.25.2.3.1.3";

    /** {@inheritDoc} */
    @Override
    public String getResourceNameFromIndex(String resourceParent, String resourceIndex) {
        SnmpObjId oid = SnmpObjId.get(HR_STORAGE_DESC + "." + resourceIndex);
        SnmpValue snmpValue = SnmpUtils.get(m_storageStrategyService.getAgentConfig(), oid);
        String value = (snmpValue != null ? snmpValue.toString() : resourceIndex);
        /*
         * Use special translation for root (base) filesystem
         */
        if (value.equals("/"))
            return "_root_fs";
        /*
         * 1. Eliminate first slash character
         * 2. Eliminate tabs and spaces on filesystem names
         * 3. Replace slash (file separator) character with "-"
         * 4. Remove Additional Information on Windows Drives
         */
        return value.replaceFirst("/", "").replaceAll("\\s", "").replaceAll("/", "-").replaceAll(":\\\\.*", "");
    }

}
