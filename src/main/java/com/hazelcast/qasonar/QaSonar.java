/*
 * Copyright (c) 2008-2015, Hazelcast, Inc. All Rights Reserved.
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

package com.hazelcast.qasonar;

import com.hazelcast.qa.PropertyReader;

import java.io.IOException;

public final class QaSonar {

    private QaSonar() {
    }

    public static void main(String[] args) throws IOException {
        PropertyReader propertyReader = PropertyReader.fromPropertyFile();

        CommandLineOptions commandLineOptions = new CommandLineOptions(args, propertyReader);
        if (!commandLineOptions.init()) {
            return;
        }

        CodeCoverageReader reader = new CodeCoverageReader(propertyReader);
        for (Integer pullRequest : commandLineOptions.getPullRequests()) {
            reader.addPullRequest(pullRequest);
        }

        CodeCoveragePrinter printer = new CodeCoveragePrinter(reader.getTableEntries(), propertyReader);
        printer.run();
    }
}