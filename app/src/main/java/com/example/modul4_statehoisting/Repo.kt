package com.example.modul4_statehoisting

import kotlinx.coroutines.delay
import kotlin.random.Random

class Repo {
    companion object {
        suspend fun getData(): Int {
            delay(2000) // Simulasi waktu delay jaringan (2 detik)
            return Random.nextInt(100, 1000)
        }
    }
}