package de.visualdigits.kaisstream.data.model.aisstreamio.staticdata

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisDataWrapper
import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMetaData

interface StaticDataAisMessageWrapper : AisDataWrapper {

    override val message: StaticDataAisMessageContainer<*>

    override val metaData: AisMetaData

    override val data: StaticDataAisMessageData<*>
        get() = message.data
}
