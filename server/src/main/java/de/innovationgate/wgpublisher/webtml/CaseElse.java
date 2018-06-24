/*******************************************************************************
 * Copyright 2009, 2010 Innovation Gate GmbH. All Rights Reserved.
 * 
 * This file is part of the OpenWGA server platform.
 * 
 * OpenWGA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * In addition, a special exception is granted by the copyright holders
 * of OpenWGA called "OpenWGA plugin exception". You should have received
 * a copy of this exception along with OpenWGA in file COPYING.
 * If not, see <http://www.openwga.com/gpl-plugin-exception>.
 * 
 * OpenWGA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with OpenWGA in file COPYING.
 * If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package de.innovationgate.wgpublisher.webtml;

import de.innovationgate.wgpublisher.webtml.utils.TMLException;

public class CaseElse extends Base {
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static class Status extends BaseTagStatus {
        private Select.Status parentTag;
    }
    
    @Override
    public Status createTagStatus() {
        return new Status();
    }

	/**
	 * @see Base#tmlStartTag()
	 */
	public void tmlStartTag() throws TMLException {

	    Status status = (Status) getStatus();
		status.keepResult = true;

		status.parentTag = (Select.Status) getStatus().getAncestorTag(Select.class);
		if (status.parentTag == null) {
			this.addWarning("No select tag found as parent", true);
			return;
		}
		
		if (status.parentTag.bHasResultTag == false) {
		    status.parentTag.addResultTag(this);
		}
		else {
		    status.evalBody = false;
		    status.writeVars = false;
		}
	}

}

