
/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package com.wtwd.common.codec.textline;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import org.apache.mina.filter.codec.textline.TextLineDecoder;

/**
 * A delimiter which is appended to the end of a text line, such as
 * <tt>CR/LF</tt>. This class defines default delimiters for various
 * OS :
 * <ul>
 * <li><b>Unix/Linux</b> : LineDelimiter.UNIX ("\n")</li>
 * <li><b>Windows</b> : LineDelimiter.WINDOWS ("\r\n")</li>
 * <li><b>MAC</b> : LineDelimiter.MAC ("\r")</li>
 * </ul>
 *
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class LineDelimiterII {
    /** the line delimiter constant of the current O/S. */
    public static final LineDelimiterII DEFAULT;

    /** Compute the default delimiter on he current OS */
    static {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintWriter out = new PrintWriter(bout, true);
        out.println();
        DEFAULT = new LineDelimiterII(new String(bout.toByteArray()));
    }

    /**
     * A special line delimiter which is used for auto-detection of
     * EOL in {@link TextLineDecoder}.  If this delimiter is used,
     * {@link TextLineDecoder} will consider both  <tt>'\r'</tt> and
     * <tt>'\n'</tt> as a delimiter.
     */
    public static final LineDelimiterII AUTO = new LineDelimiterII("");

    /**
     * The CRLF line delimiter constant (<tt>"\r\n"</tt>)
     */
    public static final LineDelimiterII CRLF = new LineDelimiterII("\r\n");

    /**
     * The line delimiter constant of UNIX (<tt>"\n"</tt>)
     */
    public static final LineDelimiterII UNIX = new LineDelimiterII("\n");

    /**
     * The line delimiter constant of MS Windows/DOS (<tt>"\r\n"</tt>)
     */
    public static final LineDelimiterII WINDOWS = CRLF;

    /**
     * The line delimiter constant of Mac OS (<tt>"\r"</tt>)
     */
    public static final LineDelimiterII MAC = new LineDelimiterII("\r");

    /**
     * The line delimiter constant for NUL-terminated text protocols
     * such as Flash XML socket (<tt>"\0"</tt>)
     */
    public static final LineDelimiterII NUL = new LineDelimiterII("\0");

    /** Stores the selected Line delimiter */
    private final String value;

    /**
     * Creates a new line delimiter with the specified <tt>value</tt>.
     * 
     * @param value The new Line Delimiter
     */
    public LineDelimiterII(String value) {
        if (value == null) {
            throw new IllegalArgumentException("delimiter");
        }

        this.value = value;
    }

    /**
     * @return the delimiter string.
     */
    public String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof LineDelimiterII)) {
            return false;
        }

        LineDelimiterII that = (LineDelimiterII) o;

        return this.value.equals(that.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (value.length() == 0) {
            return "delimiter: auto";
        } else {
            StringBuilder buf = new StringBuilder();
            buf.append("delimiter:");

            for (int i = 0; i < value.length(); i++) {
                buf.append(" 0x");
                buf.append(Integer.toHexString(value.charAt(i)));
            }

            return buf.toString();
        }
    }
}

