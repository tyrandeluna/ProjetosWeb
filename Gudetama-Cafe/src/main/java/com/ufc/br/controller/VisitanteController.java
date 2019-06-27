package com.ufc.br.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VisitanteController {
	
	@RequestMapping("/home")
	public String home() {
		return "index";
	}
	@RequestMapping("/contato")
	public String contato() {
		return "contato";
	}
	
	@RequestMapping("/sobre")
	public String sobre() {
		return "sobre";
	}
}
