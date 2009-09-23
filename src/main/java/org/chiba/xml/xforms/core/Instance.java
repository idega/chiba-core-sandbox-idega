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
package org.chiba.xml.xforms.core;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.NodeImpl;
import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.events.ChibaEventNames;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.ns.NamespaceResolver;
import org.chiba.xml.xforms.XFormsElement;
import org.chiba.xml.xforms.core.impl.XercesElementImpl;
import org.chiba.xml.xforms.core.impl.XercesNodeImpl;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.exception.XFormsLinkException;
import org.chiba.xml.xforms.xpath.ExtensionFunctions;
import org.chiba.xml.xpath.XPathUtil;
import org.chiba.xml.xpath.impl.JXPathDOMFactory;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Implementation of XForms instance Element.
 *
 * @version $Id: Instance.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class Instance extends XFormsElement implements org.apache.commons.jxpath.Container {
    protected static Log LOGGER = LogFactory.getLog(Instance.class);
    private Document instanceDocument = null;
    private Element initialInstance = null;
    private JXPathContext instanceContext = null;

    /**
     * Creates a new Instance object.
     *
     * @param element the DOM Element of this instance
     * @param model the owning Model of this instance
     */
    public Instance(Element element, Model model) {
        super(element, model);
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

        // load initial instance
        this.initialInstance = getInitiallInstance();

        // create instance document
        this.instanceDocument = createInstanceDocument();

        initInstanceContext();
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

        this.initialInstance = null;
        this.instanceDocument = null;
        this.instanceContext = null;
    }

    /**
     * initialize JXPath Context correctly to contain all namespaces in scope.
     */
    protected void initInstanceContext() {
        // create instance context
        JXPathDOMFactory factory = new JXPathDOMFactory();
        factory.setNamespaceContext(this.element);

        ExtensionFunctions functions = new ExtensionFunctions();
        functions.setNamespaceContext(this.element);

        this.instanceContext = JXPathContext.newContext(this);
        this.instanceContext.setFactory(factory);
        this.instanceContext.setFunctions(functions);

        Map namespaces = NamespaceResolver.getAllNamespaces(this.element);
        Iterator iterator = namespaces.keySet().iterator();
        while (iterator.hasNext()) {
            String prefix = (String) iterator.next();
            String uri = (String) namespaces.get(prefix);

            this.instanceContext.registerNamespace(prefix, uri);
        }
    }

    // instance specific methods

    /**
     * this method lets you access the state of individual instance data nodes.
     * each node has an associated ModelItem object which holds the current
     * status of readonly, required, validity, relevant. it also annotates the
     * DOM node with type information.
     * <p/>
     * When an ModelItem does not exist already it will be created and attached
     * as useroject to its corresponding node in the instancedata.
     *
     * @param locationPath - an absolute location path pointing to some node in
     * this instance
     * @return the ModelItem for the specified node
     */
    public ModelItem getModelItem(String locationPath) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(this + " get model item for locationPath '" + locationPath + "'");
        }

        // ensure that node exists since getPointer is buggy
        if (!existsNode(locationPath)) {
            return null;
        }

        // lookup pointer, node, item
        this.instanceContext.getVariables().declareVariable("contextmodel", this.model.getId());
        Pointer pointer = this.instanceContext.getPointer(locationPath);
        this.instanceContext.getVariables().undeclareVariable("contextmodel");

        NodeImpl node = (NodeImpl) pointer.getNode();
        ModelItem item = (ModelItem) node.getUserData();

        if (item == null) {
            // create item
            item = createModelItem(node);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(this + " get model item: created item for path '" + pointer + "'");
            }
        }

        return item;
    }

    /**
     * Returns an iterator over all existing model items.
     *
     * @return an iterator over all existing model items.
     */
    public Iterator iterateModelItems() {
        return iterateModelItems("/");
    }

    /**
     * Returns an iterator over the specified model items.
     *
     * @param path the path selecting a set of model items.
     * @return an iterator over the specified model items.
     */
    public Iterator iterateModelItems(String path) {
        return iterateModelItems(path, true);
    }

    /**
     * Returns an iterator over the specified model items.
     *
     * @param path the path selecting a set of model items.
     * @param deep include attributes and children or not.
     * @return an iterator over the specified model items.
     */
    public Iterator iterateModelItems(String path, boolean deep) {
        // hack 1: ensure that node exists since JXPath's getPointer() is buggy
        if (!existsNode(path)) {
            return null;
        }

        // create list, fill and iterate it
        // todo: optimize with live iterator
        List list = new ArrayList();
        Pointer pointer;
        Object nested;
        NodeImpl node;
        Iterator iterator = this.instanceContext.iteratePointers(path);
        while (iterator.hasNext()) {
            pointer = (Pointer) iterator.next();

            // hack 2: when path is a plain 'instance(...)' function call without
            // any following path step, JXPath wraps the actual node pointer
            // with a fairly useless bean pointer
            if (pointer instanceof org.apache.commons.jxpath.ri.model.beans.BeanPointer) {
                nested = pointer.getNode();
                if (nested instanceof org.apache.commons.jxpath.ri.model.dom.DOMNodePointer) {
                    pointer = (Pointer) nested;
                }
            }

            node = (NodeImpl) pointer.getNode();
            listModelItems(list, node, deep);
        }

        return list.iterator();
    }

    /**
     * Returns the value of the specified node.
     *
     * @param path the path pointing to a node.
     * @return the value of the specified node.
     */
    public String getNodeValue(String path) throws XFormsException {
        ModelItem item = getModelItem(path);
        if (item == null) {
            throw new XFormsException("model item for path '" + path + "' does not exist");
        }

        return item.getValue();
    }

    /**
     * Sets the value of the specified node.
     *
     * @param path the path pointing to a node.
     * @param value the value to be set.
     */
    public void setNodeValue(String path, String value) throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " set node value: " + path + "=" + value);
        }

        ModelItem item = getModelItem(path);
        if (item == null) {
            throw new XFormsException("model item for path '" + path + "' does not exist");
        }

        if (!item.isReadonly()) {
            item.setValue(value);
            this.model.addChanged((NodeImpl) item.getNode());
        }
        else {
            getLogger().warn(this + " set node value: attempt to set readonly value");
        }
    }

    public void setNode(String path, Element node) throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " set node value: " + path + "=" + node);
        }

        ModelItem item = getModelItem(path);
        if (item == null) {
            throw new XFormsException("model item for path '" + path + "' does not exist");
        }

        if (!item.isReadonly()) {
            //todo: think this is wrong cause we want to attach ourselves as child element to object denoted by 'path'
            Node imported = this.instanceDocument.importNode(node,true);
            ((Element)item.getNode()).appendChild(imported);
//            item.setNode(node);
            this.model.addChanged((NodeImpl) item.getNode());
        }
        else {
            getLogger().warn(this + " set node value: attempt to set readonly value");
        }
    }
    
    /**
     * Checks if the specified node exists.
     *
     * @param path the path pointing to a node.
     * @return <code>true</code> if the specified node exists, otherwise
     *         <code>false</code>.
     */
    public boolean existsNode(String path) {
        // Note: This might seem inefficient but isn't. Using 'count()' is as
        // performant as using 'JXPathContext.selectSingleNode(String) != null'
        // in all cases, except when 'path' doesn't contain any predicates.
        // Anyway, there's a serious bug in 'JXPathContext.getPointer(String)'
        // and in 'JXPathContext.selectSingleNode(String)' respectively: calling
        // somtehing with a zero or negative predicate value, e.g. '/data/item[0],
        // will effectively return a pointer/node from the nodeset !!!
        return countNodeset(path) > 0;
    }

    /**
     * Counts the number of nodes in the specified nodeset.
     *
     * @param path the path pointing to a nodeset.
     * @return the number of nodes in the specified nodeset.
     */
    public int countNodeset(String path) {
        return ((Double) this.instanceContext.getValue("count(" + path + ")", Double.class)).intValue();
    }

    /**
     * Creates the specified node.
     *
     * @param path the path pointing to a node.
     */
    public void createNode(String path) throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " create node: " + path);
        }

        try {
            if (this.instanceDocument.getDocumentElement() == null) {
                createRootElement(this.instanceDocument, XPathUtil.getFirstPart(path));
            }

            this.instanceContext.createPath(path);
        }
        catch (Exception e) {
            throw new XFormsException(e);
        }
    }

    /**
     * Inserts the specified node.
     *
     * @param origin the path pointing to the origin node.
     * @param before the path pointing to the node before which a clone of the
     * origin node will be inserted.
     */
    public void insertNode(String origin, String before) throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " insert node: " + origin + " before " + before);
            getLogger().debug(this + " insert node: instance data before manipulation" + toString(this.instanceDocument));
        }

        // lookup manadatory origin node
        ModelItem originItem = getModelItem(origin);
        Node originNode;
        if (originItem != null) {
            originNode = (Node) originItem.getNode();
        }
        else {
            throw new XFormsException("origin node does not exist");
        }

        // lookup optional before node
        ModelItem beforeItem = getModelItem(before);
        Node beforeNode = null;
        String beforePath = null;
        if (beforeItem != null) {
            // get node and parent path
            beforeNode = (Node) beforeItem.getNode();
            beforePath = before;
        }
        else {
            // lookup following sibling of last node in 'before' nodeset
            String firstSibling = XPathUtil.getNodesetAndPredicates(before)[0] + "[last()]/following-sibling::*[1]";
            beforeItem = getModelItem(firstSibling);

            if (beforeItem != null) {
                // get node and parent path
                beforeNode = (Node) beforeItem.getNode();
                beforePath = firstSibling;
            }
        }

        // lookup manadatory parent node
        String parent;
        if (beforePath != null) {
            parent = beforePath + "/..";
        }
        else {
            String[] parentParts = XPathUtil.splitPathExpr(before);
            parent = XPathUtil.joinPathExpr(parentParts, 0, parentParts.length - 1);
        }

        ModelItem parentItem = getModelItem(parent);
        Node parentNode;
        if (parentItem != null) {
            parentNode = (Node) parentItem.getNode();
        }
        else {
            throw new XFormsException("parent node does not exist");
        }

        // insert a deep clone of 'origin' node before 'before' node. if
        // 'before' node is null, the clone will be appended to 'parent' node.
        parentNode.insertBefore(this.instanceDocument.importNode(originNode, true), beforeNode);

        // get canonical path for inserted node
        String canonicalPath = this.instanceContext.getPointer(beforeItem != null ? before : parent + "/*[last()]").asPath();
        String[] canonicalParts = XPathUtil.getNodesetAndPredicates(canonicalPath);

        // dispatch internal chiba event (for instant repeat updating)
        HashMap map = new HashMap();
        map.put("nodeset", canonicalParts[0]);
        map.put("position", canonicalParts[1]);
        this.container.dispatch(this.target, ChibaEventNames.NODE_INSERTED, map);

        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " insert node: instance data after manipulation" + toString(this.instanceDocument));
        }
    }

    /**
     * Deletes the specified node.
     *
     * @param path the path pointing to the node to be deleted.
     */
    public void deleteNode(String path) throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " delete node: " + path);
            getLogger().debug(this + " delete node: instance data before manipulation" + toString(this.instanceDocument));
        }

        // get canonical path for node to be deleted
        String canonicalPath;
        try {
            canonicalPath = this.instanceContext.getPointer(path).asPath();
        }
        catch (Exception e) {
            throw new XFormsException("delete node does not exist");
        }
        String[] canonicalParts = XPathUtil.getNodesetAndPredicates(canonicalPath);

        // remove specified path
        this.instanceContext.removePath(canonicalPath);

        // dispatch internal chiba event (for instant repeat updating)
        HashMap map = new HashMap();
        map.put("nodeset", canonicalParts[0]);
        map.put("position", canonicalParts[1]);
        this.container.dispatch(this.target, ChibaEventNames.NODE_DELETED, map);

        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " delete node: instance data after manipulation" + toString(this.instanceDocument));
        }
    }

    /**
     * Returns the instance context. This may also be used for non-default
     * instances, cause it knows how to switch the context correctly with help
     * of xforms:instance() function.
     *
     * @return the instance context.
     */
    public JXPathContext getInstanceContext() {
        return this.instanceContext;
    }

    /**
     * Sets the instance document.
     *
     * @param document the instance document.
     */
    void setInstanceDocument(Document document) {
        this.instanceDocument = document;

        initInstanceContext();
    }

    /**
     * Returns the instance document.
     *
     * @return the instance document.
     */
    public Document getInstanceDocument() {
        return this.instanceDocument;
    }

    /**
     * Returns a pointer for the specified node.
     *
     * @param path the path pointing to a node.
     * @return a pointer for the specified node.
     */
    public Pointer getPointer(String path) {
        return this.instanceContext.getPointer(path);
    }

    /**
     * Returns a pointer iterator for the specified nodeset.
     *
     * @param path the path pointing to a nodeset.
     * @return a pointer for the specified nodeset.
     */
    public Iterator getPointerIterator(String path) {
        return this.instanceContext.iteratePointers(path);
    }

    /**
     * Checks wether the specified <code>instance</code> element has an initial
     * instance.
     * <p/>
     * The specified <code>instance</code> element is considered to have an
     * initial instance if it has either a <code>src</code> attribute or at
     * least one element child.
     *
     * @return <code>true</code> if the <code>instance</code> element has an
     *         initial instance, otherwise <code>false</code>.
     */
    public boolean hasInitialInstance() {
        return this.initialInstance != null;
    }

    /**
     * Stores the current instance data as initial instance if no original
     * instance exists.
     * <p/>
     * This is needed for resetting an instance to its initial state when no
     * initial instance exists.
     */
    public void storeInitialInstance() {
        if (this.initialInstance == null) {
            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " store initial instance");
            }

            this.initialInstance = (Element) this.instanceDocument.getDocumentElement().cloneNode(true);
        }
    }

    /**
     * Performs element reset.
     *
     * @throws XFormsException if any error occurred during reset.
     */
    public void reset() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " reset");
        }

        // recreate instance document
        this.instanceDocument = createInstanceDocument();
    }

    // implementation of 'org.apache.commons.jxpath.Container'

    /**
     * Returns the contained value.
     *
     * @return the contained value.
     */
    public Object getValue() {
        return getInstanceDocument();
    }

    /**
     * Modifies the value contained by this container.
     *
     * @param value the new value.
     */
    public void setValue(Object value) {
        if (value instanceof Document) {
            setInstanceDocument((Document) value);
        }
    }

    // private helper methods

    /**
     * Creates the root element of the instance data.
     *
     * @param qname the qualified name of the root element.
     */
    private void createRootElement(Document document, String qname) {
        int separator = qname.indexOf(':');
        Element root;

        if (separator > -1) {
            String prefix = qname.substring(0, separator);
            String uri = NamespaceResolver.getNamespaceURI(this.element, prefix);
            root = document.createElementNS(uri, qname);
        }
        else {
            root = document.createElement(qname);
        }

        NamespaceResolver.applyNamespaces(this.element, root);
        document.appendChild(root);
    }

    /**
     * Returns the original instance.
     * <p/>
     * If this instance has a <code>src</code> attribute, it will be resolved
     * and the resulting document element is used. Otherwise the first child
     * element of this instance is used.
     *
     * @return the original instance.
     */
    private Element getInitiallInstance() throws XFormsLinkException {
        String srcAttribute = getXFormsAttribute(SRC_ATTRIBUTE);
        if (srcAttribute != null) {
            Map props = new HashMap(1);
            props.put(XFormsEventNames.RESOURCE_URI_PROPERTY,srcAttribute);

            Object result;
            try {
                result = this.container.getConnectorFactory().createURIResolver(srcAttribute, this.element).resolve();
            }
            catch (Exception e) {
                throw new XFormsLinkException("uri resolution failed", e, this.model.getTarget(), props);
            }


            if (result instanceof Document) {
                return ((Document) result).getDocumentElement();
            }

            if (result instanceof Element) {
                return (Element) result;
            }

            throw new XFormsLinkException("object model not supported", this.model.getTarget(), props);
        }

        return DOMUtil.getFirstChildElement(this.element);
    }

    /**
     * Returns a new created instance document.
     * <p/>
     * If this instance has an original instance, it will be imported into this
     * new document. Otherwise the new document is left empty.
     *
     * @return a new created instance document.
     */
    private Document createInstanceDocument() throws XFormsException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            Document document = factory.newDocumentBuilder().newDocument();

            if (this.initialInstance != null) {
                document.appendChild(document.importNode(this.initialInstance.cloneNode(true), true));

                String srcAttribute = getXFormsAttribute(SRC_ATTRIBUTE);
                if (srcAttribute == null) {
                    // apply namespaces
                    NamespaceResolver.applyNamespaces(this.element, document.getDocumentElement());
                }
            }

            return document;
        }
        catch (Exception e) {
            throw new XFormsException(e);
        }
    }

    private ModelItem createModelItem(NodeImpl node) {
        String id = this.model.generateModelItemId();
        ModelItem modelItem = node.getNodeType() == Node.ELEMENT_NODE ? new XercesElementImpl(id) : new XercesNodeImpl(id);
        modelItem.setNode(node);

        NodeImpl parentNode = (NodeImpl) (node.getNodeType() == Node.ATTRIBUTE_NODE ? ((Attr) node).getOwnerElement() : node.getParentNode());
        if (parentNode != null) {
            ModelItem parentItem = (ModelItem) parentNode.getUserData();
            if (parentItem == null) {
                parentItem = createModelItem(parentNode);
            }

            modelItem.setParent(parentItem);
        }

        node.setUserData(modelItem);
        return modelItem;
    }

    private void listModelItems(List list, NodeImpl node, boolean deep) {
        ModelItem modelItem = (ModelItem) node.getUserData();
        if (modelItem == null) {
            modelItem = createModelItem(node);
        }
        list.add(modelItem);

        if (deep) {
            NamedNodeMap attributes = node.getAttributes();
            for (int index = 0; attributes != null && index < attributes.getLength(); index++) {
                listModelItems(list, (NodeImpl) attributes.item(index), deep);
            }

            NodeList children = node.getChildNodes();
            for (int index = 0; index < children.getLength(); index++) {
                listModelItems(list, (NodeImpl) children.item(index), deep);
            }
        }
    }

    private String toString(Node node) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            DOMUtil.prettyPrintDOM(node, stream);
        }
        catch (TransformerException e) {
            // nop
        }
        return System.getProperty("line.separator") + stream;
    }

}

// end of class
