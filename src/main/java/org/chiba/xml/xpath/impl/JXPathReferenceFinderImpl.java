// Copyright 2006 Chibacon
/*
 *    Artistic License
 *
 *    Preamble
 *
 *    The intent of this document is to state the conditions under which a
 *    Package may be copied, such that the Copyright Holder maintains some
 *    semblance of artistic control over the development of the package,
 *    while giving the users of the package the right to use and distribute
 *    the Package in a more-or-less customary fashion, plus the right to make
 *    reasonable modifications.
 *
 *    Definitions:
 *
 *    "Package" refers to the collection of files distributed by the
 *    Copyright Holder, and derivatives of that collection of files created
 *    through textual modification.
 *
 *    "Standard Version" refers to such a Package if it has not been
 *    modified, or has been modified in accordance with the wishes of the
 *    Copyright Holder.
 *
 *    "Copyright Holder" is whoever is named in the copyright or copyrights
 *    for the package.
 *
 *    "You" is you, if you're thinking about copying or distributing this Package.
 *
 *    "Reasonable copying fee" is whatever you can justify on the basis of
 *    media cost, duplication charges, time of people involved, and so
 *    on. (You will not be required to justify it to the Copyright Holder,
 *    but only to the computing community at large as a market that must bear
 *    the fee.)
 *
 *    "Freely Available" means that no fee is charged for the item itself,
 *    though there may be fees involved in handling the item. It also means
 *    that recipients of the item may redistribute it under the same
 *    conditions they received it.
 *
 *    1. You may make and give away verbatim copies of the source form of the
 *    Standard Version of this Package without restriction, provided that you
 *    duplicate all of the original copyright notices and associated
 *    disclaimers.
 *
 *    2. You may apply bug fixes, portability fixes and other modifications
 *    derived from the Public Domain or from the Copyright Holder. A Package
 *    modified in such a way shall still be considered the Standard Version.
 *
 *    3. You may otherwise modify your copy of this Package in any way,
 *    provided that you insert a prominent notice in each changed file
 *    stating how and when you changed that file, and provided that you do at
 *    least ONE of the following:
 *
 *        a) place your modifications in the Public Domain or otherwise make
 *        them Freely Available, such as by posting said modifications to
 *        Usenet or an equivalent medium, or placing the modifications on a
 *        major archive site such as ftp.uu.net, or by allowing the Copyright
 *        Holder to include your modifications in the Standard Version of the
 *        Package.
 *
 *        b) use the modified Package only within your corporation or
 *        organization.
 *
 *        c) rename any non-standard executables so the names do not conflict
 *        with standard executables, which must also be provided, and provide
 *        a separate manual page for each non-standard executable that
 *        clearly documents how it differs from the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    4. You may distribute the programs of this Package in object code or
 *    executable form, provided that you do at least ONE of the following:
 *
 *        a) distribute a Standard Version of the executables and library
 *        files, together with instructions (in the manual page or
 *        equivalent) on where to get the Standard Version.
 *
 *        b) accompany the distribution with the machine-readable source of
 *        the Package with your modifications.
 *
 *        c) accompany any non-standard executables with their corresponding
 *        Standard Version executables, giving the non-standard executables
 *        non-standard names, and clearly documenting the differences in
 *        manual pages (or equivalent), together with instructions on where
 *        to get the Standard Version.
 *
 *        d) make other distribution arrangements with the Copyright Holder.
 *
 *    5. You may charge a reasonable copying fee for any distribution of this
 *    Package. You may charge any fee you choose for support of this
 *    Package. You may not charge a fee for this Package itself.  However,
 *    you may distribute this Package in aggregate with other (possibly
 *    commercial) programs as part of a larger (possibly commercial) software
 *    distribution provided that you do not advertise this Package as a
 *    product of your own.
 *
 *    6. The scripts and library files supplied as input to or produced as
 *    output from the programs of this Package do not automatically fall
 *    under the copyright of this Package, but belong to whomever generated
 *    them, and may be sold commercially, and may be aggregated with this
 *    Package.
 *
 *    7. C or perl subroutines supplied by you and linked into this Package
 *    shall not be considered part of this Package.
 *
 *    8. The name of the Copyright Holder may not be used to endorse or
 *    promote products derived from this software without specific prior
 *    written permission.
 *
 *    9. THIS PACKAGE IS PROVIDED "AS IS" AND WITHOUT ANY EXPRESS OR IMPLIED
 *    WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 *    MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package org.chiba.xml.xpath.impl;

import org.apache.commons.jxpath.ri.Compiler;
import org.apache.commons.jxpath.ri.Parser;
import org.apache.commons.jxpath.ri.compiler.*;
import org.chiba.xml.xforms.exception.XFormsException;
import org.chiba.xml.xpath.XPathReferenceFinder;

import java.util.*;

/**
 * A JXPath-based reference finder implementation.
 *
 * @author Adrian Baker
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: JXPathReferenceFinderImpl.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class JXPathReferenceFinderImpl implements XPathReferenceFinder {

    private Compiler compiler;

    /**
     * Creates a new JXPath-based reference finder.
     */
    public JXPathReferenceFinderImpl() {
    }

    /**
     * Returns the set of all references to other nodes contained in the given
     * XPath expression.
     *
     * @param xpath the XPath expression.
     * @return the set of all references to other nodes.
     * @throws XFormsException if a reference detection error occurred.
     */
    public Set getReferences(String xpath) throws XFormsException {
        try {
            if (this.compiler == null) {
                this.compiler = new TreeCompiler();
            }

            Object expression = Parser.parseExpression(xpath, this.compiler);
            Map references = new HashMap();
            addExpressionReferences(references, null, (Expression) expression);

            return references.keySet();
        }
        catch (Exception e) {
            throw new XFormsException(e);
        }
    }

    private void addExpressionReferences(Map references, Expression context, Expression expression) {
        // handle location pathes like "../relative/path" or "//absolute/path"
        if (expression instanceof LocationPath) {
            // put location path in context and add as reference
            LocationPath locationPath = (LocationPath) expression;
            Path path = addLocationPath(context, (LocationPath) expression);
            references.put(path.toString(), path);

            // pass empty location path when there is no context
            Expression contextExpression = (Expression) (context == null
                                                ? this.compiler.locationPath(locationPath.isAbsolute(), null)
                                                : context);
            // add references of step predicates
            addStepReferences(references, contextExpression, locationPath.getSteps());
            return;
        }
        // handle expression pathes like "instance('id')/path" or "ext:function()[predicate]/path"
        if (expression instanceof ExpressionPath) {
            // add expression path as reference
            ExpressionPath expressionPath = (ExpressionPath) expression;
            references.put(expressionPath.toString(), expressionPath);

            // add references in expression predicates
            Expression[] predicates = expressionPath.getPredicates();
            for (int i = 0; predicates != null && i < predicates.length; i++) {
                addExpressionReferences(references, expressionPath.getExpression(), predicates[i]);
            }

            // add references in step predicates
            addStepReferences(references, expressionPath.getExpression(), expressionPath.getSteps());
            return;
        }
        // handle standalone instance() function
        if (expression instanceof ExtensionFunction) {
            ExtensionFunction function = (ExtensionFunction) expression;
            // todo: how to handle all functions returning a nodeset ?
            if (function.getFunctionName().getName().equals("instance")) {
                references.put(expression.toString(), expression);
                return;
            }
        }
        // handle all other operations like "black + white" or "count(../apples and /oranges)"
        if (expression instanceof Operation) {
            Operation operation = (Operation) expression;
            Expression[] arguments = operation.getArguments();

            // add references in function arguments
            for (int i = 0; arguments != null && i < arguments.length; i++) {
                addExpressionReferences(references, context, arguments[i]);
            }
        }
        // don't handle constants
        // if (expression instanceof Constant) {
        // }
        // don't handle variable references (maybe needed someday)
        // if (expression instanceof VariableReference) {
        //  }
    }

    private void addStepReferences(Map references, Expression context, Step[] steps) {
        Path path;
        Step step;
        Step tmp;
        Expression[] predicates;

        for (int i = 0; steps != null && i < steps.length; i++) {
            step = steps[i];
            predicates = step.getPredicates();

            if (predicates != null && predicates.length > 0) {
                // create path without current predicates as context
                tmp = step;
                steps[i] = (Step) this.compiler.step(step.getAxis(), step.getNodeTest(), null);
                path = addSteps(context, steps, i + 1);
                steps[i] = tmp;

                // add references in step predicates
                for (int j = 0; j < predicates.length; j++) {
                    addExpressionReferences(references, path, predicates[j]);
                }
            }
        }
    }

    private Path addLocationPath(Expression context, LocationPath path) {
        if (context == null || path.isAbsolute()) {
            return path;
        }

        Step[] steps = path.getSteps();
        return addSteps(context, steps, steps.length);
    }

    private Path addSteps(Expression context, Step[] steps, int length) {
        List stepList = new ArrayList();
        if (context instanceof Path) {
            Path path = (Path) context;
            if (path.getSteps() != null) {
                stepList.addAll(Arrays.asList(((Path) context).getSteps()));
            }
        }
        for (int i = 0; i < length; i++) {
            stepList.add(steps[i]);
        }
        Step[] stepArray = (Step[]) stepList.toArray(new Step[stepList.size()]);

        if (context instanceof LocationPath) {
            LocationPath locationPath = (LocationPath) context;
            return (Path) this.compiler.locationPath(locationPath.isAbsolute(), stepArray);
        }
        if (context instanceof ExpressionPath) {
            ExpressionPath expressionPath = (ExpressionPath) context;
            return (Path) this.compiler.expressionPath(expressionPath.getExpression(), expressionPath.getPredicates(), stepArray);
        }
        return (Path) this.compiler.expressionPath(context, null, stepArray);

    }

}
