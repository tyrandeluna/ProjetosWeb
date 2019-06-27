package com.ufc.br.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.Item;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.service.ItemService;

@Controller
public class CarrinhoController {
	
	@Autowired
	private PratoRepository pratoRepository;
	
	@Autowired
	private ItemService itemService;
	
	private List<Item> itens = new ArrayList<Item>();
	private int count = 0;
	
	@GetMapping("/carrinho")
	public ModelAndView listarItem() {
		ModelAndView mv = new ModelAndView("Cart");
		mv.addObject("listaDeItens", itens);
		
		return mv;
	}
	
	@RequestMapping("/addItem/{idprato}")
	public ModelAndView addCart(@PathVariable Long idprato) {
		ModelAndView mv = new ModelAndView("redirect:/listar");
		
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		Item item = new Item();
		item.setIdcliente(user.getUsername());
		item.setIdprato(idprato);
		item.setNomeprato(pratoRepository.getOne(idprato).getNome());
		item.setPrecoprato(pratoRepository.getOne(idprato).getPreco());
		if (itens.isEmpty()) {
			count = itemService.ultimoId();
			item.setCodigo(count);
			item.setQuantidade(1);
			itens.add(item);
			count++;
		} else {
			int index = this.exists(idprato, itens);
		if(index == -1) {
			item.setCodigo(count++);
			item.setQuantidade(1);
			itens.add(item);
		} else {
			int quantidade = itens.get(index).getQuantidade() + 1;
			double preco = quantidade * item.getPrecoprato();
			itens.get(index).setQuantidade(quantidade);
			itens.get(index).setPrecoprato(preco);
			}
		}
		
		//itemService.salvarItem(item);
		return mv;
	}
	
	@RequestMapping("/removeItem/{codigo}")
	public ModelAndView excluir(@PathVariable int codigo) {
		//itemService.remover(codigo);
		if(!itens.isEmpty()) {
			for (int i = 0; i < itens.size(); i++) {
				if((codigo == itens.get(i).getCodigo())) {
					if((itens.get(i).getQuantidade() > 1)) {
						double preco_original = itens.get(i).getPrecoprato() / itens.get(i).getQuantidade();
						int quantidade = itens.get(i).getQuantidade() - 1;
						itens.get(i).setQuantidade(quantidade);
						itens.get(i).setPrecoprato(itens.get(i).getPrecoprato() - preco_original);
					}else {
						Item removedItem = itens.get(i);
						itens.remove(removedItem);
						count--;
						}
				}
			}
			
		}
		ModelAndView mv = new ModelAndView("redirect:/carrinho");
		return mv;
	}
	
	@RequestMapping("/finalizar")
	public ModelAndView finalizar() {
		ModelAndView mv = new ModelAndView("redirect:/carrinho");
		for (int i = 0; i < itens.size(); i++) {
			Item item = itens.get(i);
			itemService.salvarItem(item);
		}
		itens.clear();
		return mv;
	}
	
	@GetMapping("/pedidos")
	public ModelAndView listarPedidos() {
		
		List<Item> its = itemService.listarItem();
		ModelAndView mv = new ModelAndView("Pedidos");
		mv.addObject("listaDeItens", its);
		
		return mv;
	}
	
	private int exists(Long id, List<Item> itens) {
		for (int i = 0; i < itens.size(); i++) {
			if (itens.get(i).getIdprato() == id) {
				return i;
			}
		}
		return -1;
	}
	
	public void esvaziar() {
		itens.clear();
	}
}
