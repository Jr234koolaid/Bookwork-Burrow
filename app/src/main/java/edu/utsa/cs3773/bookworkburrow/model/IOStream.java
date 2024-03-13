// app\main\java\..\model\IOStream.java

package edu.utsa.cs3773.bookworkburrow.model;

import java.io.FileNotFoundException;

public interface IOStream {

    public void readFrom(String _path) throws FileNotFoundException;

    public void writeTo(String _path) throws FileNotFoundException;
}
