package com.hmodule.adaptation

import android.util.AtomicFile
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

/** Match Android/POSIX rename replacement on Windows, where File.renameTo cannot overwrite. */
@Implements(AtomicFile::class)
class FileRenameShadow {
    companion object {
        @Implementation
        @JvmStatic
        fun rename(source: File, target: File) {
            Files.move(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING)
        }
    }
}
