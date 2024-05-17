package com.ueg.storage.produtos.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ueg.storage.produtos.model.Produto;
import com.ueg.storage.produtos.repository.ProdutoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setQuantidade(10);
    }

    @Test
    public void testListarTodos() {
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto));

        List<Produto> produtos = produtoService.listarTodos();

        assertNotNull(produtos);
        assertEquals(1, produtos.size());
        assertEquals(produto, produtos.get(0));
    }

    @Test
    public void testBuscarPorId() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        Optional<Produto> produtoOpt = produtoService.buscarPorId(1L);

        assertTrue(produtoOpt.isPresent());
        assertEquals(produto, produtoOpt.get());
    }

    @Test
    public void testSalvar() {
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto produtoSalvo = produtoService.salvar(produto);

        assertNotNull(produtoSalvo);
        assertEquals(produto, produtoSalvo);
    }

    @Test
    public void testDeletar() {
        doNothing().when(produtoRepository).deleteById(1L);

        produtoService.deletar(1L);

        verify(produtoRepository, times(1)).deleteById(1L);
    }
}
