package br.com.alura.comex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class DataBaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initializeDatabase() {
        try {
            addColumns();
            removeForeignKeyConstraints();
            addForeignKeyConstraints();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addColumns() {
        // Verificar se a coluna tipo_desconto já existe antes de adicionar
        Integer columnCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user_tab_columns WHERE table_name = 'PEDIDOS' AND column_name = 'TIPO_DESCONTO'",
                Integer.class
        );

        if (columnCount != null && columnCount == 0) {
            // Adicionar coluna tipo_desconto em pedidos
            jdbcTemplate.execute("ALTER TABLE pedidos ADD tipo_desconto varchar2(255 char)");

            // Atualizar valores NULL para 'NENHUM'
            jdbcTemplate.execute("UPDATE pedidos SET tipo_desconto = 'NENHUM' WHERE tipo_desconto IS NULL");

            // Modificar a coluna para NOT NULL
            jdbcTemplate.execute("ALTER TABLE pedidos MODIFY tipo_desconto NOT NULL");
        } else {
            System.out.println("Coluna tipo_desconto já existe na tabela pedidos.");
        }
    }

    private void removeForeignKeyConstraints() {
        // Remover constraint de chave estrangeira existente, se houver
        try {
            jdbcTemplate.execute("ALTER TABLE pedidos DROP CONSTRAINT FK6wgk5emlhdcthucnnmi4dpl33");
        } catch (Exception e) {
            System.out.println("Constraint FK6wgk5emlhdcthucnnmi4dpl33 não encontrada ou não pode ser removida: " + e.getMessage());
        }

        try {
            jdbcTemplate.execute("ALTER TABLE produto DROP CONSTRAINT fk_categoria_produto");
        } catch (Exception e) {
            System.out.println("Constraint fk_categoria_produto não encontrada ou não pode ser removida: " + e.getMessage());
        }
    }

    private void addForeignKeyConstraints() {
        // Verificar e adicionar constraints de chave estrangeira

        // Verifica se a chave primária existe na tabela cliente
        Integer clienteCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM cliente",
                Integer.class
        );

        if (clienteCount != null && clienteCount > 0) {
            // Adicionar constraint de chave estrangeira em pedidos
            try {
                jdbcTemplate.execute(
                        "ALTER TABLE pedidos ADD CONSTRAINT FK6wgk5emlhdcthucnnmi4dpl33 FOREIGN KEY (cliente_id) REFERENCES cliente(id)"
                );
            } catch (Exception e) {
                System.out.println("Erro ao adicionar constraint FK6wgk5emlhdcthucnnmi4dpl33: " + e.getMessage());
            }
        } else {
            System.out.println("Tabela cliente não contém registros, constraint FK6wgk5emlhdcthucnnmi4dpl33 não pode ser adicionada.");
        }

        // Verifica se a chave primária existe na tabela categoria
        Integer categoriaCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM categoria",
                Integer.class
        );

        if (categoriaCount != null && categoriaCount > 0) {
            // Adicionar constraint de chave estrangeira em produto
            try {
                jdbcTemplate.execute(
                        "ALTER TABLE produto ADD CONSTRAINT fk_categoria_produto FOREIGN KEY (categoria_id) REFERENCES categoria(id)"
                );
            } catch (Exception e) {
                System.out.println("Erro ao adicionar constraint fk_categoria_produto: " + e.getMessage());
            }
        } else {
            System.out.println("Tabela categoria não contém registros, constraint fk_categoria_produto não pode ser adicionada.");
        }
    }
}
