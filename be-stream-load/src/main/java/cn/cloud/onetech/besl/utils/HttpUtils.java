package cn.cloud.onetech.besl.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

/**
 * @author heguanhui
 * @desc
 * @date 2024/10/25 15:24
 */
public class HttpUtils {

    private static RequestConfig requestConfig =
            RequestConfig.custom()
                    .setConnectTimeout(60 * 1000)
                    .setConnectionRequestTimeout(60 * 1000)
                    // default checkpoint timeout is 10min
                    .setSocketTimeout(9 * 60 * 1000)
                    .build();

    public static HttpClientBuilder getHttpClientBuilderForBatch() {
        return HttpClients.custom()
                .setRedirectStrategy(
                        new DefaultRedirectStrategy() {
                            @Override
                            protected boolean isRedirectable(String method) {
                                return true;
                            }
                        })
                .setDefaultRequestConfig(requestConfig)
                .disableAutomaticRetries();
    }

}
