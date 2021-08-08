package fr.redkissifrott.paymybuddy.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.redkissifrott.paymybuddy.customUser.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// @Bean
	// public PasswordEncoder passwordEncoder() {
	// return new PasswordEncoderNo();
	// }

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	// @Override
	// protected void configure(HttpSecurity http) throws Exception {
	// http.csrf().disable()
	//
	// .authorizeRequests().antMatchers("/", "home").permitAll()
	// .antMatchers("/ressources/**").permitAll().anyRequest()
	// .authenticated().and()
	//
	// .formLogin().usernameParameter("email")
	// .loginProcessingUrl("/authentication").permitAll()
	// .defaultSuccessUrl("/profile").permitAll()
	//
	// .and().logout().logoutSuccessUrl("/").permitAll().and()
	//
	// .rememberMe().key("uniqueAndSecret");
	// }
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/users")
				.authenticated().anyRequest().permitAll()

				.and().formLogin().loginPage("/login")
				.usernameParameter("email").defaultSuccessUrl("/profile", true)
				.permitAll()

				.and().logout().logoutSuccessUrl("/").permitAll();
	}

}
