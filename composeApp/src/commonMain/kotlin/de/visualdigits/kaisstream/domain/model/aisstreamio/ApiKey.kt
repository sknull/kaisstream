package de.visualdigits.kaisstream.domain.model.aisstreamio

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiKey(
    @SerialName("APIKey") val apiKey: String? = null,
    @SerialName("BoundingBoxes") val boundingBoxes: List<List<List<Double>>> = listOf()
)
