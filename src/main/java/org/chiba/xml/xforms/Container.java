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
 */
package org.chiba.xml.xforms;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.ElementImpl;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.events.XMLEventService;
import org.chiba.xml.ns.NamespaceConstants;
import org.chiba.xml.xforms.config.XFormsConfigException;
import org.chiba.xml.xforms.connector.ConnectorFactory;
import org.chiba.xml.xforms.core.BindingResolver;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.exception.XFormsErrorIndication;
import org.chiba.xml.xforms.exception.XFormsException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a complete XForms document. It encapsulates the DOM
 * document.
 * <p/>
 * The XForms document may consist of pure XForms markup or may contain mixed
 * markup from other namespaces (e.g. HTML, SVG, WAP).
 * <p/>
 * Responsibilities are: <ul> <li>model creation and initialization</li>
 * <li>event creation, dispatching and error handling</li> <li>element
 * registry</li> </ul>
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @author Eduardo Millan <emillan@users.sourceforge.net>
 * @version $Id: Container.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class Container {
    private static final Log LOGGER = LogFactory.getLog(Container.class);
    private BindingResolver bindingResolver;
    private ChibaBean processor;
    private ConnectorFactory connectorFactory;
    private Document document;
    private Element root;
    private XMLEventService eventService;
    private JXPathContext rootContext;
    private List models;
    private Map xFormsElements;
    private XFormsElementFactory elementFactory;
    private CustomElementFactory customElementFactory;
    private boolean modelConstructDone = false;
    private int idCounter = 0;
    private List eventExceptions;
    public static final String XFORMS_1_0 = "1.0";
    public static final String XFORMS_1_1 = "1.1";

    /**
     * associates DocumentContainer with Processor.
     *
     * @param processor the Processor object
     */
    public Container(ChibaBean processor) {
        this.processor = processor;
    }

    /**
     * returns the processor for this container
     *
     * @return the processor for this container
     */
    public ChibaBean getProcessor() {
        return processor;
    }

    /**
     * Returns the binding resolver.
     *
     * @return the binding resolver.
     */
    public BindingResolver getBindingResolver() {
        if (this.bindingResolver == null) {
            this.bindingResolver = new BindingResolver();
        }

        return this.bindingResolver;
    }

    /**
     * Returns the connector factory.
     *
     * @return the connector factory.
     */
    public ConnectorFactory getConnectorFactory() {
        if (this.connectorFactory == null) {
            try {
                this.connectorFactory = ConnectorFactory.getFactory();
                this.connectorFactory.setContext(this.processor.getContext());
            } catch (XFormsConfigException xce) {
                throw new RuntimeException(xce);
            }
        }

        return this.connectorFactory;
    }

    /**
     * Returns the XForms element factory which is responsible for creating
     * objects for all XForms elements in the input document.
     *
     * @return the XForms element factory
     * @see XFormsElementFactory
     */
    public XFormsElementFactory getElementFactory() {
        if (this.elementFactory == null) {
            this.elementFactory = new XFormsElementFactory();
        }

        return this.elementFactory;
    }

    /**
     * Returns the custom element factory which is responsible for creating
     * objects for all custom elements in the input document.
     *
     * @return the custom element factory
     * @see CustomElementFactory
     */
    public CustomElementFactory getCustomElementFactory() throws XFormsException {
        if (this.customElementFactory == null) {
            this.customElementFactory = new CustomElementFactory();
        }

        return this.customElementFactory;
    }

    /**
     * Returns the XML Event Service.
     *
     * @return the XML Event Service.
     */
    public XMLEventService getXMLEventService() {
        return this.eventService;
    }

    /**
     * Sets the XML Event Service.
     *
     * @param eventService the XML Event Service.
     */
    public void setXMLEventService(XMLEventService eventService) {
        this.eventService = eventService;
    }

    /**
     * passes the XML container document which contains XForms markup. This
     * method must be called before init() and already builds up the
     * NamespaceResolver and RootContext objects.
     *
     * @param document a DOM Document
     */
    public void setDocument(Document document) {
        this.document = document;
        this.root = this.document.getDocumentElement();

        // check for chiba namespace
        if (!this.root.hasAttributeNS(NamespaceConstants.XMLNS_NS, NamespaceConstants.CHIBA_PREFIX)) {
            this.root.setAttributeNS(NamespaceConstants.XMLNS_NS,
                    "xmlns:" + NamespaceConstants.CHIBA_PREFIX,
                    NamespaceConstants.CHIBA_NS);
        }

        this.rootContext = JXPathContext.newContext(this.root);
    }

    /**
     * Returns the container as a dom tree representing an (external) XML
     * representation of the xforms container.  The return value is live, that
     * means changes to the return tree affects the internal container
     * representation.
     *
     * @return the container as a document.
     */
    public Document getDocument() {
        return this.document;
    }

    /**
     * returns the root JXPathContext for the whole Document
     *
     * @return the root JXPathContext for the whole Document
     */
    public JXPathContext getRootContext() {
        return this.rootContext;
    }

    /**
     * stores this container as userobject in document element and triggers
     * model initialization
     *
     * @throws XFormsException
     */
    public void init() throws XFormsException {
        ((ElementImpl) this.root).setUserData(this);

        String version = getVersion();
        LOGGER.info("processing XForms " + version);

        // trigger model initialization
        initModels();

        if(version != null && version.indexOf(XFORMS_1_0) == -1){
        
            HashMap map = new HashMap();
            map.put("message", "XForms 1.1 is not yet fully supported - terminating. If testing already existing features of 1.1 please use 1.0 as version string.");

            dispatch((EventTarget)getDefaultModel().getElement(),XFormsEventNames.VERSION_EXCEPTION, map);
            this.processor.shutdown();
        }
    }

    /**
     * @return the version of XForms required by form author or if attribute not present "1.0" as default
     */
    public String getVersion(){
        NodeList nl =root.getElementsByTagNameNS(NamespaceConstants.XFORMS_NS,"model");
        if(nl.getLength() > 0){
            Node defaultModel = nl.item(0);
            if(XFormsElement.getXFormsAttribute((Element)defaultModel,"version") != null){
                String versionString = XFormsElement.getXFormsAttribute((Element)defaultModel,"version");
                if(versionString.indexOf(XFORMS_1_0) != -1 || versionString.indexOf(XFORMS_1_1) != -1 ){
                    return versionString;
                }
            }
        }
        return XFORMS_1_0;
    }


    /**
     * Triggers model destruction.
     *
     * @throws XFormsException
     */
    public void shutdown() throws XFormsException {
        if (this.models != null) {
            Model model;
            for (int index = 0; index < this.models.size(); index++) {
                model = (Model) this.models.get(index);
                // todo: release resources in Model.performDefault()
                dispatch(model.getTarget(), XFormsEventNames.MODEL_DESTRUCT, null);
            }
        }
    }

    /**
     * Adds a DOM Event Listener to the specified target node.
     *
     * @param targetId the id of the target node.
     * @param eventType the event type.
     * @param eventListener the Event Listener.
     * @param useCapture use event capturing or not.
     * @throws XFormsException if the target node could not be found.
     */
    public void addEventListener(String targetId, String eventType, EventListener eventListener, boolean useCapture) throws XFormsException {
        EventTarget eventTarget = lookupEventTarget(targetId);
        if (eventTarget != null) {
            eventTarget.addEventListener(eventType, eventListener, useCapture);
            return;
        }

        throw new XFormsException("event target '" + targetId + "' not found");
    }

    /**
     * Removes a DOM Event Listener from the specified target node.
     *
     * @param targetId the id of the target node.
     * @param eventType the event type.
     * @param eventListener the Event Listener.
     * @param useCapture use event capturing or not.
     * @throws XFormsException if the target node could not be found.
     */
    public void removeEventListener(String targetId, String eventType, EventListener eventListener, boolean useCapture) throws XFormsException {
        EventTarget eventTarget = lookupEventTarget(targetId);
        if (eventTarget != null) {
            eventTarget.addEventListener(eventType, eventListener, useCapture);
            return;
        }

        throw new XFormsException("event target '" + targetId + "' not found");
    }

    /**
     * Dispatches a DOM Event to the specified target node.
     *
     * @param targetId the id of the target node.
     * @param eventType the event type.
     * @return <code>true</code> if the event has been cancelled during dispatch,
     * otherwise <code>false</code>.
     * @throws XFormsException if the target node could not be found.
     */
    public boolean dispatch(String targetId, String eventType) throws XFormsException {
        return dispatch(targetId, eventType, null);
    }

    /**
     * Dispatches a DOM Event to the specified target node.
     *
     * @param targetId the id of the target node.
     * @param eventType the event type.
     * @param info a context information object.
     * @return <code>true</code> if the event has been cancelled during dispatch,
     * otherwise <code>false</code>.
     * @throws XFormsException if the target node could not be found.
     */
    public boolean dispatch(String targetId, String eventType, Object info) throws XFormsException {
        return dispatch(targetId, eventType, info, true, true);
    }

    /**
     * Dispatches a DOM Event to the specified target node.
     * <p/>
     * The bubbles and cancelable parameters are ignored for well-known event types.
     *
     * @param targetId the id of the target node.
     * @param eventType the event type.
     * @param info a context information object.
     * @param bubbles specifies wether the event can bubble.
     * @param cancelable specifies wether the event's default action can be prevented.
     * @return <code>true</code> if the event has been cancelled during dispatch,
     * otherwise <code>false</code>.
     * @throws XFormsException if the target node could not be found.
     */
    public boolean dispatch(String targetId, String eventType, Object info, boolean bubbles, boolean cancelable) throws XFormsException {
        EventTarget eventTarget = lookupEventTarget(targetId);
        if (eventTarget != null) {
            return dispatch(eventTarget, eventType, info, bubbles, cancelable);
        }

        throw new XFormsException("event target '" + targetId + "' not found");
    }

    /**
     * Dispatches a DOM Event to the specified target node.
     *
     * @param eventTarget the target node.
     * @param eventType the event type.
     * @param info a context information object.
     * @return <code>true</code> if the event has been cancelled during dispatch,
     * otherwise <code>false</code>.
     * @throws XFormsException if the target node could not be found.
     */
    public boolean dispatch(EventTarget eventTarget, String eventType, Object info) throws XFormsException {
        return dispatch(eventTarget, eventType, info, true, true);
    }

    /**
     * Dispatches a DOM Event to the specified target node.
     * <p/>
     * The bubbles and cancelable parameters are ignored for well-known event types.
     *
     * @param eventTarget the target node.
     * @param eventType the event type.
     * @param info a context information object.
     * @param bubbles specifies wether the event can bubble.
     * @param cancelable specifies wether the event's default action can be prevented.
     * @return <code>true</code> if the event has been cancelled during dispatch,
     * otherwise <code>false</code>.
     * @throws XFormsException if the target node could not be found.
     */
    public boolean dispatch(EventTarget eventTarget, String eventType, Object info, boolean bubbles, boolean cancelable) throws XFormsException {
        boolean result = false;
        XFormsException xFormsException = null;

        try {
            result = this.eventService.dispatch(eventTarget, eventType, bubbles, cancelable, info);
        }
        finally {
            // exception(s) during event flow ?
            if (this.eventExceptions != null && this.eventExceptions.size() > 0) {
                Exception exception = (Exception) this.eventExceptions.get(0);

                if (exception instanceof XFormsErrorIndication) {
                    if (((XFormsErrorIndication) exception).isFatal()) {
                        // downcast fatal error for rethrowal
                        xFormsException = (XFormsException) exception;
                    }
                    else {
                        LOGGER.warn("dispatch: non-fatal xforms error", exception);
                    }
                }
                else if (exception instanceof XFormsException) {
                    // downcast exception for rethrowal
                    xFormsException = (XFormsException) exception;
                }
                else {
                    // wrap exception for rethrowal
                    xFormsException = new XFormsException(exception);
                }

                // get rid of follow-up exceptions
                this.eventExceptions.clear();
            }
        }

        if (xFormsException != null) {
            // rethrow exception
            LOGGER.error("dispatch: exception during event flow", xFormsException);
            throw xFormsException;
        }

        return result;
    }

    /**
     * Stores an exception until the currently ongoing event flow has finished.
     *
     * @param exception an exception occurring during event flow.
     */
    public void handleEventException(Exception exception) {


        LOGGER.warn("handle event exception: " + exception.getClass().getName() + " kept for rethrowal after dispatch() has finished");

        if (this.eventExceptions == null) {
            this.eventExceptions = new ArrayList();
        }

        if (exception instanceof XFormsErrorIndication) {
            XFormsErrorIndication indication = (XFormsErrorIndication) exception;

            LOGGER.warn("XForms Error: " + indication.getMessage());
            if (!indication.isHandled()) {
                // dispatch error indication event
                try {
                    dispatch(indication.getEventTarget(),
                            indication.getEventType(),
                            indication.getContextInfo());
                }
                catch (XFormsException e) {
                    LOGGER.error("handle event exception: exception during error indication event", e);
                }

                // set error indication handled
                indication.setHandled();
            }
        }

        // keep exception
        this.eventExceptions.add(exception);
    }

    /**
     * Returns the specified XForms element.
     *
     * @param id the id of the XForms element.
     * @return the specified XForms element or <code>null</code> if the id is
     * unknown.
     */
    public XFormsElement lookup(String id) {
        if (this.xFormsElements != null) {
            return (XFormsElement) this.xFormsElements.get(id);
        }

        return null;
    }

    /**
     * Generates an unique identifier.
     *
     * @return an unique identifier.
     */
    public String generateId() {
        // todo: build external is service
        String id = "C" + (++this.idCounter);

        while (lookup(id) != null) {
            id = "C" + (++this.idCounter);
        }

        return id;
    }

    /**
     * Registers the specified XForms element with this <code>container</code>.
     * <p/>
     * Attaches Container as listener for XForms exception events.
     *
     * @param element the XForms element to be registered.
     */
    public void register(XFormsElement element) {
        if (this.xFormsElements == null) {
            this.xFormsElements = new HashMap();
        }

        this.xFormsElements.put(element.getId(), element);
    }

    /**
     * Deregisters the specified XForms element with this
     * <code>container</code>.
     *
     * @param element the XForms element to be deregistered.
     */
    public void deregister(XFormsElement element) {
        if (this.xFormsElements != null) {
            this.xFormsElements.remove(element.getId());
        }
    }

    /**
     * convenience method to return default model without knowing its id.
     *
     * @return returns the first model in document order
     */
    public Model getDefaultModel() throws XFormsException {
        return getModel(null);
    }

    /**
     * return a model object by its id. If id is null or an empty string, the
     * default model (first found in document order) is returned.
     */
    public Model getModel(String id) throws XFormsException {
        if ((id == null) || (id.length() == 0)) {
            if (this.models != null && this.models.size() > 0) {
                return (Model) this.models.get(0);
            }

            throw new XFormsException("default model not found");
        }

        if (this.models != null) {
            Model model;
            for (int index = 0; index < this.models.size(); index++) {
                model = (Model) this.models.get(index);

                if (model.getId().equals(id)) {
                    return model;
                }
            }
        }

        throw new XFormsException("model '" + id + "' not found");
    }

    /**
     * Returns all models.
     *
     * @return all models.
     */
    public List getModels() {
        return this.models;
    }

    /**
     * returns true, if the default-processing for xforms-model-construct-done
     * Event has been executed already.
     *
     * @return true, if the default-processing for xforms-model-construct-done
     *         Event has been executed already.
     */
    public boolean isModelConstructDone() {
        return this.modelConstructDone;
    }

    /**
     * create Model-objects which simply hold their Model-element node (formerly
     * named XForm-element).
     * <p/>
     * The first Model-element found in the container is the default-model and
     * if it has no model-id it is stored with a key of 'default' in the models
     * hashtable. Otherwise the provided id is used.
     * <p/>
     * The subsequent model-elements are stored with their id as the key. If no
     * id exists an exception is thrown (as defined by Spec).
     *
     * todo: fix to conform to http://www.w3.org/2006/03/REC-xforms-20060314-errata.html#E29
     * todo: directly call construct without dispatching event
     */
    private void initModels() throws XFormsException {
        this.models = new ArrayList();
        NodeList nl = root.getElementsByTagNameNS(NamespaceConstants.XFORMS_NS, XFormsConstants.MODEL);
        Model model;
        Element modelElement;
        
        // create all models and dispatch xforms-model-construct to all models
        for (int i = 0; i < nl.getLength(); i++) {
            modelElement = (Element) nl.item(i);

            model = (Model) getElementFactory().createXFormsElement(modelElement, null);
            model.init();

            this.models.add(model);
            dispatch(model.getTarget(), XFormsEventNames.MODEL_CONSTRUCT, null);
        }

        for (int i = 0; i < nl.getLength(); i++) {
            model = (Model) this.models.get(i);
            dispatch(model.getTarget(), XFormsEventNames.MODEL_CONSTRUCT_DONE, null);
            
            // set flag to signal that construction has been performed
            this.modelConstructDone = true;
        }

        for (int i = 0; i < nl.getLength(); i++) {
            model = (Model) this.models.get(i);
            dispatch(model.getTarget(), XFormsEventNames.READY, null);
        }
    }

    /**
     * Returns the specified Event target.
     *
     * @param id the id of the Event target.
     * @return the specified Event target or <code>null</code> if the id is
     * unknown.
     */
    private EventTarget lookupEventTarget(String id) {
        XFormsElement xFormsElement = lookup(id);
        if (xFormsElement != null) {
            return xFormsElement.getTarget();
        }

        if (this.rootContext != null) {
            Pointer pointer = this.rootContext.getPointer("//*[@id='" + id + "']");
            if (pointer != null) {
                return (EventTarget) pointer.getNode();
            }
        }

        return null;
    }

}
