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
package org.chiba.xml.events;

/**
 * All event names defined by the XForms Spec.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: XFormsEventNames.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public interface XFormsEventNames {

    // XForms initialization events

    /**
     * XForms init event constant.
     */
    String MODEL_CONSTRUCT = "xforms-model-construct";

    /**
     * XForms init event constant.
     */
    String MODEL_CONSTRUCT_DONE = "xforms-model-construct-done";

    /**
     * XForms init event constant.
     */
    String READY = "xforms-ready";

    /**
     * XForms init event constant.
     */
    String MODEL_DESTRUCT = "xforms-model-destruct";

    // XForms interaction events

    /**
     * XForms interaction event constant.
     */
    String PREVIOUS = "xforms-previous";

    /**
     * XForms interaction event constant.
     */
    String NEXT = "xforms-next";

    /**
     * XForms interaction event constant.
     */
    String FOCUS = "xforms-focus";

    /**
     * XForms interaction event constant.
     */
    String HELP = "xforms-help";

    /**
     * XForms interaction event constant.
     */
    String HINT = "xforms-hint";

    /**
     * XForms interaction event constant.
     */
    String REBUILD = "xforms-rebuild";

    /**
     * XForms interaction event constant.
     */
    String REFRESH = "xforms-refresh";

    /**
     * XForms interaction event constant.
     */
    String REVALIDATE = "xforms-revalidate";

    /**
     * XForms interaction event constant.
     */
    String RECALCULATE = "xforms-recalculate";

    /**
     * XForms interaction event constant.
     */
    String RESET = "xforms-reset";

    /**
     * XForms interaction event constant.
     */
    String SUBMIT = "xforms-submit";

    // XForms notification events

    /**
     * XForms notification event constant.
     */
    String VALUE_CHANGED = "xforms-value-changed";

    /**
     * XForms notification event constant.
     */
    String SELECT = "xforms-select";

    /**
     * XForms notification event constant.
     */
    String DESELECT = "xforms-deselect";

    /**
     * XForms notification event constant.
     */
    String SCROLL_FIRST = "xforms-scroll-first";

    /**
     * XForms notification event constant.
     */
    String SCROLL_LAST = "xforms-scroll-last";

    /**
     * XForms notification event constant.
     */
    String INSERT = "xforms-insert";

    /**
     * XForms notification event constant.
     */
    String DELETE = "xforms-delete";

    /**
     * XForms notification event constant.
     */
    String VALID = "xforms-valid";

    /**
     * XForms notification event constant.
     */
    String INVALID = "xforms-invalid";

    /**
     * XForms notification event constant.
     */
    String READONLY = "xforms-readonly";

    /**
     * XForms notification event constant.
     */
    String READWRITE = "xforms-readwrite";

    /**
     * XForms notification event constant.
     */
    String REQUIRED = "xforms-required";

    /**
     * XForms notification event constant.
     */
    String OPTIONAL = "xforms-optional";

    /**
     * XForms notification event constant.
     */
    String ENABLED = "xforms-enabled";

    /**
     * XForms notification event constant.
     */
    String DISABLED = "xforms-disabled";

    /**
     * XForms notification event constant.
     */
    String IN_RANGE = "xforms-in-range";

    /**
     * XForms notification event constant.
     */
    String OUT_OF_RANGE = "xforms-out-of-range";

    /**
     * XForms notification event constant.
     */
    String SUBMIT_DONE = "xforms-submit-done";

    /**
     * XForms notification event constant.
     */
    String SUBMIT_ERROR = "xforms-submit-error";

    // XForms error indications

    /**
     * XForms error indication event constant.
     */
    String BINDING_EXCEPTION = "xforms-binding-exception";

    /**
     * XForms error indication event constant.
     */
    String LINK_EXCEPTION = "xforms-link-exception";

    /**
     * XForms error indication event constant.
     */
    String LINK_ERROR = "xforms-link-error";

    /**
     * XForms error indication event constant.
     */
    String COMPUTE_EXCEPTION = "xforms-compute-exception";

    /**
     * XForms Version Exception constant.
     */
    String VERSION_EXCEPTION = "xforms-version-exception";

    /**
     * Constant representing event property as defined in 4.5.4 the xforms-link-exception Event
     */
    String RESOURCE_URI_PROPERTY="resource-uri";
}
