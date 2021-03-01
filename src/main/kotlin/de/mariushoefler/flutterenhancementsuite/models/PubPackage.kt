package de.mariushoefler.flutterenhancementsuite.models

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class PubPackage(
    val name: String,
    private val version: String,
    private val dependencies: Map<String, Any>,
    private val author: String?,
    private val authors: ArrayList<String>?,
    val description: String?,
    val homepage: String?
) {

    fun getAuthorName(): String {
        return if (author != null) {
            author.split("<")[0].trim()
        } else if (!authors.isNullOrEmpty()) {
            val authorsString = if (authors.size - 1 > 0) {
                " & ${authors.size - 1} more"
            } else {
                ""
            }
            authors[0].split("<")[0].trim() + authorsString
        } else ""
    }

    fun generateDependencyString() = "$name: ^$version"

    class Deserializer : ResponseDeserializable<PubPackage> {
        override fun deserialize(content: String): PubPackage {
            return Gson().fromJson(content, Root::class.java).latest.pubspec
        }
    }

    data class Root(val latest: Latest)

    data class Latest(val pubspec: PubPackage)
}