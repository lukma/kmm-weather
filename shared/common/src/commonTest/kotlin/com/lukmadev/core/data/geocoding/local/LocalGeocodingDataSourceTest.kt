package com.lukmadev.core.data.geocoding.local

import app.cash.sqldelight.db.SqlDriver
import com.lukmadev.core.data.db.AppDatabase
import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.domain.common.exception.DatabaseError
import com.lukmadev.core.util.DatabaseTest
import com.lukmadev.core.util.TestSamples
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LocalGeocodingDataSourceTest : DatabaseTest {
    private val driver: SqlDriver by inject()
    private val database: AppDatabase by inject()
    private lateinit var dataSource: GeocodingDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = LocalGeocodingDataSource(get())
    }

    @Test
    fun perform_markCityAsFavorite_got_success() = runTest {
        // when
        dataSource.markCityAsFavorite(
            city = TestSamples.cities.first(),
        )
        val actual = database.favoriteCityQueries
            .finds(::mapCity)
            .executeAsList()

        // then
        val expected = TestSamples.cities
        assertEquals(expected, actual)
    }

    @Test
    fun perform_markCityAsFavorite_got_failure() = runTest {
        // given
        driver.close()

        // when
        val actual = runCatching {
            dataSource.markCityAsFavorite(
                city = TestSamples.cities.first(),
            )
        }

        // then
        assertFailsWith<DatabaseError> { actual.getOrThrow() }
    }

    @Test
    fun perform_getFavoriteCities_got_value() = runTest {
        // given
        database.transaction {
            TestSamples.cities.forEach {
                database.favoriteCityQueries.upsert(favoriteCityTable = it.toFavoriteCityTable())
            }
        }

        // when
        val actual = dataSource.getFavoriteCities().single()

        // then
        val expected = TestSamples.cities
        assertEquals(expected, actual)
    }

    @Test
    fun perform_getFavoriteCities_got_failure() = runTest {
        // given
        driver.close()

        // when
        val actual = runCatching {
            dataSource.getFavoriteCities().single()
        }

        // then
        assertFailsWith<DatabaseError> { actual.getOrThrow() }
    }
}
