package com.dawson.jonat.stockers.APIUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;


/**
 * Object that represents Http Request
 * Containing UrlAPI, HttpMethod, bearer token and params
 *
 */
public class SimpleAPICaller {

    //Basic Http Request Settings
    private String urlAPI;
    private HttpMethods method;
    private String bearer;
    private Map<String, String> params;


    /**
     * Instantiates the settings for the Http Request
     *
     * @param urlAPI
     * @param method
     * @param params
     * @param bearer
     */
    public SimpleAPICaller(String urlAPI, HttpMethods method, Map<String, String> params, String bearer) {
        this.urlAPI = urlAPI;
        this.method = method;
        this.params = params;
        this.bearer = bearer;
    }

    /**
     *
     * @return urlAPI
     */
    public String getUrlAPI() {
        return urlAPI;
    }

    /**
     *
     * @param urlAPI
     */
    public void setUrlAPI(String urlAPI) {
        this.urlAPI = urlAPI;
    }

    /**
     *
     * @return method
     */
    public HttpMethods getMethod() {
        return method;
    }

    /**
     *
     * @param method
     */
    public void setMethod(HttpMethods method) {
        this.method = method;
    }

    /**
     *
     * @return params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     *
     * @param params
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     *
     * @return bearer
     */
    public String getBearer() {
        return bearer;
    }

    /**
     *
     * @param bearer
     */
    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    /**
     * Builds a querystring starting with ? and adding on key/value pairs
     *
     * @return querystring
     * @throws UnsupportedEncodingException
     */
    public String buildQueryString() throws UnsupportedEncodingException {
        return buildString("?");
    }

    /**
     *  Builds a post param string with the instance params
     *
     * @return postparams
     * @throws UnsupportedEncodingException
     */
    public String buildPostParams() throws UnsupportedEncodingException {
        return buildString("");
    }

    /**
     * Builds a string with the instance params and specified starting string
     *
     * @param qs
     * @return
     * @throws UnsupportedEncodingException
     */
    private String buildString(String qs) throws UnsupportedEncodingException {
        int startLength = qs.length() + 1;

        for (Map.Entry<String, String> param : this.params.entrySet()) {
            qs += URLEncoder.encode(param.getKey(), "UTF-8") + "=" + URLEncoder.encode(param.getValue(), "UTF-8") + "&";
        }

        //Returns empty if the params are empty
        if (qs.length() < startLength) {
            return "";
        }

        //Delete the last '&' before returning
        return qs.substring(0, qs.length() - startLength);
    }
}
