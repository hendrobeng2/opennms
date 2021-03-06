/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2006-2014 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2014 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.web.outage;

import java.sql.Date;

/**
 * <p>OutageSuppress class.</p>
 *
 * @author ranger
 * @version $Id: $
 * @since 1.8.1
 */
public class OutageSuppress {
    
    /**
     * <p>SuppressOutage</p>
     *
     * @param outageID a {@link java.lang.Integer} object.
     * @param Time a {@link java.sql.Date} object.
     * @param suppressedBy a {@link java.lang.String} object.
     */
    public void SuppressOutage (Integer outageID, Date Time, String suppressedBy) {
        // Some quirks, if time is == 0 - We will set this to 
        // the largest possible date that we can come up with
        //    
    }
    
    /**
     * <p>UnSuppressOutage</p>
     *
     * @param outageID a {@link java.lang.Integer} object.
     * @param suppressedBy a {@link java.lang.String} object.
     */
    public void UnSuppressOutage (Integer outageID, String suppressedBy) {
        // Need no time really....
        // We'll actually just delete the suppresstimefield
    
    }
    
    /**
     * <p>SubmitOutageSuppressedEvent</p>
     *
     * @param outageID a {@link java.lang.Integer} object.
     * @param suppressTime a {@link java.sql.Date} object.
     * @param suppressedBy a {@link java.lang.String} object.
     */
    public void SubmitOutageSuppressedEvent(Integer outageID, Date suppressTime, String suppressedBy){

    }
    
    /**
     * <p>SubmitUnSuppressedEvent</p>
     *
     * @param outageID a {@link java.lang.Integer} object.
     * @param suppressedBy a {@link java.lang.String} object.
     */
    public void SubmitUnSuppressedEvent (Integer outageID, String suppressedBy){
        
    }

}
