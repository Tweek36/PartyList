package ru.tweek.partylist

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.util.Calendar

class Holiday(
    val name: String,
    var description: String,
    var date: Calendar
) {
    companion object {
        private val gson = Gson()

        fun getHolidaysFile(context: Context): File {
            val file = File(context.filesDir, "holidays.json")
            if (!file.exists()) {
                file.createNewFile()
                file.writeText("[]")
            }
            if (file.readText() == "") {
                file.writeText("[]")
            }
            return file
        }

        fun get(context: Context, name: String): Holiday? {
            val holidaysFile = getHolidaysFile(context)
            val json = holidaysFile.readText()
            val holidaysList = gson.fromJson(json, Array<Holiday>::class.java)
            return holidaysList.find { it.name == name }
        }

        fun all(context: Context): List<Holiday> {
            val holidaysFile = getHolidaysFile(context)
            if (holidaysFile.exists()) {
                val json = holidaysFile.readText()
                return gson.fromJson(json, Array<Holiday>::class.java).toList()
            }
            return emptyList()
        }
    }

    fun save(context: Context) {
        val holidaysFile = getHolidaysFile(context)
        val holidaysList = if (holidaysFile.exists()) {
            val json = holidaysFile.readText()
            gson.fromJson(json, Array<Holiday>::class.java).toMutableList()
        } else {
            mutableListOf()
        }

        val existingHoliday = holidaysList.find { it.name == name }
        if (existingHoliday != null) {
            existingHoliday.description = description
            existingHoliday.date = date
        } else {
            holidaysList.add(this)
        }

        val updatedJson = gson.toJson(holidaysList)
        holidaysFile.writeText(updatedJson)
    }

    fun delete(context: Context) {
        val holidaysFile = getHolidaysFile(context)
        val holidaysList = if (holidaysFile.exists()) {
            val json = holidaysFile.readText()
            gson.fromJson(json, Array<Holiday>::class.java).toMutableList()
        } else {
            mutableListOf()
        }

        val existingHoliday = holidaysList.find { it.name == name }
        if (existingHoliday != null) {
            holidaysList.remove(existingHoliday)
            val updatedJson = gson.toJson(holidaysList)
            holidaysFile.writeText(updatedJson)
        }
    }
}
