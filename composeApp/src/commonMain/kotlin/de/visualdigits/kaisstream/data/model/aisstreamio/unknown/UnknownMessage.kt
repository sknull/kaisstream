package de.visualdigits.kaisstream.data.model.aisstreamio.unknown

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageData
import kotlinx.serialization.Serializable

@Serializable
data class UnknownMessage(
    val value: String? = null
) : AisMessageData<UnknownMessageContainer>
