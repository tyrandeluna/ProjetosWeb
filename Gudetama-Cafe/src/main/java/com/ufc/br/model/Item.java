package com.ufc.br.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
	@Id
	//a@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigo;
	
	private String idcliente;
	private Long idprato;
	private String nomeprato;
	private double precoprato;
	private int quantidade;
	
	public Long getIdprato() {
		return idprato;
	}
	public void setIdprato(Long idprato) {
		this.idprato = idprato;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}
	public String getNomeprato() {
		return nomeprato;
	}
	public void setNomeprato(String nomeprato) {
		this.nomeprato = nomeprato;
	}
	public double getPrecoprato() {
		return precoprato;
	}
	public void setPrecoprato(double d) {
		this.precoprato = d;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
}
