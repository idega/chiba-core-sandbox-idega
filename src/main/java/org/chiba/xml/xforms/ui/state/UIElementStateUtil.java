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
package org.chiba.xml.xforms.ui.state;

import org.apache.commons.jxpath.JXPathContext;
import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.events.ChibaEventNames;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.ns.NamespaceConstants;
import org.chiba.xml.ns.NamespaceResolver;
import org.chiba.xml.xforms.Container;
import org.chiba.xml.xforms.core.ModelItem;
import org.chiba.xml.xforms.core.StateChangeView;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.ui.BindingElement;
import org.chiba.xml.xforms.ui.UIElementState;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventTarget;

import java.util.HashMap;
import java.util.Map;

/**
 * State keeper utility methods.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: UIElementStateUtil.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class UIElementStateUtil {

    public static final short VALID = 0;
    public static final short READONLY = 1;
    public static final short REQUIRED = 2;
    public static final short ENABLED = 3;

    /**
     * Creates the state element.
     *
     * @param parent the parent element to use.
     * @return the state element.
     */
    public static Element createStateElement(Element parent) {
        Element state = DOMUtil.findFirstChildNS(parent, NamespaceConstants.CHIBA_NS, UIElementState.STATE_ELEMENT);
        if (state != null) {
            return state;
        }

        Document document = parent.getOwnerDocument();
        state = document.createElementNS(NamespaceConstants.CHIBA_NS, NamespaceConstants.CHIBA_PREFIX + ":" + UIElementState.STATE_ELEMENT);
        parent.appendChild(state);

        return state;
    }

    /**
     * Creates or updates the specified state attribute. If the value is
     * <code>null</code>, the attribute will be removed.
     *
     * @param element the element.
     * @param name the attribute name.
     * @param value the attribute value.
     */
    public static void setStateAttribute(Element element, String name, String value) {
        if (value != null) {
            element.setAttributeNS(NamespaceConstants.CHIBA_NS, NamespaceConstants.CHIBA_PREFIX + ":" + name, value);
        }
        else {
            element.removeAttributeNS(NamespaceConstants.CHIBA_NS, name);
        }
    }

    public static boolean hasPropertyChanged(boolean[] currentProperties, boolean[] newProperties, short index) {
        return (currentProperties == null && newProperties != null) ||
                (currentProperties != null && newProperties == null) ||
                (currentProperties != null && currentProperties[index] != newProperties[index]);
    }

    public static boolean hasValueChanged(Object currentValue, Object newValue) {
        return (currentValue == null && newValue != null) ||
                (currentValue != null && newValue == null) ||
                (currentValue != null && !currentValue.equals(newValue));
    }

    public static boolean hasTypeChanged(String currentType, String newType) {
        return hasValueChanged(currentType, newType);
    }

    public static ModelItem getModelItem(BindingElement owner) {
        JXPathContext context = owner.getModel().getDefaultInstance().getInstanceContext();
        context.getVariables().declareVariable("contextmodel", owner.getModelId());

        if (owner.isBound()) {
            return owner.getModel()
                    .getInstance(owner.getInstanceId())
                    .getModelItem(owner.getLocationPath());
        }
        context.getVariables().undeclareVariable("contextmodel");

        return null;
    }

    public static boolean[] getModelItemProperties(ModelItem modelItem) {
        boolean[] properties = {true, false, false, false};
        if (modelItem != null) {
            properties[VALID] = modelItem.isValid();
            properties[READONLY] = modelItem.isReadonly();
            properties[REQUIRED] = modelItem.isRequired();
            properties[ENABLED] = modelItem.isEnabled();
        }

        return properties;
    }

    public static String getDefaultDatatype(Element element) {
        String prefix = NamespaceResolver.getPrefix(element, NamespaceConstants.XMLSCHEMA_NS);
        return prefix != null ? prefix + ":string" : "string";
    }

    public static String getDatatype(ModelItem modelItem, Element element) {
        String datatype = modelItem.getDeclarationView().getDatatype();
        if (datatype == null) {
            datatype = modelItem.getXSIType();
            if (datatype == null) {
                datatype = getDefaultDatatype(element);
            }
        }

        return datatype;
    }

    public static void dispatchXFormsEvents(BindingElement bindingElement, ModelItem modelItem, boolean[] properties) throws XFormsException {
        if (modelItem != null) {
            // dispatch events according to 4.3.4 The xforms-refresh Event
            StateChangeView stateChangeView = modelItem.getStateChangeView();
            Container container = bindingElement.getContainerObject();
            EventTarget eventTarget = bindingElement.getTarget();

            if (stateChangeView.hasValueChanged()) {
                container.dispatch(eventTarget, XFormsEventNames.VALUE_CHANGED, null);
                container.dispatch(eventTarget, properties[VALID] ? XFormsEventNames.VALID : XFormsEventNames.INVALID, null);
                container.dispatch(eventTarget, properties[READONLY] ? XFormsEventNames.READONLY : XFormsEventNames.READWRITE, null);
                container.dispatch(eventTarget, properties[REQUIRED] ? XFormsEventNames.REQUIRED : XFormsEventNames.OPTIONAL, null);
                container.dispatch(eventTarget, properties[ENABLED] ? XFormsEventNames.ENABLED : XFormsEventNames.DISABLED, null);
            }
            else {
                if (stateChangeView.hasValidChanged()) {
                    container.dispatch(eventTarget, properties[VALID] ? XFormsEventNames.VALID : XFormsEventNames.INVALID, null);
                }
                if (stateChangeView.hasReadonlyChanged()) {
                    container.dispatch(eventTarget, properties[READONLY] ? XFormsEventNames.READONLY : XFormsEventNames.READWRITE, null);
                }
                if (stateChangeView.hasRequiredChanged()) {
                    container.dispatch(eventTarget, properties[REQUIRED] ? XFormsEventNames.REQUIRED : XFormsEventNames.OPTIONAL, null);
                }
                if (stateChangeView.hasEnabledChanged()) {
                    container.dispatch(eventTarget, properties[ENABLED] ? XFormsEventNames.ENABLED : XFormsEventNames.DISABLED, null);
                }
            }
        }
    }

    public static void dispatchChibaEvents(BindingElement bindingElement, boolean[] currentProperties, boolean[] newProperties) throws XFormsException {
        dispatchChibaEvents(bindingElement, currentProperties, null, null, newProperties, null, null);
    }

    public static void dispatchChibaEvents(BindingElement bindingElement, String currentValue, String newValue) throws XFormsException {
        dispatchChibaEvents(bindingElement, null, currentValue, null, null, newValue, null);
    }

    public static void dispatchChibaEvents(BindingElement bindingElement, boolean[] currentProperties, String currentValue, boolean[] newProperties, String newValue) throws XFormsException {
        dispatchChibaEvents(bindingElement, currentProperties, currentValue, null, newProperties, newValue, null);
    }

    public static void dispatchChibaEvents(BindingElement bindingElement, boolean[] currentProperties, Object currentValue, String currentType, boolean[] newProperties, Object newValue, String newType) throws XFormsException {
        // determine changes
        Map context = new HashMap();
        if (hasPropertyChanged(currentProperties, newProperties, VALID)) {
            context.put(UIElementState.VALID_PROPERTY, String.valueOf(newProperties[VALID]));
        }
        if (hasPropertyChanged(currentProperties, newProperties, READONLY)) {
            context.put(UIElementState.READONLY_PROPERTY, String.valueOf(newProperties[READONLY]));
        }
        if (hasPropertyChanged(currentProperties, newProperties, REQUIRED)) {
            context.put(UIElementState.REQUIRED_PROPERTY, String.valueOf(newProperties[REQUIRED]));
        }
        if (hasPropertyChanged(currentProperties, newProperties, ENABLED)) {
            context.put(UIElementState.ENABLED_PROPERTY, String.valueOf(newProperties[ENABLED]));
        }
        if (hasValueChanged(currentValue, newValue)) {
            context.put("value", newValue);
        }
        if (hasTypeChanged(currentType, newType)) {
            context.put(UIElementState.TYPE_ATTRIBUTE, newType);
        }

        if (!context.isEmpty()) {
            // dispatch internal chiba event to update presentation context
            Container container = bindingElement.getContainerObject();
            EventTarget eventTarget = bindingElement.getTarget();
            container.dispatch(eventTarget, ChibaEventNames.STATE_CHANGED, context);
        }
    }
}

// end of class
