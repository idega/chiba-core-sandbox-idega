// Copyright 2006 Chibacon
/*
 *
 *    Artistic License
 *
 *    Preamble
 *
 *    The intent of this document is to state the conditions under which a
 *    Package may be copied, such that the Copyright Holder maintains some
 *    semblance of artistic control over the development of the package,
 *    while giving the users of the package the right to use and distribute
 *    the Package in a more-or-less customary fashion, plus the right to make
 *    reasonable modifications.
 *
 *    Definitions:
 *
 *    "Package" refers to the collection of files distributed by the
 *    Copyright Holder, and derivatives of that collection of files created
 *    through textual modification.
 *
 *    "Standard Version" refers to such a Package if it has not been
 *    modified, or has been modified in accordance with the wishes of the
 *    Copyright Holder.
 *
 *    "Copyright Holder" is whoever is named in the copyright or copyrights
 *    for the package.
 *
 *    "You" is you, if you're thinking about copying or distributing this Package.
 *
 *    "Reasonable copying fee" is whatever you can justify on the basis of
 *    media cost, duplication charges, time of people involved, and so
 *    on. (You will not be required to justify it to the Copyright Holder,
 *    but only to the computing community at large as a market that must bear
 *    the fee.)
 *
 *    "Freely Available" means that no fee is charged for the item itself,
 *    though there may be fees involved in handling the item. It also means
 *    that recipients of the item may redistribute it under the same
 *    conditions they received it.
 *
 *    1. You may make and give away verbatim copies of the source form of the
 *    Standard Version of this Package without restriction, provided that you
 *    duplicate all of the original copyright notices and associated
 *    disclaimers.
 *
 *    2. You may apply bug fixes, portability fixes and other modifications
 *    derived from the Public Domain or from the Copyright Holder. A Package
 *    modified in such a way shall still be considered the Standard Version.
 *
 *    3. You may otherwise modify your copy of this Package in any way,
 *    provided that you insert a prominent notice in each changed file
 *    stating how and when you changed that file, and provided that you do at
 *    least ONE of the following:
 *
 *        a) place your modifications in the Public Domain or otherwise make
 *        them Freely Available, such as by posting said modifications to
 *        Usenet or an equivalent medium, or placing the modifications on a
 *        major archive site such as ftp.uu.net, or by allowing the Copyright
 *        Holder to include your modifications in the Standard Version of the
 *        Package.
 *
 *        b) use the modified Package only within your corporation or
 *        organization.
 *
 *        c) rename any non-standard executables so the names do not conflict
 *        with standard executables, which must also be provided, and provide
 *        a separate manual page for each non-standard executable that
 *        clearly documents how it differs from the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    4. You may distribute the programs of this Package in object code or
 *    executable form, provided that you do at least ONE of the following:
 *
 *        a) distribute a Standard Version of the executables and library
 *        files, together with instructions (in the manual page or
 *        equivalent) on where to get the Standard Version.
 *
 *        b) accompany the distribution with the machine-readable source of
 *        the Package with your modifications.
 *
 *        c) accompany any non-standard executables with their corresponding
 *        Standard Version executables, giving the non-standard executables
 *        non-standard names, and clearly documenting the differences in
 *        manual pages (or equivalent), together with instructions on where
 *        to get the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    5. You may charge a reasonable copying fee for any distribution of this
 *    Package. You may charge any fee you choose for support of this
 *    Package. You may not charge a fee for this Package itself.  However,
 *    you may distribute this Package in aggregate with other (possibly
 *    commercial) programs as part of a larger (possibly commercial) software
 *    distribution provided that you do not advertise this Package as a
 *    product of your own.
 *
 *    6. The scripts and library files supplied as input to or produced as
 *    output from the programs of this Package do not automatically fall
 *    under the copyright of this Package, but belong to whomever generated
 *    them, and may be sold commercially, and may be aggregated with this
 *    Package.
 *
 *    7. C or perl subroutines supplied by you and linked into this Package
 *    shall not be considered part of this Package.
 *
 *    8. The name of the Copyright Holder may not be used to endorse or
 *    promote products derived from this software without specific prior
 *    written permission.
 *
 *    9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package org.chiba.xml.xforms.connector.http;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpecBase;
import org.apache.commons.httpclient.cookie.MalformedCookieException;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.connector.AbstractConnector;
import org.chiba.xml.xforms.exception.XFormsException;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple base class for convenient HTTP connector interface implementation.
 *
 * @author <a href="mailto:unl@users.sourceforge.net">uli</a>
 * @version $Id: AbstractHTTPConnector.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class AbstractHTTPConnector extends AbstractConnector {
    private static Log LOGGER = LogFactory.getLog(AbstractHTTPConnector.class);
    public static final String REQUEST_COOKIE = "request-cookie";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    
    /**
     * The response body.
     */
    private InputStream responseBody = null;

    /**
     * The response header.
     */
    private Map responseHeader = null;

    /**
     * Returns the response body.
     *
     * @return the response body.
     */
    protected InputStream getResponseBody() {
        return responseBody;
    }

    /**
     * Returns the response header.
     *
     * @return the response header.
     */
    protected Map getResponseHeader() {
        return responseHeader;
    }

    /**
     * @deprecated submissionMap is not used from local code any more.
     */
    private Map submissionMap = null;

    /**
     * allows to pass arbitrary params to an application by storing them in the submission map
     *
     * @param map
     * @deprecated submissionMap is not used from local code any more.
     */
    protected void setSubmissionMap(Map map) {
        this.submissionMap = map;
    }

    /**
     * Performs a HTTP GET request.
     *
     * @param uri the request uri.
     * @throws XFormsException if any error occurred during the request.
     */
    protected void get(String uri) throws XFormsException {
        HttpMethod httpMethod = new GetMethod(uri);
        try {
            httpMethod.setRequestHeader(new Header("User-Agent", ChibaBean.getAppInfo()));
            execute(httpMethod);
        } catch (Exception e) {
            throw new XFormsException(e);
        }
    }

    /**
     * Performs a HTTP POST request.
     * <p/>
     * The content type is <code>application/xml</code>.
     *
     * @param uri  the request uri.
     * @param body the request body.
     * @param encoding the encoding being used.
     * @throws XFormsException if any error occurred during the request.
     */
    protected void post(String uri, String body, String encoding) throws XFormsException {
        post(uri, body, "application/xml", encoding);
    }

    /**
     * Performs a HTTP POST request.
     *
     * @param uri  the request uri.
     * @param body the request body.
     * @param type the content type.
     * @param encoding the encoding being used.
     * @throws XFormsException if any error occurred during the request.
     */
    protected void post(String uri, String body, String type, String encoding) throws XFormsException {
        EntityEnclosingMethod httpMethod = new PostMethod(uri);
        try {
            configureRequest(httpMethod, body, type, encoding);

            execute(httpMethod);
        } catch (Exception e) {
            throw new XFormsException(e);
        }
    }

    /**
     * Performs a HTTP PUT request.
     * <p/>
     * The content type is <code>application/xml</code>.
     *
     * @param uri  the request uri.
     * @param body the request body.
     * @param encoding the encoding being used.
     * @throws XFormsException if any error occurred during the request.
     */
    protected void put(String uri, String body, String encoding) throws XFormsException {
        put(uri, body, "application/xml", encoding);
    }

    /**
     * Performs a HTTP PUT request.
     *
     * @param uri  the request uri.
     * @param body the request body.
     * @param type the content type.
     * @param encoding the encoding being used.
     * @throws XFormsException if any error occurred during the request.
     */
    protected void put(String uri, String body, String type, String encoding) throws XFormsException {
        EntityEnclosingMethod httpMethod = new PutMethod(uri);
        try {
            configureRequest(httpMethod, body, type, encoding);

            execute(httpMethod);
        } catch (Exception e) {
            throw new XFormsException(e);
        }
    }

    protected void execute(HttpMethod httpMethod) throws Exception {
        //		(new HttpClient()).executeMethod(httpMethod);
        HttpClient client = new HttpClient();

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("context params>>>");
            Map map = getContext();
            Iterator keys = map.keySet().iterator();
            while (keys.hasNext()) {
                String key =  keys.next().toString();
                String value =  map.get(key).toString();
                LOGGER.debug(key + "=" + value);
            }
            LOGGER.debug("<<<end params");
        }

        if (getContext().containsKey(ACCEPT_LANGUAGE)) {
            String acceptLanguage = (String) getContext().get(ACCEPT_LANGUAGE);
            if ((acceptLanguage != null) && (acceptLanguage.length() > 0)){
                httpMethod.setRequestHeader(new Header("Accept-Language", acceptLanguage));
            }
        }

        //alternative method for non-tomcat servers
        if(getContext().containsKey(REQUEST_COOKIE)){
            HttpState state = client.getState();
            state.setCookiePolicy(CookiePolicy.COMPATIBILITY);

            if(getContext().get(REQUEST_COOKIE) instanceof Cookie[]){
                Cookie[] cookiesIn = (Cookie[]) getContext().get(REQUEST_COOKIE);
                if(cookiesIn[0] != null){
                    for (int i=0;i<cookiesIn.length;i++){
                        Cookie cookie = cookiesIn[i];
                        state.addCookie(cookie);
                    }
                    Cookie[] cookies = state.getCookies();
                    Header cookieOut = new CookieSpecBase().formatCookieHeader(cookies);
                    httpMethod.setRequestHeader(cookieOut);
                    client.setState(state);
                }
            }else{
                throw new MalformedCookieException("Cookies must be passed as org.apache.commons.httpclient.Cookie objects.");
            }
        }

        client.executeMethod(httpMethod);

        if (httpMethod.getStatusCode() >= 300) {
            throw new XFormsException("HTTP status "
                    + httpMethod.getStatusCode()
                    + ": "
                    + httpMethod.getStatusText()
                    + ": "
                    + httpMethod.getResponseBodyAsString());
        }
        this.handleHttpMethod(httpMethod);
    }

    protected void handleHttpMethod(HttpMethod httpMethod) throws Exception {
        Header[] headers = httpMethod.getResponseHeaders();
        this.responseHeader = new HashMap();

        for (int index = 0; index < headers.length; index++) {
            responseHeader.put(headers[index].getName(), headers[index].getValue());
        }

        this.responseBody = httpMethod.getResponseBodyAsStream();
    }

    private void configureRequest(EntityEnclosingMethod httpMethod, String body, String type, String encoding) throws UnsupportedEncodingException {
        httpMethod.setRequestBody(body);
        httpMethod.setRequestHeader(new Header("Content-Type", type));
        httpMethod.setRequestHeader(new Header("Content-Length", String.valueOf(body.getBytes(encoding).length)));

        httpMethod.setRequestHeader(new Header("User-Agent", ChibaBean.getAppInfo()));
    }

}

//end of class
