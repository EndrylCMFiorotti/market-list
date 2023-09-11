package com.study.marketlist.di

import android.content.Context
import com.study.marketlist.repository.UserRepository
import com.study.marketlist.service.ClientService
import com.study.marketlist.util.ResourceWrapper
import com.study.marketlist.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    factory { ResourceWrapper(get() as Context) }

    factory { ClientService() }

    factory { UserRepository(get()) }

    viewModel { LoginViewModel(get(), get()) }
}