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

import org.chiba.xml.events.DefaultAction;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.core.ModelItem;
import org.chiba.xml.xforms.core.UpdateHandler;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.ui.state.BoundElementState;
import org.chiba.xml.xforms.ui.state.UIElementStateUtil;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;

/**
 * Base class for all form controls.
 *
 * @author Joern Turner
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: AbstractFormControl.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public abstract class AbstractFormControl extends BindingElement implements DefaultAction {

    /**
     * Creates a new abstract form control.
     *
     * @param element the host document element.
     * @param model the context model.
     */
    public AbstractFormControl(Element element, Model model) {
        super(element, model);
    }

    // todo: extract interface
    // form control methods

    /**
     * Sets the value of this form control.
     * <p/>
     * The bound instance data is updated and the event sequence for this
     * control is executed. Event sequences are described in Chapter 4.6 of
     * XForms 1.0 Recommendation.
     *
     * @param value the value to be set.
     */
    public abstract void setValue(String value) throws XFormsException;

    /**
     * Returns the current value of this form control.
     *
     * @return the current value of this form control.
     */
    public Object getValue()
    {
        if(this.elementState != null)
        {
        		return this.elementState.getValue();
        }

        return null;
    }

    /**
     * Returns the datatype of the bound node.
     *
     * @return the datatype of the bound node.
     */
    public String getDatatype() {
        if (isBound()) {
            ModelItem modelItem = this.model.getInstance(getInstanceId()).getModelItem(getLocationPath());
            if (modelItem != null) {
                return UIElementStateUtil.getDatatype(modelItem, this.element);
            }
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
        initializeElementState();
        initializeChildren();
        initializeActions();
    }

    // lifecycle template methods

    /**
     * Initializes the default action.
     */
    protected void initializeDefaultAction() {
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.BINDING_EXCEPTION, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.PREVIOUS, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.NEXT, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.FOCUS, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.HELP, this);
        this.container.getXMLEventService().registerDefaultAction(this.target, XFormsEventNames.HINT, this);
    }

    /**
     * Disposes the default action.
     */
    protected void disposeDefaultAction() {
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.BINDING_EXCEPTION, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.PREVIOUS, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.NEXT, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.FOCUS, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.HELP, this);
        this.container.getXMLEventService().deregisterDefaultAction(this.target, XFormsEventNames.HINT, this);
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
        return isBound() ? new BoundElementState() : null;
    }

    /**
     * Dispatches the '4.6.7 Value Change with Focus Change' event sequence.
     *
     * @throws XFormsException if an error occurred during event sequencing.
     */
    protected void dispatchValueChangeSequence() throws XFormsException {
        // prevent chiba internal value change dispatching on this control
        if (this.elementState != null) {
            this.elementState.setProperty("dispatchValueChange", Boolean.FALSE);
        }

        // update behaviour
        UpdateHandler updateHandler = this.model.getUpdateHandler();
        if (updateHandler == null) {
            this.container.dispatch(this.model.getTarget(), XFormsEventNames.RECALCULATE, null);
            this.container.dispatch(this.model.getTarget(), XFormsEventNames.REVALIDATE, null);
            this.container.dispatch(this.model.getTarget(), XFormsEventNames.REFRESH, null);
        }
        else {
            updateHandler.doRecalculate(true);
            updateHandler.doRevalidate(true);
            updateHandler.doRefresh(true);
        }

        // reset chiba internal value change dispatching on this control
        if (this.elementState != null) {
            this.elementState.setProperty("dispatchValueChange", Boolean.TRUE);
        }
    }

    // implementation of 'org.chiba.xml.events.DefaultAction'

    /**
     * Performs the implementation specific default action for this event.
     *
     * @param event the event.
     */
    public void performDefault(Event event) {
        super.performDefault(event);
        if (isCancelled(event)) {
            getLogger().debug(this + " event " + event.getType() + " cancelled");
            return;
        }
        if (event.getType().equals(XFormsEventNames.PREVIOUS)) {
            // todo
            getLogger().warn(this + " default action for " + event.getType() + " is not implemented yet");
            return;
        }
        if (event.getType().equals(XFormsEventNames.NEXT)) {
            // todo
            getLogger().warn(this + " default action for " + event.getType() + " is not implemented yet");
            return;
        }
        if (event.getType().equals(XFormsEventNames.FOCUS)) {
            // todo
            getLogger().warn(this + " default action for " + event.getType() + " is not implemented yet");
            return;
        }
        if (event.getType().equals(XFormsEventNames.HELP)) {
            // todo
            getLogger().warn(this + " default action for " + event.getType() + " is not implemented yet");
            return;
        }
        if (event.getType().equals(XFormsEventNames.HINT)) {
            // todo
            getLogger().warn(this + " default action for " + event.getType() + " is not implemented yet");
            return;
        }
    }

}

// end of class
