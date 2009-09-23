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
package org.chiba.adapter.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xslt.TransformerService;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;

/**
 * An XSLT-based UI generator implementation.
 *
 * todo: unit testing
 *
 * @author Ulrich Nicolas Liss&eactue;
 * @version $Id: XSLTGenerator.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class XSLTGenerator implements UIGenerator {

    private static Log LOGGER = LogFactory.getLog(XSLTGenerator.class);

    private Source source = null;
    private Result result = null;
    private HashMap parameters = null;
    private TransformerService transformerService = null;
    private URI stylesheetURI = null;
    private HashMap properties = null;

    /**
     * Creates a new XSLT-based UI generator.
     */
    public XSLTGenerator() {
    }

    // implementation of 'org.chiba.adapter.ui.UIGenerator'

    /**
     * Sets the generator input.
     *
     * @param input the generator output.
     * @throws RuntimeException if the specified input cannot be converted to a
     * JAXP Source object.
     */
    public void setInput(Object input) {
        this.source = createInputSource(input);
    }

    /**
     * Sets the generator output.
     *
     * @param output the generator output.
     * @throws RuntimeException if the specified input cannot be converted to a
     * JAXP Result object.
     */
    public void setOutput(Object output) {
        this.result = createOutputResult(output);
    }

    /**
     * Returns a generator parameter.
     *
     * @param name the parameter name.
     */
    public Object getParameter(String name) {
        if (this.parameters == null) {
            return null;
        }

        return this.parameters.get(name);
    }

    /**
     * Sets a generator parameter.
     *
     * @param name the parameter name.
     * @param value the parameter value.
     */
    public void setParameter(String name, Object value) {
        if (this.parameters == null) {
            this.parameters = new HashMap();
        }

        this.parameters.put(name, value);
    }

    /**
     * Generates a client-specific representation of the XForms container
     * document by applying an XSLT transformation to Chiba's internal DOM.
     *
     * @throws XFormsException if an error occurred during the generation process.
     */
    public void generate() throws XFormsException {
        try {
            // sanity checks
            if (this.transformerService == null) {
                throw new IllegalStateException("transformer service missing");
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("generate: using stylesheet at " + this.stylesheetURI);
            }
            Transformer transformer = this.transformerService.getTransformer(this.stylesheetURI);
            prepareTransformer(transformer);

            long start = 0l;
            if (LOGGER.isDebugEnabled()) {
                start = System.currentTimeMillis();
            }

            transformer.transform(this.source, this.result);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("generate: transformation needed " + (System.currentTimeMillis() - start) + " ms");
            }
        }
        catch (Exception e) {
            throw new XFormsException(e);
        }
    }

    // XSLT generator members

    /**
     * Returns the transformer service to be used for transformations.
     *
     * @return the transformer service to be used for transformations.
     */
    public TransformerService getTransformerService() {
        return this.transformerService;
    }

    /**
     * Sets the transformer service to be used for transformations.
     *
     * @param transformerService the transformer service to be used for transformations.
     */
    public void setTransformerService(TransformerService transformerService) {
        this.transformerService = transformerService;
    }

    /**
     * Returns the stylesheet URI.
     *
     * @return the stylesheet URI.
     */
    public URI getStylesheetURI() {
        return this.stylesheetURI;
    }

    /**
     * Sets the stylesheet URI.
     *
     * @param stylesheetURI the stylesheet URI.
     */
    public void setStylesheetURI(URI stylesheetURI) {
        this.stylesheetURI = stylesheetURI;
    }

    /**
     * Returns the specified XSLT output property.
     *
     * @param name the name of the output property.
     * @return the specified XSLT output property.
     */
    public String getOutputProperty(String name) {
        if (this.properties == null) {
            return null;
        }

        return (String) this.properties.get(name);
    }

    /**
     * Sets the specified XSLT output property.
     *
     * @param name the name of the output property.
     * @param value the value of the output property.
     */
    public void setOutputProperty(String name, String value) {
        if (this.properties == null) {
            this.properties = new HashMap();
        }

        this.properties.put(name, value);
    }

    // template methods for easy subclassing

    /**
     * Prepares the transformer for the transformation process.
     * <p/>
     * All output properties as well as all stylesheet parameters are set.
     *
     * @param transformer the transformer.
     * @return the prepared transformer.
     */
    protected Transformer prepareTransformer(Transformer transformer) {
        if (this.properties != null) {
            String name;
            String value;
            Iterator iterator = this.properties.keySet().iterator();
            while (iterator.hasNext()) {
                name = (String) iterator.next();
                value = (String) this.properties.get(name);

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("prepare: setting output property " + name + "=" + value);
                }
                transformer.setOutputProperty(name, value);
            }
        }
        else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("prepare: no output properties to be set");
            }
        }

        if (this.parameters != null) {
            String name;
            Object value;
            Iterator iterator = this.parameters.keySet().iterator();
            while (iterator.hasNext()) {
                name = (String) iterator.next();
                value = this.parameters.get(name);

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("prepare: setting stylesheet parameter " + name + "=" + value);
                }
                transformer.setParameter(name, value);
            }
        }
        else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("prepare: no stylesheet parameters to be set");
            }
        }

        return transformer;
    }

    /**
     * Creates a JAXP Source from the specified input object.
     * <p/>
     * Follwoing object models are supported:
     * <ul>
     * <li><code>org.w3c.dom.Node</code></li>
     * <li><code>org.xml.sax.InputSource</code></li>
     * <li><code>java.io.InputStream</code></li>
     * <li><code>java.io.Reader</code></li>
     * <li><code>java.io.File</code></li>
     * </ul>
     *
     * @param input the input object.
     * @return a JAXP Source.
     * @throws NullPointerException if the input object is <code>null</code>.
     * @throws IllegalArgumentException if the object model is not supported.
     */
    protected Source createInputSource(Object input) {
        if (input == null) {
            throw new NullPointerException("parameter 'input' must not be null");
        }

        // DOM
        if (input instanceof Node) {
            return new DOMSource((Node) input);
        }

        // SAX
        if (input instanceof InputSource) {
            return new SAXSource((InputSource) input);
        }

        // Stream
        if (input instanceof InputStream) {
            return new StreamSource((InputStream) input);
        }
        if (input instanceof Reader) {
            return new StreamSource((Reader) input);
        }
        if (input instanceof File) {
            return new StreamSource((File) input);
        }

        throw new IllegalArgumentException(input.getClass().getName() + " not supported as input");
    }

    /**
     * Creates a JAXP Result from the specified input object.
     * <p/>
     * Follwoing object models are supported:
     * <ul>
     * <li><code>org.w3c.dom.Node</code></li>
     * <li><code>org.xml.sax.ContentHandler</code></li>
     * <li><code>java.io.OutputStream</code></li>
     * <li><code>java.io.Writer</code></li>
     * <li><code>java.io.File</code></li>
     * </ul>
     *
     * @param output the output object.
     * @return a JAXP Result.
     * @throws NullPointerException if the output object is <code>null</code>.
     * @throws IllegalArgumentException if the object model is not supported.
     */
    protected Result createOutputResult(Object output) {
        if (output == null) {
            throw new NullPointerException("parameter 'output' must not be null");
        }

        // DOM
        if (output instanceof Node) {
            return new DOMResult((Node) output);
        }

        // SAX
        if (output instanceof ContentHandler) {
            return new SAXResult((ContentHandler) output);
        }

        // Stream
        if (output instanceof OutputStream) {
            return new StreamResult((OutputStream) output);
        }
        if (output instanceof Writer) {
            return new StreamResult((Writer) output);
        }
        if (output instanceof File) {
            return new StreamResult((File) output);
        }

        throw new IllegalArgumentException(output.getClass().getName() + " not supported as output");
    }
}
