package de.visualdigits.kaisstream.data.model.aisstreamio

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.kaisstream.data.model.aisstreamio.shipstaticdata.ShipStaticDataContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ShipStaticData")
class ShipStaticDataWrapper(
    @SerialName("Message") override val message: ShipStaticDataContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) : StaticDataAisMessageWrapper
