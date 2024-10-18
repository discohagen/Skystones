import java.io.File

fun main() {
    val txtFilePath = "resources/cards.txt"
    val jsonFilePath = "resources/cards.json"

    val cards = File(txtFilePath)
        .readText()
        .trim()
        .split("</tr>")
        .mapNotNull { card ->
            val fields = card
                .trim()
                .removePrefix("<tr>")
                .split("</td>")
                .mapNotNull { cardField ->
                    cardField
                        .trim()
                        .removePrefix("<td>")
                        .takeIf { it.isNotEmpty() }
                }
            if (fields.isNotEmpty()) {
                Card(
                    name = fields[0],
                    northSpikes = fields[2].toInt(),
                    southSpikes = fields[3].toInt(),
                    eastSpikes = fields[4].toInt(),
                    westSpikes = fields[5].toInt()
                )
            } else {
                null
            }

        }

    val json = cards.joinToString(prefix = "[", postfix = "]", separator = ",") { card ->
        """
        {
            "name": "${card.name}",
            "northSpikes": ${card.northSpikes},
            "eastSpikes": ${card.eastSpikes},
            "southSpikes": ${card.southSpikes},
            "westSpikes": ${card.westSpikes}
        }
        """.trimIndent()
    }

    File(jsonFilePath).writeText(json)

}
