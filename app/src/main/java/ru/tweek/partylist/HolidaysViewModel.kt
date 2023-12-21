package ru.tweek.partylist

import androidx.lifecycle.ViewModel

class HolidaysViewModel : ViewModel() {
    var holidaysList: List<Holiday> = emptyList()
    var showDate: Boolean = true
    var currentHoliday: String? = null
}