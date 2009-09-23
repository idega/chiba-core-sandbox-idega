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
package org.chiba.xml.events.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.NodeImpl;
import org.chiba.xml.events.*;
import org.chiba.xml.xforms.XFormsElement;
import org.w3c.dom.events.EventTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * XMLEventService provides a dispatching facility for XML Events. Additionally,
 * default actions can be registered per Event Target and Event type.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: DefaultXMLEventService.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class DefaultXMLEventService implements XMLEventService {

    private static final Log LOGGER = LogFactory.getLog(DefaultXMLEventService.class);

    private XMLEventFactory eventFactory;
    private XMLEventInitializer eventInitializer;
    private Map defaultActions;

    /**
     * Returns the XML Event Factory.
     *
     * @return the XML Event Factory.
     */
    public XMLEventFactory getXMLEventFactory() {
        return this.eventFactory;
    }

    /**
     * Sets the XML Event Factory.
     *
     * @param eventFactory the XML Event Factory.
     */
    public void setXMLEventFactory(XMLEventFactory eventFactory) {
        this.eventFactory = eventFactory;
    }

    /**
     * Returns the XML Event Initializer.
     *
     * @return the XML Event Initializer.
     */
    public XMLEventInitializer getXMLEventInitializer() {
        return this.eventInitializer;
    }

    /**
     * Sets the XML Event Initializer.
     *
     * @param eventInitializer the XML Event Initializer.
     */
    public void setXMLEventInitializer(XMLEventInitializer eventInitializer) {
        this.eventInitializer = eventInitializer;
    }

    /**
     * Registers a default action for the specified event type occurring on
     * the given event target.
     * <p/>
     * Only one default action can be registered for a particular event type and
     * event target combination.
     *
     * @param target the event target.
     * @param type specifies the event type.
     * @param action the default action.
     */
    public void registerDefaultAction(EventTarget target, String type, DefaultAction action) {
        // check parameters, throw exception ?
        if (target == null || type == null || action == null) {
            return;
        }

        // check master map
        if (this.defaultActions == null) {
            this.defaultActions = new HashMap();
        }

        // check sub-map
        Map types = (Map) this.defaultActions.get(target);
        if (types == null) {
            types = new HashMap();
            this.defaultActions.put(target, types);
        }

        // store type/action association
        types.put(type, action);
    }

    /**
     * Deregisters a default action for the specified event type occurring on
     * the given event target.
     *
     * @param target the event target.
     * @param type specifies the event type.
     * @param action the default action.
     */
    public void deregisterDefaultAction(EventTarget target, String type, DefaultAction action) {
        // check parameters, throw exception ?
        if (target == null || type == null || action == null) {
            return;
        }

        // check master map
        if (this.defaultActions == null) {
            return;
        }

        // check sub-map
        Map types = (Map) this.defaultActions.get(target);
        if (types == null) {
            return;
        }

        // check default action
        DefaultAction current = (DefaultAction) types.get(type);
        if (current == null || !current.equals(action)) {
            return;
        }

        // remove type/action association
        types.remove(type);

        // memory clean-up
        if (types.isEmpty()) {
            this.defaultActions.remove(target);

            if (this.defaultActions.isEmpty()) {
                this.defaultActions = null;
            }
        }
    }

    /**
     * Dispatches the specified event to the given target.
     *
     * @param target the event target.
     * @param type specifies the event type.
     * @param bubbles specifies wether the event can bubble.
     * @param cancelable specifies wether the event's default action can be prevented.
     * @param context optionally specifies contextual information.
     * @return <code>true</code> if one of the event listeners called
     * <code>preventDefault</code>, otherwise <code>false</code>.
     */
    public boolean dispatch(EventTarget target, String type, boolean bubbles, boolean cancelable, Object context) {
        if (LOGGER.isDebugEnabled()) {
            if(target instanceof NodeImpl){
                NodeImpl node = (NodeImpl) target;
                Object object = node.getUserData();
                if(object instanceof XFormsElement){
                    //Note: the cast seems pointless here but makes sure the right toString method is called
                    LOGGER.debug("dispatch event: " + type + " to " + ((XFormsElement)object).toString()) ;
                }
            } else{
                LOGGER.debug("dispatch event: " + type + " to " + target);
            }
        }

        XMLEvent event = this.eventFactory.createXMLEvent(type);
        this.eventInitializer.initXMLEvent(event, type, bubbles, cancelable, context);

        long start;
        long end;

        start = System.currentTimeMillis();
        boolean preventDefault = target.dispatchEvent(event);
        end = System.currentTimeMillis();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("dispatch event: " + type + " handling needed " + (end - start) + " ms");
        }

        if (!event.getCancelable() || !preventDefault) {
            DefaultAction action = lookup(target, type);

            if (action != null) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("dispatch event: " + type + " default action at " + action);
                }

                // todo: set event phase without downcast ?
                ((XercesXMLEvent) event).eventPhase = XMLEvent.DEFAULT_ACTION;
                start = System.currentTimeMillis();
                action.performDefault(event);
                end = System.currentTimeMillis();
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("dispatch event: " + type + " default action needed " + (end - start) + " ms");
                }
            }
        }

        return preventDefault;
    }


    protected DefaultAction lookup(EventTarget target, String type) {
        // check master map
        if (this.defaultActions == null) {
            return null;
        }

        // check sub-map
        Map types = (Map) this.defaultActions.get(target);
        if (types == null) {
            return null;
        }

        // check default action
        return (DefaultAction) types.get(type);
    }
}
