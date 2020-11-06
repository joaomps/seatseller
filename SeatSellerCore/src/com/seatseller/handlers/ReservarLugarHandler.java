package com.seatseller.handlers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import com.seatseller.Configuration;
import com.seatseller.api.IReservarLugarHandler;
import com.seatseller.api.exceptions.DoesNotExistException;
import com.seatseller.api.exceptions.InvalidCreditCardException;
import com.seatseller.api.wrappers.Combinacao;
import com.seatseller.cartoesdecredito.ISistemaDeCartoesDeCreditoAdapter;
import com.seatseller.cartoesdecredito.SistemaDeCartoesDeCreditoAdapterFactory;
import com.seatseller.core.lugares.CatalogoGrelhas;
import com.seatseller.core.lugares.CatalogoTiposDeLugar;
import com.seatseller.core.lugares.Grelha;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;
import com.seatseller.core.reservas.CatalogoReservas;
import com.seatseller.core.reservas.Reserva;
import com.seatseller.core.reservas.ReservaFactory;
import com.seatseller.core.utilizadores.CatalogoUtilizadores;
import com.seatseller.core.utilizadores.ClienteFinal;
import com.seatseller.core.utilizadores.Utilizador;
import com.seatseller.pagamentos.CartaoCredito;
import com.seatseller.pagamentos.Pagamento;

public class ReservarLugarHandler implements IReservarLugarHandler {

	private CatalogoGrelhas catalogoGrelhas;
	private CatalogoUtilizadores catalogoUtilizadores;
	private CatalogoTiposDeLugar catalogoTipos;
	private CatalogoReservas catalogoReservas;
	private Optional<Utilizador> cli;
	private ReservaFactory factory;
	private Reserva res;
	private SistemaDeCartoesDeCreditoAdapterFactory sisFactory;
	private ISistemaDeCartoesDeCreditoAdapter sisCC;
	private CartaoCredito cc;
	private Lugar lugarRes;
	private Grelha grelhaRes;

	public ReservarLugarHandler(CatalogoUtilizadores catUtilizadores,
			CatalogoGrelhas catG, CatalogoTiposDeLugar catTipos,
			CatalogoReservas catReservas,
			SistemaDeCartoesDeCreditoAdapterFactory sisCC) {
		this.catalogoUtilizadores = catUtilizadores;
		this.catalogoGrelhas = catG;
		this.sisFactory = sisCC;
		this.catalogoTipos = catTipos;
		this.catalogoReservas = catReservas;
		this.factory = new ReservaFactory();
	}

	@Override
	public void indicarCliente(String username) throws DoesNotExistException {
		cli = catalogoUtilizadores.getCliente(username);
		if (!cli.isPresent()) {
			throw new DoesNotExistException(username);
		}
	}

	@Override
	public Iterable<Combinacao> indicarDataHora(LocalDate date,
			LocalTime time) {
		Iterable<Combinacao> lComb;
		res = factory.getProximaReserva();
		res.setCliente(cli);
		res.novaLinha(date, time);

		// 4
		lComb = catalogoGrelhas.getCombinacoes(date, time);

		return lComb;
	}

	@Override
	public String indicarCombinacao(String grelha, String tipoDeLugar)
			throws DoesNotExistException {
		Grelha g = catalogoGrelhas.getGrelha(grelha);
		Optional<TipoDeLugar> t = catalogoTipos.getTipo(tipoDeLugar);
		if (t.isPresent()) {
			LocalDate data = res.getDataCorrente();
			LocalTime hora = res.getHoraCorrente();
			Lugar lugDisponivel = g.getDisponivel(t, data, hora);

			//
			lugarRes = lugDisponivel;
			grelhaRes = g;

			lugDisponivel.adicionaLinha(res.getLinhaR());
			res.getLinhaR().associaLugar(lugDisponivel);
			return lugDisponivel.toString();
		} else {
			throw new DoesNotExistException("Tipo de lugar nao existe");
		}
	}

	@Override
	public void terminarLugares() {
		res.finalizar();
	}

	@Override
	public double indicarCC(String numero, int ccv, int mes, int ano)
			throws InvalidCreditCardException {

		sisCC = sisFactory.getSistemaDeCartoesDeCreditoAdapter();

		boolean b = sisCC.validar(numero, ccv, mes, ano);

		if (b && cli.isPresent()) {
			ClienteFinal cliente = (ClienteFinal) cli.get();

			double preco = 0;
			if (!cliente.temCC(numero)) {
				cc = cliente.criaCC(numero, ccv, mes, ano);
			}
			preco = res.getPreco();
			return preco;
		} else {
			throw new InvalidCreditCardException(
					"O cartao de credito nao e valido");
		}
	}

	@Override
	public String confirmarReserva() {
		String codRes;
		// 1, 2, 3
		Configuration config = Configuration.getInstance();
		boolean cativar = config.cativarDuranteReservas();
		double valor = config.valorDuranteReservas();
		Pagamento pg = new Pagamento(cativar, valor);
		// 4
		if (cli.isPresent()) {
			ClienteFinal cliente = (ClienteFinal) cli.get();
			cliente.registarPagamento(pg);
			// 5
			res.registarPagamento(pg);
			// 6
			cliente.associarReserva(res);
			// 7
			catalogoReservas.registarReserva(res);
		}
		// 8
		String num;
		int ccv;
		int mes;
		int ano;
		num = cc.getNumero();
		ccv = cc.getCcv();
		mes = cc.getMes();
		ano = cc.getAno();
		// 9
		if (cativar) {
			sisCC.cativar(num, ccv, mes, ano, valor);
		} else {
			sisCC.retirar(num, ccv, mes, ano, valor);
		}
		// 10
		// notificar grelhas
		grelhaRes.confirmarReserva(lugarRes);

		// 11
		codRes = res.getCodigo();

		return "R" + codRes;
	}

}
