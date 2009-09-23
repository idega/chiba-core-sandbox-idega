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

import org.apache.xerces.dom.ElementImpl;
import org.chiba.xml.ns.NamespaceConstants;
import org.chiba.xml.xforms.core.ModelItem;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ModelItem implementation based on Xerces' ElementImpl.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: XercesElementImpl.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class XercesElementImpl extends XercesNodeImpl implements ModelItem {

    private ElementImpl element;

    /**
     * Creates a new Xerces ElementImpl based ModelItem implementation.
     *
     * @param id the id of this model item.
     */
    public XercesElementImpl(String id) {
        super(id);
    }

    /**
     * Returns the node of this model item.
     *
     * @return the node of this model item.
     */
    public Object getNode() {
        return this.element;
    }

    /**
     * Stes the node of this model item.
     *
     * @param node the node of this model item.
     */
    public void setNode(Object node) {
        this.element = (ElementImpl) node;
    }

    /**
     * Returns the value of this model item.
     *
     * @return the value of this model item.
     */
    public String getValue() {
        Node child;
        NodeList children = this.element.getChildNodes();
        for (int index = 0; index < children.getLength(); index++) {
            child = children.item(index);
            if (Node.TEXT_NODE == child.getNodeType() ||
                    Node.CDATA_SECTION_NODE == child.getNodeType()) {
                return child.getNodeValue();
            }
        }

        return "";
    }

    /**
     * Sets the value of this model item.
     *
     * @param value the value of this model item.
     */
    public void setValue(String value) {
        if (valueChanged(value)) {
            // check for @xsi:nil
            Node nil = this.element.getAttributeNodeNS(NamespaceConstants.XMLSCHEMA_INSTANCE_NS, "nil");
            if (nil != null) {
                nil.setNodeValue(String.valueOf(value == null || value.length() == 0));
            }

            Node child;
            NodeList children = this.element.getChildNodes();
            for (int index = 0; index < children.getLength(); index++) {
                child = children.item(index);
                if (Node.TEXT_NODE == child.getNodeType() ||
                        Node.CDATA_SECTION_NODE == child.getNodeType()) {
                    child.setNodeValue(value);
                    return;
                }
            }

            this.element.insertBefore(this.element.getOwnerDocument().createTextNode(value), this.element.getFirstChild());
        }
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
        return this.element.getAttributeNS(NamespaceConstants.XMLSCHEMA_INSTANCE_NS, "nil").equals("true");
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
        return this.element.hasAttributeNS(NamespaceConstants.XMLSCHEMA_INSTANCE_NS, "type")
                ? this.element.getAttributeNS(NamespaceConstants.XMLSCHEMA_INSTANCE_NS, "type")
                : null;
    }

}
