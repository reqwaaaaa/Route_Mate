package com.RouteMate.framework.ai.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网络连接测试工具类
 * 
 * @author RouteMate
 */
public class NetworkTestUtil
{
    private static final Logger log = LoggerFactory.getLogger(NetworkTestUtil.class);

    /**
     * 测试URL连通性
     * 
     * @param urlString 要测试的URL
     * @param timeoutMs 超时时间（毫秒）
     * @return 测试结果
     */
    public static Map<String, Object> testUrlConnectivity(String urlString, int timeoutMs)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("url", urlString);
        result.put("timeout", timeoutMs);
        
        try {
            URL url = new URL(urlString);
            String host = url.getHost();
            int port = url.getPort();
            if (port == -1) {
                port = url.getDefaultPort();
            }
            
            result.put("host", host);
            result.put("port", port);
            
            // 1. 测试TCP连接
            long tcpStart = System.currentTimeMillis();
            boolean tcpConnectable = testTcpConnection(host, port, timeoutMs);
            long tcpTime = System.currentTimeMillis() - tcpStart;
            
            result.put("tcpConnectable", tcpConnectable);
            result.put("tcpTime", tcpTime);
            
            if (!tcpConnectable) {
                result.put("success", false);
                result.put("error", "TCP连接失败");
                return result;
            }
            
            // 2. 测试HTTP连接
            long httpStart = System.currentTimeMillis();
            Map<String, Object> httpResult = testHttpConnection(urlString, timeoutMs);
            long httpTime = System.currentTimeMillis() - httpStart;
            
            result.put("httpTime", httpTime);
            result.putAll(httpResult);
            
            return result;
            
        } catch (Exception e) {
            log.error("网络连接测试异常", e);
            result.put("success", false);
            result.put("error", "测试异常: " + e.getMessage());
            return result;
        }
    }

    /**
     * 测试TCP连接
     */
    private static boolean testTcpConnection(String host, int port, int timeoutMs)
    {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeoutMs);
            return true;
        } catch (IOException e) {
            log.debug("TCP连接失败: {}:{}, 错误: {}", host, port, e.getMessage());
            return false;
        }
    }

    /**
     * 测试HTTP连接
     */
    private static Map<String, Object> testHttpConnection(String urlString, int timeoutMs)
    {
        Map<String, Object> result = new HashMap<>();
        
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 设置请求参数
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(timeoutMs);
            connection.setReadTimeout(timeoutMs);
            connection.setRequestProperty("User-Agent", "RouteMate-Network-Test/1.0");
            
            // 获取响应
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            
            result.put("success", true);
            result.put("responseCode", responseCode);
            result.put("responseMessage", responseMessage);
            result.put("contentType", connection.getContentType());
            result.put("contentLength", connection.getContentLength());
            
            // 检查是否为有效的HTTP响应
            if (responseCode >= 200 && responseCode < 600) {
                result.put("httpValid", true);
            } else {
                result.put("httpValid", false);
                result.put("error", "无效的HTTP响应码: " + responseCode);
            }
            
        } catch (java.net.SocketTimeoutException e) {
            result.put("success", false);
            result.put("error", "HTTP连接超时");
        } catch (java.net.ConnectException e) {
            result.put("success", false);
            result.put("error", "HTTP连接被拒绝");
        } catch (IOException e) {
            result.put("success", false);
            result.put("error", "HTTP连接异常: " + e.getMessage());
        }
        
        return result;
    }

    /**
     * 测试AI API连通性
     * 
     * @param apiUrl API地址
     * @param apiKey API密钥
     * @param timeoutMs 超时时间
     * @return 测试结果
     */
    public static Map<String, Object> testAiApiConnectivity(String apiUrl, String apiKey, int timeoutMs)
    {
        Map<String, Object> result = new HashMap<>();
        result.put("apiUrl", apiUrl);
        result.put("timeout", timeoutMs);
        
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // 设置请求参数
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(timeoutMs);
            connection.setReadTimeout(timeoutMs);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("User-Agent", "RouteMate-AI-Test/1.0");
            connection.setDoOutput(true);
            
            // 发送一个简单的测试请求
            String testRequest = "{\"model\":\"test\",\"messages\":[{\"role\":\"user\",\"content\":\"test\"}]}";
            try (java.io.OutputStream os = connection.getOutputStream()) {
                os.write(testRequest.getBytes("UTF-8"));
            }
            
            // 获取响应
            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();
            
            result.put("success", true);
            result.put("responseCode", responseCode);
            result.put("responseMessage", responseMessage);
            
            // 分析响应码
            if (responseCode == 200) {
                result.put("apiStatus", "正常");
                result.put("message", "AI API连接正常");
            } else if (responseCode == 401) {
                result.put("apiStatus", "认证失败");
                result.put("message", "API密钥无效");
            } else if (responseCode == 400) {
                result.put("apiStatus", "请求格式错误");
                result.put("message", "API接受请求但格式有误（这是正常的测试结果）");
            } else if (responseCode == 429) {
                result.put("apiStatus", "频率限制");
                result.put("message", "API请求频率过高");
            } else if (responseCode >= 500) {
                result.put("apiStatus", "服务器错误");
                result.put("message", "AI服务器内部错误");
            } else {
                result.put("apiStatus", "未知状态");
                result.put("message", "未知的响应状态: " + responseCode);
            }
            
        } catch (java.net.SocketTimeoutException e) {
            result.put("success", false);
            result.put("error", "AI API连接超时");
            result.put("message", "请检查网络连接或增加超时时间");
        } catch (java.net.ConnectException e) {
            result.put("success", false);
            result.put("error", "无法连接到AI API");
            result.put("message", "请检查API地址和网络连接");
        } catch (IOException e) {
            result.put("success", false);
            result.put("error", "AI API连接异常: " + e.getMessage());
            result.put("message", "网络或IO异常");
        }
        
        return result;
    }

    /**
     * 获取网络诊断信息
     */
    public static Map<String, Object> getNetworkDiagnostics()
    {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 系统网络信息
            result.put("javaVersion", System.getProperty("java.version"));
            result.put("osName", System.getProperty("os.name"));
            result.put("osVersion", System.getProperty("os.version"));
            
            // 网络代理设置
            result.put("httpProxy", System.getProperty("http.proxyHost"));
            result.put("httpsProxy", System.getProperty("https.proxyHost"));
            
            // DNS测试
            long dnsStart = System.currentTimeMillis();
            try {
                java.net.InetAddress.getByName("ark.cn-beijing.volces.com");
                result.put("dnsResolution", "成功");
                result.put("dnsTime", System.currentTimeMillis() - dnsStart);
            } catch (Exception e) {
                result.put("dnsResolution", "失败: " + e.getMessage());
            }
            
        } catch (Exception e) {
            result.put("error", "获取网络诊断信息失败: " + e.getMessage());
        }
        
        return result;
    }
}
