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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.ElementImpl;
import org.chiba.xml.events.DefaultAction;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.events.XMLEvent;
import org.chiba.xml.xforms.Initializer;
import org.chiba.xml.xforms.XFormsConstants;
import org.chiba.xml.xforms.XFormsElement;
import org.chiba.xml.xforms.exception.XFormsBindingException;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xpath.XPathReferenceFinder;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of XForms Model Bind Element.
 *
 * @version $Id: Bind.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class Bind extends XFormsElement implements Binding, DefaultAction {

    protected static Log LOGGER = LogFactory.getLog(Bind.class);

    private String locationPath;
    private String instanceId;

    private String type;
    private String readonly;
    private String required;
    private String relevant;
    private String calculate;
    private String constraint;
    private String p3ptype;

    private XPathReferenceFinder referenceFinder;
    private Set readonlyReferences;
    private Set requiredReferences;
    private Set relevantReferences;
    private Set calculateReferences;
    private Set constraintReferences;

    /**
     * Creates a new Bind object.
     *
     * @param element the DOM Element annotated by this object
     * @param model   the parent Model object
     */
    public Bind(Element element, Model model) {
        super(element, model);

        // register with model
        getModel().addBindElement(this);
    }

    // implementation of 'org.chiba.xml.xforms.core.Binding'

    /**
     * Returns the binding expression.
     *
     * @return the binding expression.
     */
    public String getBindingExpression() {
        return getXFormsAttribute(NODESET_ATTRIBUTE);
    }

    /**
     * Returns the id of the binding element.
     *
     * @return the id of the binding element.
     */
    public String getBindingId() {
        return this.id;
    }

    /**
     * Returns the enclosing element.
     *
     * @return the enclosing element.
     */
    public Binding getEnclosingBinding() {
        ElementImpl parentElement = (ElementImpl) this.element.getParentNode();

        if (parentElement.getLocalName().equals(XFormsConstants.MODEL)) {
            return null;
        }

        return (Binding) parentElement.getUserData();
    }

    /**
     * Returns the location path.
     *
     * @return the location path.
     */
    public String getLocationPath() {
        return this.locationPath;
    }

    /**
     * Returns the model id of the binding element.
     *
     * @return the model id of the binding element.
     */
    public String getModelId() {
        return this.model.getId();
    }

    // convenience

    /**
     * Returns the instance id of the binding element.
     *
     * @return the instance id of the binding element.
     */
    public String getInstanceId() {
        return this.instanceId;
    }

    // bind members

    /**
     * Returns the <code>type</code> attribute.
     *
     * @return the <code>type</code> attribute.
     */
    public String getDatatype() {
        return this.type;
    }

    /**
     * Returns the <code>readonly</code> attribute.
     *
     * @return the <code>readonly</code> attribute.
     */
    public String getReadonly() {
        return this.readonly;
    }

    /**
     * Returns the <code>required</code> attribute.
     *
     * @return the <code>required</code> attribute.
     */
    public String getRequired() {
        return this.required;
    }

    /**
     * Returns the <code>relevant</code> attribute.
     *
     * @return the <code>relevant</code> attribute.
     */
    public String getRelevant() {
        return this.relevant;
    }

    /**
     * Returns the <code>calculate</code> attribute.
     *
     * @return the <code>calculate</code> attribute.
     */
    public String getCalculate() {
        return this.calculate;
    }

    /**
     * Returns the <code>constraint</code> attribute.
     *
     * @return the <code>constraint</code> attribute.
     */
    public String getConstraint() {
        return this.constraint;
    }

    /**
     * Returns the <code>p3ptype</code> attribute.
     *
     * @return the <code>p3ptype</code> attribute.
     */
    public String getP3PType() {
        return this.p3ptype;
    }

    /**
     * Assigns an XPath reference finder.
     *
     * @param referenceFinder the XPath reference finder.
     */
    public void setReferenceFinder(XPathReferenceFinder referenceFinder) {
        this.referenceFinder = referenceFinder;
    }

    /**
     * Returns the set of all node names referenced by the <code>readonly</code> expression.
     *
     * @return the set of all node names referenced by the <code>readonly</code> expression.
     */
    public Set getReadonlyReferences() {
        return this.readonlyReferences;
    }

    /**
     * Returns the set of all node names referenced by the <code>required</code> expression.
     *
     * @return the set of all node names referenced by the <code>required</code> expression.
     */
    public Set getRequiredReferences() {
        return this.requiredReferences;
    }

    /**
     * Returns the set of all node names referenced by the <code>relevant</code> expression.
     *
     * @return the set of all node names referenced by the <code>relevant</code> expression.
     */
    public Set getRelevantReferences() {
        return this.relevantReferences;
    }

    /**
     * Returns the set of all node names referenced by the <code>calculate</code> expression.
     *
     * @return the set of all node names referenced by the <code>calculate</code> expression.
     */
    public Set getCalculateReferences() {
        return this.calculateReferences;
    }

    /**
     * Returns the set of all node names referenced by the <code>constraint</code> expression.
     *
     * @return the set of all node names referenced by the <code>constraint</code> expression.
     */
    public Set getConstraintReferences() {
        return this.constraintReferences;
    }

    // implementation of 'org.chiba.xml.events.DefaultAction'

    /**
     * Performs the implementation specific default action for this event.
     *
     * @param event the event.
     */
    public void performDefault(Event event) {
        if (event.getType().equals(XFormsEventNames.BINDING_EXCEPTION)) {
            getLogger().error(this + " binding exception: " + ((XMLEvent) event).getContextInfo(XMLEvent.DIRTY_DEFAULT_INFO));
            return;
        }
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
        initializeBindingContext();
        initializeModelItems();
        Initializer.initializeBindElements(getModel(), getElement(), this.referenceFinder);
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
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.BINDING_EXCEPTION, this);
    }

    /**
     * Initializes the binding context.
     *
     * @throws XFormsException if any error occured during binding context init.
     */
    protected void initializeBindingContext() throws XFormsException {
        // resolve location path and instance id
        this.locationPath = this.container.getBindingResolver().resolve(this);
        this.instanceId = this.model.computeInstanceId(this.locationPath);
        if (this.instanceId == null) {
            throw new XFormsBindingException("wrong instance id", this.target, this.locationPath);
        }

        // get type attributes
        this.type = getXFormsAttribute(TYPE_ATTRIBUTE);
        this.p3ptype = getXFormsAttribute(P3PTYPE_ATTRIBUTE);

        // get model item attributes and analyze path structure
        this.readonly = getXFormsAttribute(READONLY_ATTRIBUTE);
        if (this.readonly != null) {
            this.readonlyReferences = this.referenceFinder.getReferences(this.readonly);
        }

        this.required = getXFormsAttribute(REQUIRED_ATTRIBUTE);
        if (this.required != null) {
            this.requiredReferences = this.referenceFinder.getReferences(this.required);
        }

        this.relevant = getXFormsAttribute(RELEVANT_ATTRIBUTE);
        if (this.relevant != null) {
            this.relevantReferences = this.referenceFinder.getReferences(this.relevant);
        }

        this.calculate = getXFormsAttribute(CALCULATE_ATTRIBUTE);
        if (this.calculate != null) {
            this.calculateReferences = this.referenceFinder.getReferences(this.calculate);
        }

        this.constraint = getXFormsAttribute(CONSTRAINT_ATTRIBUTE);
        if (this.constraint != null) {
            this.constraintReferences = this.referenceFinder.getReferences(this.constraint);
        }
    }

    /**
     * Initializes all bound model items.
     *
     * @throws XFormsException if any error occured during model item init.
     */
    protected void initializeModelItems() throws XFormsException {
        Instance instance = getModel().getInstance(getInstanceId());
        Iterator iterator = instance.iterateModelItems(getLocationPath(), false);
        if (iterator != null) {
            ModelItem modelItem;
            while (iterator.hasNext()) {
                modelItem = (ModelItem) iterator.next();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(this + " init: model item for " + modelItem.getNode());
                }

                // 4.2.1 - 4.b applying model item properties to each node
                initializeModelItemProperties(modelItem);
            }
        }
    }

    /**
     * Initializes the model item properties of the specified model item.
     *
     * @param item the model item.
     * @throws XFormsException if any error occured during model item properties init.
     */
    protected void initializeModelItemProperties(ModelItem item) throws XFormsException {
        DeclarationView declaration = item.getDeclarationView();

        if (this.type != null) {
            if (declaration.getDatatype() != null) {
                throw new XFormsBindingException("property 'type' already present at model item", this.target, this.id);
            }

            if (!this.model.getValidator().isSupported(this.type)) {
                throw new XFormsBindingException("datatype '" + this.type + "' is not supported", this.target, this.id);
            }
            if (!this.model.getValidator().isKnown(this.type)) {
                throw new XFormsBindingException("datatype '" + this.type + "' is unknown", this.target, this.id);
            }

            declaration.setDatatype(this.type);
        }

        if (this.readonly != null) {
            if (declaration.getReadonly() != null) {
                throw new XFormsBindingException("property 'readonly' already present at model item", this.target, this.id);
            }

            declaration.setReadonly(this.readonly);
        }

        if (this.required != null) {
            if (declaration.getRequired() != null) {
                throw new XFormsBindingException("property 'required' already present at model item", this.target, this.id);
            }

            declaration.setRequired(this.required);
        }

        if (this.relevant != null) {
            if (declaration.getRelevant() != null) {
                throw new XFormsBindingException("property 'relevant' already present at model item", this.target, this.id);
            }

            declaration.setRelevant(this.relevant);
        }

        if (this.calculate != null) {
            if (declaration.getCalculate() != null) {
                throw new XFormsBindingException("property 'calculate' already present at model item", this.target, this.id);
            }

            declaration.setCalculate(this.calculate);
        }

        if (this.constraint != null) {
            if (declaration.getConstraint() != null) {
                throw new XFormsBindingException("property 'constraint' already present at model item", this.target, this.id);
            }

            declaration.setConstraint(this.constraint);
        }

        if (this.p3ptype != null) {
            if (declaration.getP3PType() != null) {
                throw new XFormsBindingException("property 'p3ptype' already present at model item", this.target, this.id);
            }

            declaration.setP3PType(this.p3ptype);
        }
    }

    /**
     * Disposes the default action.
     */
    protected void disposeDefaultAction() {
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.BINDING_EXCEPTION, this);
    }

}

// end of class
