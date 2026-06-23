package de.visualdigits.kaisstream.data.model.aisstreamio.staticdata

import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageContainer
import de.visualdigits.kaisstream.data.model.aisstreamio.common.AisMessageData
import de.visualdigits.kaisstream.data.model.aisstreamio.common.Dimension
import de.visualdigits.kaisstream.domain.model.geodata.ShipType

interface StaticDataAisMessageData<C : AisMessageContainer<C>> : AisMessageData<C> {

    val valid: Boolean
    val imoNumber: Long
    val callSign: String
    val destination: String
    val dimension: Dimension
    val shipType: ShipType
    val maximumStaticDraught: Double
}
