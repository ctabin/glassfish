/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.s1asdev.security.jmac.soapembedded.servletws;

import jakarta.jws.WebService;

@WebService(targetNamespace = "http://servletws.soapembedded.jmac.security.s1asdev.sun.com", serviceName = "JmacSoapEmbeddedServletService")
public class HelloServlet {
    
    public String hello(String who) {
        String message = "HelloServlet " + who;
        System.out.println(message);
        return message;

    }
}
