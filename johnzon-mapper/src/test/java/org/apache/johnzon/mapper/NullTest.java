/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.johnzon.mapper;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class NullTest {

    @Test
    public void writeNullObjectDefault() {
        final StringWriter writer = new StringWriter();
        new MapperBuilder().build().writeObject(new NullObject(), writer);
        assertEquals("{\"emptyArray\":[]}", writer.toString());
    }

    @Test
    public void writeNullObjectDefaultMap() {
        final StringWriter writer = new StringWriter();

        final String expectedJson = "{\"map\":{\"key1\":\"val1\",\"null\":\"val3\"}}";

        final Comparator<String> attributeOrder = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                
                if(o1 == null) {
                    o1 = "null";
                }
                
                if(o2 == null) {
                    o2 = "null";
                }
                
                return expectedJson.indexOf(o1) - expectedJson.indexOf(o2);
            }
        };

        new MapperBuilder().setAttributeOrder(attributeOrder).build().writeObject(new NullObjectWithMap(), writer);
        assertEquals(expectedJson, writer.toString());
    }

    @Test
    public void writeNullObjectDefaultMapAllowNull() {
        final StringWriter writer = new StringWriter();

        final String expectedJson = "{\"map\":{\"key1\":\"val1\",\"key2\":null,\"null\":\"val3\"}}";

        final Comparator<String> attributeOrder = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                
                if(o1 == null) {
                    o1 = "null";
                }
                
                if(o2 == null) {
                    o2 = "null";
                }
                
                return expectedJson.indexOf(o1) - expectedJson.indexOf(o2);
            }
        };

        new MapperBuilder().setSkipNull(false).setAttributeOrder(attributeOrder).build().writeObject(new NullObjectWithMap(), writer);
        assertEquals(expectedJson, writer.toString());
    }

    @Test
    public void writeNullObjectAllowNull() {
        final StringWriter writer = new StringWriter();

        final String expectedJson = "{\"stringIsnull\":null,\"integerIsnull\":null,\"nullArray\":null,\"emptyArray\":[]}";

        final Comparator<String> attributeOrder = new Comparator<String>() {
            @Override
            public int compare(final String o1, final String o2) {
                return expectedJson.indexOf(o1) - expectedJson.indexOf(o2);
            }
        };

        new MapperBuilder().setSkipNull(false).setAttributeOrder(attributeOrder).build().writeObject(new NullObject(), writer);
        assertEquals(expectedJson, writer.toString());
    }

    @Test
    public void writeNullObjectAllowNullSkipEmptyArray() {
        final StringWriter writer = new StringWriter();

        final String expectedJson = "{\"stringIsnull\":null,\"integerIsnull\":null,\"nullArray\":null}";

        final Comparator<String> attributeOrder = new Comparator<String>() {
            @Override
            public int compare(final String o1, final String o2) {
                return expectedJson.indexOf(o1) - expectedJson.indexOf(o2);
            }
        };

        new MapperBuilder().setSkipNull(false).setSkipEmptyArray(true).setAttributeOrder(attributeOrder).build()
                .writeObject(new NullObject(), writer);
        assertEquals(expectedJson, writer.toString());
    }

    @Test
    public void writeNullObjectSkipAll() {
        final StringWriter writer = new StringWriter();
        new MapperBuilder().setSkipNull(true).setSkipEmptyArray(true).build().writeObject(new NullObject(), writer);
        assertEquals("{}", writer.toString());
    }

    public static class NullObjectWithMap {

        private Map map = new LinkedHashMap();

        NullObjectWithMap() {
            super();
            map.put("key1", "val1");
            map.put("key2", null);
            map.put(null, "val3");
        }

        public Map getMap() {
            return map;
        }

        public void setMap(final Map map) {
            this.map = map;
        }

    }

    public static class NullObject {

        private String stringIsnull;
        private Integer integerIsnull;
        private String[] nullArray;
        private String[] emptyArray = new String[0];

        public String[] getNullArray() {
            return nullArray;
        }

        public void setNullArray(final String[] nullArray) {
            this.nullArray = nullArray;
        }

        public String[] getEmptyArray() {
            return emptyArray;
        }

        public void setEmptyArray(final String[] emptyArray) {
            this.emptyArray = emptyArray;
        }

        public String getStringIsnull() {
            return stringIsnull;
        }

        public void setStringIsnull(final String stringIsnull) {
            this.stringIsnull = stringIsnull;
        }

        public Integer getIntegerIsnull() {
            return integerIsnull;
        }

        public void setIntegerIsnull(final Integer integerIsnull) {
            this.integerIsnull = integerIsnull;
        }

    }

}
