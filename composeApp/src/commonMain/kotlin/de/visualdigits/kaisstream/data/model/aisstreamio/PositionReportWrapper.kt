package de.visualdigits.kaisstream.data.model.aisstreamio

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.kaisstream.data.model.aisstreamio.positionreport.PositionReportContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("PositionReport")
class PositionReportWrapper(
    @SerialName("Message") override val message: PositionReportContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) : PositionAisMessageWrapper
