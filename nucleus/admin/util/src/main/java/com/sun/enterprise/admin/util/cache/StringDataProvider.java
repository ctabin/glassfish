/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.enterprise.admin.util.cache;

import com.sun.enterprise.util.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.jvnet.hk2.annotations.Service;

/**
 * DataProvider for String
 *
 * @author mmares
 */
@Service
public class StringDataProvider implements DataProvider {

    @Override
    public boolean accept(Class clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void writeToStream(Object o, OutputStream stream) throws IOException {
        String str = (String) o;
        stream.write(str.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Object toInstance(InputStream stream, Class clazz) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileUtils.copy(stream, baos, 0);
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }

}
