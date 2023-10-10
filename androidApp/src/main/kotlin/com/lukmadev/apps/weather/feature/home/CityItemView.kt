package com.lukmadev.apps.weather.feature.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.lukmadev.apps.weather.ui.theme.WeatherTheme
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.uikit.util.ext.shimmerPlaceholder

@Composable
fun CityItemView(
    item: CityListItemModel,
    toggle: (City) -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(modifier) {
        val (
            name,
            stateAndCountry,
            isFavorite,
        ) = createRefs()

        Text(
            text = (item as? CityListItemModel.Loaded)?.city?.name.orEmpty(),
            modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    width = if (item is CityListItemModel.Loading) {
                        Dimension.percent(percent = 0.5f)
                    } else {
                        end.linkTo(isFavorite.start)
                        Dimension.fillToConstraints
                    }
                }
                .shimmerPlaceholder(item is CityListItemModel.Loading),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
        )

        Text(
            text = (item as? CityListItemModel.Loaded)?.city?.let { city ->
                val texts = mutableListOf<String>()
                city.state?.run(texts::add)
                texts.add(city.country)
                texts.joinToString(", ") { it }
            } ?: "",
            modifier = Modifier
                .constrainAs(stateAndCountry) {
                    start.linkTo(parent.start)
                    top.linkTo(name.bottom, margin = 4.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .shimmerPlaceholder(item is CityListItemModel.Loading),
            style = MaterialTheme.typography.bodyMedium,
        )

        if (item is CityListItemModel.Loaded) {
            IconButton(
                onClick = { (item as? CityListItemModel.Loaded)?.city?.run { toggle(this) } },
                modifier = Modifier
                    .constrainAs(isFavorite) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .size(24.dp)
            ) {
                Icon(
                    if (item.isFavorite) Icons.Filled.Star else Icons.Filled.StarOutline,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    WeatherTheme {
        CityItemView(
            item = CityListItemModel.Loaded(
                city = City(
                    name = "Semarang",
                    latitude = -6.9903988,
                    longitude = 110.4229104,
                    state = "Central Java",
                    country = "ID",
                ),
                isFavorite = false
            ),
            toggle = { /* no-op */ },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
