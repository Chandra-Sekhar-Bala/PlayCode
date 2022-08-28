package com.chandra.sekhar.playcode.codeScreen.repo.network

data class ProgramModel(
    var script: String,
    var language: String,
    var stdin : String = "",
    var versionIndex: String =  "0",
    var clientId: String =  "81170d0edc1f4ba0bcf5aeb23dde8326",
    var clientSecret: String = "856456e4b3b2816f460afcbe57ca74f81d9638e547428d907c6c20639ca958c4"
)