package jcom.cms.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
//@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //@Autowired
    //private  UserService userService;

    @Value("${static.crossOrigin}")
    private String CrossOrigin;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
             http.csrf()
                 .disable()
               //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                 .authorizeRequests()
                //.antMatchers("/**","/js/**", "/css/**", "/images/**", "/img/**", "/fonts/**", "/img_theme/**").permitAll()

                 .antMatchers("/admin/**",
                                 "/content/config/**",
                                 "/content/files/**",
                                 "/*.xml",
                                 "/*.json"
                                ).hasAnyRole("ADMIN")

                .antMatchers("/",
                        "/**",
                        "/content/**",
                        "/registration",
                        "/error",
                        "/favicon.ico",
                        "/*.png",
                        "/*.gif",
                        "/*.svg",
                        "/*.jpg",
                        "/*.jpg",
                        //"/*.html",
                        "/*.css",
                        "/*.js").permitAll()

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll();
    }

    /*
    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
  */
    /*
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        if(CrossOrigin != null)
         configuration.setAllowedOrigins(Arrays.asList(CrossOrigin));

        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

     */



/*
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
*/

}
