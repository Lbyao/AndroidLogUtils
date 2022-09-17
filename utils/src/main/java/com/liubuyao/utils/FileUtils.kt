package com.liubuyao.utils

import android.os.Build
import java.io.File
import java.io.FilenameFilter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardOpenOption

object FileUtils {

    /**
     * 文件存在
     * @return 是否存在
     */
    fun fileExists(path: String?): Boolean {
        if (path == null) return false
        val file = getFile(path) ?: return false
        return file.exists()
    }

    /**
     * 文件存在
     * @return 是否存在
     */
    fun fileExists(file: File?): Boolean {
        if (file == null) return false
        return file.exists()
    }

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
                if (append) {
                    file.appendText(content)
                } else {
                    file.writeText(content)
                }
            }
            return true
        } catch (e: IOException) {
            return false
        }
    }

    /**
     * 删除文件
     */
    fun deleteFile(path: String?): Boolean {
        return getFile(path)?.delete() ?: false
    }

    /**
     * 获取文件列表
     */
    fun getFileListByFile(path: String?, filterFilename: String, suffix: String): Array<String>? {
        val file = getFile(path)
        if (file != null) {
            val list = if (file.isFile) {
                val parentFile = file.parentFile
                parentFile?.list(object : FilenameFilter {
                    override fun accept(dir: File?, name: String?): Boolean {
                        if (name != null) {
                            if (name.contains(filterFilename) && name.endsWith(".$suffix")) {
                                return true
                            }
                        }
                        return false
                    }
                })
            } else {
                file.list(object : FilenameFilter {
                    override fun accept(dir: File?, name: String?): Boolean {
                        if (name != null) {
                            if (name.contains(filterFilename) && name.endsWith(".$suffix")) {
                                return true
                            }
                        }
                        return false
                    }

                })
            }
            return list
        }
        return null
    }

}