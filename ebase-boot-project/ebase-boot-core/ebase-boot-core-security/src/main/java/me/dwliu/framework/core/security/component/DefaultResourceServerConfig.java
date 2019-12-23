package me.dwliu.framework.core.security.component;

import lombok.AllArgsConstructor;
import me.dwliu.framework.core.security.properties.OAuth2Client4IgnoreUrlProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.web.client.RestTemplate;

/**
 * 默认资源认证服务器
 * <p>
 * 子服务中不添加资源服务器就默认加载这里
 *
 * @author liudw
 * @date 2019-05-02 11:58
 **/
@AllArgsConstructor
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final OAuth2Client4IgnoreUrlProperties oAuth2Client4IgnoreUrlProperties;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final RemoteTokenServices remoteTokenServices;

    private final RestTemplate lbRestTemplate;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable();
//                http.authorizeRequests()
//                .antMatchers("/system/user/getUserByUsername").permitAll()
//                .anyRequest().authenticated();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        oAuth2Client4IgnoreUrlProperties.getIgnoreUrls()
                .forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated();


    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        UserAuthenticationConverter userTokenConverter = new CustomUserAuthenticationConverter();
        accessTokenConverter.setUserTokenConverter(userTokenConverter);

        resources.authenticationEntryPoint(customAuthenticationEntryPoint);

        remoteTokenServices.setRestTemplate(lbRestTemplate);
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        //https://my.oschina.net/u/2518341/blog/3021642
        resources.tokenServices(remoteTokenServices);
    }
}
