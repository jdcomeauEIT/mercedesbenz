package com.example.mercedesbenz.di

import com.example.mercedesbenz.model.RepositoryContract
import com.example.mercedesbenz.model.RestaurantRepository
import com.example.mercedesbenz.model.rest.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideRepository(
        serviceApi: ServiceApi,
        coroutineScope: CoroutineScope
    ): RepositoryContract =
        RestaurantRepository(serviceApi, coroutineScope)

}