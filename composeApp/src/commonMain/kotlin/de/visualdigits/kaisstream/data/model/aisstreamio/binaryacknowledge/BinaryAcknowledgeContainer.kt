package de.visualdigits.kaisstream.data.model.aisstreamio.binaryacknowledge

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BinaryAcknowledgeContainer(
    @SerialName("BinaryAcknowledge") override val data: BinaryAcknowledge
) : AisMessageContainer<BinaryAcknowledgeContainer> {
    override fun toString(): String {
        return data.toString()
    }
}

