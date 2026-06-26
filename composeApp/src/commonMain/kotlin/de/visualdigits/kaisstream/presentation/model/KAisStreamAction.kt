package de.visualdigits.kaisstream.presentation.model

import androidx.compose.runtime.Immutable
import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.common.domain.model.ui.KeyValue
import de.visualdigits.common.domain.model.ui.UiText
import de.visualdigits.kaisstream.domain.model.geodata.AisDataUi
import de.visualdigits.kaisstream.domain.model.settings.Settings
import de.visualdigits.kaisstream.domain.model.type.Language
import kotlinx.io.Sink
import kotlinx.io.Source

sealed interface KAisStreamAction {

    //
    // Settings
    //

    @Immutable
    data class OnEditSettingsClick(
        val isEditingSettings: Boolean
    ) : KAisStreamAction

    @Immutable
    data class OnSettingsValueChanged(
        val keyValue: KeyValue,
    ): KAisStreamAction

    @Immutable
    class OnEditSettingsCancelClick : KAisStreamAction

    @Immutable
    data class OnSettingsImport(
        val fileName: String,
        val source: Source
    ): KAisStreamAction

    @Immutable
    data class OnSettingsExport(
        val fileName: String,
        val sink: Sink
    ): KAisStreamAction

    @Immutable
    class OnSaveSettingsClick : KAisStreamAction

    @Immutable
    data class OnShowInfosClick(
        val isShowInfos: Boolean
    ) : KAisStreamAction

    @Immutable
    data class UpdateMaxImageSize(
        val settings: Settings?,
        val maxImageSize: Int
    ) : KAisStreamAction

    //
    // Masterdata
    //

    @Immutable
    data class OnMasterDataImport(
        val fileName: String,
        val source: Source
    ): KAisStreamAction

    @Immutable
    data class OnMasterDataExport(
        val fileName: String,
        val sink: Sink
    ): KAisStreamAction

    //
    // Vessels
    //
    data class OnShowRadar(
        val location: Location?,
        val vessels: List<AisDataUi>,
        val selectedVessel: AisDataUi
    ): KAisStreamAction

    class OnShowRadarBack: KAisStreamAction

    //
    // Tabs
    //
    data class OnTabSelected(
        val index: Int
    ): KAisStreamAction

    data class OnInitializeTabs(
        val tabLabels: List<Pair<String, UiText>>
    ): KAisStreamAction

    //
    //
    //
    @Immutable
    data class OnCollapsibleStateChange(
        val id: String,
        val isExpanded: Boolean
    ): KAisStreamAction

    @Immutable
    data class OnLanguageSelected(
        val language: Language,
    ): KAisStreamAction

    @Immutable
    class OnReconnect: KAisStreamAction
}
