class Card(
    val name: String,
    val northSpikes: Int,
    val eastSpikes: Int,
    val southSpikes: Int,
    val westSpikes: Int
) {

    override fun toString(): String {
        return name
    }

    fun beats(other: Card, direction: Direction): Boolean {
        return when (direction) {
            Direction.NORTH -> this.northSpikes > other.southSpikes
            Direction.EAST -> this.eastSpikes > other.westSpikes
            Direction.SOUTH -> this.southSpikes > other.northSpikes
            Direction.WEST -> this.westSpikes > other.eastSpikes
        }
    }
}