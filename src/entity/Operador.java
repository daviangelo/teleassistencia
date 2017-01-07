package entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Classe que representa o operador.
 * @author Davi
 *
 */

@Entity
@Table(name = "operador")
public class Operador implements Login, Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String nome;
	private String login;
	private String senha;
	
	public Operador(){}
	
	
	public Operador(int id, String nome, String login, String senha) {
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}


	@Id
	@SequenceGenerator(name = "operador_seq", sequenceName = "operador_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operador_seq")
	@Column(name = "id")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String getLogin() {
		return this.login;
	}

	@Override
	public void setLogin(String login) {
		this.login = login;

	}

	@Override
	public String getSenha() {
		return this.senha;
	}

	@Override
	public void setSenha(String senha) {
		this.senha = senha;

	}

}
