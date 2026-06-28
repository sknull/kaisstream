package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.shipermansfriend.data.repository.AisStreamClient
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.gap


@Composable
fun VesselsTab(
    viewModel: ShipermansFriendViewModel,
    aisStreamClient: AisStreamClient,
    platformType: PlatformType,
    screenWidth: Dp,
    screenHeight: Dp,
    isMoored: Boolean,
    onAction: (ShipermansFriendAction) -> Unit,
    location: () -> Location?
) {
    val receiverState by aisStreamClient.receiverState.collectAsStateWithLifecycle()
    val lastLocationUpdate by aisStreamClient.lastLocationUpdateMinutes.collectAsStateWithLifecycle()

    val uiVesselsList by viewModel.uiVessels.collectAsState()
    val innerRadius by aisStreamClient.innerRadius.collectAsState()
    val vessels = uiVesselsList.filter { it.isMoored == isMoored}
    val locationValue = location()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        LocationBox(
            locationValue = locationValue,
            receiverState = receiverState,
            lastLocationUpdate = lastLocationUpdate,
            currentRadarRadius = innerRadius,
            vesselNumber = vessels.size,
            onAction = viewModel::onAction
        )

        PlatformVerticalScrollbarBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = if (platformType == PlatformType.jvm) 20.dp else 0.dp),
            scrollbarModifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .width(10.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
            scrollbarStyle = PlatformScrollbarStyle(
                minimalHeight = 16.dp,
                thickness = 8.dp,
                shape = RoundedCornerShape(4.dp),
                hoverDurationMillis = 300,
                unhoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                hoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        ) {
            if (vessels.isNotEmpty()) {
                vessels.mapIndexed { index, vessel ->
                    Pair("entry_${vessel.mmsi}", @Composable {
                        key("entry_${vessel.mmsi}") {
                            VesselCard(
                                screenWidth = screenWidth,
                                screenHeight = screenHeight,
                                location = locationValue,
                                vessels = vessels,
                                selectedVessel = vessel,
                                onAction = onAction
                            )
                        }
                    })
                }
            } else listOf()
        }
    }
}
