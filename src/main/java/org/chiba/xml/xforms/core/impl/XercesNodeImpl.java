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
package org.chiba.xml.xforms.core.impl;

import org.apache.xerces.dom.NodeImpl;
import org.chiba.xml.xforms.core.DeclarationView;
import org.chiba.xml.xforms.core.LocalUpdateView;
import org.chiba.xml.xforms.core.ModelItem;
import org.chiba.xml.xforms.core.StateChangeView;

/**
 * ModelItem implementation based on Xerces' NodeImpl.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: XercesNodeImpl.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class XercesNodeImpl implements ModelItem {

    private String id;
    private NodeImpl node;
    private ModelItem parent;
    private DeclarationViewImpl declarationView;
    private LocalUpdateViewImpl localUpdateView;
    private StateChangeViewImpl stateChangeView;
    private String filename;
    private String mediatype;

    /**
     * Creates a new Xerces NodeImpl based ModelItem implementation.
     *
     * @param id the id of this model item.
     */
    public XercesNodeImpl(String id) {
        this.id = id;
        this.declarationView = new DeclarationViewImpl();
        this.localUpdateView = new LocalUpdateViewImpl();
        this.stateChangeView = new StateChangeViewImpl(this);
    }

    /**
     * Returns the id of this model item.
     *
     * @return the id of this model item.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the node of this model item.
     *
     * @return the node of this model item.
     */
    public Object getNode() {
        return this.node;
    }

    /**
     * Stes the node of this model item.
     *
     * @param node the node of this model item.
     */
    public void setNode(Object node) {
        this.node = (NodeImpl) node;
    }

    /**
     * Returns the parent of this model item.
     *
     * @return the parent of this model item.
     */
    public ModelItem getParent() {
        return this.parent;
    }

    /**
     * Sets the parent of this model item.
     *
     * @param parent the parent of this model item.
     */
    public void setParent(ModelItem parent) {
        this.parent = parent;
    }

    /**
     * Returns the computed <code>readonly</code> state.
     * <p/>
     * A model item is readonly when its readonly property evaluates to
     * <code>true</code> <i>or</i> its parent item is readonly.
     *
     * @return the computed <code>readonly</code> state.
     */
    public boolean isReadonly() {
        if (this.parent != null) {
            return this.localUpdateView.isLocalReadonly() || this.parent.isReadonly();
        }

        return this.localUpdateView.isLocalReadonly();
    }

    /**
     * Returns the computed <code>required</code> state.
     * <p/>
     * A model item is required when its required property evaluates to
     * <code>true</code>.
     *
     * @return the computed <code>required</code> state.
     */
    public boolean isRequired() {
        return this.localUpdateView.isLocalRequired();
    }

    /**
     * Returns the computed <code>enabled</code> state.
     * <p/>
     * A model item is enabled when its relevant property evaluates to
     * <code>true</code> <i>and</i> its parent item is enabled.
     *
     * @return the computed <code>enabled</code> state.
     */
    public boolean isEnabled() {
        if (this.parent != null) {
            return this.localUpdateView.isLocalRelevant() && this.parent.isEnabled();
        }

        return this.localUpdateView.isLocalRelevant();
    }

    /**
     * Returns the computed <code>valid</code> state.
     * <p/>
     * A model item is valid when its constraint property evaluates to
     * <code>true</code> <i>and</i> the type property is satisfied.
     *
     * @return the computed <code>valid</code> state.
     */
    public boolean isValid() {
        return this.localUpdateView.isConstraintValid() && this.localUpdateView.isDatatypeValid();
    }

    /**
     * Returns the value of this model item.
     *
     * @return the value of this model item.
     */
    public String getValue() {
        return this.node.getNodeValue();
    }

    /**
     * Sets the value of this model item.
     *
     * @param value the value of this model item.
     */
    public void setValue(String value) {
        if (valueChanged(value)) {
            this.node.setNodeValue(value);
        }
    }

    /**
     * Returns the declaration view of this model item.
     *
     * @return the declaration view of this model item.
     */
    public DeclarationView getDeclarationView() {
        return this.declarationView;
    }

    /**
     * Returns the local update view of this model item.
     *
     * @return the local update view of this model item.
     */
    public LocalUpdateView getLocalUpdateView() {
        return this.localUpdateView;
    }

    /**
     * Returns the state change view of this model item.
     *
     * @return the state change view of this model item.
     */
    public StateChangeView getStateChangeView() {
        return this.stateChangeView;
    }

    /**
     * Checks wether this model item is nillable.
     * <p/>
     * A model item is considered nillable if it is an element and has a
     * <code>xsi:nil</code> attribute with the value <code>true</code>.
     *
     * @return <code>true</code> if this model item is nillable, otherwise
     * <code>false</code>.
     */
    public boolean isXSINillable() {
        return false;
    }

    /**
     * Returns the additional schema type declaration of this model item.
     * <p/>
     * A model item has an additional schema type declaration if it is an
     * element and has a <code>xsi:type</code> attribute.
     *
     * @return the additional schema type declaration of this model item or
     * <code>null</code> if there is no such type declaration.
     */
    public String getXSIType() {
        return null;
    }

    // todo: file upload fixes, the following methods are needed for form-data serialization ...
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getMediatype() {
        return this.mediatype;
    }

    // helper

    /**
     * Check wether the new value differs from the current value. If so, the
     * state change view will be notified to set the corresponding flag.
     *
     * @param value the new value of this model item.
     * @return <code>true</code> if the new value differs from the current
     * value, otherwise <code>false</code>
     */
    protected final boolean valueChanged(String value) {
        // check current value
        String current = getValue();
        if ((current == null && value == null) || (current != null && current.equals(value))) {
            return false;
        }

        // notify state change view
        this.stateChangeView.setValueChanged();
        return true;
    }

}
