package edu.utsa.cs3773.bookworkburrow.model;

import java.io.IOException;

public interface IOStream<T> {

    T read() throws IOException;

    void write(T _type) throws IOException;

} // interface IOStream
