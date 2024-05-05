package org.example

import kotlin.random.Random

class MontyHallChoice(
    private val keepChoice: Boolean
) {

    private val doors: Map<DoorNumber, DoorType>
    private val doorRandom = Random.nextFloat()
    val win: Boolean

    companion object {
        private const val ONE_THIRD = 1f/3f
        private const val TWO_THIRD = 2f/3f
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
            doorRandom >= ONE_THIRD && doorRandom < TWO_THIRD -> {
                mapOf(
                    DoorNumber.FIRST to DoorType.GOAT,
                    DoorNumber.SECOND to DoorType.CAR,
                    DoorNumber.THIRD to DoorType.GOAT,
                )
            }
            doorRandom >= TWO_THIRD && doorRandom < 1f -> {
                mapOf(
                    DoorNumber.FIRST to DoorType.GOAT,
                    DoorNumber.SECOND to DoorType.GOAT,
                    DoorNumber.THIRD to DoorType.CAR,
                )
            }
            else -> throw IllegalStateException("impossible")
        }
        // симулируем выбор(ы) игрока
        win = calcWin()
    }

    private fun calcWin(): Boolean {
        // делаем первый выбор
        val firstChoice = makeChoice()
        return if (keepChoice) {
            // решаем сохранить выбор
            doors[firstChoice] == DoorType.CAR
        } else {
            // меняем выбор
            val secondChoice = changeChoice(firstChoice)
            doors[secondChoice] == DoorType.CAR
        }
    }

    private fun makeChoice(): DoorNumber {
        val random = Random.nextFloat()
        return when {
            random < ONE_THIRD -> DoorNumber.FIRST
            random >= ONE_THIRD && random < TWO_THIRD -> DoorNumber.SECOND
            random >= TWO_THIRD && random < 1f -> DoorNumber.THIRD
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

        // выбираем оставшуюся дверь, т.е. "меняем выбор"
        return remainingDoors
            .filterKeys { it != sheepDoorNumber }
            .entries
            .first()
            .key
    }
}