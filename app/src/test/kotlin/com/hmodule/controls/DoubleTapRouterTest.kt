package com.hmodule.controls

import org.junit.Assert.assertEquals
import org.junit.Test

class DoubleTapRouterTest {
    @Test fun routesLeftAndRightHalvesUsingTheActualWindowBounds() {
        assertEquals(DoubleTapAction.CLEAN_SCREEN, DoubleTapRouter.action(249f, 100, 300))
        assertEquals(DoubleTapAction.COMMENT, DoubleTapRouter.action(250f, 100, 300))
        assertEquals(DoubleTapAction.COMMENT, DoubleTapRouter.action(10f, 0, 0))
    }
}
