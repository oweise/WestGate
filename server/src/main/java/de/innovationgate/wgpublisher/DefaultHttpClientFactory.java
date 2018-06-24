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

package de.innovationgate.wgpublisher;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;

import de.innovationgate.webgate.api.HttpClientFactory;
import de.innovationgate.wga.config.WGAConfiguration;

public class DefaultHttpClientFactory implements HttpClientFactory {
    
    private WGAConfiguration _config;

    public DefaultHttpClientFactory(WGAConfiguration config) {
        _config = config;
    }

    public HttpClient createHttpClient() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        BasicHttpClientConnectionManager httpClientConnectionManager = new BasicHttpClientConnectionManager();
        httpClientConnectionManager.setSocketConfig(SocketConfig.custom()
                .setSoTimeout(60 * 1000)
                .build());
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10 * 1000)
                .setConnectionRequestTimeout(10 * 1000)
                .setSocketTimeout(60 * 1000).build();

        builder.setConnectionManager(httpClientConnectionManager);
        builder.setDefaultRequestConfig(config);

        if (_config != null && _config.getProxyConfiguration() != null && _config.getProxyConfiguration().isEnabled()) {
            if (_config.getProxyConfiguration().getHttpProxy() != null) {

                HttpHost proxy = new HttpHost(_config.getProxyConfiguration().getHttpProxy(), _config.getProxyConfiguration().getHttpProxyPort());
                DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);

                builder.setProxy(proxy);
                
                if (_config.getProxyConfiguration().getUser() != null) {
                    UsernamePasswordCredentials creds = new UsernamePasswordCredentials(_config.getProxyConfiguration().getUser(), _config.getProxyConfiguration().getPassword());
                    CredentialsProvider credsProvider = new BasicCredentialsProvider();
                    credsProvider.setCredentials( new AuthScope(proxy.getHostName(),proxy.getPort()), creds );
                    builder.setDefaultCredentialsProvider(credsProvider);
                }
            }
            
        }
        
        return builder.build();
    }

}
