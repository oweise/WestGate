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
package de.innovationgate.wgpublisher.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import de.innovationgate.wga.common.CodeCompletion;
import de.innovationgate.wga.config.MailConfiguration;

/**
 * This Class provides mail send functionality to TMLScript
 */
@CodeCompletion(methodMode=CodeCompletion.MODE_INCLUDE, beanMode=CodeCompletion.BEAN_MODE_ALL)
public class SmtpMail extends MailBase {
    
    class MailAuthenticator extends Authenticator {

        private String _user;

        private String _pwd;

        public MailAuthenticator(String user, String pwd) {
            _user = user;
            _pwd = pwd;
        }

        /*
         * (Kein Javadoc)
         * 
         * @see javax.mail.Authenticator#getPasswordAuthentication()
         */
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(_user, _pwd);
        }

    }

    public SmtpMail(String smtpHost, String username, String password) throws UnsupportedEncodingException {
    	MailConfiguration config = new MailConfiguration();
    	config.setServer(smtpHost);
    	config.setUser(username);
    	config.setPassword(password);
    	WGAMailConfiguration mailConfig = WGAMailConfiguration.create(config);
    	init(mailConfig);
    }

    public SmtpMail(WGAMailConfiguration mailConfig) throws UnsupportedEncodingException {
        init(mailConfig);
    }
    
    @Override
    @CodeCompletion
    public void send(Message message) throws MessagingException {
        Transport.send(message);
    }
    
    @Override
    protected Session createMailSession() {
        return _mailConfig.createMailSession();
    }


}
