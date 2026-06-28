package de.visualdigits.shipermansfriend.presentation.page.radar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_knots
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.presentation.style.LightGray
import de.visualdigits.shipermansfriend.presentation.style.MarineBlue
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HoveredVesselBox(
    modifier: Modifier = Modifier,
    activeHoverVesselState: MutableState<List<AisDataUi>>
) {
    val vessels = activeHoverVesselState.value
    if (vessels.isNotEmpty()) {
        BoxWithConstraints(
            modifier = modifier
                .fillMaxHeight()
        ) {
            val maxEntries = (maxHeight / 30.dp).toInt() - 1
            Box(
                modifier = modifier
                    .width(IntrinsicSize.Min)
                    .dropShadow(
                        shape = MaterialTheme.shapes.extraSmall,
                        shadow = Shadow(
                            radius = 2.dp,
                            spread = 2.dp,
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = DpOffset(2.dp, 2.dp)
                        )
                    )
            ) {
                Column(
                    modifier = modifier
                        .clip(MaterialTheme.shapes.extraSmall)
                        .width(IntrinsicSize.Min)
                        .background(MaterialTheme.colorScheme.surface),
                ) {
                    vessels.take(maxEntries).forEach { vessel ->
                        val speedLabel = if (vessel.sog > 0.5) {
                            "${vessel.sog} ${stringResource(Res.string.label_knots)}"
                        } else {
                            stringResource(Res.string.label_moored)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .width(IntrinsicSize.Max)
                                        .fillMaxHeight()
                                        .padding(MaterialTheme.shapes.gap / 2),
                                    text = "${vessel.name} ${vessel.distanceString} [$speedLabel]",
                                    style = MaterialTheme.typography.bodySmall,
                                    maxLines = 1
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .width(40.dp)
                                    .fillMaxHeight()
                                    .background(MarineBlue)
                                    .padding(MaterialTheme.shapes.gap / 2),
                                contentAlignment = Alignment.Center
                            ) {
                                vessel.shipType?.category?.icon?.let { icon ->
                                    Icon(
                                        modifier = Modifier
                                            .height(24.dp),
                                        painter = painterResource(icon),
                                        contentDescription = vessel.shipType.category.name,
                                        tint = LightGray,
                                    )
                                }
                            }
                        }
                    }

                    if (vessels.size > maxEntries) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.shapes.gap / 2),
                            text = "...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
