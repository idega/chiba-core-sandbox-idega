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
package org.chiba.xml.xforms.constraints;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.Pointer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.dom.NodeImpl;
import org.chiba.xml.xforms.core.Bind;
import org.chiba.xml.xforms.core.Instance;
import org.chiba.xml.xforms.core.Model;
import org.chiba.xml.xforms.core.ModelItem;
import org.chiba.xml.xforms.exception.XFormsException;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * Implementation of XForms recalculation.
 *
 * Todo: The Dependency Engine desperately needs refactoring, then tuning.
 *
 * @author This code is based on the ideas of Mikko Honkala from the X-Smiles project.
 *         Although it has been heavily refactored and rewritten to meet our needs.
 * @author unl, joernt
 * @version $Id: MainDependencyGraph.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class MainDependencyGraph extends DependencyGraph {
    /**
     * Logger
     */
    private static Log LOGGER = LogFactory.getLog(MainDependencyGraph.class);

    public MainDependencyGraph() {
        super();
    }

    /**
     * returns all Vertices
     *
     * @return all Vertices
     */
    public Vector getVertices() {
        return this.vertices;
    }

    /**
     * Adds a single bind's ref node to the Main Graph
     * called by MainDependencyGraph.buildBindGraph()
     */
    private void addReferredNodesToGraph(JXPathContext relativeContext, NodeImpl instanceNode,
                                         String expression, short property, Set references) throws XFormsException {
        //creates a new vertex for this Node or return it, in case it already existed
        Vertex vertex = this.addVertex(relativeContext, instanceNode, expression, property);
        boolean hadVertex = vertex.wasAlreadyInGraph;
        vertex.wasAlreadyInGraph = false;

        // Analyze the Xpath Expression 'calculate'. Read nodeset RefNS
        // (the nodes this XPAth references)
        String xpath = vertex.getXPathExpression();

        if ((xpath == null) || (xpath.length() == 0)) {
            // bind without xpath, remove vertex
            if (!hadVertex) {
                this.removeVertex(vertex);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("addReferredNodesToGraph: ignoring vertex " + vertex + " without xpath");
            }

            return;
        }

        //Analyse xpath-expression to determine the Referenced dataitems
        Vector refns = this.getXPathRefNodes(relativeContext, xpath, references);

        if (refns == null) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("addReferredNodesToGraph: ignoring vertex " + vertex + " without references");
            }

            return;
        }

        if (refns.size() == 0) {
            // this is a calculated value, that is not depending on anything, let's calculate it now
            vertex.compute();
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("addReferredNodesToGraph: processing vertex " + vertex + " with " + refns.size() + " references");
        }

        Enumeration enumeration = refns.elements();

        while (enumeration.hasMoreElements()) {
            NodeImpl referencedNode = (NodeImpl) enumeration.nextElement();

            // pre-build vertex
            Vertex refVertex = this.addVertex(null, referencedNode, null, Vertex.CALCULATE_VERTEX);
            this.addEdge(refVertex, vertex);
        }
    }

    /**
     * builds the dependency graph for a single Bind. Processes all instancenodes that are associated with
     * the bind and creates one Vertex-object for every Modelitem Property found. That means that if there are
     * two instancenodes in the evaluated nodeset, two Vertices for every property (readonly, required, relevant,
     * constraint, calculate) will be created.
     * <p/>
     * Note: only dynamic Modelitem Properties will be processed.
     */
    public void buildBindGraph(Bind bind, Model model) throws XFormsException {
        Instance instance = model.getInstance(bind.getInstanceId());
        String locationPath = bind.getLocationPath();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("buildBindGraph: building " + bind);
        }

        JXPathContext instanceContext = instance.getInstanceContext();
        Pointer instancePointer;
        JXPathContext relativeContext;
        ModelItem modelItem;
        NodeImpl node;
        String property;

        Iterator iterator = instance.getPointerIterator(locationPath);
        while (iterator.hasNext()) {
            instancePointer = (Pointer) iterator.next();
            relativeContext = instanceContext.getRelativeContext(instancePointer);
            relativeContext.setFunctions(instanceContext.getFunctions());

            String s = instancePointer.asPath();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("buildBindGraph: processing " + s);
            }
            modelItem = instance.getModelItem(s);
            node = (NodeImpl) modelItem.getNode();

            property = bind.getCalculate();
            if (property != null) {
                modelItem.getDeclarationView().setCalculate(property);
                this.addReferredNodesToGraph(relativeContext, node, property, Vertex.CALCULATE_VERTEX, bind.getCalculateReferences());
            }

            property = bind.getRelevant();
            if (property != null) {
                modelItem.getDeclarationView().setRelevant(property);
                this.addReferredNodesToGraph(relativeContext, node, property, Vertex.RELEVANT_VERTEX, bind.getRelevantReferences());
            }

            property = bind.getReadonly();
            if (property != null) {
                modelItem.getDeclarationView().setReadonly(property);
                this.addReferredNodesToGraph(relativeContext, node, property, Vertex.READONLY_VERTEX, bind.getReadonlyReferences());
            }

            property = bind.getRequired();
            if (property != null) {
                modelItem.getDeclarationView().setRequired(property);
                this.addReferredNodesToGraph(relativeContext, node, property, Vertex.REQUIRED_VERTEX, bind.getRequiredReferences());
            }

            property = bind.getConstraint();
            if (property != null) {
                modelItem.getDeclarationView().setConstraint(property);
                this.addReferredNodesToGraph(relativeContext, node, property, Vertex.CONSTRAINT_VERTEX, bind.getConstraintReferences());
            }

            property = bind.getDatatype();
            if (property != null) {
                modelItem.getDeclarationView().setDatatype(property);
            }

            property = bind.getP3PType();
            if (property != null) {
                modelItem.getDeclarationView().setP3PType(property);
            }
        }
    }

    /**
     * Extends DependencyGraph.recalculate(). Restores the vertices vector after recalc
     */
/*
    public void recalculate() {
        this.printGraph();

        Vector tempvertices = (Vector) this.vertices.clone();
        super.recalculate();
        this.vertices = tempvertices;

        //		calculated=true;
    }
*/
}
