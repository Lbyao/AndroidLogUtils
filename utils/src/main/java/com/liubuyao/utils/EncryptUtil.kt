package com.liubuyao.utils

import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 加解密工具类
 */
object EncryptUtil {

    fun base64Encode(input: String): ByteArray? {
        return base64Encode(input.toByteArray())
    }

    fun base64Encode(input: ByteArray?): ByteArray? {
        return if (input == null || input.isEmpty()) ByteArray(0) else Base64.encode(
            input,
            Base64.NO_WRAP
        )
    }

    fun base64Encode2String(input: ByteArray?): String? {
        return if (input == null || input.isEmpty()) "" else Base64.encodeToString(
            input,
            Base64.NO_WRAP
        )
    }

    fun base64Decode(input: String?): ByteArray? {
        return if (input == null || input.isEmpty()) ByteArray(0) else Base64.decode(
            input,
            Base64.NO_WRAP
        )
    }

    fun base64Decode(input: ByteArray?): ByteArray? {
        return if (input == null || input.isEmpty()) ByteArray(0) else Base64.decode(
            input,
            Base64.NO_WRAP
        )
    }

    fun encryptMD5ToString(data: String?): String? {
        return if (data.isNullOrEmpty() || data.isEmpty()) "" else encryptMD5ToString(data.toByteArray())
    }

    fun encryptMD5ToString(data: String?, salt: String?): String? {
        if (data.isNullOrEmpty() && salt.isNullOrEmpty()) return ""
        if (salt.isNullOrEmpty()) return MyUtils.bytes2HexString(encryptMD5(data!!.toByteArray()))
        return if (data.isNullOrEmpty()) MyUtils.bytes2HexString(encryptMD5(salt.toByteArray())) else MyUtils.bytes2HexString(
            encryptMD5((data + salt).toByteArray())
        )
    }

    fun encryptMD5ToString(data: ByteArray?): String {
        return MyUtils.bytes2HexString(encryptMD5(data)) ?: ""
    }

    fun encryptMD5ToString(data: ByteArray?, salt: ByteArray?): String? {
        if (data == null && salt == null) return ""
        if (salt == null) return MyUtils.bytes2HexString(encryptMD5(data))
        if (data == null) return MyUtils.bytes2HexString(encryptMD5(salt))
        val dataSalt = ByteArray(data.size + salt.size)
        System.arraycopy(data, 0, dataSalt, 0, data.size)
        System.arraycopy(salt, 0, dataSalt, data.size, salt.size)
        return MyUtils.bytes2HexString(encryptMD5(dataSalt))
    }


    fun encryptMD5(data: ByteArray?): ByteArray? {
        return hashTemplate(data, "MD5")
    }

    fun hashTemplate(data: ByteArray?, algorithm: String?): ByteArray? {
        return if (data == null || data.isEmpty() || algorithm.isNullOrEmpty()) null else try {
            val md: MessageDigest = MessageDigest.getInstance(algorithm)
            md.update(data)
            md.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }
    }


    fun encryptAESToBase64(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String?,
        iv: ByteArray?
    ): ByteArray? {
        return base64Encode(encryptAES(data, key, transformation, iv))
    }

    fun encryptAESToHexString(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String?,
        iv: ByteArray?
    ): String? {
        return MyUtils.bytes2HexString(encryptAES(data, key, transformation, iv))
    }


    fun encryptAES(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String?,
        iv: ByteArray?
    ): ByteArray? {
        return symmetricTemplate(data, key, "AES", transformation, iv, true)
    }

    fun decryptAES(
        data: ByteArray?,
        key: ByteArray?,
        transformation: String?,
        iv: ByteArray?
    ): ByteArray? {
        return symmetricTemplate(data, key, "AES", transformation, iv, false)
    }

    private fun symmetricTemplate(
        data: ByteArray?,
        key: ByteArray?,
        algorithm: String,
        transformation: String?,
        iv: ByteArray?,
        isEncrypt: Boolean
    ): ByteArray? {
        return if (data == null || data.isEmpty() || key == null || key.isEmpty()) null else try {
            val secretKey = if ("DES" == algorithm) {
                val desKey = DESKeySpec(key)
                val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance(algorithm)
                keyFactory.generateSecret(desKey)
            } else {
                SecretKeySpec(key, algorithm)
            }
            val cipher: Cipher = Cipher.getInstance(transformation)
            if (iv == null || iv.isEmpty()) {
                cipher.init(if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE, secretKey)
            } else {
                val params: AlgorithmParameterSpec = IvParameterSpec(iv)
                cipher.init(
                    if (isEncrypt) Cipher.ENCRYPT_MODE else Cipher.DECRYPT_MODE,
                    secretKey,
                    params
                )
            }
            cipher.doFinal(data)
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }


}