package com.study.marketlist.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.study.marketlist.R
import com.study.marketlist.fixture.presentation.UserPresentationFixture
import com.study.marketlist.fixture.response.ErrorResponseFixture
import com.study.marketlist.fixture.response.LoginResponseFixture
import com.study.marketlist.network.wrapper.ResultWrapper
import com.study.marketlist.repository.UserRepository
import com.study.marketlist.util.ResourceWrapper
import com.study.marketlist.util.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var repository: UserRepository
    private lateinit var resourceWrapper: ResourceWrapper
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        repository = mockk(relaxed = true)
        resourceWrapper = mockk(relaxed = true)
        viewModel = LoginViewModel(resourceWrapper, repository)
    }

    @Test
    fun `WHEN email is filled WITH correct validation SHOULD return an empty text`() {
        viewModel.changeEmail(USER_EMAIL)

        val result = viewModel.setErrorEmail.getOrAwaitValue()
        val expected = EMPTY_TEXT

        assertEquals(expected, result)
    }

    @Test
    fun `WHEN email is filled WITH incorrect validation SHOULD return an error message`() {
        every { resourceWrapper.getString(R.string.frag_login_et_email_error) } returns ERROR_MESSAGE_EMAIL

        viewModel.changeEmail(USER_EMAIL_INVALID)

        val result = viewModel.setErrorEmail.getOrAwaitValue()
        val expected = ERROR_MESSAGE_EMAIL

        assertEquals(expected, result)
    }

    @Test
    fun `WHEN email isn't filled and password is filled WITH correct password SHOULD not button be activated`() {
        viewModel.changeEmail(EMPTY_TEXT)
        viewModel.changePassword(USER_PASSWORD)

        val result = viewModel.setStatusButtonLogin.getOrAwaitValue()

        assertFalse(result)
    }

    @Test
    fun `WHEN email is filled and password isn't filled WITH correct email SHOULD not button be activated`() {
        viewModel.changeEmail(USER_EMAIL)
        viewModel.changePassword(EMPTY_TEXT)

        val result = viewModel.setStatusButtonLogin.getOrAwaitValue()

        assertFalse(result)
    }

    @Test
    fun `WHEN email and password is filled WITH incorrect email validation SHOULD not button be activated`() {
        viewModel.changeEmail(USER_EMAIL_INVALID)
        viewModel.changePassword(USER_PASSWORD)

        val result = viewModel.setStatusButtonLogin.getOrAwaitValue()

        assertFalse(result)
    }

    @Test
    fun `WHEN email and password is filled WITH correct validation SHOULD button be activated`() {
        viewModel.changeEmail(USER_EMAIL)
        viewModel.changePassword(USER_PASSWORD)

        val result = viewModel.setStatusButtonLogin.getOrAwaitValue()

        assertTrue(result)
    }

    @Test
    fun `WHEN requested to validate the login WITH correct information SHOULD return the user`() =
        runTest {
            coEvery {
                repository.checkLogin(USER_EMAIL, USER_PASSWORD)
            } returns ResultWrapper.Success(
                LoginResponseFixture.getLoginResponseComplete(withError = null).build()
            )

            viewModel.validationLogin(USER_EMAIL, USER_PASSWORD)

            val result = viewModel.setLoginSuccess.getOrAwaitValue()
            val expected = UserPresentationFixture.getUserPresentationComplete().build()

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN requested to validate the login WITH correct information SHOULD return an error of graphQL`() =
        runTest {
            coEvery {
                repository.checkLogin(USER_EMAIL, USER_PASSWORD)
            } returns ResultWrapper.Success(
                LoginResponseFixture.getLoginResponseComplete(withUser = null).build()
            )

            viewModel.validationLogin(USER_EMAIL, USER_PASSWORD)

            val result = viewModel.setLoginError.getOrAwaitValue()
            val expected = ErrorResponseFixture.getErrorResponseComplete().build().message

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN requested to validate the login WITH correct information SHOULD return an error from request`() =
        runTest {
            coEvery {
                repository.checkLogin(USER_EMAIL, USER_PASSWORD)
            } returns ResultWrapper.Error(Exception())

            viewModel.validationLogin(USER_EMAIL, USER_PASSWORD)

            val result = viewModel.setLoginError.getOrAwaitValue()
            val expected = Exception().message

            assertEquals(expected, result)
        }

    @Test
    fun `WHEN requested to validate the login WITH incorrect information SHOULD return an error from request`() =
        runTest {
            coEvery {
                repository.checkLogin(USER_EMAIL_INVALID, USER_PASSWORD)
            } returns ResultWrapper.Error(Exception())

            viewModel.validationLogin(USER_EMAIL_INVALID, USER_PASSWORD)

            val result = viewModel.setLoginError.getOrAwaitValue()
            val expected = Exception().message

            assertEquals(expected, result)
        }

    companion object {
        private const val USER_EMAIL = "endryl@gmail.com"
        private const val USER_EMAIL_INVALID = "endrylgmail.com"
        private const val USER_PASSWORD = "123456789"
        private const val EMPTY_TEXT = ""
        private const val ERROR_MESSAGE_EMAIL = "Email inválido."
    }
}