package com.alps.compose_mvvm_strcucture.di

import android.content.Context
import androidx.room.Room
import com.alps.compose_mvvm_strcucture.db.AppDatabase
import com.alps.compose_mvvm_strcucture.db.ProductDAO
import com.alps.compose_mvvm_strcucture.model.network.ApiService
import com.alps.compose_mvvm_strcucture.utils.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// This module provides dependencies for database, networking, and API services
@Module
@InstallIn(SingletonComponent::class)  // This ensures that the provided dependencies are available for the entire app
object NetworkModule {

    // Provides the AppDatabase instance (Room Database) for dependency injection
    @Provides
    @Singleton  // Ensures a single instance is used throughout the app
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,   // Context is used to build the database
            AppDatabase::class.java,   // Specifies the database class
            "product_db"  // Name of the database
        ).build()   // Builds and returns the database instance
    }

    // Provides Retrofit instance to handle HTTP requests
    @Provides
    @Singleton  // Ensures a single instance of Retrofit is used
    fun providerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstant.BASE_URL)  // Base URL for the API requests
            .addConverterFactory(GsonConverterFactory.create())  // Converts JSON to Kotlin objects using Gson
            .build()  // Builds and returns the Retrofit instance
    }

    // Provides the ApiService instance, which is used to define network calls
    @Provides
    @Singleton  // Ensures a single instance of ApiService
    fun providerApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)  // Creates and returns the ApiService using Retrofit
    }

    // Provides the ProductDAO instance for database operations (used to access the product table)
    @Singleton  // Ensures a single instance of ProductDAO is used
    @Provides
    fun provideProductDao(appDatabase: AppDatabase): ProductDAO {
        return appDatabase.getFavoriteProducts()  // Returns the DAO for accessing the product table
    }
}
