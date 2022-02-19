package com.byagowi.persiancalendar.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.viewModels
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.byagowi.persiancalendar.R
import com.byagowi.persiancalendar.entities.Jdn
import com.byagowi.persiancalendar.ui.converter.ConverterFragment
import com.byagowi.persiancalendar.ui.converter.ConverterViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConverterFragmentTest {
    @Test
    fun testTodayButtonVisibility() {
        launchFragmentInContainer<ConverterFragment>(themeResId = R.style.LightTheme).onFragment {
            val model by it.viewModels<ConverterViewModel>()

            // Converter
            assertEquals(model.jdn.value, Jdn.today())
            assertFalse(model.todayButtonVisibility.value)
            model.jdn.value = Jdn.today() + 1
            assertEquals(model.jdn.value, Jdn.today() + 1)
            assertTrue(model.todayButtonVisibility.value)
            model.jdn.value = Jdn.today()
            assertFalse(model.todayButtonVisibility.value)

            // Day distance
            assertFalse(model.isDayDistance.value)
            model.isDayDistance.value = true
            assertTrue(model.isDayDistance.value)
            model.distanceJdn.value = Jdn.today() + 1
            assertTrue(model.todayButtonVisibility.value)
            model.distanceJdn.value = Jdn.today()

            runTest(UnconfinedTestDispatcher()) {
                val values = mutableListOf<Boolean>()
                val job = launch { model.todayButtonVisibility.collect(values::add) }
                model.distanceJdn.value = Jdn.today() + 1
                job.cancel()
                assertEquals(listOf(false, true), values)
            }
        }
    }
}