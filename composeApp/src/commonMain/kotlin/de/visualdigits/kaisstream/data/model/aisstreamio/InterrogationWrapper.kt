package de.visualdigits.kaisstream.data.model.aisstreamio

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.kaisstream.data.model.aisstreamio.interrogation.InterrogationContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Interrogation")
class InterrogationWrapper(
    @SerialName("Message") override val message: InterrogationContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) : StandardAisMessageWrapper
