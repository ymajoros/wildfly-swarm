package org.wildfly.swarm.jpa.postgresql;

import org.wildfly.swarm.config.JPA;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.spi.api.Fraction;
import org.wildfly.swarm.spi.api.annotations.Configuration;
import org.wildfly.swarm.spi.api.annotations.Default;

@Configuration(
        marshal = true,
        extension = "org.jboss.as.jpa",
        parserFactoryClassName = "org.wildfly.swarm.jpa.postgresql.ParserFactory"
)
public class PostgreSQLJPAFraction extends JPA<PostgreSQLJPAFraction> implements Fraction {

    public PostgreSQLJPAFraction() {
    }

    @Default
    public static PostgreSQLJPAFraction createDefaultFraction() {
        return new PostgreSQLJPAFraction()
                .defaultExtendedPersistenceInheritance(DefaultExtendedPersistenceInheritance.DEEP);
    }

    public PostgreSQLJPAFraction inhibitDefaultDatasource() {
        this.inhibitDefaultDatasource = true;
        return this;
    }

    @Override
    public void initialize(Fraction.InitContext initContext) {
        if (!inhibitDefaultDatasource) {
            final DatasourcesFraction datasources = new DatasourcesFraction()
                    .jdbcDriver("postgresql", (d) -> {
                        d.driverClassName("org.postgresql.Driver");
                        d.xaDatasourceClass("org.postgresql.xa.PGXADataSource");
                        d.driverModuleName("org.postgresql");
                    })
                    .dataSource("ExampleDS", (ds) -> {
                        ds.driverName("postgresql");
                        ds.connectionUrl("jdbc:postgresql://localhost:5432/test");
                        ds.userName("postgres");
                        ds.password("postgres");
                    });

            initContext.fraction(datasources);
            System.err.println("setting default Datasource to ExampleDS");
            defaultDatasource("jboss/datasources/ExampleDS");
        }
    }

    private boolean inhibitDefaultDatasource = false;
}
