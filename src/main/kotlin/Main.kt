package org.example

fun main() {
    /*
    Википедия и некоторые математики говорят, что при изменении выбора двери в парадоксе Монти-Холла
    шанс выигрыша повышается. Данный код проверяет эту теорию.
    */

    // симулируем некоторое количество выборов для каждой стратегии
    val simCount = 100000

    val choicesWithKeepStrategy = List(simCount) {
        MontyHallChoice(keepChoice = true)
    }
    val choicesWithChangeStrategy = List(simCount) {
        MontyHallChoice(keepChoice = false)
    }

    // вычисляем win rate для каждой из групп
    val keepStrategyWins = choicesWithKeepStrategy.count { it.win }
    val changeStrategyWins = choicesWithChangeStrategy.count { it.win }

    println("KEEP   strategy wins: $keepStrategyWins (${ keepStrategyWins / simCount.toFloat() * 100 }%)")
    println("CHANGE strategy wins: $changeStrategyWins (${ changeStrategyWins / simCount.toFloat() * 100 }%)")

    /*
    Итоги:
    KEEP   strategy wins: 33497 (33.497%)
    CHANGE strategy wins: 66810 (66.81%)

    Получается что выбирать другую дверь - это всегда правильное решение?

    Мне не верится, что стратегия изменения выбора работает.
    Я подумал, что возможно дело в рандоме. Ведь для симуляций ситуации, когда мы меняем выбор,
    и когда мы не меняем его, используется разный рандом для распределения призов и выборов.
    Поэтому я решил одновременно симулировать выборы для условных "двух игроков" в одной и той же ситуации.
    Первый игрок не меняет выбор, а второй - меняет.
    */

    println()

    val choicesForTwoPlayers = List(simCount) {
        MontyHallChoiceTwoPlayers()
    }

    // вычисляем win rate для каждого из игроков
    val keepPlayerWins = choicesForTwoPlayers.count { it.keepPlayerWin }
    val changePlayerWins = choicesForTwoPlayers.count { it.changePlayerWin }

    println("KEEP   player wins: $keepPlayerWins (${ keepPlayerWins / simCount.toFloat() * 100 }%)")
    println("CHANGE player wins: $changePlayerWins (${ changePlayerWins / simCount.toFloat() * 100 }%)")

    /*
    И даже так, игрок который меняет выбор, всё равно побеждает чаще.
    KEEP   player wins: 33300 (33.3%)
    CHANGE player wins: 66700 (66.7%)
    */
}