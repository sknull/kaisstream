package de.visualdigits.kaisstream.presentation.page.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.ui.FileMode
import de.visualdigits.common.presentation.components.PlatformFileChooser
import de.visualdigits.common.presentation.components.PlatformFileSaver
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.dialog_title_export_settings
import de.visualdigits.compose.resources.dialog_title_import_settings
import de.visualdigits.compose.resources.icon_download_24px
import de.visualdigits.compose.resources.icon_upload_24px
import de.visualdigits.compose.resources.label_masterdata
import de.visualdigits.compose.resources.label_settings
import de.visualdigits.compose.resources.save
import de.visualdigits.kaisstream.presentation.model.KAisStreamAction
import de.visualdigits.kaisstream.presentation.style.gap
import kotlinx.io.files.Path
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun SettingsMenuBar(
    onAction: (KAisStreamAction) -> Unit
) {
    val homeDirectoryPath: String = koinInject(qualifier = named("homeDirectory"))

    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
    ) {
        PlatformFileChooser(
            label = stringResource(Res.string.label_settings),
            buttonTextStyle = MaterialTheme.typography.bodySmall,
            buttonTextAlign = TextAlign.Start,
            title = stringResource(Res.string.dialog_title_import_settings),
            fileMode = FileMode.FILES_ONLY,
            buttonColor = MaterialTheme.colorScheme.surface,
            leadingIcon = painterResource(Res.drawable.icon_download_24px),
            startDirectory = Path(homeDirectoryPath, "backup"),
            onOkSource = { fileName, source ->
                onAction(KAisStreamAction.OnSettingsImport(fileName, source))
            }
        )

        PlatformFileSaver(
            label = stringResource(Res.string.label_settings),
            labelSaveButton = stringResource(Res.string.save),
            buttonTextStyle = MaterialTheme.typography.bodySmall,
            buttonTextAlign = TextAlign.Start,
            title = stringResource(Res.string.dialog_title_export_settings),
            fileMode = FileMode.FILES_ONLY,
            suggestedFileName = "KiekutHelper-settings_${KmpOffsetDateTime.now().format("yyyy-MM-dd_HH-mm-ss")}.json",
            buttonColor = MaterialTheme.colorScheme.surface,
            leadingIcon = painterResource(Res.drawable.icon_upload_24px),
            startDirectory = Path(homeDirectoryPath, "backup"),
            ) { fileName, outs ->
                onAction(KAisStreamAction.OnSettingsExport(fileName, outs))
            }

        PlatformFileChooser(
            label = stringResource(Res.string.label_masterdata),
            buttonTextStyle = MaterialTheme.typography.bodySmall,
            buttonTextAlign = TextAlign.Start,
            title = stringResource(Res.string.dialog_title_import_settings),
            fileMode = FileMode.FILES_ONLY,
            buttonColor = MaterialTheme.colorScheme.surface,
            leadingIcon = painterResource(Res.drawable.icon_download_24px),
            startDirectory = Path(homeDirectoryPath, "backup"),
            onOkSource = { fileName, source ->
                onAction(KAisStreamAction.OnMasterDataImport(fileName, source))
            }
        )

        PlatformFileSaver(
            label = stringResource(Res.string.label_masterdata),
            labelSaveButton = stringResource(Res.string.save),
            buttonTextStyle = MaterialTheme.typography.bodySmall,
            buttonTextAlign = TextAlign.Start,
            title = stringResource(Res.string.dialog_title_export_settings),
            fileMode = FileMode.FILES_ONLY,
            suggestedFileName = "KiekutHelper-masterdata_${KmpOffsetDateTime.now().format("yyyy-MM-dd_HH-mm-ss")}.json",
            buttonColor = MaterialTheme.colorScheme.surface,
            leadingIcon = painterResource(Res.drawable.icon_upload_24px),
            startDirectory = Path(homeDirectoryPath, "backup"),
        ) { fileName, outs ->
            onAction(KAisStreamAction.OnMasterDataExport(fileName, outs))
        }
    }
}
