package de.visualdigits.shipermansfriend.presentation.page.categories

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.components.container.VerticalCollapsibleBox
import de.visualdigits.common.presentation.model.CommonAction
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_arrow_drop_down_24px
import de.visualdigits.compose.resources.icon_arrow_right_24px
import de.visualdigits.shipermansfriend.data.repository.AisStreamClient
import de.visualdigits.shipermansfriend.domain.model.geodata.AisDataUi
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.vessels.LocationBox
import de.visualdigits.shipermansfriend.presentation.page.vessels.VesselCard
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource


@Composable
fun CategoriesPage(
    viewModel: ShipermansFriendViewModel,
    state: ShipermansFriendState,
    aisStreamClient: AisStreamClient,
    platformType: PlatformType,
    screenWidth: Dp,
    screenHeight: Dp,
    uriHandler: UriHandler,
    onCommonAction: ((CommonAction) -> Unit)?,
    onAction: (ShipermansFriendAction) -> Unit,
    location: () -> Location?
) {
    val receiverState by aisStreamClient.receiverState.collectAsStateWithLifecycle()
    val lastLocationUpdate by aisStreamClient.lastLocationUpdateMinutes.collectAsStateWithLifecycle()

    val uiVesselsList by viewModel.uiVessels.collectAsState()
    val locationValue = location()

    var categories by remember {
        mutableStateOf<List<Pair<String,List<Pair<String, AisDataUi>>>>>(emptyList())
    }

    // 2. Der asynchrone Lade-Block lauscht auf die Schiffsliste
    LaunchedEffect(uiVesselsList) {
        if (uiVesselsList.isEmpty()) {
            categories = emptyList()
            return@LaunchedEffect
        }

        // A) Ermittle alle benötigten Kategorien
        val distinctCategories = uiVesselsList
            .map { vessel -> vessel.shipType?.category ?: ShipType.Unknown_0.category }
            .distinct()

        // B) Berechne die Übersetzungen asynchron über die suspend 'getString'-Funktion
        // Ohne die UI-Liste währenddessen zu leeren!
        val lookupMap = distinctCategories.associateWith { category ->
            getString(category.label) // Deine suspend-Funktion läuft hier völlig sicher
        }

        // C) Baue die neue Datenstruktur im Speicher zusammen
        val updatedCategories = uiVesselsList
            .mapNotNull { vessel ->
                lookupMap[vessel.shipType?.category ?: ShipType.Unknown_0.category]
                    ?.let { translatedName -> Pair(translatedName, vessel) }
            }
            .groupBy { entry -> entry.first }
            .toList()
            .sortedBy { entry -> entry.first }

        // D) ATOMARER SCHNITT: Erst JETZT tauschen wir die alte Liste gegen die neue aus.
        // Die Scrollbox sieht zu keinem Zeitpunkt eine leere Liste, die Höhe bleibt stabil!
        categories = updatedCategories
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
    ) {
        LocationBox(
            uriHandler = uriHandler,
            locationValue = locationValue,
            receiverState = receiverState,
            lastLocationUpdate = lastLocationUpdate,
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
            ),
            scrollbarId = "categories",
            scrollPosition = viewModel.scrollPosition,
            onCommonAction = onCommonAction
        ) {
            listOf(Pair("categories", @Composable {
               Column(
                    modifier = Modifier
                        .fillMaxSize(1f)
                        .padding(top = MaterialTheme.shapes.gap),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                ) {
                    categories.forEach { category ->
                        val categoryName = category.first
                        VerticalCollapsibleBox(
                            paddingContainer = PaddingValues(MaterialTheme.shapes.gap),
                            title = "$categoryName [${category.second.size}]",
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            backgroundColor = Color.Black.copy(alpha = 0.2f),
                            shape = MaterialTheme.shapes.extraSmall,
                            isExpanded = state.collapsibleState["category_$categoryName"] == true,
                            iconArrowRight = painterResource(Res.drawable.icon_arrow_right_24px),
                            iconArrowDown = painterResource(Res.drawable.icon_arrow_drop_down_24px),
                            iconTint = Color.White,
                            onStateChange = { state->
                                onAction(ShipermansFriendAction.OnCollapsibleStateChange("category_$categoryName", state))
                            }
                        ) {
                            key("category_$categoryName") {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                                ) {
                                    category.second.forEach { entry ->
                                        val data = entry.second
                                        key("category_${categoryName}_${data.mmsi}") {
                                            VesselCard(
                                                uriHandler = uriHandler,
                                                screenWidth = screenWidth,
                                                screenHeight = screenHeight,
                                                location = locationValue,
                                                vessels = category.second.map { it.second },
                                                selectedVessel = data,
                                                simple = true,
                                                onAction = onAction
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }))
        }
    }
}
