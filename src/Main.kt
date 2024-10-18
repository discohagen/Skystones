import java.io.File

fun loadCardsFromJson(filePath: String): List<Card> {
    val jsonText = File(filePath).readText()
    val jsonArray = jsonText
        .trim()
        .removeSurrounding("[", "]")
        .split("},")
        .map {
            it
                .trim()
                .removeSuffix("}") + "}"
        }

    return jsonArray.map { jsonObject ->
        val props = jsonObject
            .removeSurrounding("{", "}")
            .split(",")
            .associate { it ->
                val (key, value) = it
                    .split(":")
                    .map {
                        it
                            .trim()
                            .removeSurrounding("\"")
                    }
                key to value
            }

        Card(
            name = props["name"] ?: "",
            northSpikes = props["northSpikes"]?.toInt() ?: 0,
            eastSpikes = props["eastSpikes"]?.toInt() ?: 0,
            southSpikes = props["southSpikes"]?.toInt() ?: 0,
            westSpikes = props["westSpikes"]?.toInt() ?: 0
        )
    }
}

fun main() {
    val cardsFilePath = "resources/cards.json"
    val cards = loadCardsFromJson(cardsFilePath)
    cards.forEach { println(it) }
}