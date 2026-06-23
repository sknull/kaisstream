package de.visualdigits.kaisstream.di

import app.cash.sqldelight.ColumnAdapter
import de.visualdigits.common.domain.util.CryptoBox
import de.visualdigits.common.domain.util.EncryptedString
import de.visualdigits.kaisstream.KAisStreamDatabaseQueries
import de.visualdigits.kaisstream.SettingsDatabase
import de.visualdigits.kaisstream.SettingsEntity
import de.visualdigits.kaisstream.data.database.DriverFactory
import de.visualdigits.kaisstream.data.repository.AisStreamClient
import de.visualdigits.kaisstream.data.repository.DefaultMasterDataRepository
import de.visualdigits.kaisstream.data.repository.DefaultSettingsRepository
import de.visualdigits.kaisstream.data.repository.VesselDataRepository
import de.visualdigits.kaisstream.domain.repository.MasterDataRepository
import de.visualdigits.kaisstream.domain.repository.SettingsRepository
import de.visualdigits.kaisstream.presentation.model.KAisStreamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

expect val homeDirectory: String

val sharedModule = module {

    single(named("homeDirectory")) { homeDirectory }

    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    singleOf(::KAisStreamViewModel)

    single {
        val driver = get<DriverFactory>().createDriver(get<String>(named("homeDirectory")))
        val cryptoBox = get<CryptoBox>()

        val passwordAdapter = object : ColumnAdapter<EncryptedString, String> {
            override fun decode(databaseValue: String): EncryptedString = cryptoBox.decrypt(databaseValue)
            override fun encode(value: EncryptedString): String = cryptoBox.encrypt(value)
        }

        SettingsDatabase(
            driver,
            SettingsEntityAdapter = SettingsEntity.Adapter(
                aisstreamApiKeyAdapter = passwordAdapter
            )
        )
    }

    single<KAisStreamDatabaseQueries> {
        get<SettingsDatabase>().kAisStreamDatabaseQueries
    }

    singleOf(::DefaultSettingsRepository).bind<SettingsRepository>()
    singleOf(::DefaultMasterDataRepository).bind<MasterDataRepository>()
    single {
        AisStreamClient(
            httpClient = get(),
            settingsRepository = get(),
            locationProvider = get(),
            scope = get()
        )
    }
    single {
        VesselDataRepository(
            httpClient = get()
        )
    }
}
