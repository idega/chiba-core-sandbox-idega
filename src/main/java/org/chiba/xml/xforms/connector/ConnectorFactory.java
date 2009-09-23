// Copyright 2006 Chibacon
/*
 *
 *    Artistic License
 *
 *    Preamble
 *
 *    The intent of this document is to state the conditions under which a Package may be copied, such that
 *    the Copyright Holder maintains some semblance of artistic control over the development of the
 *    package, while giving the users of the package the right to use and distribute the Package in a
 *    more-or-less customary fashion, plus the right to make reasonable modifications.
 *
 *    Definitions:
 *
 *    "Package" refers to the collection of files distributed by the Copyright Holder, and derivatives
 *    of that collection of files created through textual modification.
 *
 *    "Standard Version" refers to such a Package if it has not been modified, or has been modified
 *    in accordance with the wishes of the Copyright Holder.
 *
 *    "Copyright Holder" is whoever is named in the copyright or copyrights for the package.
 *
 *    "You" is you, if you're thinking about copying or distributing this Package.
 *
 *    "Reasonable copying fee" is whatever you can justify on the basis of media cost, duplication
 *    charges, time of people involved, and so on. (You will not be required to justify it to the
 *    Copyright Holder, but only to the computing community at large as a market that must bear the
 *    fee.)
 *
 *    "Freely Available" means that no fee is charged for the item itself, though there may be fees
 *    involved in handling the item. It also means that recipients of the item may redistribute it under
 *    the same conditions they received it.
 *
 *    1. You may make and give away verbatim copies of the source form of the Standard Version of this
 *    Package without restriction, provided that you duplicate all of the original copyright notices and
 *    associated disclaimers.
 *
 *    2. You may apply bug fixes, portability fixes and other modifications derived from the Public Domain
 *    or from the Copyright Holder. A Package modified in such a way shall still be considered the
 *    Standard Version.
 *
 *    3. You may otherwise modify your copy of this Package in any way, provided that you insert a
 *    prominent notice in each changed file stating how and when you changed that file, and provided that
 *    you do at least ONE of the following:
 *
 *        a) place your modifications in the Public Domain or otherwise make them Freely
 *        Available, such as by posting said modifications to Usenet or an equivalent medium, or
 *        placing the modifications on a major archive site such as ftp.uu.net, or by allowing the
 *        Copyright Holder to include your modifications in the Standard Version of the Package.
 *
 *        b) use the modified Package only within your corporation or organization.
 *
 *        c) rename any non-standard executables so the names do not conflict with standard
 *        executables, which must also be provided, and provide a separate manual page for each
 *        non-standard executable that clearly documents how it differs from the Standard
 *        Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    4. You may distribute the programs of this Package in object code or executable form, provided that
 *    you do at least ONE of the following:
 *
 *        a) distribute a Standard Version of the executables and library files, together with
 *        instructions (in the manual page or equivalent) on where to get the Standard Version.
 *
 *        b) accompany the distribution with the machine-readable source of the Package with
 *        your modifications.
 *
 *        c) accompany any non-standard executables with their corresponding Standard Version
 *        executables, giving the non-standard executables non-standard names, and clearly
 *        documenting the differences in manual pages (or equivalent), together with instructions
 *        on where to get the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    5. You may charge a reasonable copying fee for any distribution of this Package. You may charge
 *    any fee you choose for support of this Package. You may not charge a fee for this Package itself.
 *    However, you may distribute this Package in aggregate with other (possibly commercial) programs as
 *    part of a larger (possibly commercial) software distribution provided that you do not advertise this
 *    Package as a product of your own.
 *
 *    6. The scripts and library files supplied as input to or produced as output from the programs of this
 *    Package do not automatically fall under the copyright of this Package, but belong to whomever
 *    generated them, and may be sold commercially, and may be aggregated with this Package.
 *
 *    7. C or perl subroutines supplied by you and linked into this Package shall not be considered part of
 *    this Package.
 *
 *    8. The name of the Copyright Holder may not be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 *    9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package org.chiba.xml.xforms.connector;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.ElementNSImpl;
import org.chiba.xml.base.XMLBaseResolver;
import org.chiba.xml.xforms.ChibaBean;
import org.chiba.xml.xforms.Container;
import org.chiba.xml.xforms.XFormsElement;
import org.chiba.xml.xforms.action.AbstractBoundAction;
import org.chiba.xml.xforms.config.Config;
import org.chiba.xml.xforms.config.XFormsConfigException;
import org.chiba.xml.xforms.core.Submission;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xpath.XPathUtil;
import org.w3c.dom.Element;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Creates connector instances according to the URI schema passed in.
 * <p/>
 * Only absolute URIs are supported. This means they must have a scheme that
 * identifies the protocol to be used.
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @author Eduardo Millan <emillan@users.sourceforge.net>
 * @version $Id: ConnectorFactory.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public abstract class ConnectorFactory {

    private static Log LOGGER = LogFactory.getLog(ConnectorFactory.class);
    private Map context = null;

    /**
     * Creates a new submission handler for the specified URI.
     *
     * @param uri the relative or absolute URI string.
     * @param element the element to start with XML Base resolution for relative
     * URIs.
     * @return a new submission handler for the specified URI.
     * @throws XFormsException if a relative URI could not be resolved, no
     * submission handler is registered for the specified URI or any error
     * occurred during submission handler creation.
     */
    public abstract SubmissionHandler createSubmissionHandler(final String uri, final Element element) throws XFormsException;

    /**
     * Creates a new URI resolver for the specified URI.
     *
     * @param uri the relative or absolute URI string.
     * @param element the element to start with XML Base resolution for relative
     * URIs.
     * @return a new URI resolver for the specified URI.
     * @throws XFormsException if a relative URI could not be resolved, if no
     * URI resolver is registered for the specified URI or any error occurred
     * during URI resolver creation.
     */
    public abstract URIResolver createURIResolver(final String uri, final Element element) throws XFormsException;

    public void setContext(Map context) {
        this.context = context;
    }

    public Map getContext() {
        return this.context;
    }

    /**
     * Returns a new connector factory loaded from configuration
     *
     * @return A new connector factory
     * @throws XFormsConfigException If a configuration error occurs
     */
    public static ConnectorFactory getFactory()
            throws XFormsConfigException {
        ConnectorFactory factory;
        String className = Config.getInstance().getConnectorFactory();

        if (className == null || className.equals("")) {
            factory = new DefaultConnectorFactory();
        }
        else {
            try {
                Class clazz = Class.forName(className);
                factory = (ConnectorFactory) clazz.newInstance();
            }
            catch (ClassNotFoundException cnfe) {
                throw new XFormsConfigException(cnfe);
            }
            catch (ClassCastException cce) {
                throw new XFormsConfigException(cce);
            }
            catch (InstantiationException ie) {
                throw new XFormsConfigException(ie);
            }
            catch (IllegalAccessException iae) {
                throw new XFormsConfigException(iae);
            }
        }

        return factory;

    }

    /**
     * resolves URI starting with given URI and start Element.<br> [1] tries to
     * resolve input URI by climbing up the tree and looking for xml:base
     * Attributes<br> [2] if that fails the URI is resolved against the baseURI
     * of the processor
     *
     * @param uri the URI string to start with
     * @param element the start Element
     * @return an evaluated URI object
     * @throws XFormsException if the URI has syntax errors
     */
    public URI getAbsoluteURI(String uri, Element element) throws XFormsException {

        String uriString = evalAttributeValueTemplates(uri,element);
        try {
            // resolve xml base
            String baseUri = XMLBaseResolver.resolveXMLBase(element, uriString);

            if (baseUri.equals(uriString) && !(new URI(baseUri).isAbsolute())) {
                // resolve chiba base uri
                ChibaBean processor = getProcessor(element);
                if (processor.getBaseURI() != null) {
                    return new URI(processor.getBaseURI()).resolve(uriString);
                }

                throw new XFormsException("no base uri present");
            }

            // return resolved xml base uri
            return new URI(baseUri);
        }
        catch (URISyntaxException e) {
            throw new XFormsException(e);
        }
    }

    /**
     * evaluates Attribute values that contain 'Attribute Value Templates' as known from XSL. Evaluation
     * will recognize two syntax variants:<br>
     * - Variable name starting with '$' enclosed by curly brackets e.g. {$foo}<br>
     * - XPath statements e.g. {/root/somepath[@id]}<br>
     *
     * @param uri
     * @param element
     * @return
     * @throws XFormsException
     */
    public String evalAttributeValueTemplates(String uri,Element element) throws XFormsException {
        String toReplace = uri;
        int start;
        int end;
        String valueTemplate;
        String value="";
        StringBuffer substitutedString = new StringBuffer();
        boolean hasTokens = true;
        while (hasTokens) {
            start = toReplace.indexOf("{");
            end = toReplace.indexOf('}');
            if (start == -1 || end == -1) {
                hasTokens = false; //exit
                substitutedString.append(toReplace);
            }
            else {
                substitutedString.append(toReplace.substring(0, start));
                valueTemplate = toReplace.substring(start + 1, end);

                //distinguish normal valueTemplate versus context $var
                if(valueTemplate.startsWith("$")){
                    //read key from context
                    valueTemplate = valueTemplate.substring(1);
                    if (this.context.containsKey(valueTemplate)) {
                        value = this.context.get(valueTemplate).toString();
                    }
                }else{
                    value = evalXPath(element, valueTemplate);
                }
                if(value.equals("")){
                    LOGGER.warn("valueTemplate could not be evaluated. Replacing '" + valueTemplate + "' with empty string");
                }
                substitutedString.append(value);
                toReplace = toReplace.substring(end + 1);
            }
        }
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("Uri: " + uri + " evaluated to: " + substitutedString);
        }
        return substitutedString.toString();
    }

    private String evalXPath(Element element, String valueTemplate) {
        XFormsElement xElement = (XFormsElement) ((ElementImpl) element).getUserData();
        JXPathContext context = xElement.getModel().getDefaultInstance().getInstanceContext();
        String xpath;

        if(XPathUtil.hasInstanceFunction(valueTemplate)){
            xpath = valueTemplate;
        }else{
            String path;
            if(xElement instanceof AbstractBoundAction) {
                path =  ((AbstractBoundAction)xElement).getLocationPath();
            }else if (xElement instanceof Submission){
                path = ((Submission) xElement).getLocationPath();
            }else{
                LOGGER.warn("no AVT processing for this element implemented - returning default instance root as context path");
                path =  "instance('default')";
            }
            xpath = evalJoinedXPath(path, valueTemplate);
        }
        return (String) context.getValue(xpath);
    }

    private String evalJoinedXPath( String path, String valueTemplate) {
        String[] steps = {path,valueTemplate};
        return XPathUtil.joinPathExpr(steps);
    }


    /**
     * parses the URI string for tokens which are defined to start with '{' and end with '}'. The contents found between
     * the brackets is interpreted as key for the ChibaBean context map. Tokens will be replaced with their value
     * in context map and the resulting URI is returned for further processing.
     *
     * @param uri
     * @return string with keys substituted by their values in Context.
     */
    public String applyContextProperties(String uri) throws XFormsException {
        String toReplace = uri;
        int start;
        int end;
        String key;
        String value;
        StringBuffer substitutedString = new StringBuffer();
        boolean hasTokens = true;
        while (hasTokens) {
            start = toReplace.indexOf("{$");
            end = toReplace.indexOf('}');
            if (start == -1 || end == -1) {
                hasTokens = false; //exit
                substitutedString.append(toReplace);
            }
            else {
                substitutedString.append(toReplace.substring(0, start));
                key = toReplace.substring(start + 2, end);

                if (this.context.containsKey(key)) {
                    value = this.context.get(key).toString();
                }
                else {
                    value = "";
                    LOGGER.warn("replaced non-existing key '" + key + "' with empty string");
                }

                substitutedString.append(value);
                toReplace = toReplace.substring(end + 1);
            }
        }

        return substitutedString.toString();
    }

    protected ChibaBean getProcessor(Element e) {
        ElementNSImpl elementNS = (ElementNSImpl) e.getOwnerDocument().getDocumentElement();

        Object o = elementNS.getUserData();
        if (o instanceof Container) {
            return ((Container) o).getProcessor();
        }
        return null;
    }

}
