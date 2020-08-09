package fastwave.cloud.stomp.webstocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author zhangqiang
 * @title CorsConfig 如果使用网关服务调用微服务，则注释掉Configuration注解；如果不使用网关服务，则需将Configuration注解的注释放开
 * @Description 配置跨域问题
 * @Date 2019年05月28日 15:02
 * @Copyright 2019-2020 www.epri.sgcc.com.cn All rights reserved.
 */
@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //允许任何域名访问
        corsConfiguration.addAllowedOrigin("*");
        //允许任何header访问
        corsConfiguration.addAllowedHeader("*");
        //允许任何方法访问
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
