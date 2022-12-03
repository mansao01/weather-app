package com.mansao.weatherapp.data.network.response

import com.google.gson.annotations.SerializedName

data class QuakeResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: Any
)

data class Data(

	@field:SerializedName("datetime")
	val datetime: String,

	@field:SerializedName("potensi")
	val potensi: String,

	@field:SerializedName("dirasakan")
	val dirasakan: String,

	@field:SerializedName("lintang")
	val lintang: String,

	@field:SerializedName("jam")
	val jam: String,

	@field:SerializedName("coordinates")
	val coordinates: String,

	@field:SerializedName("magnitude")
	val magnitude: String,

	@field:SerializedName("tanggal")
	val tanggal: String,

	@field:SerializedName("kedalaman")
	val kedalaman: String,

	@field:SerializedName("wilayah")
	val wilayah: String,

	@field:SerializedName("shakemap")
	val shakemap: String,

	@field:SerializedName("bujur")
	val bujur: String
)
