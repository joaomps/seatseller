package com.seatseller.core.lugares;

/**
 * Representa um tipo de lugar e as operacoes associadas ao uso deste
 * 
 * @author 031
 *
 */

public class TipoDeLugar {
	private String desig;
	private String descricao;
	private double preco;

	/**
	 * Cria um novo tipo de lugar
	 * 
	 * @param d
	 *            designacao do tipo de lugar
	 * @param desc
	 *            descricao do tipo de lugar
	 * @param preco
	 *            preco do tipo de lugar
	 */
	public TipoDeLugar(String d, String desc, double preco) {
		this.desig = d;
		this.descricao = desc;
		this.preco = preco;
	}

	/**
	 * Devolve a designacao do tipo de lugar
	 * 
	 * @return designacao do tipo de lugar
	 */
	public String getDesig() {
		return desig;
	}

	/**
	 * Devolva a descricao do tipo de lugar
	 * 
	 * @return descricao do tipo de lugar
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Devolve o preco do tipo de lugar relativo ao indice de preco de uma
	 * grelha
	 * 
	 * @param ind
	 *            indice de preco de uma grelha
	 * @return preco do tipo de lugar relativo ao indice de preco de uma grelha
	 */
	public double getPreco(double ind) {
		return preco * ind;
	}

	@Override
	public String toString() {
		return desig;
	}
}
