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
package org.chiba.xml.xpath;

import java.util.ArrayList;

/**
 * Provides XPath parsing utility functions tailored to Chiba's needs.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @version $Id: XPathUtil.java,v 1.1 2009/02/17 09:00:53 civilis Exp $
 */
public class XPathUtil {

    /**
     * Start of an instance() function expression.
     */
    public static final String INSTANCE_FUNCTION = "instance(";

    /**
     * XPath for outermost context.
     */
    public static final String OUTERMOST_CONTEXT = "/*[1]";

    /**
     * Extracts the first part of the specified path expression.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>"a"</code> returns <code>"a"</code>.
     * <li><code>"/a"</code> returns <code>"a"</code>.
     * <li><code>"/a/b"</code> returns <code>"a"</code>.
     * <li><code>"instance('i')/a"</code> returns <code>"instance('i')"</code>.
     * </ul>
     *
     * @param path the path expression.
     * @return the first part of the specified path expression.
     */
    public static String getFirstPart(String path) {
        if (path == null) {
            return null;
        }
        if (path.length() == 0) {
            return path;
        }

        String[] expressions = XPathUtil.splitPathExpr(path, 2);
        return path.charAt(0) == '/' ? expressions[1] : expressions[0];
    }

    /**
     * Splits the specified path expression into a nodeset part and all
     * predicates of the last step of the path expression.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>"/a/b"</code> returns <code>{"/a/b"}</code>.
     * <li><code>"/a/b[p]"</code> returns <code>{"/a/b", "p"}</code>.
     * <li><code>"/a/b[p][q]"</code> returns <code>{"/a/b", "p", "q"}</code>.
     * <li><code>"/a[1]/b[2]/c[3]"</code> returns <code>{"/a[1]/b[2]/c", "3"}</code>.
     * </ul>
     *
     * @param path the path expression.
     * @return the path expression splitted into nodeset and predicate parts.
     */
    public static String[] getNodesetAndPredicates(String path) {
        if (path == null) {
            return null;
        }
        if (path.length() == 0) {
            return new String[]{path};
        }

        String[] expressions = XPathUtil.splitPathExpr(path);
        String[] parts = XPathUtil.splitStep(expressions[expressions.length - 1]);

        expressions[expressions.length - 1] = parts[0];
        parts[0] = XPathUtil.joinPathExpr(expressions);

        return parts;
    }

    /**
     * Checks wether the specified path expression starts with the
     * <code>instance</code> function.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>"a"</code> returns <code>false</code>.
     * <li><code>"a[.=instance('i')/b]"</code> returns <code>false</code>.
     * <li><code>"instance('i')/a"</code> returns <code>true</code>.
     * <li><code>"instance(/b/c)/a"</code> returns <code>true</code>.
     * </ul>
     *
     * @param path the path expression.
     * @return <code>true</code> if the specified path expression starts with
     * the <code>instance</code> function, <code>false</code> otherwise.
     */
    public static boolean hasInstanceFunction(String path) {
        return path != null && path.startsWith(INSTANCE_FUNCTION);
    }

    /**
     * Extracts the parameter expression of the <code>instance</code> function
     * in case the the specified path expression starts with the
     * <code>instance</code> function.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>"a"</code> returns <code>null</code>.
     * <li><code>"a[.=instance('i')/b]"</code> returns <code>null</code>.
     * <li><code>"instance('i')/a"</code> returns <code>"'i'"</code>.
     * <li><code>"instance(/b/c)/a"</code> returns <code>"/b/c"</code>.
     * </ul>
     *
     * @param path the path expression.
     * @return the parameter expression of the <code>instance</code> function.
     */
    public static String getInstanceParameter(String path) {
        if (XPathUtil.hasInstanceFunction(path)) {
            String instance = XPathUtil.splitPathExpr(path, 1)[0];
            return instance.substring(INSTANCE_FUNCTION.length(), instance.lastIndexOf(')'));
        }

        return null;
    }

