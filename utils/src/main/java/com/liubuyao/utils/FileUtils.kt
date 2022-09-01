package com.liubuyao.utils

import android.os.Build
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardOpenOption

object FileUtils {

    /**
     * 通过路径获取文件
     * @return 文件
     */
    fun getFile(filePath: String?): File? {
        return if (MyUtils.isSpace(filePath)) null else File(filePath)
    }

    /**
     * 创建文件
     * @return 是否成功
     */
    fun createFile(filePath: String?): Boolean {
        return createFile(getFile(filePath))
    }

    /**
     * 创建文件
     * @return 是否成功
     */
    fun createFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        return file.createNewFile()
    }

    /**
     * 写文本到文件内
     *
     * @return 是否成功
     */
    fun writeToFile(filePath: String?, content: String?, append: Boolean): Boolean {
        return writeToFile(getFile(filePath), content, append)
    }

    /**
     * 写文本到文件内
     *
     * @return 是否成功
     */
    fun writeToFile(file: File?, content: String?, append: Boolean = false): Boolean {
        if (file == null || content == null) return false
        if (!createFile(file)) {
            return false
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (append) {
                    Files.write(file.toPath(), content.toByteArray(), StandardOpenOption.APPEND)
                } else {
                    Files.write(file.toPath(), content.toByteArray(), StandardOpenOption.CREATE_NEW)
                }
            } else {
                file.bufferedWriter().use {
                    if (append) {
                        it.append(content)
                    } else {
                        it.write(content)
                    }
                }
            }
            return true
        } catch (e: IOException) {
            return false
        }
    }


}