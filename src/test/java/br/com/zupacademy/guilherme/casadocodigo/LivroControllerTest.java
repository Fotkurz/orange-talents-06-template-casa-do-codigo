package br.com.zupacademy.guilherme.casadocodigo;


import br.com.zupacademy.guilherme.casadocodigo.controller.form.AutorForm;
import br.com.zupacademy.guilherme.casadocodigo.controller.form.CategoriaForm;
import br.com.zupacademy.guilherme.casadocodigo.controller.form.LivroForm;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
        String resumo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in ";
        String sumario = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
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
        String resumo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris";
        String sumario = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
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
        System.out.println("Autor");
        System.out.println(jsonAutor);

        String jsonCategoria = jsonMapper.writeValueAsString(categoriaForm);
        System.out.println("Categoria");
        System.out.println(jsonCategoria);

        String jsonLivro = jsonMapper.writeValueAsString(livroForm);
        System.out.println("Livro");
        System.out.println(jsonLivro);

        System.out.println("Mock Autores");
        mockMvc.perform(MockMvcRequestBuilders.post(uriAutores)
                .content(jsonAutor)
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Mock Categoria");
        mockMvc.perform(MockMvcRequestBuilders.post(uriCategoria)
                .content(jsonCategoria)
                .contentType(MediaType.APPLICATION_JSON));

        System.out.println("Mock Livros");
        mockMvc.perform(MockMvcRequestBuilders.post(uriLivros)
                .content(jsonLivro)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    @Transactional
    public void deveRetornar200SeguidoDeUmJsonComTodosOsLivros() throws Exception {
        URI uri = new URI("/livros");
        String titulo = "Senhor dos aneis";
        String resumo = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris";
        String sumario = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        BigDecimal preco = new BigDecimal("1223.50");
        Integer paginas = 500;
        String isbn = "988-3-16-148410-0";
        LocalDate dataPublicacao = LocalDate.of(2022, 1, 1);

        Autor autor = new Autor("Guilherme", "guilherme@gmail.com", "descricao");
        Categoria categoria = new Categoria("Ficção");

        Livro livro1 = new Livro(titulo, resumo, sumario, preco,
                paginas, isbn, dataPublicacao, autor, categoria);

        Livro livro2 = new Livro("O hobbit", resumo, sumario,
                preco, paginas, "998-3-16-148410-0", dataPublicacao, autor, categoria);

        em.persist(autor);
        em.persist(categoria);
        em.persist(livro1);
        em.persist(livro2);


        LivroResponseDto dto1 = new LivroResponseDto(em.find(Livro.class, 1L));
        LivroResponseDto dto2 = new LivroResponseDto(em.find(Livro.class, 2L));
        String json1 = jsonMapper.writeValueAsString(dto1);
        String json2 = jsonMapper.writeValueAsString(dto2);

        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string("[" + json1 + "," + json2 + "]"));
    }
}
