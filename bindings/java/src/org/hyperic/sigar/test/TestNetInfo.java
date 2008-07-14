/*
 * Copyright (C) [2004, 2005, 2006], Hyperic, Inc.
 * This file is part of SIGAR.
 * 
 * SIGAR is free software; you can redistribute it and/or modify
 * it under the terms version 2 of the GNU General Public License as
 * published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */

package org.hyperic.sigar.test;

import org.hyperic.sigar.NetConnection;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarPermissionDeniedException;

public class TestNetInfo extends SigarTestCase {

    public TestNetInfo(String name) {
        super(name);
    }

    public void testNetInfo() throws SigarException {
        NetInfo info = getSigar().getNetInfo();
        NetInterfaceConfig config = getSigar().getNetInterfaceConfig(null);
        traceln("");
        traceln(info.toString());
        traceln(config.toString());

        int flags = NetFlags.CONN_SERVER | NetFlags.CONN_TCP;

        NetConnection[] connections;

        try {
            connections =
                getSigar().getNetConnectionList(flags);
        } catch (SigarPermissionDeniedException e) {
            return;
        }

        for (int i=0; i<connections.length; i++) {
            long port = connections[i].getLocalPort();
            String listenAddress = 
                getSigar().getNetListenAddress(port);
            if (NetFlags.isAnyAddress(listenAddress)) {
                listenAddress = "*";
            }
            traceln("Listen " + listenAddress + ":" + port);
        }
    }
}
