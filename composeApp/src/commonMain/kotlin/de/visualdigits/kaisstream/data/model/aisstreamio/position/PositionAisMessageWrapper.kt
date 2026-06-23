package de.visualdigits.kaisstream.data.model.aisstreamio.position

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisDataWrapper
import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMetaData

interface PositionAisMessageWrapper : AisDataWrapper {

    override val message: PositionAisMessageContainer<*>

    override val metaData: AisMetaData

    override val data: PositionAisMessageData<*>
        get() = message.data
}
