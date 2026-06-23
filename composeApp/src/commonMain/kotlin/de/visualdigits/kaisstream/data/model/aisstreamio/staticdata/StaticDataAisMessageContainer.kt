package de.visualdigits.kaisstream.data.model.aisstreamio.staticdata

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageContainer

interface StaticDataAisMessageContainer<T : StaticDataAisMessageContainer<T>> : AisMessageContainer<T> {

    override val data: StaticDataAisMessageData<T>
}
