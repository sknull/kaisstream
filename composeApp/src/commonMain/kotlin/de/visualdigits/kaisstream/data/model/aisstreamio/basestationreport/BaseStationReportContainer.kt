package de.visualdigits.kaisstream.data.model.aisstreamio.basestationreport

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class BaseStationReportContainer(
    @SerialName("BaseStationReport") override val data: BaseStationReport
) : AisMessageContainer<BaseStationReportContainer> {
    override fun toString(): String {
        return data.toString()
    }
}

