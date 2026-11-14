/*
 * Copyright 2014 protoc-jar developers
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.blackrock.protocjar;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ProtocDownloadRegressionTest {
    @TempDir Path dir;
    @Test public void failsWhenDestinationCannotBeCreated() throws Exception {
        Path source = Files.writeString(dir.resolve("source"), "compiler");
        Path blocked = Files.writeString(dir.resolve("blocked"), "regular file");
        assertThrows(IOException.class, () -> Protoc.downloadFile(
                new URLSpec(source.toUri().toURL()), blocked.resolve("compiler").toFile(), 0));
    }
    @Test public void downloadsIntoNestedDestination() throws Exception {
        Path source = Files.writeString(dir.resolve("source"), "compiler");
        File destination = dir.resolve("nested/compiler").toFile();
        assertEquals(destination, Protoc.downloadFile(new URLSpec(source.toUri().toURL()), destination, 0));
        assertEquals("compiler", Files.readString(destination.toPath()));
    }
    @Test public void preservesCachedFileAfterRefreshFailure() throws Exception {
        File destination = Files.writeString(dir.resolve("compiler"), "cached").toFile();
        destination.setLastModified(1);
        assertEquals(destination, Protoc.downloadFile(new URLSpec(dir.resolve("missing").toUri().toURL()), destination, 1));
        assertEquals("cached", Files.readString(destination.toPath()));
    }
}