    /**
     * Checks wether the specified path expression is an absolute path.
     *
     * @param path the path expression.
     * @return <code>true</code> if specified path expression is an absolute
     * path, otherwise <code>false</code>.
     */
    public static boolean isAbsolutePath(String path) {
        return path != null && (path.startsWith("/") || path.startsWith(INSTANCE_FUNCTION));
    }

    /**
     * Checks wether the specified path expression is a dot reference.
     *
     * @param path the path expression.
     * @return <code>true</code> if specified path expression is a dot
     * reference, otherwise <code>false</code>.
     */
    public static boolean isDotReference(String path) {
        return path != null && path.equals(".");
    }

    /**
     * Checks wether the specified path expression is a self reference and
     * strips the self referencing expression portion.
     *
     * @param path the path expression.
     * @return the stripped path expression if the specified path path is
     * a self reference, otherwise the unmodified path expression.
     */
    public static String stripSelfReference(String path) {
        if (path != null && path.startsWith("./")) {
            // strip self reference
            return path.substring(2);
        }

        // leave unmodified
        return path;
    }

    // generic helper

    /**
     * Splits the specified path expression into step-wise parts. Leading
     * slashes result in empty parts while trailing slashes are igonred.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>"a/b/c"</code> returns <code>{"a", "b", "c"}</code>.
     * <li><code>"/a/b/c"</code> returns <code>{"", "a", "b", "c"}</code>.
     * <li><code>"/a/b/c/"</code> returns <code>{"", "a", "b", "c"}</code>.
     * <li><code>"//a/b/c"</code> returns <code>{"", "", "a", "b", "c"}</code>.
     * <li><code>"//a/b/c/"</code> returns <code>{"", "", "a", "b", "c"}</code>.
     * <li><code>"a/n:b/c[d/e]"</code> returns <code>{"a", "n:b", "c[d/e]"}</code>.
     * </ul>
     *
     * @param path the path expression.
     * @return the specified path expression in step-wise parts.
     */
    public static String[] splitPathExpr(String path) {
        return XPathUtil.splitPathExpr(path, 0);
    }

    /**
     * Splits the specified path expression into step-wise parts. Leading
     * slashes result in empty parts while trailing slashes are igonred.
     * <p/>
     * Additionally the desired count of parts to be returned may be specified.
     * Any number less than 1 or greater than the actual number of parts is
     * meaningless and returns all found parts.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>"a/b/c", 0</code> returns <code>{"a", "b", "c"}</code>.
     * <li><code>"a/b/c", 2</code> returns <code>{"a", "b"}</code>.
     * <li><code>"/a/b/c", 4</code> returns <code>{"a", "b", "c"}</code>.
     * </ul>
     *
     * @param path the path expression.
     * @param count the desired number of parts.
     * @return the specified path expression in step-wise parts.
     */
    public static String[] splitPathExpr(String path, int count) {
        if (path == null) {
            return null;
        }

        int offset = 0;
        int braces = 0;
        ArrayList list = new ArrayList();
        for (int index = 0; index < path.length() && (count < 1 || count > list.size()); index++) {
            switch (path.charAt(index)) {
                case '[':
                    // fall through
                case '(':
                    braces++;
                    break;
                case ']':
                    // fall through
                case ')':
                    braces--;
                    break;
                case '/':
                    if (braces == 0) {
                        list.add(path.substring(offset, index));
                        offset = index + 1;
                    }
                    break;
            }
        }

        if (offset < path.length() && (count < 1 || count > list.size())) {
            list.add(path.substring(offset, path.length()));
        }

        return (String[]) list.toArray(new String[0]);
    }

