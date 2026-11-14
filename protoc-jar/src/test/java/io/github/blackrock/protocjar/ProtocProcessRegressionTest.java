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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class ProtocProcessRegressionTest {
    private String javaCommand() {
        return new File(System.getProperty("java.home"), "bin/java" +
                (System.getProperty("os.name").startsWith("Windows") ? ".exe" : "")).getPath();
    }
    @Test public void drainsOutputBeforeReturning() throws Exception {
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        OutputStream slowOutput = new OutputStream() {
            public void write(int value) throws IOException { captured.write(value); }
            public void write(byte[] data, int offset, int length) throws IOException {
                try { Thread.sleep(10000); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); throw new IOException(e); }
                captured.write(data, offset, length);
            }
        };
        assertEquals(0, Protoc.runProtoc(javaCommand(), Arrays.asList("--version"),
                slowOutput, new ByteArrayOutputStream()));
        assertTrue(captured.size() > 0, "output must be drained when runProtoc returns");
    }
    @Test public void reportsLauncherFailureAsNonzeroExit() throws Exception {
        Process child = new ProcessBuilder(javaCommand(), "-Dos.name=UnsupportedOS", "-cp",
                new File(Protoc.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath(),
                Protoc.class.getName(), "--version").redirectErrorStream(true).start();
        String output = new String(child.getInputStream().readAllBytes());
        assertTrue(child.waitFor(20, TimeUnit.SECONDS));
        assertTrue(output.contains("Exception"), output);
        assertNotEquals(0, child.exitValue(), "launcher exceptions must fail the command");
    }
}
