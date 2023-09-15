package com.study.marketlist.di

import com.study.marketlist.repository.UserRepository
import com.study.marketlist.service.ClientService
import com.study.marketlist.util.ResourceWrapper
import com.study.marketlist.viewmodel.LoginViewModel
import io.mockk.mockk
import io.mockk.mockkClass
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.koin.test.mock.MockProvider

class ModuleTest {

    @Before
    fun setUp() {
        MockProvider.register { mockkClass(it) }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `SHOULD inject to module`() {
        koinApplication {
            androidContext(mockk(relaxed = true))
            modules(module)
            checkModules {
                withInstance<ResourceWrapper>()
                withInstance<ClientService>()
                withInstance<UserRepository>()
                withInstance<LoginViewModel>()
            }
        }
    }
}