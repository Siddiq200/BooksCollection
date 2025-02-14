// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.orgJetbrainsKotlinKapt) apply false
    alias(libs.plugins.kotlinParcelize) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.navigation.safeargs.kotlin) apply false
}