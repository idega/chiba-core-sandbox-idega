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
package org.chiba.xml.xforms.core;

import org.apache.commons.jxpath.Pointer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.DOMInputImpl;
import org.apache.xerces.dom.NodeImpl;
import org.apache.xerces.xs.*;
import org.chiba.xml.events.DefaultAction;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.events.XMLEvent;
import org.chiba.xml.ns.NamespaceConstants;
import org.chiba.xml.ns.NamespaceResolver;
import org.chiba.xml.xforms.Container;
import org.chiba.xml.xforms.Initializer;
import org.chiba.xml.xforms.XFormsConstants;
import org.chiba.xml.xforms.XFormsElement;
import org.chiba.xml.xforms.config.Config;
import org.chiba.xml.xforms.config.XFormsConfigException;
import org.chiba.xml.xforms.connector.ConnectorFactory;
import org.chiba.xml.xforms.constraints.MainDependencyGraph;
import org.chiba.xml.xforms.constraints.SubGraph;
import org.chiba.xml.xforms.constraints.Vertex;
import org.chiba.xml.xforms.exception.XFormsComputeException;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.exception.XFormsLinkException;
import org.chiba.xml.xpath.XPathUtil;
import org.chiba.xml.xpath.impl.JXPathReferenceFinderImpl;
import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.events.Event;
import org.w3c.dom.ls.LSInput;
import org.w3c.xforms.XFormsModelElement;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

