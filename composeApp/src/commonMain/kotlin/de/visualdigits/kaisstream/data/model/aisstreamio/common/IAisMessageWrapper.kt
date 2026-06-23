package de.visualdigits.kaisstream.data.model.aisstreamio.common

interface IAisMessageWrapper {

    val message: AisMessageContainer<*>

    val metaData: AisMetaData

    val data: AisMessageData<*>
        get() = message.data
}
