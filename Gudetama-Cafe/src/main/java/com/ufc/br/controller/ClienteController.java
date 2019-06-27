package com.ufc.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Cliente;
import com.ufc.br.service.ClienteService;

@RestController

public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private CarrinhoController cController;
	
	@RequestMapping("/cadastroCliente")
	public ModelAndView form() {
		ModelAndView mv = new ModelAndView("CadastroCliente");
		mv.addObject("cliente", new Cliente());
		return mv;
	}
	
	@PostMapping("/salvarCliente")
	public ModelAndView salvarCliente(@Validated Cliente cliente, BindingResult result) {
		ModelAndView mv = new ModelAndView("CadastroCliente");
		
		if(result.hasErrors()) {
			return mv;
		}
		
		clienteService.cadastrar(cliente);
		mv.addObject("mensagem", "Cliente cadastrado com sucesso!");
		
		return mv;
	}
	
	@RequestMapping("/login")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("Login");
		cController.esvaziar();
		return mv;
	}
	
	@RequestMapping("/login?logout")
	public ModelAndView logout() {
		ModelAndView mv = new ModelAndView("Login");
		cController.esvaziar();
		return mv;
	}
}
