package com.github.tools.data

import android.os.Environment

object constant {

    private val data = Environment.getDataDirectory()

    private val dataPath = "$data/data/"

    private val packName = Context.getContext().packageName

    //data path
    val dataPaths = dataPath + packName
}