package de.visualdigits.kaisstream.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.util.mix
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.image_direction_white
import de.visualdigits.kaisstream.domain.model.settings.SK
import de.visualdigits.kaisstream.presentation.model.KAisStreamAction
import de.visualdigits.kaisstream.presentation.model.KAisStreamState
import de.visualdigits.kaisstream.presentation.model.KAisStreamViewModel
import de.visualdigits.kaisstream.presentation.style.gap
import org.jetbrains.compose.resources.imageResource

@Composable
fun RadarPage(
    viewModel: KAisStreamViewModel,
    state: KAisStreamState,
    location: Location,
    onAction: (KAisStreamAction) -> Unit
) {

    val radiusInner = state.settings?.get<String>(SK.radiusInner)?.toDouble()?:1000.0
    var currentRadarRadius by remember { mutableStateOf(radiusInner) }

    val selectedVessel = state.selectedVessel!!
    val activeHoverNameState = remember { mutableStateOf<String?>(null) }

    val vessels by viewModel.uiVessels.collectAsStateWithLifecycle()

    val imageHeading = imageResource(Res.drawable.image_direction_white)
    val colorBackground = Color(0xFF004711)
    val colorGrid = Color(0xFF00FF00)

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RadarPageMenuBar(
            currentRadarRadius = currentRadarRadius,
            setCurrentRadarRadius = { radius -> currentRadarRadius = radius },
            radiusInner = radiusInner,
            selectedVessel = selectedVessel,
            onAction = onAction
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            RadarBox(
                location = location,
                currentRadarRadius = currentRadarRadius,
                selectedVessel = selectedVessel,
                vessels = vessels,
                activeHoverNameState = activeHoverNameState,
                setActiveHoverName = { activeHoverName ->
                    activeHoverNameState.value = activeHoverName
                },
                imageHeading = imageHeading,
                colorBackground = colorBackground,
                colorGrid = colorGrid
            )

            val currentHoverName = activeHoverNameState.value
            currentHoverName?.let { name ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.extraSmall)
                            .border(2.dp, colorGrid, MaterialTheme.shapes.extraSmall)
                            .background(colorBackground.mix(colorGrid, 0.2f, BlendMode.Multiply))
                            .width(300.dp)
                            .height(IntrinsicSize.Min)
                            .padding(MaterialTheme.shapes.gap),
                        text = name,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = colorGrid,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
