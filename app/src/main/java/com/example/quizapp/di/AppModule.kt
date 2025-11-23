package com.example.quizapp.di

import android.content.Context
import androidx.compose.foundation.layout.add
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.example.quizapp.presentation.signIn.GoogleAuthUiClient
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerGoogleAuthClient(
        @ApplicationContext context: Context,
    ): GoogleAuthUiClient {
        return GoogleAuthUiClient(context = context)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }
}
