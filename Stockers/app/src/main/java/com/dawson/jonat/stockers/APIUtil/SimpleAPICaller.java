package com.dawson.jonat.stockers.APIUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class SimpleAPICaller {
    private String urlAPI;
    private HttpMethods method;
    private String bearer;
    private Map<String, String> params;

    public SimpleAPICaller(String urlAPI, HttpMethods method, Map<String, String> params, String bearer) {
        this.urlAPI = urlAPI;
        this.method = method;
        this.params = params;
    }

    public String getUrlAPI() {
        return urlAPI;
    }

    public void setUrlAPI(String urlAPI) {
        this.urlAPI = urlAPI;
    }

    public HttpMethods getMethod() {
        return method;
    }

    public void setMethod(HttpMethods method) {
        this.method = method;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    /**
     * Builds a querystring starting with ? and adding on key/value pairs
     *
     * @return
     */
    public String buildQueryString() throws UnsupportedEncodingException {
        String qs = "?";
        return buildString(qs);
    }

    public String buildPostParams() throws UnsupportedEncodingException {
        String qs = "";
        return buildString(qs);
    }

    private String buildString(String qs) throws UnsupportedEncodingException {
        int startLength = qs.length() + 1;

        for (Map.Entry<String, String> param : this.params.entrySet()) {
            qs += URLEncoder.encode(param.getKey(), "UTF-8") + "=" + URLEncoder.encode(param.getValue(), "UTF-8") + "&";
        }

        if (qs.length() < startLength) {
            return "";
        }
        //Delete the last & before returning
        return qs.substring(0, qs.length() - startLength);
    }
}
