package com.seatseller.handlers;

import com.seatseller.api.IConcluirReservaHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;
import com.seatseller.cartoesdecredito.ISistemaDeCartoesDeCreditoAdapter;
import com.seatseller.cartoesdecredito.SistemaDeCartoesDeCreditoAdapterFactory;
import com.seatseller.core.reservas.CatalogoReservas;
import com.seatseller.core.reservas.Reserva;
import com.seatseller.core.utilizadores.ClienteFinal;

import com.seatseller.pagamentos.Pagamento;

public class ConcluirReservaHandler implements IConcluirReservaHandler {

	private double valorFalta;
	private CatalogoReservas catalogoReservas;
	private Reserva res;
	private SistemaDeCartoesDeCreditoAdapterFactory sisFactory;

	public ConcluirReservaHandler(CatalogoReservas catReservas,
			SistemaDeCartoesDeCreditoAdapterFactory sisCC) {
		catalogoReservas = catReservas;
		this.sisFactory = sisCC;
	}

	@Override
	public double confirmarValorEmFalta(String codigo)
			throws DoesNotExistException {
		res = catalogoReservas.getReserva(codigo);
		if (res != null) {
			valorFalta = res.getValorEmFalta();

			return valorFalta;
		} else {
			throw new DoesNotExistException(
					"Nao existe reserva com o codigo" + codigo);
		}
	}

	@Override
	public void indicarCC(String numero, int ccv, int mes, int ano)
			throws InvalidCreditCardException {
		ISistemaDeCartoesDeCreditoAdapter sisCC;
		sisCC = sisFactory.getSistemaDeCartoesDeCreditoAdapter();
		Pagamento pg;
		boolean b = sisCC.validar(numero, ccv, mes, ano);

		if (b) {
			ClienteFinal cli = res.getCliente();
			if (!cli.temCC(numero)) {
				cli.criaCC(numero, ccv, mes, ano);
			}
			pg = new Pagamento(false, valorFalta);
			cli.registarPagamento(pg);
			res.registarPagamento(pg);

			sisCC.retirar(numero, ccv, mes, ano, valorFalta);
		} else {
			throw new InvalidCreditCardException(
					"O cartao de credito indicado nao e valido");
		}

	}

}
