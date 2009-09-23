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
import org.chiba.xml.events.*;

import java.util.HashMap;

/**
 * XMLEventInitializer ensures that pre-defined Events as in XForms are
 * initialized properly.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: DefaultXMLEventInitializer.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class DefaultXMLEventInitializer implements XMLEventInitializer {

    private static final Log LOGGER = LogFactory.getLog(DefaultXMLEventInitializer.class);

    private static final short EVENT_BUBBLES = 0;
    private static final short EVENT_CANCELABLE = 1;
    private static final short EVENT_CONTEXT = 2;

    private static final HashMap INITIALIZATION_RULES = new HashMap();
    static {
        // Initialization Events
        INITIALIZATION_RULES.put(XFormsEventNames.MODEL_CONSTRUCT, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.MODEL_CONSTRUCT_DONE, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.READY, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.MODEL_DESTRUCT, new boolean[]{true, false, false});

        // Interaction Events
        INITIALIZATION_RULES.put(XFormsEventNames.PREVIOUS, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.NEXT, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.FOCUS, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.HELP, new boolean[]{true, true, false});
        INITIALIZATION_RULES.put(XFormsEventNames.HINT, new boolean[]{true, true, false});
        INITIALIZATION_RULES.put(XFormsEventNames.REBUILD, new boolean[]{true, true, false});
        INITIALIZATION_RULES.put(XFormsEventNames.REFRESH, new boolean[]{true, true, false});
        INITIALIZATION_RULES.put(XFormsEventNames.REVALIDATE, new boolean[]{true, true, false});
        INITIALIZATION_RULES.put(XFormsEventNames.RECALCULATE, new boolean[]{true, true, false});
        INITIALIZATION_RULES.put(XFormsEventNames.RESET, new boolean[]{true, true, false});
        INITIALIZATION_RULES.put(XFormsEventNames.SUBMIT, new boolean[]{true, true, false});

        // Notification Events
        INITIALIZATION_RULES.put(DOMEventNames.ACTIVATE, new boolean[]{true, true, false});
        INITIALIZATION_RULES.put(XFormsEventNames.VALUE_CHANGED, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.SELECT, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.DESELECT, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.SCROLL_FIRST, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.SCROLL_LAST, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.INSERT, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(XFormsEventNames.DELETE, new boolean[]{true, false, true   });
        INITIALIZATION_RULES.put(XFormsEventNames.VALID, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.INVALID, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(DOMEventNames.FOCUS_IN, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(DOMEventNames.FOCUS_OUT, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.READONLY, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.READWRITE, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.REQUIRED, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.OPTIONAL, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.ENABLED, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.DISABLED, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.IN_RANGE, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.OUT_OF_RANGE, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.SUBMIT_DONE, new boolean[]{true, false, false});
        INITIALIZATION_RULES.put(XFormsEventNames.SUBMIT_ERROR, new boolean[]{true, false, true});

        // Error Indications
        INITIALIZATION_RULES.put(XFormsEventNames.BINDING_EXCEPTION, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(XFormsEventNames.LINK_EXCEPTION, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(XFormsEventNames.LINK_ERROR, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(XFormsEventNames.COMPUTE_EXCEPTION, new boolean[]{true, false, true});

        // Chiba Events
        INITIALIZATION_RULES.put(ChibaEventNames.LOAD_URI, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.RENDER_MESSAGE, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.REPLACE_ALL, new boolean[]{true, false, true});

        INITIALIZATION_RULES.put(ChibaEventNames.STATE_CHANGED, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.NODE_INSERTED, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.NODE_DELETED, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.PROTOTYPE_CLONED, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.ID_GENERATED, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.ITEM_INSERTED, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.ITEM_DELETED, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.INDEX_CHANGED, new boolean[]{true, false, true});
        INITIALIZATION_RULES.put(ChibaEventNames.SWITCH_TOGGLED, new boolean[]{true, false, true});
    }

    /**
     * Initializes the given event. If the event type is known to the initializer, the
     * event will be initialized according to the initializer's internal configuration.
     * Otherwise the specified parameters are used.
     *
     * @param event the event.
     * @param type specifies the event type.
     * @param bubbles specifies wether the event can bubble.
     * @param cancelable specifies wether the event's default action can be prevented.
     * @param context optionally specifies contextual information.
     */
    public void initXMLEvent(XMLEvent event, String type, boolean bubbles, boolean cancelable, Object context) {
        boolean[] rules = (boolean[]) INITIALIZATION_RULES.get(type);

        if (rules != null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("initializing event " + type + " from rule: bubbles=" + rules[EVENT_BUBBLES] + ", cancelable=" + rules[EVENT_CANCELABLE] + ", context=" + (rules[EVENT_CONTEXT] ? "yes" : "no"));
            }
            event.initXMLEvent(type, rules[EVENT_BUBBLES], rules[EVENT_CANCELABLE], rules[EVENT_CONTEXT] ? context : null);
        }
        else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("initializing event " + type + " from parameters: bubbles=" + bubbles + ", cancelable=" + cancelable + ", context=" + (context != null ? "yes" : "no"));
            }
            event.initXMLEvent(type, bubbles, cancelable, context);
        }
    }
}
