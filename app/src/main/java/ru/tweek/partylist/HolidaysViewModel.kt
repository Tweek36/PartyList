package ru.tweek.partylist

import androidx.lifecycle.ViewModel

class HolidaysViewModel : ViewModel() {
    var holidaysList: List<Holiday> = emptyList()
}