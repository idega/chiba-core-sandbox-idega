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
package org.chiba.xml.xforms.xpath;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.jxpath.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chiba.xml.xforms.Container;
import org.chiba.xml.xforms.core.DeclarationView;
import org.chiba.xml.xforms.core.Instance;
import org.chiba.xml.xforms.core.ModelItem;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides chiba extension functions. These functions are implemented and
 * tested for use with JXPath.
 *
 * @author Ulrich Nicolas Liss&eacute;
 * @author Terence Jacyno
 * @version $Id: ChibaExtensionFunctions.java,v 1.1 2009/02/17 09:00:54 civilis Exp $
 */
public class ChibaExtensionFunctions {
    private static final Log LOGGER = LogFactory.getLog(ChibaExtensionFunctions.class);

    /**
     * Prevents outside instantiation
     */
    private ChibaExtensionFunctions() {
        // NOP
    }

    /**
     * Returns the value of the context property or the empty string if no such
     * property exists.
     *
     * @param expressionContext the expression context.
     * @param name              the name of the context property.
     * @return the value of the context property or the empty string if no such
     *         property exists.
     * @see #context(ExpressionContext,String,String)
     */
    public static Object context(ExpressionContext expressionContext, String name) {
        return ChibaExtensionFunctions.context(expressionContext, name, "");
    }

    /**
     * Returns the value of the context property or the default value if no such
     * property exists.
     * <p/>
     * It is possible to access nested properties with simple XPathes, e.g use
     * <code>chiba:context('foo/bar')</code> to access property <code>bar</code>
     * living in a map which in turn is stored as <code>foo</code> in the context.
     *
     * @param expressionContext the expression context.
     * @param name              the name of the context property.
     * @param value             the default value of the context property
     * @return the value of the context property or the default value if no such
     *         property exists.
     */
    public static Object context(ExpressionContext expressionContext, String name, String value) {
        Container container = ExtensionFunctionsHelper.getChibaContainer(expressionContext);
        JXPathContext xPathContext = JXPathContext.newContext(container.getProcessor().getContext());
        xPathContext.setLenient(true);
        Pointer pointer = xPathContext.getPointer(name);

        return pointer.getValue() != null ? (Object) pointer : (Object) value;
    }


    private static Map m_regexPatterns = new HashMap();

