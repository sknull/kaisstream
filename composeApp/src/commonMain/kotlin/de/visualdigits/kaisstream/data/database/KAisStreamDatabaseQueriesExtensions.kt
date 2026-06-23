package de.visualdigits.kaisstream.data.database

import de.visualdigits.kaisstream.KAisStreamDatabaseQueries
import de.visualdigits.kaisstream.SettingsEntity

fun KAisStreamDatabaseQueries.upsertSettings(masterDataEntity: SettingsEntity) {
    val entity = getSettingsById(masterDataEntity.id).executeAsOneOrNull()
    if (entity != null) {
        updateSettings(masterDataEntity)
    } else {
        insertSettings(masterDataEntity)
    }
}

fun KAisStreamDatabaseQueries.insertSettings(masterDataEntity: SettingsEntity) {
    insertSettings(
        language = masterDataEntity.language,
        lastMaxImageSize = masterDataEntity.lastMaxImageSize,
        aisstreamApiKey = masterDataEntity.aisstreamApiKey,
        location = masterDataEntity.location,
        useGpsLocation = masterDataEntity.useGpsLocation,
        radiusOuter = masterDataEntity.radiusOuter,
        radiusInner = masterDataEntity.radiusInner
    )
}

fun KAisStreamDatabaseQueries.updateSettings(masterDataEntity: SettingsEntity) {
    updateSettingsEntity(
        language = masterDataEntity.language,
        lastMaxImageSize = masterDataEntity.lastMaxImageSize,
        aisstreamApiKey = masterDataEntity.aisstreamApiKey,
        location = masterDataEntity.location,
        useGpsLocation = masterDataEntity.useGpsLocation,
        radiusOuter = masterDataEntity.radiusOuter,
        radiusInner = masterDataEntity.radiusInner,
        id = masterDataEntity.id
    )
}
