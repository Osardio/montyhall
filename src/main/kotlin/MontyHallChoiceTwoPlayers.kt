package org.example

import kotlin.random.Random

class MontyHallChoiceTwoPlayers {

    private val doors: Map<DoorNumber, DoorType>
    private val doorRandom = Random.nextFloat()
    val keepPlayerWin: Boolean
    val changePlayerWin: Boolean

    companion object {
        private const val ONE_THIRD = 1f/3f
        private const val TWO_THIRDS = 2f/3f
    }

    init {
        // при помощи рандома распределяем призы за дверьми
        doors = when {
            doorRandom < ONE_THIRD -> {
                mapOf(
                    DoorNumber.FIRST to DoorType.CAR,
                    DoorNumber.SECOND to DoorType.GOAT,
                    DoorNumber.THIRD to DoorType.GOAT,
                )
            }
            doorRandom >= ONE_THIRD && doorRandom < TWO_THIRDS -> {
                mapOf(
                    DoorNumber.FIRST to DoorType.GOAT,
                    DoorNumber.SECOND to DoorType.CAR,
                    DoorNumber.THIRD to DoorType.GOAT,
                )
            }
            doorRandom >= TWO_THIRDS && doorRandom < 1f -> {
                mapOf(
                    DoorNumber.FIRST to DoorType.GOAT,
                    DoorNumber.SECOND to DoorType.GOAT,
                    DoorNumber.THIRD to DoorType.CAR,
                )
            }
            else -> throw IllegalStateException("impossible")
        }
        // симулируем выбор(ы) игрока
        val firstChoice = makeFirstChoice()

        // игрок 1 сохраняет свой выбор
        keepPlayerWin = doors[firstChoice] == DoorType.CAR

        // игрок 2 меняет свой выбор
        val secondChoice = changeChoice(firstChoice)
        changePlayerWin = doors[secondChoice] == DoorType.CAR
    }

    private fun makeFirstChoice(): DoorNumber {
        val random = Random.nextFloat()
        return when {
            random < ONE_THIRD -> DoorNumber.FIRST
            random >= ONE_THIRD && random < TWO_THIRDS -> DoorNumber.SECOND
            random >= TWO_THIRDS && random < 1f -> DoorNumber.THIRD
            else -> throw IllegalStateException()
        }
    }

    private fun changeChoice(firstChoice: DoorNumber): DoorNumber {
        // берём оставшиеся две двери
        val remainingDoors = doors.filter { it.key != firstChoice }

        // "открываем" дверь с козой
        val sheepDoorNumber = if (remainingDoors.containsValue(DoorType.CAR)) {
            // если за одной из оставшихся дверей есть машина, открываем первую дверь с козой
            remainingDoors.entries.first { it.value == DoorType.GOAT }.key
        } else {
            // иначе, среди обеих оставшихся дверей будут козы, поэтому выбираем из них случайную
            if (Random.nextBoolean()) {
                remainingDoors.entries.first().key
            } else {
                remainingDoors.entries.last().key
            }
        }

        // выбираем оставшуюся дверь, т.е. "меняем" изначальный выбор
        return remainingDoors
            .filterKeys { it != sheepDoorNumber }
            .entries
            .first()
            .key
    }
}