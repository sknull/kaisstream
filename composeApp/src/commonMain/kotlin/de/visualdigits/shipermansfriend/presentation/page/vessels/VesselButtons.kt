package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.presentation.components.button.IndicatorButton
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_add_a_photo_24px
import de.visualdigits.compose.resources.icon_radar_24px
import de.visualdigits.compose.resources.icon_read_more_24px
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.IndicatorColor
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import org.jetbrains.compose.resources.painterResource

@Composable
fun VesselButtons(
    state: ShipermansFriendState,
    location: Location?,
    selectedVessel: AisDataUi,
    vessels: List<AisDataUi>,
    buttonSize: Dp,
    onAction: (ShipermansFriendAction) -> Unit
) {
    val vesselInProtocol = state.photoProtocol.containsKey(selectedVessel.mmsi)

    IndicatorButton(
        buttonColor = MarineBlue,
        width = buttonSize,
        height = buttonSize,
        leadingIcon = painterResource(Res.drawable.icon_add_a_photo_24px),
        leadingIconTint = if (vesselInProtocol) IndicatorColor else Color.White,
        onClick = {
            onAction(ShipermansFriendAction.OnAddVesselToPhotoProtocol(selectedVessel))
        }
    )

    IndicatorButton(
        buttonColor = MarineBlue,
        width = buttonSize,
        height = buttonSize,
        leadingIcon = painterResource(Res.drawable.icon_read_more_24px),
        leadingIconTint = Color.White,
        onClick = {
            routePlatformLink("https://www.myshiptracking.com/vessels/${selectedVessel.mmsi}-mmsi-${selectedVessel.mmsi}-imo-")
        }
    )

    IndicatorButton(
        buttonColor = MarineBlue,
        width = buttonSize,
        height = buttonSize,
        leadingIcon = painterResource(Res.drawable.icon_radar_24px),
        leadingIconTint = Color.White,
        onClick = {
            onAction(
                ShipermansFriendAction.OnShowRadar(
                    location = location,
                    vessels = vessels,
                    selectedVessel = selectedVessel
                )
            )
        }
    )
}
