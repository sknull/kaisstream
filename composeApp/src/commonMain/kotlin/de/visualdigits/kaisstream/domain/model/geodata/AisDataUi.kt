package de.visualdigits.kaisstream.domain.model.geodata

import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.geodata.Location

data class AisDataUi(
    val name: String,
    val mmsi: Long,
    val timeUtc: KmpOffsetDateTime,

    val location: Location,
    val isMoored: Boolean = true,
    val sog: Double = 0.0,
    val heading: Double = 0.0,

    val imoNumber: Long? = null,
    val callSign: String? = null,
    val destination: String? = null,
    val totalLength: Long? = null,
    val totalWidth: Long? = null,
    val shipType: ShipType? = null,
    val maximumStaticDraught: Double? = null,

    val distance: Double,
    val distanceString: String
) {

    override fun toString(): String {
        return "${name} mmsi=${mmsi} timeUtc=${timeUtc}, imo=${imoNumber}, type=${shipType?.category?.name}, dest=${destination}, maxDraught=${maximumStaticDraught}"
    }
}
