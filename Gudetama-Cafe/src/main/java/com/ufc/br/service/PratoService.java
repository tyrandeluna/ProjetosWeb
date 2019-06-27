package com.ufc.br.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ufc.br.model.Pratos;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.util.AulaFileUtils;

@Service
public class PratoService {
	
	@Autowired
	private PratoRepository pratoRepository;
	
	public void cadastrar(Pratos prato, MultipartFile imagem) {
		String caminho = "images/" + prato.getNome() + ".png";
		
		AulaFileUtils.salvarImagens(caminho,imagem);
		
		pratoRepository.save(prato);
	}
	
	public List<Pratos> listarPratos() {
		return pratoRepository.findAll();
	}

	public void excluir(Long codigo) {
		pratoRepository.deleteById(codigo);
	}

	public Pratos buscaPorId(Long codigo) {
		return pratoRepository.getOne(codigo);
	}
}
