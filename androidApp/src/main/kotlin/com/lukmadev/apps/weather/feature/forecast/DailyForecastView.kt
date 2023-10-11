package com.lukmadev.apps.weather.feature.forecast

import android.os.Build
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.lukmadev.apps.weather.R
import com.lukmadev.apps.weather.ui.theme.WeatherTheme
import com.lukmadev.core.domain.forecast.DailyForecast
import com.lukmadev.core.domain.forecast.Wind
import com.lukmadev.uikit.util.ext.shimmerPlaceholder
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DailyForecastView(
    item: DailyForecastListItemModel,
    modifier: Modifier = Modifier,
) {
    Card(modifier) {
        ConstraintLayout(
            Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
        ) {
            val (
                dateValue,
                temperatureValue,
                humidityLabel,
                humidityValue,
                windSpeedLabel,
                windSpeedValue,
            ) = createRefs()

            Text(
                text = (item as? DailyForecastListItemModel.Loaded)?.dailyForecast?.date
                    ?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val formatter =
                                DateTimeFormatter.ofPattern(
                                    "dd MMM yyyy, HH:mm",
                                    Locale.getDefault()
                                )
                            val localDateTime = LocalDateTime.from(
                                it.toJavaInstant().atZone(ZoneId.systemDefault())
                            )
                            formatter.format(localDateTime)
                        } else {
                            it.toString()
                        }
                    }
                    .orEmpty(),
                modifier = Modifier
                    .constrainAs(dateValue) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        width = if (item is DailyForecastListItemModel.Loading) {
                            Dimension.percent(0.5f)
                        } else {
                            Dimension.wrapContent
                        }
                    }
                    .shimmerPlaceholder(item is DailyForecastListItemModel.Loading),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
            )

            Text(
                text = (item as? DailyForecastListItemModel.Loaded)?.dailyForecast?.temperature
                    ?.let { "$it°" }
                    .orEmpty(),
                modifier = Modifier
                    .constrainAs(temperatureValue) {
                        start.linkTo(parent.start)
                        top.linkTo(dateValue.bottom, margin = 8.dp)
                        width = if (item is DailyForecastListItemModel.Loading) {
                            Dimension.percent(0.3f)
                        } else {
                            Dimension.wrapContent
                        }
                    }
                    .shimmerPlaceholder(item is DailyForecastListItemModel.Loading),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayLarge,
            )

            Text(
                text = stringResource(id = R.string.label_humidity),
                modifier = Modifier.constrainAs(humidityLabel) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    width = Dimension.percent(0.3f)
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
            )

            Text(
                text = (item as? DailyForecastListItemModel.Loaded)?.dailyForecast?.humidity?.toString()
                    .orEmpty(),
                modifier = Modifier
                    .constrainAs(humidityValue) {
                        start.linkTo(humidityLabel.start)
                        top.linkTo(humidityLabel.bottom)
                        end.linkTo(humidityLabel.end)
                        width = Dimension.fillToConstraints
                    }
                    .shimmerPlaceholder(item is DailyForecastListItemModel.Loading),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = stringResource(id = R.string.label_wind_speed),
                modifier = Modifier.constrainAs(windSpeedLabel) {
                    start.linkTo(humidityLabel.start)
                    top.linkTo(humidityValue.bottom, margin = 4.dp)
                    end.linkTo(humidityLabel.end)
                    width = Dimension.fillToConstraints
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
            )

            Text(
                text = (item as? DailyForecastListItemModel.Loaded)?.dailyForecast?.wind?.speed?.toString()
                    .orEmpty(),
                modifier = Modifier
                    .constrainAs(windSpeedValue) {
                        start.linkTo(windSpeedLabel.start)
                        top.linkTo(windSpeedLabel.bottom)
                        end.linkTo(windSpeedLabel.end)
                        width = Dimension.fillToConstraints
                    }
                    .shimmerPlaceholder(item is DailyForecastListItemModel.Loading),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    WeatherTheme {
        DailyForecastView(
            item = DailyForecastListItemModel.Loaded(
                dailyForecast = DailyForecast(
                    date = Instant.parse("2023-10-09T15:00:00.000Z"),
                    temperature = 302.11,
                    humidity = 79.0,
                    wind = Wind(
                        speed = 1.21,
                        degrees = 227.0,
                        gust = 1.41,
                    )
                ),
            ),
        )
    }
}
