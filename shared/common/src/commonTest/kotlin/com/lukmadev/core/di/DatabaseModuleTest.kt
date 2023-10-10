package com.lukmadev.core.di

import com.lukmadev.core.data.db.AppDatabase
import com.lukmadev.core.util.ModuleTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DatabaseModuleTest : ModuleTest {

    @Test
    fun should_inject_AppDatabase() {
        // when
        val component1 = get<AppDatabase>()
        val component2 = get<AppDatabase>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }
}
