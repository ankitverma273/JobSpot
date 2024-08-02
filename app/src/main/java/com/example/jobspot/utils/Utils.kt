package com.example.jobspot.utils

import org.json.JSONObject

fun modifyJson(jsonString: String): String {
    val jsonObject = JSONObject(jsonString)

    val value = jsonObject.getJSONArray("results")

    for (i in 0..value.length()-1) {
        val type = value.getJSONObject(i)

        if (type.has("type")) {
            val typeValue = type.getInt("type")
            type.put("type", typeValue.toString())
        }

    }

    return jsonObject.toString()
}

fun extractTitleAndDetails(otherDetails: String): String {
    // Define regular expressions to match the "Title" and "Other Details" fields
    val titleRegex = "Title\\s*:\\s*([^\\n\\r]+)".toRegex()
    val detailsRegex = "Other Details\\s*:\\s*([^\\n\\r]+)".toRegex()

    // Find the matches for "Title" and "Other Details"
    val titleMatch = titleRegex.find(otherDetails)
    val detailsMatch = detailsRegex.find(otherDetails)

    // Extract the values or use empty strings if not found
    val title = titleMatch?.groupValues?.get(1)?.trim() ?: ""
    val details = detailsMatch?.groupValues?.get(1)?.trim() ?: ""

    // Merge the title and details with a period and space
    return "$title. $details"
}