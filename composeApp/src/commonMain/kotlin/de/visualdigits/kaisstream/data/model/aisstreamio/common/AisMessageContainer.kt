package de.visualdigits.kaisstream.data.model.aisstreamio.common

interface AisMessageContainer<T : AisMessageContainer<T>> {

    val data: AisMessageData<T>
}
