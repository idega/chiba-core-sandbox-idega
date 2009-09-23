package org.chiba.xml.xforms.exception;

import org.w3c.dom.events.EventTarget;

/**
 * Signals an <code>xforms-version-exception</code> error indication.
 *
 * @author Joern Turner
 */
public class XFormsVersionException extends XFormsErrorIndication {

    /**
     * Creates a new <code>xforms-binding-exception</code> error indication.
     *
     * @param message the error message.
     * @param target  the event target.
     * @param info    the context information.
     */
    public XFormsVersionException(String message, EventTarget target, Object info) {
        this(message, null, target, info);
    }

    /**
     * Creates a new <code>xforms-binding-exception</code> error indication.
     *
     * @param message the error message.
     * @param cause   the root cause.
     * @param target  the event target.
     * @param info    the context information.
     */
    public XFormsVersionException(String message, Exception cause, EventTarget target, Object info) {
        super(message, cause, target, info);
        this.id = "xforms-version-exception";
    }

    /**
     * Specifies wether this error indication is fatal or non-fatal.
     *
     * @return <code>true</code>.
     */
    public final boolean isFatal() {
        return true;
    }
}
