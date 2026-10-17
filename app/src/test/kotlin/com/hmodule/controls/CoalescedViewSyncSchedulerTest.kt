package com.hmodule.controls

import android.app.Activity
import android.view.View
import android.widget.FrameLayout
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CoalescedViewSyncSchedulerTest {
    @Test fun burstCallbacksCollapseToTheHighestPendingRoots() {
        val activity = Robolectric.buildActivity(Activity::class.java).setup().get()
        val root = FrameLayout(activity)
        val branch = FrameLayout(activity)
        val leaf = View(activity)
        root.addView(branch); branch.addView(leaf)
        val posted = mutableListOf<Runnable>()
        val synced = mutableListOf<View>()
        val scheduler = CoalescedViewSyncScheduler(posted::add, synced::add)

        repeat(500) { scheduler.request(leaf) }
        scheduler.request(branch)
        scheduler.request(root)

        assertEquals(1, posted.size)
        posted.single().run()
        assertEquals(listOf(root), synced)
    }

    @Test fun clearAllowsARequestFromTheNextConfigurationEpoch() {
        val activity = Robolectric.buildActivity(Activity::class.java).setup().get()
        val root = FrameLayout(activity)
        val posted = mutableListOf<Runnable>()
        val synced = mutableListOf<View>()
        val scheduler = CoalescedViewSyncScheduler(posted::add, synced::add)

        scheduler.request(root)
        scheduler.clear()
        scheduler.request(root)

        assertEquals(2, posted.size)
        posted[0].run()
        posted[1].run()
        assertEquals(listOf(root), synced)
    }
}
