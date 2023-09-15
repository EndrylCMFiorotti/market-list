package com.study.marketlist.repository

import com.study.marketlist.fixture.response.LoginResponseFixture
import com.study.marketlist.network.wrapper.ResultWrapper
import com.study.marketlist.service.ClientServiceMock
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        repository = UserRepository(ClientServiceMock())
    }

    @Test
    fun `WHEN requested a login WITH email and password correct SHOULD successfully return a user`() =
        runTest {
            val result = repository.checkLogin(USER_EMAIL, USER_PASSWORD)
            val expected = ResultWrapper.Success(
                LoginResponseFixture.getLoginResponseComplete().build()
            )

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN requested a login WITH email incorrect SHOULD return an error of graphQL`() =
        runTest {
            val result = repository.checkLogin(USER_EMAIL_INVALID, USER_PASSWORD)
            val expected = ResultWrapper.Success(
                LoginResponseFixture.getLoginResponseComplete(withHasError = true).build()
            )

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN requested a login WITH password incorrect SHOULD return an error of graphQL`() =
        runTest {
            val result = repository.checkLogin(USER_EMAIL, USER_PASSWORD_INVALID)
            val expected = ResultWrapper.Success(
                LoginResponseFixture.getLoginResponseComplete(withHasError = true).build()
            )

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN requested a login WITH email and password incorrect SHOULD return an error of graphQL`() =
        runTest {
            val result = repository.checkLogin(USER_EMAIL_INVALID, USER_PASSWORD_INVALID)
            val expected = ResultWrapper.Success(
                LoginResponseFixture.getLoginResponseComplete(withHasError = true).build()
            )

            assertEquals(expected, result)
        }

    companion object {
        private const val USER_EMAIL = "gabriel@gmail.com"
        private const val USER_EMAIL_INVALID = "gabrielgmail.com"
        private const val USER_PASSWORD = "123456789"
        private const val USER_PASSWORD_INVALID = "1234"
    }
}