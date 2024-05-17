package com.ueg.storage.produtos.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ueg.storage.produtos.model.Produto;
import com.ueg.storage.produtos.service.ProdutoService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProdutoService produtoService;

    private Produto produto;

    @BeforeEach
    public void setUp() {
        produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");
        produto.setPreco(100.0);
        produto.setQuantidade(10);
    }

    @Test
    public void testListarTodos() throws Exception {
        when(produtoService.listarTodos()).thenReturn(Arrays.asList(produto));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Produto Teste"));
    }

    @Test
    public void testBuscarPorId() throws Exception {
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/produtos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    public void testAtualizar() throws Exception {
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));
        when(produtoService.salvar(produto)).thenReturn(produto);

        mockMvc.perform(put("/produtos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\": \"Produto Teste\", \"preco\": 100.0, \"quantidade\": 10}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Produto Teste"));
    }

    @Test
    public void testDeletar() throws Exception {
        when(produtoService.buscarPorId(1L)).thenReturn(Optional.of(produto));

        mockMvc.perform(delete("/produtos/1"))
                .andExpect(status().isNoContent());
    }
}
