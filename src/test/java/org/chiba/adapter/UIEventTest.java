package org.chiba.adapter;

/**
 * Created by IntelliJ IDEA.
 * User: joernturner
 * Date: Mar 30, 2005
 * Time: 12:39:52 AM
 * To change this template use File | Settings | File Templates.
 */

import junit.framework.TestCase;

public class UIEventTest extends TestCase {
    ChibaEvent uiEvent;

    public void testInitEvent() throws Exception {
        ChibaEvent uiEvent = new DefaultChibaEventImpl();
        //uiEvent.initEvent(DefaultChibaEventImpl.VALUE, null);
        //assertEquals(DefaultChibaEventImpl.VALUE,uiEvent.getEventName());
        //assertEquals("foo",uiEvent.getId());
    }
}