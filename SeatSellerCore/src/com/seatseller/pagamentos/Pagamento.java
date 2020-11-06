package com.seatseller.pagamentos;

/**
 * Representa um pagamento efetuado
 * 
 * @author Grupo 031
 *
 */

public class Pagamento {

	private boolean pago;
	private double valorPago;

	/**
	 * Cria um novo pagamento
	 * 
	 * @param b
	 *            pagamento foi efetuado
	 * @param valorPago
	 *            valor pago
	 */
	public Pagamento(boolean b, double valorPago) {
		this.pago = b;
		this.valorPago = valorPago;
	}

	/**
	 * Indica o valor que ja foi pago
	 * 
	 * @return valor que ja foi pago
	 */
	public double getValorPago() {
		return this.valorPago;
	}

}
