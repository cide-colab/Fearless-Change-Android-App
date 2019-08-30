package de.thkoeln.colab.fearlesschange.view.editor

import com.google.gson.*
import java.lang.reflect.Type

class SpanInfoInterfaceAdapter : JsonDeserializer<Any>, JsonSerializer<Any> {

    companion object {
        const val CLASSNAME = "CLASSNAME"
        const val DATA = "DATA"
    }

    @Throws(JsonParseException::class)
    override fun deserialize(jsonElement: JsonElement, type: Type,
                             jsonDeserializationContext: JsonDeserializationContext): Any {

        val jsonObject = jsonElement.asJsonObject
        val prim = jsonObject.get(CLASSNAME) as JsonPrimitive
        val className = prim.asString
        val objectClass = getObjectClass(className)
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), objectClass)
    }

    override fun serialize(jsonElement: Any, type: Type, jsonSerializationContext: JsonSerializationContext): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.addProperty(CLASSNAME, jsonElement.javaClass.name)
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement))
        return jsonObject
    }

    private fun getObjectClass(className: String): Class<*> {
        try {
            return Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e.message)
        }

    }
}