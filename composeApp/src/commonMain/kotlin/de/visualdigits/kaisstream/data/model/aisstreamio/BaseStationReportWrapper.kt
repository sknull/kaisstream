package de.visualdigits.kaisstream.data.model.aisstreamio

import de.visualdigits.kaisstream.data.model.aisstreamio.basestationreport.BaseStationReportContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMetaData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("BaseStationReport")
class BaseStationReportWrapper(
    @SerialName("Message") override val message: BaseStationReportContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) : StandardAisMessageWrapper
