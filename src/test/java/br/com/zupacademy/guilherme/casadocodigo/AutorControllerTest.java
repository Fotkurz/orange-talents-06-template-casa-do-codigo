package br.com.zupacademy.guilherme.casadocodigo;


import br.com.zupacademy.guilherme.casadocodigo.modelo.Autor;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void deveriaDevolver400CasoEmailEstejaEmFormatoIncorreto() throws Exception {
        URI uri = new URI("/autores");
        String json =  "{\"nome\":\"guilherme\", \"email\":\"teste\", \"descricao\":\"Ficção Científica\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveRetornar200SeEstiverComFormCorretamentePreenchido() throws Exception {
        URI uri = new URI("/autores");
        String nome = "guilherme";
        String email = "1@gmail.com";
        String descricao = "Ficção Científica";
        String json1 = "{\"nome\":" + nome + "\"";
        String json =  "{\"nome\":\"guilherme\", \"email\":\"1@gmail.com\", \"descricao\":\"Ficção Científica\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveRetornar400QuandoDescricaoPossuiMaisDe400Caracteres() throws Exception {
        URI uri = new URI("/autores");
        String json =  "{\"nome\":\"guilherme\", \"email\":\"guilherme@gmail.com\", \"descricao\":\"" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt " +
                "ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
                "laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat" +
                " non proident, sunt in culpa qui officia deserunt mollit anim id est laborum." + "\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void devePersistirCorretamenteOAutorNoBanco() throws Exception {
        URI uri = new URI("/autores");
        String json =  "{\"nome\":\"guilherme\", \"email\":\"2@gmail.com\", \"descricao\":\"Ficção Científica\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        Autor autor = em.find(Autor.class, 1L);
        Assert.assertNotNull(autor);
    }

    @Test
    public void deveRetornar400QuandoEmailEhDuplicado() throws Exception {
        URI uri = new URI("/autores");
        String json1 =  "{\"nome\":\"guilherme\", \"email\":\"7@gmail.com\", \"descricao\":\"Ficção Científica\"}";
        String json2 =  "{\"nome\":\"jose\", \"email\":\"7@gmail.com\", \"descricao\":\"Ficção Científica\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    public void devePassarCasoDoisAutoresTenhamEmailDiferentes() throws Exception {
        URI uri = new URI("/autores");
        String json1 =  "{\"nome\":\"guilherme\", \"email\":\"6@gmail.com\", \"descricao\":\"Ficção Científica\"}";
        String json2 =  "{\"nome\":\"jose\", \"email\":\"7@gmail.com\", \"descricao\":\"Ficção Científica\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json1)
                .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(json2)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
