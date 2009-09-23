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
package org.chiba.xml.xforms.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.ElementImpl;
import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.events.ChibaEventNames;
import org.chiba.xml.events.XMLEvent;
import org.chiba.xml.ns.NamespaceConstants;
import org.chiba.xml.xforms.XFormsElement;
import org.chiba.xml.xforms.core.Bind;
import org.chiba.xml.xforms.core.Instance;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.ui.state.RepeatElementState;
import org.chiba.xml.xpath.XPathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of <b>9.3.1 The repeat Element</b>.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: Repeat.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class Repeat extends BindingElement implements EventListener {
    protected static Log LOGGER = LogFactory.getLog(Repeat.class);

    private int index;
    private Element prototype;
    private List items;

    /**
     * Creates a new repeat element handler.
     *
     * @param element the host document element.
     * @param model the context model.
     */
    public Repeat(Element element, Model model) {
        super(element, model);
    }

    // event handling methods

    /**
     * This method is called whenever an event occurs of the type for which the
     * <code>EventListener</code> interface was registered.
     *
     * @param event The <code>Event</code> contains contextual information about
     * the event. It also contains the <code>stopPropagation</code> and
     * <code>preventDefault</code> methods which are used in determining the
     * event's flow and default action.
     */
    public void handleEvent(Event event) {
        try {
            if (ChibaEventNames.NODE_INSERTED.equals(event.getType())) {
                handleNodeInserted(event);
                return;
            }
            if (ChibaEventNames.NODE_DELETED.equals(event.getType())) {
                handleNodeDeleted(event);
                return;
            }
        }
        catch (Exception e) {
            // handle exception, prevent default action and stop event propagation
            this.container.handleEventException(e);
            event.preventDefault();
            event.stopPropagation();
        }
    }

    // repeat specific methods

    /**
     * Returns the repeat index.
     *
     * @return the repeat index.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Sets the index of this <code>repeat</code>.
     *
     * @param index the index of this <code>repeat</code>.
     */
    public void setIndex(int index) throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " set index: " + index);
        }

        // update index
        this.index = index;
        notifyIndexChange();

        if (isRepeated()) {
            // set enclosing index
            RepeatItem repeatItem = (RepeatItem) this.container.lookup(getRepeatItemId());
            repeatItem.getRepeat().setIndex(repeatItem.getPosition());
        }
        else {
            // register repeat item under original id
            registerRepeatItem(index);
        }
    }

    /**
     * Returns the context size of this repeat.
     * <p/>
     * The context size is the size of the bound nodeset.
     *
     * @return the context size of this repeat.
     */
    public int getContextSize() {
        if (isBound()) {
            return this.model.getInstance(getInstanceId()).countNodeset(getLocationPath());
        }

        return 0;
    }

    /**
     * Returns the specified repeat item.
     *
     * @param position the repeat item position.
     * @return the specified repeat item or <code>null</code> if there is no
     *         such position.
     */
    public RepeatItem getRepeatItem(int position) {
        if (position > 0 && position <= this.items.size()) {
            return (RepeatItem) this.items.get(position - 1);
        }

        return null;
    }

    /**
     * Returns the repeat prototype element.
     *
     * @return the repeat prototype element.
     */
    public Element getPrototype() {
        return this.prototype;
    }

    // binding related methods

    /**
     * Checks wether this element has a model binding.
     * <p/>
     * This element has a model binding if it has a <code>bind</code> or a
     * <code>repeat-bind</code> attribute.
     *
     * @return <code>true</code> if this element has a model binding, otherwise
     *         <code>false</code>.
     */
    public boolean hasModelBinding() {
        return getXFormsAttribute(REPEAT_BIND_ATTRIBUTE) != null ||
                getXFormsAttribute(BIND_ATTRIBUTE) != null;
    }

    /**
     * Checks wether this element has an ui binding.
     * <p/>
     * This element has an ui binding if it has a <code>nodeset</code> or a
     * <code>repeat-nodeset</code> attribute.
     *
     * @return <code>true</code> if this element has an ui binding, otherwise
     *         <code>false</code>.
     */
    public boolean hasUIBinding() {
        return getXFormsAttribute(REPEAT_NODESET_ATTRIBUTE) != null ||
                getXFormsAttribute(NODESET_ATTRIBUTE) != null;
    }

    /**
     * Returns the model binding of this element.
     *
     * @return the model binding of this element.
     */
    public Bind getModelBinding() {
        String bindAttribute = getXFormsAttribute(REPEAT_BIND_ATTRIBUTE);
        if (bindAttribute != null) {
            return (Bind) this.container.lookup(bindAttribute);
        }

        return super.getModelBinding();
    }

    /**
     * Returns the binding expression.
     *
     * @return the binding expression.
     */
    public String getBindingExpression() {
        String nodesetAttribute = getXFormsAttribute(REPEAT_NODESET_ATTRIBUTE);
        if (nodesetAttribute != null) {
            return nodesetAttribute;
        }

        nodesetAttribute = getXFormsAttribute(NODESET_ATTRIBUTE);
        if (nodesetAttribute != null) {
            return nodesetAttribute;
        }

        return null;
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

        initializeDefaultAction();
        initializeInstanceNode();
        initializePrototype();
        initializeElementState();
        initializeRepeat();
    }

    /**
     * Performs element update.
     *
     * @throws XFormsException if any error occurred during update.
     */
    public void refresh() throws XFormsException {
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " update");
        }

        updateRepeat();
        updateElementState();
        updateChildren();
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
        disposeChildren();
        disposeElementState();
        disposeRepeat();
        disposeSelf();
    }

    // template methods

    /**
     * Factory method for the element state.
     *
     * @return an element state implementation or <code>null</code> if no state
     *         keeping is required.
     * @throws XFormsException if an error occurred during creation.
     */
    protected UIElementState createElementState() throws XFormsException {
        return isBound() ? new RepeatElementState() : null;
    }


    // lifecycle template methods

    /**
     * Initializes this repeat.
     * <p/>
     * The repeat prototype is cloned and removed from the document. The data
     * element is initialized with the prototype data.
     */
    protected void initializePrototype() throws XFormsException {
        // create prototype element
        Document document = this.element.getOwnerDocument();
        this.prototype = document.createElementNS(NamespaceConstants.XFORMS_NS, this.xformsPrefix + ":" + GROUP);
        this.prototype.setAttributeNS(null, "id", this.container.generateId());
        this.prototype.setAttributeNS(null,  APPEARANCE_ATTRIBUTE, "repeated");

        // clone repeat prototype
        NodeList children = this.element.getChildNodes();
        for (int index = 0; index < children.getLength(); index++) {
            initializePrototype(this.prototype, children.item(index));
        }

        // remove repeat prototype
        DOMUtil.removeAllChildren(this.element);
    }

    /**
     * Initializes this repeat.
     * <p/>
     * The repeat is registered with the instance as event listener. For each
     * node in the bound nodeset repeat items are created and initialized. The
     * repeat index is set to 1, unless the bound nodeset is empty. The repeat
     * is registered with the instance as event listener.
     */
    protected void initializeRepeat() throws XFormsException {
        // register repeat as event listener *before* items are initialized
        Instance instance = this.model.getInstance(getInstanceId());
        instance.getTarget().addEventListener(ChibaEventNames.NODE_INSERTED, this, false);
        instance.getTarget().addEventListener(ChibaEventNames.NODE_DELETED, this, false);

        // initialize repeat items
        int count = getContextSize();
        this.items = new ArrayList(count);

        if (count > 0) {
            // set index before item initialization, notify index change later
            this.index = 1;
        }

        if(element.hasAttributeNS(NamespaceConstants.CHIBA_NS,"index")){
            //we're initializing a deserialized form
            this.index = Integer.parseInt(element.getAttributeNS(NamespaceConstants.CHIBA_NS,"index"));
        }


        if (getLogger().isDebugEnabled()) {
            getLogger().debug(this + " init: initializing " + count + " repeat item(s)");
        }
        for (int position = 1; position < count + 1; position++) {
            this.items.add(initializeRepeatItem(position));
        }

        if (count > 0) {
            // register selected item with original id
            registerRepeatItem(this.index);

            // notify index change
            notifyIndexChange();
        }
    }

    /**
     * Updates this repeat.
     * <p/>
     * The list of repeat items is synchronized with the bound nodeset.
     */
    protected void updateRepeat() throws XFormsException {
        // check context size (bound nodeset count) against ui size (repeat item count)
        int contextSize = getContextSize();
        int uiSize = this.items.size();

        if (contextSize < uiSize) {
            // remove obsolete repeat items
            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " update: disposing " + (uiSize - contextSize) + " repeat item(s)");
            }
            for (int position = uiSize; position > contextSize; position--) {
                disposeRepeatItem((RepeatItem) this.items.remove(position - 1));
            }

            if (getIndex() > contextSize) {
                // set index to last
                setIndex(contextSize);
            }
        }

        if (contextSize > uiSize) {
            // add missing repeat items
            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " update: initializing " + (contextSize - uiSize) + " repeat item(s)");
            }
            for (int position = uiSize + 1; position <= contextSize; position++) {
                this.items.add(initializeRepeatItem(position));
            }

            if (getIndex() == 0) {
                // set index to first
                setIndex(1);
            }
        }
    }

    /**
     * Disposes this repeat.
     * <p/>
     * The repeat is deregistered as event listener. The list of repeat items
     * and the repeat prototype are freed.
     */
    protected void disposeRepeat() throws XFormsException {
        // deregister repeat as event listener
        Instance instance = this.model.getInstance(getInstanceId());
        instance.getTarget().removeEventListener(ChibaEventNames.NODE_INSERTED, this, false);
        instance.getTarget().removeEventListener(ChibaEventNames.NODE_DELETED, this, false);

        // free repeat items and prototype
        this.items.clear();
        this.items = null;
        this.prototype = null;
    }

    // event handling template methods

    protected void handleNodeInserted(Event event) throws XFormsException {
        if (doHandleInstanceEvent(event)) {
            // get nodeset position
            int contextPosition = Integer.parseInt(String.valueOf(((XMLEvent) event).getContextInfo("position")));

            // get ui position
            int uiPosition = computeUIPosition(contextPosition);

            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " insert: position " + uiPosition);
            }

            // insert repeat item
            this.items.add(uiPosition - 1, initializeRepeatItem(uiPosition));

            // update position of following items
            for (int index = uiPosition; index < this.items.size(); index++) {
                ((RepeatItem) this.items.get(index)).setPosition(index + 1);
            }

            // set index to inserted item
            setIndex(uiPosition);
        }
    }

    protected void handleNodeDeleted(Event event) throws XFormsException {
        if (doHandleInstanceEvent(event)) {
            // get nodeset position
            int contextPosition = Integer.parseInt(String.valueOf(((XMLEvent) event).getContextInfo("position")));

            // get ui position
            int uiPosition = computeUIPosition(contextPosition);

            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " delete: position " + uiPosition);
            }

            // delete repeat item
            disposeRepeatItem((RepeatItem) this.items.remove(uiPosition - 1));

            // update position of following items
            for (int index = uiPosition - 1; index < this.items.size(); index++) {
                ((RepeatItem) this.items.get(index)).setPosition(index + 1);
            }

            // set index only if it was pointing to the deleted item
            // and the deleted item was the last collection member
            int contextSize = getContextSize();
            if (getIndex() > contextSize) {
                setIndex(contextSize);
            }
        }
    }

    // helper methods

    private boolean doHandleInstanceEvent(Event event) {
        // check context size
        int contextSize = getContextSize();
        if (contextSize == 0) {
            return false;
        }

        // check instance nodeset
        String canonicalPath = this.model.getInstance(getInstanceId()).getPointer(getLocationPath()).asPath();
        String canonicalNodeset = XPathUtil.getNodesetAndPredicates(canonicalPath)[0];
        String insertNodeset = String.valueOf(((XMLEvent) event).getContextInfo("nodeset"));
        if (!canonicalNodeset.equals(insertNodeset)) {
            return false;
        }

        // check context size (bound nodeset count) against ui size (repeat item count)
        int uiSize = this.items.size();
        return contextSize != uiSize;
    }

    private int computeUIPosition(int contextPosition) {
        int contextFirst = 0;
        int contextLast = 0;

        if (this.items.size() > 0) {
            contextFirst = ((RepeatItem) this.items.get(0)).getContextPosition();
            contextLast = ((RepeatItem) this.items.get(this.items.size() - 1)).getContextPosition();
        }

        // check lower bounds
        if (contextPosition <= contextFirst) {
            return 1;
        }

        // check upper bounds
        if (contextPosition > contextLast) {
            return this.items.size() + 1;
        }

        // search repeat item with a context position greater or equal than the given one
        RepeatItem repeatItem = (RepeatItem) this.items.get(0);
        for (int index = 1; index < this.items.size() && repeatItem.getContextPosition() < contextPosition; index++) {
            repeatItem = (RepeatItem) this.items.get(index);
        }

        // return ui position
        return repeatItem.getPosition();
    }

    private void notifyIndexChange() throws XFormsException {
        // update element state
        this.elementState.setProperty("index", new Integer(this.index));

        if (this.model.isReady()) {
            // dispatch internal chiba event
            HashMap map = new HashMap();
            map.put("originalId", this.originalId != null ? this.originalId : this.id);
            map.put("index", String.valueOf(this.index));
            this.container.dispatch(this.target, ChibaEventNames.INDEX_CHANGED, map);
        }
    }

    private void initializePrototype (Node parent, Node prototype) {
        Node copy = prototype.cloneNode(false);
        if (copy.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) copy;
            if (element.getAttributeNS(null, "id").length() == 0) {
                element.setAttributeNS(null, "id", this.container.generateId());
            }

            NodeList children = prototype.getChildNodes();
            for (int index = 0; index < children.getLength(); index++) {
                initializePrototype(element, children.item(index));
            }
        }

        parent.appendChild(copy);
    }

    private RepeatItem initializeRepeatItem(int position) throws XFormsException {
        // detect reference node
        Node before = DOMUtil.findNthChildNS(this.element, NamespaceConstants.XFORMS_NS, GROUP, position);
        if (before == null) {
            before = DOMUtil.findFirstChildNS(this.element, NamespaceConstants.CHIBA_NS, "data");
        }

        // create repeat item
        Element group = (Element) this.prototype.cloneNode(true);
        this.element.insertBefore(group, before);

        if (this.model.isReady()) {
            // dispatch internal chiba event
            HashMap map = new HashMap();
            map.put("originalId", this.originalId != null ? this.originalId : this.id);
            map.put("prototypeId", this.prototype.getAttributeNS(null, "id"));
            this.container.dispatch(this.target, ChibaEventNames.PROTOTYPE_CLONED, map);
        }

        // initialize repeat item
        RepeatItem repeatItem = (RepeatItem) this.container.getElementFactory().createXFormsElement(group, getModel());
        repeatItem.setRepeat(this);
        repeatItem.setPosition(position);
        repeatItem.setGeneratedId(this.container.generateId());
        repeatItem.registerId();
        repeatItem.init();

        if (this.model.isReady()) {
            // dispatch internal chiba event
            HashMap map = new HashMap();
            map.put("originalId", this.originalId != null ? this.originalId : this.id);
            map.put("position", String.valueOf(position));
            this.container.dispatch(this.target, ChibaEventNames.ITEM_INSERTED, map);
        }

        return repeatItem;
    }

    private void disposeRepeatItem(RepeatItem repeatItem) throws XFormsException {
        // dispose repeat item
        Element element = repeatItem.getElement();
        int position = repeatItem.getPosition();
        repeatItem.dispose();
        this.element.removeChild(element);

        if (this.model.isReady()) {
            // dispatch internal chiba event
            HashMap map = new HashMap();
            map.put("originalId", this.originalId != null ? this.originalId : this.id);
            map.put("position", String.valueOf(position));
            this.container.dispatch(this.target, ChibaEventNames.ITEM_DELETED, map);
        }
    }

    private void registerRepeatItem(int position) {
        RepeatItem repeatItem = getRepeatItem(position);
        if (repeatItem != null) {
            repeatItem.registerId();
            registerChildren(repeatItem.getElement());
        }
    }

    private void registerChildren(Node parent) {
        NodeList childNodes = parent.getChildNodes();

        for (int index = 0; index < childNodes.getLength(); index++) {
            Node node = childNodes.item(index);

            if (node instanceof ElementImpl) {
                ElementImpl elementImpl = (ElementImpl) node;
                XFormsElement xFormsElement = (XFormsElement) elementImpl.getUserData();

                if (xFormsElement != null) {
                    // register current (action or ui) element
                    xFormsElement.registerId();

                    if (xFormsElement instanceof Repeat) {
                        // register *selected* repeat item only, if any
                        Repeat repeat = (Repeat) xFormsElement;
                        RepeatItem repeatItem = repeat.getRepeatItem(repeat.getIndex());

                        if (repeatItem != null) {
                            repeatItem.registerId();
                            registerChildren(repeatItem.getElement());
                        }
                    }
                    else {
                        // register *all* children
                        registerChildren(xFormsElement.getElement());
                    }
                }
            }
        }
    }

}

// end of class
