/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.s1peqe.mq.cmt.excpt.client;

import jakarta.jms.*;
import javax.naming.*;
import java.sql.*;
import com.sun.ejte.ccl.reporter.SimpleReporterAdapter;

public class SimpleMessageClient {

    private static SimpleReporterAdapter stat =
        new SimpleReporterAdapter("appserv-tests");

    public static void main(String[] args) {

        stat.addDescription("This is to test simple "+
             "message driven bean sample.");

        Context                 jndiContext = null;
        QueueConnectionFactory  queueConnectionFactory = null;
        QueueConnection         queueConnection = null;
        QueueSession            queueSession = null;
        Queue                   queue = null;
        QueueSender             queueSender = null;
        TextMessage             message = null;
        final int               NUM_MSGS = 3;
        boolean                 passed = true;

        try {
            jndiContext = new InitialContext();
            //stat.addStatus("simple mdb main", stat.PASS);
        } catch (NamingException e) {
            System.out.println("Could not create JNDI " +
                "context: " + e.toString());
            stat.addStatus("simple mdb main", stat.FAIL);
            stat.printSummary("simpleMdbID");
            System.exit(1);
        }

        try {
            queueConnectionFactory = (QueueConnectionFactory)
                jndiContext.lookup
                ("java:comp/env/jms/QCFactory");
            queue = (Queue) jndiContext.lookup("java:comp/env/jms/SampleQueue");
            //stat.addStatus("simple mdb main", stat.PASS);
        } catch (NamingException e) {
            System.out.println("JNDI lookup failed: " +
                e.toString());
            stat.addStatus("simple mdb main", stat.FAIL);
            stat.printSummary("simpleMdbID");
            System.exit(1);
        }

        try {
            queueConnection =
                queueConnectionFactory.createQueueConnection();
            queueSession =
                queueConnection.createQueueSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(queue);
            message = queueSession.createTextMessage();

            for (int i = 0; i < NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                message.setIntProperty("Id",i);
                System.out.println("Sending message: " +
                    message.getText());
                queueSender.send(message);
            }

                for (int i=0; i< args.length; i++)
                        System.out.println("Client: "+ args[i]);

           Thread.sleep(10000);
            Class.forName(args[0]);
            String url = args[1];
            java.sql.Connection con = DriverManager.getConnection(url,args[2],args[3]);
            ResultSet rs = con.createStatement().executeQuery("select exCount from mq_cmt_excpt");
            int count = 0;
            while (rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            con.close();
            if (count != 15) {
               throw new Exception("test failed because the exception count was " + count);
            }
            System.out.println("Each message got redelivered " + (count/NUM_MSGS -1) + " times successfully and then stopped delivery");
        } catch (Throwable e) {
            System.out.println("Exception occurred: " + e.toString());
            passed = false;
            stat.addStatus("simple mdb main", stat.FAIL);
        } finally {
            if (queueConnection != null) {
                try {
                    queueConnection.close();
                } catch (JMSException e) {}
            } // if
            if (passed) stat.addStatus("simple mdb main", stat.PASS);
            stat.printSummary("simpleMdbID");
            System.exit(0);
        } // finally
    } // main
} // class

