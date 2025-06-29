package com.tanimul.android_template_kotlin.db.type_converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tanimul.android_template_kotlin.features.users.domain.model.*

class UserTypeConverters {

    private val gson = Gson()

    // Hair
    @TypeConverter
    fun fromHair(hair: Hair?): String = gson.toJson(hair)

    @TypeConverter
    fun toHair(hairString: String): Hair? =
        gson.fromJson(hairString, object : TypeToken<Hair>() {}.type)

    // Crypto
    @TypeConverter
    fun fromCrypto(crypto: Crypto?): String = gson.toJson(crypto)

    @TypeConverter
    fun toCrypto(cryptoString: String): Crypto? =
        gson.fromJson(cryptoString, object : TypeToken<Crypto>() {}.type)

    // Bank
    @TypeConverter
    fun fromBank(bank: Bank?): String = gson.toJson(bank)

    @TypeConverter
    fun toBank(bankString: String): Bank? =
        gson.fromJson(bankString, object : TypeToken<Bank>() {}.type)

    // Coordinates
    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates?): String = gson.toJson(coordinates)

    @TypeConverter
    fun toCoordinates(coordinatesString: String): Coordinates? =
        gson.fromJson(coordinatesString, object : TypeToken<Coordinates>() {}.type)

    // Address
    @TypeConverter
    fun fromAddress(address: Address?): String = gson.toJson(address)

    @TypeConverter
    fun toAddress(addressString: String): Address? =
        gson.fromJson(addressString, object : TypeToken<Address>() {}.type)

    // Company
    @TypeConverter
    fun fromCompany(company: Company?): String = gson.toJson(company)

    @TypeConverter
    fun toCompany(companyString: String): Company? =
        gson.fromJson(companyString, object : TypeToken<Company>() {}.type)

    // User List (for UserResponse)
    @TypeConverter
    fun fromUserList(users: ArrayList<User>?): String = gson.toJson(users)

    @TypeConverter
    fun toUserList(userString: String): ArrayList<User> =
        gson.fromJson(userString, object : TypeToken<ArrayList<User>>() {}.type)

}
