/*******************************************************************************
 * Copyright 2009, 2010 Innovation Gate GmbH. All Rights Reserved.
 * 
 * This file is part of the OpenWGA databaseServer platform.
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

package de.innovationgate.wgpublisher.cache;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.cyberneko.html.parsers.DOMParser;
import org.dom4j.io.DOMReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

import de.innovationgate.utils.WGUtils;
import de.innovationgate.webgate.api.WGDatabase;
import de.innovationgate.webgate.api.WGFactory;
import de.innovationgate.wgpublisher.WGACore;

public class HttpCachePreloader implements CachePreloader {
    
    public interface PrepareTask {
        
        public void prepare(HttpClient client, HttpRequestBase method);

    }
    
    private String _url;
    private Credentials _credentials;
    private PrepareTask _prepareTask = null;
    
    public HttpCachePreloader(String url) {
        _url = url;
    }
    
    public HttpCachePreloader(String url, Credentials creds) {
        _url = url;
        _credentials = creds;
    }
    
    public HttpCachePreloader(String url, String user, String pwd) {
        _url = url;
        _credentials = new UsernamePasswordCredentials(user, pwd);
    }

    @Override
    public String preloadCache(WGACore core, WGDatabase db) throws CachePreloadException {

        try {
            HttpClient client = WGFactory.getHttpClientFactory().createHttpClient();

            HttpGet method = new HttpGet(_url);
            final HttpClientContext context = HttpClientContext.create();
            if (_credentials != null) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(AuthScope.ANY, _credentials);

                AuthCache authCache = new BasicAuthCache();
                authCache.put(new HttpHost(method.getURI().getHost()), new BasicScheme());

                // Add AuthCache to the execution context
                context.setCredentialsProvider(credsProvider);
                context.setAuthCache(authCache);
            }
            

            
            if (_prepareTask != null) {
                _prepareTask.prepare(client, method);
            }
            
            HttpResponse response = client.execute(method, context);
            int status = response.getStatusLine().getStatusCode();

            if (status != 200) {
                if (status == 503 || status == 504 || status == 507 || status == 509) {
                    throw new CachePreloadException("HTTP Status Code is " + status + ". Server either timed out or not ready. Will requeue.", false);
                }
                else {
                    throw new CachePreloadException("HTTP Status Code is " + status, false);
                }
            }
            
            Reader reader;
            HttpEntity httpEntity = response.getEntity();
            ContentType contentType = ContentType.getOrDefault(httpEntity);
            Charset charset = contentType.getCharset();

            if (charset != null) {
                reader = new InputStreamReader(httpEntity.getContent(), charset);
            }
            else {
                reader = new InputStreamReader(httpEntity.getContent(), "UTF-8");
            }
            
            String contents = WGUtils.readString(reader);
            return contents;
        }
        catch (Exception e) {
            throw new CachePreloadException("Exception preloading cache", e, false);
        }
        
    }

    public PrepareTask getPrepareTask() {
        return _prepareTask;
    }

    public void setPrepareTask(PrepareTask prepareTask) {
        _prepareTask = prepareTask;
    }

}
