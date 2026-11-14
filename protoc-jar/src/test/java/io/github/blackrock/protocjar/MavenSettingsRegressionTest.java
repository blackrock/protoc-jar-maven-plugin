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
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MavenSettingsRegressionTest {
    @TempDir Path dir;
    private MavenUtil.MavenSettings settings(String content) throws Exception {
        Path file = dir.resolve("settings.xml");
        Files.writeString(file, "<settings>" + content + "</settings>");
        return MavenUtil.parseMavenSettings(file.toFile());
    }
    @Test public void ignoresInactiveProxy() throws Exception {
        assertNull(settings("<proxies><proxy><active>false</active><host>disabled</host></proxy></proxies>").mProxyHost);
    }
    @Test public void selectsActiveProxyAfterInactiveOne() throws Exception {
        assertEquals("enabled", settings("<proxies><proxy><active>false</active><host>disabled</host></proxy><proxy><active>true</active><host>enabled</host><port>3128</port></proxy></proxies>").mProxyHost);
    }
    @Test public void keepsDefaultActiveProxy() throws Exception {
        assertEquals("default", settings("<proxies><proxy><host>default</host></proxy></proxies>").mProxyHost);
    }
    private String mirror(String url, String of) {
        return "<mirror><url>"+url+"</url><mirrorOf>"+of+"</mirrorOf></mirror>";
    }
    @Test public void respectsCentralExclusion() throws Exception {
        assertNull(settings("<mirrors>"+mirror("https://excluded/", "*,!central")+"</mirrors>").mMirrorUrl);
    }
    @Test public void keepsFirstMatchingWildcard() throws Exception {
        assertEquals("https://first/", settings("<mirrors>"+mirror("https://first/", "*")+mirror("https://second/", "*")+"</mirrors>").mMirrorUrl);
    }
    @Test public void prefersExactCentralMirror() throws Exception {
        assertEquals("https://exact/", settings("<mirrors>"+mirror("https://exact/", "central")+mirror("https://wildcard/", "*")+"</mirrors>").mMirrorUrl);
    }
    @Test public void ignoresHttpOnlyMirrorForHttpsCentral() throws Exception {
        assertNull(settings("<mirrors>"+mirror("http://http-only/", "external:http:*")+"</mirrors>").mMirrorUrl);
    }
}
