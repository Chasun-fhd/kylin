/*
 * Copyright 2013-2014 eBay Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kylinolap.cube.cli;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import com.kylinolap.common.util.JsonUtil;
import com.kylinolap.dict.DictionaryInfo;
import com.kylinolap.dict.DictionaryInfoSerializer;

public class DumpDictionaryCLI {

    public static void main(String[] args) throws IOException {
        for (String path : args) {
            dump(new File(path));
        }
    }
    
    public static void dump(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                dump(c);
            return;
        }
        
        if (f.getName().endsWith(".dict")) {
            DictionaryInfoSerializer ser = new DictionaryInfoSerializer();
            DictionaryInfo dictInfo = ser.deserialize(new DataInputStream(new FileInputStream(f)));

            System.out.println("============================================================================");
            System.out.println("File: " + f.getAbsolutePath());
            System.out.println(new Date(dictInfo.getLastModified()));
            System.out.println(JsonUtil.writeValueAsIndentString(dictInfo));
            dictInfo.getDictionaryObject().dump(System.out);
            System.out.println();
        }
    }
}
