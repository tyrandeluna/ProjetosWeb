package com.ufc.br.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Pratos;
import com.ufc.br.service.PratoService;

@RestController
public class PratoController {
	
	@Autowired
	private PratoService pratoService;
	
	@RequestMapping("/cadastroPrato")
	public ModelAndView formPrato() {
		ModelAndView mv = new ModelAndView("CadastroPrato");
		mv.addObject("pratos", new Pratos());
		return mv;
	}
	
	@PostMapping("/salvarPrato")
	public ModelAndView salvarPrato(@Validated Pratos prato, BindingResult result, @RequestParam(value="imagem") MultipartFile imagem) {
		ModelAndView mv = new ModelAndView("CadastroPrato");

		if(result.hasErrors()) {
			return mv;
		}
		
		pratoService.cadastrar(prato, imagem);
		mv.addObject("mensagem", "Prato cadastrado com sucesso!");
		
		return mv;
	}
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		
		List<Pratos> pratos = pratoService.listarPratos();
		ModelAndView mv = new ModelAndView("galeria");
		mv.addObject("listaDePratos", pratos);
		
		return mv;
	}
	
	@RequestMapping("/excluir/{codigo}")
	public ModelAndView excluir(@PathVariable Long codigo) {
		pratoService.excluir(codigo);
		
		ModelAndView mv = new ModelAndView("redirect:/listar");
		return mv;
	}
	
	@RequestMapping("/atualizar/{codigo}")
	public ModelAndView atualizar(@PathVariable Long codigo) {
		Pratos prato = pratoService.buscaPorId(codigo);
		ModelAndView mv = new ModelAndView("CadastroPrato");
		mv.addObject("pratos", prato);
		
		return mv;
	}
}
