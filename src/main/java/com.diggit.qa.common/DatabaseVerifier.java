package com.diggit.qa.common;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Azeez on 26/09/2016.
 */
public class DatabaseVerifier {



    public static Connection createConnection(String url, String userName, String password) throws ClassNotFoundException, SQLException {

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }catch (Exception e){
            e.printStackTrace();
        }


            return DriverManager.getConnection(url, userName, password);

    }

    public static List<String> list(ResultSet rs) throws SQLException {
        List<String> results = new ArrayList<String>();
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                int numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<String, String>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(name, value);
                        results.add(value);

                    }

                }
            }
        } finally {
            close(rs);
        }
        return results;
    }

    public static List<Map<String, String>> map(ResultSet rs) throws SQLException {
        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                int numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<String, String>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        String value = rs.getString(i);
                        row.put(name, value);
                    }
                    results.add(row);
                }
            }
        } finally {
            close(rs);
        }
        return results;
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void verifyDiggitTitleIdColumnNotNullOrZero(){
        ResultSet resultSet = null;
        Statement statement = null;
        try{
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT diggit_title_id FROM mm_titles");
            while (resultSet.next()){
                long diggit_title_id = resultSet.getInt("diggit_title_id");
                if(diggit_title_id == 0){
                    TextFileWriter.writeLineToFileWithOutOverWrite(diggit_title_id + "\n");
                }else{
                    TextFileWriter.writeLineToFileWithOutOverWrite(diggit_title_id + "\n");
                }
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }
        close(resultSet);
    }

    public static String getIMDBId(){
        ResultSet resultSet = null;
        Statement statement = null;
        String imdbId = "";
        try{
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT * FROM diggit_titles LIMIT  5");
            while (resultSet.next()){
                imdbId = resultSet.getString("imdb_id");
                break;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        close(resultSet);

        return imdbId;
    }

    public static List<String> getIMDBIds(){
        ResultSet resultSet = null;
        Statement statement = null;
        List<String> imdbIds = new ArrayList<>();
        try{
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT * FROM diggit_titles LIMIT  5");
            imdbIds = list(resultSet);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        close(resultSet);

        return imdbIds;
    }


    public static List<Map<String, String>> getTitles(){
        ResultSet resultSet = null;
        Statement statement = null;
        List<Map<String, String>> titles = new ArrayList<>();
        try{
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT * FROM diggit_titles LIMIT  5");
            titles = map(resultSet);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        close(resultSet);

        return titles;
    }

    public static List<String> getGenres(String titleId){
        ResultSet resultSet = null;
        Statement statement = null;
        List<String> genres = new ArrayList<>();
        try{
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT genre FROM torrents.genres where genre_id in (SELECT genre_id FROM torrents.diggit_title_genre where diggit_title_id = '"+titleId+"')");
            genres = list(resultSet);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        close(resultSet);

        return genres;

    }


}