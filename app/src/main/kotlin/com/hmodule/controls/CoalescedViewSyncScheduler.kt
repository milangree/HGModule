package com.hmodule.controls

import android.view.View

/** Coalesces bursts of view callbacks into one traversal per event-loop turn. */
class CoalescedViewSyncScheduler(
    private val post: (Runnable) -> Unit,
    private val sync: (View) -> Unit,
) {
    private val pending = linkedSetOf<View>()
    private var scheduled = false

    fun request(view: View) {
        if (pending.any { it === view || isInside(view, it) }) return
        pending.removeAll { isInside(it, view) }
        pending.add(view)
        if (scheduled) return
        scheduled = true
        post(Runnable {
            val batch = pending.toList()
            pending.clear()
            scheduled = false
            batch.forEach(sync)
        })
    }

    fun clear() {
        pending.clear()
        scheduled = false
    }

    private fun isInside(child: View, ancestor: View): Boolean {
        var node: View? = child
        repeat(40) {
            if (node === ancestor) return true
            node = node?.parent as? View
        }
        return false
    }
}
