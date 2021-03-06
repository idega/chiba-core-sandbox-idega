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

import org.chiba.xml.dom.DOMUtil;
import org.chiba.xml.xforms.core.ModelItem;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xforms.ui.BindingElement;
import org.chiba.xml.xforms.ui.UIElementState;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * State keeper for bound elements.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: BoundElementState.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class BoundElementState implements UIElementState {

    private boolean handleTypes;
    private boolean handleValue;
    private BindingElement owner;
    protected Element state;
    private boolean[] currentProperties;
    private String currentType;
    private Object currentValue;
    private boolean dispatchValueChange;

    /**
     * Creates a new bound element state.
     */
    public BoundElementState() {
        this(true, true);
    }

    /**
     * Creates a new bound element state.
     *
     * @param handleTypes handle types or not.
     * @param handleValue handle value or not.
     */
    public BoundElementState(boolean handleTypes, boolean handleValue) {
        this.handleTypes = handleTypes;
        this.handleValue = handleValue;

        this.dispatchValueChange = true;
    }

    // implementation of 'org.chiba.xml.xforms.ui.UIElementState'

    /**
     * Sets the owning element.
     *
     * @param owner the owning element.
     */
    public void setOwner(BindingElement owner) {
        this.owner = owner;
    }

    /**
     * Initializes this element state.
     *
     * @throws XFormsException if an error occurred during init.
     */
    public void init() throws XFormsException {
        // create state element
        this.state = UIElementStateUtil.createStateElement(this.owner.getElement());

        // set model item properties and value only before xforms-ready (not during repeat processing)
        if (!this.owner.getModel().isReady()) {
            // get model item and its current properties
            ModelItem modelItem = UIElementStateUtil.getModelItem(this.owner);
            boolean[] properties = UIElementStateUtil.getModelItemProperties(modelItem);

            setProperties(properties);

            // keep properties
            this.currentProperties = properties;

            if (modelItem != null) {
                // set types
                if (this.handleTypes) {
                    String datatype = UIElementStateUtil.getDatatype(modelItem, this.owner.getElement());
                    String p3ptype = modelItem.getDeclarationView().getP3PType();
                    UIElementStateUtil.setStateAttribute(this.state, TYPE_ATTRIBUTE, datatype);
                    UIElementStateUtil.setStateAttribute(this.state, P3PTYPE_ATTRIBUTE, p3ptype);

                    // keep datatype
                    this.currentType = datatype;
                }

                //set value
                if(this.handleValue)
                {
                	//attempt to store the subtree (xforms:copy)
                    if(modelItem.getNode() instanceof Element)
                    {
                    	this.currentValue = storeSubtree(modelItem);
                    	Element childElement = DOMUtil.getFirstChildElement((Element)modelItem.getNode());
                    	if(childElement != null)
                    		return;
                    }
                    
	            	//otherwise fallback to a string value
	                String value = modelItem.getValue();
	                DOMUtil.setElementValue(this.state, value);
	                this.currentValue = value;
                }
            }
        }
    }

    /**
     * stores an XML subtree as child of the chiba:data element for controls that work with such
     * a structure instead of a simple value. This is especially interesting for e.g. output/@appearance="dojo:tree"
     * which allows to visualize an abritrary piece of an instance data tree.
     *
     * @param modelItem the modelItem which has a subtree as value
     * @return the stored node or null
     */
    protected Node storeSubtree(ModelItem modelItem)
    {
        Object o = modelItem.getNode();
        if(o instanceof Element)
        {
            if(DOMUtil.getFirstChildElement( (Element)o ) != null)
            {
                Node n = (Node) modelItem.getNode();
                if(n != null)
                {
                    Node imported = this.state.getOwnerDocument().importNode(n,true);
                    return this.state.appendChild(imported);
                }
            }
        }
        
        return null;
    }

    /**
     * Updates this element state.
     *
     * @throws XFormsException if an error occurred during update.
     */
    public void update() throws XFormsException
    {
        // get model item and its current properties
        ModelItem modelItem = UIElementStateUtil.getModelItem(this.owner);
        boolean[] properties = UIElementStateUtil.getModelItemProperties(modelItem);

        // update properties
        setProperties(properties);

        // update types
        String datatype = null;
        if(this.handleTypes)
        {
            datatype = modelItem != null ? UIElementStateUtil.getDatatype(modelItem, this.owner.getElement()) : null;
            String p3ptype = modelItem != null ? modelItem.getDeclarationView().getP3PType() : null;
            UIElementStateUtil.setStateAttribute(this.state, TYPE_ATTRIBUTE, datatype);
            UIElementStateUtil.setStateAttribute(this.state, P3PTYPE_ATTRIBUTE, p3ptype);
        }

        // update value
        Object value = null;
        if(this.handleValue)
        {
        	//attempt to store the subtree (xforms:copy)
        	if(modelItem != null && modelItem.getNode() instanceof Element)
        	{
        		value = storeSubtree(modelItem);
        		Element childElement = DOMUtil.getFirstChildElement((Element)modelItem.getNode());
        		if(childElement == null)
        		{
        			//otherwise fallback to a string value
            		value = modelItem != null ? modelItem.getValue() : null;
            		DOMUtil.setElementValue(this.state, (String)value);
        		}
        	}
        	else
        	{
        		//otherwise fallback to a string value
        		value = modelItem != null ? modelItem.getValue() : null;
        		DOMUtil.setElementValue(this.state, (String)value);
        	}
        }

        // dispatch xforms events
        UIElementStateUtil.dispatchXFormsEvents(this.owner, modelItem, properties);
        if(this.dispatchValueChange)
        {
            // dispatch property, value, and type change events because the owner's external value change caused this update
            UIElementStateUtil.dispatchChibaEvents(this.owner, this.currentProperties, this.currentValue, this.currentType, properties, value, datatype);
        }
        else
        {
            // don't dispatch value and type change events because the owner's external value change caused this update
            UIElementStateUtil.dispatchChibaEvents(this.owner, this.currentProperties, properties);

            // reset automatically to avoid incosistencies
            this.dispatchValueChange = true;
        }
        
        //store properties and value
        this.currentProperties = properties;
        this.currentValue = value;
    }

    /**
     * Disposes this element state.
     *
     * @throws XFormsException if an error occurred during disposal.
     */
    public void dispose() throws XFormsException {
        // remove any content and free resources
        this.state.getParentNode().removeChild(this.state);
        this.state = null;
        this.owner = null;
    }

    /**
     * Returns the current value.
     *
     * @return the current value.
     */
    public Object getValue() {
        return this.handleValue ? this.currentValue : null;
    }

    /**
     * Sets an arbitrary property.
     * <p/>
     * Only <code>dispatchValueChange</code> is reccognized.
     *
     * @param name the property name.
     * @param value the property value.
     */
    public void setProperty(String name, Object value) {
        if (("dispatchValueChange").equals(name)) {
            this.dispatchValueChange = Boolean.valueOf(String.valueOf(value)).booleanValue();
        }
    }

    private void setProperties(boolean[] properties) {
        // set properties
        UIElementStateUtil.setStateAttribute(this.state, VALID_PROPERTY, String.valueOf(properties[UIElementStateUtil.VALID]));
        UIElementStateUtil.setStateAttribute(this.state, READONLY_PROPERTY, String.valueOf(properties[UIElementStateUtil.READONLY]));
        UIElementStateUtil.setStateAttribute(this.state, REQUIRED_PROPERTY, String.valueOf(properties[UIElementStateUtil.REQUIRED]));
        UIElementStateUtil.setStateAttribute(this.state, ENABLED_PROPERTY, String.valueOf(properties[UIElementStateUtil.ENABLED]));
    }

    
}

// end of class
