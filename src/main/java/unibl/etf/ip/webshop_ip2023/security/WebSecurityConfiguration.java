package unibl.etf.ip.webshop_ip2023.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import unibl.etf.ip.webshop_ip2023.services.JwtUserDetailsService;

@EnableWebSecurity   // includes our custom security configuration into spring security configuration
@Configuration       // marks our class ass configuration class
public class WebSecurityConfiguration {

    // service that enables retrieving user data based on specified username
    private final JwtUserDetailsService jwtUserDetailsService;
    // security filter that validates received requests
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    // takes care of requests which failed validation
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    public WebSecurityConfiguration(JwtUserDetailsService jwtUserDetailsService, JwtAuthorizationFilter jwtAuthorizationFilter, JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

     //interface declaring authenticate()  method, which accepts or rejects request depending on success of authentication
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    // service which encodes and encrypts password
    @Bean
    public PasswordEncoder passwordEncoder() {      //service that provides password encryption and encoding
        return new BCryptPasswordEncoder();
    }
    //declaring rules for accessing api
    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
//                .and()
                .disable()//ovo sam bio obrisao zbog cors-a
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/products/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/products/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/purchases").authenticated()
                .requestMatchers(HttpMethod.GET, "/purchases/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/messages").authenticated()
                .requestMatchers(HttpMethod.POST, "/comments").authenticated()
                .requestMatchers(HttpMethod.POST, "/images").authenticated()
//                .requestMatchers(HttpMethod.DELETE, "/users/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/**").permitAll() //enables unauthorized requests
                .requestMatchers(HttpMethod.POST, "/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/**").permitAll()

//                        .antMatchers("/api/v1/auth/**").permitAll()

                .anyRequest()
                .authenticated();
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


//    private HttpSecurity createAuthorizationRules(HttpSecurity http) throws Exception {
//        AuthorizationRules authorizationRules = new ObjectMapper().readValue(new ClassPathResource("rules.json").getInputStream(), AuthorizationRules.class);
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry interceptor = http.authorizeRequests();
//        interceptor = interceptor.antMatchers(HttpMethod.POST, "/login").permitAll().antMatchers(HttpMethod.POST, "/sign-up").permitAll();
//        for (Rule rule : authorizationRules.getRules()) {
//            if (rule.getMethods().isEmpty())
//                interceptor = interceptor.antMatchers(rule.getPattern()).hasAnyAuthority(rule.getRoles().toArray(String[]::new));
//            else for (String method : rule.getMethods()) {
//                interceptor = interceptor.antMatchers(HttpMethod.resolve(method), rule.getPattern()).hasAnyAuthority(rule.getRoles().toArray(String[]::new));
//            }
//        }
//        return interceptor.anyRequest().denyAll().and();
//    }

     //cors filter - we shouldn't allow all methods origins and headers...
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    //changes role prefix that is added automaticaly
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix
    }

}
