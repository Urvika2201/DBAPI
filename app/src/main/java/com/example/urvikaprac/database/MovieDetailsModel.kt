package com.example.urvikaprac.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

@Entity(tableName = "movie_details")
@TypeConverters(MovieTypeConverters::class)
data class MovieDetailsModel(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection,
    val budget: Int,
    val genres: List<GenreModel>,
    val homepage: String,
    @PrimaryKey
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
)

data class BelongsToCollection(
    val id: Long,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
)

data class GenreModel(
    val id: Int,
    val name: String,
)

data class ProductionCompany(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String,
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    val name: String,
)

data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    val name: String,
)

class MovieTypeConverters {

    private val gson = Gson()

    @TypeConverter
    fun fromBelongsToCollection(value: BelongsToCollection): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toBelongsToCollection(value: String): BelongsToCollection {
        return gson.fromJson(value, BelongsToCollection::class.java)
    }

    @TypeConverter
    fun fromGenreList(value: List<GenreModel>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toGenreList(value: String): List<GenreModel> {
        val listType = object : TypeToken<List<GenreModel>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromProductionCompanyList(value: List<ProductionCompany>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProductionCompanyList(value: String): List<ProductionCompany> {
        val listType = object : TypeToken<List<ProductionCompany>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromProductionCountryList(value: List<ProductionCountry>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProductionCountryList(value: String): List<ProductionCountry> {
        val listType = object : TypeToken<List<ProductionCountry>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromSpokenLanguageList(value: List<SpokenLanguage>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSpokenLanguageList(value: String): List<SpokenLanguage> {
        val listType = object : TypeToken<List<SpokenLanguage>>() {}.type
        return gson.fromJson(value, listType)
    }
}

