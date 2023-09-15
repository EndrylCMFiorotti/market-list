package com.study.marketlist.util

import android.content.Context
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import junit.framework.TestCase.assertEquals

class ResourceWrapperTest {
    private lateinit var context: Context
    private lateinit var resourceWrapper: ResourceWrapper

    @Before
    fun setUp() {
        context = mockk()
        resourceWrapper = ResourceWrapper(context)
    }

    @Test
    fun `WHEN get a text from resource WITH a valid id SHOULD return a text`() {
        every { context.getString(any()) } returns MESSAGE

        val result = resourceWrapper.getString(STRING_ID)
        val expected = MESSAGE

        assertEquals(expected, result)
    }

    companion object {
        private const val STRING_ID = 1
        private const val MESSAGE = "Message"
    }
}