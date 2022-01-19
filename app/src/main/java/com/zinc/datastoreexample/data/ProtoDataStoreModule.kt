package com.zinc.datastoreexample.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.zinc.datastoreexample.model.UsersInfo
import java.io.InputStream
import java.io.OutputStream

object UserSerializable : Serializer<UsersInfo> {
    override val defaultValue: UsersInfo = UsersInfo.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UsersInfo {
        try {
            return UsersInfo.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: UsersInfo,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.userDataStore: DataStore<UsersInfo> by dataStore(
    fileName = "users_info.pb",
    serializer = UserSerializable
)
