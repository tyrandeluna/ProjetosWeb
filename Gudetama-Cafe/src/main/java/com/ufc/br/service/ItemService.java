package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ufc.br.model.Item;
import com.ufc.br.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository itemRepository;
	
	public void salvarItem(Item item) {
		itemRepository.save(item);
	}
	
	public List<Item> listarItem(){
		List<Item> aux = new ArrayList<Item>();
		Object auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = (UserDetails) auth;
		
		for (int i = 0; i < itemRepository.findAll().size(); i++) {
			if (itemRepository.findAll().get(i).getIdcliente().equals(user.getUsername())) {
				aux.add(itemRepository.findAll().get(i));
			}
		}
		return aux;
	}
	
	public int ultimoId() {
		return itemRepository.findAll().size();
	}
	
	public void remover(Long codigo) {
		itemRepository.deleteById(codigo);
	}
}