    /**
     * Joins step-wise parts to a path expression. The parts are simply
     * concatenated with in-between slashes.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>{"a", "b", "c"}</code> returns <code>"a/b/c"</code>.
     * <li><code>{"", "a", "b", "c"}</code> returns <code>"/a/b/c"</code>.
     * <li><code>{"", "a", "b", "c", ""}</code> returns <code>"/a/b/c/"</code>.
     * <li><code>{"", "", "a", "b", "c"}</code> returns <code>"//a/b/c"</code>.
     * <li><code>{"", "", "a", "b", "c", ""}</code> returns <code>"//a/b/c/"</code>.
     * <li><code>{"a", "n:b", "c[d/e]"}</code> returns <code>"a/n:b/c[d/e]"</code>.
     * </ul>
     *
     * @param parts the step-wise parts.
     * @return the path expression.
     */
    public static String joinPathExpr(String[] parts) {
        return XPathUtil.joinPathExpr(parts, 0, parts != null ? parts.length : -1);
    }

    /**
     * Joins step-wise parts to a path expression. The parts are simply
     * concatenated with in-between slashes.
     * <p/>
     * Additionally the desired start and end indices may be specified.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>{"a", "b", "c"}, 0, 3</code> returns <code>"a/b/c"</code>.
     * <li><code>{"a", "b", "c"}, 1, 2</code> returns <code>"b"</code>.
     * </ul>
     *
     * @param parts the step-wise parts.
     * @param start the start index.
     * @param end the end index.
     * @return the path expression.
     */
    public static String joinPathExpr(String[] parts, int start, int end) {
        if (parts == null || start > end) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        for (int index = start; index < end; index++) {
            if (index > start) {
                buffer.append('/');
            }
            buffer.append(parts[index]);
        }

        return buffer.toString();
    }

    /**
     * Splits the specified step into parts.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>"a"</code> returns <code>{"a"}</code>.
     * <li><code>"a[p]"</code> returns <code>{"a", "p"}</code>.
     * <li><code>"a[p][q]"</code> returns <code>{"a", "p", "q"}</code>.
     * <li><code>"child::a[p][q]"</code> returns <code>{"child::a", "p", "q"}</code>.
     * <li><code>"a[b[p]][c/d[q]]"</code> returns <code>{"a", "b[p]", "c/d[q]"}</code>.
     * </ul>
     *
     * @param step the step.
     * @return the specified step in parts.
     */
    public static String[] splitStep(String step) {
        if (step == null) {
            return null;
        }

        int offset = 0;
        int braces = 0;
        ArrayList list = new ArrayList();
        for (int index = 0; index < step.length(); index++) {
            switch (step.charAt(index)) {
                case '[':
                    if (braces == 0) {
                        if (offset == 0) {
                            list.add(step.substring(offset, index));
                        }
                        offset = index + 1;
                    }
                    braces++;
                    break;
                case ']':
                    if (braces == 1) {
                        list.add(step.substring(offset, index));
                        offset = index + 1;
                    }
                    braces--;
                    break;
            }
        }

        if (offset < step.length()) {
            list.add(step.substring(offset, step.length()));
        }

        return (String[]) list.toArray(new String[0]);
    }

    /**
     * Joins parts to a complete step. The first part will be treated as a node
     * test, an axis or a combination of both, and all following parts will be
     * treated as predictes.
     * <p/>
     * Examples:
     * <ul>
     * <li><code>{"a"}</code> returns <code>"a"</code>.
     * <li><code>{"a", "p"}</code> returns <code>"a[p]"</code>.
     * <li><code>{"a", "p", "q"}</code> returns <code>"a[p][q]"</code>.
     * <li><code>{"child::a", "p", "q"}</code> returns <code>"child::a[p][q]"</code>.
     * <li><code>{"a", "b[p]", "c/d[q]"}</code> returns <code>"a[b[p]][c/d[q]]"</code>.
     * </ul>
     *
     * @param parts the parts.
     * @return the step.
     */
    public static String joinStep(String[] parts) {
        if (parts == null) {
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        for (int index = 0; index < parts.length; index++) {
            if (index > 0) {
                buffer.append('[');
            }
            buffer.append(parts[index]);
            if (index > 0) {
                buffer.append(']');
            }
        }

        return buffer.toString();
    }

}
