package org.chiba.xml.xforms.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.xml.events.XFormsEventNames;
import org.chiba.xml.xforms.core.Instance;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xpath.XPathUtil;
import org.w3c.dom.Element;

/**
 * Implements the action as defined in <code>9.3.5 The insert Element</code>.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: InsertAction11.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class InsertAction11 extends InsertAction {
    protected static Log LOGGER = LogFactory.getLog(InsertAction11.class);

    /**
     * Creates an insert action implementation.
     *
     * @param element the element.
     * @param model   the context model.
     */
    public InsertAction11(Element element, Model model) {
        super(element, model);
    }

    // lifecycle methods

    /**
     * Performs element init.
     */
    public void init() throws XFormsException {
        super.init();
    }


    /**
     * Performs the <code>insert</code> action.
     *
     * @throws org.chiba.xml.xforms.exception.XFormsException
     *          if an error occurred during <code>insert</code>
     *          processing.
     */
    public void perform() throws XFormsException {
        // get instance and nodeset information
        Instance instance = this.model.getInstance(getInstanceId());
        String pathExpression = getLocationPath();
        int contextSize = instance.countNodeset(pathExpression);
        if (contextSize == 0) {
            getLogger().warn(this + " perform: nodeset '" + pathExpression + "' is empty");
            return;
        }

        long position = computeInsertPosition(contextSize, instance, pathExpression);

        // insert specified node and dispatch notification event
        String origin = originAttribute;
        if (origin == null)
            origin = new StringBuffer(pathExpression).append('[').append(contextSize).append(']').toString();
        else {
            if (!XPathUtil.isAbsolutePath(origin)) {
                //resolve origin against eval context
                String[] steps = {pathExpression, origin};
                String fullPath = XPathUtil.joinPathExpr(steps);
                origin = instance.getPointer(fullPath).asPath();
            }
        }
        String before = new StringBuffer(pathExpression).append('[').append(position).append(']').toString();
        instance.insertNode(origin, before);
        this.container.dispatch(instance.getTarget(), XFormsEventNames.INSERT, before);

        // update behaviour
        doRebuild(true);
        doRecalculate(true);
        doRevalidate(true);
        doRefresh(true);
    }
}


