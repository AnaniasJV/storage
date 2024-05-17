package com.ueg.storage.produtos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ueg.storage.produtos.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    
}
