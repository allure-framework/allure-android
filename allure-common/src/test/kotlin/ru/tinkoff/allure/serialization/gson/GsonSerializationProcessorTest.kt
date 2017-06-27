package ru.tinkoff.allure.serialization.gson

import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import ru.tinkoff.allure.model.Label
import ru.tinkoff.allure.model.Link
import ru.tinkoff.allure.model.TestResult

/**
 * @author Badya on 01.06.2017.
 */
class GsonSerializationProcessorTest {
    val testResult =
            TestResult(
                    uuid = "0123456789ABCDEF",
                    name = "Test",
                    historyId = "testHistoryId",
                    fullName = "${this.javaClass.name}.serializeTestResult",
                    links = listOf(Link(name = "myLink")),
                    labels = listOf(Label(name = "label", value = "value"))
            )

    @Rule
    @JvmField
    val tempFolder = TemporaryFolder()

    @Test
    fun serializeTestResult() =
            Assert.assertEquals("Serialize TestResult failed",
                    """{
                      |  "uuid": "${testResult.uuid}",
                      |  "historyId": "${testResult.historyId}",
                      |  "fullName": "${GsonSerializationProcessorTest::class.java.canonicalName}.serializeTestResult",
                      |  "labels": [
                      |    {
                      |      "name": "label",
                      |      "value": "value"
                      |    }
                      |  ],
                      |  "links": [
                      |    {
                      |      "name": "myLink"
                      |    }
                      |  ],
                      |  "name": "${testResult.name}",
                      |  "steps": [],
                      |  "attachments": [],
                      |  "parameters": []
                      |}""".trimMargin(),
                    GsonSerializationProcessor.serialize(testResult)
            )


    @Test
    fun deserializeTestResult() {
        val deserialized = GsonSerializationProcessor.deserialize(GsonSerializationProcessor.serialize(testResult), TestResult::class.java)

        Assert.assertEquals(testResult.uuid, deserialized.uuid)
        Assert.assertEquals(testResult.fullName, deserialized.fullName)
    }
}