/**
 * Copyright 2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.swarm.spi.meta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author Juan Gonzalez
 *
 */
public class ZipPathSource extends PathSource {

    private ZipEntry zipEntry;
    private ZipFile zipFile;

    public ZipPathSource(ZipFile file, ZipEntry entry) {
        super(null, new File(entry.getName()).toPath());
        zipFile = file;
        zipEntry = entry;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return zipFile.getInputStream(zipEntry);
    }

}