    /**
     * <code>Regexp</code> is a utility class providing the functionality
     * present within the EXSLT Regular Expressions definition (<a
     * href="http://www.exslt.org/regexp" target="_top">http://www.exslt.org/regexp</a>).
     * <br><br> This is a contribution from Terence Jacyno.
     * <p/>
     * Todo: Move to 'ExsltRegExpExtensionFunctions, rename to 'test', add 'replace' and 'match'
     */
    public static boolean match(String input, String regex, String flags) {

        String regexKey = ((flags == null) || (flags.indexOf('i') == -1))
                ? "s " + regex : "i " + regex;

        Pattern pattern = (Pattern) m_regexPatterns.get(regexKey);

        if (pattern == null) {
            pattern = (regexKey.charAt(0) == 'i')
                    ? Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
                    : Pattern.compile(regex);
            m_regexPatterns.put(regexKey, pattern);
        }

        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * custom extension function to get the size of a local file.
     *
     * @param expressionContext
     * @param nodeset           nodeset must contain a single node that has a filename or
     *                          path as value. The value will be resolved against the baseURI of the
     *                          processor to find the file.
     * @return the size of the file as String
     *         <p/>
     *         todo: revisit code structure - fileSize and fileDate functions
     *         only differ in one line of code
     */
    public static String fileSize(ExpressionContext expressionContext, List nodeset) {
        if ((nodeset == null) || (nodeset.size() == 0)) {
            return "Error: Nodeset does not exist";
        }
        JXPathContext rootContext = expressionContext.getJXPathContext();

        while (rootContext != null) {
            Object rootNode = rootContext.getContextBean();

            if (rootNode instanceof Instance) {
                //get the Context
                Instance instance = (Instance) rootNode;
                String baseUri = instance.getModel().getContainer().getProcessor().getBaseURI();
                String path;
                try {
                    path = new URI(baseUri).getPath();
                }
                catch (URISyntaxException e) {
                    return "Error: base URI not valid: " + baseUri;
                }

                File file = new File(path, (String) nodeset.get(0));
                if (!file.exists() || file.isDirectory()) {
                    LOGGER.info("File " + file.toString() + " does not exist or is directory");
                    return "";
                }

                return "" + file.length();
            }
            rootContext = rootContext.getParentContext();
        }
        return "";
    }

    /**
     * custom extension function to get the lastModified Date of a local file.
     *
     * @param expressionContext
     * @param nodeset           must contain a single node that has a filename or path as
     *                          value. The value will be resolved against the baseURI of the processor to
     *                          find the file.
     * @param format            a format pattern conformant with to
     *                          java.text.SimpleDateFormat. If an empty string is passed the format
     *                          defaults to "dd.MM.yyyy H:m:s".
     * @return the formatted lastModified Date of the file
     * @see java.text.SimpleDateFormat
     */
    public static String fileDate(ExpressionContext expressionContext, List nodeset, String format) {
        if ((nodeset == null) || (nodeset.size() == 0)) {
            return "Error: Nodeset does not exist";
        }
        JXPathContext rootContext = expressionContext.getJXPathContext();

        while (rootContext != null) {
            Object rootNode = rootContext.getContextBean();

            if (rootNode instanceof Instance) {
                //get the Context
                Instance instance = (Instance) rootNode;
                String baseUri = instance.getModel().getContainer().getProcessor().getBaseURI();
                String path;
                try {
                    path = new URI(baseUri).getPath();
                }
                catch (URISyntaxException e) {
                    return "Error: base URI not valid: " + baseUri;
                }

                File file = new File(path, (String) nodeset.get(0));
                if (!file.exists() || file.isDirectory()) {
                    LOGGER.info("File " + file.toString() + " does not exist or is directory");
                    return "";
                }

                return formatDateString(file, format);
            }
            rootContext = rootContext.getParentContext();
        }
        return "Error: Calculation failed";
    }

    /**
     * custom extension function to get the content length of an uploaded file
     *
     * @param expressionContext
     * @param nodeset           must contain a single node that is the uploaded file
     * @return the length of the files content in bytes or -1 if we cannot get the content length
     */
    public static long uploadContentLength(ExpressionContext expressionContext, List nodeset) {
        if (nodeset != null) {
            if (nodeset.size() == 1) {
                JXPathContext rootContext = expressionContext.getJXPathContext();
                String xpath = rootContext.getContextPointer().asPath();
                String uploadDataType = null;

                //get the data type of the upload
                while (rootContext != null) {
                    Object rootNode = rootContext.getContextBean();

                    if (rootNode instanceof Instance) {
                        Instance instance = (Instance) rootNode;
                        ModelItem uploadItem = instance.getModelItem(xpath);
                        DeclarationView uploadItemDeclaration = uploadItem.getDeclarationView();

                        uploadDataType = uploadItemDeclaration.getDatatype();
                    }
                    rootContext = rootContext.getParentContext();
                }

                //get the encoded content of the upload
                String encodedUploadContent = (String) nodeset.get(0);

                //decode and calculate the length of the uploaded content
                if (encodedUploadContent != null && uploadDataType != null) {
                    byte[] decodedUploadContent = null;

                    if (uploadDataType.equals("base64Binary")) {
                        //decode base64Binary
                        decodedUploadContent = Base64.decodeBase64(encodedUploadContent.getBytes());
                    } else if (uploadDataType.equals("hexBinary")) {
                        //decode hexBinary
                        try {
                            decodedUploadContent = Hex.decodeHex(encodedUploadContent.toCharArray());
                        }
                        catch (DecoderException e) {
                            return -1;
                        }
                    } else if (uploadDataType.equals("anyURI")) {
                        //no decoding
                        decodedUploadContent = encodedUploadContent.getBytes();
                    }

                    //get the length of the decoded content
                    if (decodedUploadContent != null) {
                        return decodedUploadContent.length;
                    }
                }
            }
        }

        //-1 indicates an error
        return -1;
    }


    private static String formatDateString(File file, String format) {
        long modified = file.lastModified();
        Calendar calendar = new GregorianCalendar(Locale.getDefault());
        calendar.setTimeInMillis(modified);
        SimpleDateFormat simple = null;
        String result;
        if (format.equals("")) {
            //default format
            simple = new SimpleDateFormat("dd.MM.yyyy H:m:s");
        } else {
            //custom format
            try {
                simple = new SimpleDateFormat(format);
            }
            catch (IllegalArgumentException e) {
//                result = "Error: illegal Date format string";
                //todo: do something better
            }
        }
        result = simple.format(calendar.getTime());
        return result;
    }
}


// end of class


