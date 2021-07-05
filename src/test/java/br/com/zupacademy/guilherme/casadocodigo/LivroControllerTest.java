package br.com.zupacademy.guilherme.casadocodigo;


import br.com.zupacademy.guilherme.casadocodigo.controller.form.AutorForm;
import br.com.zupacademy.guilherme.casadocodigo.controller.form.CategoriaForm;
import br.com.zupacademy.guilherme.casadocodigo.controller.form.LivroForm;
import br.com.zupacademy.guilherme.casadocodigo.dto.LivroDetalheResponseDto;
import br.com.zupacademy.guilherme.casadocodigo.dto.LivroResponseDto;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Categoria;
import br.com.zupacademy.guilherme.casadocodigo.modelo.Livro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ObjectMapper jsonMapper;

    @Test
    @DisplayName("Deve falhar ao tentar criar um livro nome, por que o autor e categoria não foram cadastradas")
    public void deveriaDevolver400CasoFalheEmAlgumaConstraint() throws Exception {
        URI uri = new URI("/livros");
        String titulo = "Senhor dos aneis";
        String resumo = "Resumo".repeat(10);
        String sumario = "Sumario".repeat(10);
        BigDecimal preco = new BigDecimal("1223.50");
        Integer paginas = 500;
        String isbn = "988-3-16-148410-0";
        LocalDate dataPublicacao = LocalDate.of(2022, 1, 1);
        Long idAutor = 1L;
        Long idCategoria = 1L;

        LivroForm form = new LivroForm(titulo, resumo, sumario,
                                        preco, paginas, isbn,
                                dataPublicacao, idAutor, idCategoria);

        String json = jsonMapper.writeValueAsString(form);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveRetornar200AoCriarCorretamenteOLivro() throws Exception {
        URI uriLivros = new URI("/livros");
        URI uriAutores = new URI("/autores");
        URI uriCategoria = new URI("/categorias");
        String titulo = "Senhor dos aneis";
        String resumo = "Resumo".repeat(10);
        String sumario = "Sumario".repeat(10);
        BigDecimal preco = new BigDecimal("1223.50");
        Integer paginas = 500;
        String isbn = "988-3-16-148410-0";
        LocalDate dataPublicacao = LocalDate.of(2022, 1, 1);
        Long idAutor = 1L;
        Long idCategoria = 1L;

        LivroForm livroForm = new LivroForm(titulo, resumo, sumario,
                preco, paginas, isbn,
                dataPublicacao, idAutor, idCategoria);

        AutorForm autorForm = new AutorForm("Guilherme","guilherme@gmail.com", "esse e a desc");

        CategoriaForm categoriaForm = new CategoriaForm("FICCAO");

        String jsonAutor = jsonMapper.writeValueAsString(autorForm);

        String jsonCategoria = jsonMapper.writeValueAsString(categoriaForm);

        String jsonLivro = jsonMapper.writeValueAsString(livroForm);

        mockMvc.perform(MockMvcRequestBuilders.post(uriAutores)
                .content(jsonAutor)
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post(uriCategoria)
                .content(jsonCategoria)
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post(uriLivros)
                .content(jsonLivro)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetornar200SeguidoDeUmJsonComTodosOsLivros() throws Exception {
        URI uri = new URI("/livros");
        String titulo = "Senhor dos aneis";
        String resumo = "Resumo".repeat(10);
        String sumario = "Sumario".repeat(10);
        BigDecimal preco = new BigDecimal("1223.50");
        Integer paginas = 500;
        String isbn = "988-3-16-148410-0";
        LocalDate dataPublicacao = LocalDate.of(2022, 1, 1);

        Autor autor = new Autor("Guilherme", "guilherme@gmail.com", "descricao");
        Categoria categoria = new Categoria("FICCAO");

        em.persist(autor);
        em.persist(categoria);

        LivroForm livroform = new LivroForm(titulo, resumo, sumario, preco,
                paginas, isbn, dataPublicacao, 1L, 1L);

        String jsonLivro = jsonMapper.writeValueAsString(livroform);
        System.out.println(jsonLivro);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(jsonLivro));

        LivroResponseDto dto1 = new LivroResponseDto(em.find(Livro.class, 1L));
        String json1 = jsonMapper.writeValueAsString(dto1);
        System.out.println(json1);

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json("[" + json1 + "]"));
    }

    @Test
    public void deveRetornar200QuandoPassadoComoParametroOIdDoLivro() throws Exception {
        String titulo = "Senhor dos aneis";
        String resumo = "Resumo".repeat(10);
        String sumario = "Sumario".repeat(10);
        BigDecimal preco = new BigDecimal("1223.50");
        Integer paginas = 500;
        String isbn = "988-3-16-148410-0";
        LocalDate dataPublicacao = LocalDate.of(2022, 1, 1);

        Autor autor = new Autor("Guilherme", "guilherme@gmail.com", "descricao");
        Categoria categoria = new Categoria("FICCAO");

        em.persist(autor);
        em.persist(categoria);

        LivroForm livroform = new LivroForm(titulo, resumo, sumario, preco,
                paginas, isbn, dataPublicacao, 1L, 1L);
        String json = jsonMapper.writeValueAsString(livroform);

        mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                .contentType(MediaType.APPLICATION_JSON).content(json));

        Livro livro = em.find(Livro.class, 1L);
        LivroDetalheResponseDto dto = new LivroDetalheResponseDto(livro, autor, categoria);

        String jsonDto = jsonMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/livros/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(jsonDto));
    }

    @Test
    public void deveRetornar404CasoNaoExistaOLivroPassadoNoId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/livros/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(404));

    }
}
