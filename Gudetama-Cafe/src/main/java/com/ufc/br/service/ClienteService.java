package com.ufc.br.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Cliente;
import com.ufc.br.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public void cadastrar(Cliente cliente) {
		cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
		clienteRepository.save(cliente);
	}
	
}
