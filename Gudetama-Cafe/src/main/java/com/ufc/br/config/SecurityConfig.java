package com.ufc.br.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ufc.br.security.UserDetailsServiceImplementacao;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsServiceImplementacao userDetailsServiceImple;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		
		.antMatchers("/home").permitAll()
		.antMatchers("/sobre").permitAll()
		.antMatchers("/contato").permitAll()
		.antMatchers("/cadastroCliente").permitAll()
		.antMatchers("/salvarCliente").permitAll()
		.antMatchers("/listar").permitAll() // /pessoa/listar todo mundo pode acessar
		.antMatchers("/cadastroPrato").hasRole("GERENTE")
		.antMatchers("/salvarPrato").hasRole("GERENTE")
		.antMatchers("/atualizar/{codigo}").hasRole("GERENTE")
		.antMatchers("/excluir/{codigo}").hasRole("GERENTE")
		.antMatchers("/carrinho").hasRole("CLIENTE")
		.antMatchers("/removeItem/{codigo}").hasRole("CLIENTE")
		
		.anyRequest().authenticated() // o resto precisa está autenticado
		
		.and()
		.formLogin()
		.loginPage("/login").defaultSuccessUrl("/home").permitAll()
		.permitAll() //permitir acesso para essa url "entrar"
		
		//.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		
		.and()
		//.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		//.logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
		//.invalidateHttpSession(true) 
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.permitAll();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImple).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**","/js/**", "/img/**", "/images/**");
	}
}
