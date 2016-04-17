package com.example.ankursingh.shaeredelementdemo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created by Om.Nishant@Makemytrip.com on 12/30/2014.
 */
public class GsonUtils {
    private static GsonUtils sInstance;
    private static final String TAG = LogUtils.makeLogTag(GsonUtils.class);

    private GsonBuilder mGsonBuilder;
    private Gson mGson;

    private GsonUtils(){
        mGsonBuilder = new GsonBuilder();
        mGson = mGsonBuilder.create();
    }

    public static synchronized GsonUtils getInstance(){
        if(sInstance == null){
            sInstance = new GsonUtils();
        }
        return sInstance;
    }

    /**
     * Deserializes the JSON object and returns an object of the the type specified in <i>classPath</i>
     * <p>
     * This method expects the classPath to refer to a non generic type.
     *
     * @param  json      the String representation of incoming JSON object
     * @param  classPath the non generic type of the return object
     * @return           the deserialized object of type classPath if the class exists and json represents an object of the type classPath, null otherwise
     */
    public <T> T deserializeJSON(String json, String classPath)throws ClassNotFoundException {
        T queryResult = null;

        if (json != null && classPath != null) {
            try {
                queryResult = (T) mGson.fromJson(json, Class.forName(classPath));
            } catch (JsonSyntaxException e) {
                LogUtils.error(TAG,"JsonSyntaxException: " + e.toString());
            } catch (JsonIOException e) {
                LogUtils.error(TAG,"JsonIOException " + e.toString());
            }catch(Exception e){
                LogUtils.error(TAG, e.toString(),e);
            }
        }
        return queryResult;
    }

    /**
     * Deserializes the JSON object and returns an object of the the type specified in <i>classPath</i>
     * <p>
     * This method expects the classPath to refer to a non generic type.
     *
     * @param  json     the String representation of incoming JSON object
     * @param  classPath the non generic type of the return object
     * @return           the deserialized object of type classPath if the class exists and json represents an object of the type classPath     * @return           the deserialized object of type classPath if the class exists and json represents an object of the type classPath, null otherwise
     */
    public <T> T deserializeJSON(String json, Class classPath) {
        T queryResult = null;

        if (json != null && classPath != null) {
            try {
                queryResult = (T) mGson.fromJson(json, classPath);
            } catch (JsonSyntaxException e) {
                LogUtils.error(TAG,"JsonSyntaxException: " + e.toString());
            } catch (JsonIOException e) {
                LogUtils.error(TAG,"JsonIOException " + e.toString());
            }catch(Exception e){
                LogUtils.error(TAG, e.toString(),e);
            }
        }
        return queryResult;
    }

    /**
     * Deserializes the JSON object and returns an object of the the type specified in <i>classPath</i>
     * <p>
     * This method expects the classPath to refer to a non generic type.
     *
     * @param  inputStream the InputStream to read the incoming JSON object
     * @param  classPath   the non generic type of the return object
     * @return             the deserialized object of type classPath if the class exists and json represents an object of the type classPath     * @return           the deserialized object of type classPath if the class exists and json represents an object of the type classPath, null otherwise
     */
    public <T> T deserializeJSON(InputStream inputStream, Class classPath) {
        T queryResult = null;

        if (inputStream != null && classPath != null) {
            Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            try {
                queryResult = (T) mGson.fromJson(reader, classPath);
            } catch (JsonSyntaxException e) {
                LogUtils.error(TAG,"JsonSyntaxException: " + e.toString(),e);
            } catch (JsonIOException e) {
                LogUtils.error(TAG,"JsonIOException " + e.toString(),e);
            } catch(Exception e){
                LogUtils.error(TAG, e.toString(),e);
            }finally {
                try {
                    reader.close();
                } catch (IOException e) {}
            }
        }
        return queryResult;
    }



    public <T> T deserializeJSONByType(InputStream inputStream, Type type) {
        T queryResult = null;

        if (inputStream != null && type != null) {
            Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            try {
                queryResult = (T) mGson.fromJson(reader, type);
            } catch (JsonSyntaxException e) {
                LogUtils.error(TAG,"JsonSyntaxException: " + e.toString());
            } catch (JsonIOException e) {
                LogUtils.error(TAG,"JsonIOException " + e.toString());
            }catch(Exception e){
                LogUtils.error(TAG, e.toString(),e);
            }finally {
                try {
                    reader.close();
                } catch (IOException e) {}
            }
        }
        return queryResult;
    }

    /**
     * Deserializes the JSON object and returns an object of the the type specified in <i>classPath</i>
     * <p>
     * This method expects the classPath to refer to a non generic type.
     *
     * @param  inputStream the InputStream to read the incoming JSON object
     * @param  classPath   the non generic type of the return object
     * @return             the deserialized object of type classPath if the class exists and json represents an object of the type classPath, null otherwise
     */
    public <T> T deserializeJSON(InputStream inputStream, String classPath) throws ClassNotFoundException {
        T queryResult = null;

        if (inputStream != null && classPath != null) {
            Reader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            try {
                queryResult = (T) mGson.fromJson(reader, Class.forName(classPath));
            } catch (JsonSyntaxException e) {
                LogUtils.error(TAG,"JsonSyntaxException: " + e.toString());
            } catch (JsonIOException e) {
                LogUtils.error(TAG,"JsonIOException " + e.toString());
            }catch(Exception e){
                LogUtils.error(TAG, e.toString(),e);
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {}
            }
        }
        return queryResult;
    }

    /**
     * Serializes an object to its JSON representation
     * <p>
     * This method should be used only with non generic objects.
     *
     * @param  object the InputStream to read the incoming JSON object
     * @return        the  JSON representation in form of a String
     */
    public String serializeToJson(Object object) {
        return mGson.toJson(object);
    }

    /**
     * Serializes an object to its JSON representation
     * <p>
     * This method should be used only with non generic objects.
     *
     * @param  object the InputStream to read the incoming JSON object
     * @return        the  JSON representation in form of a String
     */
    public String serializeToJson(Object object, Type type) {
        return mGson.toJson(object,type);
    }

}
