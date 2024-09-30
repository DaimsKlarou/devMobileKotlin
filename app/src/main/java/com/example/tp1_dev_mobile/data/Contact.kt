package com.example.tp1_dev_mobile.data

import android.os.Parcel
import android.os.Parcelable

class Contact(
    var name: String,
    var prenoms: String,
    var numero: String,
    var profession: String,
    var id: Int = -1,
    var image : ByteArray = ByteArray(0),
    var favorite: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.createByteArray() ?: ByteArray(0),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(prenoms)
        parcel.writeString(numero)
        parcel.writeString(profession)
        parcel.writeInt(id)
        parcel.writeByteArray(image)
        parcel.writeByte(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}
