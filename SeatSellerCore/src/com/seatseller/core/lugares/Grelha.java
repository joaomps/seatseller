package com.seatseller.core.lugares;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.seatseller.api.wrappers.Combinacao;
import com.seatseller.core.lugares.Lugar;
import com.seatseller.core.lugares.TipoDeLugar;
import com.seatseller.core.lugares.alocacao.EncontrarLugarStrategyFactory;
import com.seatseller.core.lugares.alocacao.IEncontrarLugarStrategy;

import java.util.Observable;

/**
 * Representa uma grelha e as operacoes associadas ao uso desta
 * 
 * @author 031
 *
 */

public class Grelha extends Observable {

	private String designacao;
	private double indice;
	private List<Lugar> listaLugares;
	private int maiorX;
	private int maiorY;
	private List<Combinacao> lc;

	/**
	 * Cria uma nova grelha
	 * 
	 * @param nome
	 *            designacao da grelha
	 * @param indicePreco
	 *            indice de preco da grelha
	 */
	public Grelha(String nome, double indicePreco) {
		this.designacao = nome;
		this.indice = indicePreco;
		listaLugares = new ArrayList<Lugar>();
		lc = new ArrayList<Combinacao>();
	}

	/**
	 * Devolve a designacao da grelha
	 * 
	 * @return designacao da grelha
	 */
	public String getDesig() {
		return designacao;
	}

	/**
	 * Devolve o indice da grelha
	 * 
	 * @return indice da grelha
	 */
	public double getIndice() {
		return indice;
	}

	/**
	 * Cria lugares para ocupar a grelha na forma de uma matriz x,y , sendo que
	 * os lugares irao ser do tipo padrao caso este exista
	 * 
	 * @param i
	 *            valor maximo x
	 * @param j
	 *            valor maximo y
	 * @param padrao
	 *            tipo de lugar padrao
	 * @param g
	 *            grelha associada aos lugares
	 */
	public void criaLugares(int i, int j, Optional<TipoDeLugar> padrao) {
		for (int n = 0; n < i; n++) {
			for (int m = 0; m < j; m++) {
				Lugar l = new Lugar(n, m, padrao, this);
				listaLugares.add(l);
			}
		}
		this.maiorX = i;
		this.maiorY = j;

	}

	/**
	 * Devolve a lista de todos os lugares presentes na grelha
	 * 
	 * @return lista de todos os lugares
	 */
	public List<Lugar> getListaLugares() {
		return listaLugares;
	}

	/**
	 * Altera o tipo de lugar de todos os lugares para o tipo dado
	 * 
	 * @param tp
	 *            tipo de lugar padrao para esta grelha
	 */
	public void defineTipoLugarPadrao(Optional<TipoDeLugar> tp) {
		for (Lugar l : listaLugares) {
			l.definirTipo(tp);
		}
	}

	/**
	 * Verifica se as coordenadas dadas sao validas para a grelha
	 * 
	 * @param i
	 *            coordenada x
	 * @param j
	 *            coordenada y
	 * @return true caso i e j sejam valores possiveis para um lugar na grelha
	 */
	public boolean coordenadasValidas(int i, int j) {
		return i >= 0 && i < maiorX && j >= 0 && j < maiorY;
	}

	/**
	 * Devolve o lugar que se encontra nas coordenadas dadas
	 * 
	 * @param i
	 *            coordenada x
	 * @param j
	 *            coordenada y
	 * @return lugar nas coordenadas i,j
	 */
	public Lugar getLugar(int i, int j) {
		Lugar l = null;
		for (Lugar lugar : listaLugares) {
			if (lugar.getX() == i && lugar.getY() == j) {
				l = lugar;
			}
		}
		return l;
	}

	/**
	 * Muda o tipo de lugar que se encontra nas coordenadas dadas para o tipo
	 * dado
	 * 
	 * @param i
	 *            coordenada x
	 * @param j
	 *            coordenada y
	 * @param tp
	 *            tipo de lugar novo
	 */
	public void defineTipoLugar(int i, int j, Optional<TipoDeLugar> tp) {
		Lugar l = getLugar(i, j);
		l.definirTipo(tp);
	}

	// Devolve true se aumentou disponibilidade de qualquer combinacao

	/**
	 * Verifica se o tipo de lugar dado ja se encontra na lista de combinacoes e
	 * caso se encontre aumenta a capacidade da lista
	 * 
	 * @param tipoLugar
	 *            tipo de lugar a procurar nas combinacoes existentes
	 * @return true caso o tipo ja exista na lista de combinacoes, false caso
	 *         contrario
	 */
	public boolean verificaTipoIgual(TipoDeLugar tipoLugar) {
		boolean encontrou = false;
		String tp = tipoLugar.getDescricao();
		for (Combinacao c : lc) {
			String tl = c.getTipoDeLugar();
			if (tp.equals(tl)) {
				c.aumentaDisponibilidade();
				encontrou = true;
			}
		}
		return encontrou;
	}

	/**
	 * Devolve uma lista que contem as combinacoes <nome da grelha, designacao
	 * do tipo de lugar, preco do lugar> para os lugares disponiveis da grelha
	 * 
	 * @param date
	 *            data na qual o lugar esta disponivel
	 * @param time
	 *            hora na qual o lugar esta disponivel
	 * @return lista de combinacoes
	 */
	public List<Combinacao> getCombinacoes(LocalDate date, LocalTime time) {
		if (lc.isEmpty()) {
			lc = new ArrayList<Combinacao>();
		}
		Combinacao c;
		for (Lugar l : listaLugares) {
			if (l.disponivel(date, time)) {
				TipoDeLugar tp = l.getDesignacaoTipo();
				if (!verificaTipoIgual(tp)) {
					c = new Combinacao(this.designacao, tp.getDesig(),
							l.getPreco());
					lc.add(c);
				}
			}
		}
		
		return lc;
	}

	/**
	 * Devolve o primeiro lugar disponivel do tipo dado de acordo com a
	 * estrategia definida em config.properties
	 * 
	 * @param t
	 *            tipo de lugar a encontrar
	 * @param data
	 *            data na qual o lugar tem de estar disponivel
	 * @param hora
	 *            hora na qual o lugar tem de estar disponivel
	 * @return lugar disponivel nas condicoes dadas
	 */
	public Lugar getDisponivel(Optional<TipoDeLugar> t, LocalDate data,
			LocalTime hora) {
		EncontrarLugarStrategyFactory factoryEstrategias = EncontrarLugarStrategyFactory
				.getInstance();
		IEncontrarLugarStrategy strat = factoryEstrategias
				.getEncontrarLugarStrategy();
		if (t.isPresent()) {
			Optional<Lugar> opt = strat.getLugar(this, t.get(), data, hora);
			if (opt.isPresent()) {
				return opt.get();
			}
		}
		return null;
	}

	/**
	 * Notifica os observers de que a reserva foi confirmada para o lugar dado
	 * 
	 * @param lugarRes
	 *            lugar reservado
	 */
	public void confirmarReserva(Lugar lugarRes) {
		this.setChanged();
		this.notifyObservers(lugarRes);
	}

}
