package persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.stat.Statistics;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

/**
 * 
 * @author Davi Lessa
 * @since 01/05/2016
 */

public class GestorBancoDados {

	protected SessionFactory sessionFactory;
	protected ServiceRegistry serviceRegistry;

	protected Connection connection;
	protected Configuration configuration;
	protected String usuario;
	protected String senha;
	protected String host;
	protected String porta;
	protected String bd;
	protected String schema;
	protected boolean exibirSQL;
	protected static final String CFG_HIBERNATE = "persistence/hibernate.cfg.xml";
	private static GestorBancoDados instancia;
	
	public GestorBancoDados() {
	            try {
					configurar();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}
	
	 public final void configurar() throws SQLException {
		 
		 try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	        usuario = "postgres";
	        senha = "camilasiqueira";
	        host = "localhost";
	        porta = "5432";
	        bd = "teleassistencia";
	        schema = "biohelp";

	        String url = "jdbc:postgresql://" + host + ":" + porta + "/" + bd;
	       
	        
	        configuration = new Configuration()
	                .setProperty("hibernate.connection.username", usuario)
	                .setProperty("hibernate.connection.password", senha)
	                .setProperty("hibernate.connection.url", url)
	                .setProperty("hibernate.default_schema", schema)
	                .setProperty("hibernate.show_sql", exibirSQL ? "true" : "false")
	                .setProperty("hibernate.format_sql", exibirSQL ? "true" : "false")
	                .setProperty("hibernate.generate_statistics", "false")
	                .configure(CFG_HIBERNATE);
	        connection = DriverManager.getConnection(url, usuario, senha);

	        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();


	    }
	
	
	/**
     * Executa o configurador do banco de dados, verificando a existencia do
     * esquema e criando as tabelas necessárias
     *
     * @param atualizaEsquema verdadeiro para atualizar o esquema do hibernate,
     * somente ser� executado se o banco de dados já existir
     * @throws SQLException
     */
    public void executar(boolean atualizaEsquema) throws SQLException {

        if (!schemaExiste()) {

            criarSchema(schema);
            executarSql("SET search_path = " + schema);
            exportaEsquemaHibernate();

        } else if (atualizaEsquema) {
            atualizaEsquemaHibernate();
        }

    }
    
    /**
     * Cria um novo esquema no banco de dados
     *
     * @param schema
     * @return
     */
    private void criarSchema(String schema) throws SQLException {
        executarSql("CREATE SCHEMA " + schema);
    }
    
    /**
     * Executa um comando SQL
     *
     * @param sql
     * @return true caso a consulta tenha sido realizada com sucesso
     * @throws SQLException
     */
    public boolean executarSql(String sql) throws SQLException {
        try (Statement st = connection.createStatement();) {
            return st.execute(sql);
        }
    }
    
    /**
     * Verifica a existência do esquema
     *
     * @return
     * @throws SQLException
     */
    private boolean schemaExiste() throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet schemas = metaData.getSchemas();) {
            while (schemas.next()) {
                if (schemas.getString(1).endsWith(schema)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Atualiza o esquema do hibernate de acordo com o hibernate.cfg.xml
     */
    private void atualizaEsquemaHibernate() {
        SchemaUpdate schemaUpdate = new SchemaUpdate(configuration);
        schemaUpdate.execute(true, true);
    }
    
    /**
     * Exporta o esquema do hibernate de acordo com o hibernate.cfg.xml
     */
    private void exportaEsquemaHibernate() {
        SchemaExport schemaExport = new SchemaExport(configuration);
        schemaExport.execute(true, true, false, true);
    }

	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public Connection getConnection() {
		return connection;
	}

	public Configuration getConfiguration() {
		return configuration;
	}
    
	  public SessionFactory getSessionFactory() {
	        if (sessionFactory == null) {
	            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	        }
	        return sessionFactory;
	    }

	    public Session getSession() {
	        Session session = getSessionFactory().openSession();
	        return session;
	    }

	    public void beginTransaction() {
	        getSession().beginTransaction();
	    }

	    public void commitTransaction() {
	        getSession().getTransaction().commit();
	    }

	    public void rollBackTransaction() {
	        getSession().getTransaction().rollback();
	    }

	    public void closeSession() {
	        getSession().close();
	    }

	    public Statistics getStatistics() {
	        return getSessionFactory().getStatistics();
	    }

		public static GestorBancoDados getInstancia() {
			if (instancia == null) {
	            instancia = new GestorBancoDados();
	        }
	        return instancia;
		}

		public boolean isExibirSQL() {
			return exibirSQL;
		}

		public void setExibirSQL(boolean exibirSQL) {
			this.exibirSQL = exibirSQL;
		}

		public String getSchema() {
			return schema;
		}
		


	

}
