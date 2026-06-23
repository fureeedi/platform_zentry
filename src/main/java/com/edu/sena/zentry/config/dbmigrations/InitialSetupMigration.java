package com.edu.sena.zentry.config.dbmigrations;

import com.edu.sena.zentry.config.Constants;
import com.edu.sena.zentry.domain.Authority;
import com.edu.sena.zentry.domain.User;
import com.edu.sena.zentry.security.AuthoritiesConstants;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import java.time.Instant;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Creates the initial database setup.
 */
@ChangeUnit(id = "users-initialization", order = "001")
public class InitialSetupMigration {

    private final MongoTemplate template;

    public InitialSetupMigration(MongoTemplate template) {
        this.template = template;
    }

    @Execution
    public void changeSet() {
        Authority userAuthority = createUserAuthority();
        userAuthority = template.save(userAuthority);
        Authority adminAuthority = createAdminAuthority();
        adminAuthority = template.save(adminAuthority);
        Authority administradorConjuntoAuthority = createAdministradorConjuntoAuthority();
        administradorConjuntoAuthority = template.save(administradorConjuntoAuthority);
        Authority clienteAuthority = createClienteAuthority();
        clienteAuthority = template.save(clienteAuthority);
        addUsers(userAuthority, adminAuthority, administradorConjuntoAuthority, clienteAuthority);
    }

    @RollbackExecution
    public void rollback() {}

    private Authority createAuthority(String authority) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(authority);
        return adminAuthority;
    }

    private Authority createAdminAuthority() {
        Authority adminAuthority = createAuthority(AuthoritiesConstants.ADMIN);
        return adminAuthority;
    }

    private Authority createUserAuthority() {
        Authority userAuthority = createAuthority(AuthoritiesConstants.USER);
        return userAuthority;
    }

    private Authority createAdministradorConjuntoAuthority() {
        Authority administradorConjuntoAuthority = createAuthority(AuthoritiesConstants.ADMINISTRADOR_CONJUNTO);
        return administradorConjuntoAuthority;
    }

    private Authority createClienteAuthority() {
        Authority clienteAuthority = createAuthority(AuthoritiesConstants.CLIENTE);
        return clienteAuthority;
    }

    private void addUsers(
        Authority userAuthority,
        Authority adminAuthority,
        Authority administradorConjuntoAuthority,
        Authority clienteAuthority
    ) {
        User user = createUser(userAuthority);
        template.save(user);
        User admin = createAdmin(adminAuthority, userAuthority);
        template.save(admin);
        User administradorConjunto = createAdministradorConjunto(administradorConjuntoAuthority, userAuthority);
        template.save(administradorConjunto);
        User cliente = createCliente(clienteAuthority, userAuthority);
        template.save(cliente);
    }

    private User createUser(Authority userAuthority) {
        User userUser = new User();
        //userUser.setId("user-2");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("User");
        userUser.setLastName("User");
        userUser.setEmail("user@localhost");
        userUser.setActivated(true);
        userUser.setLangKey("es");
        userUser.setCreatedBy(Constants.SYSTEM);
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(userAuthority);
        return userUser;
    }

    private User createAdmin(Authority adminAuthority, Authority userAuthority) {
        User adminUser = new User();
        //adminUser.setId("user-1");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("es");
        adminUser.setCreatedBy(Constants.SYSTEM);
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        return adminUser;
    }

    private User createAdministradorConjunto(Authority administradorConjuntoAuthority, Authority userAuthority) {
        User administradorConjuntoUser = new User();
        administradorConjuntoUser.setLogin("administrador_conjunto");
        administradorConjuntoUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        administradorConjuntoUser.setFirstName("administrador_conjunto");
        administradorConjuntoUser.setLastName("administrador_conjunto");
        administradorConjuntoUser.setEmail("administradorConjunto@localhost");
        administradorConjuntoUser.setActivated(true);
        administradorConjuntoUser.setLangKey("es");
        administradorConjuntoUser.setCreatedBy(Constants.SYSTEM);
        administradorConjuntoUser.setCreatedDate(Instant.now());
        administradorConjuntoUser.getAuthorities().add(administradorConjuntoAuthority);
        administradorConjuntoUser.getAuthorities().add(userAuthority);
        return administradorConjuntoUser;
    }

    private User createCliente(Authority clienteAuthority, Authority userAuthority) {
        User clienteUser = new User();
        //adminUser.setId("user-1");
        clienteUser.setLogin("cliente");
        clienteUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        clienteUser.setFirstName("cliente");
        clienteUser.setLastName("cliente");
        clienteUser.setEmail("cliente@localhost");
        clienteUser.setActivated(true);
        clienteUser.setLangKey("es");
        clienteUser.setCreatedBy(Constants.SYSTEM);
        clienteUser.setCreatedDate(Instant.now());
        clienteUser.getAuthorities().add(clienteAuthority);
        clienteUser.getAuthorities().add(userAuthority);
        return clienteUser;
    }
}
