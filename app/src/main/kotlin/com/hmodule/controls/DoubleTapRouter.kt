package com.hmodule.controls

enum class DoubleTapAction { CLEAN_SCREEN, COMMENT }

object DoubleTapRouter {
    fun action(rawX: Float, windowLeft: Int, windowWidth: Int): DoubleTapAction {
        if (windowWidth <= 0) return DoubleTapAction.COMMENT
        val center = windowLeft + windowWidth / 2f
        return if (rawX < center) DoubleTapAction.CLEAN_SCREEN else DoubleTapAction.COMMENT
    }
}
