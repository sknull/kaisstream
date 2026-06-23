package de.visualdigits.kaisstream.data.model.aisstreamio.unknown

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageContainer
import kotlinx.serialization.Serializable

@Serializable
class UnknownMessageContainer(
    override val data: UnknownMessage = UnknownMessage()
) : AisMessageContainer<UnknownMessageContainer> {
    override fun toString(): String {
        return "UnknownMessage:"
    }
}
