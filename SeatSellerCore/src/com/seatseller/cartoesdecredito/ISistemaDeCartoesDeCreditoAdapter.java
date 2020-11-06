package com.seatseller.cartoesdecredito;

/**
 * Define tipos de adaptadores para interacao com sistemas de cartoes de credito
 * 
 * @author Grupo 031
 *
 */

public interface ISistemaDeCartoesDeCreditoAdapter {

	/**
	 * Verifica se o cartao e valido
	 * 
	 * @param num
	 *            numero do cartao
	 * @param ccv
	 *            ccv do cartao
	 * @param mes
	 *            mes do cartao
	 * @param ano
	 *            ano do cartao
	 * @return true se o cartao for valido, false se o cartao nao for valido
	 */
	boolean validar(String num, int ccv, int mes, int ano);

	/**
	 * Cativa o valor dado no cartao caso este seja valido
	 * 
	 * @param num
	 *            numero do cartao
	 * @param ccv
	 *            ccv do cartao
	 * @param mes
	 *            mes do cartao
	 * @param ano
	 *            ano do cartao
	 * @param qt
	 *            valor a retirar do cartao
	 * @return true se o valor for cativado, false caso o valor nao seja
	 *         cativado
	 */
	boolean cativar(String num, int ccv, int mes, int ano, double qt);

	/**
	 * Retira o valor dado do cartao caso este seja valido
	 * 
	 * @param num
	 *            numero do cartao
	 * @param ccv
	 *            ccv do cartao
	 * @param mes
	 *            mes do cartao
	 * @param ano
	 *            ano do cartao
	 * @param qt
	 *            valor a retirar do cartao
	 * @return true se o valor for retirado, false caso o valor nao seja
	 *         retirado
	 */
	boolean retirar(String num, int ccv, int mes, int ano, double qt);
}
