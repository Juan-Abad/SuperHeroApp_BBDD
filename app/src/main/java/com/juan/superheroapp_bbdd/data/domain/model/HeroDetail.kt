package com.juan.superheroapp_bbdd.data.domain.model

data class HeroDetail (
    val name: String,
    val powerstats: PowerStatsHeroDetail,
    val image: ImageHeroDetail,
    val biography: HeroDetailBiography,
    val appearance: HeroDetailAppearance
)

data class PowerStatsHeroDetail(
    val intelligence: String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String
)

data class ImageHeroDetail(val url:String)

data class HeroDetailBiography(
    val fullName:String,
    val publisher:String,
    val birthplace:String,
    val starting:String,
    val aliases:Array<String>
)

data class HeroDetailAppearance(
    val race:String,
    val height:Array<String>,
    val weight:Array<String>
)