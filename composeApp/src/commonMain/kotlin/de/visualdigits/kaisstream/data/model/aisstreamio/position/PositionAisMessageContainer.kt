package de.visualdigits.kaisstream.data.model.aisstreamio.position

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageContainer

interface PositionAisMessageContainer<T : PositionAisMessageContainer<T>> : AisMessageContainer<T> {

    override val data: PositionAisMessageData<T>
}
