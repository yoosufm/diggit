package com.diggit.qa.common;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.StreamHandler;

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

    public static List<Map<String, String>> getTitles(String titleId){
        ResultSet resultSet = null;
        Statement statement = null;
        List<Map<String, String>> titles = new ArrayList<>();
        try{
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT * FROM diggit_titles WHERE diggit_title_id = '"+titleId+"' LIMIT  5");
            titles = map(resultSet);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        close(resultSet);

        return titles;
    }

    public static int getInfohashTrackCount(String infohash, Statement statement){
        ResultSet resultSet = null;
        int trackCount = 0;

        try{

            resultSet = statement.executeQuery("SELECT TRACKED FROM torrents.infohashes WHERE infohash = '" +infohash + "'");
            while (resultSet.next()){
                trackCount = resultSet.getInt("TRACKED");
                break;
            }        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return trackCount;
    }

    public static int getGroupInfohashCount(String infohash, Statement statement){
        ResultSet resultSet = null;

        int groupInfohash = 0;

        try{

            resultSet = statement.executeQuery("SELECT count(infohash) AS rowcount FROM torrents.group_infohashes where infohash = '"+infohash+"'");
            while (resultSet.next()){
                groupInfohash = resultSet.getInt("rowcount");
                break;
            }        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return groupInfohash;
    }

    public static int getJobCount(String infohash, Statement statement){
        ResultSet resultSet = null;
        int jobCount = 0;
        try{
            resultSet = statement.executeQuery("SELECT count(infohash) AS rowcount FROM jobcentral.jobs where infohash = '"+infohash+"'");
            while (resultSet.next()){
                jobCount = resultSet.getInt("rowcount");
                break;
            }        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return jobCount;
    }

    public static List<Integer>  getStateMachineCount(String infohash){

        Statement statement = null;
        List<Integer> stateMachineCount = new ArrayList<>();
        int groupInfohash = 0;
        int jobCount = 0;
        int trackCount = 0;
        try {
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            groupInfohash = getGroupInfohashCount(infohash, statement);
            jobCount = getJobCount(infohash, statement);
            trackCount = getInfohashTrackCount(infohash, statement);
        }catch (Exception e){

        }
        try {
            stateMachineCount.add(trackCount);
            stateMachineCount.add(groupInfohash);

            stateMachineCount.add(jobCount);
        }catch (Exception e){
            e.printStackTrace();
        }
        close(statement);
        return stateMachineCount;
    }

    public static List<String> getInfohashs(){
        ResultSet resultSet = null;
        int jobCount = 0;
        List<String> infohashes = new ArrayList<>();
        Statement statement = null;
        try{
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT infohash FROM torrents.infohashes ORDER BY rand() LIMIT 3000;");
            infohashes = list(resultSet);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        close(statement);

        return infohashes;
    }


    public static List<Map<String, String>> getTitle(){
        ResultSet resultSet = null;
        Statement statement = null;
        List<Map<String, String>> titles = new ArrayList<>();
        try{
            statement = DatabaseConnection.getDatabaseConnection().
                    createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT * FROM diggit_titles ORDER BY rand() LIMIT 100;");
            titles = map(resultSet);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        close(resultSet);

        return titles;
    }

    public static int getLeftJoinCount(){
        ResultSet resultSet = null;
        Statement statement = null;
        int leftJoinCount = 1;
        Connection connection = null;
        try{
            connection = DatabaseConnection.getDatabaseConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT COUNT(dti.diggit_title_id) AS rowcount FROM diggit_titles_infohashes dti LEFT JOIN diggit_titles dt ON dt.diggit_title_id = dti.diggit_title_id WHERE dt.diggit_title_id IS NULL;");
            while (resultSet.next()){
                leftJoinCount = Integer.valueOf(resultSet.getString("rowcount"));
                break;
            }
        }catch (CommunicationsException ex){
            ex.printStackTrace();
            return Errors.DB_SERVER_NOT_AVAILABLE;
        }catch (SQLException sq){
            return Errors.DB_NOT_AVAILABLE;
        }
        close(resultSet);

        return leftJoinCount;
    }

    public static int getJobCount(){
        ResultSet resultSet = null;
        Statement statement = null;
        int leftJoinCount = 100;
        Connection connection = null;
        try {
            connection = DatabaseConnection.getDatabaseConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT * FROM jobcentral.jobs ORDER BY rand() LIMIT 100;");
        }catch (CommunicationsException ex){
            ex.printStackTrace();
            return Errors.DB_SERVER_NOT_AVAILABLE;
        }catch (SQLException sq){
            return Errors.DB_NOT_AVAILABLE;
        }
        close(resultSet);

        return leftJoinCount;
    }

    public static int getErrorCount(){
        ResultSet resultSet = null;
        Statement statement = null;
        int leftJoinCount = 100;
        Connection connection = null;
        try {
            connection = DatabaseConnection.getDatabaseConnection();
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = statement.executeQuery("SELECT * FROM management.mn_error_reports ORDER BY rand() LIMIT 100;");
        }catch (CommunicationsException ex){
            ex.printStackTrace();
            return Errors.DB_SERVER_NOT_AVAILABLE;
        }catch (SQLException sq){
            return Errors.DB_NOT_AVAILABLE;
        }
        close(resultSet);

        return leftJoinCount;
    }
}
