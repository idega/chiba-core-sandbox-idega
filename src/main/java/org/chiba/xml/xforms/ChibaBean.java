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
package org.chiba.xml.xforms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.session.DefaultSerializer;
import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.events.XMLEventService;
import org.chiba.xml.events.impl.DefaultXMLEventInitializer;
import org.chiba.xml.events.impl.DefaultXMLEventService;
import org.chiba.xml.events.impl.XercesXMLEventFactory;
import org.chiba.xml.xforms.config.Config;
import org.chiba.xml.xforms.config.XFormsConfigException;
import org.chiba.xml.xforms.connector.ConnectorFactory;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.ui.AbstractFormControl;
import org.chiba.xml.xforms.ui.Repeat;
import org.chiba.xml.xforms.ui.Upload;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is an implementation of a W3C XForms 1.0 conformant
 * XForms processor.
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: ChibaBean.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class ChibaBean implements Externalizable {
    private static final Log LOGGER = LogFactory.getLog(ChibaBean.class);
    private static String APP_INFO = null;

    private static final long serialVersionUID = 140;

    /**
     * The document container object model.
     */
    private Container container = null;

    /**
     * The base URI for resolution of relative URIs.
     */
    private String baseURI = null;

    /**
     * The context map which stores application-specific parameters.
     */
    private Map context = null;


    /**
     * Creates a new ChibaBean object.
     */
    public ChibaBean() {
        LOGGER.info(getAppInfo());
    }

    /**
     * Returns Chiba version string.
     *
     * @return Chiba version string.
     */
    public static String getAppInfo() {
        synchronized (ChibaBean.class) {
            if (APP_INFO == null) {
                try {
                    BufferedInputStream stream = new BufferedInputStream(ChibaBean.class.getResourceAsStream("version.info"));
                    StringBuffer buffer = new StringBuffer("Chiba/");
                    int c;

                    while ((c = stream.read()) > -1) {
                        if (c != 10 && c != 13) {
                            buffer.append((char) c);
                        }
                    }

                    stream.close();

                    APP_INFO = buffer.toString();
                }
                catch (IOException e) {
                    APP_INFO = "Chiba";
                }
            }

            return APP_INFO;
        }
    }

    /**
     * Sets the config path.
     * <p/>
     * Checks existence of the config path and creates a config instance.
     *
     * todo: change to accept an URI
     * @param path the absolute path to the config file.
     */
    public void setConfig(String path) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("set config: " + path);
        }

        if ((path == null) || (new File(path).exists() == false)) {
            throw new XFormsConfigException("path not found: " + path);
        }

        Config.getInstance(path);
    }

    /**
     * Sets the base URI.
     * <p/>
     * The base URI is used for resolution of relative URIs occurring in the
     * document, e.g. instance sources or submission actions.
     *
     * @param uri the base URI.
     */
    public void setBaseURI(String uri) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("set base uri: " + uri);
        }

        this.baseURI = uri;
    }

    /**
     * Returns the base URI.
     *
     * @return the base URI.
     * @see #setBaseURI(String)
     */
    public String getBaseURI() {
        return this.baseURI;
    }

    /**
     * Allows to set a context map for storing application-specific parameters.
     *
     * @param context the context map to use.
     */
    public void setContext(Map context) {
        this.context = context;
    }

    /**
     * Returns the context map which stores application-specific parameters.
     *
     * @return the context map which stores application-specific parameters.
     */
    public Map getContext() {
        if (this.context == null) {
            this.context = new HashMap();
        }
        return this.context;
    }

    /**
     * Returns the document container associated with this processor.
     *
     * @return the document container associated with this processor.
     */
    public Container getContainer() {
        return this.container;
    }

    /**
     * Sets the containing document.
     * <p/>
     * A new document container is created.
     *
     * @param node Either the containing document as DOM Document or the root of
     * a DOM (sub)tree as DOM Element.
     * @throws XFormsException if the document container could not be created.
     */
    public void setXMLContainer(Node node) throws XFormsException {
        ensureContainerNotInitialized();

        Document document = toDocument(node);
        createContainer().setDocument(document);
    }

    /**
     * Sets the containing document.
     * <p/>
     * A new document container is created.
     *
     * @param uri the containing document URI.
     * @throws XFormsException if the document container could not be created.
     */
    public void setXMLContainer(URI uri) throws XFormsException {
        ensureContainerNotInitialized();

        // todo: refactor / fix uri resolution in connector factory to work without an init'd processor
        String absoluteURI = resolve(uri);
        ConnectorFactory connectorFactory = ConnectorFactory.getFactory();
        connectorFactory.setContext(getContext());
        Node node = (Node) connectorFactory.createURIResolver(absoluteURI, null).resolve();

        Document document = toDocument(node);
        createContainer().setDocument(document);
    }

    /**
     * Sets the containing document.
     * <p/>
     * A new document container is created.
     *
     * @param stream the containing document as input stream.
     * @throws XFormsException if the document container could not be created.
     */
    public void setXMLContainer(InputStream stream) throws XFormsException {
        ensureContainerNotInitialized();

        Document document;
        try {
            document = getDocumentBuilder().parse(stream);
        }
        catch (Exception e) {
            throw new XFormsException("could not create document container", e);
        }

        createContainer().setDocument(document);
    }

    /**
     * Sets the containing document.
     * <p/>
     * A new document container is created.
     *
     * @param source the containing document as input source.
     * @throws XFormsException if the document container could not be created.
     */
    public void setXMLContainer(InputSource source) throws XFormsException {
        ensureContainerNotInitialized();

        Document document;
        try {

            document = getDocumentBuilder().parse(source);
        }
        catch (Exception e) {
            throw new XFormsException("could not create document container", e);
        }

        createContainer().setDocument(document);
    }

    /**
     * Returns the containing document as DOM.
     * <p/>
     * This returns the live DOM processed by Chiba internally. Changes will
     * affect internal state and may cause malfunction. Should we better be more
     * restrictive and return a clone to prevent this ?
     *
     * @return the containing document.
     * @throws XFormsException if no document container is present.
     */
    public Document getXMLContainer() throws XFormsException {
        ensureContainerPresent();

        return this.container.getDocument();
    }

    /**
     * returns the DOM Document of the XForms Instance identified by id.
     * @param id identifier for Instance
     * @return the DOM Document of the XForms Instance identified by id
     * @throws DOMException in case the processor was not intialized or the wanted Instance does not exist
     */
    public Document getInstanceDocument(String id) throws DOMException {
        try {
            ensureContainerInitialized();
        } catch (XFormsException e) {
            throw new DOMException(DOMException.INVALID_STATE_ERR,"Processor is not intialized");
        }

        List models = container.getModels();

        Document instance=null;
        for (int i = 0; i < models.size(); i++) {
            Model model = (Model) models.get(i);
            instance = model.getInstanceDocument(id);
            if (instance != null){
                return instance;
            }
        }
        throw new DOMException(DOMException.NOT_FOUND_ERR,"Instance with id: '" + id + "' not found");
    }

    /**
     * Bootstraps processor initialization.
     * <p/>
     * Use this method after setXMLContainer() and (optionally)
     * setInstanceData() have been called to actually start the processing.
     *
     * @throws XFormsException if no document container is present or an error
     * occurred during init.
     */
    public void init() throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("init");
        }

        ensureContainerPresent();
        ensureContainerNotInitialized();

        this.container.init();
    }

    /**
     * Dispatches an event of the given type to the specified target.
     *
     * @param id the id of the event target.
     * @param event the event type.
     * @throws XFormsException if no document container is present or an error
     * occurred during dispatch.
     */
    public boolean dispatch(String id, String event) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("dispatch: id: " + id + ", event: " + event);
        }

        ensureContainerPresent();
        ensureContainerInitialized();

        return this.container.dispatch(id, event);
    }

    /**
     * Checks wether the value of the specified form control might have changed.
     *
     * @param id the id of the form control.
     * @param value the value to check.
     * @return <code>true</code> if the given value differs from the specified
     * control's value, otherwise <code>false</code>.
     * @throws XFormsException if no document container is present or the
     * control is unknown.
     */
    public final boolean hasControlChanged(String id, String value) throws XFormsException {
        ensureContainerPresent();
        ensureContainerInitialized();

        // sanity checks
        XFormsElement element = this.container.lookup(id);
        if (element == null || !(element instanceof AbstractFormControl)) {
            throw new XFormsException("id '" + id + "' does not identify a form control");
        }

        // check control value
        AbstractFormControl control = (AbstractFormControl) element;
        Object controlValue = control.getValue();
        if(controlValue == null) {
            // prevents controls being not bound or disabled from updates
            return false;
        }
        //todo: rather doubt that this equality check works correct
        return !controlValue.equals(value);
    }

    /**
     * Checks wether the datatype of the specified form control is of the given
     * type.
     *
     * @param id the id of the form control.
     * @param type the fully qualified type name.
     * @return <code>true</code> if the control's datatype equals to or is
     * derived by restriction from the specified type, otherwise <code>false</code>.
     * @throws XFormsException if no document container is present or the
     * control is unknown.
     */
    public final boolean hasControlType(String id, String type) throws XFormsException {
        ensureContainerPresent();
        ensureContainerInitialized();

        // sanity checks
        XFormsElement element = this.container.lookup(id);
        if (element == null || !(element instanceof AbstractFormControl)) {
            throw new XFormsException("id '" + id + "' does not identify a form control");
        }

        // get datatype restriction
        String datatype = ((AbstractFormControl) element).getDatatype();
        if (datatype != null) {
            return element.getModel().getValidator().isRestricted(type, datatype);
        }

        return false;
    }

    /**
     * This method updates the value of an UI control. Upload controls cannot
     * be updated with this method.
     * <p/>
     * The value will be changed regardless wether there was a change.
     * Applications have to call this method to propagate their UI value
     * changes to the Chiba processor. They should check for changes via
     * hasControlChanged() before using this method.
     *
     * @param id the id of the control.
     * @param value the new value for the control.
     * @throws XFormsException if no document container is present, the control
     * is unknown or an error occurred during value update.
     */
    public final void updateControlValue(String id, String value) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("update control value: id: " + id + ", value: " + value);
        }

        ensureContainerPresent();
        ensureContainerInitialized();

        // sanity checks
        XFormsElement element = this.container.lookup(id);
        if (element == null || !(element instanceof AbstractFormControl)) {
            throw new XFormsException("id '" + id + "' does not identify a form control");
        }
        if (element instanceof Upload) {
            throw new XFormsException("upload cannot be updated with this method");
        }

        // update control value
        AbstractFormControl control = (AbstractFormControl) element;
        control.setValue(value);
    }

    /**
     * This method updates the value of an upload control. Other controls cannot
     * be updated with this method.
     *
     * @param id the id of the control.
     * @param mediatype the mediatype of the uploaded resource.
     * @param filename the filename of the uploaded resource.
     * @param data the uploaded data as byte array.
     * @throws XFormsException if no document container is present, the control
     * is unknown or an error occurred during value update.
     */
    public final void updateControlValue(String id, String mediatype, String filename, byte[] data) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("update control value: id: " + id + ", mediatype: " + mediatype + ", filename: " + filename + ", data: " + (data != null ? data.length + " bytes" : "null"));
        }

        ensureContainerPresent();
        ensureContainerInitialized();

        // sanity checks
        XFormsElement element = this.container.lookup(id);
        if (element == null || !(element instanceof Upload)) {
            throw new XFormsException("id '" + id + "' does not identify an upload control");
        }

        // update upload control
        Upload upload = (Upload) element;
        upload.setValue(data, filename, mediatype);
    }

    /**
     * Updates the specified Repeat's index.
     *
     * @param id the repeat id.
     * @param index the repeat index.
     * @throws XFormsException if no document container is present, the repeat
     * is unkown or an error occurred during index update.
     */
    public void updateRepeatIndex(String id, int index) throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("update repeat index: id: " + id + ", index: " + index);
        }

        ensureContainerPresent();
        ensureContainerInitialized();

        // sanity checks
        XFormsElement element = this.container.lookup(id);
        if (element == null || !(element instanceof Repeat)) {
            throw new XFormsException("id '" + id + "' does not identify a repeat");
        }

        // update repeat index
        Repeat repeat = (Repeat) element;
        repeat.setIndex(index);

        // update model dependencies and UI
        Model model = repeat.getModel();
        this.container.dispatch(model.getTarget(), XFormsEventNames.REBUILD, null);
        this.container.dispatch(model.getTarget(), XFormsEventNames.RECALCULATE, null);
        this.container.dispatch(model.getTarget(), XFormsEventNames.REVALIDATE, null);
        this.container.dispatch(model.getTarget(), XFormsEventNames.REFRESH, null);
    }

    /**
     * Finishes processor operation.
     *
     * This operation degrades gracefully, i.e. neither subsequent calls will
     * lead to an exception nor an attempt to shutdown a non-initialized
     * processor.
     *
     * @throws XFormsException if an error occurred during shutdown.
     */
    public void shutdown() throws XFormsException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shutdown");
        }

        if (this.container != null) {
            this.container.shutdown();
            this.container = null;
        }
        else {
            LOGGER.warn("shutdown: container not present");
        }
    }

    private Container createContainer() {
        XMLEventService eventService = new DefaultXMLEventService();
        eventService.setXMLEventFactory(new XercesXMLEventFactory());
        eventService.setXMLEventInitializer(new DefaultXMLEventInitializer());

        this.container = new Container(this);
        this.container.setXMLEventService(eventService);

        return this.container;
    }

    private Document toDocument(Node node) throws XFormsException {
        // ensure xerces dom
        if (node instanceof org.apache.xerces.dom.DocumentImpl) {
            return (Document) node;
        }

        Document document = getDocumentBuilder().newDocument();
        if (node instanceof Document) {
            node = ((Document) node).getDocumentElement();
        }
        document.appendChild(document.importNode(node, true));

        return document;
    }

    private DocumentBuilder getDocumentBuilder() throws XFormsException {
        // ensure xerces dom
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            factory.setAttribute("http://apache.org/xml/properties/dom/document-class-name", "org.apache.xerces.dom.DocumentImpl");

            return factory.newDocumentBuilder();
        }
        catch (Exception e) {
            throw new XFormsException(e);
        }
    }

    private void ensureContainerPresent() throws XFormsException {
        if (this.container == null) {
            throw new XFormsException("document container not present");
        }
    }

    private void ensureContainerInitialized() throws XFormsException {
        if (this.container == null || !this.container.isModelConstructDone()) {
            throw new XFormsException("document container not initialized");
        }
    }

    private void ensureContainerNotInitialized() throws XFormsException {
        if (this.container != null && this.container.isModelConstructDone()) {
            throw new XFormsException("document container already initialized");
        }
    }

    // todo: move this code away
    private String resolve(URI relative) throws XFormsException {
        if (relative.isAbsolute() || relative.isOpaque()) {
            return relative.toString();
        }

        if (this.baseURI == null) {
            throw new XFormsException("base uri not present");
        }

        try {
            return new URI(this.baseURI).resolve(relative).toString();
        }
        catch (URISyntaxException e) {
            throw new XFormsException(e);
        }
    }

    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        DefaultSerializer serializer = new DefaultSerializer(this);
        Document serializedForm = serializer.serialize();

        StringWriter stringWriter = new StringWriter();
        Transformer transformer = null;
        StreamResult result = new StreamResult(stringWriter);
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.transform(new DOMSource(serializedForm), result);
        } catch (TransformerConfigurationException e) {
            throw new IOException("TransformerConfiguration invalid: " + e.getMessage());
        } catch (TransformerException e) {
            throw new IOException("Error during serialization transform: " + e.getMessage());
        }
        objectOutput.writeUTF(stringWriter.getBuffer().toString());
        objectOutput.flush();
        objectOutput.close();

    }

    /**
     * reads serialized host document from ObjectInputStream and parses the resulting String
     * to a DOM Document. After that the host document is passed to the processor. init() is NOT yet
     * called on the processor to allow an using application to do its own configuration work (like
     * setting of baseURI and passing of context params).
     *
     * todo: rethink the question of the baseURI - is this still necessary when deaserializing? Presumably yes to further allow dynamic resolution.
     * @param objectInput
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("deserializing XForms host document");
        }
        String read = objectInput.readUTF();
        Document host=null;
        try {
            host = DOMUtil.parseString(read,true,false);
            setXMLContainer(host.getDocumentElement());
        } catch (ParserConfigurationException e) {
            throw new IOException("Parser misconfigured: " + e.getMessage());
        } catch (SAXException e) {
            throw new IOException("Parsing failed: " + e.getMessage());
        } catch (XFormsException e) {
            throw new IOException("An XForms error occurred when passing the host document: " + e.getMessage());
        }
        
    }
}

// end of class
