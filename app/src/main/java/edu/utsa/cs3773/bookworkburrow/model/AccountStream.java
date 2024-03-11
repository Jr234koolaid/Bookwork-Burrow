// app\main\java\..\model\AccountStream.java

package edu.utsa.cs3773.bookworkburrow.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AccountStream implements IOStream {
    @Override
    public void readFrom(String _path) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(_path));
    }

    @Override
    public void writeTo(String _path) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(_path));
    }
}
