// app\src\main\java\..\model\Database

package edu.utsa.cs3773.bookworkburrow.model;

public class Database
{
    private static Database m_database;

    public static Database GetDatabase()
    {
        if (m_database == null)
        {
            m_database = new Database();

        } return m_database;
    }

} // class Database