/**
 * encapsulates the model-part of a XForm: the schema, instance, submission and
 * Bindings and gives access to the individual parts.
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @author Mark Dimon
 * @author Sophie Ramel
 * @version $Id: Model.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class Model extends XFormsElement implements XFormsModelElement, DefaultAction {
    protected static Log LOGGER = LogFactory.getLog(Model.class);

    private List instances;
    private List modelBindings;
    private MainDependencyGraph mainGraph;
    private Validator validator;
    private Vector changed = new Vector();
    private List schemas;
    private boolean ready;
    private UpdateHandler updateHandler;
    private UpdateSequencer updateSequencer;
    private int modelItemCounter = 0;

    /**
     * Creates a new Model object.
     *
     * @param element the DOM Element representing this Model
     */
    public Model(Element element) {
        super(element);
    }

    /**
     * Checks wether this model is ready or not.
     *
     * @return <code>true</code> if this model has received the
     *         <code>xforms-ready</event>, otherwise <code>false</code>.
     */
    public boolean isReady() {
        return this.ready;
    }

    /**
     * returns the list of changed Vertices
     *
     * @return the list of changed Vertices
     */
    public Vector getChanged() {
        return this.changed;
    }

    // children of this model can get acess to the container
    public Container getContainer() {
        return this.container;
    }

    /**
     * returns the default instance of this model. this is always the first in
     * document order regardless of its id-attribute.
     *
     * @return the default instance of this model
     */
    public Instance getDefaultInstance() {
        if (this.instances.size() > 0) {
            return (Instance) this.instances.get(0);
        }

        return null;
    }

    /**
     * returns the instance-object for given id.
     *
     * @param id the identifier for instance
     * @return the instance-object for given id or null if no instance with that id exists.
     */
    public Instance getInstance(String id) {
        if ((id == null) || "".equals(id)) {
            return getDefaultInstance();
        }

        for (int index = 0; index < this.instances.size(); index++) {
            Instance instance = (Instance) this.instances.get(index);

            if (id.equals(instance.getId())) {
                return instance;
            }
        }

        return null;
    }

    public List getInstances(){
        return this.instances;
    }

    /**
     * Computes the real instance id for the given path expression.
     * <p/>
     * If the path expression doesn't start with an instance function the id
     * of the default instance is returned. Otherwise the parameter of that
     * function is evaluated. The result is then used to lookup the instance
     * and if an instance is found, its real id is returned.
     *
     * @param path the path expression.
     * @return the real instance id for the given path expression.
     */
    public String computeInstanceId(String path) {
        if (path == null) {
            return null;
        }

        // lookup expression (instance function parameter)
        String expression = XPathUtil.getInstanceParameter(path);
        if (expression != null) {
            // evaluate expression and lookup instance
            if(expression.equals("") || expression.equals("''")){
                return getDefaultInstance().getId();
            }else{
                String value = (String) getDefaultInstance().getInstanceContext().getValue(expression, String.class);
                String realId = null;
                Instance instance = getInstance(value);
                if (instance != null) {
                    realId = instance.getId();
                }

                return realId;
            }
                
        }

        // get real id of default instance
        return getDefaultInstance().getId();
    }
    /**
     * returns the Main-Calculation-Graph
     *
     * @return the Main-Calculation-Graph
     */
    public MainDependencyGraph getMainGraph() {
        return this.mainGraph;
    }

    /**
     * returns this Model object
     *
     * @return this Model object
     */
    public Model getModel() {
        return this;
    }

    /**
     * Returns the validator.
     *
     * @return the validator.
     */
    public Validator getValidator() {
        if (this.validator == null) {
            this.validator = new Validator();
            this.validator.setModel(this);
        }

        return this.validator;
    }

    /**
     * Returns a list of Schemas associated with this Model. <P> The list is
     * loaded at xforms-model-construct time and is ordered as follows: <ol>
     * <li>The XForms Datatypes Schema is always on top of the list.</li>
     * <li>All linked Schemas of this Model in order of their occurrence in the
     * <tt>xforms:schema</tt> attribute.</li> <li>All inline Schemas of this
     * Model in document order.</li> </ol>
     *
     * @return a list of Schemas associated with this Model.
     */
    public List getSchemas() {
        return this.schemas;
    }

    /**
     * adds a changed Vertex to the changelist. this happens every time a nodes
     * value has changed.
     * <p/>
     * this method has to be called after each change to the instance-data!
     *
     * @param changedNode - the Node whose value has changed
     */
    public void addChanged(NodeImpl changedNode) {
        if (this.mainGraph != null) {
            if (this.changed == null) {
                this.changed = new Vector();
            }

            Vertex vertex = this.mainGraph.getVertex(changedNode, Vertex.CALCULATE_VERTEX);

            if (vertex != null) {
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug(this + " add changed: adding calculate vertex for " + changedNode);
                }

                this.changed.add(vertex);
            }
        }
    }

    /**
     * adds a new instance to this model.
     *
     * @param id the optional instance id.
     * @return the new instance.
     */
    public Instance addInstance(String id) throws XFormsException {
        // create instance node
        Element instanceNode = this.element.getOwnerDocument()
                .createElementNS(NamespaceConstants.XFORMS_NS, xformsPrefix + ":" + INSTANCE);

        // ensure id
        String realId = id;
        if (realId == null || realId.length() == 0) {
            realId = this.container.generateId();
        }

        instanceNode.setAttributeNS(null, "id", realId);
        instanceNode.setAttributeNS(NamespaceConstants.XMLNS_NS, "xmlns", "");
        this.element.appendChild(instanceNode);

        // create and initialize instance object
        createInstanceObject(instanceNode);
        return getInstance(id);
    }

    /**
     * adds a Bind object to this Model
     *
     * @param bind the Bind object to add
     */
    public void addBindElement(Bind bind) {
        if (this.modelBindings == null) {
            this.modelBindings = new ArrayList();
        }

        this.modelBindings.add(bind);
    }

    /**
     * Returns the update handler associated with this model.
     *
     * @return the update handler associated with this model.
     */
    public UpdateHandler getUpdateHandler() {
        return this.updateHandler;
    }

    /**
     * Associates the update handler with this model.
     *
     * @param updateHandler the update handler to be associated with this model.
     */
    public void setUpdateHandler(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    /**
     * Generates a model item id.
     *
     * @return a model item id.
     */
    public String generateModelItemId() {
        // todo: build external id service
        return String.valueOf(++this.modelItemCounter);
    }

    // lifecycle methods

    /**
     * Performs element init.
     *
     * @throws XFormsException if any error occurred during init.
     */
    public void init() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " init");
        }

        this.updateSequencer = new UpdateSequencer(this);

        initializeDefaultAction();
        initializeExtensionFunctions();
        Initializer.initializeActionElements(this, this.element);
    }

    /**
     * Performs element disposal.
     *
     * @throws XFormsException if any error occurred during disposal.
     */
    public void dispose() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " dispose");
        }

        disposeDefaultAction();
    }

    // lifecycle template methods

    /**
     * Initializes the default action.
     */
    protected void initializeDefaultAction() {
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.MODEL_CONSTRUCT, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.MODEL_CONSTRUCT_DONE, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.READY, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.REFRESH, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.REVALIDATE, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.RECALCULATE, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.REBUILD, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.RESET, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.BINDING_EXCEPTION, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.LINK_EXCEPTION, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.LINK_ERROR, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.COMPUTE_EXCEPTION, this);
    }

    /**
     * this method checks for the existence of all functions listed on model and
     * throws a XFormsComputeException if one is not found (see 7.12 Extensions
     * Functions, XForms 1.0 Rec.) <br/><br/> Note: this method only checks if
     * the passed functions can be found in Chiba configuration file but doesn't
     * try to invoke them. It may still happen that runtime exceptions with
     * these functions occur in case the functions class does contain the
     * function in question or wrong parameter are used.
     *
     * @throws XFormsComputeException in case one of the listed functions cannot
     * be found or loaded
     */
    protected void initializeExtensionFunctions() throws XFormsComputeException {
        String functions = getXFormsAttribute(XFormsConstants.FUNCTIONS);
        if (functions != null && !functions.equals("")) {
            //check for availability of extension functions...
            StringTokenizer tokenizer = new StringTokenizer(functions);
            while (tokenizer.hasMoreTokens()) {
                String qname = tokenizer.nextToken();
                String prefix = qname.substring(0, qname.indexOf(":"));
                String localName = qname.substring(qname.indexOf(":") + 1);
                String[] functionInfo = {""};
                if (functionInfo != null) {
                    try {
                        String uri = NamespaceResolver.getNamespaceURI(this.element, prefix);
                        functionInfo = Config.getInstance().getExtensionFunction(uri, localName);
                        if (functionInfo != null) {
                            Class.forName(functionInfo[0]);
                        }
                        else {
                            throw new XFormsComputeException("Function '" + localName + "' cannot be found in Namespace '" + uri + "'", this.target, null);
                        }
                    }
                    catch (ClassNotFoundException e) {
                        throw new XFormsComputeException("Class containing Function cannot be found ", this.target, null);
                    }
                    catch (XFormsConfigException e) {
                        throw new XFormsComputeException("Configuration Problem - check default.xml ", this.target, null);
                    }
                }
            }

        }
    }

    /**
     * Disposes the default action.
     */
    protected void disposeDefaultAction() {
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.MODEL_CONSTRUCT, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.MODEL_CONSTRUCT_DONE, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.READY, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.REFRESH, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.REVALIDATE, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.RECALCULATE, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.REBUILD, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.RESET, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.BINDING_EXCEPTION, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.LINK_EXCEPTION, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.LINK_ERROR, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.COMPUTE_EXCEPTION, this);
    }

    // implementation of org.w3c.xforms.XFormsModelElement

    /**
     * 7.3.1 The getInstanceDocument() Method.
     * <p/>
     * This method returns a DOM Document that corresponds to the instance data
     * associated with the <code>instance</code> element containing an
     * <code>ID</code> matching the <code>instance-id</code> parameter. If there
     * is no matching instance data, a <code>DOMException</code> is thrown.
     *
     * @param instanceID the instance id.
     * @return the corresponding DOM document.
     * @throws DOMException if there is no matching instance data.
     */
    public Document getInstanceDocument(String instanceID) throws DOMException {
        Instance instance = getInstance(instanceID);
        if (instance == null) {
            throw new DOMException(DOMException.NOT_FOUND_ERR, instanceID);
        }

        return instance.getInstanceDocument();
    }

    /**
     * 7.3.2 The rebuild() Method
     * <p/>
     * This method signals the XForms processor to rebuild any internal data
     * structures used to track computational dependencies within this XForms
     * Model. This method takes no parameters and raises no exceptions.
     * <p/>
     * Creates a MainDependencyGraph.
     */
    public void rebuild() {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " rebuild");
        }

        try {
            if (this.updateSequencer.sequence(REBUILD)) {
                return;
            }

            if (this.modelBindings != null && this.modelBindings.size() > 0) {
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug(this + " rebuild: creating main dependency graph for " +
                            this.modelBindings.size() + " bind(s)");
                }

                this.mainGraph = new MainDependencyGraph();

                for (int index = 0; index < this.modelBindings.size(); index++) {
                    Bind bind = (Bind) this.modelBindings.get(index);
                    this.mainGraph.buildBindGraph(bind, this);
                }

                this.changed = (Vector) this.mainGraph.getVertices().clone();
            }

            this.updateSequencer.perform();
        }
        catch (Exception e) {
            this.updateSequencer.reset();
            this.container.handleEventException(e);
        }
    }

    /**
     * 7.3.3 The recalculate() Method
     * <p/>
     * This method signals the XForms Processor to perform a full recalculation
     * of this XForms Model. This method takes no parameters and raises no
     * exceptions.
     * <p/>
     * Creates and recalculates a SubDependencyGraph.
     */
    public void recalculate() {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " recalculate");
        }

        try {
            if (this.updateSequencer.sequence(RECALCULATE)) {
                return;
            }

            if (this.changed != null && this.changed.size() > 0) {
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug(this + " recalculate: creating sub dependency graph for " +
                            this.changed.size() + " node(s)");
                }

                SubGraph subGraph = new SubGraph();
                subGraph.constructSubDependencyGraph(this.changed);
                subGraph.recalculate();
                this.changed.clear();
            }

            this.updateSequencer.perform();
        }
        catch (Exception e) {
            this.updateSequencer.reset();
            this.container.handleEventException(e);
        }
    }

    /**
     * 7.3.4 The revalidate() Method
     * <p/>
     * This method signals the XForms Processor to perform a full revalidation
     * of this XForms Model. This method takes no parameters and raises no
     * exceptions.
     * <p/>
     * Revalidates all instances of this model.
     */
    public void revalidate() {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " revalidate");
        }

        try {
            if (this.updateSequencer.sequence(REVALIDATE)) {
                return;
            }

            if (this.instances != null && this.instances.size() > 0) {
                if (getLogger().isDebugEnabled()) {
                    getLogger().debug(this + " revalidate: revalidating " + this.instances.size() +
                            " instance(s)");
                }

                for (int index = 0; index < this.instances.size(); index++) {
                    getValidator().validate((Instance) this.instances.get(index));
                }
            }

            this.updateSequencer.perform();
        }
        catch (Exception e) {
            this.updateSequencer.reset();
            this.container.handleEventException(e);
        }
    }

    /**
     * 7.3.5 The refresh() Method
     * <p/>
     * This method signals the XForms Processor to perform a full refresh of
     * form controls bound to instance nodes within this XForms Model. This
     * method takes no parameters and raises no exceptions.
     */
    public void refresh() {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " refresh");
        }

        try {
            if (this.updateSequencer.sequence(REFRESH)) {
                return;
            }

            Initializer.updateUIElements(this.container.getDocument().getDocumentElement());

            if (this.instances != null) {
                Instance instance;
                Iterator iterator;
                ModelItem modelItem;
                for (int index = 0; index < this.instances.size(); index++) {
                    instance = (Instance) this.instances.get(index);

                    // resets state keeping on model items
                    iterator = instance.iterateModelItems();
                    while (iterator.hasNext()) {
                        modelItem = (ModelItem) iterator.next();
                        modelItem.getStateChangeView().reset();
                    }
                }
            }

            this.updateSequencer.perform();
        }
        catch (Exception e) {
            this.updateSequencer.reset();
            this.container.handleEventException(e);
        }
    }


    // implementation of 'org.chiba.xml.events.DefaultAction'

    /**
     * Performs the implementation specific default action for this event.
     *
     * @param event the event.
     */
    public void performDefault(Event event) {
        try {
            if (event.getType().equals(XFormsEventNames.MODEL_CONSTRUCT)) {
                modelConstruct();
                return;
            }
            if (event.getType().equals(XFormsEventNames.MODEL_CONSTRUCT_DONE)) {
                modelConstructDone();
                return;
            }
            if (event.getType().equals(XFormsEventNames.READY)) {
                ready();
                return;
            }
            if (event.getType().equals(XFormsEventNames.REFRESH)) {
                if (isCancelled(event)) {
                    getLogger().debug(this + event.getType() + " cancelled");
                    return;
                }

                refresh();
                return;
            }
            if (event.getType().equals(XFormsEventNames.REVALIDATE)) {
                if (isCancelled(event)) {
                    getLogger().debug(this + event.getType() + " cancelled");
                    return;
                }
                revalidate();
                return;
            }
            if (event.getType().equals(XFormsEventNames.RECALCULATE)) {
                if (isCancelled(event)) {
                    getLogger().debug(this + event.getType() + " cancelled");
                    return;
                }
                recalculate();
                return;
            }
            if (event.getType().equals(XFormsEventNames.REBUILD)) {
                if (isCancelled(event)) {
                    getLogger().debug(this + event.getType() + " cancelled");
                    return;
                }
                rebuild();
                return;
            }
            if (event.getType().equals(XFormsEventNames.RESET)) {
                if (isCancelled(event)) {
                    getLogger().debug(this + event.getType() + " cancelled");
                    return;
                }
                reset();
                return;
            }
            if (event.getType().equals(XFormsEventNames.BINDING_EXCEPTION)) {
                getLogger().error(this + " binding exception: " + ((XMLEvent) event).getContextInfo(XMLEvent.DIRTY_DEFAULT_INFO));
                return;
            }
            if (event.getType().equals(XFormsEventNames.LINK_EXCEPTION)) {
                getLogger().error(this + " link exception: " + ((XMLEvent) event).getContextInfo(XFormsEventNames.RESOURCE_URI_PROPERTY));
                return;
            }
            if (event.getType().equals(XFormsEventNames.LINK_ERROR)) {
                getLogger().warn(this + " link error: " + ((XMLEvent) event).getContextInfo(XFormsEventNames.RESOURCE_URI_PROPERTY));
                return;
            }
            if (event.getType().equals(XFormsEventNames.COMPUTE_EXCEPTION)) {
                getLogger().error(this + " compute exception: " + ((XMLEvent) event).getContextInfo(XMLEvent.DIRTY_DEFAULT_INFO));
                return;
            }
        }
        catch (Exception e) {
            // handle exception and stop event propagation
            this.container.handleEventException(e);
            event.stopPropagation();
        }
    }

    /**
     * Implements <code>xforms-model-construct</code> default action.
     */
    private void modelConstruct() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " model construct");
        }

        // load schemas
        this.schemas = new ArrayList();
        loadDefaultSchema(this.schemas);
        loadLinkedSchemas(this.schemas);
        loadInlineSchemas(this.schemas);

        // set datatypes for validation
        getValidator().setDatatypes(getNamedDatatypes(this.schemas));


        // build instances
        this.instances = new ArrayList();

        // todo: move to static method in initializer
        NodeList nl = getElement().getElementsByTagNameNS(NamespaceConstants.XFORMS_NS, INSTANCE);
        int count = nl.getLength();
        
        if(count > 0){
            for (int index = 0; index < count; index++) {
                Element xformsInstance = (Element) nl.item(index);
                createInstanceObject(xformsInstance);
            }

            // todo: initialize p3p ?
            // initialize binds and submissions (actions should be initialized already)
            Initializer.initializeBindElements(this, this.element, new JXPathReferenceFinderImpl());
            Initializer.initializeSubmissionElements(this, this.element);

            rebuild();
            recalculate();
            revalidate();
        }
    }

    private void createInstanceObject(Element xformsInstance) throws XFormsException {
        Instance instance = (Instance) this.container.getElementFactory().createXFormsElement(xformsInstance, this);
        instance.init();
        this.instances.add(instance);
    }


    /**
     * Implements <code>xforms-model-construct-done</code> default action.
     */
    private void modelConstructDone() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " model construct done");
        }

        if (getContainer().isModelConstructDone()) {
            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " model construct done: already performed");
            }

            // process only once for all models
        }
        else {
            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " model construct done: starting ui initialization");
            }

            // initialize ui elements
            Initializer.initializeUIElements(this.container.getDocument().getDocumentElement());
        }
    }

    /**
     * Implements <code>xforms-ready</code> default action.
     */
    private void ready() {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " ready");
        }

        if (this.instances != null) {
            Instance instance;
            Iterator iterator;
            ModelItem modelItem;
            for (int index = 0; index < this.instances.size(); index++) {
                instance = (Instance) this.instances.get(index);
                instance.storeInitialInstance();

                // init state keeping on model items
                iterator = instance.iterateModelItems();
                while (iterator.hasNext()) {
                    modelItem = (ModelItem) iterator.next();
                    modelItem.getStateChangeView().reset();
                }
            }
        }

        this.ready = true;
    }

    /**
     * Implements <code>xforms-reset</code> default action.
     */
    private void reset() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " reset");
        }

        if (this.instances != null && this.instances.size() > 0) {
            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " reset: resetting " + this.instances.size() + " instance(s)");
            }

            for (int index = 0; index < this.instances.size(); index++) {
                Instance instance = (Instance) this.instances.get(index);
                instance.reset();
            }
        }

        // dispatch xforms-rebuild, xforms-recalculate, xforms-revalidate
        // and xforms-refresh to model
        this.container.dispatch(this.target, XFormsEventNames.REBUILD, null);
        this.container.dispatch(this.target, XFormsEventNames.RECALCULATE, null);
        this.container.dispatch(this.target, XFormsEventNames.REVALIDATE, null);
        this.container.dispatch(this.target, XFormsEventNames.REFRESH, null);
    }

    private void loadDefaultSchema(List list) throws XFormsException {
        try {
            // todo: still a hack
            InputStream stream = Config.class.getResourceAsStream("/org/chiba/xml/xforms/config/XFormsDatatypes.xsd");
            XSModel schema = loadSchema(stream);

            if (schema == null) {
                throw new NullPointerException("resource not found");
            }
            list.add(schema);
        }
        catch (Exception e) {
            throw new XFormsLinkException("could not load default schema", e, this.target, null);
        }
    }

    private void loadLinkedSchemas(List list) throws XFormsException {
        String schemaURI = null;
        try {
            String schemaAttribute = getXFormsAttribute("schema");
            if (schemaAttribute != null) {
                StringTokenizer tokenizer = new StringTokenizer(schemaAttribute, " ");
                XSModel schema = null;

                while (tokenizer.hasMoreTokens()) {
                    schemaURI = tokenizer.nextToken();

                    if (schemaURI.startsWith("#")) {
                        // lookup fragment
                        String id = schemaURI.substring(1);
                        Pointer pointer = this.container.getRootContext().getPointer("//*[@id='" + id + "']");
                        Element element = (Element) pointer.getNode();
                        schema = loadSchema(element);
                    }
                    else {
                        // resolve URI
                        schema = loadSchema(schemaURI);
                    }

                    if (schema == null) {
                        throw new NullPointerException("resource not found");
                    }
                    list.add(schema);
                }
            }
        }
        catch (Exception e) {
            Map props = new HashMap(1);
            props.put(XFormsEventNames.RESOURCE_URI_PROPERTY,schemaURI);
            throw new XFormsLinkException("could not load linked schema", this.target, props);
        }
    }

    private void loadInlineSchemas(List list) throws XFormsException {
        String schemaId = null;
        try {
            NodeList children = this.element.getChildNodes();

            for (int index = 0; index < children.getLength(); index++) {
                Node child = children.item(index);

                if (Node.ELEMENT_NODE == child.getNodeType() &&
                        NamespaceConstants.XMLSCHEMA_NS.equals(child.getNamespaceURI())) {
                    Element element = (Element) child;
                    schemaId = element.hasAttributeNS(null, "id")
                            ? element.getAttributeNS(null, "id")
                            : element.getNodeName();

                    XSModel schema = loadSchema(element);

                    if (schema == null) {
                        throw new NullPointerException("resource not found");
                    }
                    list.add(schema);
                }
            }
        }
        catch (Exception e) {
            throw new XFormsLinkException("could not load inline schema", e, this.target, schemaId);
        }
    }

    // todo: move to schema helper component
    public Map getNamedDatatypes(List schemas) {
        Map datatypes = new HashMap();

        // iterate schemas
        Iterator schemaIterator = schemas.iterator();
        while (schemaIterator.hasNext()) {
            XSModel schema = (XSModel) schemaIterator.next();
            XSNamedMap definitions = schema.getComponents(XSConstants.TYPE_DEFINITION);

            for (int index = 0; index < definitions.getLength(); index++) {
                XSTypeDefinition type = (XSTypeDefinition) definitions.item(index);

                // process named simple types being supported by XForms
                if (type.getTypeCategory() == XSTypeDefinition.SIMPLE_TYPE &&
                        !type.getAnonymous() &&
                        getValidator().isSupported(type.getName())) {
                    String name = type.getName();

                    // extract local name
                    int separator = name.indexOf(':');
                    String localName = separator > -1 ? name.substring(separator + 1) : name;

                    // build expanded name
                    String namespaceURI = type.getNamespace();
                    String expandedName = NamespaceResolver.expand(namespaceURI, localName);

                    if (NamespaceConstants.XFORMS_NS.equals(namespaceURI) ||
                            NamespaceConstants.XMLSCHEMA_NS.equals(namespaceURI)) {
                        // register default xforms and schema datatypes without namespace for convenience
                        datatypes.put(localName, type);
                    }

                    // register uniquely named type
                    datatypes.put(expandedName, type);
                }
            }
        }

        return datatypes;
    }

    public String getTargetNamespace(XSModel xsModel) {
        String namespace = xsModel.getComponents(XSConstants.TYPE_DEFINITION).item(0).getNamespace();
        return namespace;
    }

    private XSModel loadSchema(String uri) throws XFormsException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ConnectorFactory connectorFactory = this.container.getConnectorFactory();
        URI absoluteURI = connectorFactory.getAbsoluteURI(uri, this.element);

        return getSchemaLoader().loadURI(absoluteURI.toString());
    }

    private XSModel loadSchema(InputStream stream) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        LSInput input = new DOMInputImpl();
        input.setByteStream(stream);

        return getSchemaLoader().load(input);
    }

    private XSModel loadSchema(Element element) throws TransformerException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Element copy = (Element) element.cloneNode(true);
        NamespaceResolver.applyNamespaces(element, copy);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.transform(new DOMSource(copy), new StreamResult(stream));
        byte[] array = stream.toByteArray();

        return loadSchema(new ByteArrayInputStream(array));
    }

    private XSLoader getSchemaLoader() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        System.setProperty(DOMImplementationRegistry.PROPERTY, "org.apache.xerces.dom.DOMXSImplementationSourceImpl");
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        XSImplementation implementation = (XSImplementation) registry.getDOMImplementation("XS-Loader");
        XSLoader loader = implementation.createXSLoader(null);

        return loader;
    }

}

// end of class
