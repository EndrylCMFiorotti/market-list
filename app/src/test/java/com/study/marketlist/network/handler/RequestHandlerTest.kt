package com.study.marketlist.network.handler

import com.study.marketlist.network.exceptions.ClientException
import com.study.marketlist.network.exceptions.RedirectException
import com.study.marketlist.network.exceptions.ServerException
import com.study.marketlist.network.exceptions.UnknownResponseException
import com.study.marketlist.network.wrapper.ResultWrapper
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.net.HttpRetryException

@ExperimentalCoroutinesApi
class RequestHandlerTest {

    private lateinit var fakeApi: FakeApi
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setUp() {
        fakeApi = mockk()
        fakeRepository = FakeRepository(fakeApi)
    }

    @Test
    fun `SHOULD return Success WHEN fake api call succeeds`() = runTest {
        coEvery { fakeApi.doRequest() } returns Unit

        val result = fakeRepository.doRequest() as ResultWrapper.Success<Unit>
        val expected = Unit

        assertEquals(expected, result.content)
    }

    @Test
    fun `SHOULD returns Error WHEN api fails with code 300`() = runTest {
        coEvery { fakeApi.doRequest() } throws mockk<HttpRetryException> {
            every { responseCode() } returns ERROR_CODE_MULTIPLE_CHOICES
            every { message } returns ERROR_MESSAGE
        }

        val result = fakeRepository.doRequest() as ResultWrapper.Error
        val expected = RedirectException(
            code = ERROR_CODE_MULTIPLE_CHOICES,
            message = ERROR_MESSAGE
        )

        assertEquals(expected, result.error)
    }

    @Test
    fun `SHOULD returns Error WHEN api fails with code 400`() = runTest {
        coEvery { fakeApi.doRequest() } throws mockk<HttpRetryException> {
            every { responseCode() } returns ERROR_CODE_BAD_REQUEST
            every { message } returns ERROR_MESSAGE
        }

        val result = fakeRepository.doRequest() as ResultWrapper.Error
        val expected = ClientException(
            code = ERROR_CODE_BAD_REQUEST,
            message = ERROR_MESSAGE
        )

        assertEquals(expected, result.error)
    }

    @Test
    fun `SHOULD returns Error WHEN api fails with code 500`() = runTest {
        coEvery { fakeApi.doRequest() } throws mockk<HttpRetryException> {
            every { responseCode() } returns ERROR_CODE_INTERNAL_SERVER
            every { message } returns ERROR_MESSAGE
        }

        val result = fakeRepository.doRequest() as ResultWrapper.Error
        val expected = ServerException(
            code = ERROR_CODE_INTERNAL_SERVER,
            message = ERROR_MESSAGE
        )

        assertEquals(expected, result.error)
    }

    @Test
    fun `SHOULD returns Error WHEN api fails with code 600`() = runTest {
        coEvery { fakeApi.doRequest() } throws mockk<HttpRetryException> {
            every { responseCode() } returns ERROR_CODE_UNKNOWN
            every { message } returns ERROR_MESSAGE
        }

        val result = fakeRepository.doRequest() as ResultWrapper.Error
        val expected = UnknownResponseException(
            code = ERROR_CODE_UNKNOWN,
            message = ERROR_MESSAGE
        )

        assertEquals(expected, result.error)
    }

    inner class FakeRepository(private val fakeApi: FakeApi) {
        suspend fun doRequest(): ResultWrapper<Unit> {
            return requestHandler { fakeApi.doRequest() }
        }
    }

    interface FakeApi {
        suspend fun doRequest()
    }

    companion object {
        private const val ERROR_CODE_MULTIPLE_CHOICES = 300
        private const val ERROR_CODE_BAD_REQUEST = 400
        private const val ERROR_CODE_INTERNAL_SERVER = 500
        private const val ERROR_CODE_UNKNOWN = 600
        private const val ERROR_MESSAGE = "Error exception description"
    }
}