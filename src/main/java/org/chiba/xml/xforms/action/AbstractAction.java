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
package org.chiba.xml.xforms.action;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.JXPathException;
import org.apache.xerces.dom.ElementImpl;
import org.chiba.xml.events.DOMEventNames;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.events.XMLEvent;
import org.chiba.xml.events.XMLEventConstants;
import org.chiba.xml.ns.NamespaceConstants;
import org.chiba.xml.xforms.XFormsConstants;
import org.chiba.xml.xforms.XFormsElement;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.core.UpdateHandler;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xpath.XPathUtil;
import org.w3c.dom.Element;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;

/**
 * Base class for all action implementations.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: AbstractAction.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public abstract class AbstractAction extends XFormsElement implements EventListener, XFormsAction {
    /**
     * The id of the containing repeat item, if any.
     */
    protected String repeatItemId;
    
    protected Object eventContextInfo;

    /**
     * The event type by which this action is triggered.
     */
    protected String eventType;
    
    private Event event;
    
    private Set<String> eventsTypes;

    /**
     * Creates an action implementation.
     *
     * @param element the element.
     * @param model the context model.
     */
    public AbstractAction(Element element, Model model) {
        super(element, model);
    }

    // repeat stuff

    /**
     * Sets the id of the repeat item this element is contained in.
     *
     * @param repeatItemId the id of the repeat item this element is contained
     * in.
     */
    public void setRepeatItemId(String repeatItemId) throws XFormsException {
        this.repeatItemId = repeatItemId;
    }

    /**
     * Returns the id of the repeat item this element is contained in.
     *
     * @return the id of the repeat item this element is contained in.
     */
    public String getRepeatItemId() {
        return this.repeatItemId;
    }

    /**
     * Checks wether this element is repeated.
     *
     * @return <code>true</code> if this element is contained in a repeat item,
     * <code>false</code> otherwise.
     */
    public boolean isRepeated() {
        return this.repeatItemId != null;
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

        initializeAction();
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

        disposeAction();
    }

    // lifecycle template methods

    /**
     * Initializes this action.
     */
    protected final void initializeAction() {
        if (!(getParentObject() instanceof XFormsAction)) {
            String event = this.element.getAttributeNS(NamespaceConstants.XMLEVENTS_NS, XMLEventConstants.EVENT_ATTRIBUTE);
            if (event.length() == 0) {
                // default if nothing other is specified
                event = DOMEventNames.ACTIVATE;
            }
            this.eventType = event;
            XFormsElement parent = getParentObject();

            parent.getTarget().addEventListener(this.eventType, this, false);

            // todo: observer
            // todo: target
            // todo: phase
            // todo: propagate


            if (getLogger().isDebugEnabled()) {
                getLogger().debug(this + " init: added handler for event '" + this.eventType + "' to " + parent);
            }
        }
    }

    /**
     * Disposes this action.
     */
    protected final void disposeAction() {
        if (!(getParentObject() instanceof XFormsAction)) {
            XFormsElement parent = getParentObject();
            parent.getTarget().removeEventListener(this.eventType, this, false);
        }
    }

    // implementation of org.w3c.dom.event.EventListener

    /**
     * This method is called whenever an event occurs of the type for which the
     * <code>EventListener</code> interface was registered.
     *
     * @param event The <code>Event</code> contains contextual information about
     * the event. It also contains the <code>stopPropagation</code> and
     * <code>preventDefault</code> methods which are used in determining the
     * event's flow and default action.
     */
    public final void handleEvent(Event event) {
        try {
            if (event.getType().equals(this.eventType)) {
                if (getLogger().isDebugEnabled()) {
                    String phase = "";
                    switch (event.getEventPhase()) {
                        case Event.CAPTURING_PHASE: phase = "capturing phase"; break;
                        case Event.AT_TARGET: phase = "at target"; break;
                        case Event.BUBBLING_PHASE: phase = "bubbling phase"; break;
                    }
                    getLogger().debug(this + " handling event '" + this.eventType + "' (" + phase + ")");
                }
                
                setEvent(event);
                
                if(event instanceof XMLEvent)
                	eventContextInfo = ((XMLEvent)event).getContextInfo();

                performWhileAndIf(this.element);
                
            }
        }
        catch (Exception e) {
            // handle exception, prevent default action and stop event propagation
            this.container.handleEventException(e);
            event.preventDefault();
            event.stopPropagation();
        }
    }

	protected void performWhileAndIf(Element actionEl) throws XFormsException {
		final XFormsAction action = (XFormsAction)((ElementImpl)actionEl).getUserData();
		final String whileCondition = XFormsElement.getXFormsAttribute(actionEl,XFormsConstants.WHILE_ATTRIBUTE);

		if(whileCondition == null){
			if(execute(actionEl)){
				action.perform();
		    }
		}else{
		    while(evalCondition(actionEl, whileCondition))
		    {
		    	if(execute(actionEl)){
		    		action.perform();
		        }
		    }
		}
	}

    /**
     * checks for existence of XForms 1.1 'if' attribute and if present evaluates it.
     *
     * @param action the DOM Element representing the action
     * @return true if no 'if' attribute is present or the if condition evaluates to 'true'. False otherwise
     */
    protected boolean execute (Element action){
        String ifCondition = XFormsElement.getXFormsAttribute(action,XFormsConstants.IF_CONDITION);

        if(ifCondition == null){
            return true;
        }else{
            if (evalCondition(action, ifCondition)) return true;
        }
        return false;
    }

    protected boolean evalCondition(Element action,String ifCondition) {
        //get the global XPath context
        JXPathContext context = this.model.getDefaultInstance().getInstanceContext();

        Boolean b;
        //get the right current context - if we're not bound we've to walk up to get it
        String currentPath = getParentContextPath(action);
        if(currentPath == null) {
            //try to evaluate ifCondition without context -> it may contain an absolute locationpath
            try{
                b = (Boolean) context.getValue(ifCondition);
            }catch(JXPathException jxe){
                jxe.printStackTrace();
                return true; //default to true behaving as if the if attribute weren't there and therefore executing the action
            }
        }else{
            String expr = XPathUtil.joinStep(new String[]{currentPath,ifCondition});
            b  = (Boolean) context.getValue("boolean(" + expr +")");
        }
        return b.booleanValue();
    }
    // convenience

    /**
     * Tells the action wether to perform a rebuild or not.
     * <p/>
     * If an update handler is set on the context model, the rebuild will be
     * deferred by the handler. If there is no update handler and the rebuild
     * flag is set to true, the rebuild takes place immediately.
     *
     * @param rebuild specifies wether to perform a rebuild or not.
     */
    protected final void doRebuild(boolean rebuild) throws XFormsException {
        UpdateHandler updateHandler = this.model.getUpdateHandler();
        if (updateHandler != null) {
            updateHandler.doRebuild(rebuild);
            return;
        }

        if (rebuild) {
            this.container.dispatch(this.model.getTarget(), XFormsEventNames.REBUILD, null);
        }
    }

    /**
     * Tells the action wether to perform a recalculate or not.
     * <p/>
     * If an update handler is set on the context model, the recalculate will be
     * deferred by the handler. If there is no update handler and the recalculate
     * flag is set to true, the recalculate takes place immediately.
     *
     * @param recalculate specifies wether to perform a recalculate or not.
     */
    protected final void doRecalculate(boolean recalculate) throws XFormsException {
        UpdateHandler updateHandler = this.model.getUpdateHandler();
        if (updateHandler != null) {
            updateHandler.doRecalculate(recalculate);
            return;
        }

        if (recalculate) {
            this.container.dispatch(this.model.getTarget(), XFormsEventNames.RECALCULATE, null);
        }
    }

    /**
     * Tells the action wether to perform a revalidate or not.
     * <p/>
     * If an update handler is set on the context model, the revalidate will be
     * deferred by the handler. If there is no update handler and the revalidate
     * flag is set to true, the revalidate takes place immediately.
     *
     * @param revalidate specifies wether to perform a revalidate or not.
     */
    protected final void doRevalidate(boolean revalidate) throws XFormsException {
        UpdateHandler updateHandler = this.model.getUpdateHandler();
        if (updateHandler != null) {
            updateHandler.doRevalidate(revalidate);
            return;
        }

        if (revalidate) {
            this.container.dispatch(this.model.getTarget(), XFormsEventNames.REVALIDATE, null);
        }
    }

    /**
     * Tells the action wether to perform a refresh or not.
     * <p/>
     * If an update handler is set on the context model, the refresh will be
     * deferred by the handler. If there is no update handler and the refresh
     * flag is set to true, the refresh takes place immediately.
     *
     * @param refresh specifies wether to perform a refresh or not.
     */
    protected final void doRefresh(boolean refresh) throws XFormsException {
        UpdateHandler updateHandler = this.model.getUpdateHandler();
        if (updateHandler != null) {
            updateHandler.doRefresh(refresh);
            return;
        }

        if (refresh) {
            this.container.dispatch(this.model.getTarget(), XFormsEventNames.REFRESH, null);
        }
    }

	protected Set<String> getEventsTypes() {
		
		if(eventsTypes == null)
			eventsTypes = new HashSet<String>();
		
		return eventsTypes;
	}

	protected void setEventsTypes(Set<String> eventsTypes) {
		this.eventsTypes = eventsTypes;
	}

	protected Event getEvent() {
		return event;
	}

	protected void setEvent(Event event) {
		this.event = event;
	}
}