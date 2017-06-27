package ru.tinkoff.allure

import org.junit.Assert
import org.junit.Test
import ru.tinkoff.allure.model.Attachment
import ru.tinkoff.allure.model.ExecutableItem
import ru.tinkoff.allure.model.WithAttachments
import java.util.*

/**
 * @author Badya on 19.04.2017.
 */
class AllureStorageTest {
    private inner class TestClass : ExecutableItem()

    @Test
    fun canGetObjectByInterface() {
        val clazzUUID = UUID.randomUUID().toString()
        val clazz = TestClass()
        AllureStorage.put(clazzUUID, clazz)
        val clazz2 = AllureStorage.get(clazzUUID, WithAttachments::class.java)
        clazz2.attachments.add(Attachment())
        val clazz3 = AllureStorage.get(clazzUUID, TestClass::class.java)
        Assert.assertFalse(clazz3.attachments.isEmpty())
    }
}
