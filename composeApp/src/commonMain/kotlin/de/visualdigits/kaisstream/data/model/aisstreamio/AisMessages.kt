package de.visualdigits.kaisstream.data.model.aisstreamio
import de.visualdigits.kaisstream.data.model.aisstreamio.basestationreport.BaseStationReportContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.binaryacknowledge.BinaryAcknowledgeContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.kaisstream.data.model.aisstreamio.common.IAisMessageWrapper
import de.visualdigits.kaisstream.data.model.aisstreamio.interrogation.InterrogationContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.position.PositionAisMessageWrapper
import de.visualdigits.kaisstream.data.model.aisstreamio.positionreport.PositionReportContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.shipstaticdata.ShipStaticDataContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.standardclassbpositionreport.StandardClassBPositionReportContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.staticdata.StaticDataAisMessageWrapper
import de.visualdigits.kaisstream.data.model.aisstreamio.staticdatareport.StaticDataReportContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.unknown.UnknownMessageContainer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("MessageType")
sealed class AisMessageWrapper {

    @SerialName("Message") abstract val message: AisMessageContainer<*>

    @SerialName("MetaData") abstract val metaData: AisMetaData

    override fun toString(): String {
        return "$metaData - $message"
    }

    @Serializable
    @SerialName("BaseStationReport")
    class BaseStationReportWrapper(
        @SerialName("Message") override val message: BaseStationReportContainer,
        @SerialName("MetaData") override val metaData: AisMetaData
    ) : AisMessageWrapper(), IAisMessageWrapper

    @Serializable
    @SerialName("BinaryAcknowledge")
    class BinaryAcknowledgeWrapper(
        @SerialName("Message") override val message: BinaryAcknowledgeContainer,
        @SerialName("MetaData") override val metaData: AisMetaData
    ) : AisMessageWrapper(), IAisMessageWrapper

    @Serializable
    @SerialName("PositionReport")
    class PositionReportWrapper(
        @SerialName("Message") override val message: PositionReportContainer,
        @SerialName("MetaData") override val metaData: AisMetaData
    ) : AisMessageWrapper(), PositionAisMessageWrapper

    @Serializable
    @SerialName("StandardClassBPositionReport")
    class StandardClassBPositionReportWrapper(
        @SerialName("Message") override val message: StandardClassBPositionReportContainer,
        @SerialName("MetaData") override val metaData: AisMetaData
    ) : AisMessageWrapper(), PositionAisMessageWrapper

    @Serializable
    @SerialName("ShipStaticData")
    class ShipStaticDataWrapper(
        @SerialName("Message") override val message: ShipStaticDataContainer,
        @SerialName("MetaData") override val metaData: AisMetaData
    ) : AisMessageWrapper(), StaticDataAisMessageWrapper

    @Serializable
    @SerialName("StaticDataReport")
    class StaticDataReportWrapper(
        @SerialName("Message") override val message: StaticDataReportContainer,
        @SerialName("MetaData") override val metaData: AisMetaData
    ) : AisMessageWrapper(), StaticDataAisMessageWrapper

    @Serializable
    @SerialName("Interrogation")
    class InterrogationWrapper(
        @SerialName("Message") override val message: InterrogationContainer,
        @SerialName("MetaData") override val metaData: AisMetaData
    ) : AisMessageWrapper(), IAisMessageWrapper

    @Serializable
    @SerialName("UnknownMessage")
    class UnknownMessageWrapper(
        @SerialName("Message") override val message: UnknownMessageContainer,
        @SerialName("MetaData") override val metaData: AisMetaData
    ) : AisMessageWrapper(), IAisMessageWrapper
}
