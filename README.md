# SGPD — Sistema de Gestão de Pessoas

MVP web para apoiar os profissionais de RH da instituição parceira no cadastro, pesquisa, consulta e atualização de informações essenciais dos colaboradores.

## Escopo

O fluxo prioritário reúne os dados em um único ambiente e permite manter os registros atualizados. O projeto usa Java 21, Spring Boot, Thymeleaf e PostgreSQL, organizado como aplicação monolítica em camadas.

O sistema está em desenvolvimento. Use somente dados fictícios até que a instituição aprove os controles de privacidade, operação e hospedagem.

## Tecnologias

Java 21, Spring Boot 3.4.5, Spring MVC, Thymeleaf, Spring Security, Spring Data JPA, Bean Validation, Flyway e PostgreSQL 16.

## Execução local

Pré-requisitos: JDK 21, Maven 3.9+ e Docker com Docker Compose.

1. Inicie o banco: `docker compose up -d`.
2. Configure as variáveis `SGPD_BOOTSTRAP_NAME`, `SGPD_BOOTSTRAP_EMAIL` e `SGPD_BOOTSTRAP_PASSWORD` no ambiente local. Escolha uma senha forte e não envie segredos ao GitHub.
3. Execute `mvn spring-boot:run`.
4. Acesse `http://localhost:8080` e entre com os valores de bootstrap definidos.

A conta inicial só é criada quando as variáveis de bootstrap estão presentes e o e-mail ainda não existe. A senha é codificada com BCrypt antes de ser gravada. Não existe conta padrão embutida no código.

## Configuração e dados

A aplicação lê `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD` e as três variáveis de bootstrap. `.env.example` contém apenas valores demonstrativos para desenvolvimento local. Em hospedagem, configure segredos no mecanismo protegido do provedor. Flyway aplica migrações versionadas e Hibernate valida o esquema sem alterá-lo automaticamente.

## Segurança e limites

As rotas de colaboradores exigem autenticação; CSRF permanece habilitado; consultas usam Spring Data JPA; Thymeleaf escapa texto por padrão; erros não retornam stack trace. Antes de usar dados reais, revisar HTTPS, limitação de tentativas de login, cabeçalhos de segurança, backups e políticas de retenção da instituição. Este MVP não substitui uma auditoria de segurança.

## Organização

- `web`: controladores e formulários validados.
- `model`: entidades Colaborador e Usuário.
- `repository`: persistência via JPA.
- `security`: autenticação, BCrypt e bootstrap opcional da primeira conta.
- `templates` e `static`: telas Thymeleaf e estilos responsivos.
- `db/migration`: esquema versionado do PostgreSQL.

