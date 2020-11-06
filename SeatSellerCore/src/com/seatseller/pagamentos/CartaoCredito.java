package com.seatseller.pagamentos;

/**
 * Representa um cartao de credito
 * 
 * @author Grupo 031
 *
 */
public class CartaoCredito {

	private String num;
	private int ccv;
	private int mes;
	private int ano;

	/**
	 * Cria um novo cartao de credito
	 * 
	 * @param num
	 *            numero do cartao de credito
	 * @param ccv
	 *            ccv do cartao de credito
	 * @param mes
	 *            mes do cartao de credito
	 * @param ano
	 *            ano do cartao de credito
	 */
	public CartaoCredito(String num, int ccv, int mes, int ano) {
		this.num = num;
		this.ccv = ccv;
		this.mes = mes;
		this.ano = ano;
	}

	/**
	 * Devolve o numero do cartao de credito
	 * 
	 * @return numero do cartao de credito
	 */
	public String getNumero() {
		return this.num;
	}

	/**
	 * Devolve o ccv do cartao de credito
	 * 
	 * @return ccv do cartao de credito
	 */
	public int getCcv() {
		return this.ccv;
	}

	/**
	 * Devolve o mes de validade do cartao de credito
	 * 
	 * @return mes de validade do cartao de credito
	 */
	public int getMes() {
		return this.mes;
	}

	/**
	 * Devolve o ano de validade do cartao de credito
	 * 
	 * @return ano de validade do cartao de credito
	 */
	public int getAno() {
		return this.ano;
	}
}
