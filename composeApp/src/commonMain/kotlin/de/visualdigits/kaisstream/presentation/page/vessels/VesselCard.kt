package de.visualdigits.kaisstream.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.kaisstream.domain.model.geodata.AisDataUi
import de.visualdigits.kaisstream.presentation.style.MarineBlue
import de.visualdigits.kaisstream.presentation.style.gap


@Composable
fun VesselCard(
    uriHandler: UriHandler,
    screenWidth: Dp,
    screenHeight: Dp,
    data: AisDataUi,
    simple: Boolean = false
) {
    val landscape = screenWidth > screenHeight
    val iconWidth = if(landscape) screenWidth / 5 else screenWidth / 3
    val cellWidth = if(landscape) {
        if (simple) {
            (screenWidth - MaterialTheme.shapes.gap) / 3
        } else {
            (screenWidth - iconWidth - MaterialTheme.shapes.gap) / 3
        }
    } else {
        if (simple) {
            screenWidth - MaterialTheme.shapes.gap
        } else {
            screenWidth - iconWidth - MaterialTheme.shapes.gap
        }
    }
    val cardHeight = if (landscape) 180.dp else 320.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .conditional(!simple) {
                dropShadow(
                    shape = RoundedCornerShape(8.dp),
                    shadow = Shadow(
                        radius = 4.dp,
                        spread = 2.dp,
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = DpOffset((5).dp, 5.dp)
                    )
                )
            }
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(MaterialTheme.shapes.gap / 2),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2)
            ) {
                IndicatorButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    buttonColor = MarineBlue,
                    textColor = Color.White,
                    width = Dp.Unspecified,
                    height = 30.dp,
                    text = data.name,
                    onClick = {
                        uriHandler.openUri("https://www.myshiptracking.com/vessels/${data.mmsi}-mmsi-${data.mmsi}-imo-")
                    }
                )

                if (landscape) {
                    DataFieldsLandscape(uriHandler, cellWidth, data)
                } else {
                    DataFieldsPortrait(uriHandler, cellWidth, data)
                }
            }

            if (!simple) {
                VesselIconBox(iconWidth, data, cardHeight)
            }
        }
    }
}
