package com.chaos.imageloader.core.download;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chaossss on 2015/9/25.
 */
public class ContentLengthInputStream extends InputStream {
    private final int length;
    private final InputStream stream;

    public ContentLengthInputStream(InputStream stream, int length){
        this.length = length;
        this.stream = stream;
    }

    @Override
    public int available() throws IOException {
        return length;
    }

    @Override
    public synchronized void reset() throws IOException {
        stream.reset();
    }

    @Override
    public boolean markSupported() {
        return stream.markSupported();
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        return stream.read(buffer);
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        return stream.read(buffer, byteOffset, byteCount);
    }

    @Override
    public long skip(long byteCount) throws IOException {
        return stream.skip(byteCount);
    }

    @Override
    public void close() throws IOException {
        stream.close();
    }

    @Override
    public void mark(int readlimit) {
        stream.mark(readlimit);
    }

    @Override
    public int read() throws IOException {
        return stream.read();
    }
}
