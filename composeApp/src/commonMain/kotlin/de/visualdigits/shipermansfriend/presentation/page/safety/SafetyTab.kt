package de.visualdigits.shipermansfriend.presentation.page.safety

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Severity
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.components.container.ErrorCard
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.hint_safety_messages
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.style.gap

@Composable
fun SafetyTab(
    viewModel: ShipermansFriendViewModel,
    platformType: PlatformType
) {

    val safetyMessages by viewModel.safetyData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        ErrorCard(
            modifier = Modifier
                .height(100.dp),
            errorMessage = UiText.StringResourceId(Res.string.hint_safety_messages),
            severity = Severity.Info,
            shapeContainer = MaterialTheme.shapes.small
        )

        PlatformVerticalScrollbarBox(
            modifier = Modifier
                .weight(1f)
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
            if(safetyMessages.isNotEmpty()) {
                safetyMessages.map { safetyMessage ->
                    Pair("safetyMessage_${safetyMessage.timeUtc}", @Composable {
                        key("safetyMessage_${safetyMessage.timeUtc}") {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .dropShadow(
                                        shape = RoundedCornerShape(8.dp),
                                        shadow = Shadow(
                                            radius = 4.dp,
                                            spread = 2.dp,
                                            color = Color.Black.copy(alpha = 0.5f),
                                            offset = DpOffset((5).dp, 5.dp)
                                        )
                                    )
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clip(MaterialTheme.shapes.small)
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(MaterialTheme.shapes.gap),
                                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .width(IntrinsicSize.Max),
                                        text = safetyMessage.timeUtc.format("yyyy-MM-dd HH:mm:ss"),
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Text(
                                        modifier = Modifier
                                            .weight(1f),
                                        text = safetyMessage.text?:"",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        }
                    })
                }
            } else {
                listOf()
            }
        }
    }
}
